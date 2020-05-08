package com.AndressaMachado.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.AndressaMachado.agenda.R;
import com.AndressaMachado.agenda.model.Contact;
import com.AndressaMachado.agenda.ui.ContactListView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.jetbrains.annotations.Nullable;

import static com.AndressaMachado.agenda.ui.activity.ConstantsActivities.CONTACT_KEY;

//AppCompatActivity: gives support to old android versions
public class ContactListActivity extends AppCompatActivity {
    private static final String APPBAR_TITLE = "Contacts";
    private final ContactListView contactListView = new ContactListView(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //1st - context: Is a base class for every entity in this environment. It indicates from where is coming that action
        //2nd - Text: Message to be displayed.
        //3rd - Represents how long the message will be displayed
        //Toast.makeText(this, "Andressa Machado", Toast.LENGTH_SHORT).show();

        //The following is not a good practice because we are giving too much responsibility inside the activity:
        //better to put inside an specific layout file (static files - inside the res folder)
        //View that represents a text. It takes a context as an argument. Activity is a context, so we pass this
        //TextView contact = new TextView(this);
        //contact.setText("Andressa Machado");
        //setContentView(contact);

        //To have access to every file inside the app > res > layout folder
        setContentView(R.layout.activity_contact_list);

        //Sets the title on the app bar
        setTitle(APPBAR_TITLE);

        configureFabNewContact();

        configureList();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, view, menuInfo);
        getMenuInflater().inflate(R.menu.activity_contact_list_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        int selectedOption = item.getItemId();

        if (selectedOption == R.id.activity_contact_list_remove_menu){
            contactListView.removalConfirmation(item);
        }

        return super.onContextItemSelected(item);
    }

    private void openAddContactForm() {
        startActivity(new Intent(this, NewContactFormActivity.class));
    }

    private void configureFabNewContact() {
        FloatingActionButton newContactButton = findViewById(R.id.activity_contact_list_fab_new_contact);
        newContactButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                openAddContactForm();
            }
        });
    }

    private void openEditContactForm(Contact desired_contact) {
        //Open an activity to create an edit page
        Intent goToContactFormActivity = new Intent(ContactListActivity.this, NewContactFormActivity.class);

        //putExtra() -> transfer data between activities (primitive or objects, but needs to be serialized)
        //Check Parcelable: https://medium.com/@lucas_marciano/por-que-usar-o-parcelable-ao-inv%C3%A9s-do-serializable-5f7543a9c7f3
        goToContactFormActivity.putExtra(CONTACT_KEY, desired_contact);

        startActivity(goToContactFormActivity);
    }

    private void configureList() {
        //Responsible for saving contacts created in the form
        //View list with adapter are known as adapter Views --> flexible
        ListView contactList = findViewById(R.id.activity_contact_list_listview);

        contactListView.configureAdapter(contactList);

        //Edit contact
        configureListenerOnClickPerItem(contactList);
        //Remove contact
        //configureListenerOnLongClickPerItem(contactList);
        //It is already configured to set a context menu for every element of a view group
        registerForContextMenu(contactList);
    }

    private void configureListenerOnClickPerItem(ListView contactList) {
        //As we dont want the whole listview component to have a listener, we use setOnItemClickListener to set the listener to its elements.
        //With this line of code, we pretend to have a listener for each contact in the list of contacts (click)
        //This method is an AdapterView exclusive
        contactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //adapterView will get back the desired contact based on the position passed as a parameter
                Contact desired_contact = (Contact) adapterView.getItemAtPosition(position);

                //for testing purpose. log.i : Logcat -> info
                //log.e : error
                //log.w : warning
                //Log.i("contact: ", " " + desired_contact);

                openEditContactForm(desired_contact);
            }
        });
    }

     @Override
    protected void onResume() {
        super.onResume();

         contactListView.updateContacts();
    }

    // Long press study purpose:
//    private void configureListenerOnLongClickPerItem(ListView contactList) {
//        //setOnItemLongClickListener -> press and hold item
//        contactList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
//                //Log.i("long click", String.valueOf(position));
//                Contact contactToBeDeleted = (Contact) adapterView.getItemAtPosition(position);
//                removeContact(contactToBeDeleted);
//
//                //true if the callback consumed the long click, false otherwise.
//                return false;
//            }
//        });
//    }

}
