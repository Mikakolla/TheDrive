package com.theDrive.entity;

import com.theDrive.entity.sub.UseServicesStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UseServices {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "use_services_generator")
    @SequenceGenerator(name = "use_services_generator", sequenceName = "use_services_id_seq", allocationSize = 1)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "services_id", referencedColumnName = "id")
    private Services services;

    private Date dateStart;

    private Date dateFinish;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private Car car;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private UseServicesStatus status;

    public UseServices(Services services, Date dateStart, Date dateFinish, Car car, User user, UseServicesStatus status) {
        this.services = services;
        this.dateStart = dateStart;
        this.dateFinish = dateFinish;
        this.car = car;
        this.user = user;
        this.status = status;
    }
}
