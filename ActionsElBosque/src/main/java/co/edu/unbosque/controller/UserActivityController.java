package co.edu.unbosque.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.model.UserActivity;
import co.edu.unbosque.service.UserActivityService;

@RestController
@RequestMapping("/logs")
@CrossOrigin(origins = { "http://localhost:8085", "http://localhost:4200", "*" })
public class UserActivityController {

	@Autowired
	private UserActivityService userActServ;

	@GetMapping("/byuser/{userId}")
	public ResponseEntity<List<UserActivity>> logsByUser(@PathVariable Long userId) {
		List<UserActivity> userAct = userActServ.getActivityByUser(userId);
		return userAct.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
				: new ResponseEntity<>(userAct, HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<List<UserActivity>> getAllLogs() {
		List<UserActivity> userAct = userActServ.getAllActivities();
		return userAct.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(userAct, HttpStatus.OK);
	}
}
