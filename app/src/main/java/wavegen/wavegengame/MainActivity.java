package wavegen.wavegengame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.graphics.Canvas;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get wavescompleted, send it to the recordlines view, and draw the view
        SharedPreferences score = getSharedPreferences("wavescompleted", 0);
        int wavescompleted = score.getInt("wavescompleted", 0);
        Canvas canvas = new Canvas();
        RecordLines recordlines = findViewById(R.id.recordlines);
        recordlines.setWavesCompleted(wavescompleted);

        //Animate Menu
        ImageView record = findViewById(R.id.recordview);
        Animation rotate = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_clockwise);
        record.startAnimation(rotate);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void launchGame(View view) {
        Intent intent = new Intent(this, Game.class);
        startActivity(intent);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }
}
