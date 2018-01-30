package pro.nanosystems.items.data;

import android.net.Uri;
import android.provider.BaseColumns;

import static pro.nanosystems.items.data.DbSchema.TBL_PHOTOS;

/**
 * Created by Sayyid Shaban on 30/01/2018.
 * E-mail: sayyid.ls@gmail.com
 */

public final class ItemsContract {
    public static final String AUTHORITY = "pro.nanosystems.items";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final class Items implements CommonColumns {
        public static final String ACTION_PATH = "items";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(ItemsContract.CONTENT_URI, ACTION_PATH);
        public static final String[] PROJECTION_ALL = {_ID, NAME, BORROWER};
        public static final String SORT_ORDER_DEFAULT = NAME + " ASC";
    }

    public static final class Photos implements BaseColumns {
        public static final String ACTION_PATH = "photos";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(ItemsContract.CONTENT_URI, ACTION_PATH);
        public static final String _DATA = "_data";
        public static final String ITEMS_ID = "items_id";
        public static final String[] PROJECTION_ALL = {_ID, _DATA, ITEMS_ID};
    }

    public static final class ItemEntities implements CommonColumns {
        public static final String ACTION_PATH = "entities";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(ItemsContract.CONTENT_URI, ACTION_PATH);
        public static final String ITEMS_ID = "items_id";
        public static final String[] PROJECTION_ALL = {TBL_PHOTOS + "." + _ID, NAME, BORROWER, Photos._DATA};
        public static final String SORT_ORDER_DEFAULT = NAME + " ASC";
    }

    public interface CommonColumns extends BaseColumns {
        String NAME = "item_name";
        String BORROWER = "borrower";
    }
}
