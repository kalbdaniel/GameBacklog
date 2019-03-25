package nl.hva.dka.gamebacklog.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import nl.hva.dka.gamebacklog.Game;

@Dao
public interface IGameDao {
    @Query("SELECT * FROM game")
    LiveData<List<Game>> getAllGames();

    @Insert
    void insertGame(Game game);

    @Delete
    void deleteGame(Game game);

    @Update
    void updateGame(Game game);
}
