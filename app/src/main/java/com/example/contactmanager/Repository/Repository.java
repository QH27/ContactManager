package com.example.contactmanager.Repository;

import com.example.contactmanager.DAO.ContactDAO;
import com.example.contactmanager.Entity.Contacts;

import java.util.List;

public class Repository {
    private final ContactDAO contactDAO;
    public Repository(ContactDAO contactDAO){
        this.contactDAO = contactDAO;
    }

    //Method in DAO being executed from Repository
    public void addContact(Contacts contacts){
        contactDAO.insert(contacts);
    }
    public void deleteContact(Contacts contacts){
        contactDAO.delete(contacts);
    }
    public List<Contacts> getAllContacts(){
        return contactDAO.getAllContacts();
    }
}
