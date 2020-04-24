package com.example.savehomework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    ArrayList<String> arrayList;
    ArrayList<String> list;
    MainAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.frgmant_recycler);
        adapter = new MainAdapter();
        recyclerView.setAdapter(adapter);
        editText=findViewById(R.id.main_edit);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                s = s.toString().toLowerCase();
                ArrayList<String> list = new ArrayList<>();
                for (int i = 0; i <arrayList.size() ; i++) {
                    String text = arrayList.get(i).toLowerCase();
                    if (text.contains(s)){
                        list.add(arrayList.get(i));
                    }
                }
                adapter.update(list);
                adapter.notifyDataSetChanged();
                }


            @Override
            public void afterTextChanged(Editable s) {

                //                String str1 = editText.getText().toString();
//                String str2 = arrayList.toString();
//                if (str1.contains(str2)){
//                    list.add(str1);
//                    adapter.update(list);
//                }

//                String str1 = editText.getText().toString();
//               arrayList.contains(str1);
//                String str2 = arrayList.toString();
//                char [] chararr1 = str2.toCharArray();
//                char [] chararr2 = str1.toCharArray();
//                for (char item:chararr2) {
//                    if (item == chararr1.length){
//                        Log.d("olo","o");
//                    }
//                }

            }
        });


        arrayList = new ArrayList<>();
        Button save = findViewById(R.id.main_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = editText.getText().toString();
                arrayList.add(0,info);
                adapter.update(arrayList);
            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();
        adapter.update(arrayList);
        SharedPreferences sh = getSharedPreferences("my_shared", MODE_PRIVATE);
        String str = sh.getString("TextValue", "Default name");
        String name = "";
        for (int i = 0; i < str.length() ; i++) {
            String ch = String.valueOf(str.charAt(i));
            if (ch.equals("[")){
                continue;
            }
            if (ch.equals("]")){
                arrayList.add(name);
                name = "";
                continue;
            }
            if (ch.equals(",")){
                arrayList.add(name);
                name = "";
                continue;
            }
            name+=ch;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences sh = getSharedPreferences("my_shared", MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        editor.putString("TextValue", arrayList.toString());
        editor.apply();
    }


}
