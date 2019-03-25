package app.coalery.wordsshuffler;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void onCreate(SQLiteDatabase db) {}
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public ArrayList<DBdata> getResult(int firstRange, int secondRange) {
        SQLiteDatabase db = getReadableDatabase();

        ArrayList<DBdata> result = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM Words;", null);
        while (cursor.moveToNext()) {
            int ID = cursor.getInt(0);
            if(!(ID >= firstRange && ID <= secondRange))
                continue;
            String English = cursor.getString(1);
            String Korean = cursor.getString(2);
            int Page = cursor.getInt(3);

            result.add(new DBdata(ID, English, Korean, Page));
        }
        cursor.close();
        return result;
    }
}
