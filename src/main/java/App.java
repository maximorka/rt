import feature.dataBaseService.developer.entity.Developer;
import feature.dataBaseService.developer.dao.DeveloperDao;
import feature.dataBaseService.developerSkills.dao.DeveloperSkillsDao;
import feature.dataBaseService.developer_project.dao.DeveloperProjectDao;
import feature.dataBaseService.project.entity.Project;
import feature.dataBaseService.project.dao.ProjectDao;
import feature.dataBaseService.skills.entity.Skills;
import feature.dataBaseService.skills.dao.SkillsDao;
import feature.prefs.Prefs;
import feature.storage.DataBaseInit;
import feature.storage.Storage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class App {
    Storage storage;
    ProjectDao projectDao;
    DeveloperProjectDao developerProjectDao;
    DeveloperDao developerDao;
    SkillsDao skillsDao;
    DeveloperSkillsDao developerSkillsDao;

    public static void main(String[] args) {
        App app = new App();

        try {
            app.settings();
            System.out.println("Salary project: " + app.getSalaryForProject("Cookie"));
            System.out.println("DevelopersForProject(\"Cookie\") = " + app.getDevelopersForProject("Cookie"));
            System.out.println("DevelopersBySkills(\"Java\") = " + app.getDevelopersByTechnology("Java"));
            System.out.println("DevelopersByLevel(\"middle\") = " + app.getDevelopersByLevel("middle"));
            System.out.println("ProjectFormated() = " + app.getProjectFormated());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void settings() throws SQLException {
        String connectionUrl = new Prefs().getString(Prefs.DATABASE_CONNECTION_URL);
        new DataBaseInit().initDB(connectionUrl);
        storage = Storage.getINSTANCE();
        Connection connection = storage.getConnection();
        projectDao = new ProjectDao(connection);
        developerProjectDao = new DeveloperProjectDao(connection);
        developerDao = new DeveloperDao(connection);
        skillsDao = new SkillsDao(connection);
        developerSkillsDao = new DeveloperSkillsDao(connection);

    }

    public int getSalaryForProject(String nameProject) throws SQLException {
        int salary = 0;
        Project project = projectDao.getProjectByName(nameProject);
        List<Long> list = developerProjectDao.getDeveloperByProject(project.getId());
        for (Long aLong : list) {
            salary += developerDao.getDeveloperById(aLong).getSalary();
        }
        return salary;
    }

    public List<Developer> getDevelopersForProject(String nameProject) throws SQLException {
        List<Developer> developers = new ArrayList<>();
        Project project = projectDao.getProjectByName(nameProject);
        List<Long> list = developerProjectDao.getDeveloperByProject(project.getId());

        for (Long aLong : list) {
            developers.add(developerDao.getDeveloperById(aLong));
        }
        return developers;
    }

    public List<Developer> getDevelopersByTechnology(String skill) throws SQLException {
        List<Developer> developers = new ArrayList<>();
        List<Long> listIdSkill = getIdSkillsByTechnology(skill);
        for (Long aLong : listIdSkill) {
            developers.addAll(getDeveloperBySkillId(aLong));
        }
        return developers;
    }

    public List<Developer> getDevelopersByLevel(String level) throws SQLException {
        List<Developer> developers = new ArrayList<>();
        List<Long> listIdSkill = getIdSkillsByLevel(level);
        for (Long aLong : listIdSkill) {
            developers.addAll(getDeveloperBySkillId(aLong));
        }
        return developers;
    }

    private List<Long> getIdSkillsByTechnology(String skill) throws SQLException {
        List<Long> idSkills = new ArrayList<>();
        List<Skills> skills = skillsDao.getAllSkills();
        for (Skills curSkill : skills) {
            if (curSkill.getKategory().equals(skill))
                idSkills.add(curSkill.getId());
        }
        return idSkills;
    }

    private List<Long> getIdSkillsByLevel(String level) throws SQLException {
        List<Long> idLevelList = new ArrayList<>();
        List<Skills> skills = skillsDao.getAllSkills();
        for (Skills curSkill : skills) {
            if (curSkill.getLevel().toString().equals(level))
                idLevelList.add(curSkill.getId());
        }
        return idLevelList;
    }

    private List<Developer> getDeveloperBySkillId(Long id) throws SQLException {
        List<Developer> devList = new ArrayList<>();
        List<Long> devId = new DeveloperSkillsDao(storage.getConnection()).getDev_idBySkills_id(id);

        for (Long aLong : devId) {
            devList.add(developerDao.getDeveloperById(aLong));
        }
        return devList;
    }

    private List<String> getProjectFormated() throws SQLException {
        List<String> projectsFormated = new ArrayList<>();
        List<Project> projects = projectDao.getAllProject();


        for (Project project : projects) {
            List<Long> developerByProject = developerProjectDao.getDeveloperByProject(project.getId());

            projectsFormated.add(project.getDate() + " - " + project.getName() + " - " + developerByProject.size());
        }
        return projectsFormated;
    }


}
