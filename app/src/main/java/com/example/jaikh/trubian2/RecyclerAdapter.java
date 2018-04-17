package com.example.jaikh.trubian2;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jai on 01-10-2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<String> itemNames;
    private ArrayList<Integer> imageList;
    private final int[] backgroundColors = {R.color.rvc1, R.color.rvc2, R.color.rvc3, R.color.rvc4, R.color.rvc5, R.color.rvc6, R.color.rvc7, R.color.rvc8, R.color.rvc9, R.color.rvc10};

    public RecyclerAdapter(ArrayList<String> itemNames, ArrayList<Integer> imageList) {
        this.itemNames = itemNames;
        this.imageList = imageList;
        for (String name : this.itemNames) {
            System.out.println("Construtor : " + name);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view;
        if (viewType == 1) {
            // inflate your first item layout & return that viewHolder
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.first_item, parent, false);
            view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    final int type = viewType;
                    final ViewGroup.LayoutParams lp = view.getLayoutParams();
                    if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                        StaggeredGridLayoutManager.LayoutParams sglp =
                                (StaggeredGridLayoutManager.LayoutParams) lp;
                        sglp.setFullSpan(true);
                        view.setLayoutParams(sglp);
                        final StaggeredGridLayoutManager lm =
                                (StaggeredGridLayoutManager) ((RecyclerView) parent).getLayoutManager();
                        lm.invalidateSpanAssignments();
                    }
                    view.getViewTreeObserver().removeOnPreDrawListener(this);
                    return true;
                }
            });
            return new RecyclerHolder1(view);
        } else {
            // inflate your second item layout & return that viewHolder
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
            return new RecyclerHolder0(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (position == 0) {
            RecyclerHolder1 holder1 = (RecyclerHolder1) holder;
            trubian2 t2 = (trubian2) holder1.email.getContext().getApplicationContext();
            HashMap<String, String> userValues = t2.getData();
            holder1.email.setText(userValues.get("email"));
            holder1.enrollment_number.setText(userValues.get("enrollment_number"));
            holder1.user_name.setText(userValues.get("name"));
        } else {
            RecyclerHolder0 holder0 = (RecyclerHolder0) holder;
            holder0.name.setText(itemNames.get(position - 1));
            holder0.image.setImageResource(imageList.get(position - 1));
            int bgColor = ContextCompat.getColor(holder0.cv.getContext(), backgroundColors[(position - 1) % 10]);
            System.out.println("bg color - " + bgColor);
            holder0.cv.setCardBackgroundColor(bgColor);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return 1;
        else return 2;
    }

    @Override
    public int getItemCount() {
        //System.out.println("size is - " + itemNames.size());
        return itemNames.size() + 1;
    }

    public class RecyclerHolder0 extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        SimpleDraweeView image;
        CardView cv;

        public RecyclerHolder0(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            image = itemView.findViewById(R.id.options_picture);
            cv = itemView.findViewById(R.id.cardview);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), itemNames.get(getAdapterPosition() - 1) + " Clicked", Toast.LENGTH_SHORT).show();
            switch (getAdapterPosition()) {
                case 1:
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
                    view.getContext().startActivity(new Intent(view.getContext(), AboutTrubaActivity.class));
                    break;
                case 7:
                    view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.co.in/maps/place/Truba+Bhopal/@23.3084532,77.3874753,20.69z/data=!4m5!3m4!1s0x397c6884e0b907f7:0x396d52f7f5065b5b!8m2!3d23.3085227!4d77.3876095")).setPackage("com.google.android.apps.maps"));
                    break;
                case 9:
                    view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://210.212.148.195/mis/")));
                    break;
                case 10:
                    view.getContext().startActivity(new Intent(view.getContext(), AboutUSActivity.class));
                    break;
            }
        }
    }

    public class RecyclerHolder1 extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView user_name, enrollment_number, email;
        SimpleDraweeView user_picture;
        CardView cv;

        public RecyclerHolder1(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cardview);
            email = itemView.findViewById(R.id.email);
            user_name = itemView.findViewById(R.id.user_name);
            enrollment_number = itemView.findViewById(R.id.enrollment_number);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), "Profile Clicked", Toast.LENGTH_SHORT).show();
            view.getContext().startActivity(new Intent(view.getContext(), AccountActivity.class));
        }
    }
}
