package feature.dataBaseService.developer.entity;

import feature.dataBaseService.developer.util.SexEnum;
import lombok.Data;

@Data
public class Developer {
private long id;
private String name;
private String surname;
private int age;
private SexEnum sex;
private int company_id;
private int salary;
}
