package feature.dataBaseService.developer.dao;

import feature.dataBaseService.developer.util.SexEnum;
import feature.dataBaseService.developer.entity.Developer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DeveloperDao {
    private PreparedStatement createSt;
    private PreparedStatement getAllSt;
    private PreparedStatement getByIdSt;
    private PreparedStatement getMaxIdSt;
    private PreparedStatement deleteByIdSt;
    private PreparedStatement updateByIdSt;

    public DeveloperDao(Connection connection) throws SQLException {

        createSt = connection.prepareStatement("INSERT INTO developer(name, surname, age, sex,company_id,salary) VALUES(?,?,?,?,?,?)");
        getAllSt = connection.prepareStatement("Select id, name, surname, age, sex, company_id,salary FROM developer");
        getByIdSt = connection.prepareStatement("Select name, surname, age, sex, company_id,salary FROM developer WHERE id = ?");
        getMaxIdSt = connection.prepareStatement("SELECT max(id) AS maxId FROM developer");
        deleteByIdSt = connection.prepareStatement("DELETE FROM developer WHERE id = ? ");
        updateByIdSt = connection.prepareStatement("UPDATE developer SET name=?, surname=?, age=?, sex=?, company_id=?,salary=? WHERE id = ?");
    }

    public long createDeveloper(Developer developer) throws SQLException {

        createSt.setString(1, developer.getName());
        createSt.setString(2, developer.getSurname());
        createSt.setInt(3, developer.getAge());
        createSt.setString(4, developer.getSex().toString());
        createSt.setLong(5, developer.getCompany_id());
        createSt.setInt(6, developer.getSalary());

        long id;
        try (ResultSet resultSet = getMaxIdSt.executeQuery()) {
            resultSet.next();
            id = resultSet.getLong("maxId");
        }

        return id;

    }

    public Developer getDeveloperById(long id) throws SQLException {
        getByIdSt.setLong(1, id);
        try (ResultSet rs = getByIdSt.executeQuery()) {

            if (!rs.next()) {
                return null;
            }
            Developer developer = new Developer();
            developer.setId(id);
            developer.setName(rs.getString("name"));
            developer.setSurname(rs.getString("surname"));
            developer.setAge(rs.getInt("age"));
            developer.setSex(SexEnum.valueOf(rs.getString("sex")));
            developer.setCompany_id(rs.getInt("company_id"));
            developer.setSalary(rs.getInt("salary"));
            return developer;
        }
    }

    public List<Developer> getAllDevelopers() throws SQLException {
        List<Developer> developers = new ArrayList<>();
        try (ResultSet rs = getAllSt.executeQuery()) {
            while (rs.next()) {
                Developer developer = new Developer();
                developer.setId(rs.getLong("id"));
                developer.setName(rs.getString("name"));
                developer.setSurname(rs.getString("surname"));
                developer.setAge(rs.getInt("age"));
                developer.setSex(SexEnum.valueOf(rs.getString("sex")));
                developer.setCompany_id(rs.getInt("company_id"));
                developer.setSalary(rs.getInt("salary"));
                developers.add(developer);
            }
             return developers;
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

    public boolean updateDeveloper(Developer developer) {

        try {
            updateByIdSt.setString(1, developer.getName());
            updateByIdSt.setString(2, developer.getSurname());
            updateByIdSt.setInt(3, developer.getAge());
            updateByIdSt.setString(4, developer.getSex().toString());
            updateByIdSt.setLong(5, developer.getCompany_id());
            updateByIdSt.setInt(6, developer.getSalary());
            updateByIdSt.setLong(7, developer.getId());
            return updateByIdSt.executeUpdate() == 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }

    }
}
