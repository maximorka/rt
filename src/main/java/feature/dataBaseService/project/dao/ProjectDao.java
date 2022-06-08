package feature.dataBaseService.project.dao;

import feature.dataBaseService.project.entity.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectDao {
    private PreparedStatement createSt;
    private PreparedStatement getAllSt;
    private PreparedStatement getByIdSt;
    private PreparedStatement getByNameSt;
    private PreparedStatement deleteByIdSt;
    private PreparedStatement updateByIdSt;
    private PreparedStatement getMaxIdSt;

    public ProjectDao(Connection connection) throws SQLException {
        createSt = connection.prepareStatement("INSERT INTO project(name, description, company_id, customer_id, cost, date) VALUES(?,?,?,?,?,?)");
        getAllSt = connection.prepareStatement("SELECT id, name, description, company_id, customer_id, cost, date FROM project");
        getByIdSt = connection.prepareStatement("SELECT name, description, company_id, customer_id, cost, date FROM project WHERE id = ?");
        getMaxIdSt = connection.prepareStatement("SELECT max(id) AS maxId FROM project");
        deleteByIdSt = connection.prepareStatement("DELETE FROM project WHERE id = ? ");
        updateByIdSt = connection.prepareStatement("UPDATE project SET name = ?, description = ?, company_id = ?, customer_id = ?, cost = ?, date = ? WHERE id = ?");

        getByNameSt = connection.prepareStatement("SELECT id, description, company_id, customer_id, cost, date FROM project WHERE name = ?");
    }

    public long createProject(Project project) throws SQLException {

        createSt.setString(1, project.getName());
        createSt.setString(2, project.getDescription());
        createSt.setLong(3, project.getCompany_id());
        createSt.setLong(4, project.getCustomer_id());
        createSt.setInt(5, project.getCost());
        createSt.setDate(6, (Date) project.getDate());
        createSt.executeUpdate();

        long id;
        try (ResultSet resultSet = getMaxIdSt.executeQuery()) {
            resultSet.next();
            id = resultSet.getLong("maxId");
        }

        return id;
    }

    public Project getProjectById(long id) throws SQLException {
        getByIdSt.setLong(1, id);
        try (ResultSet rs = getByIdSt.executeQuery()) {

            if (!rs.next()) {
                return null;
            }
            Project project = new Project();
            project.setId(id);
            project.setName(rs.getString("name"));
            project.setDescription(rs.getString("description"));
            project.setCompany_id(rs.getLong("company_id"));
            project.setCustomer_id(rs.getLong("customer_id"));
            project.setCost(rs.getInt("cost"));
            project.setDate(rs.getDate("date"));

            return project;
        }
    }

    public List<Project> getAllProject() throws SQLException {
        List<Project> projects = new ArrayList<>();
        try (ResultSet rs = getAllSt.executeQuery()) {
            while (rs.next()) {
                Project project = new Project();
                project.setId(rs.getLong("id"));
                project.setName(rs.getString("name"));
                project.setDescription(rs.getString("description"));
                project.setCompany_id(rs.getLong("company_id"));
                project.setCustomer_id(rs.getLong("customer_id"));
                project.setCost(rs.getInt("cost"));
                project.setDate(rs.getDate("date"));
                projects.add(project);
            }
        }
        return projects;
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

    public boolean updateProject(Project project) {
        try {
            updateByIdSt.setString(1, project.getName());
            updateByIdSt.setString(2, project.getDescription());
            updateByIdSt.setLong(3, project.getCompany_id());
            updateByIdSt.setLong(4, project.getCustomer_id());
            updateByIdSt.setLong(5, project.getId());
            updateByIdSt.setInt(6, project.getCost());
            updateByIdSt.setDate(7, (Date) project.getDate());
            return updateByIdSt.executeUpdate() == 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }

    }

    public Project getProjectByName(String name) throws SQLException {
        getByNameSt.setString(1, name);
        try (ResultSet rs = getByNameSt.executeQuery()) {

            if (!rs.next()) {
                return null;
            }
            Project project = new Project();
            project.setId(rs.getLong("id"));
            project.setName(name);
            project.setDescription(rs.getString("description"));
            project.setCompany_id(rs.getLong("company_id"));
            project.setCustomer_id(rs.getLong("customer_id"));
            project.setCost(rs.getInt("cost"));
            project.setDate(rs.getDate("date"));

            return project;
        }
    }
}
