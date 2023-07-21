package com.we.SuperHeroSightings.Controller;

import com.we.SuperHeroSightings.entities.*;
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
        String powerId = request.getParameter("powerPK");
        String[] organizationIds = request.getParameterValues("organizationPK");

        hero.setPower(service.getPowerByID(Integer.parseInt(powerId)));

        List<Organization> organizations = new ArrayList<>();
        for(String organizationId : organizationIds) {
            organizations.add(service.getOrganizationByID(Integer.parseInt(organizationId)));
        }
        hero.setOrganizations(organizations);
        service.addHero(hero);

        return "redirect:/heroes";
    }

    @GetMapping("deleteHero")
    public String deleteHero(Integer id) {
        service.deleteHeroByID(id);

        return "redirect:/heroes";
    }

    @GetMapping("heroDetail")
    public String heroDetail(Integer id, Model model) {
        Hero hero = service.getHeroByID(id);
        List<Sighting> sightings = service.getSightingsByHero(hero);
        model.addAttribute("sightings", sightings);
        model.addAttribute("hero", hero);

        return "heroDetail";
    }

    @GetMapping("editHero")
    public String editHero(Integer id, Model model) {
        Hero hero = service.getHeroByID(id);
        List<Power> powers = service.getAllPowers();
        List<Organization> organizations = service.getAllOrganizations();
        model.addAttribute("hero", hero);
        model.addAttribute("powers", powers);
        model.addAttribute("organizations", organizations);

        return "editHero";
    }

    @PostMapping("editHero")
    public String performEditHero(Hero hero, HttpServletRequest request) {
        String powerId = request.getParameter("powerPK");
        String[] organizationIds = request.getParameterValues("organizationPK");

        hero.setPower(service.getPowerByID(Integer.parseInt(powerId)));

        List<Organization> organizations = new ArrayList<>();
        for(String organizationId : organizationIds) {
            organizations.add(service.getOrganizationByID(Integer.parseInt(organizationId)));
        }
        hero.setOrganizations(organizations);

        service.updateHero(hero);

        return "redirect:/heroes";
    }


}
