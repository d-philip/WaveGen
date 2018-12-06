package wavegen.wavegengame;

import android.graphics.Color;
import android.provider.ContactsContract;
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

import java.util.Random;
import java.util.logging.StreamHandler;

import static com.jjoe64.graphview.GridLabelRenderer.GridStyle.NONE;

public class Game extends AppCompatActivity implements View.OnTouchListener, GestureDetector.OnGestureListener{

    private static final String TAG = "Game";

    private static final int SIN = 0;
    private static final int SQR = 1;
    private static final int SAW = 2;
    private static final int TRI = 3;


    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    //views
    private GraphView graph1, graph2, graph3, graphf;
    private View selectedView;

    //variables
    private GestureDetector mGestureDetector;
    private int type1 = SIN;
    private double pan1 = 0;
    private double panfinal1 = 0;
    private double stretch1 = 0;

    private int type2 = SIN;
    private double pan2 = 0;
    private double panfinal2 = 0;
    private double stretch2 = 0;

    private int type3 = SIN;
    private double pan3 = 0;
    private double panfinal3 = 0;
    private double stretch3 = 0;

    private double percentoff = .1; // percent off the pan values can be off to win
    private boolean finalcorrect = false;

    private LineGraphSeries<DataPoint> series1 = new LineGraphSeries<DataPoint>();
    private LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>();
    private LineGraphSeries<DataPoint> series3 = new LineGraphSeries<DataPoint>();
    private LineGraphSeries<DataPoint> seriesuserf = new LineGraphSeries<DataPoint>();
    private LineGraphSeries<DataPoint> seriesf = new LineGraphSeries<DataPoint>();

    DataPoint circlecurve1[];
    DataPoint circlecurve2[];
    DataPoint circlecurve3[];
    DataPoint circlecurvef[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mGestureDetector = new GestureDetector(this,this);

        //First Input Graph
        graph1 = (GraphView) findViewById(R.id.graphinput1);

        //Second Input Graph
        graph2 = (GraphView) findViewById(R.id.graphinput2);

        //Third Input Graph
        graph3 = (GraphView) findViewById(R.id.graphinput3);

        //Final Graph
        graphf = (GraphView) findViewById(R.id.graphfinal);

        //Touch Listeners
        graph1.setOnTouchListener(this);
        graph2.setOnTouchListener(this);
        graph3.setOnTouchListener(this);

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

        graph3.getViewport().setXAxisBoundsManual(true);
        graph3.getViewport().setMinX(0);
        graph3.getViewport().setMaxX(2* Math.PI);
        graph3.getViewport().setYAxisBoundsManual(true);
        graph3.getViewport().setMinY(-1);
        graph3.getViewport().setMaxY(1);

        graphf.getViewport().setXAxisBoundsManual(true);
        graphf.getViewport().setMinX(0);
        graphf.getViewport().setMaxX(2* Math.PI);
        graphf.getViewport().setYAxisBoundsManual(true);
        graphf.getViewport().setMinY(-1);
        graphf.getViewport().setMaxY(1);

        Random generator = new Random();

        type1 = generator.nextInt(3);
        pan1 = Math.random()* 2 * Math.PI;
        panfinal1 = Math.random()* 2 * Math.PI;
        stretch1 = Math.random()* 9 + 1; //Num between 1 and 10

        type2 = generator.nextInt(3);
        pan2 = Math.random()* 2 * Math.PI;
        panfinal2 = Math.random()* 2 * Math.PI;
        stretch2 = Math.random()* 9 + 1; //Num between 1 and 10

        type3 = generator.nextInt(3);
        pan3 = Math.random()* 2 * Math.PI;
        panfinal3 = Math.random()* 2 * Math.PI;
        stretch3 = Math.random()* 9 + 1; //Num between 1 and 10

        String typetag1 = "type1 = " + type1;
        String typetag2 = "type2 = " + type2;
        String typetag3 = "type3 = " + type3;

        Log.d(TAG, typetag1);
        Log.d(TAG, typetag2);
        Log.d(TAG, typetag3);


        DataPoint wave1[] = getSimpleWave(pan1,stretch1,type1);
        DataPoint wave2[] = getSimpleWave(pan2,stretch2,type2);
        DataPoint wave3[] = getSimpleWave(pan3,stretch3,type3);
        DataPoint waveuserf[] = getSumThreeWavePts(wave1,wave2,wave3);

        DataPoint wavefinal1[] = getSimpleWave(panfinal1,stretch1, type1);
        DataPoint wavefinal2[] = getSimpleWave(panfinal2,stretch2, type2);
        DataPoint wavefinal3[] = getSimpleWave(panfinal3,stretch3, type3);
        DataPoint wavef[] = getSumThreeWavePts(wavefinal1,wavefinal2,wavefinal3);

        circlecurve1 = getCircleWavePts(7);
        circlecurve2 = getCircleWavePts(8);
        circlecurve3 = getCircleWavePts(9);
        circlecurvef = getCircleWavePts(10);

        DataPoint wave1c[] = getSumTwoWavePts(wave1, circlecurve1);
        DataPoint wave2c[] = getSumTwoWavePts(wave2, circlecurve2);
        DataPoint wave3c[] = getSumTwoWavePts(wave3, circlecurve3);
        DataPoint waveuserfc[] = getSumTwoWavePts(waveuserf, circlecurvef);

        DataPoint wavefc[] = getSumTwoWavePts(wavef, circlecurvef);

        series1.resetData(wave1c);
        series2.resetData(wave2c);
        series3.resetData(wave3c);
        seriesuserf.resetData(waveuserfc);
        seriesf.resetData(wavefc);


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

        series3.setThickness(8);
        graph3.getGridLabelRenderer().setGridStyle(NONE);
        graph3.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graph3.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graph3.addSeries(series3);

        seriesuserf.setThickness(8);
        graphf.getGridLabelRenderer().setGridStyle(NONE);
        graphf.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graphf.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graphf.addSeries(seriesf);
        graphf.addSeries(seriesuserf);

    }

    public DataPoint[] getSimpleWave(double pan, double stretch, int type){
        DataPoint Pts[] = new DataPoint[360];
        switch (type){
            case SIN:
                Pts = getSinWavePts(pan, stretch);
                break;
            case SQR:
                Pts = getSqrWavePts(pan, stretch);
                break;
            //case SAW:
                //Pts = getSawWavePts(pan, stretch);
                //break;
                default:
                    Pts = getSinWavePts(pan,stretch);
        };
        return Pts;
    }

    public DataPoint[] getSinWavePts(double pan, double stretch){
        DataPoint Pts[] = new DataPoint[360];

        double x = 0;
        double y;
        for(int i = 0; i < 360; i++){
            y = Math.sin(stretch * x - pan);
            Pts[i] = new DataPoint(x,y);
            x = x + Math.PI/180;
        }

        return Pts;
    }

    public DataPoint[] getSqrWavePts(double pan, double stretch){
        DataPoint Pts[] = new DataPoint[360];

        double x = 0;
        double y;
        for(int i = 0; i < 360; i++){
            if(Math.sin(stretch*x - pan)>=0) {
                y = 1;
            }
            else{
                y = -1;
            }
            Pts[i] = new DataPoint(x, y);
            x = x + Math.PI / 180;
        }

        return Pts;
    }

    public DataPoint[] getTriWavePts(double pan, double stretch){
        DataPoint Pts[] = new DataPoint[360];

        double x = 0;
        double y = -1;
        for(int i = 0; i < 360; i++){
            y += (x-pan)/Math.PI;
            if(y >= 1){
                y = -1;
                x = x%(2*Math.PI);
            }
            Pts[i] = new DataPoint(x,y);
            x = x + Math.PI/180;
        }

        return Pts;
    }


    /*
    public DataPoint[] getSawWavePts(double pan, double stretch){
        DataPoint Pts[] = new DataPoint[360];

        double x = 0;
        double y = (x-pan);
        double pancount = pan%(2*Math.PI);
        for(double j = 0; j < pan%(2*Math.PI); j+=Math.PI/180){
            if(Math.sin(x - pan) > 0){
                y += 1/(2*Math.PI);
            }
            else{
                y -= 1/(2*Math.PI);
            }
            pancount -= Math.PI/180;
            x = x + Math.PI/180;
        }
        if(Math.sin(x-pan) > 0){
            y += pancount * 1/(2*Math.PI);
        }
        else{
            y -= pancount * 1/(2*Math.PI);
        }
        x = 0;
        for(int i = 0; i < 360; i++){
            Pts[i] = new DataPoint(x,y);
            if(Math.sin(x - pan) > 0){
                y += 1/(2*Math.PI);
            }
            else{
                y -= 1/(2*Math.PI);
            }
            Pts[i] = new DataPoint(x,y);
            x = x + Math.PI/180;
        }

        return Pts;
    }
    */

    public DataPoint[] getCircleWavePts(double radius){
        DataPoint Pts[] = new DataPoint[360];

        double x = 0;
        double y;
        double y1 = Math.sqrt(Math.pow(radius,2) - Math.pow(x - Math.PI,2));
        for(int i = 0; i < 360; i++){
            y = Math.sqrt(Math.pow(radius,2) - Math.pow(x - Math.PI,2)) - y1;
            Pts[i] = new DataPoint(x, y);
            x = x + Math.PI / 180;
        }

        return Pts;
    }

    public DataPoint[] getSumTwoWavePts(DataPoint[] wave1, DataPoint[] wave2){
        DataPoint Pts[] = new DataPoint[360];

        double x = 0;
        double y;
        for(int i = 0; i < 360; i++){
            y = (wave1[i].getY() + wave2[i].getY())/2;
            Pts[i] = new DataPoint(x,y);
            x = x + Math.PI/180;
        }

        return Pts;
    }

    public DataPoint[] getSumThreeWavePts(DataPoint[] wave1, DataPoint[] wave2, DataPoint[] wave3){
        DataPoint Pts[] = new DataPoint[360];

        double x = 0;
        double y;
        for(int i = 0; i < 360; i++){
            y = (wave1[i].getY() + wave2[i].getY() + wave3[i].getY())/3;
            Pts[i] = new DataPoint(x,y);
            x = x + Math.PI/180;
        }

        return Pts;
    }

    /*
    public DataPoint[] initRandomizedPts()
    {
        DataPoint[] Pts = new DataPoint[360];

        double Min=-1.0;
        double Max=1.0;
        double y = Math.random()*2-1;
        double x = 0;
        double prev = 0;
        double constraint = .01;

        Pts[0] = new DataPoint(x,y);
        x = x + Math.PI/180;
        for(int i=1; i<180;i++)
        {
            y = prev + Math.random()*2*constraint-constraint;
            prev = y;
            Pts[i] = new DataPoint(x,y);
            x = x + Math.PI/180;
        }
        for(int i=180; i<360;i++)
        {
            Pts[i]  = new DataPoint(Pts[180-i].getX(),Pts[180-i].getY());
            x = x + Math.PI/180;
        }

        return Pts;
    }

    public DataPoint[] getRandomizedWavePts(LineGraphSeries<DataPoint> series){
        DataPoint Pts[] = new DataPoint[360];

        + pan3

        double x = 0;
        double y;
        for(int i = 0; i < 360; i++){
            y = (wave1[i].getY() + wave2[i].getY())/2;
            Pts[i] = new DataPoint(x,y);
            x = x + Math.PI/180;
        }

        return Pts;
    }
*/


    public boolean checkCorrectDataPoints(DataPoint wave1[], DataPoint wave2[]) {
        for(int i = 0; i < 360; i++){
            if(Math.abs(wave1[i].getY()-wave2[i].getY())/((wave1[i].getY()+wave2[i].getY())/2) > percentoff){
                return false;
            }
        }
        return true;
    }

    public boolean checkCorrectPan(double pan, double panfinal){
        Log.d(TAG, "Percent Off: " + Math.abs(pan%(2*Math.PI)-panfinal)/((pan%(2*Math.PI)+panfinal)/2));

        double panmin = panfinal - panfinal * percentoff;
        double panmax = panfinal + panfinal * percentoff;

        if(pan >= 0){
            if(panmin <= pan%(2*Math.PI) && pan%(2*Math.PI) <= panmax) {
                return true;
            }
        }
        else{
            if(panmin <= pan%(2*Math.PI) && pan%(2*Math.PI) <= panmax) {
                return true;
            }
        }

        return false;
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent){
        if(view.getId() == R.id.graphinput1){
            selectedView = graph1;
        }
        if(view.getId() == R.id.graphinput2){
            selectedView = graph2;
        }
        if(view.getId() == R.id.graphinput3){
            selectedView = graph3;
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
            pan1 -= distanceX / graph1.getWidth() * stretch1 * 2 * Math.PI;
        }
        if(selectedView == graph2){
            pan2 -= distanceX / graph2.getWidth() * stretch2 * 2 * Math.PI;
        }
        if(selectedView == graph3){
            pan3 -= distanceX / graph3.getWidth() * stretch3 * 2 * Math.PI;
        }

        Log.d(TAG, "onScroll: called.");


        String panLog = "panfinal1 = " + panfinal1;
        Log.d(TAG, panLog);
        panLog = "panfinal2 = " + panfinal2;
        Log.d(TAG, panLog);
        panLog = "panfinal3 = " + panfinal3;
        Log.d(TAG, panLog);

        panLog = "pan1 = " + pan1;
        Log.d(TAG, panLog);
        panLog = "pan2 = " + pan2;
        Log.d(TAG, panLog);
        panLog = "pan3 = " + pan3;
        Log.d(TAG, panLog);

        DataPoint wave1[] = getSimpleWave(pan1,stretch1,type1);
        DataPoint wave2[] = getSimpleWave(pan2,stretch2,type2);
        DataPoint wave3[] = getSimpleWave(pan3,stretch3,type3);
        DataPoint waveuserf[] = getSumThreeWavePts(wave1,wave2,wave3);

        DataPoint wave1c[] = getSumTwoWavePts(wave1, circlecurve1);
        DataPoint wave2c[] = getSumTwoWavePts(wave2, circlecurve2);
        DataPoint wave3c[] = getSumTwoWavePts(wave3, circlecurve3);
        DataPoint waveuserfc[] = getSumTwoWavePts(waveuserf, circlecurvef);

        series1.resetData(wave1c);
        series2.resetData(wave2c);
        series3.resetData(wave3c);
        seriesuserf.resetData(waveuserfc);

        Log.d(TAG, "completed transformation");



        if(checkCorrectPan(pan1,panfinal1)&&checkCorrectPan(pan2,panfinal2)&&checkCorrectPan(pan3,panfinal3)){
            Log.d(TAG, "You Win!");
        }

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


