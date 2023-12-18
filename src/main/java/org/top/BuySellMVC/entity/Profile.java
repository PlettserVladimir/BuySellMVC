package org.top.BuySellMVC.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

//Содержит поля для хранения профиля пользователя
@Entity
@Table(name="profile_t")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name_f", nullable = false)
    private String name;

    @Column(name = "email",nullable = false)
    private String email;

    @Column(name = "registration_date_t",nullable = false)
    private LocalDate registrationDate;

    @Column(name = "rate_t",nullable = false)
    private Double rate;

    @Column(name = "wallet_t",nullable = false)
    private Double wallet;

    @OneToMany(mappedBy = "profile")
    private Set<Announcement> announcements;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private User user;

    public Profile(){
        id = 0;
        name = "";
        email = "";
        registrationDate = LocalDate.now();
        rate = 0.0;
        wallet = 0.00;

    }

    public Set<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(Set<Announcement> announcements) {
        this.announcements = announcements;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Announcement> getAdvertisements() {
        return announcements;
    }

    public void setAdvertisements(Set<Announcement> announcements) {
        this.announcements = announcements;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getWallet() {
        return wallet;
    }

    public void setWallet(Double wallet) {
        this.wallet = wallet;
    }

    @Override
    public String toString() {
        return "ИД-"+id+"-ИМЯ-"+ name+"-EMAIL"+email+"-РЕЙТИНГ-"+rate+"-ДАТА РЕГИСТРАЦИИ-"
                +registrationDate+"-СУММА НА СЧЁТЕ-"+wallet;
    }
}
