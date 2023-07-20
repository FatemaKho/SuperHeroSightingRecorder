/*package com.we.SuperHeroSightings.Controller;

import com.we.SuperHeroSightings.entities.*;
import com.we.SuperHeroSightings.service.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class HeroController {

    @Autowired
    ServiceInterface service;

    @GetMapping("heroes")
    public String displayHeroes(Model model) {
        List<Hero> heroes = service.getAllHeros();
        List<Power> powers = service.getAllPowers();
        List<Organization> organizations = service.getAllOrganizations();
        model.addAttribute("heroes", heroes);
        model.addAttribute("powers", powers);
        model.addAttribute("organizations", organizations);

        return "heroes";
    }


    @PostMapping("addHero")
    public String addSighting(Hero hero, HttpServletRequest request) {
        String powerId = request.getParameter("heroPK");
        String organizationId = request.getParameter("locationPK");

        sighting.setHero(service.getHeroByID(Integer.parseInt(heroId)));
        sighting.setLocation(service.getLocationByID(Integer.parseInt(locationId)));


        service.addSighting(sighting);

        return "redirect:/sightings";
    }

    @GetMapping("deleteSighting")
    public String deleteSighting(Integer id) {
        service.deleteSightingByID(id);

        return "redirect:/sightings";
    }

    @GetMapping("sightingDetail")
    public String sightingDetail(Integer id, Model model) {
        Sighting sighting = service.getSightingByID(id);
        model.addAttribute("sighting", sighting);

        return "sightingDetail";
    }

    @GetMapping("editSighting")
    public String editSighting(Integer id, Model model) {
        Sighting sighting = service.getSightingByID(id);
        List<Hero> heroes = service.getAllHeros();
        List<Location> locations = service.getAllLocations();
        model.addAttribute("sighting", sighting);
        model.addAttribute("heroes", heroes);
        model.addAttribute("locations", locations);

        return "editSighting";
    }

    @PostMapping("editSighting")
    public String performEditSighting(Sighting sighting, HttpServletRequest request) {
        String heroId = request.getParameter("heroPK");
        String locationId = request.getParameter("locationPK");

        sighting.setHero(service.getHeroByID(Integer.parseInt(heroId)));
        sighting.setLocation(service.getLocationByID(Integer.parseInt(locationId)));

        service.updateSighting(sighting);

        return "redirect:/sightings";
    }



} */
