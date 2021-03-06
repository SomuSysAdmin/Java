import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class stringSplitter {
	public static void main(String... args) {
		String myString = "Arpi x Somu x Neha x Santy x Debarati";
		String[] words = myString.split(" x ");
		System.out.println(Arrays.toString(words));
		
		// Challenge 1
		String city = "Mississippi";
		String[] parts = city.split("s");
		for(String part: parts)
			System.out.print(part+" ");
		System.out.println();
		
		Pattern p = Pattern.compile("Mi(.*?)pi");
		Matcher m = p.matcher(city);
		
		while(m.find())
			System.out.println(m.group(1));
	}
}