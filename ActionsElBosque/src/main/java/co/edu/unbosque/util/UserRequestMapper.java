package co.edu.unbosque.util;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.model.request.UserAlpRequest;
import co.edu.unbosque.model.request.UserRequest;

public class UserRequestMapper {
	
	public static UserAlpRequest toUserAlpRequest(UserRequest userRequest) {
	    UserRequest.Contact contact = userRequest.getContact();
	    UserRequest.Identity identity = userRequest.getIdentity();
	    UserRequest.Disclosures disclosures = userRequest.getDisclosures();
	    UserRequest.TrustedContact trustedContact = userRequest.getTrustedContact();

	    return new UserAlpRequest(
	            new UserAlpRequest.Contact(
	                    contact != null ? contact.getEmail_address() : null,
	                    contact != null ? contact.getPhone_number() : null,
	                    contact != null ? contact.getStreet_address() : new ArrayList<String>(),
	                    contact != null ? contact.getCity() : null,
	                    contact != null ? contact.getPostal_code() : null,
	                    contact != null ? contact.getState() : null
	            ),
	            new UserAlpRequest.Identity(
	                    identity != null ? identity.getGiven_name() : null,
	                    identity != null ? identity.getFamily_name() : null,
	                    identity != null ? identity.getDate_of_birth() : null,
	                    identity != null ? identity.getTax_id_type() : null,
	                    identity != null ? identity.getTax_id() : null,
	                    identity != null ? identity.getCountry_of_citizenship() : null,
	                    identity != null ? identity.getCountry_of_birth() : null,
	                    identity != null ? identity.getCountry_of_tax_residence() : null,
	                    identity != null ? identity.getFunding_source() : null,
	                    identity != null ? identity.getAnnual_income_min() : null,
	                    identity != null ? identity.getAnnual_income_max() : null,
	                    identity != null ? identity.getTotal_net_worth_min() : null,
	                    identity != null ? identity.getTotal_net_worth_max() : null,
	                    identity != null ? identity.getLiquid_net_worth_min() : null,
	                    identity != null ? identity.getLiquid_net_worth_max() : null,
	                    identity != null ? identity.getLiquidity_needs() : null,
	                    identity != null ? identity.getInvestment_experience_with_stocks() : null,
	                    identity != null ? identity.getInvestment_experience_with_options() : null,
	                    identity != null ? identity.getRisk_tolerance() : null,
	                    identity != null ? identity.getInvestment_objective() : null,
	                    identity != null ? identity.getInvestment_time_horizon() : null,
	                    identity != null ? identity.getMarital_status() : null,
	                    identity != null ? identity.getNumber_of_dependents() : null
	            ),
	            new UserAlpRequest.Disclosures(
	                    disclosures != null ? disclosures.getIs_control_person() : null,
	                    disclosures != null ? disclosures.getIs_affiliated_exchange_or_finra() : null,
	                    disclosures != null ? disclosures.getIs_politically_exposed() : null,
	                    disclosures != null ? disclosures.getImmediate_family_exposed() : null
	            ),
	            userRequest.getAgreements() != null ?
	                    userRequest.getAgreements().stream().map(
	                            a -> new UserAlpRequest.Agreement(
	                                    a.getAgreement(),
	                                    a.getSigned_at(),
	                                    a.getIp_address()
	                            )
	                    ).toList() : List.of(),
	            userRequest.getDocuments() != null ?
	                    userRequest.getDocuments().stream().map(
	                            d -> new UserAlpRequest.Document(
	                                    d.getDocument_type(),
	                                    d.getDocument_sub_type(),
	                                    d.getContent(),
	                                    d.getMime_type()
	                            )
	                    ).toList() : List.of(),
	            new UserAlpRequest.TrustedContact(
	                    trustedContact != null ? trustedContact.getGiven_name() : null,
	                    trustedContact != null ? trustedContact.getFamily_name() : null,
	                    trustedContact != null ? trustedContact.getEmail_address() : null
	            ),
	            userRequest.getPassword(),
	            userRequest.getIdCommission()
	    );
	}


}
