package process;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by mujiang on 2017/11/21.
 */
public class NetTest {

    String output = "";

    private void exec(){

        String ip = "localhost";
        String port = "8080";
        String shell = "telnet "+ ip + " "+ port;

        BufferedReader br = null ;

        try {

            Process process = Runtime.getRuntime().exec(shell);
            br = new BufferedReader(new InputStreamReader(process.getInputStream()));

            TaskWorker taskWorker = new TaskWorker(process);
            taskWorker.start();

            String line =  "init";
            while (line != null){
                line = br.readLine();
                if(line != null ){
                    output = output.concat(line + "\r\n");
                    System.out.println("-  "+line);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("************");
        System.out.println(output);
    }


    public static void main(String[] args) {

        new NetTest().exec();

    }

    static class TaskWorker extends Thread{

        private final Process process;

        TaskWorker(Process process ) {
            this.process = process;
        }

        public void run(){

            try {
                sleep(3000);
                process.destroy();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }

    }

}