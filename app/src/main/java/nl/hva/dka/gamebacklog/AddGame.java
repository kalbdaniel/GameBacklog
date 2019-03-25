package nl.hva.dka.gamebacklog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddGame extends AppCompatActivity {
    private String mDateFormat = "dd/MM/yyyy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);

        final Spinner dropdown = findViewById(R.id.spinnerCreate);
        final EditText titleInput = findViewById(R.id.titleInput);
        final EditText platformInput = findViewById(R.id.platformInput);

        FloatingActionButton fab = findViewById(R.id.saveGame);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dropdownChoice = dropdown.getSelectedItem().toString();
                String title = titleInput.getText().toString();
                String platform = platformInput.getText().toString();

                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat(mDateFormat);
                Calendar calendar = Calendar.getInstance();
                String date = simpleDateFormat.format(calendar.getTime());

                Intent returnInten = new Intent();
                returnInten.putExtra("title", title);
                returnInten.putExtra("platform", platform);
                returnInten.putExtra("status", dropdownChoice);
                returnInten.putExtra("date", date);
                setResult(Activity.RESULT_OK, returnInten);
                finish();

            }

        });
        //for the dropdown
        String[] items = new String[]{"","Want to play", "Playing", "Stalled", "Dropped"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
    }
}
