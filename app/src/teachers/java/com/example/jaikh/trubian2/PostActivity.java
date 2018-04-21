package com.example.jaikh.trubian2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;

public class PostActivity extends BaseActivity {
    private ImageButton mSelectImage;
    private EditText mPostTitle;
    private EditText mPostBranch;
    private EditText mPostDesc;
    private FloatingActionButton mSubmitBtn;
    private Uri mImageUri=null;
    private StorageReference mStorage;
    private ProgressBar mProgress;
    private DatabaseReference mDatabase;
    private String username;
    private static final int GALLERY_REQUEST=1;
    LinearLayout container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        setTitle(getResources().getString(R.string.app_name) + " : New Post");

        mStorage= FirebaseStorage.getInstance().getReference();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Feed");

        mSelectImage = findViewById(R.id.imageButton3);
        mPostTitle= findViewById(R.id.titleField);
        mPostBranch= findViewById(R.id.branchField);
        mPostDesc= findViewById(R.id.DescField);
        mSubmitBtn= findViewById(R.id.SubmitBtn);
        mProgress = findViewById(R.id.posting_progress);
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

        container = findViewById(R.id.post_button_container);
    }

    private void startPosting() {
        mProgress.setTag("Posting...");

        final String title_val=mPostTitle.getText().toString().trim();
        final String desc_val=mPostDesc.getText().toString().trim();
        final String branch_val=mPostBranch.getText().toString().trim();
        if(!TextUtils.isEmpty(title_val) && !TextUtils.isEmpty(desc_val) && !TextUtils.isEmpty(branch_val) && mImageUri!=null)
        {
            mProgress.isIndeterminate();
            mProgress.setVisibility(View.VISIBLE);
            container.setVisibility(View.GONE);

            StorageReference filepath = mStorage.child("Feed_Images").child(mImageUri.getLastPathSegment());

            try {
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
                        newPost.child("time_stamp").setValue((1 - new Date().getTime()));
                        newPost.child("image").setValue(downloadUrl.toString());
                        newPost.setPriority((new Date().getTime()));
                        mProgress.setVisibility(View.GONE);
                        finish();
                    }
                });
            } catch (Exception e) {
                mProgress.setVisibility(View.GONE);
                container.setVisibility(View.VISIBLE);
                Toast.makeText(this, "Something happened! Please try again.", Toast.LENGTH_SHORT).show();
            }

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
