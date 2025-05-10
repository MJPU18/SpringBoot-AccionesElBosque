package co.edu.unbosque.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.model.User;
import co.edu.unbosque.repository.UserRepository;

@Service
public class UserService implements CRUDOperation<User>{
	
	@Autowired
	private UserRepository userRepo;
	
	public UserService() {}
	
	

	@Override
	public User create(User data) {
		return userRepo.save(data);
		
	}

	@Override
	public List<User> getAll() {
		return userRepo.findAll();
	}

	@Override
	public User deleteById(Long id) {
		Optional<User> found= userRepo.findById(id);
		if(found.isPresent()) {
			userRepo.delete(found.get());
			return found.get();
		}
		return null;
	}

	@Override
	public User updateById(Long id, User newData) {
		Optional<User> found = userRepo.findById(id);
		if(found.isPresent()) {
			User temp= found.get();
			temp.setFirstName(newData.getFirstName());
			temp.setLastName(newData.getLastName());
			temp.setPassword(newData.getPassword());
			temp.setPhone(newData.getPhone());
			return userRepo.save(temp);
		}
		return null;
	}
	
	public String getAlpacaUserIdByEmail(String email) {
	    Optional<User> userOptional = userRepo.findByEmail(email);
	    if (userOptional.isPresent()) {
	        return userOptional.get().getAlpacaUserId();
	    }
	    return null;
	}
	
	public User getUserByAlpacaId(String alpacaUserId) {
		Optional<User> userOptional = userRepo.findByAlpacaUserId(alpacaUserId);
	    if (userOptional.isPresent()) {
	        return userOptional.get();
	    }
	    return null;
	}
	
	public User getUserByEmail(String email) {
		Optional<User> userOptional = userRepo.findByEmail(email);
		if(userOptional.isPresent()) {
			return userOptional.get();
		}
		return null;
	}

	@Override
	public boolean exist(Long id) {
		return userRepo.existsById(id);
	}
	
	public User getById(Long id) {
		return userRepo.findById(id).get();
	}
	
	public User verifyAccount(String email, String password) {
		for(User user:userRepo.findAll()) {
			if(email.equals(user.getEmail()) && password.equals(user.getPassword())) {
				return user;
			}
		}
		return null;
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
        return sb.toString();
    }

}