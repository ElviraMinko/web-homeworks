package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/** @noinspection unused*/
public class MyArticlesPage {
    ArticleService articleService = new ArticleService();
    private void action(HttpServletRequest request, Map<String, Object> view) {
        if(request.getSession().getAttribute("user") == null){
            throw new RedirectException("/index");
        }
        findAll(request, view);
    }

    private void findAll(HttpServletRequest request, Map<String, Object> view) {
        view.put("myArticles", articleService.findAllById(((User) (request.getSession().getAttribute("user"))).getId()));
    }

    private void changeVisibility(HttpServletRequest request, Map<String, Object> view) {
        long id = Long.parseLong(request.getParameter("id"));
//        String hidden = request.getParameter("button");
//        String action = request.getParameter("action");
        String str = request.getParameter("button");
        articleService.changeVisibility(id, str);

    }
}
