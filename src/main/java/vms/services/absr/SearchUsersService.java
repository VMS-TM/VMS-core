package vms.services.absr;

import org.springframework.web.client.RestTemplate;
import vms.models.cityenvironment.City;
import vms.models.countryenvironment.Country;

import java.util.List;

public interface SearchUsersService {
	List<Country> getCountries();

	List<City> getCities(Long id, String query);

	void getUsersInSelectedCity(Long cityID);

	RestTemplate getRestTemplate(String ip, int port);
}
