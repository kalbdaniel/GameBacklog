package nl.hva.dka.gamebacklog;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "game")
public class Game implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "status")
    private String status;

    @ColumnInfo(name = "platform")
    private String platform;

    @ColumnInfo(name = "date")
    private String date;


    public Game(String title, String status, String platform, String date)

    {
        this.title = title;
        this.status = status;
        this.platform = platform;
        this.date = date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }

    public String getPlatform() {
        return platform;
    }

    public String getDate() {
        return date;
    }
    public Long getId() {
        return id;
    }
}
