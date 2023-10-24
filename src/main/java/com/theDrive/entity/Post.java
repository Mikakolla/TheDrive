package com.theDrive.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_generator")
    @SequenceGenerator(name = "post_generator", sequenceName = "post_id_seq", allocationSize = 1)
    private Long id;

    private String title;

    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;

    private Date dateCreate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private Car car;

    private String uuid;

    private String imageName;

    private Integer price;

    private Integer mileage;

    private Boolean display;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_service_id", referencedColumnName = "id")
    private CategoryServices category;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Like> like;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "services_id", referencedColumnName = "id")
    private Services services;

    public Post(String title, String text, User user, Date dateCreate, Car car, String uuid, String imageName, Integer price, Integer mileage, Boolean display) {
        this.title = title;
        this.text = text;
        this.author = author;
        this.dateCreate = dateCreate;
        this.car = car;
        this.uuid = uuid;
        this.imageName = imageName;
        this.price = price;
        this.mileage = mileage;
        this.display = display;
    }

    public Post(String title, String text, User author, Date dateCreate, Car car, String uuid, String imageName, Integer price, Integer mileage, Boolean display, Company company, Services services, CategoryServices categoryServices) {
        this.title = title;
        this.text = text;
        this.author = author;
        this.dateCreate = dateCreate;
        this.car = car;
        this.uuid = uuid;
        this.imageName = imageName;
        this.price = price;
        this.mileage = mileage;
        this.display = display;
        this.company = company;
        this.services = services;
        this.category = categoryServices;
    }
}
