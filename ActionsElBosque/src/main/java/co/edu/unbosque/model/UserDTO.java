package co.edu.unbosque.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Contact contact;
    private Identity identity;
    private Disclosures disclosures;
    private List<Agreement> agreements;
    private List<Document> documents;
    private TrustedContact trustedContact;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Contact {
        private String email_address;
        private String phone_number;
        private List<String> street_address;
        private String city;
        private String postal_code;
        private String state;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Identity {
        private String given_name;
        private String family_name;
        private String date_of_birth;
        private String tax_id_type;
        private String tax_id;
        private String country_of_citizenship;
        private String country_of_birth;
        private String country_of_tax_residence;
        private List<String> funding_source;
        private String annual_income_min;
        private String annual_income_max;
        private String total_net_worth_min;
        private String total_net_worth_max;
        private String liquid_net_worth_min;
        private String liquid_net_worth_max;
        private String liquidity_needs;
        private String investment_experience_with_stocks;
        private String investment_experience_with_options;
        private String risk_tolerance;
        private String investment_objective;
        private String investment_time_horizon;
        private String marital_status;
        private Integer number_of_dependents;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Disclosures {
        private Boolean is_control_person;
        private Boolean is_affiliated_exchange_or_finra;
        private Boolean is_politically_exposed;
        private Boolean immediate_family_exposed;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Agreement {
        private String agreement;
        private String signed_at;
        private String ip_address;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Document {
        private String document_type;
        private String document_sub_type;
        private String content;
        private String mime_type;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TrustedContact {
        private String given_name;
        private String family_name;
        private String email_address;
    }
}