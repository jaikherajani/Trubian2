package com.example.jaikh.trubian2;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

/**
 * Created by Jai on 01-10-2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder> {

    private ArrayList<String> itemNames;
    private ArrayList<Integer> imageList;

    public RecyclerAdapter(ArrayList<String> itemNames, ArrayList<Integer> imageList) {
        this.itemNames = itemNames;
        this.imageList = imageList;
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
    public void onBindViewHolder(RecyclerHolder holder, final int position) {
        holder.name.setText(itemNames.get(position));
        holder.image.setImageResource(imageList.get(position));
    }

    @Override
    public int getItemCount() {
        //System.out.println("size is - " + itemNames.size());
        return itemNames.size();
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        SimpleDraweeView image;

        public RecyclerHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            image = itemView.findViewById(R.id.options_picture);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), itemNames.get(getAdapterPosition()) + " Clicked", Toast.LENGTH_SHORT).show();
            switch (getAdapterPosition()) {
                case 0:
                    view.getContext().startActivity(new Intent(view.getContext(), NewsFeedActivity.class));
                    break;
                case 2:
                    view.getContext().startActivity(new Intent(view.getContext(), TimeTableActivity.class));
                    break;
                case 3:
                    view.getContext().startActivity(new Intent(view.getContext(), CalendarActivity.class));
                    break;
                case 4:
                    view.getContext().startActivity(new Intent(view.getContext(), ResourcesActivity.class));
                    break;
                case 5:
                    view.getContext().startActivity(new Intent(view.getContext(), ContactsActivity.class));
                    break;
                case 6:
                    view.getContext().startActivity(new Intent(view.getContext(), AboutActivity.class));
                    break;
            }
        }
    }
}
