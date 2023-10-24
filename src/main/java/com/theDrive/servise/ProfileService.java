package com.theDrive.servise;

import com.theDrive.entity.Bookmark;
import com.theDrive.entity.Company;
import com.theDrive.entity.Post;
import com.theDrive.entity.User;
import com.theDrive.entity.sub.Body;
import com.theDrive.entity.sub.Brand;
import com.theDrive.entity.sub.Drive;
import com.theDrive.entity.sub.Engine;
import com.theDrive.repos.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepo userRepo;
    private final BrandRepo brandRepo;
    private final BodyRepo bodyRepo;
    private final EngineRepo engineRepo;
    private final DriveRepo driveRepo;
    private final SubscriptionService subscriptionService;
    private final CategoryServicesRepo categoryServicesRepo;
    private final CompanyRepo companyRepo;
    private final UseServicesService useServicesService;
    private final PostRepo postRepo;

    public HashMap<String, Object> getAllProfile(Long userId) {
        User user = userRepo.findOneById(userId);

        HashMap<String, Object> dataForProfile = dataPreparationProfileDriver();

        if (user.getRole().getCode().equals("DRIVER")) {
            dataForProfile.put("cars", user.getCars());
        } else if (user.getRole().getCode().equals("COMPANY")) {

            Company company = companyRepo.findOneByUserId(userId);

            dataForProfile.put("company", company);
            dataForProfile.put("useServices", useServicesService.getAllByCompanyId(company.getId()));
        } else {
            dataForProfile.put("posts", postRepo.findAllPostByUserId(userId));
        }

        dataForProfile.put("categories", categoryServicesRepo.findAll());

        return dataForProfile;
    }

    public HashMap<String, Object> getProfile(Long userId, Authentication auth) {

        User userProfile = userRepo.findOneById(userId);

        HashMap<String, Object> dataForProfile = dataPreparationProfileDriver();

        if (userProfile.getRole().getCode().equals("DRIVER")) {
            dataForProfile.put("role", userProfile.getRole().getCode());
            dataForProfile.put("cars", userProfile.getCars());
            if (auth != null)
                dataForProfile.put("subscriptionUser", subscriptionService.getSubscriptionOnUser(userId, auth));

        } else if (userProfile.getRole().getCode().equals("COMPANY")) {
            User user = auth != null ? userRepo.findOneById(((User) auth.getPrincipal()).getId()) : null;
            dataForProfile.put("cars", user != null ? user.getCars() : null);
            dataForProfile.put("company", companyRepo.findOneByUserId(userId));
            if (auth != null)
                dataForProfile.put("subscriptionUser", subscriptionService.getSubscriptionOnUser(userId, auth));
        } else if (userProfile.getRole().getCode().equals("AUTHOR")) {
            dataForProfile.put("posts", postRepo.findAllPostByUserId(userId));
            if (auth != null)
                dataForProfile.put("subscriptionUser", subscriptionService.getSubscriptionOnUser(userId, auth));
        }

        dataForProfile.put("user", userProfile);
        dataForProfile.put("role", userProfile.getRole().getCode());

        return dataForProfile;
    }

    public HashMap<String, Object> dataPreparationProfileDriver() {

        List<Brand> brands = brandRepo.findAll();
        List<Body> bodies = bodyRepo.findAll();
        List<Engine> engines = engineRepo.findAll();
        List<Drive> drives = driveRepo.findAll();

        HashMap<String, Object> data = new HashMap<>();

        data.put("brands" , brands);
        data.put("bodies" , bodies);
        data.put("engines" , engines);
        data.put("drives" , drives);

        return data;
    }
}
