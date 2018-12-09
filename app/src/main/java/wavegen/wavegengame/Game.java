package wavegen.wavegengame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import java.util.Random;
import static android.graphics.Color.argb;
import static com.jjoe64.graphview.GridLabelRenderer.GridStyle.NONE;

public class Game extends AppCompatActivity implements View.OnTouchListener, GestureDetector.OnGestureListener{

    private static final String TAG = "Game"; //Debug tag

    //Int values of waves
    private static final int SIN = 0;
    private static final int SQR = 1;
    private static final int BMP = 2;


    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    //views
    private GraphView graph1, graph2, graph3, graphf;
    private View selectedView;

    private GestureDetector mGestureDetector;

    //3 graphs, each with a type, pan, goal pan, and stretch
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

    private double percentoff = .15; //amount a graph can be off to still be considered correct

    //status of whether user is scrolling
    private boolean hasWon = false;

    //5 series, will hold the datapoints for the graphs
    private LineGraphSeries<DataPoint> series1 = new LineGraphSeries<DataPoint>();
    private LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>();
    private LineGraphSeries<DataPoint> series3 = new LineGraphSeries<DataPoint>();
    private LineGraphSeries<DataPoint> seriesuserf = new LineGraphSeries<DataPoint>();
    private LineGraphSeries<DataPoint> seriesf = new LineGraphSeries<DataPoint>();

    //Arrays for the curves used for applying a circle transformation;
    DataPoint circlecurve1[];
    DataPoint circlecurve2[];
    DataPoint circlecurve3[];
    DataPoint circlecurvef[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mGestureDetector = new GestureDetector(this,this); //init gesture detector

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

        //Setting the x and y bounds of the graphs
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

        Random generator = new Random();//Init rand num generator for int values

        //randomize type, pan, goal pan, and stretch for each graph
        type1 = generator.nextInt(3);
        pan1 = Math.random()* 2 * Math.PI;
        panfinal1 = Math.random()* 2 * Math.PI;
        stretch1 = Math.random()* 4 + 1; //Num between 1 and 5

        type2 = generator.nextInt(3);
        pan2 = Math.random()* 2 * Math.PI;
        panfinal2 = Math.random()* 2 * Math.PI;
        stretch2 = Math.random()* 4 + 1; //Num between 1 and 5

        type3 = generator.nextInt(3);
        pan3 = Math.random()* 2 * Math.PI;
        panfinal3 = Math.random()* 2 * Math.PI;
        stretch3 = Math.random()* 4 + 1; //Num between 1 and 5

        //Print type values to log for debugging
        String typetag1 = "type1 = " + type1;
        String typetag2 = "type2 = " + type2;
        String typetag3 = "type3 = " + type3;
        Log.d(TAG, typetag1);
        Log.d(TAG, typetag2);
        Log.d(TAG, typetag3);


        //Get data points for waves based on pans, stretches, and types, and then sum them to get the user's final/combined wave
        DataPoint wave1[] = getSimpleWave(pan1,stretch1,type1);
        DataPoint wave2[] = getSimpleWave(pan2,stretch2,type2);
        DataPoint wave3[] = getSimpleWave(pan3,stretch3,type3);
        DataPoint waveuserf[] = getSumThreeWavePts(wave1,wave2,wave3);

        //Get data points for final waves to then make the goal wave based on them
        DataPoint wavefinal1[] = getSimpleWave(panfinal1,stretch1, type1);
        DataPoint wavefinal2[] = getSimpleWave(panfinal2,stretch2, type2);
        DataPoint wavefinal3[] = getSimpleWave(panfinal3,stretch3, type3);
        DataPoint wavef[] = getSumThreeWavePts(wavefinal1,wavefinal2,wavefinal3);

        //Get data points for the waves that will curve the waves, and then combine them with each wave to curve them
        circlecurve1 = getCircleWavePts(6);
        circlecurve2 = getCircleWavePts(7);
        circlecurve3 = getCircleWavePts(8);
        circlecurvef = getCircleWavePts(9);
        DataPoint wave1c[] = getSumTwoWavePts(wave1, circlecurve1);
        DataPoint wave2c[] = getSumTwoWavePts(wave2, circlecurve2);
        DataPoint wave3c[] = getSumTwoWavePts(wave3, circlecurve3);
        DataPoint waveuserfc[] = getSumTwoWavePts(waveuserf, circlecurvef);
        DataPoint wavefc[] = getSumTwoWavePts(wavef, circlecurvef);

        //Set the graph's series to the waves
        series1.resetData(wave1c);
        series2.resetData(wave2c);
        series3.resetData(wave3c);
        seriesuserf.resetData(waveuserfc);
        seriesf.resetData(wavefc);

        //Set colors of graphs
        int graphcolor = argb(255, 227, 221, 182);
        int graphcolorf = argb(255, 0, 0, 0);
        int graphthickness = 8;
        int graphthicknessuser = 10;

        //Change graph properties, and set which series go to which graphs
        series1.setThickness(graphthicknessuser);
        series1.setColor(graphcolor);
        graph1.getGridLabelRenderer().setGridStyle(NONE);
        graph1.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graph1.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graph1.addSeries(series1);

        series2.setThickness(graphthicknessuser);
        series2.setColor(graphcolor);
        graph2.getGridLabelRenderer().setGridStyle(NONE);
        graph2.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graph2.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graph2.addSeries(series2);

        series3.setThickness(graphthicknessuser);
        series3.setColor(graphcolor);
        graph3.getGridLabelRenderer().setGridStyle(NONE);
        graph3.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graph3.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graph3.addSeries(series3);

        seriesf.setThickness(graphthickness);
        seriesf.setColor(graphcolorf);
        seriesuserf.setThickness(graphthicknessuser);
        seriesuserf.setColor(graphcolor);
        graphf.getGridLabelRenderer().setGridStyle(NONE);
        graphf.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graphf.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graphf.addSeries(seriesf);
        graphf.addSeries(seriesuserf);

    }

    public DataPoint[] getSimpleWave(double pan, double stretch, int type){ //Returns data points of wave with given arguments
        DataPoint Pts[] = new DataPoint[360];
        switch (type){
            case SIN:
                Pts = getSinWavePts(pan, stretch);
                break;
            case SQR:
                Pts = getSqrWavePts(pan, stretch);
                break;
            case BMP:
                Pts = getBmpWavePts(pan, stretch);
                break;
                default:
                    Pts = getSinWavePts(pan,stretch);
        };
        return Pts;
    }

    public DataPoint[] getSinWavePts(double pan, double stretch){ //Returns data points for a sine wave with given arguments
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

    public DataPoint[] getSqrWavePts(double pan, double stretch){ //Returns data points for a square wave with given arguments
        DataPoint Pts[] = new DataPoint[360];

        double x = 0;
        double y;
        for(int i = 0; i < 360; i++){
            if(Math.sin(stretch *x - pan)>=0) {
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

    public DataPoint[] getBmpWavePts(double pan, double stretch){ //Returns data points for a "bumpy" graph with given arguments
        DataPoint Pts[] = new DataPoint[360];

        double x = 0;
        double y;
        for(int i = 0; i < 360; i++){
            y = 2*Math.abs(Math.sin((.5)*(stretch * x - pan)))-1;
            Pts[i] = new DataPoint(x,y);
            x = x + Math.PI/180;
        }

        return Pts;
    }

    public DataPoint[] getCircleWavePts(double radius){ //Returns a set of datapoints for a circle graph with radius r shifted for the domain and range of the graphs
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

    public DataPoint[] getSumTwoWavePts(DataPoint[] wave1, DataPoint[] wave2){ //Sums the data points of two waves
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

    public DataPoint[] getSumThreeWavePts(DataPoint[] wave1, DataPoint[] wave2, DataPoint[] wave3){ //Sums the data points of three waves
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


    public boolean checkCorrectPan(double pan, double panfinal){ //Checks if the pan is close enough to panfinal
        Log.d(TAG, "Percent Off: " + Math.abs(pan%(2*Math.PI)-panfinal)/((pan%(2*Math.PI)+panfinal)/2));

        double panmin = panfinal - panfinal * percentoff;
        double panmax = panfinal + panfinal * percentoff;

        if(pan >= 0){
            if(panmin <= pan%(2*Math.PI) && pan%(2*Math.PI) <= panmax) {
                return true;
            }
        }
        else{
            if(panmin <= 2*Math.PI + pan%(2*Math.PI) && 2*Math.PI + pan%(2*Math.PI) <= panmax) {
                return true;
            }
        }

        return false;
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent){

        //Detect which graph was touched
        if(view.getId() == R.id.graphinput1){
            selectedView = graph1;
        }
        if(view.getId() == R.id.graphinput2){
            selectedView = graph2;
        }
        if(view.getId() == R.id.graphinput3){
            selectedView = graph3;
        }

        mGestureDetector.onTouchEvent(motionEvent); //send motionEvent to gesture detector
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

        if(!hasWon) {
            if (selectedView == graph1) {
                pan1 -= distanceX / graph1.getWidth() * stretch1 * 2 * Math.PI;
            }
            if (selectedView == graph2) {
                pan2 -= distanceX / graph2.getWidth() * stretch2 * 2 * Math.PI;
            }
            if (selectedView == graph3) {
                pan3 -= distanceX / graph3.getWidth() * stretch3 * 2 * Math.PI;
            }

            //Print that OnScroll was called
            Log.d(TAG, "onScroll: called.");

            //Print goal pan and pan values to log for debugging
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

            //get new wave data points
            DataPoint wave1[] = getSimpleWave(pan1, stretch1, type1);
            DataPoint wave2[] = getSimpleWave(pan2, stretch2, type2);
            DataPoint wave3[] = getSimpleWave(pan3, stretch3, type3);
            DataPoint waveuserf[] = getSumThreeWavePts(wave1, wave2, wave3);

            DataPoint wave1c[] = getSumTwoWavePts(wave1, circlecurve1);
            DataPoint wave2c[] = getSumTwoWavePts(wave2, circlecurve2);
            DataPoint wave3c[] = getSumTwoWavePts(wave3, circlecurve3);
            DataPoint waveuserfc[] = getSumTwoWavePts(waveuserf, circlecurvef);

            //reset series with new data points
            series1.resetData(wave1c);
            series2.resetData(wave2c);
            series3.resetData(wave3c);
            seriesuserf.resetData(waveuserfc);

            //Print message to log if code above ran successfully for debugging
            Log.d(TAG, "completed transformation");


            if (checkCorrectPan(pan1, panfinal1) && checkCorrectPan(pan2, panfinal2) && checkCorrectPan(pan3, panfinal3)) { //Check win condition
                //Print message to log for debugging
                Log.d(TAG, "You Win!");

                hasWon = true;
                trackScore();
                //Thread.sleep(2000);

                //Switch to PostGame intent
                launchPostGame();
                Intent winintent = new Intent(this, PostGame.class);
                startActivity(winintent);
            }
        }
        return false;
    }

    public void trackScore(){
        //Keeps track of score
        int currentscore;
        SharedPreferences score = getSharedPreferences("wavescompleted", 0);
        if(score == null){
            currentscore = 0;
        }
        else{
            currentscore = score.getInt("wavescompleted", 0) + 1;
        }
        SharedPreferences.Editor editor = score.edit();
        editor.putInt("wavescompleted", currentscore++);
        editor.apply();
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }

    public void launchPostGame() {
        Intent intent = new Intent(this, PostGame.class);
        startActivity(intent);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }
}


