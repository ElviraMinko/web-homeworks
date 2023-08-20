package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.repository.EventRepository;
import ru.itmo.wp.model.repository.impl.EventRepositoryImpl;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused"})
public class LogoutPage extends Page {
    private final EventRepository eventRepository = new EventRepositoryImpl();

    @Override
    public void action(HttpServletRequest request, Map<String, Object> view) {
        super.action(request, view);
        User user = getUser();
        if (user != null) {
            request.getSession().removeAttribute("user");
            Event event = new Event();
            event.setTypeFromString("LOGOUT");
            event.setUserId(user.getId());
            eventRepository.save(event);
            request.removeAttribute("user");
            setMessage("Good bye. Hope to see you soon!");
        }
        throw new RedirectException("/index");
    }
}
