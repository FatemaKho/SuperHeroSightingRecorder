package com.we.SuperHeroSightings.Controller;

import com.we.SuperHeroSightings.entities.Organization;
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
public class OrganizationController {
    @Autowired
    ServiceInterface service;

    @GetMapping("organizations")
    public String displayOrganizations(Model model) {
        List<Organization> organizations = service.getAllOrganizations();
        List<Hero> heroes = service.getAllHeros();

        model.addAttribute("organizations", organizations);
        model.addAttribute("heroes", heroes);

        return "organizations";
    }

    @PostMapping("addOrganization")
    public String addOrganization(Organization organization, HttpServletRequest request) {
        String[] memberIds = request.getParameterValues("memberId");

        List<Hero> members = new ArrayList<>();
        for (String memberId : memberIds) {
            Hero hero = service.getHeroByID(Integer.parseInt(memberId));
            members.add(hero);
        }
        organization.setMembers(members);
        service.addOrganization(organization);

        return "redirect:/organizations";
    }



    @PostMapping("deleteOrganization")
    public String deleteOrganization(Integer id) {
        service.deleteOrganizationByID(id);

        return "redirect:/organization";
    }


    @GetMapping("organizationDetail")
    public String organizationDetail(Integer id, Model model) {
        Organization organization = service.getOrganizationByID(id);
        model.addAttribute("organization", organization);

        return "organizationDetail";
    }

    @GetMapping("editOrganization")
    public String editOrganization(Integer id, Model model) {
        Organization organization = service.getOrganizationByID(id);
        List<Hero> members = service.getAllHeros();
        model.addAttribute("organization", organization);
        model.addAttribute("members", members);

        return "editOrganization";
    }

    @PostMapping("editOrganization")
    public String performEditOrganization(Organization organization, HttpServletRequest request) {
        String[] memberIds = request.getParameterValues("memberId");

        List<Hero> members = new ArrayList<>();
        for (String memberId : memberIds) {
            Hero hero = service.getHeroByID(Integer.parseInt(memberId));
            members.add(hero);
        }
        organization.setMembers(members);

        service.updateOrganization(organization);

        return "redirect:/organizations";
    }



}
