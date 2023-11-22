package org.top.BuySellMVC.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="user_profile_t")
public class UserProfile {
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

    public UserProfile(){
        id = 0;
        name = "";
        email = "";
        registrationDate = LocalDate.now();
        rate = 0.0;
        wallet = 0.00;

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
