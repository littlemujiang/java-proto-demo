package scope;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by mujiang on 2018/1/16.
 */

@RestController
public class ScopeTestController {

    @Autowired
    ScopeTestService scopeTestService;


    @GetMapping("/test/scope")
    public String testBeanScope(){

        String objId = "";

        objId = scopeTestService.getObjId();

        System.out.println("~~~~~~~~~~~~"+objId);

        return "~~~~~~~~~~~~"+objId;

    }

}
