package by.bstu.fit.oleggutsev.lab6dbandroid;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.io.*;

public class MainActivity extends AppCompatActivity {

    private static final String FILENAME_PUBLIC = "laba6Public.txt";
    private static final String FILENAME_PRIVATE = "laba6Private.txt";
    private static final String TAG = "LAb6DB";
    private static final String DIR_SD = "MyFiles";

    private String mState = Environment.getExternalStorageState();

    private EditText mName;
    private EditText mSurname;
    private EditText mMobilePhone;
    private EditText mBirthday;
    private EditText mSearch;

    private TextView mSearchResult;

    private RadioButton mPublic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mName = (EditText) findViewById(R.id.Name_editText);
        mSurname = (EditText) findViewById(R.id.Surname_editText);
        mMobilePhone = (EditText) findViewById(R.id.MobilePhone_editText);
        mBirthday = (EditText) findViewById(R.id.Birthday_editText);
        mSearch = (EditText) findViewById(R.id.Search_editText);

        mSearchResult = (TextView) findViewById(R.id.SearchResult_textView);
        mSearchResult.setText("");

        mPublic = (RadioButton) findViewById(R.id.Public_radioButton);

    }

    public void WriteToFile_onClick(View view) {

        String str = "";

        if (mName.getText().toString().isEmpty() ||
                mSurname.getText().toString().isEmpty() ||
                mMobilePhone.getText().toString().isEmpty() ||
                mBirthday.getText().toString().isEmpty()) {
            Toast.makeText(this, "Не все пол заполнены", Toast.LENGTH_SHORT).show();
        } else {
            str = mName.getText().toString() + " "
                    + mSurname.getText().toString() + " "
                    + mMobilePhone.getText().toString() + " "
                    + mBirthday.getText().toString() + "\n";
        }

        if (Environment.MEDIA_MOUNTED.equals(mState)) {

            try {
                if (mPublic.isChecked()) {
                    File sdPath = Environment.getExternalStorageDirectory();
                    sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);
                    sdPath.mkdirs();
                    File sdFile = new File(sdPath, FILENAME_PUBLIC);
                    Log.d(TAG, "Файл " + FILENAME_PUBLIC + "cоздан");
                    BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));
                    bw.write(str);
                    bw.close();
                    Log.d(TAG, "Файл записан на SD: " + sdFile.getAbsolutePath());
                } else {
                    File sdPath = new File(super.getExternalFilesDir(DIR_SD), FILENAME_PRIVATE);
                    sdPath.mkdirs();
                    Log.d(TAG, "Файл " + FILENAME_PRIVATE + "cоздан");
                    BufferedWriter bw = new BufferedWriter(new FileWriter(sdPath));
                    bw.write(str);
                    bw.close();
                    Log.d(TAG, "Файл записан на SD: " + sdPath.getAbsolutePath());
                }
            } catch (Exception e) {
                Log.d(TAG, "Файл " + FILENAME_PUBLIC + " не создан");
            }
        }
    }

       /*try {
        // отрываем поток для записи
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                openFileOutput(FILENAME_PUBLIC, MODE_APPEND)));
        // пишем данные
        bw.write(str);
        // закрываем поток
        bw.close();
        Log.d(TAG, "Файл записан");
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }*/
        /*
        if (Environment.MEDIA_MOUNTED.equals(mState)) {
            File sdPath = Environment.getExternalStorageDirectory();
            sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);
            try {
                if (mPublic.isChecked()) {
                    sdPath.mkdirs();
                    File sdFile = new File(sdPath, FILENAME_PUBLIC);
                    Log.d(TAG, "Файл " + FILENAME_PUBLIC + "cоздан");
                    BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));
                    bw.write(str);
                    bw.close();
                    Log.d(TAG, "Файл записан на SD: " + sdFile.getAbsolutePath());
                }
            } catch (Exception e) {
                Log.d(TAG, "Файл " + FILENAME_PUBLIC + " не создан");
            }
        }*/

    public void ReadToFile_onClick(View view) {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.d(TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
            return;
        }

        File sdPath = Environment.getExternalStorageDirectory();

        sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);

        File sdFile = new File(sdPath, FILENAME_PUBLIC);
        try {
            BufferedReader br = new BufferedReader(new FileReader(sdFile));
            String str = "";
            String strSeach = mSearch.getText().toString();

            while ((str = br.readLine()) != null) {
                if (strSeach.equals(str.substring(0, str.indexOf(' ')))) {
                    mSearchResult.setText(mSearchResult.getText() + str + "\n");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
/*
 try {
                // открываем поток для чтения
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        openFileInput(FILENAME_PUBLIC)));
                String str = "";
                String strSeach = mSearch.getText().toString();

                // читаем содержимое
                while ((str = br.readLine()) != null) {
                    if(strSeach.equals(str.substring(0, str.indexOf(' ')))){
                        mSearchResult.setText(mSearchResult.getText() + str + "\n");
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
* */