package com.AndressaMachado.agenda.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import com.AndressaMachado.agenda.R;
import com.AndressaMachado.agenda.dao.ContactDAO;
import com.AndressaMachado.agenda.model.Contact;

import static com.AndressaMachado.agenda.ui.activity.ConstantsActivities.APPBAR_TITLE_EDIT_CONTACT;
import static com.AndressaMachado.agenda.ui.activity.ConstantsActivities.APPBAR_TITLE_NEW_CONTACT;
import static com.AndressaMachado.agenda.ui.activity.ConstantsActivities.CONTACT_KEY;

//documentation about the activity`s lifecycle https://developer.android.com/reference/android/app/Activity
public class NewContactFormActivity extends AppCompatActivity {
    private EditText nameField;
    private EditText phoneField;
    private EditText emailField;
    private Contact contact;
    private final ContactDAO dao = new ContactDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set the view to be the new contact form one
        setContentView(R.layout.activity_new_contact_form);
        fieldsInitialization();
        //configureSaveButton();
        loadsContactInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_contact_form_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int selectedOption = item.getItemId();

        if (selectedOption == R.id.activity_form_contact_menu_save){
            finalizeForm();
        }
        return super.onOptionsItemSelected(item);
    }

    private void fieldsInitialization() {
        nameField = findViewById(R.id.activity_form_contact_name);
        phoneField = findViewById(R.id.activity_form_contact_phone);
        emailField = findViewById(R.id.activity_form_contact_email);
    }

    private void fillFields() {
        String name = nameField.getText().toString();
        String phone = phoneField.getText().toString();
        String email = emailField.getText().toString();

        contact.setName(name);
        contact.setPhone(phone);
        contact.setEmail(email);
    }

//    private void configureSaveButton() {
//        Button saveButton = findViewById(R.id.activity_form_contact_save_button);
//
//        saveButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                finalizeForm();
//            }
//        });
//    }

    private void finalizeForm() {
        fillFields();

        if (contact.isValidId()){
            dao.editContact(contact);
            Toast.makeText(NewContactFormActivity.this, "Contact Edited", Toast.LENGTH_SHORT).show();
        } else {
            dao.save(contact);
            Toast.makeText(NewContactFormActivity.this, "Contact Saved", Toast.LENGTH_SHORT).show();
        }

        finish();
    }

    private void loadsContactInfo() {
        Intent contactData = getIntent();

        //Checking if a contact already exists to be edit
        //We need to check if there is an Extra to avoid the risk of the Extra not be sent and receive a null reference
        if(contactData.hasExtra("Contact")) {
            setTitle(APPBAR_TITLE_EDIT_CONTACT);
            //Holds the contact information to be used in the edit form
            //Parameter for the getSerializableExtra() must be the same as in the putExtra() in:
            //ContactListActivity > ConfigureList() > onItemClick()
            //We must cast as a serializable is returned
            contact = (Contact) contactData.getSerializableExtra(CONTACT_KEY);
            assert contact != null;
            nameField.setText(contact.getName());
            phoneField.setText(contact.getPhone());
            emailField.setText(contact.getEmail());
        } else {
            setTitle(APPBAR_TITLE_NEW_CONTACT);
            contact = new Contact();
        }
    }
}
