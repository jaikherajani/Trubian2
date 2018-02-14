package com.example.jaikh.trubian2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;

public class PostActivity extends AppCompatActivity {
    private ImageButton mSelectImage;
    private EditText mPostTitle;
    private EditText mPostBranch;
    private EditText mPostDesc;
    private Button mSubmitBtn;
    private Uri mImageUri=null;
    private StorageReference mStorage;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabase;
    private String username;
    private static final int GALLERY_REQUEST=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        mStorage= FirebaseStorage.getInstance().getReference();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Feed");

        mSelectImage = findViewById(R.id.imageButton3);
        mPostTitle= findViewById(R.id.titleField);
        mPostBranch= findViewById(R.id.branchField);
        mPostDesc= findViewById(R.id.DescField);
        mSubmitBtn= findViewById(R.id.SubmitBtn);
        mProgress= new ProgressDialog(this,R.style.Theme_AppCompat_DayNight_Dialog);
        trubian2 t2 = (trubian2) getApplicationContext();
        username = t2.getData().get("name");
        mSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
            }
        });

        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPosting();
            }
        });

    }

    private void startPosting() {
        mProgress.setMessage("Posting .....");

        final String title_val=mPostTitle.getText().toString().trim();
        final String desc_val=mPostDesc.getText().toString().trim();
        final String branch_val=mPostBranch.getText().toString().trim();
        if(!TextUtils.isEmpty(title_val) && !TextUtils.isEmpty(desc_val) && !TextUtils.isEmpty(branch_val) && mImageUri!=null)
        {
            mProgress.show();
            StorageReference filepath = mStorage.child("Feed_Images").child(mImageUri.getLastPathSegment());

            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    DatabaseReference newPost = mDatabase.push();
                    newPost.child("title").setValue(title_val);
                    newPost.child("branch").setValue(branch_val);
                    newPost.child("desc").setValue(desc_val);
                    newPost.child("by").setValue(username);
                    newPost.child("on").setValue(new Date().getTime());
                    newPost.child("time_stamp").setValue((1-new Date().getTime()));
                    newPost.child("image").setValue(downloadUrl.toString());
                    newPost.setPriority((new Date().getTime()));
                    mProgress.dismiss();
                    finish();
                }
            });

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GALLERY_REQUEST && resultCode==RESULT_OK) {
            mImageUri= data.getData();
            mSelectImage.setImageURI(mImageUri);
        }
    }
}
