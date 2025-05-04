package co.edu.unbosque.model.request;

import lombok.Data;

@Data
public class AchRelationshipRequest {
	private String account_owner_name;
	private String bank_account_type;
	private String bank_account_number;
	private String bank_routing_number;
	private String nickname;
}
