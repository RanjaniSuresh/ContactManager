/* Authors: Swathi Varadharajan and Ranjani Suresh
 * Class description: This is the Activity class for displaying list.
 *
 * */

package com.example.utd.edu;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.utd.edu.contactmanager.R;

import java.io.IOException;
import java.util.ArrayList;

public class ContactManager extends AppCompatActivity  {
    ListView listView ;
    public static ArrayList<ContactManagerVO> list = new ArrayList<>();
    /*
    * Created by: Swathi Varadharajan and Ranjani Suresh
    *
    * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_manager_list);

        FileOperations fileOperations = new FileOperations();
        try {
            list = fileOperations.readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //sort
        ContactDetails.sort(list);
        ContactAdapter adapter = new ContactAdapter(this, list);
        // Creating objects for elements in the activity.
        TextView noContacts =(TextView) findViewById(R.id.no_contacts);
        listView = (ListView) findViewById(R.id.listView);

        listView.setAdapter(adapter);

        /* Created by: Swathi Varadharajan and Ranjani Suresh
           Description: ListView Item Click Listener
        */

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ContactDetails.isSave = false;
                LinearLayout layOutObj = (LinearLayout) view;
                Intent intent = new Intent(ContactManager.this, ContactDetails.class);

                TextView firstNameText = (TextView) layOutObj.getChildAt(1);

                String firstName = firstNameText.getText().toString();
                intent.putExtra("Contact.FirstName", firstName);
                //intent.putExtra("Contact.LastName", )
                intent.putExtra("Contact.Position", position+"");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });
        /*
        Created by: Swathi Varadharajan and Ranjani Suresh
        Description: imageButton Item Click Listener( for Add button)*
         */
        if(list.size()<1) noContacts.setVisibility(View.VISIBLE);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Log.d("CheckingNow","IT comes here");
        if (id == R.id.add_new_contact) {
            Intent intent = new Intent(this, ContactDetails.class);
            intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            Intent i=new Intent(this,ContactDetails.class);
            i.putExtra("mode", "add");
            startActivity(i);
            //this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}