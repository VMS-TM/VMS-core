package vms.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vms.globalVariables.ConstantsForVkApi;
import vms.models.ProxyServer;
import vms.models.cityenvironment.City;
import vms.models.cityenvironment.CityRootObject;
import vms.models.countryenvironment.Country;
import vms.models.countryenvironment.CountryRootObject;
import vms.models.usersenvironment.UserFromVK;
import vms.models.usersenvironment.UsersRootObject;
import vms.services.absr.ProxyServerService;
import vms.services.absr.SearchUsersService;
import vms.services.absr.UserFromVkService;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Service for search users in selected city
 */
@Service
public class SearchUsersServiceImpl implements SearchUsersService {

	private RestTemplate restTemplate = new RestTemplate();

	@Autowired
	private Environment environment;

	@Autowired
	private ProxyServerService proxyServerService;

	@Autowired
	private UserFromVkService userFromVkService;

	/**
	 * Method forms url for get all countries
	 *
	 * @return String url for get countries
	 */
	private String urlForDatabaseCountries() {
		String token;
		StringBuilder builder = new StringBuilder(ConstantsForVkApi.URL);
		List<ProxyServer> proxyServers = new ArrayList<>();
		proxyServers.addAll(proxyServerService.proxyServerList());

		token = proxyServers.get(0).getToken();

		builder.append(ConstantsForVkApi.METHOD_GET_COUNTRIES);
		builder.append(ConstantsForVkApi.PARAMETERS_FOR_COUNTRIES);
		builder.append(ConstantsForVkApi.TOKEN);
		builder.append(token);
		builder.append(ConstantsForVkApi.VERSION);

		return builder.toString();
	}

	/**
	 * Method forms url for search cities
	 *
	 * @param id - id of country
	 * @param query - title wanted city
	 * @return String url for get countries
	 */
	private String urlForDatabaseCities(Long id, String query) {
		String token;
		List<ProxyServer> proxyServers = new ArrayList<>();
		StringBuilder builder = new StringBuilder(ConstantsForVkApi.URL);
		proxyServers.addAll(proxyServerService.proxyServerList());

		token = proxyServers.get(0).getToken();

		builder.append(ConstantsForVkApi.METHOD_GET_CITIES);
		builder.append(ConstantsForVkApi.PARAMETERS_FOR_CITIES);
		builder.append(ConstantsForVkApi.PARAMETER_COUNTRY);
		builder.append(id);
		builder.append(ConstantsForVkApi.PARAMETER_QUERY);
		builder.append(query);
		builder.append(ConstantsForVkApi.TOKEN);
		builder.append(token);
		builder.append(ConstantsForVkApi.VERSION);

		return builder.toString();
	}

	/**
	 * Method return all countries
	 *
	 * @return List of countries
	 */
	@Override
	public List<Country> getCountries() {
		CountryRootObject countryRootObject = restTemplate.getForObject(urlForDatabaseCountries(), CountryRootObject.class);

		return countryRootObject.getResponse().getItems();
	}

	/**
	 * Method return list of cities in selected country and sent query
	 *
	 * @param id - id of country
	 * @param query - title wanted city
	 * @return List of cities
	 */
	@Override
	public List<City> getCities(Long id, String query) {
		CityRootObject cityRootObject = restTemplate.getForObject(urlForDatabaseCities(id, query), CityRootObject.class);

		return cityRootObject.getResponse().getItems();
	}

	/**
	 * Method return id-s of users in selected city
	 *
	 * @param cityID - city, in which will be search users
	 * @return void, add users in DB
	 */
	@Override
	public void getUsersInSelectedCity(Long cityID) {
		int counterProxy = 0;
		int firstElement = 0;
		int lastElement = 0;

		List<ProxyServer> proxyServerList = new ArrayList<>();
		proxyServerList.addAll(proxyServerService.proxyServerList());

		if (proxyServerList.size() == 0) {
			return;
		}

		/**
		 * 366 days in year max. Dividing birthdays into two sexes - 732
		 */
		int requestOnProxy = 732 / proxyServerList.size();
		int remainingRequests = 732 % proxyServerList.size();

		List<String> queries = getListOfQueries(requestOnProxy, remainingRequests, cityID);

		List<UserFromVK> userFromVKList = userFromVkService.getAllUsersOfVk();

		ExecutorService executorService = Executors.newFixedThreadPool(proxyServerList.size());

		for (ProxyServer proxyServer : proxyServerList) {
			RestTemplate proxyTemplate = getRestTemplate(proxyServerList.get(counterProxy).getIp(), proxyServerList.get(counterProxy).getPort());
			lastElement += requestOnProxy;
			if (remainingRequests > 0) {
				remainingRequests--;
				lastElement += 1;
			}

			final int start = firstElement;
			final int finish = lastElement;


			Future future = executorService.submit(new Thread(() -> {
				/**
				 * Sending requests with the necessary delays to vk.api
				 */
				for (int i = start; i < finish; i++) {
					if (i > 0 && i % 3 == 0) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					if (i > 0 && i % 100 == 0) {
						try {
							Thread.sleep(300_000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

					UsersRootObject usersRootObject = proxyTemplate.getForObject(queries.get(i), UsersRootObject.class);
					List<UserFromVK> foundedUsersFromVk = usersRootObject.getResponse().getItems();

					if (!userFromVKList.containsAll(foundedUsersFromVk)) {
						userFromVkService.addListOfUsersOfVK(foundedUsersFromVk);
					} else if (userFromVKList.containsAll(foundedUsersFromVk) && foundedUsersFromVk.size()!= 0){
						List<UserFromVK>usersWhichNotInDB = new ArrayList<>();

						for (UserFromVK user : foundedUsersFromVk) {
							if(!userFromVKList.contains(user)){
								usersWhichNotInDB.add(user);
							}
						}

						userFromVkService.addListOfUsersOfVK(usersWhichNotInDB);
					}
				}
			}));
			firstElement += lastElement;
			counterProxy++;
		}

		executorService.shutdown();
	}

	/**
	 * Method return RestTemplate, using proxy
	 *
	 * @param ip of proxy server
	 * @param port of proxy server
	 * @return RestTemplate
	 */
	private RestTemplate getRestTemplate(String ip, int port) {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		InetSocketAddress address = new InetSocketAddress(ip, port);
		Proxy proxy = new Proxy(Proxy.Type.HTTP, address);

		factory.setProxy(proxy);

		return new RestTemplate(factory);
	}

	/**
	 * Method return list of queries
	 *
	 * @param requestOnProxy - quantity requests on one proxy
	 * @param remainingRequests - quantity remaining requests on one proxy
	 * @param cityID - id of city where will be search users
	 * @return List of queries
	 */
	private List<String> getListOfQueries(int requestOnProxy, int remainingRequests, Long cityID) {
		boolean flagNextProxyServer = false;
		int countRequest = 0;
		int countProxyServer = 0;
		Integer daysInMonth;

		List<String> list = new ArrayList<>();
		StringBuilder builder = new StringBuilder();

		List<ProxyServer> proxyServerList = new ArrayList<>();
		proxyServerList.addAll(proxyServerService.proxyServerList());

		/**
		 * Bypass all days of the year (2 times)
		 */
		for (Integer sex = 1; sex <= 2; sex++) {
			for (Integer month = 1; month <= 12; month++) {
				if (month <= 7) {
					if (month == 2) {
						daysInMonth = 29;
					} else if (month % 2 == 0) {
						daysInMonth = 30;
					} else {
						daysInMonth = 31;
					}
				} else {
					if (month % 2 == 0) {
						daysInMonth = 31;
					} else {
						daysInMonth = 30;
					}
				}

				/**
				 * Create a request according to each birthday
				 */
				for (Integer day = 1; day <= daysInMonth; day++) {
					if (countRequest >= requestOnProxy) {
						if (remainingRequests > 0 && !flagNextProxyServer) {
							remainingRequests--;
							flagNextProxyServer = true;
						} else {
							countRequest = 0;
							countProxyServer++;
							flagNextProxyServer = false;
						}
					}
					builder.append(ConstantsForVkApi.URL);
					builder.append(ConstantsForVkApi.METHOD_GET_USERS);
					builder.append(ConstantsForVkApi.PARAMETER_CITY);
					builder.append(cityID);
					builder.append(ConstantsForVkApi.PARAMETER_COUNT);
					builder.append(ConstantsForVkApi.PARAMETER_AGE_FROM);
					builder.append(ConstantsForVkApi.PARAMETER_SEX);
					builder.append(sex);
					builder.append(ConstantsForVkApi.PARAMETER_BIRTH_MONTH);
					builder.append(month);
					builder.append(ConstantsForVkApi.PARAMETER_BIRTH_DAY);
					builder.append(day);
					builder.append(ConstantsForVkApi.TOKEN);
					builder.append(proxyServerList.get(countProxyServer).getToken());
					builder.append(ConstantsForVkApi.VERSION);

					list.add(builder.toString());

					builder.delete(0, builder.length());
					countRequest++;
				}
			}
		}

		return list;
	}
}
