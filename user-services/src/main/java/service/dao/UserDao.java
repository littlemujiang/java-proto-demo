package service.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import service.entity.User;

import java.util.List;

/**
 * Created by epcm on 2017/7/12.
 */
@Repository
public interface UserDao extends MongoRepository<User,String> {

    List<User> findAll() ;

    List<User> findByUsername(String username);

    List<User> findByUsernameAndAppkey(String username, String appkey);

    User findOneByUsernameAndAppkey(String username, String appkey);

    <S extends User> S insert(S s);


}
