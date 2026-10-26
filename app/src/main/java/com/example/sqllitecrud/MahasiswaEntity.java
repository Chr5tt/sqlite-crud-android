package com.example.sqllitecrud;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "mahasiswa")
public class MahasiswaEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String nama;
    public String nim;
    public String jurusan;

    public MahasiswaEntity(String nama, String nim, String jurusan) {
        this.nama = nama;
        this.nim = nim;
        this.jurusan = jurusan;
    }
}
