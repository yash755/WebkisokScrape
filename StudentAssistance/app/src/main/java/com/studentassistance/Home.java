package com.studentassistance;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_logout){
            UserLocalStore userLocalStore = new UserLocalStore(this);
            userLocalStore.clearUserdata();
            userLocalStore.setUserloggedIn(false);
            Intent myintent=new Intent(this, Login.class);
            startActivity(myintent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        super.onBackPressed();

    }

    @Override
    protected void onResume() {
        UserLocalStore userLocalStore;
        userLocalStore = new UserLocalStore(this);
        super.onResume();
        if(!userLocalStore.getuserloggedIn())
            startActivity(new Intent(this,Login.class));
    }
}
