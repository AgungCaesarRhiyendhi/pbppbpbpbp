package com.frumentiusdaneswara.tubes_uts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class KosRecyclerAdapter extends RecyclerView.Adapter<KosRecyclerAdapter.RoomViewHolder> implements Filterable {
    private List<Kos> dataList;
    private List<Kos> filteredDataList;
    private Context context;

    public KosRecyclerAdapter(Context context, List<Kos> dataList){
        this.context = context;
        this.dataList = dataList;
        this.filteredDataList = dataList;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycle_adapter_kos, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KosRecyclerAdapter.RoomViewHolder holder, int position) {
        final Kos brg = filteredDataList.get(position);
        holder.twNama.setText(brg.getNamakos());
        holder.twAlamat.setText(brg.getAlamatkos());


        holder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                DetailKosFragment dialog = new DetailKosFragment();
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
        private TextView twNama, twAlamat;
        private LinearLayout mParent;

        public RoomViewHolder(@NonNull View itemView){
            super(itemView);
            twNama = itemView.findViewById(R.id.twNama);
            twAlamat = itemView.findViewById(R.id.twalamat);
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
                    List<Kos> filteredList = new ArrayList<>();
                    for (Kos Kos : dataList) {
                        if (Kos.getNamakos().toLowerCase().contains(charSequenceString.toLowerCase())){
                            filteredList.add(Kos);
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
                filteredDataList = (List<Kos>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
