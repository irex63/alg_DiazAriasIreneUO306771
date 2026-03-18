package algstudent.s4;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DevoradorTiempos {
	public static void main(String[] args) {
		JSONParser parser = new JSONParser();
		long t1, t2;

		for(int i=8;i<=65536;i*=2 ){
			try (FileReader reader = new FileReader("sols/g"+i+".json")) {

				JSONObject jsonObject = (JSONObject) parser.parse(reader);
				@SuppressWarnings("unchecked")
				Map<String, List<String>> grafo = (Map<String, List<String>>) jsonObject.get("grafo");


				t1 = System.currentTimeMillis();

				Map<String, String> solucion = ColoreoGrafo.realizarVoraz(grafo);

				t2 = System.currentTimeMillis();

				System.out.println(" n=" + i + "**TIEMPO EN MS=" + (t2 - t1));

				try (FileWriter file = new FileWriter("solucion.json")) {
					file.write(new JSONObject(solucion).toJSONString());
				}

				if (solucion != null) {
					//System.out.println("Solución encontrada: " + solucion);
				} else {
					System.out.println("No se encontró solución.");
				}
			} catch (IOException | ParseException e) {
				e.printStackTrace();
			}
		}
		
	}
}
