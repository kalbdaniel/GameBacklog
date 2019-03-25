package nl.hva.dka.gamebacklog.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import nl.hva.dka.gamebacklog.Game;

@Database(entities = {Game.class}, version = 1, exportSchema = false)
public abstract class GameDatabase extends RoomDatabase {
    private final static String NAME_DATABASE = "db_game";

    public abstract IGameDao gameDao();

    private static volatile GameDatabase INSTANCE;

    public static GameDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (GameDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            GameDatabase.class, NAME_DATABASE)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
