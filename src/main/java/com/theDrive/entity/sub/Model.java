package com.theDrive.entity.sub;

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
@Table(name = "sub_car_model")
public class Model {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name="brand_id")
    private Brand brand;

    private String code;

    private String description;
}
