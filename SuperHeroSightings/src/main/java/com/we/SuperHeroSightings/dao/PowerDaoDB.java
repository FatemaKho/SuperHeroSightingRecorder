package com.we.SuperHeroSightings.dao;

import com.we.SuperHeroSightings.entities.Power;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
/* @sarahnaameh */
@Repository
public class PowerDaoDB implements PowerDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Power getPowerByID(int id) {
        try {
            final String SELECT_POWER_BY_ID = "SELECT * FROM `superpower` WHERE PowerID = ?;";
            return jdbc.queryForObject(SELECT_POWER_BY_ID, new PowerMapper(), id);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Power> getAllPowers() {
        final String SELECT_ALL_POWERS = "SELECT * FROM `superpower`;";
        return jdbc.query(SELECT_ALL_POWERS, new PowerMapper());
    }

    @Override
    public Power addPower(Power power) {
        final String INSERT_POWER = "INSERT INTO `superpower` (powerPK, power, description) VALUES (?, ?, ?);";
        jdbc.update(INSERT_POWER, power.getPowerID(), power.getPowerName(), power.getDescription());
        return power;
    }

    @Override
    public void updatePower(Power power) {
        final String UPDATE_POWER = "UPDATE `superpower` SET power = ?, description = ? WHERE powerPK = ?;";
        jdbc.update(UPDATE_POWER, power.getPowerName(), power.getDescription(), power.getPowerID());
    }

    @Override
    public void deletePowerByID(int id) {
        final String DELETE_POWER = "DELETE FROM `superpower` WHERE powerPK = ?;";
        jdbc.update(DELETE_POWER, id);
    }

    // Implement the PowerMapper class
    // private class PowerMapper implements RowMapper<Power> {
    //     // map ResultSet to Power object
    // }
}
