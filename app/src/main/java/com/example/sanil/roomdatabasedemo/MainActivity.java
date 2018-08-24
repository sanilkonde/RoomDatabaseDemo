package com.example.sanil.roomdatabasedemo;



import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    SampleDatabase sampleDatabase;
    public static String TAG = "Room";
    University university;
    List<University> universities = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        String str = "\"Dfgdfgdf<b>fdgdfgdf</b><br><img src=\\\"http://192.168.0.21/ICommunity/QuestionFiles/1150/answerpic20180627150301.jpg\\\" /  width=\\\"100%\\\"/ height=\\\"auto\\\" border-radius=\\\"10%\\\"/><br>\"/>" ;

        String[] s = getImgStr(str);

        Log.i(TAG,"img str "+s[0]);
        Log.i(TAG,"remain str "+s[1]);
        Log.i(TAG,"actual url "+s[0].substring(s[0].indexOf("htt"),s[0].indexOf(".jpg")+4));



        sampleDatabase = Room.databaseBuilder(getApplicationContext(),
                SampleDatabase.class, "sample-db").allowMainThreadQueries().build();


        final SpannableString text = new SpannableString("Hello stackOverflow");

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {

                Toast.makeText(MainActivity.this, "aya re", Toast.LENGTH_SHORT).show();
            }
        };
        text.setSpan(new RelativeSizeSpan(1.5f), text.length() - "stackOverflow".length(), text.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setSpan(clickableSpan, 6, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ((TextView)findViewById(R.id.spannTxtVw)).setText(text);
        ((TextView)findViewById(R.id.spannTxtVw)).setMovementMethod(LinkMovementMethod.getInstance());

        //Let's add some dummy data to the database.
        university = new University();
        university.setName("MyUniversity");

//        Log.i(TAG,"name is "+sampleDatabase.daoAccess().getSingleRecord(1).getCollege().getName());
//        Log.i(TAG,"name is "+sampleDatabase.daoAccess().getSingleRecord(2).getCollege().getName());


        universities = sampleDatabase.daoAccess().fetchAllData();

        Log.i(TAG,"all data "+universities.size());


        for (int i=0;i<universities.size();i++)
        {
            University uni = universities.get(i);
            Log.i(TAG,"name is "+uni.getCollege().getName());
        }

//        To update only name of university, change it and pass the object along with the primary key value.
//        university.setSlNo(1);
//        university.setName("ABCUniversity");
//        sampleDatabase.daoAccess().updateRecord(university);

        //To delete this record set the primary key and this will delete the record matching that primary key value.
//        University university1 = new University();
//        university1.setSlNo(1);
//        sampleDatabase.daoAccess().deleteRecord(university1);

        findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                College college = new College();
                college.setId(9);
                college.setName("MyCollege Record 9");
                university.setCollege(college);


                //Now access all the methods defined in DaoAccess with sampleDatabase object
                sampleDatabase.daoAccess().insertOnlySingleRecord(university);
            }
        });

        findViewById(R.id.removeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sampleDatabase.daoAccess().deleteParticularRecord(9);
            }
        });



        //For Live Data
        LiveData<List<University>> universityLiveData = sampleDatabase.daoAccess().fetchAllDataForLive();
        universityLiveData.observe(this, new Observer<List<University>>() {
            @Override
            public void onChanged(@Nullable List<University> universities) {
//                Update your UI here.

                Log.i(TAG,"universities size "+universities.size());

                for (int i=0;i<universities.size();i++)
                {
                    Log.i(TAG,"clg name "+universities.get(i).getCollege().getName());
                }
            }
        });
    }


    public String[] getImgStr(String str)
    {
        String[] strArray = new String[2];

        String newStr = str.substring(str.indexOf("<img"),str.length());

        strArray[0] = newStr.substring(0,newStr.indexOf("/>")+2);
        strArray[1] = str.replace(strArray[0],"");


        return strArray;
    }
}
