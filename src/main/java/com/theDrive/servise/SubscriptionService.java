package com.theDrive.servise;

import com.theDrive.entity.Car;
import com.theDrive.entity.Subscription;
import com.theDrive.entity.User;
import com.theDrive.repos.CarRepo;
import com.theDrive.repos.SubscriptionRepo;
import com.theDrive.repos.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepo subscriptionRepo;
    private final UserRepo userRepo;
    private final CarRepo carRepo;

    public Integer getFollowers(Object obj) {
        if (Car.class == obj.getClass()) {
            Car car = (Car) obj;
            return subscriptionRepo.findAllFollowersCar(car.getId());
        } else {
            User user = (User) obj;
            return subscriptionRepo.findAllFollowersUser(user.getId());
        }
    }

    public Integer getCountFollowersCar(Long carId) {
        return subscriptionRepo.findAllFollowersCar(carId);
    }

    public Integer getFollowersUser(Long carId) {
        return subscriptionRepo.findAllFollowersUser(carId);
    }

    public void changeSubscriptionUser(Long userIdToSubscription, Long userId) {

        Subscription subscription = subscriptionRepo.findOneSubscriptionOnUser(userIdToSubscription, userId);

        if (subscription == null) {
            addSubscriptionUser(userIdToSubscription, userId);
        } else {
            subscriptionRepo.delete(subscription);
        }
    }

    public void changeSubscriptionCar(Long carIdToSubscription, Long userId) {

        Subscription subscription = subscriptionRepo.findOneSubscriptionOnCar(carIdToSubscription, userId);

        if (subscription == null) {
            addSubscriptionCar(carIdToSubscription, userId);
        } else {
            subscriptionRepo.delete(subscription);
        }
    }

    public void addSubscriptionUser(Long userIdToSubscription, Long userId) {

        User userToSubscription = userRepo.findOneById(userIdToSubscription);
        User user = userRepo.findOneById(userId);

        Subscription subscription = new Subscription(user, userToSubscription, null);

        subscriptionRepo.save(subscription);
    }

    public void addSubscriptionCar(Long userIdToSubscription, Long userId) {

        Car carToSubscription = carRepo.findOneById(userIdToSubscription);
        User user = userRepo.findOneById(userId);

        Subscription subscription = new Subscription(user, null, carToSubscription);

        subscriptionRepo.save(subscription);
    }

    public Boolean getSubscriptionOnUser(Long userIdToCheckSubscription, Authentication auth) {

        User user = (User) auth.getPrincipal();

        if (userIdToCheckSubscription.equals(user.getId()))
            return null;

        return subscriptionRepo.findOneSubscriptionOnUser(userIdToCheckSubscription, user.getId()) != null;
    }

    public Boolean getSubscriptionOnCar(Long carIdToCheckSubscription, Authentication auth) {

        User user = (User) auth.getPrincipal();
        Car car = carRepo.findOneById(carIdToCheckSubscription);

        if (car.getUser().getId().equals(user.getId()))
            return null;

        return subscriptionRepo.findOneSubscriptionOnCar(carIdToCheckSubscription, user.getId()) != null;
    }
}
