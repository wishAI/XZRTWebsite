package com.wishai.xzrtw.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String password;
    private String permission;

    public User() {
    }
//
//    public User(
//            @JsonProperty("name") String name,
//            @JsonProperty("password") String password,
//            @JsonProperty("permission") String permission
//    ) {
//        this.name = name;
//        this.password = password;
//        this.permission = permission;
//    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
