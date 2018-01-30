package pro.nanosystems.items;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import pro.nanosystems.items.data.ItemsAdapter;
import pro.nanosystems.items.data.ItemsContract;

public class MainActivity extends AppCompatActivity {
    private Cursor mcursor;
    private ListView itemsLv;
    private EditText searchEd;
    private Button searchBtn;
    private ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itemsLv = findViewById(R.id.itemsLv);

        mcursor = getContentResolver().query(ItemsContract.Items.CONTENT_URI,
                ItemsContract.Items.PROJECTION_ALL,
                null, null, ItemsContract.Items.SORT_ORDER_DEFAULT);
        itemsAdapter = new ItemsAdapter(this, mcursor);
        itemsLv.setAdapter(itemsAdapter);

        searchEd = findViewById(R.id.searchEd);
        searchBtn = findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(v -> {
            if (!searchEd.getText().toString().isEmpty()) {
                Uri uri = ContentUris.withAppendedId(ItemsContract.Items.CONTENT_URI,
                        Integer.parseInt(searchEd.getText().toString().trim()));
                mcursor = getContentResolver().query(uri, ItemsContract.Items.PROJECTION_ALL,
                        null, null, ItemsContract.Items.SORT_ORDER_DEFAULT);
            } else {
                mcursor = getContentResolver().query(ItemsContract.Items.CONTENT_URI, ItemsContract.Items.PROJECTION_ALL,
                        null, null, ItemsContract.Items.SORT_ORDER_DEFAULT);
            }

            itemsAdapter.changeCursor(mcursor);
        });

    }
}
