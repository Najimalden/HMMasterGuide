package mrsmaster.hmmasterguide;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by FK08 on 16.05.2018.
 */

public class DatabaseHelper extends SQLiteAssetHelper{
    private static final String strDatabaseName = "MasterDB.db";
    private static final int intDatabaseVersion = 1;

    public DatabaseHelper(Context context) {
        super(context,strDatabaseName,null,intDatabaseVersion);
    }
}
