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

        SharedPreferences score = getSharedPreferences("wavescompleted", 0);
        int wavescompleted = score.getInt("wavescompleted", 0);

        String scoretext = "Total Waves: " + wavescompleted;

        TextView scoreview = findViewById(R.id.scoreview);
        scoreview.setText(scoretext);
    }

    public void launchPlayAgain(View view) {
        Intent intent = new Intent (this, Game.class);
        startActivity(intent);
    }

    public void launchMainMenu(View view) {
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }
}
