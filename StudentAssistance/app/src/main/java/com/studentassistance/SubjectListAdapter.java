package com.studentassistance;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static com.studentassistance.R.id.subjectname;

/**
 * Created by yash on 25/3/17.
 */
public class SubjectListAdapter extends ArrayAdapter<SubjectListModel> {
    private ArrayList<SubjectListModel> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView subjectname;
    }

    public SubjectListAdapter(ArrayList<SubjectListModel> data, Context context) {
        super(context, R.layout.subjectlist, data);
        this.dataSet = data;
        this.mContext = context;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final SubjectListModel subjectListModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.subjectlist, parent, false);
            viewHolder.subjectname = (TextView) convertView.findViewById(subjectname);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }
        lastPosition = position;
        viewHolder.subjectname.setText(subjectListModel.getSubjectname());

        viewHolder.subjectname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,Request.class);
                intent.putExtra("subjectname", subjectListModel.getSubjectname());
                intent.putExtra("subjectcode", subjectListModel.getSubjectcode());
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }
}
