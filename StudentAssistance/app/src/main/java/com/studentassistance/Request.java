package com.studentassistance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    String subjectname,subjectcode,subjectbucket,selectedsubject,selectedsubjectcode;
    ArrayList<BucketSubjectModel> bucketSubjectModels;
    TextView title;
    Spinner bucketlist;
    Button send;
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        title = (TextView)findViewById(R.id.subject_title);
        bucketlist = (Spinner)findViewById(R.id.bucket_list);
        send = (Button)findViewById(R.id.send);

        bucketlist.setOnItemSelectedListener(this);

        title.setVisibility(View.INVISIBLE);
        bucketlist.setVisibility(View.INVISIBLE);
        send.setVisibility(View.INVISIBLE);

        send.setOnClickListener(this);

        bucketSubjectModels = new ArrayList<>();
        if(getIntent().hasExtra("subjectname")) {
            subjectname = new String(getIntent().getStringExtra("subjectname"));
            subjectcode = new String(getIntent().getStringExtra("subjectcode"));
        }

        System.out.println("sub" + subjectname + subjectcode);
        fetchdata();
    }

    void fetchdata(){
        new Util().getbucketdata(this, new GetJsonArrayResult() {
            @Override
            public void done(JSONArray jsonArray) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject bucket_subject = jsonArray.getJSONObject(i);
                        String subjectname = bucket_subject.getString("subjectName");
                        String subjectcode = bucket_subject.getString("subjectCode");
                        String year = bucket_subject.getString("year");
                        String bucketname = bucket_subject.getString("bucketName");
                        bucketSubjectModels.add(new BucketSubjectModel(subjectname,subjectcode,year,bucketname));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                setrequest();
            }
        });
    }

    void setrequest(){

        for(int i=0;i<bucketSubjectModels.size();i++){
            BucketSubjectModel bucketSubjectModel = bucketSubjectModels.get(i);
            System.out.println("Subject" + bucketSubjectModel.getSubjectname());
            if(bucketSubjectModel.getSubjectcode().equalsIgnoreCase(subjectcode)){
                subjectbucket = bucketSubjectModel.getBucketname();
                flag = 1;
                break;
            }
        }

        if(flag == 0){
            title.setVisibility(View.VISIBLE);
            title.setText("Subject not allowed for change.");
        }else {
            List<String> categories = new ArrayList<String>();
            title.setVisibility(View.VISIBLE);
            title.setText("Choose from Dropdown:");
            bucketlist.setVisibility(View.VISIBLE);
            for(int i=0;i<bucketSubjectModels.size();i++){
                BucketSubjectModel bucketSubjectModel = bucketSubjectModels.get(i);
                if(bucketSubjectModel.getBucketname().equalsIgnoreCase(subjectbucket)){
                        if (!bucketSubjectModel.getSubjectcode().equalsIgnoreCase(subjectcode))
                            categories.add(bucketSubjectModel.getSubjectname());
                }
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            bucketlist.setAdapter(dataAdapter);
            send.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedsubject = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if (v == send){
            for(int i=0;i<bucketSubjectModels.size();i++){
                BucketSubjectModel bucketSubjectModel = bucketSubjectModels.get(i);
                if(bucketSubjectModel.getSubjectname().equalsIgnoreCase(selectedsubject)){
                        selectedsubjectcode = bucketSubjectModel.getSubjectcode();
                        break;
                }
            }

            Map<String, String> params = new HashMap<String, String>();
            params.put("uid1", new UserLocalStore(this).geteno());
            params.put("subjectCode1", subjectcode);
            params.put("subjectCode2", selectedsubjectcode);
            params.put("phoneNumber",new UserLocalStore(this).getpno());
            System.out.println("PARAMS" + params.toString());
            new Util().sendrequest(params,this, new GetResult() {
                @Override
                public void done(JSONObject jsonObject) {
                           // new Util().showsuccessmessage(getApplicationContext(), "Request sent Successfully...");
                            Intent intent = new Intent(getApplicationContext(),Home.class);
                            startActivity(intent);
                }
            });

        }
    }
}
