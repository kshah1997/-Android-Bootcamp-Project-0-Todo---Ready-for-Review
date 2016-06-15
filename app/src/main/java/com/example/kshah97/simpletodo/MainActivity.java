package com.example.kshah97.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView list_view_items;
    ArrayList<String> itemsArray;
    ArrayAdapter<String> itemsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readItems();

        list_view_items = (ListView) findViewById(R.id.lvItems);


        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemsArray);

        list_view_items.setAdapter(itemsAdapter);

        setupListViewListener();

        itemClickListener();





    }

    public void onClickAdd(View view) {

        EditText item_to_add = (EditText) findViewById(R.id.etNewItem);

        String new_item = item_to_add.getText().toString();

        itemsAdapter.add(new_item);

        item_to_add.setText("");

        writeItems();


    }



    public void setupListViewListener() {
        list_view_items.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                itemsArray.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File simpleTodo = new File(filesDir,"simpleTodo.txt");

        try {
            itemsArray =  new ArrayList<String>(FileUtils.readLines(simpleTodo));
        } catch (IOException e) {
            itemsArray = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File simpleTodo = new File(filesDir,"simpleTodo.txt");

        try {

            FileUtils.writeLines(simpleTodo, itemsArray);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void itemClickListener() {
        list_view_items.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int result = 0;

                Intent intent_to_edit = new Intent(MainActivity.this, EditItemActivity.class);
                intent_to_edit.putExtra("original_text",itemsArray.get(position));
                intent_to_edit.putExtra("position", position);
                startActivityForResult(intent_to_edit,result);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String new_item_back = data.getExtras().getString("new_item");
        int new_item_back_position = data.getExtras().getInt("new_item_position");
        itemsArray.set(new_item_back_position,new_item_back);
        itemsAdapter.notifyDataSetChanged();
        writeItems();
        Toast.makeText(this, new_item_back, Toast.LENGTH_SHORT).show();


    }
}
