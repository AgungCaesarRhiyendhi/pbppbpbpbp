package com.frumentiusdaneswara.tubes_uts;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TransaksiRecyclerAdapter extends RecyclerView.Adapter<TransaksiRecyclerAdapter.RoomViewHolder> implements Filterable {
    private List<TransaksiDAO> dataList;
    private List<TransaksiDAO> filteredDataList;
    private Context context;

    public TransaksiRecyclerAdapter(Context context, List<TransaksiDAO> dataList){
        this.context = context;
        this.dataList = dataList;
        this.filteredDataList = dataList;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycle_adapter_transaksi, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransaksiRecyclerAdapter.RoomViewHolder holder, int position) {
        final TransaksiDAO brg = filteredDataList.get(position);
        holder.twNama.setText(brg.getNamapemesan());
        holder.twNohp.setText(brg.getNohppemesan());
        holder.twmetode.setText(brg.getMetodepembayaran());
        holder.twharga.setText(brg.getHargakos());


        holder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                DetailTransaksiFragment dialog = new DetailTransaksiFragment();
                dialog.show(manager, "dialog");

                Bundle args = new Bundle();
                args.putString("id", brg.getId());
                dialog.setArguments(args);
            }
        });
    }


    @Override
    public int getItemCount() {
        return filteredDataList.size();
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder{
        private TextView twNama, twNohp, twmetode, twharga;
        private LinearLayout mParent;

        public RoomViewHolder(@NonNull View itemView){
            super(itemView);
            twNama = itemView.findViewById(R.id.twNama);
            twNohp = itemView.findViewById(R.id.twnohp);
            twmetode = itemView.findViewById(R.id.twmetode);
            twharga = itemView.findViewById(R.id.twharga);
            mParent = itemView.findViewById(R.id.linearLayout);
        }

    }

    @Override
    public Filter getFilter(){
        return new Filter(){
            @Override
            protected FilterResults performFiltering(CharSequence constraint){
                String charSequenceString = constraint.toString();
                if(charSequenceString.isEmpty()){
                    filteredDataList = dataList;
                }else{
                    List<TransaksiDAO> filteredList = new ArrayList<>();
                    for (TransaksiDAO TransaksiDAO : dataList) {
                        if (TransaksiDAO.getNamapemesan().toLowerCase().contains(charSequenceString.toLowerCase())){
                            filteredList.add(TransaksiDAO);
                        }
                        filteredDataList = filteredList;
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredDataList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredDataList = (List<TransaksiDAO>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
