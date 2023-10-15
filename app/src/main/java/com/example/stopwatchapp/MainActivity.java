package com.example.stopwatchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView result;
    Button start, stop, reset;
    private long startTime = 0L;
    private long currentTime = 0L;
    private boolean isRunning = false;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.resultBox);
        start = findViewById(R.id.startBtn);
        stop = findViewById(R.id.stopBtn);
        reset = findViewById(R.id.resetBtn);

        // Removed the Runnable here and placed it outside the onCreate method

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRunning) {
                    isRunning = true;
                    startTime = SystemClock.uptimeMillis();
                    handler.post(updateTimer);
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRunning) {
                    isRunning = false;
                    handler.removeCallbacks(updateTimer);
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRunning = false;
                handler.removeCallbacks(updateTimer);
                currentTime = 0L;
                updateUI(currentTime);
            }
        });
    }

    private Runnable updateTimer = new Runnable() {
        public void run() {
            // Calculate the time passed and update the TextView
            currentTime = SystemClock.uptimeMillis() - startTime;
            updateUI(currentTime);

            handler.postDelayed(this, 100);
        }
    };

    private void updateUI(long timeElapsed) {
        int seconds = (int) (timeElapsed / 1000);
        int minutes = seconds / 60;
        int hours = minutes / 60;
        seconds = seconds % 60;
        int milliseconds = (int) (timeElapsed % 1000);
        result.setText(String.format("%02d:%02d:%02d:%02d", hours, minutes, seconds, milliseconds));
    }
}
