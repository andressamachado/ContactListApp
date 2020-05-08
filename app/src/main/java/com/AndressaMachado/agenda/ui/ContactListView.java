package com.AndressaMachado.agenda.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.AndressaMachado.agenda.dao.ContactDAO;
import com.AndressaMachado.agenda.model.Contact;
import com.AndressaMachado.agenda.ui.adapter.ContactListAdapter;

public class ContactListView {
    private final ContactListAdapter adapter;
    private final ContactDAO dao;
    private final Context context;


    public ContactListView(Context context) {
        this.context = context;
        //Adapter: data linker and rendering view
        //android.R.layout -> ready to use layouts from android framework
        this.adapter = new ContactListAdapter(context);
        this.dao = new ContactDAO();
    }

    public void removalConfirmation(final MenuItem item) {
        //Builder allow us to add some behaviors at the moment of the creation
        new AlertDialog.Builder(context)
                .setTitle("Remove Contact")
                .setMessage("You are going to lose this information. Are you sure you want to delete it?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //Adapter specific. It will just work if used with AdapterView
                        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

                        Contact contactToBeDeleted = adapter.getItem(menuInfo.position);
                        removeContact(contactToBeDeleted);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    public void updateContacts() {
        adapter.update(dao.all());
    }

    private void removeContact(Contact contact) {
        dao.remove(contact);
        //Sets the remove behavior
        adapter.remove(contact);
        Toast.makeText(context, "Contact Removed", Toast.LENGTH_SHORT).show();
    }

    public void configureAdapter(ListView contactList) {
        contactList.setAdapter(adapter);
    }

}
