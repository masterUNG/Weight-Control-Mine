package nu.khamenketkan.waritsara.weightcontrol;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//คลาส ที่ทำหน้าที่ในการ แก้ไข user
public class EditActivity extends AppCompatActivity {

    //Explicit
    private EditText nameEditText, surnameEditText,
            weightEditText, heightEditText,
            ageEditText, sexEditText;
    private Button button;
    private MyCalculateBMR myCalculateBMR;
    private String nameString, surnameString, weightString,
            heightString, ageString, sexString, bmrString;
    private String[] sexStrings = new String[]{"Male", "Female"};
    private int index = 0;

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
        final SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM userTABLE", null);
        cursor.moveToFirst();

        nameEditText.setText(cursor.getString(cursor.getColumnIndex(MyManage.column_Name)));
        surnameEditText.setText(cursor.getString(cursor.getColumnIndex(MyManage.column_Surname)));
        weightEditText.setText(cursor.getString(cursor.getColumnIndex(MyManage.column_Weight)));
        heightEditText.setText(cursor.getString(cursor.getColumnIndex(MyManage.column_Height)));
        ageEditText.setText(cursor.getString(cursor.getColumnIndex(MyManage.column_Age)));
        sexEditText.setText(cursor.getString(cursor.getColumnIndex(MyManage.column_Sex)));

        //Button Controller
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get Value from Edit Text ไปไว้ที่ String
                nameString = nameEditText.getText().toString().trim();
                surnameString = surnameEditText.getText().toString().trim();
                weightString = weightEditText.getText().toString().trim();
                heightString = heightEditText.getText().toString().trim();
                ageString = ageEditText.getText().toString().trim();
                sexString = sexEditText.getText().toString().trim();

                //Assign Value to index โดย 0 ==> male, 1 ==> female
                if (sexString.equals(sexStrings[1])) {
                    index = 1;
                }   // if

                //คือการคำนวนค่าของ BMR ใหม่
                myCalculateBMR = new MyCalculateBMR(EditActivity.this,
                        index,
                        Double.parseDouble(weightString),
                        Double.parseDouble(heightString),
                        Double.parseDouble(ageString));
                String strBMR = myCalculateBMR.myBMR();

                //Delete All SQLite ลบค่าเก่าทั้งหมดออกไป
                sqLiteDatabase.delete(MyManage.user_table, null, null);

                //Update Value to SQLite
                MyManage myManage = new MyManage(EditActivity.this);
                myManage.addUser(nameString, surnameString, weightString,
                        heightString, sexString, ageString, strBMR);

                //Alert 4 วินาที ว่า สำเร็จ
                Toast.makeText(EditActivity.this, "แก้ไขเรียบร้อยแล้ว คะ", Toast.LENGTH_SHORT).show();
                finish();

            }   // onClick
        });


    }   // Main Method

}   // Main Class
