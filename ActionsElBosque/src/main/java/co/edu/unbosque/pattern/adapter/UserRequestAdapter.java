package co.edu.unbosque.pattern.adapter;

import java.util.ArrayList;
import java.util.List;
import co.edu.unbosque.model.request.UserAlpRequest;
import co.edu.unbosque.model.request.UserRequest;

public class UserRequestAdapter implements UserAlpRequestTarget {

	private final UserRequest userRequest;

	public UserRequestAdapter(UserRequest userRequest) {
		this.userRequest = userRequest;
	}

	@Override
	public UserAlpRequest getUserAlpRequest() {
		if (userRequest == null) {
			return null;
		}

		return new UserAlpRequest(adaptContact(), adaptIdentity(), adaptDisclosures(), adaptAgreements(),
				adaptDocuments(), adaptTrustedContact(), userRequest.getPassword(), userRequest.getIdCommission());
	}

	private UserAlpRequest.Contact adaptContact() {
		UserRequest.Contact contact = userRequest.getContact();
		if (contact == null) {
			return new UserAlpRequest.Contact(null, null, new ArrayList<>(), null, null, null);
		}

		return new UserAlpRequest.Contact(contact.getEmail_address(), contact.getPhone_number(),
				contact.getStreet_address() != null ? contact.getStreet_address() : new ArrayList<>(),
				contact.getCity(), contact.getPostal_code(), contact.getState());
	}

	private UserAlpRequest.Identity adaptIdentity() {
		UserRequest.Identity identity = userRequest.getIdentity();
		if (identity == null) {
			return new UserAlpRequest.Identity(null, null, null, null, null, null, null, null, null, null, null, null,
					null, null, null, null, null, null, null, null, null, null, null);
		}

		return new UserAlpRequest.Identity(identity.getGiven_name(), identity.getFamily_name(),
				identity.getDate_of_birth(), identity.getTax_id_type(), identity.getTax_id(),
				identity.getCountry_of_citizenship(), identity.getCountry_of_birth(),
				identity.getCountry_of_tax_residence(), identity.getFunding_source(), identity.getAnnual_income_min(),
				identity.getAnnual_income_max(), identity.getTotal_net_worth_min(), identity.getTotal_net_worth_max(),
				identity.getLiquid_net_worth_min(), identity.getLiquid_net_worth_max(), identity.getLiquidity_needs(),
				identity.getInvestment_experience_with_stocks(), identity.getInvestment_experience_with_options(),
				identity.getRisk_tolerance(), identity.getInvestment_objective(), identity.getInvestment_time_horizon(),
				identity.getMarital_status(), identity.getNumber_of_dependents());
	}

	private UserAlpRequest.Disclosures adaptDisclosures() {
		UserRequest.Disclosures disclosures = userRequest.getDisclosures();
		if (disclosures == null) {
			return new UserAlpRequest.Disclosures(null, null, null, null);
		}

		return new UserAlpRequest.Disclosures(disclosures.getIs_control_person(),
				disclosures.getIs_affiliated_exchange_or_finra(), disclosures.getIs_politically_exposed(),
				disclosures.getImmediate_family_exposed());
	}

	private List<UserAlpRequest.Agreement> adaptAgreements() {
		if (userRequest.getAgreements() == null) {
			return List.of();
		}

		return userRequest.getAgreements().stream()
				.map(agreement -> new UserAlpRequest.Agreement(agreement.getAgreement(), agreement.getSigned_at(),
						agreement.getIp_address()))
				.toList();
	}

	private List<UserAlpRequest.Document> adaptDocuments() {
		if (userRequest.getDocuments() == null) {
			return List.of();
		}

		return userRequest.getDocuments().stream()
				.map(document -> new UserAlpRequest.Document(document.getDocument_type(),
						document.getDocument_sub_type(), document.getContent(), document.getMime_type()))
				.toList();
	}

	private UserAlpRequest.TrustedContact adaptTrustedContact() {
		UserRequest.TrustedContact trustedContact = userRequest.getTrustedContact();
		if (trustedContact == null) {
			return new UserAlpRequest.TrustedContact(null, null, null);
		}

		return new UserAlpRequest.TrustedContact(trustedContact.getGiven_name(), trustedContact.getFamily_name(),
				trustedContact.getEmail_address());
	}
}
