import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    //minutely data collection
    public static void main(String[] args) throws InterruptedException {

        ArrayList<HashMap<String, String>> historicalData = new ArrayList<>();

        updateRecords(historicalData, "AAPL");
        ArrayList<String> keys = new ArrayList<>(historicalData.get(0).keySet());

        historicalData = new ArrayList<>();
        while (true) {
            updateRecords(historicalData, "AAPL");
            Writer writer = new Writer(keys, historicalData);
            writer.write("IEXQuoteData.csv");
            Thread.sleep(60000);
        }
    }

    private static void updateRecords(ArrayList<HashMap<String, String>> records, String ticker) {
        String data = Collector.getTickerStream(ticker);
        records.add(Parser.getData(data));
    }
}
