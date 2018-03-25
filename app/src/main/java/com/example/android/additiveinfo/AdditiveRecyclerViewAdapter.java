package com.example.android.additiveinfo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import model.Additive;

/**
 * Created by Eddie on 3/25/2018.
 */

public class AdditiveRecyclerViewAdapter extends RecyclerView.Adapter<AdditiveRecyclerViewAdapter.AdditiveViewHolder> {

    private List<Additive> mAdditives;
    private int code;
    private OnViewCLickListener mOnViewClickListener;

    public AdditiveRecyclerViewAdapter(List<Additive> mAdditives, int code, OnViewCLickListener listener) {
        this.mAdditives = mAdditives;
        this.code = code;
        mOnViewClickListener = listener;
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
        Additive additive = mAdditives.get(position);
        holder.bindAdditive(additive);
    }

    @Override
    public int getItemCount() {
        return mAdditives.size();
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


    class AdditiveViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nameView;
        private TextView codeView;
        private TextView descriptionView;
        private Additive mAdditive;


        AdditiveViewHolder(View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.additive_name);
            codeView = itemView.findViewById(R.id.additive_code);
            descriptionView = itemView.findViewById(R.id.additive_desc);
            itemView.setOnClickListener(this);
        }

        public void bindAdditive(Additive additive) {
            mAdditive = additive;
            nameView.setText(additive.getName());
            descriptionView.setText(additive.getDescription());
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
        }

        @Override
        public void onClick(View view) {
            onViewClick(mAdditive);
        }
    }

    public interface OnViewCLickListener {
        void goToAdditive(Additive additive);
    }
}
