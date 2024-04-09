package com.midterm.quocanle.databaseUtil;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.midterm.quocanle.DAO.QuestionDAO;
import com.midterm.quocanle.model.Question;


@Database(entities = {Question.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase{
    public abstract QuestionDAO questionDAO();

    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context,
                    AppDatabase.class, "database-question").fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}