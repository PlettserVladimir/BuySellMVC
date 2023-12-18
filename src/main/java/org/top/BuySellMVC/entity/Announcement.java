package org.top.BuySellMVC.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;
//Сущность объявления
@Entity
@Table(name = "advertisement_t")
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title_f")
    private String title;
    @Column(name = "price_f")
    private Double price;
    @Column(name = "description_f")
    private String description;
    @Column(name = "data_creation_f")
    private LocalDate dataCreation;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @Lob
    @Column(name = "preview_image_f",columnDefinition = "MEDIUMBLOB")
    private String previewImageData;

    public Announcement(){
        id = 0;
        title = "";
        price = 0.0;
        description = "";
        category = null;
        profile = null;
    }

    public String getPreviewImageData() {
        return previewImageData;
    }

    public void setPreviewImageData(String previewImageData) {
        this.previewImageData = previewImageData;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDataCreation() {
        return dataCreation;
    }

    public void setDataCreation(LocalDate dataCreation) {
        this.dataCreation = dataCreation;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "Announcement{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", dataCreation=" + dataCreation +
                ", category=" + category +
                ", profile=" + profile +
                '}';
    }
}
