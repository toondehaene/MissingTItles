import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Scanner;

public class MissingTitles {

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public static void main(String[] args) throws IOException, JSONException {
        String tconst = "";
        Scanner sc = new Scanner(new File("c:/temp/missing_tconst.csv"));
        String line = "titleID,title\n";
        BufferedWriter writer = new BufferedWriter(new FileWriter("c:/temp/titlesOutput.txt"));

//        int i = 0;
        sc.nextLine();
        while(sc.hasNextLine()){
            tconst = sc.nextLine();
            JSONObject json = readJsonFromUrl("https://www.omdbapi.com/?apikey=b9755aba&i=" + tconst);
            System.out.println("https://www.omdbapi.com/?apikey=b9755aba&i=" + tconst);
            line = "" + ((String) json.get("imdbID")).substring(2) +"," + json.get("Title") + "\n";

            writer.write(line);
//            i++;
        }
        writer.close();
    }
}
