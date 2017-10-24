import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class celebGrepper {
	
	static class CelebData {
		URL link;
		String name;
		
		CelebData(URL link, String name) {
			this.link=link;
			this.name=name;
		}
	}
	
	public static String grepper(String url) {
		URL source;
		String data = null;
		
		try {
			source = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) source.openConnection();
			connection.connect();
			
			InputStream is = connection.getInputStream();
			
			/**
			 * Attempting to fetch an entire line at a time instead of just a character each time!
			 */
			StringBuilder str = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			while((data = br.readLine()) != null)
				str.append(data);
			
			data=str.toString();
					
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	public static ArrayList<CelebData> parser(String html) throws MalformedURLException {
		ArrayList<CelebData> list = new ArrayList<CelebData>();
		
		/**
		 * The following RegEx originally matched HTML tags but the page made an AJAX 
		 * call to obtain JSON data - which we're dealing with directly now to reduce
		 * page load time and complexity of RegEx required!
		 */
		Pattern p = Pattern.compile("\"name\":\\s*\"(.+?)\"[\\s\\S]*?\"squareImage\":\\s*\"(.*?)\"");
		Matcher m = p.matcher(html);
		
		while(m.find()) {
			CelebData current = new CelebData(new URL("https:"+m.group(2)),m.group(1));
			list.add(current);
		}
		
		return list;
	}
	
	public static void main(String... args) throws MalformedURLException {
		String html = grepper("https://www.forbes.com/ajax/list/data?year=2017&uri=celebrities&type=person");
		System.out.println("RAW Input: "+html);
		System.out.println("Start Grepping...");
		ArrayList<CelebData> celebList = parser(html);
		for(CelebData item: celebList) {
			System.out.println("Name:\t\t "+item.name);
			System.out.println("Image URL:\t "+item.link+"\n");
		}
		System.out.println("Grepping Done!");
	}

}
