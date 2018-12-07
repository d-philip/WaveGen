package wavegen.wavegengame;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        launchMenu();



    }

    private void launchMenu(){
        Intent menuActivity = new Intent(MainActivity.this, Menu.class);

        startActivityForResult(menuActivity, 1);
    }

    private void launchGame(){
        Intent gameActivity = new Intent(MainActivity.this, Game.class);

        startActivityForResult(gameActivity, 2);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if(data.getBooleanExtra("button_pressed", false)){
                launchGame();
            }
        }

        //Needs to be implemented in Game
        if (requestCode == 2 && resultCode == RESULT_OK){
        }
    }

    public void launchGameHard(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("difficulty_button_press", "hard");
        startActivity(intent);
    }

    public void launchGameMedium(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("difficulty_button_press", "medium");
        startActivity(intent);
    }

    public void launchGameEasy(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("difficulty_button_press", "easy");
        startActivity(intent);
    }

    public void launchInstructions(View view) {
        Intent intent = new Intent(this, InstructionActivity.class);
        startActivity(intent);
    }
}
