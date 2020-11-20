package org.example.demo.rest;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@SpringBootTest
class RestApplicationTests {
    @Autowired
    DataSource dataSource;

    @Autowired
    SqlSession sqlSession;
    @Test
    void contextLoads() {
    }

    @Test
    public void setDataSource() throws SQLException {
        Connection connection = dataSource.getConnection();
        log.info("DBCP : "+dataSource.getClass());
        log.info("connection url  : "+connection.getMetaData().getURL());
        log.info("      username  : "+connection.getMetaData().getUserName());
        log.info(sqlSession.getClass().toString());
    }

}
