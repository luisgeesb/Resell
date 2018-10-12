package com.easy.eightfivehundred.resell;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MessagesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_home:
                        Intent homeIntent = new Intent(MessagesActivity.this, MainActivity.class);
                        startActivity(homeIntent);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        finish();
                        break;
                    case R.id.ic_messages:
                        break;
                    case R.id.ic_post:
                        Intent postIntent = new Intent(MessagesActivity.this, PostActivity.class);
                        startActivity(postIntent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        finish();
                        break;
                    case R.id.ic_offers:
                        Intent offersIntent = new Intent(MessagesActivity.this, OffersActivity.class);
                        startActivity(offersIntent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        finish();
                        break;
                    case R.id.ic_profile:
                        Intent accountIntent = new Intent(MessagesActivity.this, AccountActivity.class);
                        startActivity(accountIntent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        finish();
                        break;
                }
                return false;
            }
        });
    }
}
