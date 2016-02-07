package com.abhishek.travel;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class DetailsForm extends ActionBarActivity {

    String userName;
    String email;
    EditText UserName;
    EditText Email;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_form);
        Intent intent =getIntent();
        userName = intent.getExtras().getString("name");
        email =intent.getExtras().getString("email");
        Email = (EditText)findViewById(R.id.email_id);
        UserName = (EditText)findViewById(R.id.name_user);
        Email.setText(email);
        UserName.setText(userName);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
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
