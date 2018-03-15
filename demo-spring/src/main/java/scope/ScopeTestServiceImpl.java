package scope;

import org.springframework.stereotype.Service;

/**
 * Created by mujiang on 2018/1/16.
 */

//@Scope("prototype")
@Service
public class ScopeTestServiceImpl implements ScopeTestService {


    @Override
    public String getObjId() {
        TempObj tempObj = null;
        String objId = "";

        if(tempObj == null){
            tempObj = new TempObj();
        }

        return tempObj.toString();
    }
}
