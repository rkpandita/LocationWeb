package com.raman.location.controller;

import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.raman.location.entities.Location;
import com.raman.location.services.LocationService;
import com.raman.location.util.EmailUtil;
import com.raman.location.util.ReportUtil;

@Controller
public class LocationController {
	
	private static final String DISPLAY_LOCATIONS = "displayLocations";

	private static final String LOCATIONS = "locations";

	private Log logger = LogFactory.getLog(LocationController.class);

	@Autowired
	private LocationService service;

	@Autowired
	private ReportUtil reportUtil;
	
	@Autowired
	private EmailUtil emailUtil;
	
	@Autowired
	private ServletContext context;
	
	
	@RequestMapping(value = "/showCreate", method = RequestMethod.GET)
	public String showCreate() {
		return "createLocation";
	}

	@RequestMapping(value = "/saveLoc")
	public String saveLocation(@ModelAttribute("location") Location location, ModelMap modelMap) {
		Location locationSaved = service.saveLocation(location);
		String msg = "Location saved with name: " + locationSaved.getName();
		modelMap.addAttribute("msg", msg);
		
		logger.info("Email functionality starts");
		emailUtil.sendEmail("springemail.config@gmail.com", "Location Saved", "Location Saved mail sent");
		
		return "createLocation";
	}

	@RequestMapping(value = "/displayLocations", method = RequestMethod.GET)
	public String displayLocations(ModelMap modelMap) {
		List<Location> locations = service.getAllLocations();
		modelMap.addAttribute(LOCATIONS, locations);
		return DISPLAY_LOCATIONS;
	}

	@RequestMapping(value = "/deleteLocation")
	public String deleteLocation(@RequestParam("id") int id, ModelMap modelMap) {
		// Location location = service.getLocationById(id);
		Location location = new Location();
		location.setId(id);
		service.deleteLocation(location);
		List<Location> locations = service.getAllLocations();
		modelMap.addAttribute(LOCATIONS, locations);
		return DISPLAY_LOCATIONS;
	}

	@RequestMapping(value = "/showUpdate", method = RequestMethod.GET)
	public String showUpdate(@RequestParam("id") int id, ModelMap modelMap) {
		Location location = service.getLocationById(id);
		modelMap.addAttribute("location", location);
		return "updateLocation";
	}

	@RequestMapping(value = "/updateLoc")
	public String updateLocation(@ModelAttribute("location") Location location, ModelMap modelMap) {
		service.updateLocation(location);
		List<Location> locations = service.getAllLocations();
		modelMap.addAttribute(LOCATIONS, locations);
		return DISPLAY_LOCATIONS;
	}
	
	@RequestMapping(value = "/generateReport", method = RequestMethod.GET)
	public String generateReport() {
		List<Object[]> data = service.findTypeAndTypeCount();
		String path = context.getRealPath("/");
		reportUtil.generatePieChart(path, data);
		return "report";
	}
	
}
