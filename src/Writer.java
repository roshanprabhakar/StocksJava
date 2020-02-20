import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class Writer {

    private String keys;
    private String data;

    public Writer(ArrayList<String> keySet, ArrayList<HashMap<String, String>> data) {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < keySet.size(); i++) {
            out.append(keySet.get(i));
            if (i != keySet.size() - 1) out.append(", ");
        }
        out.append("\n");
        this.keys = out.toString();
        out = new StringBuilder();
        for (HashMap<String, String> quote : data) {
            for (int i = 0; i < keySet.size(); i++) {
                out.append(quote.get(keySet.get(i)));
                if (i != keySet.size() - 1) out.append(", ");
            }
            out.append("\n");
        }
        this.data = out.toString();
    }

    public void write(String filepath) {
        try {

            File dataFile = new File(filepath);
            if (!dataFile.exists()) dataFile.createNewFile();

            String previousLines = read(filepath);

            if (previousLines.length() == 0) {
                previousLines += keys += data;
            } else {
                previousLines += data;
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile));
            writer.write(previousLines);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String read(String filepath) {
        StringBuilder data = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(filepath)));
            String line;
            while ((line = reader.readLine()) != null) {
                data.append(line);
                data.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data.toString();
    }
}
