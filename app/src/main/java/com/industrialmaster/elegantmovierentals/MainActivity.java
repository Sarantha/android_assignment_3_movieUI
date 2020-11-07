package com.industrialmaster.elegantmovierentals;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    DatePickerDialog.OnDateSetListener setListener;

    //arrays and arraylists for movie interests
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItem = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Underline the "ELEGANT" title
        TextView title = (TextView) findViewById(R.id.title_elegant);
        SpannableString content = new SpannableString("ELEGANT");
        content.setSpan(new UnderlineSpan(),0,content.length(),0);
        title.setText(content);

        //Select date of birth
        final TextView tv_dob = (TextView) findViewById(R.id.dob_tv);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        tv_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int month, int i2) {
                month = month + 1;
                String date = day+"/"+month+"/"+year;
                tv_dob.setText(date);
            }
        };

        //Select Age group of spinner
        Spinner spinner = (Spinner) findViewById(R.id.ageGroup);
        spinner.setOnItemSelectedListener(this);

        //Multi Select for movie interests
        Button selectIn = (Button) findViewById(R.id.selectInterests);
        final TextView selectedIn = (TextView) findViewById(R.id.your_interests);

        listItems = getResources().getStringArray(R.array.movie_interests);
        checkedItems = new boolean[listItems.length];

        selectIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Select Your Movie Interests");
                builder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if(isChecked){
                            if(!mUserItem.contains(position)){
                                mUserItem.add(position);
                            }else{
                                mUserItem.remove(position);
                            }
                        }
                    }
                });

                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String item = "";
                        for (int i=0;i<mUserItem.size();i++){
                            item = item + listItems[mUserItem.get(i)];
                            if(i != mUserItem.size() -1){
                                item = item + ", ";
                            }
                        }
                        selectedIn.setText(item);
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.setNeutralButton("Clear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for (i=0;i<checkedItems.length;i++){
                            checkedItems[i] = false;
                            mUserItem.clear();
                            selectedIn.setText("Your Interests");
                        }
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
