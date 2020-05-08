package com.AndressaMachado.agenda.dao;

import com.AndressaMachado.agenda.model.Contact;
import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ContactDAO {
    private final static List<Contact> contacts = new ArrayList<>();
    private static int idCounter = 1;

    public void save(Contact contact) {
        contact.setId(idCounter);
        contacts.add(contact);
        idCounter++;
    }

    public void editContact(Contact contact) {
        Contact contactToBeFind = findContactById(contact);

        if (contactToBeFind != null) {
            int contactPosition = contacts.indexOf(contactToBeFind);
            contacts.set(contactPosition, contact);
        }
    }

    @Nullable
    private Contact findContactById(Contact contact) {
        for (Contact currentContact : contacts) {
            if (currentContact.getId() == contact.getId()) {
                return currentContact;
            }
        }
        return null;
    }

    public List<Contact> all() {
        return new ArrayList<>(contacts);
    }

    public void remove(Contact contact) {
        Contact contactToBeDeleted = findContactById(contact);

        if(contactToBeDeleted != null) {
            contacts.remove(contactToBeDeleted);
        }
    }
}
