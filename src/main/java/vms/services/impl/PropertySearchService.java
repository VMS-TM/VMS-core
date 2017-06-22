package vms.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vms.models.Property;
import vms.services.absr.PropertyService;

@Service
public class PropertySearchService {

	@Autowired
	PropertyService propertyService;

	private static final Logger logger = LoggerFactory.getLogger(PropertySearchService.class);

	public String getValue(String key) {

		Property property = propertyService.getPropertyByName(key);

		if (property != null) {
			return property.getValue();
		}

		return null;

	}


}
