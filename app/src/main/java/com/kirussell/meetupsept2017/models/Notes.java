package com.kirussell.meetupsept2017.models;


public class Notes {
    public enum BedType { QUIN, KING, TWIN }
    BedType bedType;
    String comments;

    public Notes(BedType bedType, String comments) {
        this.bedType = bedType;
        this.comments = comments;
    }

    public BedType getBedType() {
        return bedType;
    }

    public String getComments() {
        return comments;
    }
}
