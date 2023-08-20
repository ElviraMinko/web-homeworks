package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;

import java.util.List;

public interface ArticleRepository {
    Article find(long id);

    Article findByTitle(String title);
//
//    User findByEmail(String email);
//
//    User findByLoginOrEmailAndPasswordSha(String type, String loginOrEmail, String passwordSha);

    List<Article> findAll();


        void save(Article article);

    List<Article> findAllById(long id);

    List<Article> findNotHidden();

    public void changeVisibility(long id, String str);


    }
