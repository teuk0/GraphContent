/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartcontent.testfront;

import java.io.IOException;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
/**
 *
 * @author Teuk
 */
public class Bench implements Runnable {
    
    int i;
    
    public Bench(int n){
        i=n;
    }
    
    public static void main(String[]argv) throws IOException, InterruptedException{
        
        long t = System.currentTimeMillis();
        for(int i =0;i<200;i++){
            new Thread(new Bench(i)).start();
            //System.out.println("go"+i);
            //Thread.sleep(10);
        }
        System.out.println("done");

    }

    @Override
    public void run() {
            // Create an instance of HttpClient.
    HttpClient client = new HttpClient();

    // Create a method instance.
    GetMethod method = new GetMethod("http://localhost:8084/TestFront-1.0-SNAPSHOT/content.jsp?contentname=1350576097728");
    if(i%4==1){
        method = new GetMethod("http://localhost:8084/TestFront-1.0-SNAPSHOT/content.jsp?contentname=1350576090355");
    }else if(i%4==2){
        method = new GetMethod("http://localhost:8084/TestFront-1.0-SNAPSHOT/content.jsp?contentname=1350576361152");
    }else if(i%4==3){
        method = new GetMethod("http://localhost:8084/TestFront-1.0-SNAPSHOT/content.jsp?contentname=1350581389625");
    }


    // Provide custom retry handler is necessary
    method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, 
        	new DefaultHttpMethodRetryHandler(3, false));

    try {
      // Execute the method.
      int statusCode = client.executeMethod(method);

      if (statusCode != HttpStatus.SC_OK) {
        System.err.println("Method failed: " + method.getStatusLine());
      }

      // Read the response body.
      byte[] responseBody = method.getResponseBody();

      // Deal with the response.
      // Use caution: ensure correct character encoding and is not binary data
      //System.out.println("ok"+i);

    } catch (HttpException e) {
      System.err.println("Fatal protocol violation: " + e.getMessage());
      e.printStackTrace();
    } catch (IOException e) {
      System.err.println("Fatal transport error: " + e.getMessage());
      e.printStackTrace();
    } finally {
      // Release the connection.
      method.releaseConnection();
    }  
    
    }
    
}
