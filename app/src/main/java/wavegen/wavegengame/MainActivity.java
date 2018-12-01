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

    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                boolean buttonpressed = data.getBooleanExtra("button_pressed", true);
            }
        }

        //Needs to be implemented in Game
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                String strEditText = data.getStringExtra("restart");
            }
        }
    }
}
