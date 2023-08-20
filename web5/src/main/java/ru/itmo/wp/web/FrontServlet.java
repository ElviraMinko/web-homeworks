package ru.itmo.wp.web;

import freemarker.template.*;
import ru.itmo.wp.web.exception.NotFoundException;
import ru.itmo.wp.web.exception.RedirectException;
import ru.itmo.wp.web.page.IndexPage;
import ru.itmo.wp.web.page.NotFoundPage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class FrontServlet extends HttpServlet {
    private static final String BASE_PACKAGE = FrontServlet.class.getPackage().getName() + ".page";
    private static final String DEFAULT_ACTION = "action";

    private Configuration sourceConfiguration;
    private Configuration targetConfiguration;

    private Configuration newFreemarkerConfiguration(String templateDirName, boolean debug)
            throws ServletException {
        File templateDir = new File(templateDirName);
        if (!templateDir.isDirectory()) {
            return null;
        }

        Configuration configuration = new Configuration(Configuration.VERSION_2_3_31);
        try {
            configuration.setDirectoryForTemplateLoading(templateDir);
        } catch (IOException e) {
            throw new ServletException("Can't create freemarker configuration [templateDir="
                    + templateDir + "]");
        }
        configuration.setDefaultEncoding(StandardCharsets.UTF_8.name());
        configuration.setTemplateExceptionHandler(debug ? TemplateExceptionHandler.HTML_DEBUG_HANDLER :
                TemplateExceptionHandler.RETHROW_HANDLER);

        configuration.setLogTemplateExceptions(false);
        configuration.setWrapUncheckedExceptions(true);

        return configuration;
    }

    @Override
    public void init() throws ServletException {
        sourceConfiguration = newFreemarkerConfiguration(
                getServletContext().getRealPath("/") + "../../src/main/webapp/WEB-INF/templates", true);
        assert sourceConfiguration != null;
        sourceConfiguration.setLocalizedLookup(false);
        targetConfiguration = newFreemarkerConfiguration(
                getServletContext().getRealPath("WEB-INF/templates"), false);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Route route = Route.newRoute(request);
        try {
            process(route, request, response);
        } catch (NotFoundException e) {
            try {
                process(Route.newNotFoundRoute(), request, response);
            } catch (NotFoundException notFoundException) {
                throw new ServletException(notFoundException);
            }
        }
    }

    private void process(Route route, HttpServletRequest request, HttpServletResponse response)
            throws NotFoundException, ServletException, IOException {
        Class<?> pageClass;
        try {
            pageClass = Class.forName(route.getClassName());
        } catch (ClassNotFoundException e) {
            throw new NotFoundException();
        }

        Method method = null;
        Map<String, Object> view = new HashMap<>();
        Object[] parameters = new Object[0];
        Class<?>[] parameterTypes;
        Map<Class<?>, Object> map = new HashMap<Class<?>, Object>() {{
            put(Map.class, view);
            put(HttpServletRequest.class, request);
        }};
        for (Class<?> clazz = pageClass; method == null && clazz != null; clazz = clazz.getSuperclass()) {
            ArrayList<Method> methods = new ArrayList<>(Arrays.asList(clazz.getDeclaredMethods()));
            for (Method it : methods) {
                if (it.getName().equals(route.getAction())) {
                    method = it;
                    parameterTypes = method.getParameterTypes();
                    parameters = new Object[parameterTypes.length];
                    for (int i = 0; i < parameters.length; i++) {
                       if (map.containsKey(parameterTypes[i])) {
                           parameters[i] = map.get(parameterTypes[i]);
                       } else {
                           method = null;
                           break;
                       }
                    }
                }
            }
        }

        if (method == null) {
            throw new NotFoundException();
        }

        Object page;
        try {
            page = pageClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException
                | NoSuchMethodException | InvocationTargetException e) {
            throw new ServletException("Can't create page [pageClass="
                    + pageClass + "]");
        }

        method.setAccessible(true);
        try {
            method.invoke(page, parameters);
        } catch (IllegalAccessException e) {
            throw new ServletException("Can't invoke action method [pageClass="
                    + pageClass + ", method=" + method + "]");
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RedirectException) {
                RedirectException redirectException = (RedirectException) cause;
                response.sendRedirect(redirectException.getTarget());
                return;
            } else {
                throw new ServletException("Can't invoke action method [pageClass="
                        + pageClass + ", method=" + method + "]", cause);
            }
        }

        Template template;

        if (request.getSession().getAttribute("lang") != null) {
            template = newTemplate(pageClass.getSimpleName() + "_" + request.getSession().getAttribute("lang") + ".ftlh");
        } else {
            template = newTemplate(pageClass.getSimpleName() + ".ftlh");
        }
        response.setContentType("text/html");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        try {
            template.process(view, response.getWriter());
        } catch (TemplateException e) {
            if (sourceConfiguration == null) {
                throw new ServletException("Can't render template [pageClass="
                        + pageClass + ", action=" + method + "]", e);
            }
        }
    }

    private Template newTemplate(String templateName) throws ServletException {
        Template template = null;

        if (sourceConfiguration != null) {
            try {
                template = sourceConfiguration.getTemplate(templateName);
            } catch (TemplateNotFoundException ignored) {
                // No operations.
            } catch (IOException e) {
                throw new ServletException("Can't load template [templateName=" + templateName + "]", e);
            }
        }

        if (template == null && targetConfiguration != null) {
            try {
                template = targetConfiguration.getTemplate(templateName);
            } catch (TemplateNotFoundException ignored) {
                // No operations.
            } catch (IOException e) {
                throw new ServletException("Can't load template [templateName=" + templateName + "]", e);
            }
        }

        if (template == null) {
            throw new ServletException("Can't find template [templateName=" + templateName + "]");
        }

        return template;
    }

    private static class Route {
        private final String className;
        private final String action;

        private Route(String className, String action) {
            this.className = className;
            this.action = action;
        }

        private String getClassName() {
            return className;
        }

        private String getAction() {
            return action;
        }

        private static Route newNotFoundRoute() {
            return new Route(NotFoundPage.class.getName(), DEFAULT_ACTION);
        }

        private static Route newIndexRoute() {
            return new Route(IndexPage.class.getName(), DEFAULT_ACTION);
        }

        private static boolean isCorrectLanguage(String language) {
            final List<String> langs = Arrays.asList("ru", "en");
            return langs.contains(language);
//            boolean answer = language.length() == 2;
//            for (int i = 0; i <= 1; i++) {
//                answer &= 'a' <= language.charAt(i) && language.charAt(i) <= 'z';
//            }
//            return answer;
        }

        private static Route newRoute(HttpServletRequest request) {
            String uri = request.getRequestURI();
            List<String> classNameParts = Arrays.stream(uri.split("/"))
                    .filter(part -> !part.isEmpty())
                    .collect(Collectors.toList());

            HttpSession session = request.getSession();
            String lang = request.getParameter("lang");
            if (lang != null && isCorrectLanguage(lang)) {
                if (lang.equals("en")) {
                    session.removeAttribute("lang");
                } else {
                    session.setAttribute("lang", lang);
                }
            }
            if (classNameParts.isEmpty()) {
                return newIndexRoute();
            }

            StringBuilder simpleClassName = new StringBuilder(classNameParts.get(classNameParts.size() - 1));
            int lastDotIndex = simpleClassName.lastIndexOf(".");
            simpleClassName.setCharAt(lastDotIndex + 1,
                    Character.toUpperCase(simpleClassName.charAt(lastDotIndex + 1)));
            classNameParts.set(classNameParts.size() - 1, simpleClassName.toString());

            String className = BASE_PACKAGE + "." + String.join(".", classNameParts) + "Page";

            String action = request.getParameter("action");
            if (action == null || action.isEmpty()) {
                action = DEFAULT_ACTION;
            }
            return new Route(className, action);


        }
    }
}
