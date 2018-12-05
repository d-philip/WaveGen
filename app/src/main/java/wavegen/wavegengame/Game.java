package wavegen.wavegengame;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import static com.jjoe64.graphview.GridLabelRenderer.GridStyle.NONE;

public class Game extends AppCompatActivity implements View.OnTouchListener, GestureDetector.OnGestureListener{

    private static final String TAG = "Game";

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    //views
    private GraphView graph1, graph2, graphf;
    private View selectedView;

    //variables
    private GestureDetector mGestureDetector;
    private double pan1 = 0;
    private double stretch1 = 0;
    private double pan2 = 0;
    private double stretch2 = 0;

    private LineGraphSeries<DataPoint> series1 = new LineGraphSeries<DataPoint>();
    private LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>();
    private LineGraphSeries<DataPoint> seriesf = new LineGraphSeries<DataPoint>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mGestureDetector = new GestureDetector(this,this);

        //First Input Graph
        graph1 = (GraphView) findViewById(R.id.graphinput1);

        //Second Input Graph
        graph2 = (GraphView) findViewById(R.id.graphinput2);

        //Final Graph
        graphf = (GraphView) findViewById(R.id.graphfinal);

        //Touch Listeners
        graph1.setOnTouchListener(this);
        graph2.setOnTouchListener(this);

        graph1.getViewport().setXAxisBoundsManual(true);
        graph1.getViewport().setMinX(0);
        graph1.getViewport().setMaxX(2* Math.PI);
        graph1.getViewport().setYAxisBoundsManual(true);
        graph1.getViewport().setMinY(-1);
        graph1.getViewport().setMaxY(1);

        graph2.getViewport().setXAxisBoundsManual(true);
        graph2.getViewport().setMinX(0);
        graph2.getViewport().setMaxX(2* Math.PI);
        graph2.getViewport().setYAxisBoundsManual(true);
        graph2.getViewport().setMinY(-1);
        graph2.getViewport().setMaxY(1);

        graphf.getViewport().setXAxisBoundsManual(true);
        graphf.getViewport().setMinX(0);
        graphf.getViewport().setMaxX(2* Math.PI);
        graphf.getViewport().setYAxisBoundsManual(true);
        graphf.getViewport().setMinY(-1);
        graphf.getViewport().setMaxY(1);


        pan1 = Math.random()* 2 * Math.PI;
        stretch1 = Math.random()* 24 + 1; //Num between 1 and 25

        pan2 = Math.random()* 2 * Math.PI;
        stretch2 = Math.random()* 24 + 1; //Num between 1 and 25

        DataPoint wave1[] = getSinWavePts(pan1,stretch1);
        DataPoint wave2[] = getSawWavePts(pan2,stretch2);
        DataPoint wavef[] = getSumTwoWavePts(wave1,wave2);

        series1.resetData(wave1);
        series2.resetData(wave2);
        seriesf.resetData(wavef);

        series1.setThickness(8);
        graph1.getGridLabelRenderer().setGridStyle(NONE);
        graph1.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graph1.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graph1.addSeries(series1);

        series2.setThickness(8);
        graph2.getGridLabelRenderer().setGridStyle(NONE);
        graph2.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graph2.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graph2.addSeries(series2);

        seriesf.setThickness(8);
        graphf.getGridLabelRenderer().setGridStyle(NONE);
        graphf.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graphf.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graphf.addSeries(seriesf);

    }

    public DataPoint[] getRandSimpleWave(double pan, double stretch){
        DataPoint Pts[] = new DataPoint[360];
        int type = (int)(Math.random() * 4);

        switch (type){
            case 0:
                Pts = getSinWavePts(pan, stretch);
                break;
            case 1:
                Pts = getSqrWavePts(pan, stretch);
                break;
            case 2:
                Pts = getSawWavePts(pan, stretch);
                break;
                default:
                    Pts = getSinWavePts(pan,stretch);
        };

        return Pts;
    }

    public DataPoint[] getSqrWavePts(double pan, double stretch){
        DataPoint Pts[] = new DataPoint[360];

        double x = 0;
        double y;
        for(int i = 0; i < 360; i++){
            x = x + Math.PI/180;

            if((i + (pan*360))%(360/stretch)<180/stretch){
                y = -1;
            }
            else{
                y = 1;
            }

            Pts[i] = new DataPoint(x,y);
        }

        return Pts;
    }

    public DataPoint[] getSinWavePts(double pan, double stretch){
        DataPoint Pts[] = new DataPoint[360];

        double x = 0;
        double y;
        for(int i = 0; i < 360; i++){
            x = x + Math.PI/180;
            y = Math.sin(stretch * x + (pan*360)/(2*Math.PI));
            Pts[i] = new DataPoint(x,y);
        }

        return Pts;
    }

    public DataPoint[] getSawWavePts(double pan, double stretch){
        DataPoint Pts[] = new DataPoint[360];

        double x = 0;
        double y;
        for(int i = 0; i < 360; i++){
            x = x + Math.PI/180;
            y = (2*i/360)-1;
            Pts[i] = new DataPoint(x,y);
        }

        return Pts;
    }

    public DataPoint[] getSumTwoWavePts(DataPoint[] wave1, DataPoint[] wave2){
        DataPoint Pts[] = new DataPoint[360];

        double x = 0;
        double y;
        for(int i = 0; i < 360; i++){
            x = x + Math.PI/180;
            y = (wave1[i].getY() + wave2[i].getY())/2;
            Pts[i] = new DataPoint(x,y);
        }

        return Pts;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent){
        if(view.getId() == R.id.graphinput1){
            selectedView = graph1;
        }
        if(view.getId() == R.id.graphinput2){
            selectedView = graph2;
        }

        mGestureDetector.onTouchEvent(motionEvent);
        return true;
    }


    //Gesture Detector

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

        if(selectedView == graph1){
            pan1 += distanceX / graph1.getWidth();
        }
        if(selectedView == graph2){
            pan2 += distanceX / graph2.getWidth();
        }

        Log.d(TAG, "onScroll: called.");


        String panLog = "pan1 = " + pan1;
        Log.d(TAG, panLog);

        DataPoint wave1[] = getSinWavePts(pan1,stretch1);
        DataPoint wave2[] = getSawWavePts(pan2,stretch2);
        DataPoint wavef[] = getSumTwoWavePts(wave1,wave2);

        series1.resetData(wave1);
        series2.resetData(wave2);
        seriesf.resetData(wavef);

        Log.d(TAG, "completed transformation");

        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}


