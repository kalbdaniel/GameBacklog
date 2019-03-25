package nl.hva.dka.gamebacklog;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import nl.hva.dka.gamebacklog.Database.GameRecyclerViewAdapter;

public class MainActivity extends AppCompatActivity implements RecyclerView.OnItemTouchListener, Serializable {

    private GameRecyclerViewAdapter mGameRecyclerViewAdapter;
    private RecyclerView mRecyclerView;
    private ViewModelGame mViewModelGame;
    private List<Game> mGames;
    private GestureDetector mGestureDetector;
    public static final int RC_ADD_GAME = 1;
    public static final int RC_EDIT_GAME = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.addNewGame);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddGame.class);
                startActivityForResult(i, RC_ADD_GAME);
            }
        });

        //first the mRecyclerView
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initialize all lists
        mGames = new LinkedList<>();

        //create the ViewModel
        mViewModelGame = ViewModelProviders.of(this).get(ViewModelGame.class);
        mViewModelGame.getAllGames().observe(this, new Observer<List<Game>>() {
            @Override
            public void onChanged(@Nullable List<Game> gameList) {
                mGames = gameList;
                updateUI();
            }
        });

        //Swiping with a ItemTouchHelper
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                int pos = viewHolder.getAdapterPosition();

                Game tempGame = mGames.get(pos);
                mViewModelGame.deleteGame(mGames.get(pos));
                mGames.remove(pos);
                mGameRecyclerViewAdapter.notifyItemRemoved(pos);

                Snackbar.make(findViewById(android.R.id.content), tempGame.getTitle() + " has been deleted", Snackbar.LENGTH_LONG)
                        .show();
            }
        };

        mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.addOnItemTouchListener(this);
    }

    private void updateUI() {
        if (mGameRecyclerViewAdapter == null) {
            mGameRecyclerViewAdapter = new GameRecyclerViewAdapter(mGames);
            mRecyclerView.setAdapter(mGameRecyclerViewAdapter);
        } else {
            mGameRecyclerViewAdapter.swapList(mGames);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_delete_all) {

            for (int i = mGames.size() - 1; i >= 0; i--) {

                mViewModelGame.deleteGame(mGames.get(i));
                mGames.remove(i);
            }

            updateUI();

            Snackbar.make(findViewById(android.R.id.content), R.string.delete_all, Snackbar.LENGTH_SHORT)
                    .show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RC_ADD_GAME) {
            if (resultCode == Activity.RESULT_OK) {
                String title = data.getStringExtra("title");
                String status = data.getStringExtra("status");
                String platform = data.getStringExtra("platform");
                String date = data.getStringExtra("date");

                Game newGame = new Game(title, status, platform, date);
                // New timestamp: timestamp of the update time
                mViewModelGame.insertGame(newGame);
                updateUI();

            }
        }

        if (requestCode == RC_EDIT_GAME) {
            if (resultCode == Activity.RESULT_OK) {

                final Game editedGame = (Game) data.getSerializableExtra("GAME");

                // New timestamp: timestamp of update
                mViewModelGame.updateGame(editedGame);
                updateUI();

            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
        if (child == null) return false;
        int childAdapterPosition = mRecyclerView.getChildAdapterPosition(child);
        if (mGestureDetector.onTouchEvent(e)){
            Intent intent = new Intent(MainActivity.this, EditGame.class);
            Game editGame = mGames.get(childAdapterPosition);
            intent.putExtra("GAME", editGame);
            startActivityForResult(intent, RC_EDIT_GAME);
        }
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {

    }
}
