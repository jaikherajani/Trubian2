package com.example.jaikh.trubian2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewsFeedActivity extends BaseActivity {

    private RecyclerView mFeedlist;
    private DatabaseReference mDatabase;
    private FloatingActionButton new_post;
    private FirebaseRecyclerAdapter<Feed,FeedViewHolder> mFirebaseAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        setTitle(getResources().getString(R.string.app_name) + " : News Feed");

        System.out.println("NewsFeedActivity");

        mFeedlist = findViewById(R.id.feed_list);
        //mFeedlist.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(true);

        new_post = findViewById(R.id.new_post);
        new_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewsFeedActivity.this, PostActivity.class));
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference();

        SnapshotParser<Feed> parser = new SnapshotParser<Feed>() {
            @Override
            public Feed parseSnapshot(DataSnapshot dataSnapshot) {
                Feed feed = dataSnapshot.getValue(Feed.class);
                if (feed != null) {
                    feed.setId(dataSnapshot.getKey());
                }
                return feed;
            }
        };

        DatabaseReference feedRef = mDatabase.child("Feed");

        FirebaseRecyclerOptions<Feed> options =
                new FirebaseRecyclerOptions.Builder<Feed>()
                        .setQuery(feedRef.orderByChild("time_stamp"), parser)
                        .build();

        mFirebaseAdapter = new FirebaseRecyclerAdapter<Feed, FeedViewHolder>(options)
        {
            @Override
            public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                return new FeedViewHolder(inflater.inflate(R.layout.feedrow, parent, false));
            }

            @Override
            protected void onBindViewHolder(@NonNull FeedViewHolder viewHolder, int position, @NonNull Feed model) {
                viewHolder.setTitle(model.getTitle());
                viewHolder.setBranch(model.getBranch());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setImage(getApplicationContext(),model.getImage());
                viewHolder.setBy(model.getBy());
                viewHolder.setOn(model.getOn());
            }

        };

        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
               /* int friendlyMessageCount = mFirebaseAdapter.getItemCount();
                int lastVisiblePosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();*/
                // If the recycler view is initially being loaded or the
                // user is at the bottom of the list, scroll to the bottom
                // of the list to show the newly added message.
                /*if (lastVisiblePosition == -1 ||
                        (positionStart >= (friendlyMessageCount - 1) &&
                                lastVisiblePosition == (positionStart - 1))) {
                    mFeedlist.scrollToPosition(positionStart);
                }*/
                mFeedlist.scrollToPosition(0);
            }
        });

        mFeedlist.setLayoutManager(mLinearLayoutManager);
        mFeedlist.setAdapter(mFirebaseAdapter);
    }

    @Override
    public void onPause() {
        mFirebaseAdapter.stopListening();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mFirebaseAdapter.startListening();
    }

    public static class FeedViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public FeedViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setTitle(String title){
            TextView post_title = mView.findViewById(R.id.post_title);
            post_title.setText(title);

        }
        public void setBranch(String branch){
            TextView post_title = mView.findViewById(R.id.post_branch);
            post_title.setText(branch);

        }
        public void setDesc(String desc){
            TextView post_title = mView.findViewById(R.id.post_desc);
            post_title.setText(desc);
        }

        public void setImage(Context ctx, String image){
            ImageView post_image = mView.findViewById(R.id.post_image);
            Glide.with(ctx).load(image).into(post_image);

        }
        public void setBy(String by){
            TextView post_title = mView.findViewById(R.id.post_by);
            post_title.setText(by);

        }
        public void setOn(long on){
            TextView post_title = mView.findViewById(R.id.post_on);
            post_title.setText(DateFormat.format("dd/MM/yyyy hh:mm:ss", on).toString());
        }
    }
}
