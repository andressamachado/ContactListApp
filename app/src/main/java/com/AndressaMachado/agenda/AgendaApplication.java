package com.AndressaMachado.agenda;

import android.app.Application;
import com.AndressaMachado.agenda.dao.ContactDAO;
import com.AndressaMachado.agenda.model.Contact;

@SuppressWarnings("WeakerAccess")
public class AgendaApplication extends Application {

    @Override
    public void onCreate() {
       super.onCreate();
        instancesForTests();
    }

    private void instancesForTests() {
        ContactDAO dao = new ContactDAO();

        dao.save(new Contact("Andressa", "012345678", "andressa@gmail.com"));
        dao.save(new Contact("Omar", "012345678", "omar@gmail.com"));
        dao.save(new Contact("Olivia", "012345678", "olivia@littlecat.com"));
    }
}
