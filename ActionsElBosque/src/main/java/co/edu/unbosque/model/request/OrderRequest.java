package co.edu.unbosque.model.request;

import lombok.Data;

@Data
public class OrderRequest {
	private String symbol;
	private String qty;
	private String side;
	private String type;
	private String time_in_force;
}
