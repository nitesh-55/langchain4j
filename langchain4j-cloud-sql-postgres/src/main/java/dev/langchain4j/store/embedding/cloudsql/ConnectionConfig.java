package dev.langchain4j.store.embedding.pgvector;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import com.google.common.base.Splitter;
import java.util.ArrayList;


public class ConnectionConfig {

  public static final String CLOUD_SQL_INSTANCE_PROPERTY = "cloudSqlInstance";
  public static final String CLOUD_SQL_NAMED_CONNECTOR_PROPERTY = "cloudSqlNamedConnector";
  // public static final String CLOUD_SQL_DELEGATES_PROPERTY = "cloudSqlDelegates";
  // public static final String CLOUD_SQL_TARGET_PRINCIPAL_PROPERTY = "cloudSqlTargetPrincipal";
  public static final String CLOUD_SQL_ADMIN_ROOT_URL_PROPERTY = "cloudSqlAdminRootUrl";
  // public static final String CLOUD_SQL_ADMIN_SERVICE_PATH_PROPERTY = "cloudSqlAdminServicePath";
  // public static final String CLOUD_SQL_REFRESH_STRATEGY_PROPERTY = "cloudSqlRefreshStrategy";
  public static final String UNIX_SOCKET_PROPERTY = "unixSocketPath";
  public static final String UNIX_SOCKET_PATH_SUFFIX_PROPERTY = "cloudSqlUnixSocketPathSuffix";
  public static final String ENABLE_IAM_AUTH_PROPERTY = "enableIamAuth";
  public static final String IP_TYPES_PROPERTY = "ipTypes";
  public static final String CLOUD_SQL_ADMIN_QUOTA_PROJECT_PROPERTY = "cloudSqlAdminQuotaProject";
  // public static final String CLOUD_SQL_UNIVERSE_DOMAIN = "cloudSqlUniverseDomain";
  public static final AuthType DEFAULT_AUTH_TYPE = AuthType.IAM;
  public static final String DEFAULT_IP_TYPES = "PUBLIC,PRIVATE";
  public static final List<IpType> DEFAULT_IP_TYPE_LIST =
      Arrays.asList(IpType.PUBLIC, IpType.PRIVATE);
  public static final String CLOUD_SQL_GOOGLE_CREDENTIALS_PATH = "cloudSqlGoogleCredentialsPath";

  //private final ConnectorConfig connectorConfig;
  private final String cloudSqlInstance;
  private final String namedConnector;
  private final String unixSocketPath;
  private final List<IpType> ipTypes;

  private final AuthType authType;
  private final String unixSocketPathSuffix;

  public static ConnectionConfig fromConnectionProperties(Properties props) {
    final String csqlInstanceName = props.getProperty(ConnectionConfig.CLOUD_SQL_INSTANCE_PROPERTY);
    final String namedConnection =
        props.getProperty(ConnectionConfig.CLOUD_SQL_NAMED_CONNECTOR_PROPERTY);

    final String unixSocketPath = props.getProperty(ConnectionConfig.UNIX_SOCKET_PROPERTY);
    final AuthType authType =
        Boolean.parseBoolean(props.getProperty(ConnectionConfig.ENABLE_IAM_AUTH_PROPERTY))
            ? AuthType.IAM
            : AuthType.PASSWORD;
    // final String targetPrincipal =
    //     props.getProperty(ConnectionConfig.CLOUD_SQL_TARGET_PRINCIPAL_PROPERTY);
    // final String delegatesStr = props.getProperty(ConnectionConfig.CLOUD_SQL_DELEGATES_PROPERTY);
    // final List<String> delegates;
    // if (delegatesStr != null && !delegatesStr.isEmpty()) {
    //   delegates = Arrays.asList(delegatesStr.split(","));
    // } else {
    //   delegates = Collections.emptyList();
    // }
    final List<IpType> ipTypes =
        listIpTypes(
            props.getProperty(
                ConnectionConfig.IP_TYPES_PROPERTY, ConnectionConfig.DEFAULT_IP_TYPES));
    final String adminRootUrl =
        props.getProperty(ConnectionConfig.CLOUD_SQL_ADMIN_ROOT_URL_PROPERTY);
    // final String adminServicePath =
    //     props.getProperty(ConnectionConfig.CLOUD_SQL_ADMIN_SERVICE_PATH_PROPERTY);
    final String unixSocketPathSuffix =
        props.getProperty(ConnectionConfig.UNIX_SOCKET_PATH_SUFFIX_PROPERTY);
    final String googleCredentialsPath =
        props.getProperty(ConnectionConfig.CLOUD_SQL_GOOGLE_CREDENTIALS_PATH);
    final String adminQuotaProject =
        props.getProperty(ConnectionConfig.CLOUD_SQL_ADMIN_QUOTA_PROJECT_PROPERTY);
    // final String universeDomain = props.getProperty(ConnectionConfig.CLOUD_SQL_UNIVERSE_DOMAIN);
    // final String refreshStrategyStr =
    //     props.getProperty(ConnectionConfig.CLOUD_SQL_REFRESH_STRATEGY_PROPERTY);
    // final RefreshStrategy refreshStrategy =
    //     "lazy".equalsIgnoreCase(refreshStrategyStr)
    //         ? RefreshStrategy.LAZY
    //         : RefreshStrategy.BACKGROUND;

    return new ConnectionConfig(
        csqlInstanceName, namedConnection, unixSocketPath, ipTypes, authType, unixSocketPathSuffix
        /*new ConnectorConfig.Builder()
           .withTargetPrincipal(targetPrincipal)
           .withDelegates(delegates)
           .withAdminRootUrl(adminRootUrl)
           .withAdminServicePath(adminServicePath)
        //   .withGoogleCredentialsPath(googleCredentialsPath)
        //   .withAdminQuotaProject(adminQuotaProject)
           .withUniverseDomain(universeDomain)
           .withRefreshStrategy(refreshStrategy)
           .build()*/
        );
  }

  private static List<IpType> listIpTypes(String cloudSqlIpTypes) {
    List<String> rawTypes = Splitter.on(',').splitToList(cloudSqlIpTypes);
    ArrayList<IpType> result = new ArrayList<>(rawTypes.size());
    for (String type : rawTypes) {
      if (type.trim().equalsIgnoreCase("PUBLIC")) {
        result.add(IpType.PUBLIC);
      } else if (type.trim().equalsIgnoreCase("PRIMARY")) {
        result.add(IpType.PUBLIC);
      } else if (type.trim().equalsIgnoreCase("PRIVATE")) {
        result.add(IpType.PRIVATE);
      } else if (type.trim().equalsIgnoreCase("PSC")) {
        result.add(IpType.PSC);
      } else {
        throw new IllegalArgumentException(
            "Unsupported IP type: " + type + " found in ipTypes parameter");
      }
    }
    return result;
  }

  private ConnectionConfig(
      String cloudSqlInstance,
      String namedConnector,
      String unixSocketPath,
      List<IpType> ipTypes,
      AuthType authType,
      String unixSocketPathSuffix
      // ConnectorConfig connectorConfig
      ) {
    this.cloudSqlInstance = cloudSqlInstance;
    this.namedConnector = namedConnector;
    this.unixSocketPath = unixSocketPath;
    this.ipTypes = ipTypes;
    this.unixSocketPathSuffix = unixSocketPathSuffix;
    // this.connectorConfig = connectorConfig;
    this.authType = authType;
  }

  public String getNamedConnector() {
    return namedConnector;
  }

  public String getCloudSqlInstance() {
    return cloudSqlInstance;
  }

  public String getUnixSocketPath() {
    return unixSocketPath;
  }

  public List<IpType> getIpTypes() {
    return ipTypes;
  }

  public String getUnixSocketPathSuffix() {
    return unixSocketPathSuffix;
  }

  //   public ConnectorConfig getConnectorConfig() {
  //     return connectorConfig;
  //   }

  public AuthType getAuthType() {
    return authType;
  }

  public enum AuthType {
    IAM,
    PASSWORD
  }

  public enum IpType {
    PUBLIC,
    PRIVATE,
    PSC;
  }
}
