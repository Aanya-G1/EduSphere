package util;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class QuizTimer extends Thread {
	private int time;
	private boolean running = true;
	private Label timerLabel;
	private Runnable onTimeUp;

	public QuizTimer(int time, Label timerLabel, Runnable onTimeUp) {
		this.time = time;
		this.timerLabel = timerLabel;
		this.onTimeUp = onTimeUp;
	}

	public void run() {
		while (time > 0 && running) {
			int minutes = time / 60;
			int seconds = time % 60;
			String display = String.format("%02d:%02d", minutes, seconds);
			Platform.runLater(() -> timerLabel.setText("Time Left: " + display));
			try {
				Thread.sleep(1000);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			time--;
		}
		if (time == 0 && running) {
			Platform.runLater(() -> {
				timerLabel.setText("Time's Up!");
				if (onTimeUp != null) {
					onTimeUp.run();
				}
			});
		}
	}

	public void stopTimer() {
		running = false;
	}
}
