package VMS.controllers;

import VMS.services.abstr.GroupSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Skrezhet on 23.04.2017.
 */

@Controller
public class SearchGroupsWhitCanPost {

    @Autowired
    private GroupSearchService groupSearchService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String searchPage(ModelMap modelMap) {
        return "search_groups";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String getUsers(@RequestParam(value = "main") String mainKeyword,
                           @RequestParam(value = "secondary") String secondaryKeyword,
                           ModelMap modelMap) throws IOException {
        int groupCount = 1;
        Map<Long, String> mapToConsole = groupSearchService.getGroupsWithCanPost(mainKeyword, secondaryKeyword);

        /**
         * For see result
         */
        for (Map.Entry<Long, String> entry : mapToConsole.entrySet()) {
            Long key = entry.getKey();
            String value = entry.getValue();
            System.out.println(key + "   " + value);
            groupCount++;
        }
        System.out.println("Open groups where we can post: " + groupCount);

        return "redirect:/";
    }
}
