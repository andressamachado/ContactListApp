package com.AndressaMachado.agenda.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.AndressaMachado.agenda.R;
import com.AndressaMachado.agenda.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactListAdapter extends BaseAdapter {
    private final List<Contact> contacts = new ArrayList<>();
    private final Context context;

    public ContactListAdapter(Context context) {
        this.context = context;
    }

    //how many elements the adapter will contain
    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Contact getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return contacts.get(position).getId();
    }

    //For each element of the adapter
    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //add the specific view into the viewGroup
        View newView = createView(parent);

        Contact desired_contact = contacts.get(position);
        TextView name = newView.findViewById(R.id.item_contact_name);
        name.setText(desired_contact.getName());
        TextView phone = newView.findViewById(R.id.item_contact_phone);
        phone.setText(desired_contact.getPhone());
        return newView;
    }

    private View createView(ViewGroup parent) {
        return LayoutInflater
                .from(context)
                .inflate(R.layout.item_contact, parent, false);
    }

    public void update(List<Contact> contacts){
        this.contacts.clear();
        this.contacts.addAll(contacts);
        notifyDataSetChanged();

    }

    public void remove(Contact contact) {
        contacts.remove(contact);
        notifyDataSetChanged();
    }
}
