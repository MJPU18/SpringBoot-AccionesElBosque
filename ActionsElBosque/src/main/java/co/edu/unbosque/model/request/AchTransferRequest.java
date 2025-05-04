package co.edu.unbosque.model.request;

import lombok.Data;

@Data
public class AchTransferRequest {
    private String transfer_type;
    private String relationship_id;
    private String amount;
    private String direction;
}