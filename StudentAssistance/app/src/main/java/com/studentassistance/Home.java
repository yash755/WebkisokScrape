package com.studentassistance;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Home extends AppCompatActivity implements View.OnClickListener {

    ArrayList<SubjectListModel> subjectListModels;
    ListView listView;
    SubjectListAdapter subjectListAdapter;
    DataBaseHelper dataBaseHelper;
    Button status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        status = (Button)findViewById(R.id.status);
        status.setOnClickListener(this);
        subjectListModels = new ArrayList<>();
        dataBaseHelper = new DataBaseHelper(this);

        if(getIntent().hasExtra("subject")) {
            try {
                JSONObject jsonObj = new JSONObject(getIntent().getStringExtra("subject"));

                JSONArray jsonArray = jsonObj.getJSONArray("subject");

                for (int i = 1; i < jsonArray.length() - 1; i++) {
                    JSONObject subject_list = jsonArray.getJSONObject(i);
                    String subject = subject_list.getString("subject");
                    String[] separated = subject.split("\\(");
                    String subjectname = separated[0];
                    String subjectcode = separated[1];
                    subjectcode = subjectcode.replaceAll("\\)", "");
                    dataBaseHelper.insertsubject(subjectname, subjectcode);
                    System.out.println("Subject" + subjectname + "Code" + subjectcode);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Cursor cr = dataBaseHelper.getsubjects();
        cr.moveToFirst();
        while (!cr.isAfterLast()) {
            subjectListModels.add(new SubjectListModel(cr.getString(cr.getColumnIndex("sub_name")),cr.getString(cr.getColumnIndex("sub_code"))));
            cr.moveToNext();
        }
        cr.close();

        listView=(ListView)findViewById(R.id.subjectlist);
        subjectListAdapter =new SubjectListAdapter(subjectListModels,this);
        listView.setAdapter(subjectListAdapter);
    }

    @Override
    public void onClick(View v) {
        if (v == status){
            Intent intent = new Intent(this, RequestStatus.class);
            startActivity(intent);
        }
    }
}
