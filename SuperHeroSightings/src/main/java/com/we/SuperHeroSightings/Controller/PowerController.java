package com.we.SuperHeroSightings.Controller;


import com.we.SuperHeroSightings.entities.Hero;
import com.we.SuperHeroSightings.entities.Power;
import com.we.SuperHeroSightings.service.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PowerController {

    @Autowired
    ServiceInterface service;

    @GetMapping("powers")
    public String displayPowers(Model model) {
        List<Power> powers = service.getAllPowers();
        model.addAttribute("powers", powers);
        return "powers";
    }

    @GetMapping("/addPower")
    public String showAddPowerForm(Model model) {
        model.addAttribute("power", new Power());
        return "addPower";
    }

    @PostMapping("/addPower")
    public String addPower(@ModelAttribute("power") @Valid Power power, BindingResult result) {
        if (result.hasErrors()) {
            // If there are validation errors, return to the form page to display them
            return "addPower";
        }

        service.addPower(power);

        return "redirect:/powers";
    }


    @PostMapping("deletePower")
    public String deletePower(Integer id) {
        service.deletePowerByID(id);

        return "redirect:/powers";
    }

    @GetMapping("powerDetail")
    public String powerDetail(Integer id, Model model) {
        Power power = service.getPowerByID(id);
        List<Hero> heroes = service.getAllHeros();
        model.addAttribute("heroes", heroes);
        model.addAttribute("power", power);

        return "powerDetail";
    }

    @GetMapping("editPower")
    public String editPower(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Power power = service.getPowerByID(id);

        model.addAttribute("power", power);

        return "editPower";
    }

    @PostMapping("editPower")
    public String performEditPower(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Power power = service.getPowerByID(id);

        power.setName(request.getParameter("name"));
        power.setDescription(request.getParameter("description"));

        service.updatePower(power);

        return "redirect:/powers";
    }
}