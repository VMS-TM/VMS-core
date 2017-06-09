package vms.globalVariables;

public class ConstantsForVkApi {

	/**
	 * Method and parameters for search cities
	 */
	public final static String URL = "https://api.vk.com/method/";

	public final static String METHOD_GET_COUNTRIES = "database.getCountries?";
	public final static String PARAMETERS_FOR_COUNTRIES = "need_all=1&count=236&";

	public final static String METHOD_GET_CITIES = "database.getCities?";
	public final static String PARAMETERS_FOR_CITIES = "count=300&need_all=1";
	public final static String PARAMETER_COUNTRY = "&country_id=";
	public final static String PARAMETER_QUERY = "&q=";

	public final static String TOKEN = "&access_token=";
	public final static String VERSION = "&v=5.52";
	/**
	 * Method and parameters for search users
	 */
	public final static String METHOD_GET_USERS = "users.search?";
	public final static String PARAMETER_CITY = "city=";
	public final static String PARAMETER_BIRTH_DAY = "&birth_day=";
	public final static String PARAMETER_BIRTH_MONTH = "&birth_month=";
	public final static String PARAMETER_COUNT = "&count=1000";
	public final static String PARAMETER_SEX = "&sex=";
	public final static String PARAMETER_AGE_FROM = "&age_from=20";
	public final static String PARAMETER_FIELDS = "&fields=wall_comments";

	public final static String DEFAULT_TOKEN_ACCESS = "cada64882a306d1fed8b31e37336af492e31c71f2a6ec70136fe8204e55e97757c15baf3e54ad8df22005";
	public final static Long ID_GROUP = 145578365L;

}
