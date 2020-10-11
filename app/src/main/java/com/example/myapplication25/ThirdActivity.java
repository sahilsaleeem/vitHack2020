package com.example.myapplication25;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class ThirdActivity extends AppCompatActivity {
//    private static final int MAX_LENGTH = 100;
//    private static final int MIN_LENGTH = 1 ;
    ImageView mainImageView;

    int myImage;
    File mediaFile;
    Button button;
    TextView name;
    TextView grade;
    RelativeLayout layout;
    ArrayList<String> firstList = new ArrayList<String>();
    ArrayList<String> secondList = new ArrayList<String>();
    ArrayList<String> media = new ArrayList<String>();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        mainImageView = findViewById(R.id.mainImageView);
        button = findViewById(R.id.save_button);
        layout = findViewById(R.id.relativeLayout);
        name = findViewById(R.id.nameTextView);
        grade = findViewById(R.id.gradeTextView);
        TinyDB tinydb = new TinyDB(this);
        firstList = tinydb.getListString("firstList");
        secondList = tinydb.getListString("secondList");



        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                  for(int i =0;i<firstList.size();i++) {
                      name.setText(firstList.get(i));
                      grade.setText(secondList.get(i));
                      Bitmap bitmap = Bitmap.createBitmap(layout.getWidth(), layout.getHeight(), Bitmap.Config.ARGB_8888);
                      Canvas canvas = new Canvas(bitmap);
                      layout.draw(canvas);

                      storeImage(bitmap,i);

                  }

                  name.setText("");
                  grade.setText("");

                  Toast.makeText(ThirdActivity.this, "Saved Successfully !", Toast.LENGTH_SHORT).show();
            }
        });
        getData();
        setData();

}

    private void getData() {
        if (getIntent().hasExtra("myImage")) {
            myImage = getIntent().getIntExtra("myImage", 1);

        } else {
            Toast.makeText(this, "No Image Data !", Toast.LENGTH_SHORT).show();
        }

    }

    private void setData() {
        mainImageView.setImageResource(myImage);

    }

//    public String random() {
//        Random random = new Random();
//        int randomNumber = random.nextInt(MAX_LENGTH - MIN_LENGTH) +MIN_LENGTH;
//        return Integer.toString(randomNumber);
//
//    }

    private File getOutputMediaFile(int j) {


        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Files");
        Log.i("Directory would be :", Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after the app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        Integer Stamp = j;
        j++;
        String mImageName = "Certificate_" + Stamp + ".jpeg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }




    private void storeImage(Bitmap image,int r) {

        File pictureFile = getOutputMediaFile(r);
        if (pictureFile == null) {
            Log.i("Hey !", "Error creating media file, check storage permissions: ");// e.getMessage());
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            Toast.makeText(this, "Saving... ! ", Toast.LENGTH_SHORT).show();
//            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

