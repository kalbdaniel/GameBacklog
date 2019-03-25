package nl.hva.dka.gamebacklog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditGame extends AppCompatActivity {

    private String mDateFormat = "dd/MM/yyyy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_game);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText titleEdit = findViewById(R.id.titleInput);
        final EditText platformEdit = findViewById(R.id.platformInput);

        //dropdown
        final Spinner dropdown = findViewById(R.id.spinnerEdit);
        String[] items = new String[]{"", getString(R.string.dropdown_1), getString(R.string.dropdown_2), getString(R.string.dropdown_3), getString(R.string.dropdown_4)};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        Intent intent = getIntent();
        final Game game = (Game) intent.getSerializableExtra("GAME");

        titleEdit.setText(game.getTitle());
        String status = game.getStatus();
        if (status == null) {
            status = "";
        }
        dropdown.setSelection(getDropdownValue(status));
        platformEdit.setText(game.getPlatform());

        // the save button
        FloatingActionButton saveButton = findViewById(R.id.saveEditGame);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String dropdownItem = dropdown.getSelectedItem().toString();
                String title = titleEdit.getText().toString();
                String platform = platformEdit.getText().toString();

                // date
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(mDateFormat);
                Calendar calendar = Calendar.getInstance();
                String date = simpleDateFormat.format(calendar.getTime());

                // values of the game
                game.setDate(date);
                game.setTitle(title);
                game.setPlatform(platform);
                game.setStatus(dropdownItem);

                Intent resultInt = new Intent();
                resultInt.putExtra("GAME", game);
                setResult(Activity.RESULT_OK, resultInt);
                finish();
            }
        });
    }

    private int getDropdownValue(String value) {
        int pos = -1;
        switch (value) {
            case "":
                pos = 0;
                break;
            case "Want to play":
                pos = 1;
                break;
            case "Playing":
                pos = 2;
                break;
            case "Stalled":
                pos = 3;
                break;
            case "Dropped":
                pos = 4;
                break;

        }
        return pos;
    }

}
