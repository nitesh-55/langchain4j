// package dev.langchain4j;

// import static com.google.common.truth.Truth.assertThat;
// import static com.google.common.truth.Truth.assertWithMessage;

// import com.google.common.collect.ImmutableList;
// import com.zaxxer.hikari.HikariConfig;
// import com.zaxxer.hikari.HikariDataSource;
// import java.sql.*;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Properties;
// import org.junit.Before;
// import org.junit.BeforeClass;
// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.junit.runners.JUnit4;

// @RunWith(JUnit4.class)
// public class DBConnectionEngineTest {

//   private static final String CONNECTION_NAME = System.getenv("POSTGRES_CONNECTION_NAME");
//   private static final String DB_NAME = System.getenv("POSTGRES_DB");
//   private static final String DB_USER = System.getenv("POSTGRES_USER");
//   private static final String DB_PASSWORD = System.getenv("POSTGRES_PASS");
//   private static final ImmutableList<String> requiredEnvVars =
//       ImmutableList.of("POSTGRES_USER", "POSTGRES_PASS", "POSTGRES_DB",
// "POSTGRES_CONNECTION_NAME");

//   private HikariDataSource connectionPool;

//   @BeforeClass
//   public static void checkEnvVars() {
//     // check that required env vars are set
//     requiredEnvVars.forEach(
//         (varName) ->
//             assertWithMessage(
//                     String.format(
//                         "Environment variable '%s' must be set to perform these tests.",
// varName))
//                 .that(System.getenv(varName))
//                 .isNotEmpty());
//   }

//   @Before
//   public void setUpPool() throws SQLException {

//     String jdbcURL = String.format("jdbc:postgresql:///%s", DB_NAME);

//     Properties connProps = new Properties();

//     connProps.setProperty("user", DB_USER);
//     connProps.setProperty("password", DB_PASSWORD);
//     connProps.setProperty("socketFactory", "com.google.cloud.sql.postgres.SocketFactory");
//     connProps.setProperty("cloudSqlInstance", CONNECTION_NAME);

//     // Initialize connection pool
//     HikariConfig config = new HikariConfig();
//     config.setJdbcUrl(jdbcURL);
//     config.setDataSourceProperties(connProps);
//     config.setConnectionTimeout(10000); // 10s

//     this.connectionPool = new HikariDataSource(config);
//   }

//   @Test
//   public void pooledConnectionTest() throws SQLException {

//     List<Timestamp> rows = new ArrayList<>();
//     try (Connection conn = connectionPool.getConnection()) {
//       try (PreparedStatement selectStmt = conn.prepareStatement("SELECT NOW() as TS")) {
//         ResultSet rs = selectStmt.executeQuery();
//         while (rs.next()) {
//           rows.add(rs.getTimestamp("TS"));
//         }
//       }
//     }
//     assertThat(rows.size()).isEqualTo(1);
//   }
// }
