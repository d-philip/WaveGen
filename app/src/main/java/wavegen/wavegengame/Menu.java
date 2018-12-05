package wavegen.wavegengame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends Activity implements View.OnClickListener {
    public static final String PLAY_BUTTON = "Play";

    private Button play_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        play_button = (Button) findViewById(R.id.playB);
        play_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.playB){
            buttonPressed(true);
        }
    }

    private void buttonPressed(boolean button){
        Intent result = new Intent();
        result.putExtra("button_pressed", button);
        setResult(RESULT_OK, result);
        finish();
    }
}
