package ru.itmo.wp.web.page;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.FrontServlet;
abstract class Page  {
    private final UserService userService = new UserService();
    private HttpServletRequest request;

    public void before(HttpServletRequest request, Map<String, Object> view) {
        this.request = request;
        putMessage(request, view);
        putUser(this.request, view);
        view.put("userCount", userService.findCount());
    }

    public void action(HttpServletRequest request, Map<String, Object> view){
    }

    public void after(HttpServletRequest request, Map<String, Object> view){
    }

    protected void putUser(HttpServletRequest request, Map<String, Object> view) {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            view.put("user", user);
        }
    }


    protected void putMessage(HttpServletRequest request, Map<String, Object> view) {
        String message = (String) request.getSession().getAttribute("message");
        if (!Strings.isNullOrEmpty(message)) {
            view.put("message", message);
            request.getSession().removeAttribute("message");
        }
    }

    protected void setMessage(String message) {
        request.getSession().setAttribute("message", message);
    }

    protected User getUser() {
        return (User) request.getSession().getAttribute("user");
    }

    protected void setUser(User user) {
        request.getSession().setAttribute("user", user);
    }
}
