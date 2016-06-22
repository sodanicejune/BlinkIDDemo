package com.microblink.blinkid;

/**
 * Created by sodajune on 6/20/16.
 */
public class Value {

    public void setId(int id) {
        this.id = id;
    }

    public void setSa(String sa) {
        this.sa = sa;
    }

    public void setPr(String pr) {
        this.pr = pr;
    }

    public void setDr(String dr) {
        this.dr = dr;
    }

    public void setPmr(String pmr) {
        this.pmr = pmr;
    }

    public void setSur(String sur) {
        this.sur = sur;
    }

    public int getId() {

        return id;
    }

    public String getSa() {
        return sa;
    }

    public String getPr() {
        return pr;
    }

    public String getDr() {
        return dr;
    }

    public String getPmr() {
        return pmr;
    }

    public String getSur() {
        return sur;
    }

    public Value(String sa, String pr, String dr, String pmr, String sur) {

        this.sa = sa;
        this.pr = pr;
        this.dr = dr;
        this.pmr = pmr;
        this.sur = sur;
    }

    private int id;
    private String sa;
    private String pr;
    private String dr;
    private String pmr;
    private String sur;




}
