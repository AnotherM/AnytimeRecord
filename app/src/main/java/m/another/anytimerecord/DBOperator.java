package m.another.anytimerecord;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MILK on 2016/2/14.
 * Thanks to: http://my.oschina.net/jettWang/blog/613343?fromerr=mjpojPpL
 */

@SuppressWarnings("ALL")
class DBOperator {

    private final SQLiteOpenHelper mDBOpenHelper;
    private final Context mContext;
    private final String tableName;
    private SQLiteDatabase database;

    public DBOperator(Context context) {
        this.mContext = context;
        mDBOpenHelper = new DBOpenHelper(context);
        this.tableName = DBOpenHelper.TABLE_NAME;
    }

    public long insert(String money, String category, String date, String time, String note) {
        long i = -1;
        database = mDBOpenHelper.getWritableDatabase();
        if (database.isOpen()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBOpenHelper.DATA_MONEY, money);
            if (TextUtils.isEmpty(category)) {
                contentValues.put(DBOpenHelper.DATA_CATEGORY, mContext.getString(R.string.not_set));
            } else {
                contentValues.put(DBOpenHelper.DATA_CATEGORY, category);
            }
            contentValues.put(DBOpenHelper.DATA_DATE, date);
            contentValues.put(DBOpenHelper.DATA_TIME, time);
            if (TextUtils.isEmpty(note)) {
                contentValues.put(DBOpenHelper.DATA_NOTE, mContext.getString(R.string.not_set));
            } else {
                contentValues.put(DBOpenHelper.DATA_NOTE, note);
            }
            i = database.insert(tableName, null, contentValues);
            database.close();
        }
        return i;
    }

    public boolean delete(int id, String money, String category, String date, String time, String note) {
        database = mDBOpenHelper.getWritableDatabase();
        if (database.isOpen()) {
            database.delete(tableName, "id=? AND money=? AND category=? AND date=? AND time=? AND note=?", new String[]{String.valueOf(id), money, category, date, time, note});
            database.close();
            return true;
        }
        return false;
    }

    public int update(String oldId, String oldMoney, String oldCategory, String oldDate, String oldTime, String oldNote, String newId, String newMoney, String newCategory, String newDate, String newTime, String newNote) {
        database = mDBOpenHelper.getWritableDatabase();
        int i = -1;
        if (database.isOpen()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBOpenHelper.ID, newId);
            contentValues.put(DBOpenHelper.DATA_MONEY, newMoney);
            if (TextUtils.isEmpty(newCategory)) {
                contentValues.put(DBOpenHelper.DATA_CATEGORY, mContext.getString(R.string.not_set));
            } else {
                contentValues.put(DBOpenHelper.DATA_CATEGORY, newCategory);
            }
            contentValues.put(DBOpenHelper.DATA_DATE, newDate);
            contentValues.put(DBOpenHelper.DATA_TIME, newTime);
            if (TextUtils.isEmpty(newNote)) {
                contentValues.put(DBOpenHelper.DATA_NOTE, mContext.getString(R.string.not_set));
            } else {
                contentValues.put(DBOpenHelper.DATA_NOTE, newNote);
            }
            i = database.update(tableName, contentValues, "id=? AND money=? AND category=? AND date=? AND time=? AND note=?", new String[]{oldId, oldMoney, oldCategory, oldDate, oldTime, oldNote});
            database.close();
        }
        return i;
    }


    public List<DataBean> queryAll() {
        database = mDBOpenHelper.getReadableDatabase();
        List<DataBean> dataBeanList = null;
        if (database.isOpen()) {
            dataBeanList = new ArrayList<>();
            Cursor cursor = database.query(tableName, null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                DataBean dataBean = new DataBean();
                int idIndex = cursor.getColumnIndex(DBOpenHelper.ID);
                int moneyIndex = cursor.getColumnIndex(DBOpenHelper.DATA_MONEY);
                int categoryIndex = cursor.getColumnIndex(DBOpenHelper.DATA_CATEGORY);
                int dateIndex = cursor.getColumnIndex(DBOpenHelper.DATA_DATE);
                int timeIndex = cursor.getColumnIndex(DBOpenHelper.DATA_TIME);
                int noteIndex = cursor.getColumnIndex(DBOpenHelper.DATA_NOTE);
                String idStr = cursor.getString(idIndex);
                String moneyStr = cursor.getString(moneyIndex);
                String categoryStr = cursor.getString(categoryIndex);
                String dateStr = cursor.getString(dateIndex);
                String timeStr = cursor.getString(timeIndex);
                String noteStr = cursor.getString(noteIndex);
                dataBean.setId(Integer.parseInt(idStr));
                dataBean.setMoney(moneyStr);
                dataBean.setCategory(categoryStr);
                dataBean.setDate(dateStr);
                dataBean.setTime(timeStr);
                dataBean.setNote(noteStr);
                dataBeanList.add(dataBean);
            }
            cursor.close();
            database.close();
        }
        return dataBeanList;
    }
}
