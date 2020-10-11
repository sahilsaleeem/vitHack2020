package com.example.myapplication25;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

   ArrayList<String> listforFirstItem = new ArrayList<String>();
    ArrayList<String> listforSecondItem = new ArrayList<String>();
    Boolean importdone = false ;


    public void import2(View view)
    {
        EditText fileNameEdit= (EditText) findViewById(R.id.fileName);
        String fileName = fileNameEdit.getText().toString();
        BufferedReader br = null;
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(fileName+".csv"));
            while ((sCurrentLine = br.readLine()) != null) {
                String string = sCurrentLine;
                String[] parts = string.split(",");
//                System.out.println(sCurrentLine);
                String part1 = parts[0];
                listforFirstItem.add(part1);
                String part2 = parts[1];
                listforSecondItem.add(part2);
//                System.out.println(part1);
//                System.out.println(part2);

            }
            System.out.println(listforFirstItem);
            System.out.println(listforSecondItem);


            Toast.makeText(this,"Imported ! ",Toast.LENGTH_SHORT).show();
            importdone = true ;
            if(fileName.length() == 0)
            {
                Toast.makeText(this,"Please Enter the Destination ! ",Toast.LENGTH_SHORT).show();
            }
            if(importdone == true)
            {
                TinyDB tinyDB = new TinyDB(this);
                tinyDB.putListString("firstList",listforFirstItem);
                tinyDB.putListString("secondList",listforSecondItem);
                Intent myIntent = new Intent(this, SecondActivity.class);
                startActivity(myIntent);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




    }
}