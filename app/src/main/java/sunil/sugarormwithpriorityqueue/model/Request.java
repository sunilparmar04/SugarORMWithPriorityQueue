package sunil.sugarormwithpriorityqueue.model;

import android.support.annotation.NonNull;


import java.util.ArrayList;

import sunil.sugarormwithpriorityqueue.db.UserDetails;

public class Request implements Comparable<Request> {

    private ArrayList<UserDetails>userDetailsArrayList;
    private Integer mSequence;

    public void setSequence(Integer val) {
        this.mSequence = val;
    }

    @Override
    public int compareTo(@NonNull Request o) {
        return this.mSequence - o.mSequence;
    }

    public ArrayList<UserDetails> getUserDetailsArrayList() {
        return userDetailsArrayList;
    }

    public void setUserDetailsArrayList(ArrayList<UserDetails> userDetailsArrayList) {
        this.userDetailsArrayList = userDetailsArrayList;
    }
}
