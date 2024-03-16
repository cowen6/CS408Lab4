package edu.jsu.mcis.cs408.memopad;

import android.util.Log;

import java.util.ArrayList;

public class MemoPadModel extends AbstractModel {

    public static final String TAG = "MemoPadModel";

    private Memo newmemo;
    private DatabaseHandler db;
    private ArrayList<Memo> memos;
    private int deleteId;

    public void init(DatabaseHandler db) {

        this.db = db;
        deleteId = 0;

    }

    public void addNewMemo(Memo memo) {
        Log.i(TAG, "Adding new Memo");
        db.addMemo(memo);
        listMemos();
    }
    public void deleteMemo() {
        if(deleteId != 0) {
            Log.i(TAG, "Deleting Memo");
            db.deleteMemo(this.deleteId);
            listMemos();
        }
        else {
            Log.i(TAG, "Cannot Delete without specified Memo");
        }

    }
    public void listMemos() {
        Log.i(TAG, "Listing all Memos");
        ArrayList<Memo> oldList = this.memos;
        this.memos =  db.getAllMemosAsList();
        firePropertyChange(MemoPadController.ELEMENT_MEMO_LIST, oldList, this.memos);
    }

    public void setDeleteId(int id) {
        this.deleteId = id;
        Log.i(TAG, "Memo ID to Delete set to: " + id);
    }

}
