package feature.dataBaseService.company.dao;

import feature.dataBaseService.company.entity.Company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyDao {

    private PreparedStatement createSt;
    private PreparedStatement getByIdSt;
    private PreparedStatement selectMaxIdSt;
    private PreparedStatement getAllSt;
    private PreparedStatement deleteByIdSt;
    private PreparedStatement updateByIdSt;

    public CompanyDao(Connection connection) throws SQLException {
        createSt = connection.prepareStatement("INSERT INTO company(name, address) VALUES(?,?)");
        getByIdSt = connection.prepareStatement("Select name, address FROM company WHERE id = ?");
        selectMaxIdSt = connection.prepareStatement("SELECT max(id) As maxId FROM company");
        getAllSt = connection.prepareStatement("Select id, name, address FROM company");
        deleteByIdSt = connection.prepareStatement("DELETE FROM company WHERE id = ? ");
        updateByIdSt = connection.prepareStatement("UPDATE company SET name = ?, address = ? WHERE id = ?");
    }

    public long create(Company company) throws SQLException {

        createSt.setString(1, company.getName());
        createSt.setString(2, company.getAddress());

        createSt.executeUpdate();
        long id;
        try (ResultSet resultSet = selectMaxIdSt.executeQuery()) {
            resultSet.next();
            id = resultSet.getLong("maxId");
        }
        return id;
    }

    public Company getById(long id) throws SQLException {

        getByIdSt.setLong(1, id);
        try (ResultSet rs = getByIdSt.executeQuery()) {

            if (!rs.next()) {
                return null;
            }
            Company company = new Company();
            company.setId(id);
            company.setName(rs.getString("name"));
            company.setAddress(rs.getString("address"));
            return company;
        }
    }

    public List<Company> getAllCompany() throws SQLException {
        List<Company> companies = new ArrayList<>();
        try (ResultSet rs = getAllSt.executeQuery()) {
            while (rs.next()) {
                Company company = new Company();
                company.setId(rs.getLong("id"));
                company.setName(rs.getString("name"));
                company.setAddress(rs.getString("address"));
                companies.add(company);
            }
            return companies;
        }
    }

    public boolean deleteById(long id) {
        try {
            deleteByIdSt.setLong(1, id);
            return deleteByIdSt.executeUpdate() == 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }

    }

    public boolean updateCompany(Company newCompany) {
        try {
            updateByIdSt.setString(1, newCompany.getName());
            updateByIdSt.setString(2, newCompany.getAddress());
            updateByIdSt.setLong(3, newCompany.getId());
            return updateByIdSt.executeUpdate() == 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }

    }
}
