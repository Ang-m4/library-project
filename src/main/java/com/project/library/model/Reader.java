package com.project.library.model;


import java.util.List;


public class Reader{

    private long id;
    private String name;
    private String lastname;
    private String password;
    private boolean blocked;
    private List<Order> orders;
    private List<Subscription> subscriptions;

    public Reader() {
    }

    public Reader(long id, String name, String lastname, String password, boolean state, List<Order> orders, List<Subscription> subscriptions) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.password = password;
        this.blocked = state;
        this.orders = orders;
        this.subscriptions = subscriptions;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    
    

}
