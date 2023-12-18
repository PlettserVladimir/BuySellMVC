package org.top.BuySellMVC.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_t")
public class User {
    @Id
    private Integer id;

    @Column(name = "name_f")
    private String userName;

    @Column(name = "password_f")
    private String password;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId
    private Profile profile;

    public User(){
        id = 0;
        userName = "";
        password = "";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String name) {
        this.userName = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
