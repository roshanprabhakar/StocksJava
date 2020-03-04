import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Utils {

    public static final int BUFFER_SECONDS = 59; //processing is part of the buffer, ~1 second of processing server-end
    public static final int DURATION_SECONDS = (int) (6.5 * 60 * 60); //Open time for the stock exchange: 6.5 hours
    public static final int DELAY_SECONDS = (int) (17.5 * 60 * 60); //Downtime for stock exchange: 18.5 hours
    public static final long INITIALIZATION = 1583096400000L;
    public static final String[] trackedTicker = {
            "CODX", "MSFT", "GOOG", "AAPL", "CMG", "COST", "FB", "TWTR", "AMZN"};

    public static void collect(String ticker) {
        ArrayList<HashMap<String, String>> historicalData = new ArrayList<>();
        updateRecords(historicalData, ticker);

        ArrayList<String> keys = new ArrayList<>(historicalData.get(0).keySet());

        historicalData = new ArrayList<>();

        long startTime = System.currentTimeMillis();
        do {
            updateRecords(historicalData, ticker);
            if (((int)((System.currentTimeMillis() - startTime) / 1000)) % 3600 == 0) { //every hour record data
                Writer writer = new Writer(keys, historicalData);
                writer.write("IEXQuoteData" + ticker + ".csv");
                System.out.println("recording data for ticker: [" + ticker + "] timestamp: [" + LocalDateTime.now() + "]");
            }
            try {Thread.sleep(BUFFER_SECONDS * 1000);} catch (InterruptedException ignored) {}
        } while (System.currentTimeMillis() - startTime <= (DURATION_SECONDS * 1000));

        Writer writer = new Writer(keys, historicalData);
        writer.write("IEXQuoteData" + ticker + ".csv");
    }

    public static void updateRecords(ArrayList<HashMap<String, String>> records, String ticker) {
        String data = Collector.getTickerStream(ticker);
        records.add(Parser.getData(data));
    }
}
