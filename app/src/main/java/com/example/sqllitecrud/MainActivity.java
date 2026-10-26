package com.example.sqllitecrud;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    EditText etNama, etNim, etJurusan;
    Button btnSave, btnCancel;
    AppDatabase db;
    MahasiswaAdapter adapter;
    MahasiswaEntity selectedMahasiswa = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNama = findViewById(R.id.etNama);
        etNim = findViewById(R.id.etNim);
        etJurusan = findViewById(R.id.etJurusan);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        RecyclerView rv = findViewById(R.id.rvMahasiswa);
        rv.setLayoutManager(new LinearLayoutManager(this));

        db = AppDatabase.getInstance(this);

        adapter = new MahasiswaAdapter(
                db.mahasiswaDao().getAll(),
                new MahasiswaAdapter.OnItemListener() {
                    @Override
                    public void onItemClick(MahasiswaEntity mahasiswa) {
                        // UPDATE: isi form dengan data yang dipilih
                        selectedMahasiswa = mahasiswa;
                        etNama.setText(mahasiswa.nama);
                        etNim.setText(mahasiswa.nim);
                        etJurusan.setText(mahasiswa.jurusan);
                        btnSave.setText("Update");
                        btnCancel.setVisibility(android.view.View.VISIBLE);
                    }

                    @Override
                    public void onDeleteClick(MahasiswaEntity mahasiswa) {
                        confirmDelete(mahasiswa);
                    }
                }
        );
        rv.setAdapter(adapter);

        btnSave.setOnClickListener(v -> saveData());
        btnCancel.setOnClickListener(v -> resetForm());
    }

    private void saveData() {
        String nama = etNama.getText().toString().trim();
        String nim = etNim.getText().toString().trim();
        String jurusan = etJurusan.getText().toString().trim();

        if (TextUtils.isEmpty(nama) || TextUtils.isEmpty(nim) || TextUtils.isEmpty(jurusan)) {
            Toast.makeText(this, "Nama, NIM, dan Jurusan wajib diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedMahasiswa == null) {
            // CREATE
            db.mahasiswaDao().insert(new MahasiswaEntity(nama, nim, jurusan));
            Toast.makeText(this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
        } else {
            // UPDATE
            selectedMahasiswa.nama = nama;
            selectedMahasiswa.nim = nim;
            selectedMahasiswa.jurusan = jurusan;
            db.mahasiswaDao().update(selectedMahasiswa);
            Toast.makeText(this, "Data berhasil diperbarui", Toast.LENGTH_SHORT).show();
        }

        resetForm();
        refreshList();
    }

    private void confirmDelete(MahasiswaEntity mahasiswa) {
        new AlertDialog.Builder(this)
                .setTitle("Hapus Data")
                .setMessage("Hapus data " + mahasiswa.nama + "?")
                .setPositiveButton("Hapus", (dialog, which) -> {
                    // DELETE
                    db.mahasiswaDao().delete(mahasiswa);
                    if (selectedMahasiswa != null && selectedMahasiswa.id == mahasiswa.id) {
                        resetForm();
                    }
                    refreshList();
                    Toast.makeText(this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Batal", null)
                .show();
    }

    // READ
    private void refreshList() {
        adapter.setData(db.mahasiswaDao().getAll());
    }

    private void resetForm() {
        selectedMahasiswa = null;
        etNama.setText("");
        etNim.setText("");
        etJurusan.setText("");
        btnSave.setText("Simpan");
        btnCancel.setVisibility(android.view.View.GONE);
        etNama.requestFocus();
    }
}
