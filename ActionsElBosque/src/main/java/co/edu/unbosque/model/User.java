package co.edu.unbosque.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_aeb")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	@Column(unique = true)
	private String cardId;
	private String alpacaUserId;
	private String firstName;
	private String lastName;
	
	@Column(unique = true)
	private String email;
	private String password;
	@Column(unique = true)
	private String phone;
	private boolean isAdministrator;
	

}
