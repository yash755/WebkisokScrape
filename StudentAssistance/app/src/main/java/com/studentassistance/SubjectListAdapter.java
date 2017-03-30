package com.studentassistance;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
/**
 * Created by yash on 25/3/17.
 */
public class SubjectListAdapter extends ArrayAdapter<SubjectList> {
    private ArrayList<SubjectList> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView subjectname;
    }

    public SubjectListAdapter(ArrayList<SubjectList> data, Context context) {
        super(context, R.layout.subjectlist, data);
        this.dataSet = data;
        this.mContext = context;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        SubjectList subjectList = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.subjectlist, parent, false);
            viewHolder.subjectname = (TextView) convertView.findViewById(R.id.subjectname);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }
        lastPosition = position;
        viewHolder.subjectname.setText(subjectList.getSubject());

        return convertView;
    }
}
