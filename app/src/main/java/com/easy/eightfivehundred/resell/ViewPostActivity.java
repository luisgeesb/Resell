package com.easy.eightfivehundred.resell;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ViewPostActivity extends AppCompatActivity {
    ImageView imageView;
    TextView title, location, description, price, brand;
    Typeface font;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);
        font = Typeface.createFromAsset(this.getAssets(), "fonts/preme.ttf");
        db = FirebaseFirestore.getInstance();

        imageView = findViewById(R.id.imageView2);
        title = findViewById(R.id.title);
        location = findViewById(R.id.location);
        description = findViewById(R.id.description);
        price = findViewById(R.id.price);
        brand = findViewById(R.id.brand);
        brand.setTypeface(font);

        final String postName = getIntent().getStringExtra("post_name");

        DocumentReference documentReference = db.collection("Posts").document(postName);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Post post = documentSnapshot.toObject(Post.class);
                title.setText(post.getTitle());
                location.setText(post.getLocation());
                description.setText(post.getDescription());
                price.setText(String.valueOf(post.getPrice()));
                brand.setText(post.getBrand());
                Glide.with(ViewPostActivity.this).load(post.getImage()).into(imageView);
            }
        });
    }
}
