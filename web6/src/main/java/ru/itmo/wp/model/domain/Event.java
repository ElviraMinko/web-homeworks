package ru.itmo.wp.model.domain;

import java.util.Date;

public class Event {
    private long id;
    private long userId;
    private Type type;
    private Date creationTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Type getType() {
        return type;
    }

    public String getTypeAsString(){
        switch(type){
            case ENTER:
                return "ENTER";
            case LOGOUT:
                return "LOGOUT";
            default:
                return "Unexpected Type Token";
        }
    }

    public void setType(Type type) {
        this.type = type;
    }
    public void setTypeFromString(String string) {
        switch(string){
            case "ENTER":
                this.type = Type.ENTER;
                break;
            case "LOGOUT":
                this.type = Type.LOGOUT;
                break;
        }
    }
    enum Type {ENTER, LOGOUT};
}
