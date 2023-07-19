package com.we.SuperHeroSightings.service;

import com.we.SuperHeroSightings.dao.SightingDao;
import com.we.SuperHeroSightings.dao.HeroDao;
import com.we.SuperHeroSightings.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class ServiceLayer implements ServiceInterface {
    @Autowired
    SightingDao sightingDao;

    @Autowired
    HeroDao heroDao;


    @Override
    public Hero getHeroByID(int id) {
        return heroDao.getHeroByID(id);
    }

    @Override
    public List<Hero> getAllHeros() {
       return heroDao.getAllHeros();
    }

    @Override
    public Hero addHero(Hero hero) {
        return heroDao.addHero(hero);
    }

    @Override
    public void updateHero(Hero hero) {
        heroDao.updateHero(hero);
    }

    @Override
    public void deleteHeroByID(int id) {
        heroDao.deleteHeroByID(id);
    }

    @Override
    public List<Hero> getHerosByLocation(Location location) {
        return heroDao.getHerosByLocation(location);
    }

    @Override
    public List<Hero> getHerosByOrganization(Organization organization) {

        return heroDao.getHerosByOrganization(organization);
    }
    @Override
public void validateHero(Hero hero) throws DuplicateNameExistsException {
        List<Hero> heroes =heroDao.getAllHeros();
        boolean isDuplicate =false;
        for(Hero ahero: heroes) {
            if (ahero.getName().toLowerCase().equals(hero.getName().toLowerCase()))
                isDuplicate = true;
        }
        if(isDuplicate) {
            throw new DuplicateNameExistsException("Hero/Villain Name Already Exists in System");

        }
    }
    @Override
    public Location getLocationByID(int id) {
        return null;
    }

    @Override
    public List<Location> getAllLocations() {
        return null;
    }

    @Override
    public Location addLocation(Location location) {
        return null;
    }

    @Override
    public void updateLocation(Location location) {

    }

    @Override
    public void deleteLocationByID(int id) {

    }

    @Override
    public List<Location> getLocationsByHero(Hero hero) {
        return null;
    }

    @Override
    public Organization getOrganizationByID(int id) {
        return null;
    }

    @Override
    public List<Organization> getAllOrganizations() {
        return null;
    }

    @Override
    public Organization addOrganization(Organization organization) {
        return null;
    }

    @Override
    public void updateOrganization(Organization organization) {

    }

    @Override
    public void deleteOrganizationByID(int id) {

    }

    @Override
    public List<Organization> getOrganizationsByHero(Hero hero) {
        return null;
    }

    @Override
    public Power getPowerByID(int id) {
        return null;
    }

    @Override
    public List<Power> getAllPowers() {
        return null;
    }

    @Override
    public Power addPower(Power power) {
        return null;
    }

    @Override
    public void updatePower(Power power) {

    }

    @Override
    public void deletePowerByID(int id) {

    }

    @Override
    public Sighting getSightingByID(int id) {
        return sightingDao.getSightingByID(id);
    }

    @Override
    public List<Sighting> getAllSightings() {
        return sightingDao.getAllSightings();
    }

    @Override
    public Sighting addSighting(Sighting sighting) {
        return sightingDao.addSighting(sighting);
    }

    @Override
    public void updateSighting(Sighting sighting) {
        sightingDao.updateSighting(sighting);
    }

    @Override
    public void deleteSightingByID(int id) {
        sightingDao.deleteSightingByID(id);
    }

    @Override
    public List<Sighting> getSightingsByDate(LocalDateTime date) {
        return sightingDao.getSightingsByDate(date);
    }

    @Override
    public List<Sighting> getSightingsByLocation(Location location) {
        return sightingDao.getSightingsByLocation(location);
    }

    @Override
    public List<Sighting> getSightingsByHero(Hero hero) {
        return sightingDao.getSightingsByHero(hero);
    }
}
