package com.studentassistance;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.studentassistance.R.id.cancel;
import static com.studentassistance.R.id.matchid;
import static com.studentassistance.R.id.matchnumber;
import static com.studentassistance.R.id.status;
import static com.studentassistance.R.id.subjectcode1;
import static com.studentassistance.R.id.subjectcode2;
import static com.studentassistance.R.id.subjectname;

/**
 * Created by yash on 3/5/17.
 */
public class StatusListAdapter extends ArrayAdapter<StatusModel> {
    private ArrayList<StatusModel> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView subject1,subject2,status,matchid,matchnumber;
        Button cancel;

    }

    public StatusListAdapter(ArrayList<StatusModel> data, Context context) {
        super(context, R.layout.statuslist, data);
        this.dataSet = data;
        this.mContext = context;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final StatusModel statusModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.statuslist, parent, false);
            viewHolder.subject1 = (TextView) convertView.findViewById(subjectcode1);
            viewHolder.subject2 = (TextView) convertView.findViewById(subjectcode2);
            viewHolder.status = (TextView) convertView.findViewById(status);
            viewHolder.matchid = (TextView) convertView.findViewById(matchid);
            viewHolder.matchnumber = (TextView) convertView.findViewById(matchnumber);
            viewHolder.cancel = (Button) convertView.findViewById(cancel);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }
        lastPosition = position;
        viewHolder.subject1.setText("Current Subject: " +statusModel.getSubjectCode1());
        viewHolder.subject2.setText("Changed Request: " + statusModel.getSubjectCode2());
        viewHolder.status.setText( statusModel.getStatus());

        if(statusModel.getMatchId().equals("0") && statusModel.getMatchPhoneNumber().equals("")){
            viewHolder.matchid.setText("No Id match");
            viewHolder.matchnumber.setVisibility(View.INVISIBLE);
        }else{
            viewHolder.matchid.setText( statusModel.getMatchId());
            viewHolder.matchnumber.setText( statusModel.getMatchPhoneNumber());
        }

        viewHolder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDeleted(statusModel.getId());
            }
        });

        return convertView;
    }

    public void getDeleted(String id){
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        new Util().deleterequest(params, mContext, new GetResult() {
            @Override
            public void done(JSONObject jsonObject) {
                Intent intent = new Intent(mContext,Home.class);
                mContext.startActivity(intent);
            }
        });
    }
}
