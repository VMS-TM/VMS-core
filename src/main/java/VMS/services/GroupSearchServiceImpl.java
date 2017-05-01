package VMS.services;

import VMS.model.Group;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by Skrezhet on 26.04.2017.
 */
@Service
public class GroupSearchServiceImpl implements GroupSearchService {
    private int countIds = 0;
    private int numberID = 0;
    private RestTemplate restTemplate = new RestTemplate();

    private String getUrlApiVk(String stringIDs) {
        final String URL = "https://api.vk.com/method/";
        final String METHOD = "groups.getById?";
        final String TOKEN = "&access_token=808bcfd51bd94b4d0593a2dda57037fc4fdc46cac46e20d1b260c1a90d88b4c23023dd977e9639f7f8279";
        final String VERSION = "&v=5.52";
        final String PARAMETER_GROUP_IDS = "group_ids=";
        final String PARAMETER_FIELDS = "&fields=can_post";

        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(URL);
        urlBuilder.append(METHOD);
        urlBuilder.append(PARAMETER_GROUP_IDS);
        urlBuilder.append(stringIDs);
        urlBuilder.append(PARAMETER_FIELDS);
        urlBuilder.append(TOKEN);
        urlBuilder.append(VERSION);

        return urlBuilder.toString();
    }

    private String getUrlApiVk(String mainKeyword, String secondaryKeyword) {
        final String URL = "https://api.vk.com/method/";
        final String METHOD = "groups.search?";
        final String TOKEN = "&access_token=808bcfd51bd94b4d0593a2dda57037fc4fdc46cac46e20d1b260c1a90d88b4c23023dd977e9639f7f8279";
        final String VERSION = "&v=5.52";
        final String PARAMETER_COUNT = "count=1000";
        final String PARAMETER_TYPE = "&type=group";
        String PARAMETER_Q = "&q=";

        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(URL);
        urlBuilder.append(METHOD);
        urlBuilder.append(PARAMETER_COUNT);
        urlBuilder.append(PARAMETER_TYPE);
        urlBuilder.append(PARAMETER_Q);
        urlBuilder.append(mainKeyword);
        urlBuilder.append(" ");
        urlBuilder.append(secondaryKeyword);
        urlBuilder.append(TOKEN);
        urlBuilder.append(VERSION);

        return urlBuilder.toString();
    }

    private String[] getMainKeyWordsArray(String mainKeyword) {
        String inputMainKeywords = mainKeyword.replace(",", "");
        return inputMainKeywords.split(" ");
    }

    private String[] getSecondaryKeyWordsArray(String secondaryKeyword) {
        String inputMainKeywords = secondaryKeyword.replace(",", "");
        return inputMainKeywords.split(" ");
    }

    public Set<Long> getGroupsIds(String mainKeywords, String secondaryKeywords) {
        Set<Group> allGroups = new HashSet<>();
        Set<Long> groupsIDs = new HashSet<>();

        String[] mainKeyWordsArray = getMainKeyWordsArray(mainKeywords);
        String[] secondaryWordsArray = getSecondaryKeyWordsArray(secondaryKeywords);

        for (String mainKeyword : mainKeyWordsArray) {
            for (String secondaryKeyword : secondaryWordsArray) {
                String result = restTemplate.getForObject(getUrlApiVk(mainKeyword, secondaryKeyword), String.class);
                try {
                    TimeUnit.MILLISECONDS.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ObjectMapper objectMapper = new ObjectMapper();

                StringBuilder stringBuilder = new StringBuilder(result);
                String json = stringBuilder.substring(stringBuilder.indexOf("["), stringBuilder.lastIndexOf("]") + 1);
                try {
                    Set<Group> groups = objectMapper.readValue(json, new TypeReference<Set<Group>>() {
                    });
                    allGroups.addAll(groups);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            Iterator<Group> iterator = allGroups.iterator();

            while (iterator.hasNext()) {
                Group group = iterator.next();
                if (group.getIs_closed() == 1) {
                    iterator.remove();
                } else {
                    groupsIDs.add(group.getId());
                }
            }
        }
        return groupsIDs;
    }

    @Override
    public Map<Long, String> getGroupsWithCanPost(String mainKeyword, String secondaryKeyword) {
        Set<Long> ids = getGroupsIds(mainKeyword, secondaryKeyword);
        StringBuilder urlBuilder = new StringBuilder();
        String stringIDs = null;
        Set<Group> groupsCanPost = new HashSet<>();
        Map<Long, String>mapIDsURLs = new HashMap<>();

        for (Long id: ids) {
            if(countIds > 420){
                countIds = 0;
                urlBuilder.deleteCharAt(urlBuilder.lastIndexOf(","));
                stringIDs = urlBuilder.toString();

                try {
                    TimeUnit.MILLISECONDS.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String result = restTemplate.getForObject(getUrlApiVk(stringIDs), String.class);
                stringIDs = "";
                urlBuilder.delete(0, urlBuilder.length() - 1);

                ObjectMapper objectMapper = new ObjectMapper();

                StringBuilder stringBuilder = new StringBuilder(result);
                String json = stringBuilder.substring(stringBuilder.indexOf("["), stringBuilder.lastIndexOf("]") + 1);
                try {
                    Set<Group> groups = objectMapper.readValue(json, new TypeReference<Set<Group>>(){});
                    groupsCanPost.addAll(groups);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            countIds++;
            urlBuilder.append(id);
            urlBuilder.append(",");
            numberID++;
            if (numberID == ids.size()){
                countIds = 0;
                numberID = 0;
                urlBuilder.deleteCharAt(urlBuilder.lastIndexOf(","));
                stringIDs = urlBuilder.toString();

                try {
                    TimeUnit.MILLISECONDS.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String result = restTemplate.getForObject(getUrlApiVk(stringIDs), String.class);

                ObjectMapper objectMapper = new ObjectMapper();

                StringBuilder stringBuilder = new StringBuilder(result);
                String json = stringBuilder.substring(stringBuilder.indexOf("["), stringBuilder.lastIndexOf("]") + 1);

                try {
                    Set<Group> groups = objectMapper.readValue(json, new TypeReference<Set<Group>>(){});
                    groupsCanPost.addAll(groups);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Iterator<Group> iterator = groupsCanPost.iterator();


        while (iterator.hasNext()) {
            Group group = iterator.next();
            Long can_post = group.getCan_post();
            if (can_post == 0 || group.getDeactivated().equals("banned")) {
                iterator.remove();
            } else {
                mapIDsURLs.put(group.getId(),"https://vk.com/club" + group.getId());
            }
        }

        return mapIDsURLs;
    }
}
