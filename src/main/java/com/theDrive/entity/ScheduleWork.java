package com.theDrive.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "schedule_company")
@Entity
public class ScheduleWork {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shedule_company_generator")
    @SequenceGenerator(name = "shedule_company_generator", sequenceName = "shedule_company_id_seq", allocationSize = 1)
    private Long Id;

    private Boolean monday;

    private Boolean tuesday;

    private Boolean wednesday;

    private Boolean thursday;

    private Boolean friday;

    private Boolean saturday;

    private Boolean sunday;

    private Time timeStart;

    private Time timeEnd;
}
