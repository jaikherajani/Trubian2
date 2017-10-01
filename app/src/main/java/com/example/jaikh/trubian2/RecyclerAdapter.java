package com.example.jaikh.trubian2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jai on 01-10-2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder> {

    private ArrayList<String> itemNames;

    public RecyclerAdapter(ArrayList<String> itemNames) {
        this.itemNames = itemNames;
        for (String name : this.itemNames) {
            System.out.println("Construtor : " + name);
        }
    }


    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        holder.name.setText(itemNames.get(position));
        System.out.println(itemNames.get(position));
    }

    @Override
    public int getItemCount() {
        System.out.println("size is - " + itemNames.size());
        return itemNames.size();
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;

        public RecyclerHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            //itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //Toast.makeText(view.getContext(), itemNames.get(getAdapterPosition())+" Clicked", Toast.LENGTH_SHORT).show();
        }
    }
}
