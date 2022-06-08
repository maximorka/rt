package feature.dataBaseService.developerSkills.dao;

import feature.dataBaseService.developerSkills.entity.DeveloperSkills;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeveloperSkillsDao {
    private PreparedStatement createSt;
    private PreparedStatement getAllSt;
    private PreparedStatement getDev_idBySkills_idSt;
    private PreparedStatement deleteByIdSt;
    private PreparedStatement updateByIdSt;

    public DeveloperSkillsDao(Connection connection) throws SQLException {
        createSt = connection.prepareStatement("INSERT INTO developer_skills(dev_id, skills_id) VALUES(?,?)");
        getAllSt = connection.prepareStatement("SELECT dev_id, skills_id  FROM developer_skills");
        getDev_idBySkills_idSt = connection.prepareStatement("SELECT dev_id FROM developer_skills WHERE skills_id = ?");
        deleteByIdSt = connection.prepareStatement("DELETE FROM developer_skills WHERE dev_id=? AND skills_id=?");
        updateByIdSt = connection.prepareStatement("UPDATE developer_skills SET dev_id=?, skills_id=? WHERE dev_id=? AND skills_id=?");
    }

    public boolean createDeveloperProject(DeveloperSkills developerSkills) throws SQLException {
        createSt.setLong(1, developerSkills.getDevId());
        createSt.setLong(2, developerSkills.getSkillsId());
        return  createSt.executeUpdate()>0;
    }


    public List<DeveloperSkills> getAllDeveloperProject() throws SQLException {
        List<DeveloperSkills> developerSkills = new ArrayList<>();
        try (ResultSet rs = getAllSt.executeQuery()) {
            while (rs.next()) {
                DeveloperSkills developerSkill = new DeveloperSkills();
                developerSkill.setDevId(rs.getLong("dev_id"));
                developerSkill.setSkillsId(rs.getLong("skills_id"));
                developerSkills.add(developerSkill);
            }
            return developerSkills;
        }
    }
    public List<Long> getDev_idBySkills_id(long id) throws SQLException {
        List<Long> dev_idsList = new ArrayList<>();
        getDev_idBySkills_idSt.setLong(1,id);
        try (ResultSet rs = getDev_idBySkills_idSt.executeQuery()) {
            while (rs.next()) {
                dev_idsList.add(rs.getLong("dev_id"));
            }
            return dev_idsList;
        }
    }
}
