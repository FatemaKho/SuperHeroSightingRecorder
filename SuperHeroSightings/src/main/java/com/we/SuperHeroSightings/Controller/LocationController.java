package com.we.SuperHeroSightings.Controller;

import com.we.SuperHeroSightings.entities.Hero;
import com.we.SuperHeroSightings.entities.Location;
import com.we.SuperHeroSightings.entities.Sighting;
import com.we.SuperHeroSightings.service.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LocationController {

    @Autowired
    ServiceInterface service;

    @GetMapping("locations")
    public String displayLocations(Model model) {
        List<Location> locations = service.getAllLocations();
        model.addAttribute("locations", locations);
        return "locations";
    }


    @PostMapping("addLocation")
    public String addLocation(Location location, HttpServletRequest request) {
        service.addLocation(location);

        return "redirect:/locations";
    }

    @GetMapping("deleteLocation")
    public String deleteLocation(Integer id) {
        service.deleteLocationByID(id);

        return "redirect:/locations";
    }

    @GetMapping("locationDetail")
    public String locationDetail(Integer id, Model model) {
        Location location = service.getLocationByID(id);
        List<Sighting> sightings = service.getSightingsByLocation(location);
        model.addAttribute("location", location);
        model.addAttribute("sightings", sightings);

        return "locationDetail";
    }

    @GetMapping("editLocation")
    public String editLocation(Integer id, Model model) {
        Location location = service.getLocationByID(id);
        model.addAttribute("location", location);

        return "editLocation";
    }

    @PostMapping("editLocation")
    public String performEditSighting(Location location, HttpServletRequest request) {

        service.updateLocation(location);

        return "redirect:/locations";
    }




}
