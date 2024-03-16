package edu.jsu.mcis.cs408.memopad;

import androidx.annotation.NonNull;

public class Memo {

    private int id;
    private final String memo;

    public Memo(int id, String memo) {

        this.id = id;
        this.memo = memo;

    }

    public Memo(String memo) {

        this.memo = memo;

    }

    public String getMemo() {
        return memo;
    }

    public int getId() { return id; }

    @NonNull
    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();
        s.append("#").append(id).append(": ").append(memo);
        return s.toString();

    }

}