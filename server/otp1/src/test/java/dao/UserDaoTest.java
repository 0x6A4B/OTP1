package dao;

import dev._x6a4b.otp1.dao.UserDao;
import dev._x6a4b.otp1.entity.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    @BeforeEach
    public void init(){

    }

    @Test
    void persist() {
        UserDao userDao = new UserDao();
        User user = new User("kekkonen1", "salasana");
        userDao.persist(user);

        User userB = userDao.findByName("kekkonen1");
        assertEquals(user.getUserName(), userB.getUserName());
    }

    @Test
    void findByName() {
        UserDao userDao = new UserDao();
        User user = new User("kekkonen2", "salasana");
        userDao.persist(user);

        User userB = userDao.findByName("kekkonen2");
        User userC = userDao.findByName("paasikivi2");

        assertEquals(user.getUserName(), userB.getUserName());
        assertNull(userC);
    }

    @Test
    void update() {
        UserDao userDao = new UserDao();
        User user = new User("kekkonen3", "salasana");
        userDao.persist(user);

        user.setUserName("paasikivi3");
        userDao.update(user);
        User userB = userDao.findByName("paasikivi3");
        User userC = userDao.findByName("kekkonen3");

        assertEquals(user.getUserName(), userB.getUserName());
        assertNull(userC);
    }

    @Test
    void delete() {
        UserDao userDao = new UserDao();
        User user = new User("kekkonen4", "salasana");
        userDao.persist(user);

        User userB = userDao.findByName("kekkonen4");
        assertEquals(user.getUserName(), userB.getUserName());
        userDao.delete(user);
        userB = userDao.findByName("kekkonen4");
        assertNull(userB);

    }
}