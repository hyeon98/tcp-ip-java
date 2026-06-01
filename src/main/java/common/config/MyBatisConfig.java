package common.config;

import java.io.IOException;
import java.io.Reader;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisConfig {

    private static final SqlSessionFactory SQL_SESSION_FACTORY = createSqlSessionFactory();

    private MyBatisConfig() {
    }

    private static SqlSessionFactory createSqlSessionFactory() {
        try {
            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");

            return new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            throw new IllegalStateException("MyBatis 설정 파일을 찾을 수 없습니다.", e);
        }
    }

    public static SqlSession openSession() {
        return SQL_SESSION_FACTORY.openSession();
    }

    public static SqlSession openSession(boolean autoCommit) {
        return SQL_SESSION_FACTORY.openSession(autoCommit);
    }
}