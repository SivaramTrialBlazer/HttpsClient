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

//java -Djsse.enableSNIExtension=false HttpsClient
//keytool -import -alias test -keystore F:\jdk1.7.0_17\jre\lib\security\cacerts -file test.cer //pass:changeit
// path=c:\windows\system32
//netsh int ip add address "Loopback" 35.244.45.179
//netsh int ip add addr 1 35.244.45.179/32 st=ac sk=tr
//set path=C:\autotrader\setup
//wget http://localhost:8080/dashboard/start
//netsh int ip delete addr 1 35.244.45.179
public class HttpsClient{
	
   public static void main(String[] args)
   {
	   int Todayopen, Yesterdayclose;
	   String symbol=readExpiry();
	   //System.out.println("Temp file e"+readExpiry());
	   
	   
	 HttpsClient ter=new HttpsClient();
	    try {
			File tempFile = new File("C:\\autotrader\\scripts\\"+ter.todaysdate());
			boolean exists = tempFile.exists();
			System.out.println("Temp file exists : " + exists);
			if(!exists)
			{
			
	   while(true)
	   {
	     System.out.println("Date we need :"+Long.toString(ter.todaysdate()));
		 String value=testIt(Long.toString(ter.todaysdate()));
		 
		 if(value.substring(16,24).equals("nextTime"))
		{
			System.out.println(value.substring(16,24));
			//TimeUnit.SECONDS.sleep(2);
			continue;
		}
		else
		{
			System.out.println("todays epoch :"+value.substring(6,16));
			
			if(value.substring(6,16).equals(Long.toString(ter.todaysdate())))
			{
				System.out.println("Todays open price: "+value.substring(34,38));
				Todayopen=Integer.parseInt(value.substring(34,38));
			
				 value=testIt(Long.toString(ter.todaysdate()-86400));
				 if(value.substring(16,24).equals("nextTime"))
				{
					System.out.println("Yesterday Epoch : "+value.substring(26,36));
					Yesterdayclose=Integer.parseInt(testIt(value.substring(26,36)).substring(23,27));
					System.out.println("Yesterday Close :"+Yesterdayclose);
				}
				else 
				{
					System.out.println("Yesterday Epoch1 : "+value.substring(6,16));
					Yesterdayclose=Integer.parseInt(value.substring(23,27));
					System.out.println("Yesterday Close :"+Yesterdayclose);
				}
				
					if(Todayopen>Yesterdayclose)
					{
						System.out.println("High");
						//usingBufferedWritter("\nPLACE_ORDER,594213438,"+symbol+",BUY,INTRADAY,LIMIT,1,"+Todayopen+",0,0,MCX,EQ,NA,0,NA,NA,DAY,CLI,0,-1,1553072066,,REGULAR,0,0,0");
						usingBufferedWritter("\nPLACE_ORDER,594213438,"+symbol+",BUY,INTRADAY,MARKET,1,0,0,0,MCX,EQ,NA,0,NA,NA,DAY,CLI,0,-1,1553072066,,REGULAR,0,0,0");
						usingBufferedWritter("\nPLACE_ORDER,594329312,"+symbol+",SELL,INTRADAY,STOP_LOSS,1,"+(Todayopen-75)+","+(Todayopen-74)+",0,MCX,EQ,NA,0,NA,NA,DAY,CLI,0,-1,1553154337,,regular,0,0,0");
						File file = new File("C:\\autotrader\\scripts\\"+ter.todaysdate());
						file.createNewFile();
						System.exit(0); 
					}
					else if(Todayopen<Yesterdayclose)
					{
						System.out.println("Low");
						//usingBufferedWritter("\nPLACE_ORDER,594213438,"+symbol+",SELL,INTRADAY,LIMIT,1,"+Todayopen+",0,0,MCX,EQ,NA,0,NA,NA,DAY,CLI,0,-1,1553072066,,REGULAR,0,0,0");
						usingBufferedWritter("\nPLACE_ORDER,594213438,"+symbol+",SELL,INTRADAY,MARKET,1,0,0,0,MCX,EQ,NA,0,NA,NA,DAY,CLI,0,-1,1553072066,,REGULAR,0,0,0");
						usingBufferedWritter("\nPLACE_ORDER,594329312,"+symbol+",BUY,INTRADAY,STOP_LOSS,1,"+(Todayopen+75)+","+(Todayopen+74)+",0,MCX,EQ,NA,0,NA,NA,DAY,CLI,0,-1,1553154337,,regular,0,0,0");
						File file = new File("C:\\autotrader\\scripts\\"+ter.todaysdate());
						file.createNewFile();
						System.exit(0); 
					}
					else if(Todayopen==Yesterdayclose)
					{
						//Do Nothing
						
					}
			}	
			}
	   }
		}
		else
		{
				System.out.println("closing orders in 100 seconds\n");
				TimeUnit.SECONDS.sleep(100);
				//usingBufferedWritter("\nCANCEL_ORDER,594329312");
				//usingBufferedWritter("\nSQUARE_OFF_POSITION,MCX,"+symbol+",1");
				System.out.println("order closed sucessfully\n");
		}
		 } catch (IOException e) {
	     e.printStackTrace();
      } 
	  catch (InterruptedException e){
      //handle the exception
   }
	   
      
   }
   
   public static long todaysdate()
   {
	   long epoch=0;
   		try{
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy");
			String str = sdf.format(cal.getTime())+" 00:00:00.000 GMT";
			
			SimpleDateFormat df = new SimpleDateFormat("dd MM yyyy HH:mm:ss.SSS zzz");
			Date date = df.parse(str);
			epoch = date.getTime();
			
		}
		catch (Exception e) 
		{
			
		}
		return (epoch/1000);
		//return 1552867200;
		//return 1552953600;
	}	
	
  public static String testIt(String et){

		 String value="";
      URL url;
      try {
		
		 String https_url = "https://tvc4.forexpros.com/a5576b59a15cff4472bf47416ca1a030/1551345017/56/56/23/history?symbol=49774&resolution=D&from="+et+"&to="+et;
		
	     url = new URL(https_url);
	     HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
			
	     //dumpl all cert info
	    // print_https_cert(con);
			
	     //dump all the content
	     value=print_content(con);
		
		System.out.println("Value we get :"+value);
		
      } catch (MalformedURLException e) {
	     e.printStackTrace();
      } catch (IOException e) {
	     e.printStackTrace();
      }
	
	  return value;
	}
	
	public static void usingBufferedWritter(String text) throws IOException
	{
    String fileContent = text;
     
    BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\autotrader\\data\\order\\orders.csv",true));
    writer.write(fileContent);
    writer.close();
	}
	
	public static String readExpiry()
	{
		StringBuilder sb = new StringBuilder();
		String Exp="";

        try (BufferedReader br = Files.newBufferedReader(Paths.get("month.txt"))) {

            // read line by line
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);//.append("\n");
				Exp=line;
            }

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }

        //System.out.println(sb.toString());
		return(Exp);
		
	}
	
   private static void print_https_cert(HttpsURLConnection con){
     
    if(con!=null){
			
      try {
				
	System.out.println("Response Code : " + con.getResponseCode());
	System.out.println("Cipher Suite : " + con.getCipherSuite());
	System.out.println("\n");
				
	Certificate[] certs = con.getServerCertificates();
	for(Certificate cert : certs){
	   System.out.println("Cert Type : " + cert.getType());
	   System.out.println("Cert Hash Code : " + cert.hashCode());
	   System.out.println("Cert Public Key Algorithm : " 
                                    + cert.getPublicKey().getAlgorithm());
	   System.out.println("Cert Public Key Format : " 
                                    + cert.getPublicKey().getFormat());
	   System.out.println("\n");
	}
				
	} catch (SSLPeerUnverifiedException e) {
		e.printStackTrace();
	} catch (IOException e){
		e.printStackTrace();
	}

     }
	
   }
	
   public static String print_content(HttpsURLConnection con){
	   
	   String add="";
	if(con!=null){
			
	try {
		
	  // System.out.println("****** Content of the URL ********");			
	   BufferedReader br = 
		new BufferedReader(
			new InputStreamReader(con.getInputStream()));
				
	   String input;
				
	   while ((input = br.readLine()) != null){
	     // System.out.println(input);
		  add+=input;
	   }
	   br.close();
				
	} catch (IOException e) {
	   e.printStackTrace();
	}
			
       }
	   
	   return add;
		
   }
	
}