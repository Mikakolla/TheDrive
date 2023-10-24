package com.theDrive.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subscription")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subscription_generator")
    @SequenceGenerator(name = "subscription_generator", sequenceName = "subscription_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id_follow", referencedColumnName = "id")
    private User userFollow;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id_follow", referencedColumnName = "id")
    private Car carFollow;

    public Subscription(User user, User userFollow, Car carFollow) {
        this.user = user;
        this.userFollow = userFollow;
        this.carFollow = carFollow;
    }
}
