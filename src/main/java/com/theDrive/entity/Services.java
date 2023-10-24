package com.theDrive.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Services {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "services_generator")
    @SequenceGenerator(name = "services_generator", sequenceName = "services_id_seq", allocationSize = 1)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name="company_id", nullable=false)
    private Company company;

    private Integer timeSpend;

    private Double price;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private CategoryServices category;

    public Services(String name, Company company, Integer timeSpend, Double price, CategoryServices category) {
        this.name = name;
        this.company = company;
        this.timeSpend = timeSpend;
        this.price = price;
        this.category = category;
    }
}
