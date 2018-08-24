package ifbarbacena.taroman.ws.ex2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;

import javax.swing.JOptionPane;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GITConsumer {
	public static void main(String[] args) {

		String semiURL = "https://api.github.com/users/";

		String userName = lerString("Digite o nome de usuário: \n");
		if(userName == null) return;

		do {
			try {
				String source = doRequest(semiURL+userName);
				//System.out.println(source.toString());
				JSONObject jso = getJSONObject(source);
				
				StringBuilder result = new StringBuilder();
				
				result.append("Usuário da consulta: ").append(jso.get("name")).append("\n\n");
				result.append(jso.get("followers")).append(" seguidores").append("\n\n");
				
				source = doRequest((String)jso.get("followers_url"));
				//System.out.println(source.toString());
				JSONArray seguidores = getJSONArray(source);
				Iterator<JSONObject> iteSeg = seguidores.iterator();
				
				JSONObject jsoAux;
				JSONArray repositorios;
				while(iteSeg.hasNext()) {
					jsoAux = iteSeg.next();
					//System.out.println(jsoAux.get("url"));
					
					source = doRequest((String) jsoAux.get("url"));
					jsoAux = getJSONObject(source);
					
					result.append(jsoAux.get("name")).append("\n");
					
					source = doRequest((String) jsoAux.get("repos_url"));
					repositorios = getJSONArray(source);
					Iterator<JSONObject> iteRep = repositorios.iterator();
					
					while(iteRep.hasNext()) {
						jsoAux = iteRep.next();
						result.append("\t").append("name");
					}
					
				}
				
				System.out.println(result.toString());
			} catch (IOException | ParseException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Não encontrou usuário ou erro na API");
			}	

			userName = lerString("Digite o nome de usuário: \n");
		}while(userName != null);


	}

	public static String lerString(String mensagem) {
		return JOptionPane.showInputDialog(mensagem);
	}
	
	public static String doRequest(String strUrl) throws IOException {
		URL url = new URL(strUrl);
		URLConnection con = url.openConnection();

		BufferedReader input = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
		String line;
		StringBuilder source = new StringBuilder();
		while((line = input.readLine()) != null)
			source.append(line);
		input.close();
		
		return source.toString();
	}
	
	public static JSONObject getJSONObject(String strJSON) throws ParseException {
		JSONParser parse = new JSONParser();
		return (JSONObject) parse.parse(strJSON);
	}
	
	public static JSONArray getJSONArray(String strJSON) throws ParseException {
		JSONParser parse = new JSONParser();
		return (JSONArray) parse.parse(strJSON);
	}
}
