package org.top.BuySellMVC.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "product_t")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name_f",nullable = false)
    private String name;

    @Column(name = "description_f")
    private String description;

    @Column(name = "price_f",nullable = false)
    private Double price;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    public Product(){
        id = 0;
        name = "";
        description = "";
        price = 0.0;

    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    @Override
    public String toString(){
        return "ИД-"+id+" Название-"+name+" Описание-"+description+" Цена-"+price;
    }
}
