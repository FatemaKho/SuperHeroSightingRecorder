package com.we.SuperHeroSightings.Controller;

import com.we.SuperHeroSightings.entities.Organization;
import com.we.SuperHeroSightings.entities.Hero;
import com.we.SuperHeroSightings.entities.Location;
import com.we.SuperHeroSightings.service.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class OrganizationController {
    @Autowired
    ServiceInterface service;

    @GetMapping("organizations")
    public String displayOrganizations(Model model) {
        List<Organization> organizations = service.getAllOrganizations();
        List<Hero> heroes = service.getAllHeros();
        List<Location> locations = service.getAllLocations();

        model.addAttribute("organizations", organizations);
        model.addAttribute("heroes", heroes);
        model.addAttribute("locations", locations);

        return "organizations";
    }
}
