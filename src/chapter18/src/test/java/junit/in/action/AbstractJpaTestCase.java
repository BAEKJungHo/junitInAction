package junit.in.action;

import org.hibernate.ejb.EntityManagerFactoryImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Connection;

class AbstractJpaTestCase {

    private static EntityManagerFactory emf;
    protected static Connection connection;
    protected EntityManager em;

    @BeforeAll
    static void setUpDatabase() throws Exception {
        emf = Persistence.createEntityManagerFactory("test-chapter-18");
        connection = getConnection(emf);
    }

    @AfterAll
    static void closeDatabase() throws Exception {
        if (connection != null) {
            connection.close();
            connection = null;
        }
        if (emf != null) {
            emf.close();
        }
    }

    @BeforeEach
    void setEntityManager() {
        em = emf.createEntityManager();
    }

    @AfterEach
    void closeEntityManager() {
        em.close();
    }

    static Connection getConnection(Object object) throws Exception {
        Connection connection = null;
        if (object instanceof EntityManagerFactoryImpl) {
        }
        return null;
    }
}
