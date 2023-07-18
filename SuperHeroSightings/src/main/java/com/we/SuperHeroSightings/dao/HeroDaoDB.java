package com.we.SuperHeroSightings.dao;

//import com.we.SuperHeroSightings.dao.Mappers.OrganizationMapper;
import com.we.SuperHeroSightings.entities.Hero;
import com.we.SuperHeroSightings.entities.Location;
import com.we.SuperHeroSightings.entities.Organization;
import com.we.SuperHeroSightings.entities.Power;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.we.SuperHeroSightings.dao.PowerDaoDB.PowerMapper;
import com.we.SuperHeroSightings.dao.OrganizationDaoDB.OrganizationMapper;

/**
 *
 * @author jtriolo
 */


@Repository
public class HeroDaoDB implements HeroDao {

    @Autowired
    JdbcTemplate jdbc;


    @Override
    public Hero getHeroByID(int id) {
        try {
            String sql = "SELECT * FROM hero WHERE HeroPK = ?";
            Hero hero = jdbc.queryForObject(sql, new HeroMapper(), id);
            hero.setPower(getPowerForHero(id));
            hero.setOrganizations(getOrganizationsForHero(id));
            return hero;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Transactional
    @Override
    public Hero addHero(Hero hero) {
        String sql = "INSERT INTO hero (HeroName, Type, Description, PowerPK) VALUES (?, ?, ?, ?)";
        jdbc.update(sql, hero.getName(), hero.getType(), hero.getDescription(), hero.getPower().getId());

        int id = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        hero.setId(id);
        return hero;
    }

    @Override
    public List<Hero> getAllHeros() {
        String sql = "SELECT * FROM hero";
        List<Hero> heroes = jdbc.query(sql, new HeroMapper());
        for (Hero hero : heroes) {
            hero.setPower(getPowerForHero(hero.getId()));
            hero.setOrganizations(getOrganizationsForHero(hero.getId()));
        }
        return heroes;
    }

    @Transactional
    @Override
    public void updateHero(Hero hero) {
        String sql = "UPDATE hero SET HeroName = ?, Type = ?, Description = ?, PowerPK = ? WHERE HeroPK = ?";
        jdbc.update(sql, hero.getName(), hero.getType(), hero.getDescription(), hero.getPower().getId(), hero.getId());
    }

    @Transactional
    @Override
    public void deleteHeroByID(int id) {
        // First, delete any associated sightings for the hero
        String deleteSightingsSQL = "DELETE FROM sighting WHERE HeroPK = ?";
        jdbc.update(deleteSightingsSQL, id);

        // Then, delete any associated records from the heroorganization table
        String deleteHeroOrganizationSQL = "DELETE FROM heroorganization WHERE HeroPK = ?";
        jdbc.update(deleteHeroOrganizationSQL, id);

        //  delete the hero record from the hero table
        String deleteHeroSQL = "DELETE FROM hero WHERE HeroPK = ?";
        jdbc.update(deleteHeroSQL, id);
    }

    public List<Hero> getHerosByLocation(Location location) {
        String sql = "SELECT h.* FROM hero h " +
                "JOIN sighting s ON h.HeroPK = s.HeroPK " +
                "JOIN location l ON s.LocationPK = l.LocationPK " +
                "WHERE l.LocationPK = ?";

        List<Hero> heroesByLocation = jdbc.query(sql, new HeroMapper(), location.getId());
        return heroesByLocation;
    }

    @Override
    public List<Hero> getHerosByOrganization(Organization organization) {
        String sql = "SELECT h.* FROM hero h " +
                "JOIN heroorganization ho ON h.HeroPK = ho.HeroPK " +
                "WHERE ho.OrganizationPK = ?";

        List<Hero> herosByOrg = jdbc.query(sql, new HeroMapper(), organization.getId());
        return herosByOrg;
    }

    private List<Organization> getOrganizationsForHero(int heroId) {
        String sql = "SELECT o.* FROM organization o " +
                "JOIN heroorganization ho ON o.OrganizationPK = ho.OrganizationPK " +
                "WHERE ho.HeroPK = ?";
        List<Organization> heroOrganizations = jdbc.query(sql, new OrganizationMapper(), heroId);
        return heroOrganizations;
    }

    private Power getPowerForHero(int heroId) {
        String sql = "SELECT p.* FROM power p " +
                "JOIN hero h ON p.PowerPK = h.PowerPK " +
                "WHERE h.HeroPK = ?";
        Power heroPower = jdbc.queryForObject(sql, new PowerMapper(), heroId);
        return heroPower;
    }

    public static final class HeroMapper implements RowMapper<Hero> {
        @Override
        public Hero mapRow(ResultSet rs, int rowNum) throws SQLException {
            Hero hero = new Hero();
            hero.setId(rs.getInt("heroPK"));
            hero.setName(rs.getString("heroName"));
            hero.setDescription(rs.getString("description"));
            hero.setType(rs.getString("type"));
            return hero;
        }
    }
}