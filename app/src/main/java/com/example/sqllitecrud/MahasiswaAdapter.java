package com.example.sqllitecrud;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.ViewHolder> {

    List<MahasiswaEntity> list;
    OnItemListener listener;

    public interface OnItemListener {
        void onItemClick(MahasiswaEntity mahasiswa);   // pilih data untuk diedit
        void onDeleteClick(MahasiswaEntity mahasiswa); // hapus data
    }

    public MahasiswaAdapter(List<MahasiswaEntity> list, OnItemListener listener) {
        this.list = list;
        this.listener = listener;
    }

    public void setData(List<MahasiswaEntity> newList) {
        this.list = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mahasiswa, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MahasiswaEntity m = list.get(position);
        holder.tvNama.setText(m.nama);
        holder.tvDetail.setText("NIM: " + m.nim + " | Jurusan: " + m.jurusan);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(m);
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) listener.onDeleteClick(m);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvDetail;
        ImageButton btnDelete;

        ViewHolder(View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvDetail = itemView.findViewById(R.id.tvDetail);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
