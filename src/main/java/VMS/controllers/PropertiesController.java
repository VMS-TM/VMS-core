package VMS.controllers;

import VMS.model.Property;
import VMS.services.abstr.VkPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Properties;

/**
 * Created by Кирилл on 03.05.2017.
 */
@Controller
@RequestMapping(value = "/property") // НАЧАЛО запроса
public class PropertiesController {

    @Autowired
    VkPropertyService propertyService;

    @RequestMapping(value = { "/"}, method = RequestMethod.GET)
    public String getAllProperty (Model model) {
        List<Property> properties = propertyService.getAllProperties();
        model.addAttribute("properties",propertyService.getAllProperties());
        return "properties";
    }

    @RequestMapping(value = { "/add"}, method = RequestMethod.GET)
    public String addProperty (Model model) {
        propertyService.addProperty(new Property("key","value"));
        return "properties";
    }
//
//    @RequestMapping(value = { "/"}, method = RequestMethod.GET)
//    public String updateProperty (Model model) {
//        model.addAttribute("properties",propertyService.updateProperty());
//        return "properties";
//    }
//
//    @RequestMapping(value = { "/"}, method = RequestMethod.GET)
//    public String delProperty (Model model) {
//        model.addAttribute("properties",propertyService.deleteProperty(););
//        return "properties";
//    }

}
