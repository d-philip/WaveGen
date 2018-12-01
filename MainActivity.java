package com.example.layanbahaidarah.firstapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //creating the seek bars and the texts that would have their values
    private static SeekBar W1;
    private static TextView T1;
    private static Double V1;
    private static SeekBar W2;
    private static TextView T2;
    private static Double V2;
    private static SeekBar W3;
    private static TextView T3;
    private static Double V3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        the1Wave();
        the2Wave();
        the3Wave();
        //playerOutput();
    }


    //testing how to make the first wave
    public void the1Wave() {
        W1 = (SeekBar) findViewById(R.id.wave1Bar);
        T1 = (TextView) findViewById(R.id.wave1Text);

        W1.setOnSeekBarChangeListener(

                new SeekBar.OnSeekBarChangeListener() {
                    double value1;

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        value1 = (progress * 2 * Math.PI) / 100;
                        V1 = (int) Math.round(value1 * 100) / (double) 100;
                        T1.setText("W1 " + V1);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        T1.setText("W1 " + V1);
                        playerOutput();
                    }
                }
        );

    }

    //second wave
    public void the2Wave() {
        W2 = (SeekBar) findViewById(R.id.wave2Bar);
        T2 = (TextView) findViewById(R.id.wave2Text);
        W2.setOnSeekBarChangeListener(

                new SeekBar.OnSeekBarChangeListener() {

                    double value2;

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        value2 = (progress * 2 * Math.PI) / 100;
                        V2 = (int) Math.round(value2 * 100) / (double) 100;
                        T2.setText("W2 " + V2);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        T2.setText("W2 " + V2);
                        playerOutput();
                    }
                }
        );
    }


    public void the3Wave() {
        W3 = (SeekBar) findViewById(R.id.wave3Bar);
        T3 = (TextView) findViewById(R.id.wave3Text);
        W3.setOnSeekBarChangeListener(

                new SeekBar.OnSeekBarChangeListener() {

                    double value3;

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        value3 = (progress * 2 * Math.PI) / 100;
                        V3 = (int) Math.round(value3 * 100) / (double) 100;
                        T3.setText("W3 " + V3);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        T3.setText("W3 " + V3);
                        playerOutput();
                    }
                }
        );
    }

    public void playerOutput() {
        TextView allWaves = findViewById(R.id.allWavesText);
        allWaves.setText(V1 + " " + V2 + " " + V3);
    }
}

