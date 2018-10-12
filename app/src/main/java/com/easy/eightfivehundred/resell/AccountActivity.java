package com.easy.eightfivehundred.resell;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Set;

public class AccountActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "AccountActivity";
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private TextView nameTextView, emailTextView, locationTextView, joinedtextView;
    private String mPhotoUrl;
    private ImageView mUserImageView;
    private FirebaseStorage storage;
    private FirebaseFirestore db;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        mUserImageView = findViewById(R.id.user_image);
        db = FirebaseFirestore.getInstance();
        nameTextView = findViewById(R.id.user_name);
        emailTextView = findViewById(R.id.user_email);
        locationTextView = findViewById(R.id.user_location);
        joinedtextView = findViewById(R.id.user_joined);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);

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
        //nameTextView.setText(mFirebaseUser.getDisplayName());
//        if (mFirebaseUser.getPhotoUrl() != null) {
//            mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
//            mUserImageView = findViewById(R.id.user_image);
//            Glide.with(this).load(mPhotoUrl).into(mUserImageView);
//        }
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("User/" + mFirebaseUser.getUid() + "/Profile");
        storageReference.child(mFirebaseUser.getUid() + ".jpg")
                .getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(AccountActivity.this).load(uri).into(mUserImageView);
                    }
                });
        db.collection("Users").document(mFirebaseUser.getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                //User user = documentSnapshot.toObject(User.class);
                nameTextView.setText(documentSnapshot.getString("userName"));
                emailTextView.setText(documentSnapshot.getString("userEmail"));
                //locationTextView.setText(documentSnapshot.getGeoPoint("userLocation").toString());
                joinedtextView.setText(documentSnapshot.getString("userJoined"));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AccountActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_home:
                        Intent homeIntent = new Intent(AccountActivity.this, MainActivity.class);
                        startActivity(homeIntent);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        finish();
                        break;
                    case R.id.ic_messages:
                        Intent messagesIntent = new Intent(AccountActivity.this, MessagesActivity.class);
                        startActivity(messagesIntent);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        finish();
                        break;
                    case R.id.ic_post:
                        Intent postIntent = new Intent(AccountActivity.this, PostActivity.class);
                        startActivity(postIntent);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        finish();
                        break;
                    case R.id.ic_offers:
                        Intent offersIntent = new Intent(AccountActivity.this, OffersActivity.class);
                        startActivity(offersIntent);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        finish();
                        break;
                    case R.id.ic_profile:
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                intent.putExtra("Name", nameTextView.getText().toString());
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
