
package com.we.SuperHeroSightings.dao;


import com.we.SuperHeroSightings.entities.Hero;
import com.we.SuperHeroSightings.entities.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.we.SuperHeroSightings.dao.HeroDaoDB.HeroMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Gabriela Gutierrez
 */
@Repository
public class OrganizationDaoDB implements OrganizationDao {
    
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Organization getOrganizationByID(int id) {
        try{
            final String SELECT_ORGANIZATION_BY_ID = "SELECT * " +
                    "FROM organization " +
                    "WHERE OrganizationPK = ?;";
            Organization organization = jdbc.queryForObject(SELECT_ORGANIZATION_BY_ID, new OrganizationMapper(), id);
            organization.setMembers(getHeroesByOrganization(organization));
            return organization;
        } catch (DataAccessException e) {
            return null;
        }
    }

    private List<Hero> getHeroesByOrganization(Organization organization) {
        final String SELECT_HERO_BY_ORGANIZATION = "SELECT DISTINCT h.* " +
                "FROM hero h " +
                "INNER JOIN heroorganization ho ON h.heroPK = ho.heroPK " +
                "WHERE organizationPK = ?;";
        return jdbc.query(SELECT_HERO_BY_ORGANIZATION, new HeroMapper(), organization.getId());
    }

    @Override
    public List<Organization> getAllOrganizations() {
        final String SELECT_ALL_ORGANIZATIONS = "SELECT * FROM organization;";
        return jdbc.query(SELECT_ALL_ORGANIZATIONS, new OrganizationMapper());
    }

    @Override
    public Organization addOrganization(Organization organization) {
        final String INSERT_NEW_ORGANIZATION = "INSERT INTO organization (OrganizationName, Type, Description, OrganizationAddress, Phone, ContactInfo) " +
                "VALUES (?,?,?,?,?,?);";
        jdbc.update(
                INSERT_NEW_ORGANIZATION,
                organization.getName(),
                organization.getType(),
                organization.getDescription(),
                organization.getAddress(),
                organization.getPhone(),
                organization.getContact());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID();", Integer.class);
        organization.setId(newId);
        return organization;
    }

    @Override
    public void updateOrganization(Organization organization) {
        final String UPDATE_ORGANIZATION = "UPDATE organization " +
                "SET OrganizationName = ?, Type = ?, Description = ?, OrganizationAddress = ?, Phone = ?, ContactInfo = ? " +
                "WHERE OrganizationPK = ?";
        jdbc.update(
                UPDATE_ORGANIZATION,
                organization.getName(),
                organization.getType(),
                organization.getDescription(),
                organization.getAddress(),
                organization.getPhone(),
                organization.getContact(),
                organization.getId()
        );
    }

    @Override
    @Transactional
    public void deleteOrganizationByID(int id) {
        // Delete from bridge table first
        final String DELETE_HERO_ORGANIZATION = "DELETE FROM heroorganization " +
                "WHERE OrganizationPK = ?";

        // Delete from organizations table
        final String DELETE_ORGANIZATION = "DELETE FROM organization " +
                "WHERE OrganizationPK = ?";

        jdbc.update(DELETE_HERO_ORGANIZATION, id);
        jdbc.update(DELETE_ORGANIZATION, id);
    }

    @Override
    public List<Organization> getOrganizationsByHero(Hero hero) {
        final String SELECT_ORGANIZATIONS_BY_HERO = "SELECT o.* " +
                "FROM organization o " +
                "INNER JOIN heroorganization ho " +
                "ON o.organizationPK = ho.organizationPK " +
                "WHERE ho.heroPK = 4";
        List<Organization> organizations = jdbc.query(SELECT_ORGANIZATIONS_BY_HERO, new OrganizationMapper(), hero.getId());
        for (Organization organization : organizations) {
            organization.setMembers(getHeroesByOrganization(organization));
        }
        return organizations;
    }

    public static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int rowNum) throws SQLException {
            Organization organization = new Organization();
            organization.setId(rs.getInt("OrganizationPK"));
            organization.setName(rs.getString("OrganizationName"));
            organization.setType(rs.getString("`Type`"));
            organization.setDescription(rs.getString("`Description`"));
            organization.setAddress(rs.getString("OrganizationAddress"));
            organization.setPhone(rs.getString("Phone"));
            organization.setContact(rs.getString("ContactInfo"));

            return organization;
        }
    }

}
