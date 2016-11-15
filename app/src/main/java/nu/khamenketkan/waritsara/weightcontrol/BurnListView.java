package nu.khamenketkan.waritsara.weightcontrol;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

//คือหน้าที่แสดง วันปัจจุบันว่า Burn อะไร ? ไปบ้าง
// โดยการ ListView รายการ Buen ที่ Where วันปัจจุบันเท่านั้น
public class BurnListView extends AppCompatActivity {
    //Explicit
    private String dateString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_burn_list_view);

        //นี่คือการรับ Date ปัจจุบัน จาก Main Activity
        dateString = getIntent().getStringExtra("Date");

        TextView textView = (TextView) findViewById(R.id.txtBurnList);
        textView.setText("Date = " + dateString);

        createListView();

    } //Main Method

    @Override
    protected void onRestart() {
        super.onRestart();
        createListView();
    }

    private void createListView() {

        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);

        //นี่คือการ สร้าง Cursor ที่ Where ที่ Column เท่ากับ Date ดึงจำนวนที่เท่า Current Date มาแสดงเท่านั้น
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM burnTABLE WHERE Date = " + "'" + dateString + "'", null);
        cursor.moveToFirst();
        String[] exerciseStrings = new String[cursor.getCount()];
        String[] burnStrings = new String[cursor.getCount()];

        for (int i=0;i<cursor.getCount();i +=1) {
            exerciseStrings[i] = cursor.getString(cursor.getColumnIndex(MyManage.column_exercise));
            burnStrings[i] = cursor.getString(cursor.getColumnIndex(MyManage.column_burn));
            cursor.moveToNext();

        } //For
        cursor.close();
        ListView listView = (ListView) findViewById(R.id.livBurnList);
        MyAdapter myAdapter = new MyAdapter(this, exerciseStrings, burnStrings);
        listView.setAdapter(myAdapter);


    } // createListView

    public void clickAddBurn(View view) {
        Intent intent = new Intent(BurnListView.this, AddBurnActivity.class);
        intent.putExtra("Date", dateString);
        startActivity(intent);

    }

    public void clickBackBurn(View view) {
        finish();
    }

} // Main Class
