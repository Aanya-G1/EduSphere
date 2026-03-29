package util;

public class QuizTimer extends Thread {

    private int time; // in seconds
    private boolean running = true;

    public QuizTimer(int time) {
        this.time = time;
    }

    public void run() {
        while (time > 0 && running) {
            System.out.println("Time left: " + time + " seconds");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            time--;
        }

        if (time == 0) {
            System.out.println("Time's up!");
        }
    }

    public void stopTimer() {
        running = false;
    }
}
