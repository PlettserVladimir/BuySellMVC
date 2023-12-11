package org.top.BuySellMVC.entity;

import jakarta.persistence.*;

import java.util.Set;
//Справочник, содержащий список категорий для объявления
@Entity
@Table(name = "category_t")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "category_f",nullable = false)
    private String category;

    @OneToMany(mappedBy = "category")
    private Set<Announcement> announcements;

    public Category(){
        id =0;
        category = "";
    }

    public Set<Announcement> getAdvertisements() {
        return announcements;
    }

    public void setAdvertisements(Set<Announcement> announcements) {
        this.announcements = announcements;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    @Override
    public String toString(){
        return "ИД-"+id+" Категория-"+category;
    }
}
