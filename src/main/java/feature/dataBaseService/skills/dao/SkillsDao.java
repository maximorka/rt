package feature.dataBaseService.skills.dao;

import feature.dataBaseService.skills.util.SkillsLevel;
import feature.dataBaseService.skills.entity.Skills;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SkillsDao {

    private PreparedStatement createSt;
    private PreparedStatement getByIdSt;
    private PreparedStatement selectMaxIdSt;
    private PreparedStatement getAllSt;
    private PreparedStatement deleteByIdSt;
    private PreparedStatement updateByIdSt;

    public SkillsDao(Connection connection) throws SQLException {
        createSt = connection.prepareStatement("INSERT INTO skills(kategory , level) VALUES(?,?)");
        getByIdSt = connection.prepareStatement("Select kategory, level FROM skills WHERE id = ?");
        selectMaxIdSt = connection.prepareStatement("SELECT max(id) As maxId FROM skills");
        getAllSt = connection.prepareStatement("Select id, kategory, level FROM skills");
        deleteByIdSt = connection.prepareStatement("DELETE FROM skills WHERE id = ? ");
        updateByIdSt = connection.prepareStatement("UPDATE skills SET kategory = ?, level = ? WHERE id = ?");
    }

    public long create(Skills skills) throws SQLException {

        createSt.setString(1, skills.getKategory());
        createSt.setString(2, skills.getLevel().toString());

        createSt.executeUpdate();
        long id = -1;
        try (ResultSet resultSet = selectMaxIdSt.executeQuery()) {
            resultSet.next();
            id = resultSet.getLong("maxId");
        }

        return id;

    }

    public Skills getById(long id) throws SQLException {

        getByIdSt.setLong(1, id);
        try (ResultSet rs = getByIdSt.executeQuery()) {

            if (!rs.next()) {
                return null;
            }
            Skills skills = new Skills();
            skills.setId(id);
            skills.setKategory(rs.getString("kategory"));
            skills.setLevel( SkillsLevel.valueOf(rs.getString("level")));
            return skills;
        }
    }

    public List<Skills> getAllSkills() throws SQLException {
        List<Skills> skills = new ArrayList<>();
        try (ResultSet rs = getAllSt.executeQuery()) {
            while (rs.next()) {
                Skills skill = new Skills();
                skill.setId(rs.getLong("id"));
                skill.setKategory(rs.getString("kategory"));
                skill.setLevel( SkillsLevel.valueOf(rs.getString("level")));
                skills.add(skill);
            }
            return skills;
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

    public boolean updateSkills(Skills skills) {
        try {
            updateByIdSt.setString(1, skills.getKategory());
            updateByIdSt.setString(2, skills.getLevel().toString());
            updateByIdSt.setLong(3, skills.getId());
            return updateByIdSt.executeUpdate() == 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }

    }
}
