package com.lofstschool.mymoneytrackeroct17.api;

import com.lofstschool.mymoneytrackeroct17.Result;

public class BalanceResult extends Result {
    public long totalExpenses;
    public long totalIncome;

    public BalanceResult(long totalExpenses, long totalIncome) {
        this.totalExpenses = totalExpenses;
        this.totalIncome = totalIncome;
    }
}