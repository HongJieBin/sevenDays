import com.memory.dao.PersonDao;
import com.memory.pojo.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:appliciationContext.xml")
public class myTest {

    @Resource(name = "personDao")
    private PersonDao personDao;

    @Autowired
    HibernateTemplate hibernateTemplate;

    @Test
    public void testSave() {
        Person p = new Person();
        p.setUserName("Winbee");
        p.setSex("女");
        p.setPassword("123");
        System.out.println(p);
        personDao.save(p);
        //System.out.println(hibernateTemplate.get(Person.class,"Winbee"));
    }
}
