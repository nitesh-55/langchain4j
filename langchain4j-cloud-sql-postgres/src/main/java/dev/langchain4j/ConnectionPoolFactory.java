package dev.langchain4j;

import com.zaxxer.hikari.HikariConfig;

public class ConnectionPoolFactory {

  public static HikariConfig configureConnectionPool(HikariConfig config) {

    // maximumPoolSize limits the total number of concurrent connections this pool will keep.
    config.setMaximumPoolSize(5);
    // minimumIdle is the minimum number of idle connections Hikari maintains in the pool.
    config.setMinimumIdle(5);

    // setConnectionTimeout is the maximum number of milliseconds to wait for a connection checkout.
    // Any attempt to retrieve a connection from this pool that exceeds the set limit will throw an
    // SQLException.
    config.setConnectionTimeout(10000); // 10 seconds
    // idleTimeout is the maximum amount of time a connection can sit in the pool. Connections that
    // sit idle for this many milliseconds are retried if minimumIdle is exceeded.
    config.setIdleTimeout(600000); // 10 minutes

    // maxLifetime is the maximum possible lifetime of a connection in the pool. Connections that
    // live longer than this many milliseconds will be closed and reestablished between uses. 
    config.setMaxLifetime(1800000); // 30 minutes
    
    return config;
  }
}
