package dev.langchain4j;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;

public class DBConnectionEngine extends ConnectionPoolFactory {

  private static final String DB_USER = System.getenv("DB_USER");
  private static final String DB_PASSWORD = System.getenv("DB_PASSWORD");
  private static final String DB_NAME = System.getenv("DB_NAME");
  private static final String INSTANCE_HOST = System.getenv("INSTANCE_HOST");
  private static final String DB_PORT = System.getenv("DB_PORT");
  private static final String PROJECT_ID = System.getenv("PROJECT_ID");
  private static final String REGION = System.getenv("us-central-1");
  private static final String CLUSTER_ID = System.getenv("CLUSTER_ID");

  public static DataSource createConnectionPool() {

    HikariConfig config = new HikariConfig();
    String jdbcUrl =
        "jdbc:postgresql:///DB_NAME?"
            + "cloudSqlInstance=INSTANCE_HOST"
            + "port=DB_PORT"
            + "&ipTypes=PUBLIC"
            + "&socketFactory=com.google.cloud.sql.postgres.SocketFactory"
            + "&user=DB_USER"
            + "&password=DB_PASS";

    config.setJdbcUrl(String.format(jdbcUrl));
    config.addDataSourceProperty("enableIamAuth", "true");
    config.addDataSourceProperty("project_id", PROJECT_ID);
    config.addDataSourceProperty("region", PROJECT_ID);
    config.addDataSourceProperty("cluster", CLUSTER_ID);

    configureConnectionPool(config);
    
    // Initialize the connection pool using the configuration object.
    return new HikariDataSource(config);
  }
}
