package edu.quinnipiac.ser210.fourinarow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * FourInARow
 * @author MacArthur Mills
 * @date 2/20/2022
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Call onStart() when the start button is clicked
    public void onButtonStart(View view) {
        EditText name = (EditText) findViewById(R.id.playerName);
        String playerName = name.getText().toString();

        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("playerName", playerName);

        startActivity(intent);
    }
}