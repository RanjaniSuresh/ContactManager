/* Authors: Swathi Varadharajan and Ranjani Suresh
 * Class description: This is an adapter class that implements the default ArrayAdapter class
 *
 * */

package com.example.utd.edu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.utd.edu.contactmanager.R;

import java.util.ArrayList;

public class ContactAdapter extends ArrayAdapter<ContactManagerVO> {

    public ContactAdapter (Context context, ArrayList<ContactManagerVO> contact) {
        super(context, 0, contact);
    }
    /*
    * Created by: Swathi Varadharajan and Ranjani Suresh
    * */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ContactManagerVO contact = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.textviewlayout, parent, false);
        }
        // Lookup view for data population
        TextView name = (TextView) convertView.findViewById(R.id.Name);
        TextView phone = (TextView) convertView.findViewById(R.id.Phone);
        TextView name2 = (TextView) convertView.findViewById(R.id.name2);
        // Populate the data into the template view using the data object
        name.setText(contact.getFirstName());
        name2.setText(contact.getLastName());
        phone.setText(contact.getPhoneNumber());
        // Return the completed view to render on screen
        return convertView;
    }

}
