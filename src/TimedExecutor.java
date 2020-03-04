import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimedExecutor {

    private Timer executorTimer;
    private Timer stockTimer;

    public TimedExecutor() {
        executorTimer = new Timer();
        executorTimer.schedule(new ScriptExecutor(), new Date(Utils.INITIALIZATION));
    }

    class ScriptExecutor extends TimerTask {
        public void run() {
            stockTimer = new Timer();
            stockTimer.schedule(new StockTask(), Utils.DELAY_SECONDS, Utils.DURATION_SECONDS);
            //delay is the time between every close and open, period is duration of market open
            executorTimer.cancel();
        }
    }

    class StockTask extends TimerTask {
        public void run() {
            for (String ticker : Utils.trackedTicker) {
                new Thread(() -> Utils.collect(ticker)).start();
            }
            stockTimer.cancel();
        }
    }
}
