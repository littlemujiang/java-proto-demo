/**
 * Created by mujiang on 2017/9/18.
 */
public class OnlyMain {


    public static void main(String[] args){

        String requestURI = "/pcsd-newretail-ac/ddddd";

        if(requestURI.contains("/v1/users/") || requestURI.contains("pcsd-newretail-ac"))
            System.out.print("pass");

    }
}
