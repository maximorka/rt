package feature.dataBaseService.skills.entity;

import feature.dataBaseService.skills.util.SkillsLevel;
import lombok.Data;

@Data
public class Skills {
private long id;
private String kategory;
private SkillsLevel level;
}
