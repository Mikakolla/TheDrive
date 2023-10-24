package com.theDrive.repos;

import com.theDrive.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SubscriptionRepo extends JpaRepository<Subscription, Long> {

    @Query("select count(s) from Subscription s where s.userFollow.id = :userId")
    Integer findAllFollowersUser(Long userId);

    @Query("select count(s) from Subscription s where s.carFollow.id = :carId")
    Integer findAllFollowersCar(Long carId);

    @Query("select s from Subscription s where s.user.id = :userId and s.userFollow.id = :userIdToCheckSubscription")
    Subscription findOneSubscriptionOnUser(Long userIdToCheckSubscription, Long userId);

    @Query("select s from Subscription s where s.user.id = :userId and s.carFollow.id = :carIdToCheckSubscription")
    Subscription findOneSubscriptionOnCar(Long carIdToCheckSubscription, Long userId);

}
