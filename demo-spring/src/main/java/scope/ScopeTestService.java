package scope;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Created by mujiang on 2018/1/16.
 */

@Service
public interface ScopeTestService {

    public String getObjId();

}
