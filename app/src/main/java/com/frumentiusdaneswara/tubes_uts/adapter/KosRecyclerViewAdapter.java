package com.frumentiusdaneswara.tubes_uts.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.frumentiusdaneswara.tubes_uts.Kos;
import com.frumentiusdaneswara.tubes_uts.databinding.ItemKosBinding;
import com.frumentiusdaneswara.tubes_uts.showData;

import java.util.List;

public class KosRecyclerViewAdapter extends RecyclerView.Adapter<KosRecyclerViewAdapter.KosViewHolder> {

    private Context context;
    private List<Kos> kosList;

    public KosRecyclerViewAdapter(Context context, List<Kos> kosList){
        this.context = context;
        this.kosList = kosList;
    }


    @NonNull
    @Override
    public KosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemKosBinding itemBinding = ItemKosBinding.inflate(layoutInflater,parent, false);
        return new KosViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull KosViewHolder holder, int position) {
        Kos kos = kosList.get(position);
        holder.bind(kos);

//        holder.binding.kosNama.setText(kosList.get(position).getNamakos());
//        holder.binding.kosAlamat.setText(kosList.get(position).getAlamatkos());
//        holder.binding.imgKos.setImageDrawable(kosList.get(position).getImageID());

    }

    @Override
    public int getItemCount() {
        return kosList.size();
    }

    public void filterList(List<Kos> filteredList) {
        kosList = filteredList;
        notifyDataSetChanged();
    }



    class KosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final ItemKosBinding binding;

        public KosViewHolder(ItemKosBinding binding){
            super(binding.getRoot());
            this.binding = binding;
            itemView.setOnClickListener(this);
        }

        public void bind(Kos kos){
            binding.setKos(kos);
            binding.executePendingBindings();
        }

        public void onClick(View view) {
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            Kos kos = kosList.get(getAdapterPosition());
            Bundle data = new Bundle();
            data.putSerializable("kos", kos);
            Intent intent = new Intent(view.getContext(), showData.class);
            intent.putExtras(data);
            view.getContext().startActivity(intent);
        }


    }
}
