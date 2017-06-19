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

	/**
	 * Method and parameters for PostSearchService
	 */
	public final static String PARAMETER_GROUP_SEARCH = "wall.search?owner_id=-";
	public final static String PARAMETER_GROUP_COUNT = "&count=100";
	public final static String PARAMETER_GROUP_QUERY = "&query=";
	public final static String PARAMETER_GROUP_VERSION = "&v=5.63";
	public final static String PARAMETER_GROUP_OWNER = "owner_id";
	public final static Integer TIMEOUT = 5;


	/**
	 * Method and parameters for GroupSearchService
	 */
	public final static String PARAMETER_GROUP_SEARCH_EXE = "/execute?code=var%20hyi1=API.groups.search(%7B%22q%22:%20%22";
	public final static String PARAMETER_GROUP_SEARCH_METHOD = "execute";
	public final static String PARAMETER_GROUP_SEARCH_QUERY_ONE = "%22,%22offset%22:0,%20%22count%22:%20500%7D);%0Aif(hyi1.items.length%3E0)%0A%7B%0Avar%20nya1=API.groups.getById(%7B%22group_ids%22:%20hyi1.items@.id,%20%22fields%22:%22members_count%22%7D);%0Avar%20hyi2%20=%20API.groups.search(%7B%22q%22:%20%22";
	public final static String PARAMETER_GROUP_SEARCH_QUERY_TWO = "%22,%20%22offset%22:500,%22count%22:%20500%7D);%0Aif(hyi2.items.length%20%3E0%20)%0A%7B%0Avar%20nya2%20=%20API.groups.getById(%7B%22group_ids%22:%20hyi2.items@.id,%20%22fields%22:%22members_count%22%7D);return+nya1+%2B+nya2%3B%7D%0Areturn%20nya1;%0A%7D%0Areturn%200;&v=5.64&access_token=f0874a39a169ec6e0b35749c71cdcecc7da034205785e5d622c173454ff95b4532cbf6bf20bf924f365e4";
	public final static String PARAMETER_GROUP_GET_ID = "groups.getById?group_id=";
	public final static String PARAMETER_GROUP_GET_ID_QUERY = "&fields=members_count&v=5.64";


	/**
	 * Method and parameters for NewsSearchService
	 */
	public final static String PARAMETER_NEWS_SEARCH_METHOD = "/newsfeed.search";
	public final static String PARAMETER_NEWS_QUERY = "?q=";
	public final static String PARAMETER_NEWS_EXTENDED = "&extended=1";
	public final static String PARAMETER_NEWS_COUNT = "&count=200";
	public final static String PARAMETER_NEWS_VERSION = "&v=5.65";

	/**
	 * Method and parameters for search posts
	 */
	public final static String METHOD_GET_USERS_POSTS = "wall.search?count=100&";
	public final static String PARAMETER_OWNER_ID = "owner_id=";
	public final static String PARAMETER_QUERY_POST = "&query=";

	/**
	 * Method and parameters for PostToGroupService
	 */
	public final static String PARAMETER_POST_TO_GROUP_METHOD = "wall.post";
	public final static Integer ID_GROUP = 148962136;

}
