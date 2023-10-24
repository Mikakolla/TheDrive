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
@Table(name = "sub_car_generation")
public class Generation {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name="model_id")
    private Model model;

    private String code;

    private String description;

    private Integer dateStart;

    private Integer dateFinish;
}
