package my;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * hbase shell $ create 'test2', 'users'
 */
public class UserTest {
    private EntityManagerFactory emf;
    private EntityManager em;

    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("hbaseTest");
        em = emf.createEntityManager();
    }

    @Test
    public void xx() {
        User u = new User();
        u.setUserId("uuid" + UUID.randomUUID().toString());
        u.setFirstName("first name");
        u.setLastName("last name");
        u.setCity("Rostov");
        em.persist(u);

        TypedQuery<User> q = em.createQuery("select u from User u where u.city like 'R%'", User.class);
        List<User> r = q.getResultList();
        assertTrue(r.size() >= 1);
        for (User ru : r) {
            assertEquals("Rostov", ru.getCity());
        }
    }

    @After
    public void tearDown() {
        if (em != null) {
            em.close();
            emf.close();
        }
    }
}
