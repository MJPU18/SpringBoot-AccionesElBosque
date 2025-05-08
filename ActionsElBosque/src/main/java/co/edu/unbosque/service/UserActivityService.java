package co.edu.unbosque.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.model.UserActivity;
import co.edu.unbosque.repository.UserActivityRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserActivityService {

    @Autowired
    private UserActivityRepository userActivityRepository;

    public void logUserActivity(Long userId, String action, String details) {
        UserActivity activity = new UserActivity();
        activity.setUserId(userId);
        activity.setAction(action);
        activity.setDetails(details);
        activity.setTimestamp(Instant.now());

        userActivityRepository.save(activity);
    }

    public List<UserActivity> getActivityByUser(Long userId){
    	return userActivityRepository.findByUserId(userId);
    }
    
    public List<UserActivity> getAllActivities() {
        Iterable<UserActivity> iterable = userActivityRepository.findAll();
        List<UserActivity> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }

    
}
