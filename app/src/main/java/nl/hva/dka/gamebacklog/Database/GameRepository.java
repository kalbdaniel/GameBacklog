package nl.hva.dka.gamebacklog.Database;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import nl.hva.dka.gamebacklog.Game;

public class GameRepository implements IGameDao{

    private GameDatabase gameDatabase;
    private IGameDao gameDao;
    private LiveData<List<Game>> gameList;
    private Executor executor = Executors.newSingleThreadExecutor();

    public GameRepository(Context context) {
        this.gameDatabase = GameDatabase.getDatabase(context);
        this.gameDao = gameDatabase.gameDao();
        this.gameList = gameDao.getAllGames();
    }

    @Override
    public LiveData<List<Game>> getAllGames() {
        return gameList;
    }

    @Override
    public void insertGame(final Game game) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                gameDao.insertGame(game);
            }
        });
    }

    @Override
    public void deleteGame(final Game game) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                gameDao.deleteGame(game);
            }
        });
    }

    @Override
    public void updateGame(final Game game) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                gameDao.updateGame(game);
            }
        });
    }
}
