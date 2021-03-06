package vms.controllers.property;

import com.vdurmont.emoji.EmojiParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import vms.models.posts.Post;
import vms.services.absr.groups.GroupService;
import vms.services.absr.posts.PostSearchService;
import vms.services.absr.posts.VkPostService;

import java.util.Iterator;
import java.util.List;

@Controller
public class ExtraPropertiesController {

	@Autowired
	private PostSearchService postSearchService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private VkPostService postService;

	private static final Logger logger = LoggerFactory.getLogger(ExtraPropertiesController.class);

	@RequestMapping(value = "/properties/extra", method = RequestMethod.GET)
	public String propertiesExtraMain() {
		return "property/extraproperties";
	}

	@RequestMapping(value = "/properties/extra", method = RequestMethod.POST)
	public String propertiesExtraOptions(@RequestParam(value = "options", required = false) int[] options, ModelMap modelMap) {

		if (options != null) {
			for (int i = 0; i < options.length; i++) {
				if (options[i] == 1) {
					List<Post> posts = postService.getAllPostFromDb();
					for (Iterator<Post> iter = posts.listIterator(); iter.hasNext(); ) {
						Post postCurrent = iter.next();
						String removedNoTag = postCurrent.getText().replaceAll("[id+\\d+|]", "");
						postCurrent.setText(EmojiParser.removeAllEmojis(removedNoTag));
					}
				}
			}
			modelMap.addAttribute("result", true);
		} else {
			modelMap.addAttribute("result", false);
		}

		return "property/extraproperties";
	}

}
