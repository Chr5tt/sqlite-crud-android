package com.example.sqllitecrud;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MahasiswaDao {

    @Insert
    void insert(MahasiswaEntity mahasiswa);

    @Update
    void update(MahasiswaEntity mahasiswa);

    @Delete
    void delete(MahasiswaEntity mahasiswa);

    @Query("SELECT * FROM mahasiswa ORDER BY id DESC")
    List<MahasiswaEntity> getAll();
}
