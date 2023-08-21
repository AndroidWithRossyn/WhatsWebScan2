package com.whatsweb.whatswebscanner.gbwhats.gb_in;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.anchorfree.partner.api.data.Country;
import com.whatsweb.whatswebscanner.gbwhats.R;

import java.util.ArrayList;
import java.util.List;

public class gb_RegionListAdapter extends RecyclerView.Adapter<gb_RegionListAdapter.ViewHolder> {

    private List<Country> regions;
    private RegionListAdapterInterface listAdapterInterface;

    public gb_RegionListAdapter(RegionListAdapterInterface listAdapterInterface) {
        this.listAdapterInterface = listAdapterInterface;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.gb_layout_region_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Country country = regions.get(position);
        if (country.getCountry() != null) {
            holder.regionTitle.setText(regions.get(position).getCountry());
        } else {
            holder.regionTitle.setText("unknown(null)");
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listAdapterInterface.onCountrySelected(regions.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return regions != null ? regions.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        TextView regionTitle;

        public ViewHolder(View v) {
            super(v);
            regionTitle=v.findViewById(R.id.region_title);

        }
    }

    public void setRegions(List<Country> list) {
        regions = new ArrayList<>();
        regions.add(new Country(""));
        regions.addAll(list);
        for(Country re:list){
            Log.e("country name",re.getCountry()+" "+re.getServers());
        }
        notifyDataSetChanged();
    }

    public interface RegionListAdapterInterface {
        void onCountrySelected(Country item);
    }
}