
package com.we.SuperHeroSightings.dao;


import com.we.SuperHeroSightings.dao.Mappers.OrganizationMapper;
import com.we.SuperHeroSightings.entities.Hero;
import com.we.SuperHeroSightings.entities.Organization;
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
 * @author Gabriela Gutierrez
 */
@Repository
public class OrganizationDaoDB implements OrganizationDao {
    
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Organization getOrganizationByID(int id) {
        try{
            final String SELECT_ORGANIZATION_BY_ID = "SELECT *\n" +
                    "FROM `organization`\n" +
                    "WHERE OrganizationPK = ?;";
            return jdbc.queryForObject(SELECT_ORGANIZATION_BY_ID, new OrganizationMapper(), id);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Organization> getAllOrganizations() {
        final String SELECT_ALL_ORGANIZATIONS = "SELECT * FROM `organization`;";
        return jdbc.query(SELECT_ALL_ORGANIZATIONS, new OrganizationMapper());
    }

    @Override
    public Organization addOrganization(Organization organization) {
        final String INSERT_NEW_ORGANIZATION = "";
        return organization;
    }

    @Override
    public void updateOrganization(Organization organization) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteOrganizationByID(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Organization> getOrganizationsByHero(Hero hero) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
