package com.project.library.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue
    @NotBlank
    @Column(name = "user_id")
    private Long id;
    @NotBlank
    @Column(name = "user_name")
    private String name;
    @NotBlank
    @Column(name = "user_lastname")
    private String lastname;
    @NotBlank
    @Column(name = "user_password")
    private String password;

    public User() {
    }
    public User(long id, String name, String lastname, String password) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.password = password;
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

}
