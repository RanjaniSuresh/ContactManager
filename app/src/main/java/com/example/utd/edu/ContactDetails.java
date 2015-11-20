/* Authors: Swathi Varadharajan and Ranjani Suresh
 * Class description: This is the Main activity class for getting  the details from the user
 *
 * */

package com.example.utd.edu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utd.edu.contactmanager.R;

import java.util.ArrayList;
import java.util.Collections;


public class ContactDetails extends AppCompatActivity {
    public static boolean isSave = true;
    public static int selectedPosition = -1;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText phone;
    private TextView fName;
    public static boolean backOk;

    /* Created by: Swathi Varadharajan and Ranjani Suresh */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);


        // Creating objects for all the elements in the layout
        //final Button saveButton = (Button) findViewById(R.id.save);
        //final Button deleteButton = (Button)findViewById(R.id.delete);

        firstName = (EditText) findViewById(R.id.firstName);

        lastName = (EditText) findViewById(R.id.lastName);

        email = (EditText) findViewById(R.id.email);
        fName=(TextView)findViewById(R.id.textView);

        phone = (EditText) findViewById(R.id.phoneNumber);
        final Intent intent = getIntent();
        // Getting the values that are passed using the adapter class
        String name = intent.getStringExtra("Contact.FirstName");
        if (intent.getStringExtra("Contact.Position") != null) {
            selectedPosition = Integer.parseInt(intent.getStringExtra("Contact.Position"));
        }

        // Setting the values for all the text fields based on the user's selection
        if (name != null) {
            firstName.setText(ContactManager.list.get(selectedPosition).getFirstName());
            lastName.setText(ContactManager.list.get(selectedPosition).getLastName());
            email.setText(ContactManager.list.get(selectedPosition).getEmail());
            phone.setText(ContactManager.list.get(selectedPosition).getPhoneNumber());
        }


    }
    /**
     * Created by Swathi and Ranjani on 11/3/2015.
     * Class description: Contains all the file input and output operations
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_delete, menu);
        if(selectedPosition==-1){
            menu.findItem(R.id.delete_contact).setEnabled(false).setVisible(false);
        }
        else{
            menu.findItem(R.id.delete_contact).setEnabled(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Created by Swathi and Ranjani on 11/3/2015.
     * Class description: Contains all the file input and output operations
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id==android.R.id.home){
            Log.d("CheckingNow", "IT comes here: home");
            onBackPressed();
            return true;
        }
        else if (id == R.id.edit_contact) {
            ContactManagerVO contact = new ContactManagerVO();
            contact.setFirstName(firstName.getText().toString());
            contact.setLastName(lastName.getText().toString());
            contact.setEmail(email.getText().toString());
            contact.setPhoneNumber(phone.getText().toString());
            if (firstName.getText().length() > 0) {
                if (selectedPosition != -1) {
                    modifyData(contact);
                    selectedPosition=-1;
                } else {
                    saveData(contact);
                }
                Intent intent = new Intent(ContactDetails.this, ContactManager.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            } else {
                Context context = getApplicationContext();
                CharSequence text = "First Name Required";
                int duration = Toast.LENGTH_SHORT;
                fName.setTextColor(getResources().getColor(R.color.red));

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }


        }


        if (id == R.id.delete_contact) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            // set title
            //alertDialogBuilder.setTitle("Your Title");

            // set dialog message
            alertDialogBuilder
                    .setMessage("Do you want to delete?")
                    .setCancelable(true)
                    .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, close the dialog box
                            delete();
                            Intent intent = new Intent(ContactDetails.this, ContactManager.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        }
                    });
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();


        }
        return super.onOptionsItemSelected(item);
    }

    /* Created by: Swathi Varadharajan and Ranjani Suresh
       Description: Modifies the Record in the file
       Arguments: DataEntryVO object
       */
    public ArrayList<ContactManagerVO> modifyData(ContactManagerVO newData) {
        FileOperations fileOp = new FileOperations();
        ContactManager.list.set(selectedPosition, newData);
        sort(ContactManager.list);
        fileOp.writeFile(ContactManager.list);
        return ContactManager.list;

    }
    /* Created by: Swathi Varadharajan and Ranjani Suresh
         Description: Saves the Record into the file
         Arguments: DataEntryVO object
    */

    public ArrayList<ContactManagerVO> saveData(ContactManagerVO newData) {

        FileOperations fileOp = new FileOperations();
        ContactManager.list.add(newData);
        sort(ContactManager.list);
        fileOp.writeFile(ContactManager.list);

        return ContactManager.list;

    }
     /* Created by: Swathi Varadharajan and Ranjani Suresh
         Description: Deletes Record from the file
         Arguments: No Args
    */

    public ArrayList<ContactManagerVO> delete() {
        FileOperations fileOperations = new FileOperations();
        if (selectedPosition == -1) {
            clear();
        } else {
            ContactManager.list.remove(selectedPosition);
        }
        sort(ContactManager.list);
        fileOperations.writeFile(ContactManager.list);
        selectedPosition=-1;
        return ContactManager.list;
    }

    /* Created by: Swathi Varadharajan and Ranjani Suresh
    *  Description: Clears all the text fields
    * */
    public void clear() {
        final EditText firstName = (EditText) findViewById(R.id.firstName);
        final EditText lastName = (EditText) findViewById(R.id.lastName);
        final EditText email = (EditText) findViewById(R.id.email);
        final EditText phone = (EditText) findViewById(R.id.phoneNumber);
        firstName.setText("");
        lastName.setText("");
        email.setText("");
        phone.setText("");

    }
    /* Created by: Swathi Varadharajan and Ranjani Suresh
    *  Description: Sorts all data
    *  Argument: Data to be sorted
    * */

    public static ArrayList<ContactManagerVO> sort(ArrayList<ContactManagerVO> oldData) {
        Collections.sort(oldData);
        return oldData;

    }

    @Override
    public void onPause(){
        selectedPosition=-1;
        super.onPause();
    }
    @Override
    public void onBackPressed(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        backOk=false;
        // set title
        //alertDialogBuilder.setTitle("Your Title");

        // set dialog message
        alertDialogBuilder
                .setMessage("Discard Changes?")
                .setCancelable(true)
                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close the dialog box
                        backOk=true;
                        Intent intent = new Intent(ContactDetails.this, ContactManager.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();



    }



}
