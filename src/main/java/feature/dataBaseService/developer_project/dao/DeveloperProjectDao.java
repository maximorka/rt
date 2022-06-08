package feature.dataBaseService.developer_project.dao;

import feature.dataBaseService.developer_project.entity.DeveloperProject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeveloperProjectDao {
    private PreparedStatement createSt;
    private PreparedStatement getAllSt;
    private PreparedStatement getByProject_idSt;
    private PreparedStatement deleteByIdSt;
    private PreparedStatement updateByIdSt;

    public DeveloperProjectDao(Connection connection) throws SQLException {
        createSt = connection.prepareStatement("INSERT INTO developer_project(develop_id, project_id ) VALUES(?,?)");
        getAllSt = connection.prepareStatement("Select develop_id, project_id FROM developer_project");
        getByProject_idSt = connection.prepareStatement("SELECT develop_id FROM developer_project WHERE project_id =?");

        deleteByIdSt = connection.prepareStatement("DELETE FROM developer_project WHERE develop_id=? AND project_id=?");
        updateByIdSt = connection.prepareStatement("UPDATE developer_project SET develop_id=?, project_id=? WHERE develop_id=? AND project_id=?");
    }

    public boolean createDeveloperProject(DeveloperProject developerProject) throws SQLException {
        createSt.setLong(1, developerProject.getDevelop_id());
        createSt.setLong(2, developerProject.getProject_id());
         return  createSt.executeUpdate()>0;
    }


    public List<DeveloperProject> getAllDeveloperProject() throws SQLException {
        List<DeveloperProject> developerProjects = new ArrayList<>();
        try (ResultSet rs = getAllSt.executeQuery()) {
            while (rs.next()) {
                DeveloperProject developerProject = new DeveloperProject();
                developerProject.setDevelop_id(rs.getLong("develop_id"));
                developerProject.setProject_id(rs.getLong("project_id"));
                developerProjects.add(developerProject);
            }
            return developerProjects;
        }
    }

    public List<Long> getDeveloperByProject(long project_id) throws SQLException {
       List<Long> listDevelopers = new ArrayList<>();
        getByProject_idSt.setLong(1,project_id);

        try(ResultSet rs = getByProject_idSt.executeQuery()){
            while (rs.next()){
                listDevelopers.add(rs.getLong("develop_id"));
            }
        }
        return listDevelopers;
    }
}
