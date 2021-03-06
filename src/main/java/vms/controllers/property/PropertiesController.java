package vms.controllers.property;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vms.models.property.Property;
import vms.services.absr.property.PropertyService;

@Controller
public class PropertiesController {

	@Autowired
	private PropertyService propertyService;

	private static final Logger logger = LoggerFactory.getLogger(PropertiesController.class);

	private static final String home = "redirect:/properties";

	@RequestMapping(value = {"/properties"}, method = RequestMethod.GET)
	public String getAllProperty(Model model) {
		model.addAttribute("properties", propertyService.getAllProperties());
		model.addAttribute(new Property());
		return "property/properties";
	}

	@RequestMapping(value = {"/properties/add"}, method = RequestMethod.POST, headers = "content-type=application/x-www-form-urlencoded")
	public String addProperty(@ModelAttribute @Validated Property property, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "redirect:/properties?hasNull";
		}

		try {
			propertyService.addProperty(property);
		} catch (Exception exp) {
			return "redirect:/properties?duplicate";
		}
		return home;
	}

	@RequestMapping(value = {"/properties/update"}, method = RequestMethod.POST)
	public String updateProperty(@ModelAttribute @Validated Property property, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "redirect:/properties?hasNull";
		}

		try {
			propertyService.updateProperty(property);
		} catch (Exception exp) {
			return "redirect:/properties?duplicate";
		}


		return home;
	}

	@RequestMapping(value = {"/properties/delete"}, method = RequestMethod.POST)
	public String delProperty(@ModelAttribute Property property) {
		propertyService.deleteProperty(property);
		return home;
	}

}
