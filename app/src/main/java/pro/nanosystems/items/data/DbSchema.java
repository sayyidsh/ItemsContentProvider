package pro.nanosystems.items.data;

import android.provider.BaseColumns;

/**
 * Created by Sayyid Shaban on 30/01/2018.
 * E-mail: sayyid.ls@gmail.com
 */

interface DbSchema {
    String DB_NAME = "items.db";
    String TBL_ITEMS = "items";
    String TBL_PHOTOS = "photos";
    String DDL_CREATE_TBL_ITEMS =
            "CREATE TABLE " + TBL_ITEMS + " (" +
                    BaseColumns._ID + " INTEGER  PRIMARY KEY AUTOINCREMENT, \n" +
                    ItemsContract.Items.NAME + " TEXT,\n" +
                    ItemsContract.Items.BORROWER + " TEXT \n" +
                    ")";
    String DDL_CREATE_TBL_PHOTOS =
            "CREATE TABLE " + TBL_PHOTOS + " (" +
                    BaseColumns._ID + " INTEGER  PRIMARY KEY AUTOINCREMENT, \n" +
                    ItemsContract.Photos._DATA + " TEXT,\n" +
                    ItemsContract.Photos.ITEMS_ID + " INTEGER  NOT NULL  UNIQUE \n" +
                    ")";
    String DDL_CREATE_TRIGGER_DEL_ITEMS =
            "CREATE TRIGGER delete_items DELETE ON " + TBL_ITEMS + " \n"
                    + " begin\n"
                    + "  delete from \"+ItemsContract.Photos.TABLE_NAME+\" where " + ItemsContract.Photos.ITEMS_ID
                    + " = old._id;\n"
                    + " end\n";

    String DDL_DROP_TBL_ITEMS =
            "DROP TABLE IF EXISTS " + TBL_ITEMS;

    String DDL_DROP_TBL_PHOTOS =
            "DROP TABLE IF EXISTS " + TBL_PHOTOS;

    String DDL_DROP_TRIGGER_DEL_ITEMS =
            "DROP TRIGGER IF EXISTS delete_items";

    String DML_WHERE_ID_CLAUSE = BaseColumns._ID + " = ?";

    String DEFAULT_TBL_ITEMS_SORT_ORDER = ItemsContract.Items.NAME + " ASC";

    String LEFT_OUTER_JOIN_STATEMENT = TBL_ITEMS + " LEFT OUTER JOIN " + TBL_PHOTOS
            + " ON(" + TBL_ITEMS + "." + BaseColumns._ID + " = " + TBL_PHOTOS + "." + ItemsContract.Photos.ITEMS_ID + ")";

}
