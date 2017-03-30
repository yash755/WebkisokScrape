package com.studentassistance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    ArrayList<SubjectList> subjectLists;
    ListView listView;
    SubjectListAdapter subjectListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        subjectLists = new ArrayList<>();

        try {
            JSONObject jsonObj = new JSONObject(getIntent().getStringExtra("subject"));

            JSONArray jsonArray = jsonObj.getJSONArray("subject");

            for(int i=1;i<jsonArray.length()-1;i++){
                JSONObject subject_list = jsonArray.getJSONObject(i);
                String subject = subject_list.getString("subject");
                subjectLists.add(new SubjectList(subject));
                System.out.println("Subject" + subject);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        listView=(ListView)findViewById(R.id.subjectlist);
        subjectListAdapter =new SubjectListAdapter(subjectLists,this);
        listView.setAdapter(subjectListAdapter);
    }
}
