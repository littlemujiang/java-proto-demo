import httpconn.EasyHttp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by mujiang on 2017/9/1.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = EasyHttp.class)
public class EasyHttpTest {

    @Autowired
    EasyHttp easyHttp;


    @Test
    public void test(){


//        String cursor = String.valueOf(System.currentTimeMillis());

//     easyHttp.sendRequest();

    }
}
