package dev.langchain4j;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.Properties;
import javax.sql.DataSource;

public class DBConnectionEngine extends ConnectionPoolFactory {

  private static final String DB_USER = System.getenv("DB_USER");
  private static final String DB_PASSWORD = System.getenv("DB_PASSWORD");
  private static final String DB_NAME = System.getenv("DB_NAME");
  private static final String CONNECTION_NAME = System.getenv("POSTGRES_CONNECTION_NAME");

  public static DataSource createConnectionPool() {

    HikariConfig config = new HikariConfig();

    String jdbcURL = String.format("jdbc:postgresql:///%s", DB_NAME);
    Properties connProps = new Properties();
    connProps.setProperty("user", DB_USER);
    connProps.setProperty("password", DB_PASSWORD);
    connProps.setProperty("socketFactory", "com.google.cloud.sql.postgres.SocketFactory");
    connProps.setProperty("cloudSqlInstance", CONNECTION_NAME);
    config.setJdbcUrl(jdbcURL);
    config.setDataSourceProperties(connProps);
    configureConnectionPool(config);

    // Initialize the connection pool using the configuration object.
    return new HikariDataSource(config);
  }
}
