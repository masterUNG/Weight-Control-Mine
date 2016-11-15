package nu.khamenketkan.waritsara.weightcontrol;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    //Explicit
    private EditText nameEditText, surnameEditText,
            weightEditText, heightEditText,
            ageEditText, sexEditText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        //Bind Widget
        nameEditText = (EditText) findViewById(R.id.editText8);
        surnameEditText = (EditText) findViewById(R.id.editText9);
        weightEditText = (EditText) findViewById(R.id.editText10);
        heightEditText = (EditText) findViewById(R.id.editText11);
        ageEditText = (EditText) findViewById(R.id.editText12);
        sexEditText = (EditText) findViewById(R.id.editText13);
        button = (Button) findViewById(R.id.button8);


        //Load Value From SQLite to Display on Edit Text
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM userTABLE", null);
        cursor.moveToFirst();

        nameEditText.setText(cursor.getString(cursor.getColumnIndex(MyManage.column_Name)));
        surnameEditText.setText(cursor.getString(cursor.getColumnIndex(MyManage.column_Surname)));
        weightEditText.setText(cursor.getString(cursor.getColumnIndex(MyManage.column_Weight)));
        heightEditText.setText(cursor.getString(cursor.getColumnIndex(MyManage.column_Height)));
        ageEditText.setText(cursor.getString(cursor.getColumnIndex(MyManage.column_Age)));
        sexEditText.setText(cursor.getString(cursor.getColumnIndex(MyManage.column_Sex)));

    }   // Main Method

}   // Main Class
