package dev.langchain4j;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import java.util.Properties;


public class DBConnectionEngine extends ConnectionPoolFactory {

  private static final String DB_USER = System.getenv("DB_USER");
  private static final String DB_PASSWORD = System.getenv("DB_PASSWORD");
  private static final String DB_NAME = System.getenv("DB_NAME");
  private static final String INSTANCE_HOST = System.getenv("INSTANCE_HOST");
  private static final String DB_PORT = System.getenv("DB_PORT");
  private static final String PROJECT_ID = System.getenv("PROJECT_ID");
  private static final String REGION = System.getenv("us-central-1");
  private static final String CLUSTER_ID = System.getenv("CLUSTER_ID");
  private static final String CONNECTION_NAME = "my-project:my-region:my-instance";

  public static DataSource createConnectionPool() {

    HikariConfig config = new HikariConfig();

    String jdbcURL = String.format("jdbc:postgresql:///%s", DB_NAME);
    Properties connProps = new Properties();
    connProps.setProperty("user", DB_USER);
    connProps.setProperty("password", DB_PASSWORD);
    connProps.setProperty("socketFactory", "com.google.cloud.sql.postgres.SocketFactory");
    connProps.setProperty("cloudSqlInstance", CONNECTION_NAME);

    configureConnectionPool(config);
    
    // Initialize the connection pool using the configuration object.
    return new HikariDataSource(config);
  }
}
