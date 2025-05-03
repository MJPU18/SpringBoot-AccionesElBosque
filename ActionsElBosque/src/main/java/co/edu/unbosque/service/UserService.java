package co.edu.unbosque.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.model.User;
import co.edu.unbosque.repository.UserRepository;
import jakarta.annotation.PostConstruct;

@Service
public class UserService implements CRUDOperation<User>{
	
	@Autowired
	private UserRepository userRepo;
	private HashSet<String> emails;
	
	public UserService() {}
	
	@PostConstruct
	public void init() {
		emails=new HashSet<String>();
		for(User user:userRepo.findAll()) {
			emails.add(user.getEmail());
		}
	}

	@Override
	public void create(User data) {
		userRepo.save(data);
	}
	
	@Override
	public List<User> getAll() {
		return userRepo.findAll();
	}

	@Override
	public void deleteById(Long id) {
		Optional<User> found= userRepo.findById(id);
		if(found.isPresent()) {
			userRepo.delete(found.get());
		}
	}

	@Override
	public void updateById(Long id, User newData) {
		Optional<User> found = userRepo.findById(id);
		if(found.isPresent()) {
			User temp= found.get();
			userRepo.save(temp);
		}
	}

	@Override
	public boolean exist(Long id) {
		return userRepo.existsById(id);
	}
	
	public User getById(Long id) {
		return userRepo.findById(id).get();
	}
	
	public boolean verifyAccount(String email, String password) {
		for(User user:userRepo.findAll()) {
			if(email.equals(user.getEmail()) && password.equals(user.getPassword())) {
				return true;
			}
		}
		return false;
	}
	
	public String validateUser(User user) {
		StringBuilder sb=new StringBuilder("");
		if (user.getFirstName() == null || user.getFirstName().trim().isEmpty()) {
            sb.append("\nNombre vacio.");
        }
		else if (user.getFirstName().length() > 175) {
            sb.append("\nNombre demasiado largo no debe exceder los 175 caracteres.");
        }
        if (user.getLastName() == null || user.getLastName().trim().isEmpty()) {
            sb.append("\nApellido vacio.");
        }
        else if (user.getLastName().length() > 175) {
           sb.append("\nApellido demasiado largo no debe exceder los 175 caracteres.");
        }
 
        if(user.getEmail()!=null && !user.getEmail().matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
        	sb.append("\nEmail invalido.");
        }
        else if(emails.contains(user.getEmail())) {
        	sb.append("\nEmail ya registrado.");
        }
        return sb.toString();
    }

}
