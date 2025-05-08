package co.edu.unbosque.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import co.edu.unbosque.model.UserActivity;

public interface UserActivityRepository extends ElasticsearchRepository<UserActivity, String> {
	
	List<UserActivity> findByUserId(Long userId);
	
}