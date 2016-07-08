package m.another.anytimerecord;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


class DBOpenHelper extends SQLiteOpenHelper {
    //数据库表的定义
    final static String TABLE_NAME = "data";
    final static String DATA_MONEY = "money";
    final static String DATA_CATEGORY = "category";
    final static String DATA_DATE = "date";
    final static String DATA_TIME = "time";
    final static String DATA_NOTE = "note";
    final static String ID = "id";

    public DBOpenHelper(Context context) {
        super(context, "data.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME
                + "("
                + ID + " integer primary key autoincrement, "
                + DATA_MONEY + " money, "
                + DATA_CATEGORY + " text, "
                + DATA_DATE + " varchar(20), "
                + DATA_TIME + " varchar(20), "
                + DATA_NOTE + " text "
                + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}