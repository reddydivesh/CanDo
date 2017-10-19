package com.android.jayanth.cando.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.jayanth.cando.model.CanDoDataModel;

import java.util.ArrayList;

import static com.android.jayanth.cando.Constants.Constants.CANDO_STATUS_TO_DO;

/**
 * Created by Jayanth Devulapally on 8/7/17.
 * database helper class to perform the operation on the database.
 */

public class CandoDbHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    public CandoDbHelper(Context context){
        super(context, CandoContract.DB_NAME, null, CandoContract.DB_VERSION);
        //getting the database
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //creating the database with columns and their data types
        String createTable = "CREATE TABLE "+ CandoContract.CandoEntry.TABLE + " ( " +
                CandoContract.CandoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CandoContract.CandoEntry.COL_CANDO_TITLE + " TEXT NOT NULL, " +
                CandoContract.CandoEntry.COL_CANDO_NOTES + " TEXT NOT NULL, " +
                CandoContract.CandoEntry.COL_CAND0_DUE_DATE + " TEXT NOT NULL, " +
                CandoContract.CandoEntry.COL_CANDO_PRIORITY + " INTEGER NOT NULL, " +
                CandoContract.CandoEntry.COL_DO_STATUS + " INTEGER NOT NULL)";

        //executing the create table query
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //deleting the existing table and creating it in the database while upgrading the database
        db.execSQL("DROP TABLE IF EXISTS "+ CandoContract.CandoEntry.TABLE);
        onCreate(db);
    }

    //helper method to insert the provided data in to the database
    public boolean insertIntoDatabase(String title, String notes, String dueDate, int priority){

        //constructing the content values to put into database table
        ContentValues values = new ContentValues();
        values.put(CandoContract.CandoEntry.COL_CANDO_TITLE, title);
        values.put(CandoContract.CandoEntry.COL_CANDO_NOTES, notes);
        values.put(CandoContract.CandoEntry.COL_CAND0_DUE_DATE, dueDate);
        values.put(CandoContract.CandoEntry.COL_CANDO_PRIORITY, priority);
        values.put(CandoContract.CandoEntry.COL_DO_STATUS, CANDO_STATUS_TO_DO);

        //inserting the values into the database table
        long result  = db.insertWithOnConflict(CandoContract.CandoEntry.TABLE,
                null, values,SQLiteDatabase.CONFLICT_REPLACE);

        //closing the database
        closeDatabase();

        //returing the result
        return result != -1;
    }

    //updating the 'status' in the row based on the ID
    public boolean updateCanDoStatus(int canDoId, int updateTo){

        //update the status values
        ContentValues values = new ContentValues();
        values.put(CandoContract.CandoEntry.COL_DO_STATUS,updateTo);

        //updating the status based on the ID
        long result = db.update(CandoContract.CandoEntry.TABLE, values,
                CandoContract.CandoEntry._ID + "=" + canDoId,null);

        //closing the datbase
        closeDatabase();

        //returing the result
        return result != -1;
    }

    //updating the information in the row baseed on the ID
    public int updateCanDo(int canDoId,String title, String notes, String dueDate, int priority){
        ContentValues values = new ContentValues();

        //putting the content values if the data is not null
        if (title != null){
            values.put(CandoContract.CandoEntry.COL_CANDO_TITLE,title);
        }
        if (notes != null){
            values.put(CandoContract.CandoEntry.COL_CANDO_NOTES,notes);
        }
        if (dueDate != null){
            values.put(CandoContract.CandoEntry.COL_CAND0_DUE_DATE,dueDate);
        }
        if (priority != -1){
            values.put(CandoContract.CandoEntry.COL_CANDO_PRIORITY,priority);
        }
        //updating only if there are any values in the content values
        if (values.size() >0) {
            //returning the result of the query execution
            return db.update(CandoContract.CandoEntry.TABLE, values,
                    CandoContract.CandoEntry._ID + "=" + canDoId, null);
        }
        //returning -2 if their is nothing to update
        return -2;
    }

    //deleting the row in the table based on the ID
    public boolean deleteCanDo(int canDoId){

        long result = db.delete(CandoContract.CandoEntry.TABLE, CandoContract.CandoEntry._ID + "=" + canDoId,null);
        //closing the database
        closeDatabase();
        //retruing the query execution result
        return result != -1;
    }

    //quering the data from the table and returning the result as cursor
    private Cursor getDataCursor(int status){
        return db.query(CandoContract.CandoEntry.TABLE,
                new String[]{CandoContract.CandoEntry._ID,
                        CandoContract.CandoEntry.COL_CANDO_TITLE,
                        CandoContract.CandoEntry.COL_CANDO_NOTES,
                        CandoContract.CandoEntry.COL_CAND0_DUE_DATE,
                        CandoContract.CandoEntry.COL_CANDO_PRIORITY,
                        CandoContract.CandoEntry.COL_DO_STATUS},
                CandoContract.CandoEntry.COL_DO_STATUS + "=" + status , null,null,null,null,null);
    }

    //this provied the data from the database in ArrayList of models that can be accessed easily around the application.
    public ArrayList<CanDoDataModel> getCanDoList(int status){
        Cursor cursor = getDataCursor(status);
        ArrayList<CanDoDataModel> canDoList = new ArrayList<>();
        while (cursor.moveToNext()){
            CanDoDataModel canDodata = new CanDoDataModel();
            canDodata.setId(cursor.getInt(0)); //0 -> ID column.
            canDodata.setTitle(cursor.getString(1)); //1 -> TITLE column.
            canDodata.setNotes(cursor.getString(2)); //2 -> NOTES column.
            canDodata.setDate(cursor.getString(3)); //3 -> DUE DATE column
            canDodata.setPriority(cursor.getInt(4)); //4 -> PRIORITY Column
            canDodata.setStatus(cursor.getInt(5)); //5 -> STATUS column
            canDoList.add(canDodata);
        }
        closeDatabase();
        return canDoList;
    }

    //helper method to close the databases
    private void closeDatabase(){
        db.close();
    }


}
