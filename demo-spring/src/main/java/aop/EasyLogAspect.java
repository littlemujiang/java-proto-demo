package aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by mujiang on 2017/11/2.
 */

@Aspect
@Component
public class EasyLogAspect {


    @Pointcut("execution(public * aop.CommonService.point(..))")
    private void pointcutMethod(){}

    @Before("pointcutMethod()")
    public void doBefore(){
        System.out.println("do before point");
    }




}
