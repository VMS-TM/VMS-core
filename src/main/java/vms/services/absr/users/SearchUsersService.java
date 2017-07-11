package vms.services.absr.users;

import org.springframework.web.client.RestTemplate;
import vms.models.users.city.City;
import vms.models.users.country.Country;

import java.util.List;

public interface SearchUsersService {
	List<Country> getCountries();

	List<City> getCities(Long id, String query);

	void getUsersInSelectedCity(Long cityID);

	RestTemplate getRestTemplate(String ip, int port);
}
