package com.abhishek.travel;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.abhishek.travel.R;

public class DetailsForm extends ActionBarActivity {

    String username_string ;
    String email_string;
    EditText editText_name;
    EditText editText_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_form);

        Intent intent =getIntent();

         username_string = intent.getExtras().getString("name");
       // Toast.makeText(getBaseContext(),username_string,Toast.LENGTH_SHORT).show();

         email_string =intent.getExtras().getString("email");

        editText_email = (EditText)findViewById(R.id.email_id);
        editText_name = (EditText)findViewById(R.id.name_user);


        editText_email.setText(username_string+"jkhkjbkjbjbk");
        editText_name.setText(email_string);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
