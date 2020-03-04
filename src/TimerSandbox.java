import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerSandbox {

    public static class RepeatExecutor {

        private long delay;
        private long buffer;
        private long executionTime;

        private TimerTask repeatedTask;

        public RepeatExecutor(long delay, long buffer, long executionTime, TimerTask repeatedTask) {
            this.delay = delay;
            this.buffer = buffer;
            this.repeatedTask = repeatedTask;
            this.executionTime = executionTime;
        }

        public void run() {
            Timer timer = new Timer();
            timer.schedule(new RepeatedTask(buffer, executionTime, repeatedTask), delay - buffer);
            timer.cancel();
        }
    }

    public static class RepeatedTask extends TimerTask {

        private long buffer;
        private long executionTime;
        private TimerTask repeatedTask;

        public RepeatedTask(long buffer, long executionTime, TimerTask repeatedTask) {
            this.buffer = buffer;
            this.repeatedTask = repeatedTask;
            this.executionTime = executionTime;
        }

        @Override
        public void run() {
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(repeatedTask, buffer, executionTime);
        }
    }

    public static class ExperimentalTask extends TimerTask {

        private static long previousEnd = System.currentTimeMillis();

        @Override
        public void run() {
            System.out.println("last execution ended: " + previousEnd);
            long start = System.currentTimeMillis();
            System.out.println("ExpTask starting: " + start);
            System.out.println("Delay in ending to initiation: " + (start - previousEnd) / (double) 1000);
            previousEnd = System.currentTimeMillis();
            for (int i = 0; i < 2; i++) System.out.println();
        }
    }

    public static void main(String[] args) {
        System.out.println("Script Executes at: " + System.currentTimeMillis());
        System.out.println();
        System.out.println();
        new RepeatedTask(3000L, 1000L, new ExperimentalTask()).run(); //buffer being treated as initial delay before repeated execution,
                                                                                          //each rep lasting et seconds, instantaneous reinitiation

    }
}
