import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimedExecutor {

    private Timer timer;

    public TimedExecutor() {
        Date startDate = new Date(Utils.START_MILLISECONDS);

        timer = new Timer();
        timer.schedule(new StockTask(), startDate, Utils.DURATION_SECONDS + 60);
    }

    class StockTask extends TimerTask {
        public void run() {
            for (String ticker : Utils.trackedTicker) {
                new Thread(() -> Utils.collect(ticker)).start();
            }
            timer.cancel();
        }
    }
}
