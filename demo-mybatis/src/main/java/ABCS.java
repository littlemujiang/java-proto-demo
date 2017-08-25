import domain.App;
import domain.Service;
import mapper_classes.AppMapper;
import mapper_classes.ServiceMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.List;

public class ABCS  {

    public SqlSession getSqlSession(){

        try {

            String resource = "mybatis-config.xml";

            InputStream inputStream = ABCS.class.getClassLoader().getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

//            Reader reader = Resources.getResourceAsReader(resource);
//            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

            SqlSession session = sqlSessionFactory.openSession();

            return session;

        }catch(Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
        }

        return null;
    }


    private void serviceMgmt(){

        SqlSession session = this.getSqlSession();
        ServiceMapper mapper = session.getMapper(ServiceMapper.class);

        Service service = mapper.getServiceById(333);

        System.out.println(service.getService_name());

    }

    private void appMgmt(){

        SqlSession session = this.getSqlSession();

        AppMapper mapper = session.getMapper(AppMapper.class);

        Timestamp now = new Timestamp(System.currentTimeMillis());

        System.out.println(now);
        System.out.println(now.getTime());

        mapper.insertApp(4, "4444", "第2个", now, now);

        session.commit();

        List<App> apps = mapper.getApps();

        System.out.println(apps.size());

    }



    public static void main(String[] args) {
        // TODO �Զ����ɵķ������

        ABCS abcs = new ABCS();
//        SqlSession session = abcs.getSqlSession();

//        String statement = "domain.serviceMapper.getServiceById";
//        Service service = session.selectOne(statement, 222);

//        abcs.serviceMgmt();

          abcs.appMgmt();




    }


}