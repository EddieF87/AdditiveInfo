package com.example.android.additiveinfo;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.additiveinfo.data.DataContract;

import java.util.ArrayList;
import java.util.List;

import model.Additive;

/**
 * Created by Eddie on 3/25/2018.
 */

public class AdditiveRecyclerViewAdapter
        extends RecyclerView.Adapter<AdditiveRecyclerViewAdapter.AdditiveViewHolder>
        implements Filterable {

    private List<Additive> mAdditivesOriginal;
    private List<Additive> mAdditivesFiltered;
    private int code;
    private OnViewCLickListener mOnViewClickListener;
    private int color0;
    private int color1;
    private int color2;
    private boolean mFavoritesOn;

    public AdditiveRecyclerViewAdapter(List<Additive> mAdditivesOriginal, int code, OnViewCLickListener listener, Context context) {
        this.mAdditivesOriginal = mAdditivesOriginal;
        this.mAdditivesFiltered = mAdditivesOriginal;
        this.code = code;
        mOnViewClickListener = listener;
        color0 = context.getResources().getColor(R.color.colorLevel0);
        color1 = context.getResources().getColor(R.color.colorLevel1);
        color2 = context.getResources().getColor(R.color.colorLevel2);
    }

    @NonNull
    @Override
    public AdditiveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_additive, parent, false);
        return new AdditiveViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdditiveViewHolder holder, int position) {
        Additive additive = mAdditivesFiltered.get(position);
        holder.bindAdditive(additive);
    }

    @Override
    public int getItemCount() {
        return mAdditivesFiltered.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void onViewClick(Additive additive) {
        if(mOnViewClickListener != null) {
            mOnViewClickListener.goToAdditive(additive);
        }
    }
    @Override

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                mAdditivesFiltered = filterSearch(charSequence);
                FilterResults filterResults = new FilterResults();
                filterResults.values = mAdditivesFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mAdditivesFiltered = (ArrayList<Additive>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }




    class AdditiveViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nameView;
        private TextView codeView;
        private ImageView favView;
//        private TextView descriptionView;
        private Additive mAdditive;


        AdditiveViewHolder(View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.additive_name);
            codeView = itemView.findViewById(R.id.additive_code);
            favView = itemView.findViewById(R.id.star_view);
//            descriptionView = itemView.findViewById(R.id.additive_desc);
            nameView.setOnClickListener(this);
            codeView.setOnClickListener(this);
            favView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int newFav = mOnViewClickListener.updateFavorite(mAdditive, getAdapterPosition());
                    if (newFav > -1) {
                        mAdditive.setFavorite(newFav == 1);

//                        notifyItemChanged(getAdapterPosition());
//                        if(newFav == 1) {
//                            favView.setColorFilter(Color.YELLOW);
//                            favView.setAlpha((float) 1.0);
//                        } else {
//                            favView.setColorFilter(null);
//                            favView.setAlpha((float) .2);
//                        }
                    }
                }
            });
        }

        public void bindAdditive(Additive additive) {
            mAdditive = additive;
            nameView.setText(additive.getName());
//            descriptionView.setText(additive.getDescription());
            String codeText;
            switch (code) {
                case R.id.radio_usa:
                    codeText = additive.getCodeUSA();
                    break;
                case R.id.radio_eu:
                    codeText = additive.getCodeEU();
                    break;
                case R.id.radio_china:
                    codeText = additive.getCodeChina();
                    break;
                default:
                    codeText = additive.getCodeUSA();
                    break;
            }
            codeView.setText(codeText);
            switch (additive.getLevel()) {
                case 0:
                    codeView.setTextColor(color0);
                    nameView.setTextColor(color0);
                    break;
                case 1:
                    codeView.setTextColor(color1);
                    nameView.setTextColor(color1);
                    break;
                case 2:
                    codeView.setTextColor(color2);
                    nameView.setTextColor(color2);
                    break;
            }
            if(mAdditive.getFavorite()) {
                favView.setColorFilter(color1);
                favView.setAlpha(1f);
            } else {
                favView.setColorFilter(null);
                favView.setAlpha(.2f);
            }
        }

        @Override
        public void onClick(View view) {
            onViewClick(mAdditive);
        }
    }

    public List<Additive> filterSearch(CharSequence charSequence) {
        String charString = charSequence.toString();
        if (charString.isEmpty()) {
            if(mFavoritesOn) {
                return mAdditivesFiltered;
            } else {
                return mAdditivesOriginal;
            }
        } else {
            List<Additive> filteredList = new ArrayList<>();
            if(mFavoritesOn) {
                for (Additive additive : mAdditivesOriginal) {
                    if (additive.getFavorite() && (additive.getName().toLowerCase().contains(charString.toLowerCase())
                            || additive.getCodeUSA().contains(charSequence))) {
                        filteredList.add(additive);
                    }
                }
            } else {
                for (Additive additive : mAdditivesOriginal) {
                    if (additive.getName().toLowerCase().contains(charString.toLowerCase())
                            || additive.getCodeUSA().contains(charSequence)) {
                        filteredList.add(additive);
                    }
                }
            }
            return filteredList;
        }
    }

    public void resetAdditives(boolean favoritesOn, CharSequence charSequence){
        mFavoritesOn = favoritesOn;
        mAdditivesFiltered = filterSearch(charSequence);
    }

    public void filterFavorites(boolean favoritesOn, CharSequence filterSequence) {

        mFavoritesOn = favoritesOn;
        if(favoritesOn) {
            List<Additive> newList = new ArrayList<>();
            for (Additive additive : mAdditivesFiltered) {
                if(additive.getFavorite()) {
                    newList.add(additive);
                }
            }
            mAdditivesFiltered = newList;
        } else {
            mAdditivesFiltered = mAdditivesOriginal;
            if(filterSequence != null) {
                mAdditivesFiltered = filterSearch(filterSequence);
            }
        }
        notifyDataSetChanged();
    }

    public interface OnViewCLickListener {
        void goToAdditive(Additive additive);
        int updateFavorite(Additive additive, int pos);
    }
}
