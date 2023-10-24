package com.theDrive.servise;

import com.theDrive.entity.Car;
import com.theDrive.entity.Subscription;
import com.theDrive.entity.User;
import com.theDrive.repos.CarRepo;
import com.theDrive.repos.SubscriptionRepo;
import com.theDrive.repos.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import static org.mockito.Mockito.spy;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceTest {

    @InjectMocks
    private SubscriptionService subscriptionService;

    @Mock
    private SubscriptionRepo mockSubscriptionRepo;

    @Mock
    private CarRepo mockCarRepo;

    @Mock
    private UserRepo mockUserRepo;

    @Mock
    private Authentication auth;

    @Test
    void getFollowers_car() {

        //given
        Car car = new Car();
        car.setId(1L);
        Integer countFollowers = 5;
        Mockito.when(mockSubscriptionRepo.findAllFollowersCar(car.getId())).thenReturn(countFollowers);

        //when
        Integer followersResult = subscriptionService.getFollowers(car);

        //then
        Assertions.assertEquals(countFollowers, followersResult);
    }

    @Test
    void getFollowers_user() {

        //given
        User user = new User();
        user.setId(1L);
        Integer countFollowers = 5;
        Mockito.when(mockSubscriptionRepo.findAllFollowersUser(user.getId())).thenReturn(countFollowers);

        //when
        Integer followersResult = subscriptionService.getFollowers(user);

        //then
        Assertions.assertEquals(countFollowers, followersResult);
    }

    @Test
    void getCountFollowersCar() {

        //given
        Long carId = 1L;
        Integer countFollowers = 5;
        Mockito.when(mockSubscriptionRepo.findAllFollowersCar(carId)).thenReturn(countFollowers);

        //when
        Integer followersResult = subscriptionService.getCountFollowersCar(carId);

        //then
        Assertions.assertEquals(countFollowers, followersResult);
    }

    @Test
    void getFollowersUser() {
        //given
        Long userId = 1L;
        Integer countFollowers = 5;
        Mockito.when(mockSubscriptionRepo.findAllFollowersUser(userId)).thenReturn(countFollowers);

        //when
        Integer followersResult = subscriptionService.getFollowersUser(userId);

        //then
        Assertions.assertEquals(countFollowers, followersResult);
    }

    @Test
    void changeSubscriptionUser_subscription_null() {

        //given
        Long userIdToSubscription = 1L;
        Long authUserId = 2L;
        Mockito.when(mockSubscriptionRepo.findOneSubscriptionOnUser(userIdToSubscription, authUserId)).thenReturn(null);

        SubscriptionService subscriptionService = spy(new SubscriptionService(mockSubscriptionRepo, mockUserRepo, mockCarRepo));
        Mockito.doNothing().when(subscriptionService).addSubscriptionUser(userIdToSubscription, authUserId);

        ArgumentCaptor<Long> captorUserId = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Long> captorAuthUserId = ArgumentCaptor.forClass(Long.class);

        //when
        subscriptionService.changeSubscriptionUser(userIdToSubscription, authUserId);

        Mockito.verify(subscriptionService).addSubscriptionUser(captorUserId.capture(), captorAuthUserId.capture());

        //then
        Long userIdResult = captorUserId.getValue();
        Long authUserResult = captorAuthUserId.getValue();

        Assertions.assertEquals(userIdToSubscription, userIdResult);
        Assertions.assertEquals(authUserId, authUserResult);
    }

    @Test
    void changeSubscriptionUser_subscription_not_null() {

        //given
        Long userIdToSubscription = 1L;
        Long authUserId = 2L;
        Subscription subscription = new Subscription();
        Mockito.when(mockSubscriptionRepo.findOneSubscriptionOnUser(userIdToSubscription, authUserId)).thenReturn(subscription);

        ArgumentCaptor<Subscription> subscriptionCaptor = ArgumentCaptor.forClass(Subscription.class);

        //when
        subscriptionService.changeSubscriptionUser(userIdToSubscription, authUserId);

        Mockito.verify(mockSubscriptionRepo).delete(subscriptionCaptor.capture());

        //then
        Subscription subscriptionResult = subscriptionCaptor.getValue();

        Assertions.assertEquals(subscription, subscriptionResult);
    }

    @Test
    void changeSubscriptionCar_subscription_null() {

        //given
        Long carIdToSubscription = 1L;
        Long authUserId = 2L;
        Mockito.when(mockSubscriptionRepo.findOneSubscriptionOnCar(carIdToSubscription, authUserId)).thenReturn(null);

        SubscriptionService subscriptionService = spy(new SubscriptionService(mockSubscriptionRepo, mockUserRepo, mockCarRepo));
        Mockito.doNothing().when(subscriptionService).addSubscriptionCar(carIdToSubscription, authUserId);

        ArgumentCaptor<Long> captorCarId = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Long> captorAuthUserId = ArgumentCaptor.forClass(Long.class);

        //when
        subscriptionService.changeSubscriptionCar(carIdToSubscription, authUserId);

        Mockito.verify(subscriptionService).addSubscriptionCar(captorCarId.capture(), captorAuthUserId.capture());

        //then
        Long carIdResult = captorCarId.getValue();
        Long authUserResult = captorAuthUserId.getValue();

        Assertions.assertEquals(carIdToSubscription, carIdResult);
        Assertions.assertEquals(authUserId, authUserResult);

    }

    @Test
    void changeSubscriptionCar_not_null() {

        //given
        Long carIdToSubscription = 1L;
        Long authUserId = 2L;
        Subscription subscription = new Subscription();
        Mockito.when(mockSubscriptionRepo.findOneSubscriptionOnCar(carIdToSubscription, authUserId)).thenReturn(subscription);

        ArgumentCaptor<Subscription> subscriptionCaptor = ArgumentCaptor.forClass(Subscription.class);

        //when
        subscriptionService.changeSubscriptionCar(carIdToSubscription, authUserId);

        Mockito.verify(mockSubscriptionRepo).delete(subscriptionCaptor.capture());

        //then
        Subscription subscriptionResult = subscriptionCaptor.getValue();

        Assertions.assertEquals(subscription, subscriptionResult);

    }

    @Test
    void addSubscriptionUser() {

        //given
        Long userId = 1L;
        User user = new User();
        user.setId(1L);
        Mockito.when(mockUserRepo.findOneById(userId)).thenReturn(user);

        Long authUserId = 2L;
        User authUser = new User();
        authUser.setId(2L);
        Mockito.when(mockUserRepo.findOneById(authUserId)).thenReturn(authUser);

        ArgumentCaptor<Subscription> subscriptionCaptor = ArgumentCaptor.forClass(Subscription.class);

        Subscription subscription = new Subscription(authUser, user, null);
        //when
        subscriptionService.addSubscriptionUser(userId, authUserId);

        Mockito.verify(mockSubscriptionRepo).save(subscriptionCaptor.capture());

        //then
        Subscription result = subscriptionCaptor.getValue();

        Assertions.assertEquals(subscription.getUser().getId(), result.getUser().getId());
    }

    @Test
    void addSubscriptionCar() {
        //given
        Long carId = 1L;
        Car car = new Car();
        car.setId(1L);
        Mockito.when(mockCarRepo.findOneById(carId)).thenReturn(car);

        Long authUserId = 2L;
        User authUser = new User();
        authUser.setId(2L);
        Mockito.when(mockUserRepo.findOneById(authUserId)).thenReturn(authUser);

        ArgumentCaptor<Subscription> subscriptionCaptor = ArgumentCaptor.forClass(Subscription.class);

        Subscription subscription = new Subscription(authUser, null, car);
        //when
        subscriptionService.addSubscriptionCar(carId, authUserId);

        Mockito.verify(mockSubscriptionRepo).save(subscriptionCaptor.capture());

        //then
        Subscription result = subscriptionCaptor.getValue();

        Assertions.assertEquals(subscription.getCarFollow().getId(), result.getCarFollow().getId());
    }

    @Test
    void getSubscriptionOnUser_null() {

        //given
        Long userIdToCheckSubscription = 1L;
        User authUser = new User();
        authUser.setId(1L);
        Mockito.when(auth.getPrincipal()).thenReturn(authUser);

        //when
        Boolean result = subscriptionService.getSubscriptionOnUser(userIdToCheckSubscription, auth);

        //then
        Assertions.assertNull(result);

    }

    @Test
    void getSubscriptionOnUser_true() {

        //given
        Long userIdToCheckSubscription = 1L;
        User authUser = new User();
        authUser.setId(2L);
        Mockito.when(auth.getPrincipal()).thenReturn(authUser);

        Subscription subscription = new Subscription();
        Mockito.when(mockSubscriptionRepo.findOneSubscriptionOnUser(userIdToCheckSubscription, authUser.getId())).thenReturn(subscription);

        //when
        Boolean result = subscriptionService.getSubscriptionOnUser(userIdToCheckSubscription, auth);

        //then
        Assertions.assertTrue(result);

    }

    @Test
    void getSubscriptionOnUser_false() {

        //given
        Long userIdToCheckSubscription = 1L;
        User authUser = new User();
        authUser.setId(2L);
        Mockito.when(auth.getPrincipal()).thenReturn(authUser);

        Mockito.when(mockSubscriptionRepo.findOneSubscriptionOnUser(userIdToCheckSubscription, authUser.getId())).thenReturn(null);

        //when
        Boolean result = subscriptionService.getSubscriptionOnUser(userIdToCheckSubscription, auth);

        //then
        Assertions.assertFalse(result);

    }

    @Test
    void getSubscriptionOnCar_null() {

        //given
        User authUser = new User();
        authUser.setId(1L);
        Mockito.when(auth.getPrincipal()).thenReturn(authUser);

        Long carIdToCheckSubscription = 1L;
        Car car = new Car();
        car.setUser(authUser);
        Mockito.when(mockCarRepo.findOneById(carIdToCheckSubscription)).thenReturn(car);

        //when
        Boolean result = subscriptionService.getSubscriptionOnCar(carIdToCheckSubscription, auth);

        //then
        Assertions.assertNull(result);
    }

    @Test
    void getSubscriptionOnCar_true() {

        //given
        User authUser = new User();
        authUser.setId(1L);
        Mockito.when(auth.getPrincipal()).thenReturn(authUser);

        Long carIdToCheckSubscription = 1L;
        User user = new User();
        user.setId(2L);
        Car car = new Car();
        car.setUser(user);
        Mockito.when(mockCarRepo.findOneById(carIdToCheckSubscription)).thenReturn(car);

        Subscription subscription = new Subscription();
        Mockito.when(mockSubscriptionRepo.findOneSubscriptionOnCar(carIdToCheckSubscription, authUser.getId())).thenReturn(subscription);

        //when
        Boolean result = subscriptionService.getSubscriptionOnCar(carIdToCheckSubscription, auth);

        //then
        Assertions.assertTrue(result);
    }

    @Test
    void getSubscriptionOnCar_false() {

        //given
        User authUser = new User();
        authUser.setId(1L);
        Mockito.when(auth.getPrincipal()).thenReturn(authUser);

        Long carIdToCheckSubscription = 1L;
        User user = new User();
        user.setId(2L);
        Car car = new Car();
        car.setUser(user);
        Mockito.when(mockCarRepo.findOneById(carIdToCheckSubscription)).thenReturn(car);

        Mockito.when(mockSubscriptionRepo.findOneSubscriptionOnCar(carIdToCheckSubscription, authUser.getId())).thenReturn(null);

        //when
        Boolean result = subscriptionService.getSubscriptionOnCar(carIdToCheckSubscription, auth);

        //then
        Assertions.assertFalse(result);
    }
}