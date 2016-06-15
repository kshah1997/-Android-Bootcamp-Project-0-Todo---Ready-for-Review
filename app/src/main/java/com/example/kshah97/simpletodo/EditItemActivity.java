package com.example.kshah97.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    int position_new_item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        final String original_text = getIntent().getStringExtra("original_text");
        //final int text_position = getIntent().getIntExtra("position", 0);
        EditText editItem = (EditText) findViewById(R.id.editItem);
        position_new_item = getIntent().getIntExtra("position", 0);
        editItem.setText("");
        editItem.append(original_text);

    }

    public void onSaveClick(View view) {
        EditText new_item_edit_text = (EditText) findViewById(R.id.editItem);
        Intent item_send_back = new Intent();
        item_send_back.putExtra("new_item", new_item_edit_text.getText().toString());
        item_send_back.putExtra("new_item_position",position_new_item);
        setResult(RESULT_OK, item_send_back);
        finish();

    }


}
