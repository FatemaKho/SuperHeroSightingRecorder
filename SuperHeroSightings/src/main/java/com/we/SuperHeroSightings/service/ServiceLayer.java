package com.we.SuperHeroSightings.service;

import com.we.SuperHeroSightings.dao.LocationDao;
import com.we.SuperHeroSightings.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import java.time.LocalDateTime;
import java.util.List;

public class ServiceLayer implements ServiceInterface {

    @Autowired
    LocationDao locationDao;

    @Override
    public Hero getHeroByID(int id) {
        return null;
    }

    @Override
    public List<Hero> getAllHeros() {
        return null;
    }

    @Override
    public Hero addHero(Hero hero) {
        return null;
    }

    @Override
    public void updateHero(Hero hero) {

    }

    @Override
    public void deleteHeroByID(int id) {

    }

    @Override
    public List<Hero> getHerosByLocation(Location location) {
        return null;
    }

    @Override
    public List<Hero> getHerosByOrganization(Organization organization) {
        return null;
    }

    @Override
    public Location getLocationByID(int id) {
            return locationDao.getLocationByID(id);
    }

    @Override
    public List<Location> getAllLocations() {
        return locationDao.getAllLocations();
    }

    @Override
    public Location addLocation(Location location) {

        return locationDao.addLocation(location);
    }

    @Override
    public void updateLocation(Location location) {
        locationDao.updateLocation(location);

    }

    @Override
    public void deleteLocationByID(int id) {
        locationDao.deleteLocationByID(id);

    }

    @Override
    public List<Location> getLocationsByHero(Hero hero) {
        return locationDao.getLocationsByHero(hero);
    }

    private static class LocationValidator {

        // Method to check if the latitude is valid
        public static boolean isValidLatitude(String latitude) {
            try {
                double lat = Double.parseDouble(latitude);
                return lat >= -90 && lat <= 90;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        // Method to check if the longitude is valid
        public static boolean isValidLongitude(String longitude) {
            try {
                double lon = Double.parseDouble(longitude);
                return lon >= -180 && lon <= 180;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }

    @Override
    public void validateLocation(Location location) throws DuplicateNameExistsException {
        List<Location> locations = locationDao.getAllLocations();
        boolean isDupe = false;

        for(Location alocation : locations) {
            if (alocation.getName().toLowerCase().equals(location.getName().toLowerCase())) {
                isDupe = true;
            }
        }
        if(isDupe) {
            throw new DuplicateNameExistsException("Location Name Already Exists");
        }

        // Validate latitude and longitude
        if (!LocationValidator.isValidLatitude(location.getLatitude())) {
            throw new InvalidDataException("Invalid Latitude Format");
        }

        if (!LocationValidator.isValidLongitude(location.getLongitude())) {
            throw new InvalidDataException("Invalid Longitude Format");
        }

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
        return null;
    }

    @Override
    public List<Sighting> getAllSightings() {
        return null;
    }

    @Override
    public Sighting addSighting(Sighting sighting) {
        return null;
    }

    @Override
    public void updateSighting(Sighting sighting) {

    }

    @Override
    public void deleteSightingByID(int id) {

    }

    @Override
    public List<Sighting> getSightingsByDate(LocalDateTime date) {
        return null;
    }

    @Override
    public List<Sighting> getSightingsByLocation(Location location) {
        return null;
    }

    @Override
    public List<Sighting> getSightingsByHero(Hero hero) {
        return null;
    }


}
