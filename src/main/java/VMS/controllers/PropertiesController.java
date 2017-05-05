package VMS.controllers;

import VMS.model.Property;
import VMS.services.abstr.VkPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Properties;

/**
 * Created by Кирилл on 03.05.2017.
 */
@Controller
@RequestMapping(value = "/property") // НАЧАЛО запроса
public class PropertiesController {

    private static String home = "redirect:/property/";

    @Autowired
    VkPropertyService propertyService;

    @RequestMapping(value = { "/"}, method = RequestMethod.GET)
    public String getAllProperty (Model model) {
        List<Property> properties = propertyService.getAllProperties();
        model.addAttribute("properties",propertyService.getAllProperties());
        model.addAttribute(new Property());
        return "properties";
    }

    @RequestMapping(value = { "/add"}, method = RequestMethod.POST, headers = "content-type=application/x-www-form-urlencoded")
    public String addProperty (@ModelAttribute Property property) {
        propertyService.addProperty(property);
        return home;
    }

    @RequestMapping(value = { "/update"}, method = RequestMethod.POST)
    public String updateProperty (@ModelAttribute Property property) {
        propertyService.updateProperty(property);
        return home;
    }

    @RequestMapping(value = { "/delete"}, method = RequestMethod.POST)
    public String delProperty (@ModelAttribute Property property) {
        propertyService.deleteProperty(property);
        return home;
    }

}
