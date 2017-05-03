package com.studentassistance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RequestStatus extends AppCompatActivity {

    ArrayList<StatusModel> statusModels;
    ListView listView;
    StatusListAdapter statusListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_status);
        statusModels = new ArrayList<>();
        getStatus();
    }

    void getStatus(){
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid1", new UserLocalStore(this).geteno());
        System.out.println("PARAMS" + params.toString());
        new Util().getstatus(params,this, new GetJsonArrayResult() {
            @Override
            public void done(JSONArray jsonArray) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject bucket_subject = jsonArray.getJSONObject(i);
                        String id = bucket_subject.getString("id");
                        String subjectcode1 = bucket_subject.getString("subjectCode1");
                        String subjectcode2 = bucket_subject.getString("subjectCode2");
                        String status = bucket_subject.getString("status");
                        String matchid = bucket_subject.getString("matchId");
                        String number = bucket_subject.getString("matchPhoneNumber");
                        statusModels.add(new StatusModel(id,subjectcode1,subjectcode2,status,matchid,number));
                        update(statusModels);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    public void update(ArrayList<StatusModel> statusModels){
        listView=(ListView)findViewById(R.id.statustlist);
        statusListAdapter =new StatusListAdapter(statusModels,this);
        listView.setAdapter(statusListAdapter);
    }
}
