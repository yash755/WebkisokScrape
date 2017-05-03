package com.studentassistance;

/**
 * Created by yash on 30/3/17.
 */

public class SubjectListModel {
    String subjectname;
    String subjectcode;

    public SubjectListModel(String subjectname, String subjectcode) {
        this.subjectname=subjectname;
        this.subjectcode=subjectcode;
    }
    public String getSubjectname() {
        return subjectname;
    }

    public String getSubjectcode(){ return subjectcode;}
}
