package vms.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vms.models.Property;
import vms.services.absr.PropertyService;

@Controller
public class PropertiesController {

    private static final String home = "redirect:/properties/";

    @Autowired
    private PropertyService propertyService;

    @RequestMapping(value = {"/properties"}, method = RequestMethod.GET)
    public String getAllProperty (Model model) {
        model.addAttribute("properties",propertyService.getAllProperties());
        model.addAttribute(new Property());
        return "properties";
    }

    @RequestMapping(value = {"/properties/add"}, method = RequestMethod.POST, headers = "content-type=application/x-www-form-urlencoded")
    public String addProperty(@ModelAttribute Property property) {
        propertyService.addProperty(property);
        return home;
    }

    @RequestMapping(value = {"/properties/update"}, method = RequestMethod.POST)
    public String updateProperty(@ModelAttribute Property property) {
        propertyService.updateProperty(property);
        return home;
    }

    @RequestMapping(value = {"/properties/delete"}, method = RequestMethod.POST)
    public String delProperty(@ModelAttribute Property property) {
        propertyService.deleteProperty(property);
        return home;
    }

}
