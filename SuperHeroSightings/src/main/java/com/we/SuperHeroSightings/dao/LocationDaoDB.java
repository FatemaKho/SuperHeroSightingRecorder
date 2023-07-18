
package com.we.SuperHeroSightings.dao;


import com.we.SuperHeroSightings.entities.Hero;
import com.we.SuperHeroSightings.entities.Location;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author jtriolo
 */
@Repository
public class LocationDaoDB implements LocationDao {


    @Autowired
    JdbcTemplate jdbc;


    @Override
    public Location getLocationByID(int id) {


        try {
            final String SELECT_LOCATION_BY_ID = "SELECT * FROM location WHERE locationId = ?";
            return jdbc.queryForObject(SELECT_LOCATION_BY_ID, new Object[]{id}, new LocationMapper());
        } catch(DataAccessException ex) {
            return null;
        }
    }


    @Override
    public List<Location> getAllLocations() {


        final String SELECT_ALL_LOCATIONS = "SELECT * FROM location";
        return jdbc.query(SELECT_ALL_LOCATIONS, new LocationMapper());
    }


    @Override
    public Location addLocation(Location location) {
        final String INSERT_LOCATION = "INSERT INTO location(locationName, locationDesc, locationAddress, locationLatitude, locationLongitude) VALUES(?,?,?,?,?)";
        jdbc.update(INSERT_LOCATION,
                location.getName(),
                location.getDescription(),
                location.getAddress(),
                location.getLatitude(),
                location.getLongitude());


// Retrieve the last inserted ID


        String selectLastIdQuery = "SELECT LAST_INSERT_ID()";
        int locationId = jdbc.queryForObject(selectLastIdQuery, Integer.class);


// Set the game ID
        location.setId(locationId);
        return location;
    }


    @Override
    public void updateLocation(Location location) {


        final String UPDATE_LOCATION = "UPDATE location SET locationName = ?, locationDesc = ?, locationAddress = ?, locationLatitude = ?, locationLongitude = ? WHERE locationId = ?";
        jdbc.update(UPDATE_LOCATION,
                location.getName(),
                location.getDescription(),
                location.getAddress(),
                location.getLatitude(),
                location.getLongitude(),
                location.getId());
    }


    @Override
    public void deleteLocationByID(int id) {
        final String DELETE_LOCATION_BY_SIGHTING = "DELETE FROM sighting WHERE locationId = ?";
        jdbc.update(DELETE_LOCATION_BY_SIGHTING, id);


        final String DELETE_LOCATION = "DELETE FROM location WHERE locationId=?";
        jdbc.update(DELETE_LOCATION, id);


    }


    @Override
    public List<Location> getLocationsByHero(Hero hero) {


        final String SELECT_LOCATION_BY_HERO = "SELECT * FROM location INNER JOIN sighting ON " +
                "sighting.locationPK = location.locationPK INNER JOIN " +
                "hero ON hero.heroPK = sighting.heroPK WHERE hero.heroPK = ?";
        return jdbc.query(SELECT_LOCATION_BY_HERO, new LocationMapper(), hero.getId());


    }


    public static final class LocationMapper implements RowMapper<Location> {


        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location location = new Location();
            location.setId(rs.getInt("LocationPK"));
            location.setName(rs.getString("LocationName"));
            location.setDescription(rs.getString("Description"));
            location.setAddress(rs.getString("LocationAddress"));
            location.setLatitude(rs.getString("Latitude"));
            location.setLongitude(rs.getString("Longitude"));
            return location;
        }
    }




}
