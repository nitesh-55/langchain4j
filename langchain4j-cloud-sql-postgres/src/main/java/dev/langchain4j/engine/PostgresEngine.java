package dev.langchain4j.engine;

import static dev.langchain4j.utils.HelperUtils.*;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;
import javax.sql.DataSource;

public class PostgresEngine {

  private DataSource dataSource;

  public PostgresEngine(
      String projectId,
      String region,
      String instance,
      String database,
      String user,
      String password,
      String ipType,
      String iamAccountEmail) {
    Boolean enableIAMAuth = false;
    if (user != null && !user.isBlank() && password != null && !password.isBlank()) {
      enableIAMAuth = false;
    } else {
      enableIAMAuth = true;
      if (iamAccountEmail != null && !iamAccountEmail.isBlank()) {
        user = iamAccountEmail;
      } else {
        // to be implemented
        user = getIAMPrincipalEmail();
      }
    }
    String instanceName =
        new StringBuilder(isNotBlank(projectId, "projectId"))
            .append(":")
            .append(isNotBlank(region, "region"))
            .append(":")
            .append(isNotBlank(instance, "instance"))
            .toString();
    dataSource = createDataSource(database, user, password, instanceName, ipType, enableIAMAuth);
  }

  private HikariDataSource createDataSource(
      String database,
      String user,
      String password,
      String instanceName,
      String ipType,
      Boolean enableIAMAuth) {
    HikariConfig config = new HikariConfig();
    config.setUsername(isNotBlank(user, "user"));
    if (enableIAMAuth) {
      config.addDataSourceProperty("enableIAMAuth", "true");
    } else {
      config.setPassword(isNotBlank(password, "password"));
    }
    config.setJdbcUrl(String.format("jdbc:postgresql:///%s", isNotBlank(database, "database")));
    config.addDataSourceProperty("socketFactory", "com.google.cloud.postgres.SocketFactory");
    config.addDataSourceProperty("cloudSqlInstance", isNotBlank(instanceName, "instanceName"));
    config.addDataSourceProperty("ipType", isNotBlank(ipType, "ipType"));

    return new HikariDataSource(config);
  }

  private String getIAMPrincipalEmail() {
    // to be implemented
    return "";
  }

  public Connection getConnection() throws SQLException {
    return dataSource.getConnection();
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private String projectId;
    private String region;
    private String instance;
    private String database;
    private String user;
    private String password;
    private String ipType;
    private String iamAccountEmail;

    public Builder() {}

    public Builder projectId(String projectId) {
      this.projectId = projectId;
      return this;
    }

    public Builder region(String region) {
      this.region = region;
      return this;
    }

    public Builder instance(String instance) {
      this.instance = instance;
      return this;
    }

    public Builder database(String database) {
      this.database = database;
      return this;
    }

    public Builder user(String user) {
      this.user = user;
      return this;
    }

    public Builder password(String password) {
      this.password = password;
      return this;
    }

    public Builder ipType(String ipType) {
      this.ipType = ipType;
      return this;
    }

    public Builder iamAccountEmail(String iamAccountEmail) {
      this.iamAccountEmail = iamAccountEmail;
      return this;
    }

    PostgresEngine build() {
      return new PostgresEngine(
          projectId, region, instance, database, user, password, ipType, iamAccountEmail);
    }
  }

  public void initVectorStoreTable(PostgresEmbeddingStoreConfig postgresEmbeddingStoreConfig) {

    try (Connection connection = getConnection(); ) {
      Statement statement = connection.createStatement();
      statement.executeUpdate("CREATE EXTENSION IF NOT EXISTS vector");

      if (postgresEmbeddingStoreConfig.getOverwriteExisting()) {
        statement.executeUpdate(
            String.format(
                "DROP TABLE \"%s\".\"%s\"",
                postgresEmbeddingStoreConfig.getSchemaName(),
                postgresEmbeddingStoreConfig.getTableName()));
      }

      String metadataClause = "";
      if (postgresEmbeddingStoreConfig.getMetadataColumns() != null
          && !postgresEmbeddingStoreConfig.getMetadataColumns().isEmpty()) {
        if (!postgresEmbeddingStoreConfig.getStoreMetadata()) {
          throw new IllegalStateException(
              "storeMetadata option is disabled but metadata was provided");
        }
        metadataClause =
            String.format(
                ", %s",
                postgresEmbeddingStoreConfig.getMetadataColumns().stream()
                    .map(MetadataColumn::generateColumnString)
                    .collect(Collectors.joining(",")));
      } else if (postgresEmbeddingStoreConfig.getStoreMetadata()) {
        throw new IllegalStateException(
            "storeMetadata option is enabled but no metadata was provided");
      }

      if (postgresEmbeddingStoreConfig.getStoreMetadata()) {
        metadataClause +=
            String.format(
                ", %s",
                new MetadataColumn(
                        postgresEmbeddingStoreConfig.getMetadataJsonColumn(), "JSON", true)
                    .generateColumnString());
      }

      String query =
          String.format(
              "CREATE \"%s\".\"%s\" (\"%s\" UUID PRIMARY KEY, \"%s\" TEXT NOT NULL, \"%s\""
                  + " vector(%d) NOT NULL%s)",
              postgresEmbeddingStoreConfig.getSchemaName(),
              postgresEmbeddingStoreConfig.getTableName(),
              postgresEmbeddingStoreConfig.getIdColumn(),
              postgresEmbeddingStoreConfig.getContentColumn(),
              postgresEmbeddingStoreConfig.getEmbeddingColumn(),
              isGreaterThanZero(postgresEmbeddingStoreConfig.getVectorSize(), "vectorSize"),
              metadataClause);
      statement.executeUpdate(query);

    } catch (SQLException ex) {
      throw new RuntimeException(
          String.format(
              "Failed to initialize vector store table: %s",
              postgresEmbeddingStoreConfig.getTableName()),
          ex);
    }
  }
}
