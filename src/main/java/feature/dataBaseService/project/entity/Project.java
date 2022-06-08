package feature.dataBaseService.project.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Project {
private long id;
private String name;
private String description;
private long company_id;
private long customer_id;
private int cost;
private Date date;
}
