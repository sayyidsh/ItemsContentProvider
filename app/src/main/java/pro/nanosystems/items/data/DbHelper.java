package pro.nanosystems.items.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sayyid Shaban on 30/01/2018.
 * E-mail: sayyid.ls@gmail.com
 */

public class DbHelper extends SQLiteOpenHelper {
    public static final int VERSION = 1;

    public DbHelper(Context context) {
        super(context, DbSchema.DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DbSchema.DDL_CREATE_TBL_ITEMS);
        db.execSQL(DbSchema.DDL_CREATE_TBL_PHOTOS);
        db.execSQL(DbSchema.DDL_CREATE_TRIGGER_DEL_ITEMS);

        ContentValues values = new ContentValues();

        values.put(ItemsContract.Items.NAME, "Android");
        values.put(ItemsContract.Items.BORROWER, "Ahmed");
        db.insert(DbSchema.TBL_ITEMS,null,values);
        values.put(ItemsContract.Items.NAME, "Java");
        values.put(ItemsContract.Items.BORROWER, "Ali");
        db.insert(DbSchema.TBL_ITEMS,null,values);
        values.put(ItemsContract.Items.NAME, "Design Pattern");
        values.put(ItemsContract.Items.BORROWER, "Hassan");
        db.insert(DbSchema.TBL_ITEMS,null,values);
        values.put(ItemsContract.Items.NAME, "Data Structure ");
        values.put(ItemsContract.Items.BORROWER, "Mohamed");
        db.insert(DbSchema.TBL_ITEMS,null,values);

        values.put(ItemsContract.Photos._DATA , "aa.png");
        values.put(ItemsContract.Photos.ITEMS_ID, 1);
        db.insert(DbSchema.TBL_PHOTOS,null,values);
        values.put(ItemsContract.Photos._DATA , "ab.png");
        values.put(ItemsContract.Photos.ITEMS_ID, 2);
        db.insert(DbSchema.TBL_PHOTOS,null,values);
        values.put(ItemsContract.Photos._DATA , "ac.png");
        values.put(ItemsContract.Photos.ITEMS_ID, 3);
        db.insert(DbSchema.TBL_PHOTOS,null,values);
        values.put(ItemsContract.Photos._DATA , "ad.png");
        values.put(ItemsContract.Photos.ITEMS_ID, 4);
        db.insert(DbSchema.TBL_PHOTOS,null,values);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DbSchema.DDL_DROP_TBL_ITEMS);
        db.execSQL(DbSchema.DDL_DROP_TBL_PHOTOS);
        db.execSQL(DbSchema.DDL_DROP_TRIGGER_DEL_ITEMS);
        onCreate(db);

    }
}
