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

//java -Djsse.enableSNIExtension=false HttpsClient
//keytool -import -alias test -keystore F:\jdk1.7.0_17\jre\lib\security\cacerts -file test.cer

public class HttpsClient{
	
   public static void main(String[] args)
   {
	   
	 HttpsClient ter=new HttpsClient();
	    try {
	   while(true)
	   {
	     System.out.println("Date we need :"+Long.toString(ter.todaysdate()));
		 String value=testIt(Long.toString(ter.todaysdate()));
		 
		 //System.out.println(value.substring(16,24));
		 if(value.substring(16,24).equals("nextTime"))
		{
			System.out.println(value.substring(16,24));
			TimeUnit.SECONDS.sleep(10);
			continue;
		}
		else
		{
			System.out.println("todays epoch"+value.substring(6,16));
			if(value.substring(6,16).equals(Long.toString(ter.todaysdate())))
			{
				System.out.println("Todays open price: "+value.substring(34,38));
				
				System.out.println("Getting yesterday close");
				System.out.println("******* ********* *****");
				 value=testIt1(Long.toString(ter.todaysdate()-86400));
				 if(value.substring(16,24).equals("nextTime"))
				{
					//value=testIt1(value.substring(27,37));
					System.out.println(value.substring(26,36));
					//System.exit(0); 
					
				}
				else 
				{
					
				}
				//Integer result = Integer.valueOf(ter.usingBufferedreader());
				// kindly skip 7 or 1
				/*Date expiry = new Date((Long.parseLong(ter.usingBufferedreader())) * 1000);
				System.out.println("res date :"+expiry);
				Calendar c = Calendar.getInstance();
				c.setTime(expiry);
				int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
				System.out.println(dayOfWeek);
				
				if(dayOfWeek==2)
				{
					
				}
				else
				{
					
				}
				usingBufferedWritter("");
				
				}*/
				
				
				
			}
		 
	   }
		}
		 
		 } /*catch (MalformedURLException e) {
	     e.printStackTrace();
      }catch (IOException e) {
	     e.printStackTrace();
      }*/ 
	  catch (InterruptedException e){
      //handle the exception
   }
	   
      //  new HttpsClient().testIt();
   }
   
   
   
   
   public static long todaysdate()
   {
	   long epoch=0;
   		try{
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy");
			String str = sdf.format(cal.getTime())+" 00:00:00.000 GMT";
			//System.out.println(str);
			SimpleDateFormat df = new SimpleDateFormat("dd MM yyyy HH:mm:ss.SSS zzz");
			Date date = df.parse(str);
			epoch = date.getTime();
			//System.out.println(df.parse(str)); 
			//System.out.println(epoch/1000); // 1055545912454
		}
		catch (Exception e) 
		{
			
		}
		return (epoch/1000);
	}	
	
  public static String testIt(String et){

	  //String url = "https://tvc4.forexpros.com/a5576b59a15cff4472bf47416ca1a030/1551345017/56/56/23/history?symbol=49774&resolution=D&from=1551398400&to=1551398400";
		 String value="";
      URL url;
      try {
		 
		
		 String https_url = "https://mail.tmbank.in?from="+et+"&to="+et;
		
	     url = new URL(https_url);
	     HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
			
	     //dumpl all cert info
	    // print_https_cert(con);
			
	     //dump all the content
	     value=print_content(con);
		// value="{\"s\":\"no_data\",\"nextTime\":1551657600}";
		 value="{\"t\":[1552521600],\"c\":[3973],\"o\":[4074],\"h\":[4117],\"l\":[3952],\"v\":[221143],\"vo\":[0],\"s\":\"ok\"}";
	
		
		
		
		/* System.out.println("Next time :"+value.substring(26,36)+"++++"+value.substring(16,24));
		 value=value.substring(26,36);
		 Date expiry = new Date(Long.parseLong(value) * 1000);
		  System.out.println("res date :"+expiry);
		  Calendar c = Calendar.getInstance();
		c.setTime(expiry);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		  System.out.println(dayOfWeek);
			//  usingBufferedWritter();*/
			
			  
      } catch (MalformedURLException e) {
	     e.printStackTrace();
      } catch (IOException e) {
	     e.printStackTrace();
      }
	  
	 /* 	if(value.substring(16,24)=="nextTime")
		{
			
			//break outer;
		}	*/
	  
	  return value;
	}
	
	
	
	
	  public static String testIt1(String et){

		 String value="";
		// value="{\"t\":[1552521600],\"c\":[3973],\"o\":[4074],\"h\":[4117],\"l\":[3952],\"v\":[221143],\"vo\":[0],\"s\":\"ok\"}";
		 value="{\"s\":\"no_data\",\"nextTime\":1551657600}";
		 return value;
	}

   
   
 public  String usingBufferedreader() throws IOException
   {  
   File file = new File("E:\\test.txt"); 
  
  BufferedReader br = new BufferedReader(new FileReader(file)); 
  
  String st,vr; 
  vr="";
  while ((st = br.readLine()) != null) 
  {	
	//System.out.println(st);
	vr=vr+st;
	} 
	return vr;

	}
	
	public static void usingBufferedWritter(String text) throws IOException
	{
    String fileContent = "Hello Learner !! Welcome to howtodoinjava.com.";
     
    BufferedWriter writer = new BufferedWriter(new FileWriter("E:\\test1.txt"));
    writer.write(fileContent);
    writer.close();
	}
	
	public static void usingBufferedWritter1(String text) throws IOException
	{
    String fileContent = text;
     
    BufferedWriter writer = new BufferedWriter(new FileWriter("E:\\test1.txt"));
    writer.write(fileContent);
    writer.close();
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