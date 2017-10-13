package by.bstu.fit.oleggutsev.lab6dbandroid2;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.*;

public class MainActivity extends AppCompatActivity {

    private static final String FILENAME_PUBLIC = "laba6Public.txt";
    private static final String FILENAME_PRIVATE = "laba6Private.txt";
    private static final String TAG = "LAb6DB";
    private static final String DIR_SD = "MyFiles";

    private String mState = Environment.getExternalStorageState();

    private EditText mSearch;
    private TextView mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearch = (EditText) findViewById(R.id.Search_editText);
        mResult = (TextView) findViewById(R.id.Result_textView);
        mResult.setText("");
    }

    public void Search_onClick(View view) {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.d(TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
            return;
        }

        if (mSearch.getText().toString().isEmpty()) {
            Toast.makeText(this, "Не все поля заполнены!", Toast.LENGTH_SHORT).show();
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
                if (str.endsWith(mSearch.getText().toString())) {
                    mResult.setText(mResult.getText() + str + "\n");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

/*  try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    openFileInput(FILENAME_PUBLIC)));
            String str = "";
            String strSeach = mSearch.getText().toString();

            // читаем содержимое
            while ((str = br.readLine()) != null) {
                if(strSeach.equals(str.endsWith("26.04.1998"))){
                    mResult.setText(mResult.getText() + str + "\n");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    } */
