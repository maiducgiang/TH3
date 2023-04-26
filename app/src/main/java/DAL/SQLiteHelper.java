package DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import model.Item;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static  final String DATABASE_NAME = "chitieu.db";
    private static int DATABASE_VERSION = 1;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE items("+
                "id integer primary key autoincrement,"+
                "title text, category text, price text, date text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    @Override
    public void onOpen(SQLiteDatabase db){
        super.onOpen(db);
    }

    //lay danh sach theo thoi gian
    public List<Item> getAll(){
        List<Item> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String order = "date DESC";
        Cursor rs = sqLiteDatabase.query("items",null,null,null,null,null,order);
        while ((rs!=null) && rs.moveToNext()){
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String price = rs.getString(3);
            String date = rs.getString(4);
            list.add(new Item(id,title,category,price,date));

        }
        return list;
    }
    public List<Item> getByDate( String date){
        List<Item> list = new ArrayList<>();
        String whereClause = "date like?";
        String[] whereArgs ={date};

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        Cursor rs = sqLiteDatabase.query("items",null,whereClause,whereArgs,null,null,null);
        while ((rs!=null) && rs.moveToNext()){
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String price = rs.getString(3);
            String date1 = rs.getString(4);
            list.add(new Item(id,title,category,price,date1));

        }
        return list;
    }
    public long addItem(Item i){
        ContentValues values = new ContentValues();
        values.put("title", i.getTitle());
        values.put("price", i.getPrice());
        values.put("category", i.getCategory());
        values.put("date", i.getDate());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert("items",null, values);
    }

    public int update(Item i){
        ContentValues values = new ContentValues();
        values.put("title", i.getTitle());
        values.put("price", i.getPrice());
        values.put("category", i.getCategory());
        values.put("date", i.getDate());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String whereClause = "id =?";
        String[] whereArgs ={Integer.toString(i.getId()) };
        return sqLiteDatabase.update("items", values, whereClause, whereArgs);

    }


    public int delete(int id){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String whereClause = "id =?";
        String[] whereArgs ={Integer.toString(id) };
        return sqLiteDatabase.delete("items", whereClause, whereArgs);
    }
}
