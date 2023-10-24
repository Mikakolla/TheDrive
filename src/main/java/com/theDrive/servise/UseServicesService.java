package com.theDrive.servise;

import com.theDrive.entity.Car;
import com.theDrive.entity.Company;
import com.theDrive.entity.Services;
import com.theDrive.entity.UseServices;
import com.theDrive.entity.sub.UseServicesStatus;
import com.theDrive.repos.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UseServicesService {

    private final UseServicesRepo useServicesRepo;
    private final UseServicesStatusRepo useServicesStatusRepo;
    private final CompanyRepo companyRepo;
    private final ServicesRepo servicesRepo;
    private final CarRepo carRepo;

    public List<Integer> getFreeTime(String date, Long serviceId) {

        Services selectedService = servicesRepo.findOneById(serviceId);

        List<UseServices> useServices = getAllByDate(date);

        Company company = companyRepo.findOneById(selectedService.getCompany().getId());

        Time timeStart = company.getScheduleWork().getTimeStart();
        Time timeEnd = company.getScheduleWork().getTimeEnd();

        List<Integer> freeTime = new ArrayList<>();
        Set<Integer> blockedTime = new HashSet<>();

        for (int hour = timeStart.getHours(); hour < timeEnd.getHours(); hour++) {
            freeTime.add(hour);
        }

        int i = 0;

        for (UseServices services : useServices) {
            for (int k = services.getDateStart().getHours(); k < services.getDateFinish().getHours(); k++) {
                blockedTime.add(k);
            }
            if (useServices.get(i) != null && useServices.get(i).getDateStart().getHours() - services.getDateFinish().getHours() < selectedService.getTimeSpend()) {
                for (int k = services.getDateFinish().getHours(); k <= useServices.get(i).getDateStart().getHours(); k++) {
                    blockedTime.add(k);
                }
            }
            if(services.getDateFinish().getHours() + selectedService.getTimeSpend() > timeEnd.getHours()) {
                for (int k = services.getDateFinish().getHours(); k <= timeEnd.getHours(); k++) {
                    blockedTime.add(k);
                }
            }
            for (Integer freeHour : freeTime) {
                if (services.getDateStart().getHours() > freeHour && services.getDateStart().getHours() - freeHour < selectedService.getTimeSpend()) {
                    blockedTime.add(freeHour);
                }
            }
            i++;
        }

        freeTime.removeAll(blockedTime);

        return freeTime;

    }

    public String add(Long serviceId, String date, String time, Long carId) throws ParseException {

        Services service = servicesRepo.findOneById(serviceId);

        Date dateStart = new SimpleDateFormat("dd.MM.yyyy HH:mm").parse(date + " " + time);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateStart);
        calendar.add(Calendar.HOUR_OF_DAY, service.getTimeSpend());

        Date dateFinish = calendar.getTime();

        Car car = carRepo.findOneById(carId);

        UseServicesStatus status = useServicesStatusRepo.findOneByCode("AWAIT");

        UseServices useServices = new UseServices(service, dateStart, dateFinish, car, car.getUser(), status);

        useServicesRepo.save(useServices);

        return (service.getCompany().getUser().getId()).toString();

    }

    public List<UseServices> getAllByDate(String date) {
        return useServicesRepo.findAllByDate(date);
    }

    public List<UseServices> getAllByCompanyId(Long companyId) { return useServicesRepo.findAllByCompanyId(companyId); }

    public List<UseServices> getAllByCar(Long carId) { return useServicesRepo.findAllByCarId(carId); }

    public void changeStatus(Long useServiceId, String statusStr) {

        UseServices useServices = useServicesRepo.findOneById(useServiceId);
        UseServicesStatus status = useServicesStatusRepo.findOneByCode(statusStr);

        useServices.setStatus(status);

        useServicesRepo.save(useServices);
    }


}
