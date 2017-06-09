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

	public final static String DEFAULT_TOKEN_ACCESS = "d8cbb44b52afa22d68b9aee1504d621b7dd2c59f2a4f0ef2179a2275aa494e6e3f861a9efd0da5980f7dd";
	public final static Integer ID_GROUP = 145578365;

}
