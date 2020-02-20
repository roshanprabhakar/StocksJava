import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    private static final String[] trackedTicker = {"AAPL", "MSFT", "GOOG"};

    //minutely data collection
    public static void main(String[] args) throws InterruptedException {
        for (String ticker : trackedTicker) {
            new Thread(() -> Utils.collect(ticker)).start();
        }
    }
}
