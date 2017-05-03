package VMS.services.abstr;

import java.util.Map;

/**
 * Created by Skrezhet on 26.04.2017.
 */
public interface GroupSearchService {
    Map<Long, String> getGroupsWithCanPost(String mainKeyword, String secondaryKeyword);
}
