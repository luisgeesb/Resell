package com.easy.eightfivehundred.resell;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;

public class PostActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    private static final int PICK_IMAGE_REQUEST = 1;
    Spinner brandSpinner, conditionSpinner;
    Button postButton;
    EditText titleEditText, descriptionEditText, priceEditText, locationEditText;
    ImageView itemImage;
    Uri mImageUri;
    private FirebaseFirestore db;
    private GoogleApiClient mGoogleApiClient;
    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    StorageReference storageReference;
    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        db = FirebaseFirestore.getInstance();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
        titleEditText = findViewById(R.id.title_edit_text);
        descriptionEditText = findViewById(R.id.description_edit_text);
        priceEditText = findViewById(R.id.price_edit_text);
        locationEditText = findViewById(R.id.location_edit_text);
        brandSpinner = findViewById(R.id.spinner_brands);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_brands, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        brandSpinner.setAdapter(adapter);
        brandSpinner.setOnItemSelectedListener(this);
        conditionSpinner = findViewById(R.id.spinner_condition);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.spinner_condition, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        conditionSpinner.setAdapter(adapter2);
        conditionSpinner.setOnItemSelectedListener(this);
        itemImage = findViewById(R.id.item_image);
        itemImage.setOnClickListener(this);
        postButton = findViewById(R.id.post_button);
        postButton.setOnClickListener(this);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        }

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("User/" + mFirebaseUser.getUid() + "/Posts");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.post_button:
//                Intent intent = new Intent(this, PostActivity2.class);
//                intent.putExtra("title", titleEditText.getText().toString());
//                intent.putExtra("description", descriptionEditText.getText().toString());
//                intent.putExtra("brand", brandSpinner.getSelectedItem().toString());
//                intent.putExtra("condition", conditionSpinner.getSelectedItem().toString());
//                intent.putExtra("price", priceEditText.getText().toString());
//                intent.putExtra("location", locationEditText.getText().toString());
//                startActivity(intent);
                uploadPost();
                break;
            case R.id.item_image:
                openFileChooser();
                break;
        }

    }

    private void uploadPost() {
        String title = titleEditText.getText().toString();
        String desc = descriptionEditText.getText().toString();
        String brand = brandSpinner.getSelectedItem().toString();
        String cond = conditionSpinner.getSelectedItem().toString();
        String location = locationEditText.getText().toString();
        Integer price = Integer.parseInt(priceEditText.getText().toString());
        String postName = title + desc + brand + cond + location;
        final StorageReference fileRef = storageReference.child(postName);
        if (mImageUri != null) {
            fileRef.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String title = titleEditText.getText().toString();
                            String desc = descriptionEditText.getText().toString();
                            String brand = brandSpinner.getSelectedItem().toString();
                            String cond = conditionSpinner.getSelectedItem().toString();
                            String location = locationEditText.getText().toString();
                            Integer price = Integer.parseInt(priceEditText.getText().toString());
                            final String postName = title + desc + brand + cond + location;
                            final Post newPost = new Post(postName, brand, cond, desc, location, price, title, uri.toString());
                            db.collection("Posts").document(postName)
                                    .set(newPost)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(PostActivity.this, "Post Was Added Successfully", Toast.LENGTH_SHORT).show();
                                            db.collection("Users").document(mFirebaseUser.getUid()).collection("UserPosts").document(postName)
                                                    .set(newPost);
                                            startActivity(new Intent(PostActivity.this, MainActivity.class));
                                            finish();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(PostActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(PostActivity.this, "Boom", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Glide.with(this).load(mImageUri).into(itemImage);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
