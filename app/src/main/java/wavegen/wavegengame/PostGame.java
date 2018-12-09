package wavegen.wavegengame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class PostGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_game);

        //retrieve score form shared preferences and set the text of TextView scoreview
        SharedPreferences score = getSharedPreferences("wavescompleted", 0);
        int wavescompleted = score.getInt("wavescompleted", 0);

        String scoretext = "Total Waves: " + wavescompleted;

        TextView scoreview = findViewById(R.id.scoreview);
        scoreview.setText(scoretext);
    }

    @Override
    public void onBackPressed(){
        //Left empty to disable back-press when in Post-Game
    }

    public void launchPlayAgain(View view) {
        Intent intent = new Intent (this, Game.class);
        startActivity(intent);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }

    public void launchMainMenu(View view) {
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }
}
