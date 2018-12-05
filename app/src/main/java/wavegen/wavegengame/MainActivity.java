package wavegen.wavegengame;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;

import wavegen.wavegengame.R;

public class MainActivity extends AppCompatActivity {

    private static SeekBar W1,W2;
    //private static TextView T1,T2;
    private static Integer V1 =0,V2=0 ;
    private static Double[] points1,points2,total,user;
    public static  Integer color1,color2, color3 ,color4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        the1Wave();
        the2Wave();
        randomizing();
        initialGraphs();
    }

    //testing how to make the first wave
    public void the1Wave() {
        W1 = findViewById(R.id.wave1Bar);
        //T1 = findViewById(R.id.wave1Text);


        W1.setOnSeekBarChangeListener(

                new SeekBar.OnSeekBarChangeListener() {


                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        V1 = progress;
                        //T1.setText("W1 " + V1);
                        userGraphs();
                        success();
//                        playerOutput();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        //T1.setText("W1 " + V1);
//                        playerOutput();

                    }
                }
        );


    }

    //second wave
    public void the2Wave() {
        W2 = findViewById(R.id.wave2Bar);
        //T2 = findViewById(R.id.wave2Text);
        W2.setOnSeekBarChangeListener(

                new SeekBar.OnSeekBarChangeListener() {



                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                        V2= progress;
                        //T2.setText("W2 " + V2);
                        userGraphs();
                        success();
//                        playerOutput();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        //T2.setText("W2 " + V2);
//                        playerOutput();
//
                    }
                }
        );
    }


    //    public void playerOutput() {
//        TextView allWaves = findViewById(R.id.allWavesText);
//        allWaves.setText(V1 + " " + V2);
//    }
    public void success()
    {
        double max,min;
        int count=0;
        for (int i=0;i<20;i++)
        {
            max = (points1[i] + points2[i]) + 1.5;
            min = (points1[i] + points2[i]) - 1.5;

            if(user[i]<=max && user[i]>=min)
                count++;
        }
        TextView rest = findViewById(R.id.RandomText);

        if(count==20)
            rest.setText("You did it!");

        else rest.setText("Try Again");
    }
    public void randomizing()
    {

        double Min=-1.0;
        double Max=1.0;
        points1=new Double[20];
        points2=new Double[20];
        total=new Double[20];
        double r1,r2;

        for(int i=0; i<20;i++)
        {
            r1=new Random().nextDouble();
            r2=new Random().nextDouble();
            points1[i]= (int) Math.round((Min + (r1*(Max - Min)))* 100) / (double) 100;
            points2[i]= (int) Math.round((Min + (r2*(Max - Min)))* 100) / (double) 100;
            total[i]=points1[i]+points2[i];
        }

        Random rnd = new Random();
        color1 = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        color2= Color.argb(255, 100, rnd.nextInt(256), 100);
        color3= Color.argb(255, rnd.nextInt(256), 100, rnd.nextInt(256));
        color4= Color.argb(255, 100, rnd.nextInt(256), rnd.nextInt(256));


    }
    //
    public void initialGraphs()
    {
        GraphView graph1 = findViewById(R.id.graph1);
        GraphView graph2 = findViewById(R.id.graph2);
        GraphView graph3 = findViewById(R.id.graph3);
        GraphView graph4 = findViewById(R.id.graph4);

        graph1.getGridLabelRenderer().setGridColor(0);
        graph1.getGridLabelRenderer().setHorizontalLabelsColor(0);
        graph1.getGridLabelRenderer().setVerticalLabelsColor(0);
        graph2.getGridLabelRenderer().setGridColor(0);
        graph2.getGridLabelRenderer().setHorizontalLabelsColor(0);
        graph2.getGridLabelRenderer().setVerticalLabelsColor(0);
        graph3.getGridLabelRenderer().setGridColor(0);
        graph3.getGridLabelRenderer().setHorizontalLabelsColor(0);
        graph3.getGridLabelRenderer().setVerticalLabelsColor(0);
        graph4.getGridLabelRenderer().setGridColor(0);
        graph4.getGridLabelRenderer().setHorizontalLabelsColor(0);
        graph4.getGridLabelRenderer().setVerticalLabelsColor(0);


        LineGraphSeries<DataPoint> series1 = new LineGraphSeries<DataPoint>();
        double p;

        for(int i = 0; i<points1.length; i++)
        {
            p= points1[i];
            series1.appendData(new DataPoint(i,p),true,20);
        }

        series1.setThickness(10);
        series1.setColor(color1);
        graph1.addSeries(series1);


        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>();
        double p2;

        for(int i = 0; i<points2.length; i++)
        {
            p2= points2[i];
            series2.appendData(new DataPoint(i,p2),true,20);
        }
        series2.setThickness(10);
        series2.setColor(color2);
        graph2.addSeries(series2);


        LineGraphSeries<DataPoint> series3 = new LineGraphSeries<DataPoint>();
        double t;

        for(int i = 0; i<total.length; i++)
        {
            t= total[i];
            series3.appendData(new DataPoint(i ,t),true,20);
        }
        series3.setThickness(20);
        series3.setColor(color3);
        graph3.addSeries(series3);


    }

    public void userGraphs()
    {
        GraphView graph1 = findViewById(R.id.graph1);
        GraphView graph2 = findViewById(R.id.graph2);
        GraphView graph4 = findViewById(R.id.graph4);

        graph1.removeAllSeries();
        graph2.removeAllSeries();
        graph4.removeAllSeries();

        user = new Double[20];
        double temp1,temp2;
        double[] p1=new double [20];
        double[] p2=new double [20];

        for(int i =0; i<points1.length; i++)
        {
            p1[i]=points1[i];
            p2[i]=points2[i];
        }

        for(int j=V1; j > 0 ; j--) {
            temp2=p1[0];
            for (int i = 1; i < 20; i++) {
                temp1 = p1[i];
                p1[i-1] = p1[i];
                p1[i] = temp1;
            }
            p1[19]=temp2;
        }
        for(int j=V2; j > 0 ; j--) {
            temp2=p2[0];
            for (int i = 1; i < 20; i++) {
                temp1 = p2[i];
                p2[i-1] = p2[i];
                p2[i] = temp1;
            }
            p2[19]=temp2;
        }

        LineGraphSeries<DataPoint> series1 = new LineGraphSeries<DataPoint>();
        double p;

        for(int i = 0; i<p1.length; i++)
        {
            p= p1[i];
            series1.appendData(new DataPoint(i,p),true,20);
        }
        series1.setThickness(10);
        series1.setColor(color1);
        graph1.addSeries(series1);


        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>();

        for(int i = 0; i<p2.length; i++)
        {
            p= p2[i];
            series2.appendData(new DataPoint(i,p),true,20);
        }
        series2.setThickness(10);
        series2.setColor(color2);
        graph2.addSeries(series2);

        LineGraphSeries<DataPoint> series4 = new LineGraphSeries<DataPoint>();

        for(int i = 0; i<user.length; i++)
        {
            user[i]=p1[i]+p2[i];
            p= user[i];
            series4.appendData(new DataPoint(i,p),true,20);
        }
        series4.setThickness(15);
        series4.setColor(color4);
        graph4.addSeries(series4);
    }
}

