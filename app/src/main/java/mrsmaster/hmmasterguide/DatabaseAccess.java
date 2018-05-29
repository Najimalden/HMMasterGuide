package mrsmaster.hmmasterguide;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FK08 on 16.05.2018.
 */

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase sqLiteDatabase;
    private static DatabaseAccess instance;
    private DatabaseAccess(Context context){
        this.openHelper = new DatabaseHelper(context);
    }

    public static DatabaseAccess getInstance(Context context){
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open() {
        this.sqLiteDatabase = openHelper.getWritableDatabase();
    }

    public void close() {
        if (sqLiteDatabase != null) {
            this.sqLiteDatabase.close();
        }
    }
    public List<String> getSchwerpunkte() {
        List<String> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT Schwerpunktname FROM Schwerpunktabelle ", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;


    }

    public List<String> getModule(int Schwerpunkt, int Semester) {
        int ModulA = 0;
        int ModulB = 0;
        switch (Schwerpunkt){
            case 1: ModulA = 2;
                    ModulB = 3;
                    break;
            case 2: ModulA = 1;
                    ModulB = 3;
                    break;
            case 3: ModulA = 1;
                    ModulB = 2;
                    break;
        }
        List<String> obligatoryList = new ArrayList<>();
        Cursor obligatoryListCursor = sqLiteDatabase.rawQuery("SELECT Modulname FROM Module where Schwerpunktmodule = "
                                                                + Schwerpunkt + " AND SoWiSe = " + Semester + " UNION SELECT Modulname FROM Module where Schwerpunktmodule = 4 " +
                                                                "AND SoWiSe = " + Semester, null);
        obligatoryListCursor.moveToFirst();
        while (!obligatoryListCursor.isAfterLast()) {
            obligatoryList.add(obligatoryListCursor.getString(0));
            obligatoryListCursor.moveToNext();
        }
        obligatoryListCursor.close();

        List<String> electiveList = new ArrayList<>();
        Cursor electiveCursor = sqLiteDatabase.rawQuery("SELECT Modulname FROM Module where Schwerpunktmodule = "
                                                        + ModulA + " AND SoWiSe = " + Semester + " UNION SELECT Modulname FROM Module where Schwerpunktmodule = "
                                                        + ModulB + " AND SoWiSe = " + Semester , null);
        electiveCursor.moveToFirst();
        while (!electiveCursor.isAfterLast()) {
            electiveList.add(electiveCursor.getString(0));
            electiveCursor.moveToNext();
        }
        electiveCursor.close();

        List<String> list = obligatoryList;
        list.addAll(electiveList);


        return list;


    }

        public boolean setChoosedModuls (List<String> choosedModuls){
        //Schreibt DB
        SQLiteDatabase db = openHelper.getWritableDatabase();
        boolean check = tableExists(db,"ModuleDesKunden");
            if(check){
                //Tabelle löschen
                String SQL_String = "DROP TABLE ModuleDesKunden";
                db.execSQL(SQL_String);
            }

        //Tabelle erstellen
        String SQL_String = "CREATE TABLE ModuleDesKunden ( " +
                            " ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            " Modul TEXT )";
        db.execSQL(SQL_String);
        ContentValues contenValue = new ContentValues();
        contenValue.put("Modul",choosedModuls.get(1));
        //Inhalt in die DB schreiben
        long  result = db.insert("ModuleDesKunden",null,contenValue);
        if (result == -1)
            return false;
        else
            return true;
            }


    private boolean tableExists(SQLiteDatabase db, String table){
        String sql = "SELECT name FROM sqlite_master WHERE type='table' AND name='"+table+"'";
        Cursor mCursor = db.rawQuery(sql, null);
        if (mCursor.getCount() > 0) {
            return true;
        }
        mCursor.close();
        return false;
    }
}