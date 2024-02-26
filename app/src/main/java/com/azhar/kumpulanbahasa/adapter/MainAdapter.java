package com.azhar.kumpulanbahasa.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.azhar.kumpulanbahasa.R;
import com.azhar.kumpulanbahasa.activities.DetailActivity;
import com.azhar.kumpulanbahasa.model.ModelBahasa;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by Azhar Rivaldi on 09-01-2024
 * Youtube Channel : https://bit.ly/2PJMowZ
 * Github : https://github.com/AzharRivaldi
 * Twitter : https://twitter.com/azharrvldi_
 * Instagram : https://www.instagram.com/azhardvls_
 * LinkedIn : https://www.linkedin.com/in/azhar-rivaldi
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> implements Filterable {

    List<ModelBahasa> modelBahasaList;
    List<ModelBahasa> modelBahasaFilterList;
    Context mContext;

    @Override
    public Filter getFilter() {
        return modelFilter;
    }

    private final Filter modelFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ModelBahasa> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(modelBahasaFilterList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ModelBahasa modelMainFilter : modelBahasaFilterList) {
                    if (modelMainFilter.getStrBahasa().toLowerCase().contains(filterPattern)) {
                        filteredList.add(modelMainFilter);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            modelBahasaList.clear();
            modelBahasaList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public MainAdapter(Context context, List<ModelBahasa> items) {
        this.mContext = context;
        this.modelBahasaList = items;
        this.modelBahasaFilterList = new ArrayList<>(items);
    }

    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_bahasa, parent, false);
        return new MainAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainAdapter.ViewHolder holder, int position) {
        ModelBahasa data = modelBahasaList.get(position);

        holder.tvNomor.setText(data.getStrNomor());
        holder.tvBahasa.setText(data.getStrBahasa());
        holder.tvWilayah.setText(data.getStrWilayah());

        holder.cvListData.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, DetailActivity.class);
            intent.putExtra(DetailActivity.DETAIL_DATA, modelBahasaList.get(position));
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return modelBahasaList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNomor, tvBahasa, tvWilayah;
        public FrameLayout cvListData;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNomor = itemView.findViewById(R.id.tvNomor);
            tvBahasa = itemView.findViewById(R.id.tvBahasa);
            tvWilayah = itemView.findViewById(R.id.tvWilayah);
            cvListData = itemView.findViewById(R.id.cvListData);
        }
    }

}
