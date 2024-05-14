package com.example.contactmanager.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.contactmanager.Contacts;
import com.example.contactmanager.Repository.Repository;

import java.util.List;

public class MyViewModel extends AndroidViewModel {
    private Repository myRepository;
    private LiveData<List<Contacts>> allContacts;

    public MyViewModel(@NonNull Application application) {
        super(application);
        this.myRepository = new Repository(application);
    }

    public LiveData<List<Contacts>> getAllContacts() {
        allContacts = myRepository.getAllContacts();
        return allContacts;
    }

    public void addNewContact(Contacts contacts){
        myRepository.addContact(contacts);
    }

    public void deleteContact(Contacts contacts){
        myRepository.deleteContact(contacts);
    }
}
