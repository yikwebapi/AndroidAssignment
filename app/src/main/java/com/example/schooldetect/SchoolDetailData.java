package com.example.schooldetect;

public class SchoolDetailData {

    private String ename;
    private String cname;
    private String eaddress;
    private String caddress;
    private double mark;

    public SchoolDetailData(String e,String c, String ea,String ca, double m) {
        ename = e;
        cname = c;
        eaddress = ea;
        caddress = ca;
        mark =m;
    }

    public String getename() {
        return ename;
    }

    public String getcname() {
        return cname;
    }
    public String geteaddress() {
        return eaddress;
    }
    public String getcaddress() {
        return caddress;
    }
    public Double getmark() {
        return mark;
    }


}
