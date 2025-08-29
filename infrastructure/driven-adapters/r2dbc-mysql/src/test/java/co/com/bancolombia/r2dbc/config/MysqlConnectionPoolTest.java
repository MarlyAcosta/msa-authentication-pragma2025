package co.com.bancolombia.r2dbc.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MysqlConnectionPoolTest {

    private MysqlConnectionPool connectionPool;
    private MysqlConnectionProperties properties;

    @BeforeEach
    void setUp() {
        connectionPool = new MysqlConnectionPool();
        properties = mock(MysqlConnectionProperties.class);

        when(properties.url()).thenReturn("r2dbc:mysql://alex:123456@192.168.1.10:3306/pragma");
    }

    @Test
    void getConnectionConfigSuccess() {
        assertNotNull(connectionPool.connectionPool(properties));
    }
}