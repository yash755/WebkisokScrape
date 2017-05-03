package com.studentassistance;

/**
 * Created by yash on 3/5/17.
 */

public class StatusModel {

    String id,subjectCode1,subjectCode2,status,matchId,matchPhoneNumber;

    StatusModel(String  id,String subjectCode1,String subjectCode2,String status,String matchId,String matchPhoneNumber){
        this.id = id;
        this.subjectCode1 =subjectCode1;
        this.subjectCode2= subjectCode2;
        this.status = status;
        this.matchId = matchId;
        this.matchPhoneNumber = matchPhoneNumber;
    }


    public String getId() {
        return id;
    }

    public String getMatchId() {
        return matchId;
    }

    public String getMatchPhoneNumber() {
        return matchPhoneNumber;
    }

    public String getStatus() {
        return status;
    }

    public String getSubjectCode1() {
        return subjectCode1;
    }

    public String getSubjectCode2() {
        return subjectCode2;
    }
}

