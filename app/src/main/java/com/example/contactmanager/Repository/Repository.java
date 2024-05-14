package com.example.contactmanager.Repository;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import com.example.contactmanager.DAO.ContactDAO;
import com.example.contactmanager.Database.ContactDatabase;
import com.example.contactmanager.Contacts;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private final ContactDAO contactDAO;
    ExecutorService executor;
    Handler handler;
    public Repository(Application application){

        ContactDatabase contactDatabase = ContactDatabase.getInstance(application);
        this.contactDAO = contactDatabase.getContactDAO();
        //Used for background Database Operations
        executor = Executors.newSingleThreadExecutor();

        //Used for updating the UI
        handler = new Handler(Looper.getMainLooper());
    }

    //Method in DAO being executed from Repository
    public void addContact(Contacts contacts){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                //Execute this code asynchronously
                //on separate thread
                contactDAO.insert(contacts);
            }
        });
    }
    public void deleteContact(Contacts contacts){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                contactDAO.delete(contacts);
            }
        });

    }
    public LiveData <List<Contacts>> getAllContacts(){
        return contactDAO.getAllContacts();
    }
}
