package com.example.jaikh.trubian2;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

/**
 * Created by Jai on 01-10-2017.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.RecyclerHolder2> {

    Context context;
    private ArrayList<String> namesList, designationList, phoneList, emailList;
    private ArrayList<Integer> imageList;

    public ContactsAdapter(Context context, ArrayList<String> namesList, ArrayList<Integer> imageList, ArrayList<String> designationList, ArrayList<String> phoneList, ArrayList<String> emailList) {
        this.namesList = namesList;
        this.imageList = imageList;
        this.designationList = designationList;
        this.phoneList = phoneList;
        this.emailList = emailList;
        this.context = context;
        for (String name : this.namesList) {
            System.out.println("Construtor : " + name);
        }
    }


    @Override
    public RecyclerHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact, parent, false);
        return new RecyclerHolder2(view);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder2 holder, final int position) {
        holder.name.setText(namesList.get(position));
        //holder.image.setImageResource(imageList.get(position));
        holder.designation.setText(designationList.get(position));
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneList.get(position))));
                    return;
                }
            }
        });
        holder.email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{emailList.get(position)});
                try {
                    v.getContext().startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(context, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
                //v.getContext().startActivity(new Intent(Intent.ACTION_SEND, Uri.parse(emailList.get(position))));
            }
        });
    }

    @Override
    public int getItemCount() {
        //System.out.println("size is - " + namesList.size());
        return namesList.size();
    }

    public class RecyclerHolder2 extends RecyclerView.ViewHolder {

        TextView name, designation;
        SimpleDraweeView image;
        ImageButton call, email;

        public RecyclerHolder2(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.contact_name);
            image = itemView.findViewById(R.id.contact_picture);
            designation = itemView.findViewById(R.id.contact_designation);
            call = itemView.findViewById(R.id.call);
            email = itemView.findViewById(R.id.email);
            //itemView.setOnClickListener(this);
        }

    }
}