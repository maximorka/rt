package feature.dataBaseService;

import feature.storage.Storage;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class DataBaseService {
private PreparedStatement dropTableSt;

    public void  dropTable( String nameTable){
        try {
            dropTableSt.setString(1,nameTable);
            dropTableSt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
