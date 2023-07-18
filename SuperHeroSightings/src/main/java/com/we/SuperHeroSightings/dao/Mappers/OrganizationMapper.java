package com.we.SuperHeroSightings.dao.Mappers;

import com.we.SuperHeroSightings.entities.Organization;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class OrganizationMapper implements RowMapper<Organization> {
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
