package pro.nanosystems.items.data;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pro.nanosystems.items.R;

/**
 * Created by Sayyid Shaban on 30/01/2018.
 * E-mail: sayyid.ls@gmail.com
 */

public class ItemsAdapter extends CursorAdapter {
    public ItemsAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.row_items, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView itemName = view.findViewById(R.id.item_nameView);
        TextView borrowerView = view.findViewById(R.id.borrowerView);

        itemName.setText(cursor.getString(cursor.getColumnIndex(ItemsContract.Items.NAME)));
        borrowerView.setText(cursor.getString(cursor.getColumnIndex(ItemsContract.Items.BORROWER)));

    }
}
