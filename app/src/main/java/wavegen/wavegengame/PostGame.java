package wavegen.wavegengame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PostGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_game);
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
