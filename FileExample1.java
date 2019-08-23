import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.Certificate;
import java.io.*;
import java.io.FileWriter; 
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileExample1 {
	
	public static void usingBufferedWritter(String text) throws IOException
	{
    String fileContent = text;
     
    BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\autotrader\\data\\order\\orders.csv",true));
    writer.write(fileContent);
    writer.close();
	}

    public static void main(String[] args) {

        StringBuilder sb = new StringBuilder();
		String sb1 ="";

        try (BufferedReader br = Files.newBufferedReader(Paths.get("C:\\autotrader\\data\\order\\platform_positions.csv"))) {

            // read line by line
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);//.append("\n");
				sb1=sb1+line;
            }
			
		System.out.println(sb1.substring(0,17));
		System.out.println(Integer.parseInt(sb1.substring(34,37)));
		
		if(Integer.parseInt(sb1.substring(34,37))==-10)
		{
			System.out.println("one sell position found:"+sb1.substring(0,17));
			usingBufferedWritter("\nCANCEL_ORDER,594329312");
			usingBufferedWritter("\nPLACE_ORDER,594213439,"+sb1.substring(0,17)+",BUY,INTRADAY,MARKET,1,0,0,0,MCX,EQ,NA,0,NA,NA,DAY,CLI,0,-1,1553072066,,REGULAR,0,0,0");
			
		}
		else if(Integer.parseInt(sb1.substring(34,37))==10)
		{
			System.out.println("one buy position found:"+sb1.substring(0,17));
			usingBufferedWritter("\nCANCEL_ORDER,594329312");
			usingBufferedWritter("\nPLACE_ORDER,594213439,"+sb1.substring(0,17)+",SELL,INTRADAY,MARKET,1,0,0,0,MCX,EQ,NA,0,NA,NA,DAY,CLI,0,-1,1553072066,,REGULAR,0,0,0");
			
		}

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }

        
		
		//( 0, 31).equals("PLACE_ORDER,594213438,CRUDEOILM"));
		

    }

}