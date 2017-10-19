package com.android.jayanth.cando.Database;

import android.provider.BaseColumns;

/**
 * Created by Jayanth Devulapally on 8/7/17.
 * database definition/contract class that contains the BaseColumns that helps in basic SQl operations performed on the SQLite database
 * used in the SQLiteOpenHelper calss
 */

public class CandoContract {

    public static final String DB_NAME = "com.jayanth.cando.db";
    public static final int DB_VERSION= 1;

    public class CandoEntry implements BaseColumns{

        public static final String TABLE = "candos";
        public static final String COL_CANDO_TITLE = "title";
        public static final String COL_CANDO_NOTES = "notes";
        public static final String COL_CAND0_DUE_DATE = "due_date";
        public static final String COL_CANDO_PRIORITY = "priority";
        public static final String COL_DO_STATUS = "status";
    }
}
