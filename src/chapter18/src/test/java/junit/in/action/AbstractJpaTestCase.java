package junit.in.action;

import org.hibernate.SessionFactory;
import org.hibernate.connection.ConnectionProvider;
import org.hibernate.ejb.EntityManagerFactoryImpl;
import org.hibernate.impl.SessionFactoryImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Connection;
import org.hibernate.cfg.Settings;

public class AbstractJpaTestCase {

    private static EntityManagerFactory emf;
    protected static Connection connection;
    protected EntityManager em;

    @BeforeAll
    public static void setUpDatabase() throws Exception {
        // emf 생성은 비용이 많이 소모되기 때문에 한번만 수행되도록 한다.
        emf = Persistence.createEntityManagerFactory("test-chapter-18");
        connection = getConnection(emf);
    }

    @AfterAll
    public static void closeDatabase() throws Exception {
        if (connection != null) {
            connection.close();
            connection = null;
        }
        if (emf != null) {
            emf.close();
        }
    }

    @BeforeEach
    public void setEntityManager() {
        em = emf.createEntityManager();
    }

    @AfterEach
    public void closeEntityManager() {
        em.close();
    }

    public static Connection getConnection(Object object) throws Exception {
        Connection connection = null;
        if (object instanceof EntityManagerFactoryImpl) {
            EntityManagerFactoryImpl emfImpl = (EntityManagerFactoryImpl) object;
            SessionFactory sessionFactory = emfImpl.getSessionFactory();
            if (sessionFactory instanceof SessionFactoryImpl) {
                SessionFactoryImpl sfi = (SessionFactoryImpl) sessionFactory;
                Settings settings = sfi.getSettings();
                ConnectionProvider provider = settings.getConnectionProvider();
                connection = provider.getConnection();
            }
        }
        return connection;
    }

    protected void beginTransaction() {
        em.getTransaction().begin();
    }

    protected void commitTranscation() {
        em.getTransaction().commit();
    }

    protected void commitTransaction(boolean clearContext) {
        commitTranscation();
        if(clearContext) {
            em.clear();
        }
    }
}
