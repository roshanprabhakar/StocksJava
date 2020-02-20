import java.util.ArrayList;
import java.util.HashMap;

public class Utils {

    public static final int BUFFER_SECONDS = 10;
    public static final int DURATION_SECONDS = 60 * 5;

    public static void collect(String ticker) {
        ArrayList<HashMap<String, String>> historicalData = new ArrayList<>();
        updateRecords(historicalData, ticker);

        ArrayList<String> keys = new ArrayList<>(historicalData.get(0).keySet());

        historicalData = new ArrayList<>();

        long startTime = System.currentTimeMillis();
        do {
            updateRecords(historicalData, ticker);
            try {Thread.sleep(BUFFER_SECONDS * 1000);} catch (InterruptedException e) {}
        } while (System.currentTimeMillis() - startTime <= (DURATION_SECONDS * 1000));

        Writer writer = new Writer(keys, historicalData);
        writer.write("IEXQuoteData" + ticker + ".csv");
    }

    public static void updateRecords(ArrayList<HashMap<String, String>> records, String ticker) {
        String data = Collector.getTickerStream(ticker);
        records.add(Parser.getData(data));
    }
}
