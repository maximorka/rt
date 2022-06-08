package feature.storage;

import feature.prefs.Prefs;
import org.flywaydb.core.Flyway;

public class DataBaseInit {
    public void initDB(String connectionUrl){
        Flyway flyway = Flyway
                .configure()
                .dataSource(connectionUrl,null, null).load();
        flyway.migrate();
    }
}
