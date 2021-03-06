package com.thepost.app.activities;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.thepost.app.R;

import java.util.Date;

@Keep
public class FullImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);

        //Initialize the Action Bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle("");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Drawable drawable = toolbar.getNavigationIcon();
        assert drawable != null;

        drawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

        Bundle bundle = getIntent().getExtras();

        assert bundle != null;
        String link = bundle.getString("imageLink");

        ImageView imageView = findViewById(R.id.viewImage);

        //Handle the loading (or error) of the image using Picasso
        Picasso.with(getApplicationContext()).load(link).into(imageView, new Callback() {
            @Override
            public void onSuccess() {

                findViewById(R.id.error).setVisibility(View.GONE);
                findViewById(R.id.progress).setVisibility(View.GONE);
            }

            @Override
            public void onError() {

                findViewById(R.id.error).setVisibility(View.VISIBLE);
                findViewById(R.id.progress).setVisibility(View.GONE);
            }
        });

        imageView.setOnTouchListener(new ImageMatrixTouchHandler(getApplicationContext()));
    }

    //Handle the back button in the action bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.stay, R.anim.push_out_left);
            return true;
        }
        else{
            return super.onOptionsItemSelected(item);
        }
    }

    //Necessary to show animation even when user presses system back button to quit activity
    @Override
    public void onBackPressed(){
        finish();
        overridePendingTransition(R.anim.stay, R.anim.push_out_left);
    }
}
