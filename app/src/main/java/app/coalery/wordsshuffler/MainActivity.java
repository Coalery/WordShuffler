package app.coalery.wordsshuffler;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final String dbPath = Environment.getExternalStorageDirectory() + File.separator + "c_db" + File.separator + "wordq.sqlite";

    private ArrayList<DBdata> originData;
    private ArrayList<String> items;
    private ArrayAdapter<String> wordAdapter;

    private TextView firstRange;
    private TextView secondRange;

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        firstRange = findViewById(R.id.editText);
        secondRange = findViewById(R.id.editText2);

        items = new ArrayList<>();

        File dir = new File(Environment.getExternalStorageDirectory() + File.separator + "c_db");
        if(!dir.exists()) dir.mkdir();

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() { public void onClick(View view) {
            if(!new File(dbPath).exists()) {
                Toast.makeText(getApplicationContext(), "DB 파일을 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
                return;
            }
            if(firstRange.getText().toString().equals("") || firstRange.getText() == null || secondRange.getText().toString().equals("") || secondRange.getText() == null) {
                Toast.makeText(getApplicationContext(), "범위를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                return;
            }
            int firstRange_i, secondRange_i;
            try {
                firstRange_i = Integer.parseInt(firstRange.getText().toString());
                secondRange_i = Integer.parseInt(secondRange.getText().toString());
            } catch (NumberFormatException ex) {
                Toast.makeText(getApplicationContext(), "범위를 숫자로 입력해 주세요.", Toast.LENGTH_SHORT).show();
                return;
            }
            DBHelper dbHelper = new DBHelper(getApplicationContext(), dbPath, null, 1);
            originData = dbHelper.getResult(firstRange_i, secondRange_i);

            items.clear();
            for(int i=0; i<originData.size(); i++)
                items.add(originData.get(i).getEnglish());
            Log.d("[ Test ]", items.size() + "");
            wordAdapter.notifyDataSetChanged();
        }});

        Button shuffleButton = findViewById(R.id.shufflebutton);
        shuffleButton.setOnClickListener(new View.OnClickListener() { public void onClick(View view) {
            if(!new File(dbPath).exists()) {
                Toast.makeText(getApplicationContext(), "DB 파일을 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
                return;
            }
            if(!new File(dbPath).exists()) {
                Toast.makeText(getApplicationContext(), "DB 파일을 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
                return;
            }
            if(firstRange.getText().toString().equals("") || firstRange.getText() == null || secondRange.getText().toString().equals("") || secondRange.getText() == null) {
                Toast.makeText(getApplicationContext(), "범위를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                return;
            }
            int firstRange_i, secondRange_i;
            try {
                firstRange_i = Integer.parseInt(firstRange.getText().toString());
                secondRange_i = Integer.parseInt(secondRange.getText().toString());
            } catch (NumberFormatException ex) {
                Toast.makeText(getApplicationContext(), "범위를 숫자로 입력해 주세요.", Toast.LENGTH_SHORT).show();
                return;
            }
            DBHelper dbHelper = new DBHelper(getApplicationContext(), dbPath, null, 1);
            originData = dbHelper.getResult(firstRange_i, secondRange_i);

            items.clear();
            for(int i=0; i<originData.size(); i++) {
                int randomv = (int)(Math.random()*originData.size());
                DBdata tmp = originData.get(i);
                originData.set(i, originData.get(randomv));
                originData.set(randomv, tmp);
            }
            for(int i=0; i<originData.size(); i++)
                items.add(originData.get(i).getEnglish());
            wordAdapter.notifyDataSetChanged();
        }});

        wordAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, items);

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(wordAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Toast.makeText(getApplicationContext(), originData.get(i).toString(), Toast.LENGTH_SHORT).show();
        }});
    }
}