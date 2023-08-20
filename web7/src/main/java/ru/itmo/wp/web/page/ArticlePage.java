package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** @noinspection unused*/
public class ArticlePage {
    private final ArticleService articleService = new ArticleService();
    private void action(HttpServletRequest request, Map<String, Object> view) {
        if(request.getSession().getAttribute("user") == null){
            throw new RedirectException("/index");
        }
    }
    private void create(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        String title = request.getParameter("title").trim();
        String text = request.getParameter("text").trim();
        User user = (User)request.getSession().getAttribute("user");
        Article article = new Article();
        article.setTitle(title);
        article.setText(text);
        article.setHidden(true);
        article.setUserId(user.getId());
        articleService.validateArticle(article);
        articleService.saveArticle(article);
        request.getSession().setAttribute("message", "You create article, " + user.getLogin());

        throw new RedirectException("/index");
    }
    private void findAll(HttpServletRequest request, Map<String, Object> view) {
        view.put("articles", articleService.findAll());
    }

}
