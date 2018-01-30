package pro.nanosystems.items.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by Sayyid Shaban on 30/01/2018.
 * E-mail: sayyid.ls@gmail.com
 */

public class Provider extends ContentProvider {
    private static final int ITEM_LIST = 1;
    private static final int ITEM_ID = 2;
    private static final int PHOTO_LIST = 3;
    private static final int PHOTO_ID = 4;
    private static final int ENTITY_LIST = 5;
    private static final int ENTITY_ID = 6;
    private static final UriMatcher MATCHER;
    private DbHelper mHelper = null;

    static {
        MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        MATCHER.addURI(ItemsContract.AUTHORITY, ItemsContract.Items.ACTION_PATH, ITEM_LIST);
        MATCHER.addURI(ItemsContract.AUTHORITY, ItemsContract.Items.ACTION_PATH + "/#", ITEM_ID);
        MATCHER.addURI(ItemsContract.AUTHORITY, ItemsContract.Photos.ACTION_PATH, PHOTO_LIST);
        MATCHER.addURI(ItemsContract.AUTHORITY, ItemsContract.Photos.ACTION_PATH + "/#", PHOTO_ID);
        MATCHER.addURI(ItemsContract.AUTHORITY, ItemsContract.ItemEntities.ACTION_PATH, ENTITY_LIST);
        MATCHER.addURI(ItemsContract.AUTHORITY, ItemsContract.ItemEntities.ACTION_PATH + "/#", ENTITY_ID);
    }


    @Override
    public boolean onCreate() {
        mHelper = new DbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection, @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        switch (MATCHER.match(uri)) {
            case ITEM_LIST:
                builder.setTables(DbSchema.TBL_ITEMS);
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = ItemsContract.Items.SORT_ORDER_DEFAULT;
                }
                break;
            case ITEM_ID:
                builder.setTables(DbSchema.TBL_ITEMS);
                builder.appendWhere(ItemsContract.Items._ID + " = " + uri.getLastPathSegment());
                break;
            case PHOTO_LIST:
                builder.setTables(DbSchema.TBL_PHOTOS);
                builder.appendWhere(ItemsContract.Photos._ID + " = " + uri.getLastPathSegment());
                break;
            case ENTITY_LIST:
                builder.setTables(DbSchema.LEFT_OUTER_JOIN_STATEMENT);
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = ItemsContract.ItemEntities.SORT_ORDER_DEFAULT;
                }
                break;
            case ENTITY_ID:
                builder.setTables(DbSchema.LEFT_OUTER_JOIN_STATEMENT);
                builder.appendWhere(DbSchema.TBL_ITEMS + "." + ItemsContract.Items._ID + " = " + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        Cursor cursor = builder.query(db, projection, selection, selectionArgs,
                null, null, sortOrder);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        if (MATCHER.match(uri) != ITEM_LIST && MATCHER.match(uri) != PHOTO_LIST) {
            throw new IllegalArgumentException(
                    "Unsupported URI for insertion: " + uri);
        }
        SQLiteDatabase db = mHelper.getWritableDatabase();
        if (MATCHER.match(uri) == ITEM_LIST) {
            long id = db.insert(DbSchema.TBL_ITEMS, null, values);
            return getUriForId(id, uri);
        } else {
            long id = db.insertWithOnConflict(DbSchema.TBL_PHOTOS, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            return getUriForId(id, uri);
        }
    }

    private Uri getUriForId(long id, Uri uri) {
        if (id > 0) {
            Uri itemUri = ContentUris.withAppendedId(uri, id);
            return itemUri;
        }
        throw new SQLException(
                "Problem while inserting into uri: " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        int delCount = 0;
        switch (MATCHER.match(uri)) {
            case ITEM_LIST:
                delCount = db.delete(
                        DbSchema.TBL_ITEMS,
                        selection,
                        selectionArgs);
                break;
            case ITEM_ID:
                String idStr = uri.getLastPathSegment();
                String where = ItemsContract.Items._ID + " = " + idStr;
                if (!TextUtils.isEmpty(selection)) {
                    where += " AND " + selection;
                }
                delCount = db.delete(
                        DbSchema.TBL_ITEMS,
                        where,
                        selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        return delCount;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        int updateCount = 0;
        switch (MATCHER.match(uri)) {
            case ITEM_LIST:
                updateCount = db.update(
                        DbSchema.TBL_ITEMS,
                        values,
                        selection,
                        selectionArgs);
                break;
            case ITEM_ID:
                String idStr = uri.getLastPathSegment();
                String where = ItemsContract.Items._ID + " = " + idStr;
                if (!TextUtils.isEmpty(selection)) {
                    where += " AND " + selection;
                }
                updateCount = db.update(
                        DbSchema.TBL_ITEMS,
                        values,
                        where,
                        selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        return updateCount;
    }
}
