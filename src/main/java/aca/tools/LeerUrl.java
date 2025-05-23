package aca.tools;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class LeerUrl{
  public static void main(String[] args) throws Exception
  {
    String urlString = "http://localhost:8082/academico/monitoracademico/metrics";
    
    // create the url
    URL url = new URL(urlString);
    
    // open the url stream, wrap it an a few "readers"
    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

    // write the output to stdout
    String line;
    while ((line = reader.readLine()) != null){
      System.out.println(line);
    }

    // close our reader
    reader.close();
  }
}