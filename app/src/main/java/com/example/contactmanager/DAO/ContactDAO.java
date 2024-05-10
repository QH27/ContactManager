package com.example.contactmanager.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.contactmanager.Entity.Contacts;

import java.util.List;

@Dao
public interface ContactDAO {
    @Insert
    void insert(Contacts contacts);
    @Delete
    void delete(Contacts contacts);
    @Query("select * from contacts_table")
    List<Contacts> getAllContacts();
}