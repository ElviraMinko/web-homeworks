package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.EventRepository;
import ru.itmo.wp.model.repository.impl.EventRepositoryImpl;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused"})
public class EnterPage extends Page {
    private final UserService userService = new UserService();
    private final EventRepository eventRepository = new EventRepositoryImpl();

    private void enter(HttpServletRequest request, Map<String, Object> view) throws ValidationException {

        String password = request.getParameter("password");
        String loginOrEmail = request.getParameter("loginOrEmail");
        String type = null;
        userService.validateEnter(loginOrEmail, password);
        User user = userService.findByLoginOrEmailAndPassword(loginOrEmail, password);

        setUser(user);
       // request.getSession().setAttribute("user", user);
        setMessage("Hello, " + user.getLogin());
        Event event = new Event();
        event.setTypeFromString("ENTER");
        event.setUserId(user.getId());
        eventRepository.save(event);
        throw new RedirectException("/index");
    }
}
