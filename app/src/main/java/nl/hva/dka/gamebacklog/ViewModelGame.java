package nl.hva.dka.gamebacklog;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import nl.hva.dka.gamebacklog.Database.GameRepository;
import nl.hva.dka.gamebacklog.Database.IGameDao;

public class ViewModelGame extends AndroidViewModel implements IGameDao {
    private GameRepository gameRepo;
    private LiveData<List<Game>> gameList;

    public ViewModelGame(@NonNull Application application) {
        super(application);
        gameRepo = new GameRepository(application.getApplicationContext());
        gameList = gameRepo.getAllGames();
    }

    @Override
    public LiveData<List<Game>> getAllGames() {
        return gameList;
    }

    @Override
    public void insertGame(Game game) {
        gameRepo.insertGame(game);
    }

    @Override
    public void deleteGame(Game game) {
        gameRepo.deleteGame(game);
    }

    @Override
    public void updateGame(Game game) {
        gameRepo.updateGame(game);
    }
}
