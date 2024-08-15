package com.example.wrmusic_project;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

public class ItemDetail extends AppCompatActivity {

    private ImageView detailImage;
    private TextView detailTitle;
    private TextView detailArtist;
    private TextView detailGenre;
    private TextView detailDescription;
    private TextView detailPrice;
    private TextView detailTrackCount;
    private ImageButton buttonIncrease;
    private ImageButton buttonDecrease;
    private Button buttonAddToCart;
    private TextView textQuantity;
    private ImageButton floatingButton;
    private ImageButton floatingCart;

    private int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        detailImage = findViewById(R.id.detail_image);
        detailTitle = findViewById(R.id.detail_title);
        detailArtist = findViewById(R.id.detail_artist);
        detailGenre = findViewById(R.id.detail_genre);
        detailDescription = findViewById(R.id.detail_description);
        detailPrice = findViewById(R.id.detail_price);
        detailTrackCount = findViewById(R.id.detail_track_count);
        buttonIncrease = findViewById(R.id.button_increase);
        buttonDecrease = findViewById(R.id.button_decrease);
        buttonAddToCart = findViewById(R.id.button_add_to_cart);
        textQuantity = findViewById(R.id.text_quantity);
        floatingButton = findViewById(R.id.floating_button);
        floatingCart = findViewById(R.id.floating_cart);

        textQuantity.setText("");

        Intent intent = getIntent();
        if (intent != null) {
            String title = intent.getStringExtra("title");
            String artist = intent.getStringExtra("artist");
            String genre = intent.getStringExtra("genre");
            String description = intent.getStringExtra("description");
            String price = intent.getStringExtra("price");
            int imageResId = intent.getIntExtra("imageResId", 0);
            int trackCount = intent.getIntExtra("trackCount", 0);

            Glide.with(this)
                    .load(imageResId)
                    .transform(new CenterCrop(), new RoundedCorners(16))
                    .into(detailImage);

            detailTitle.setText(title);
            detailArtist.setText(artist);
            detailGenre.setText(genre);
            detailDescription.setText(description);
            detailPrice.setText(price);
            detailTrackCount.setText(String.valueOf(trackCount));
        }

        updateQuantity();

        buttonIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity++;
                updateQuantity();
            }
        });

        buttonDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity > 0) {
                    quantity--;
                    updateQuantity();
                }
            }
        });

        buttonAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity > 0) {
                    buttonAddToCart.setText("Added Successfully");
                    Handler mHandler = new Handler();
                    mHandler.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            buttonAddToCart.setText("+ Add to Cart");
                        }
                    }, 3000L);
                } else {
                    showCustomDialog();
                }
            }
        });

        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        floatingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemDetail.this, Cart.class);
                startActivity(intent);
            }
        });
    }

    private void updateQuantity() {
        textQuantity.setText(String.valueOf(quantity));
    }

    private void showCustomDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_custom);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView dialogTitle = dialog.findViewById(R.id.dialogTitle);
        TextView dialogMessage = dialog.findViewById(R.id.dialogMessage);
        Button btnDialogOk = dialog.findViewById(R.id.btnDialogOk);

        dialogTitle.setText("Oops!");
        dialogMessage.setText("Your Quantity is still empty or less than one!");

        btnDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
