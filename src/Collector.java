import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Collector {

    private static final String token = "sk_861a4746f9784f83b68c4717e9e06a9b";

    public static String getTickerStream(String ticker) {
        try {
            URL url = new URL("https://cloud.iexapis.com/stable/stock/" + ticker + "/quote?token=" + token);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();

            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
