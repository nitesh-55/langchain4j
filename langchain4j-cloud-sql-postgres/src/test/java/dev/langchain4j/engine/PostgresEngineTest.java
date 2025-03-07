// package dev.langchain4j.engine;

// import static com.google.common.truth.Truth.assertThat;
// import static org.assertj.core.api.Assertions.assertThat;

// import com.zaxxer.hikari.HikariDataSource;
// import java.sql.*;
// import java.sql.Connection;
// import java.sql.ResultSet;
// import java.sql.ResultSetMetaData;
// import java.sql.SQLException;
// import java.util.HashSet;
// import java.util.Set;
// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.Test;

// public class PostgresEngineTest {

//   private static final String TABLE_NAME = "JAVA_ENGINE_TEST_TABLE";
//   private static final String CUSTOM_TABLE_NAME = "JAVA_ENGINE_TEST_CUSTOM_TABLE";
//   private static final Integer VECTOR_SIZE = 768;
//   private static String IAM_EMAIL;
//   private static String projectId;
//   private static String region;
//   // private static String cluster;
//   private static String instance;
//   private static String database;
//   private static String user;
//   private static String password;

//   private static PostgresEngine engine;
//   private static Connection defaultConnection;
//   private static HikariDataSource connectionPool;
//   private static EmbeddingStoreConfig defaultParameters;
//   private static final String CUSTOM_SCHEMA = "custom_schema";

//   //   @Before
//   //   public void setUpPool() throws SQLException {

//   //     String jdbcURL = String.format("jdbc:postgresql:///%s", "postgres");

//   //     Properties connProps = new Properties();

//   //     connProps.setProperty("user", "postgres");
//   //     connProps.setProperty("password", "my-test-password");
//   //     connProps.setProperty("socketFactory", "com.google.cloud.sql.postgres.SocketFactory");
//   //     connProps.setProperty("cloudSqlInstance",
//   // "senseai-framework-testing:us-central1:my-instance");

//   //     // Initialize connection pool
//   //     HikariConfig config = new HikariConfig();
//   //     config.setJdbcUrl(jdbcURL);
//   //     config.setDataSourceProperties(connProps);
//   //     config.setConnectionTimeout(10000); // 10s

//   //     this.connectionPool = new HikariDataSource(config);
//   //   }

//   @BeforeAll
//   public static void beforeAll() throws SQLException {
//     // projectId = System.getenv("POSTGRES_PROJECT_ID");
//     // region = System.getenv("POSTGRES_REGION");
//     // // cluster = System.getenv("POSTGRES_CLUSTER");
//     // instance = System.getenv("POSTGRES_INSTANCE");
//     // database = System.getenv("POSTGRES_DB_NAME");
//     // user = System.getenv("POSTGRES_USER");
//     // password = System.getenv("POSTGRES_PASSWORD");
//     // IAM_EMAIL = System.getenv("POSTGRES_IAM_EMAIL");

//     projectId = "devshop-mosaic-11010494";
//     region = "us-central1";
//     // cluster = System.getenv("POSTGRES_CLUSTER");
//     instance = "senseai-framework-testing:us-central1:my-instance";
//     database = "my-instance";
//     user = "postgres";
//     password = "my-test-password";
//     // IAM_EMAIL = System.getenv("POSTGRES_IAM_EMAIL");

//     //     export ALLOYDB_USER=postgres
//     // export ALLOYDB_PASSWORD=alloydbtest
//     // export ALLOYDB_PROJECT_ID=devshop-mosaic-11010494
//     // export ALLOYDB_REGION=us-central1
//     // export ALLOYDB_CLUSTER=senseai-alloydb-cluster
//     // export ALLOYDB_INSTANCE=senseai-alloydb-cluster-primary
//     // export ALLOYDB_DB_NAME=test
//     // export ALLOYDB_IAM_EMAIL=<your-account-email>

//     // String jdbcURL = String.format("jdbc:postgresql:///%s", "postgres");

//     // Properties connProps = new Properties();

//     // connProps.setProperty("user", "postgres");
//     // connProps.setProperty("password", "my-test-password");
//     // connProps.setProperty("socketFactory", "com.google.cloud.sql.postgres.SocketFactory");
//     // connProps.setProperty("cloudSqlInstance",
//     // "senseai-framework-testing:us-central1:my-instance");

//     // // Initialize connection pool
//     // HikariConfig config = new HikariConfig();
//     // config.setJdbcUrl(jdbcURL);
//     // config.setDataSourceProperties(connProps);
//     // config.setConnectionTimeout(10000); // 10s

//     // this.connectionPool = new HikariDataSource(config);

//     engine =
//         PostgresEngine.builder()
//             .projectId(projectId)
//             .region(region)
//             // .cluster(cluster)
//             .instance(instance)
//             .database(database)
//             .user(user)
//             .password(password)
//             .ipType("PUBLIC")
//             .build();

//     defaultConnection = engine.getConnection();
//     defaultConnection
//         .createStatement()
//         .executeUpdate(String.format("CREATE SCHEMA IF NOT EXISTS \"%s\"", CUSTOM_SCHEMA));

//     defaultParameters =
//         EmbeddingStoreConfig.builder().tableName(TABLE_NAME).vectorSize(VECTOR_SIZE).build();
//   }

//   private void verifyColumns(String tableName, Set<String> expectedColumns) throws SQLException {
//     Set<String> actualNames = new HashSet<>();

//     try (ResultSet resultSet =
//         engine.getConnection().createStatement().executeQuery("SELECT * FROM " + tableName)) {
//       ResultSetMetaData rsMeta = resultSet.getMetaData();
//       int columnCount = rsMeta.getColumnCount();
//       for (int i = 1; i <= columnCount; i++) {
//         actualNames.add(rsMeta.getColumnName(i));
//       }
//       assertThat(actualNames).isEqualTo(expectedColumns);
//     }
//   }

//   @Test
//   void initialize_vector_table_with_default_schema() throws SQLException {
//     // default options
//     engine.initVectorStoreTable(defaultParameters);

//     Set<String> expectedNames = new HashSet<>();

//     expectedNames.add("langchain_id");
//     expectedNames.add("content");
//     expectedNames.add("embedding");

//     verifyColumns(TABLE_NAME, expectedNames);

//     // verifyIndex(TABLE_NAME, "hnsw", "USING hnsw (embedding vector_l2_ops)");
//   }
// }
