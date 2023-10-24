package com.theDrive.entity;

import com.theDrive.entity.sub.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_generator")
    @SequenceGenerator(name = "car_generator", sequenceName = "cars_id_seq", allocationSize = 1)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    private Brand brand;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id", referencedColumnName = "id")
    private Model model;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "generation_id", referencedColumnName = "id")
    private Generation generation;

    private Integer yearCreate;

    private Double engineSize;

    private Boolean transmission;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "body_id", referencedColumnName = "id")
    private Body body;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "engine_id", referencedColumnName = "id")
    private Engine engine;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drive_id", referencedColumnName = "id")
    private Drive drive;

    private String uuid;

    private String imageName;

    private String name;

    @OneToMany(mappedBy = "carFollow", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Subscription> subscriptionFollowers;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Post> posts;

    public Car(Brand brand, Model model, Generation generation, Integer yearCreate, Double engineSize, Boolean transmission, User user, Body body, Engine engine, Drive drive, String imageName, String name) {
        this.brand = brand;
        this.model = model;
        this.generation = generation;
        this.yearCreate = yearCreate;
        this.engineSize = engineSize;
        this.transmission = transmission;
        this.user = user;
        this.body = body;
        this.engine = engine;
        this.drive = drive;
        this.imageName = imageName;
        this.name = name;
    }

    public List<Subscription> getFollowers() {
        return this.subscriptionFollowers;
    }

    public Integer getCountPost() {
        return this.posts.size();
    }
}
