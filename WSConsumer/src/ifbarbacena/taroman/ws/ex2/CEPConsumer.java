package ifbarbacena.taroman.ws.ex2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class CEPConsumer {
	public static void main(String[] args) {
		try {
			URL url = new URL("https://api.github.com/users/TarllesRoman");
			
			URLConnection con = url.openConnection();
			
			BufferedReader input = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
			
			String line;
			StringBuilder source = new StringBuilder();
			
			while((line = input.readLine()) != null)
				source.append(line);
			
			input.close();
			
			System.out.println(source.toString());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
}
