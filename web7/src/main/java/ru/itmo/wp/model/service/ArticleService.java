package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import com.google.common.hash.Hashing;
import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.ArticleRepository;
import ru.itmo.wp.model.repository.impl.ArticleRepositoryImpl;

import java.util.List;

/** @noinspection UnstableApiUsage*/
public class ArticleService{
    private final ArticleRepository articleRepository = new ArticleRepositoryImpl();

    public void validateArticle(Article article) throws ValidationException {
        if (articleRepository.findByTitle(article.getTitle()) != null) {
            throw new ValidationException("Article with the same title was found");
        }

        if (Strings.isNullOrEmpty(article.getTitle())) {
            throw new ValidationException("Title can't be empty");
        }

        if (Strings.isNullOrEmpty(article.getText())) {
            throw new ValidationException("Text can't be empty");
        }

        if (article.getTitle().length() > 20) {
            throw new ValidationException("Title is too long");
        }

        if (article.getTitle().length() < 4) {
            throw new ValidationException("Title is too short");
        }

        if (article.getText().length() > 3000) {
            throw new ValidationException("Text is too long");
        }

        if (article.getText().length() < 4) {
            throw new ValidationException("Text is too short");
        }
    }

    public Article find(long id) {
        return articleRepository.find(id);
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public List<Article> findNotHidden() {
        return articleRepository.findNotHidden();
    }

    public void saveArticle(Article article) {
        articleRepository.save(article);
    }

    public List<Article> findAllById(long id) {
        return articleRepository.findAllById(id);
    }

    public void changeVisibility(long id, String str) {
        articleRepository.changeVisibility(id,str);
    }
}

