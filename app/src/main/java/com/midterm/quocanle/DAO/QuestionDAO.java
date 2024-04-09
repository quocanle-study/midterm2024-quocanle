package com.midterm.quocanle.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.midterm.quocanle.model.Question;

import java.util.List;

@Dao
public interface QuestionDAO {
    @Query("SELECT * FROM question")
    List<Question> getAll();

    @Query("SELECT * FROM question WHERE id IN (:questionIDs)")
    List<Question> loadAllByIds(int[] questionIDs);

    @Insert
    void insertAll(Question... questions);


    @Delete
    void delete(Question questions);

    @Delete
    void deleteAll(List<Question> questions);
}