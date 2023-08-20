package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** @noinspection unused*/
public class IndexPage {
    private final ArticleService articleService = new ArticleService();
    private final UserService userService = new UserService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        putMessage(request, view);
        if (request.getRequestURI().equals("/")) {
            throw new RedirectException("/index");
        }
    }

    private void putMessage(HttpServletRequest request, Map<String, Object> view) {
        String message = (String) request.getSession().getAttribute("message");
        if (!Strings.isNullOrEmpty(message)) {
            view.put("message", message);
            request.getSession().removeAttribute("message");
        }
    }

    private void findAll(HttpServletRequest request, Map<String, Object> view) {
        view.put("articles", articleService.findNotHidden());
        view.put("users", userService.findAll());
    }

    private void findArticle(HttpServletRequest request, Map<String, Object> view) {
        view.put("article",
                articleService.find(Long.parseLong(request.getParameter("userId"))));
    }
}
