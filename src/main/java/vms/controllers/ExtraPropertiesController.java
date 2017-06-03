package vms.controllers;

import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import vms.models.postenvironment.Post;
import vms.models.postenvironment.PostResponse;
import vms.services.absr.GroupService;
import vms.services.absr.PostSearchService;
import vms.services.absr.VkPostService;

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


	@RequestMapping(value = "/properties/extra", method = RequestMethod.GET)
	public String propertiesExtraMain() {
		return "extraproperties";
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

		return "extraproperties";
	}

}
