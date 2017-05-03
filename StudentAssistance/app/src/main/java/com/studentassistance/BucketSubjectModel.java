package com.studentassistance;

/**
 * Created by yash on 3/5/17.
 */

public class BucketSubjectModel {

    String subjectname;
    String subjectcode;
    String year;
    String bucketname;

    public BucketSubjectModel(String subjectname, String subjectcode,String year,String bucketname) {
        this.subjectname=subjectname;
        this.subjectcode=subjectcode;
        this.year = year;
        this.bucketname = bucketname;
    }
    public String getSubjectname() {
        return subjectname;
    }

    public String getSubjectcode(){ return subjectcode;}

    public String getYear(){return  year;}

    public  String getBucketname(){return  bucketname;}
}

