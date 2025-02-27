package com.example.fragmentsbonus.search.view.countries;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fragmentsbonus.R;
import com.example.fragmentsbonus.models.countries.CountryItem;
import com.example.fragmentsbonus.search.click_listener.OnItemClicked;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.ViewHolder> {
    private List<CountryItem> countries;
    private OnItemClicked listener;

    public CountriesAdapter() {
        this.countries = new ArrayList<>();
    }

    public void clickItem(OnItemClicked listener) {
        this.listener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingrediant_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CountryItem country = countries.get(position);
        // Get first 3 letters of country name
        String shortName = country.getStrArea().toLowerCase().substring(0, 3);
        holder.countryName.setText(country.getStrArea());
        holder.countryImage.setImageResource(COUNTRY_IMAGES.get(shortName));
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClicked(country.getStrArea());
            }
        });
    }

    private final Map<String, Integer> COUNTRY_IMAGES = new HashMap<String, Integer>() {{
        put("ame", R.drawable.america);
        put("bri", R.drawable.britain);
        put("can", R.drawable.canada);
        put("chi", R.drawable.china);
        put("cro", R.drawable.coratia);
        put("dut", R.drawable.dutch);
        put("egy", R.drawable.egypt);
        put("fil", R.drawable.filipino);
        put("fre", R.drawable.france);
        put("gre", R.drawable.greece);
        put("ind", R.drawable.india);
        put("iri", R.drawable.ireland);
        put("ita", R.drawable.italy);
        put("jam", R.drawable.jamaica);
        put("jap", R.drawable.japan);
        put("ken", R.drawable.kenya);
        put("mal", R.drawable.malaysia);
        put("mex", R.drawable.mexico);
        put("mor", R.drawable.morocco);
        put("nor", R.drawable.nor);
        put("pol", R.drawable.poland);
        put("por", R.drawable.portugal);
        put("rus", R.drawable.russia);
        put("spa", R.drawable.spain);
        put("tha", R.drawable.thailand);
        put("tun", R.drawable.tunisia);
        put("tur", R.drawable.turkey);
        put("vie", R.drawable.turkey);
        put("ukr", R.drawable.ukraine);
        put("uru", R.drawable.uruguay);

    }};

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public void setCountries(List<CountryItem> countries) {
        this.countries = countries;
        notifyDataSetChanged();
        Log.d("CountriesAdapter", "Updated countries list. Size: " + countries.size());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView countryName;
        ImageView countryImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            countryName = itemView.findViewById(R.id.dishTitleIng);
            countryImage = itemView.findViewById(R.id.foodImageIng);
        }
    }
}
