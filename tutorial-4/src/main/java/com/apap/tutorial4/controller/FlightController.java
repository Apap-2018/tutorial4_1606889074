package com.apap.tutorial4.controller;

import com.apap.tutorial4.model.FlightModel;
import com.apap.tutorial4.model.PilotModel;
import com.apap.tutorial4.service.FlightService;
import com.apap.tutorial4.service.PilotService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * FlightController
 */
@Controller
public class FlightController {
	@Autowired
	private FlightService flightService;
	
	@Autowired
	private PilotService pilotService;
	
	@RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.GET)
	private String add(@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {
		FlightModel flight = new FlightModel();
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		flight.setPilot(pilot);
		
		model.addAttribute("flight", flight);
		return "addFlight";
	}
	
	@RequestMapping(value = "/flight/add", method = RequestMethod.POST)
	private String addFligthSubmit(@ModelAttribute FlightModel flight) {
		flightService.addFlight(flight);
		return "add";
	}
	
	@RequestMapping(value = "/flight/delete/{id}")
	private String deleteFlight(@PathVariable(value = "id") long id, Model model) {
		FlightModel archive = flightService.getFlightDetailById(id);
		
		flightService.deleteFlight(archive);
		return "delete";
	}
	
	@RequestMapping(value = "/flight/update/{id}", method = RequestMethod.GET)
	private String update(@PathVariable(value = "id") long id, Model model) {
		FlightModel archive = flightService.getFlightDetailById(id);
		
		model.addAttribute("flight", archive);
		return "updateFlight";
	}
	
	@RequestMapping(value = "/flight/update", method = RequestMethod.POST)
	private String updateFlightSubmit(@ModelAttribute FlightModel flight) {
		FlightModel archive = flightService.getFlightDetailById(flight.getId());
		
		archive.setDestination(flight.getDestination());
		archive.setFlightNumber(flight.getFlightNumber());
		archive.setOrigin(flight.getOrigin());
		archive.setTime(flight.getTime());
		
		flightService.addFlight(archive);
		return "update";
	}
	
	@RequestMapping(value = "/flight/view")
	private String view(Model model) {
		List<FlightModel> archive = flightService.getAllFlight();
		
		model.addAttribute("flightList", archive);
		return "view-flight";
	}
}
