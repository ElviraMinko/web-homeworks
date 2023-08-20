package ru.itmo.web.hw4.model;

public class Post {
    private final Long id;
    private final String title;
    private final String text;
    private final Long userId;

    public Post(long id, String title, String text, Long user_id) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.userId = user_id;

    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public Long getUserId() {
        return userId;
    }
}
