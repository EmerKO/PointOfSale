package ie.ul.emerkennedyozdemir.pointofsale;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mNameTextView, mQuantityTextView, mDateTextView;
    private Item mCurrentItem;
    private Item mClearedItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNameTextView = findViewById(R.id.name_text);
        mQuantityTextView = findViewById(R.id.quantity_text);
        mDateTextView = findViewById(R.id.date_text);


        //Boilerplate code. Dom't mess with it.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
            }
        });
    }

    private void addItem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //simple dialog
        //builder.setTitle("");
        //builder.setMessage("");
        //builder.setPositiveButton("OK", null);
        //builder.setTitle(ie.ul.emerkennedyozdemir.pointofsale.R.string.add_item)
        View view = getLayoutInflater().inflate(R.layout.dialog_add, null, false);
        builder.setView(view);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);

        builder.create().show();

    }

    private void showCurrentItem() {
        mNameTextView.setText(mCurrentItem.getName());
        mQuantityTextView.setText(getString(R.string.quantity_format, mCurrentItem.getQuantity()));
        mDateTextView.setText(getString(R.string.date_format, mCurrentItem.getDeliveryDateString()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present. Boilerplate code
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.action_reset:
                mClearedItem = mCurrentItem;
                mCurrentItem = new Item();
                showCurrentItem();
              //todo: use a snackbar to enable undo
              Snackbar snackbar = Snackbar.make(findViewById(R.id.coordinator_layout),
                      "Item Cleared", Snackbar.LENGTH_LONG);
              snackbar.setAction("UNDO", new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      // todo: do the undo
                      mCurrentItem = mClearedItem;
                      showCurrentItem();
                      Snackbar.make(findViewById(R.id.coorddinator_layout),
                              "Item restored", Snackbar.LENGTH_SHORT).show();

                  }
              });

              snackbar.show();
                return true;
            case R.id.action_settings:
                //startActivity(new Intent(Settings.ACTION_SETTINGS));
                startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));

                return true;
        }

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
