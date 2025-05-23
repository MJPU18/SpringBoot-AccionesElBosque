package co.edu.unbosque.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
	
	private String cardId;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;

}
