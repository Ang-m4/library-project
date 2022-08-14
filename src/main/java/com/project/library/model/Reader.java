package com.project.library.model;

import java.util.List;

public class Reader extends User{
    
    private boolean state;
    private List<Order> orders;
    private List<Subscription> subscriptions;

    public Reader() {
    }

    public Reader(long id, String name, String lastname, String password, boolean state, List<Order> orders, List<Subscription> subscriptions) {
        super(id, name, lastname, password);
        this.state = state;
        this.orders = orders;
        this.subscriptions = subscriptions;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
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
