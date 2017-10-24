import java.io.*;
import java.net.*;

public class NetConnectTest 
{	
	class DownloadTask {
		String download(String url) {
			String result = null;
			try {
				
				URL link = new URL(url);
				HttpURLConnection con = (HttpURLConnection) link.openConnection();
				con.connect();
				
				InputStream reader = con.getInputStream();
				int data = reader.read();
				while(data != -1) {
				    result += (char) data;
				    data = reader.read();
				}
				reader.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			return result;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(new NetConnectTest()
							.new DownloadTask()
							.download("https://www.somusysadmin.com"));
	}
}
