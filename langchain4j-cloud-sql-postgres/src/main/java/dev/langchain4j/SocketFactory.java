package dev.langchain4j;

import com.google.cloud.sql.core.InternalConnectorRegistry;
import com.google.cloud.sql.core.ConnectionConfig;
import java.util.Properties;
import java.net.Socket;
import java.io.IOException;
import java.net.InetAddress;

public class SocketFactory extends javax.net.SocketFactory{
    //private static final String SOCKET_ARG = "SocketFactoryArg";
    private static final String POSTGRES_SUFFIX = "/.s.PGSQL.5432";
  
    private final Properties props;
    
    static {
      InternalConnectorRegistry.addArtifactId("postgres-socket-factory");
    }

    public SocketFactory(Properties info) {
        //String instanceKey = info.getProperty(SOCKET_ARG);
        // if (instanceKey != null) {
        //   logger.debug(
        //       String.format(
        //           "The '%s' property has been deprecated. Please update your postgres driver and use"
        //               + "the  '%s' property in your JDBC url instead.",
        //           SOCKET_ARG, ConnectionConfig.CLOUD_SQL_INSTANCE_PROPERTY));
        //   info.setProperty(ConnectionConfig.CLOUD_SQL_INSTANCE_PROPERTY, instanceKey);
        // }
        this.props = info;
      }

      @Override
      public Socket createSocket() throws IOException {
        try {
          return InternalConnectorRegistry.getInstance()
              .connect(ConnectionConfig.fromConnectionProperties(props));
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }

      @Override
      public Socket createSocket(String host, int port, InetAddress localHost, int localPort) {
        throw new UnsupportedOperationException();
      }

      @Override
      public Socket createSocket(
          InetAddress address, int port, InetAddress localAddress, int localPort) {
        throw new UnsupportedOperationException();
      }

      @Override
      public Socket createSocket(InetAddress host, int port) {
        throw new UnsupportedOperationException();
      }

      @Override
      public Socket createSocket(String host, int port) {
        throw new UnsupportedOperationException();
      }
}
