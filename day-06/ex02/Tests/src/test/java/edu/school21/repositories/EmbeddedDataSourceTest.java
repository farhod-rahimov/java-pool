package edu.school21.repositories;

import static   org.junit.jupiter.api.Assertions.*;
import          org.junit.jupiter.api.*;
import          org.springframework.jdbc.datasource.embedded.*;
import          javax.sql.*;
import          java.sql.*;


public class EmbeddedDataSourceTest {
    private EmbeddedDatabase db;

    @BeforeEach
    public void init() {
        this.db = new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.HSQL)
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
    }

    @Test
    void testConnection() {
        Connection connection = null;

        try {
            connection = this.db.getConnection();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            fail();
            return ;
        }
        assertNotNull(connection);
        this.db.shutdown();
    }
}
