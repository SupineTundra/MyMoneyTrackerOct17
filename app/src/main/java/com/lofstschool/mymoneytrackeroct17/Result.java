package com.lofstschool.mymoneytrackeroct17;

import android.text.TextUtils;


public class Result {
    String status;

    public boolean isSuccess() {
        return TextUtils.equals(status, "success");
    }

    public class AddResult extends Result{  public int id; }

}
