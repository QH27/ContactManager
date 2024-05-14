package com.example.contactmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.PrimaryKey;

import android.os.Bundle;
import android.util.Log;

import com.example.contactmanager.Adapter.MyAdapter;
import com.example.contactmanager.Database.ContactDatabase;
import com.example.contactmanager.Contacts;
import com.example.contactmanager.ViewModel.MyViewModel;
import com.example.contactmanager.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //Data Source
    private ContactDatabase contactDatabase;
    private ArrayList<Contacts> contactsArrayList = new ArrayList<>();

    //Adapter
    private MyAdapter myAdapter;

    //Binding
    private ActivityMainBinding mainBinding;
    private MainActivityClickHandler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Databinding
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        handler = new MainActivityClickHandler(this);
        mainBinding.setClickhandler(handler);

        //RecycleView
        RecyclerView recyclerView = mainBinding.recycleView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        //Database
        contactDatabase = ContactDatabase.getInstance(this);

        //ViewModel
        MyViewModel viewModel = new ViewModelProvider(this)
                .get(MyViewModel.class);

        //Inserting a new Contact
//        Contacts c1 = new Contacts("Huy", "qhuy113749885@gmail.com");
//        viewModel.addNewContact(c1);

        //Loading the Data from RoomDB
        viewModel.getAllContacts().observe(this,
                new Observer<List<Contacts>>() {
                    @Override
                    public void onChanged(List<Contacts> contacts) {
                        contactsArrayList.clear();
                        for (Contacts c : contacts){
                            Log.v("TAGY", c.getName());
                            contactsArrayList.add(c);
                        }
                        myAdapter.notifyDataSetChanged();
                    }
                });
        //Adapter
        myAdapter = new MyAdapter(contactsArrayList);

        //Adapter
        recyclerView.setAdapter(myAdapter);
        //swipe to delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //If you swipe the item to the left
                Contacts c = contactsArrayList.get(viewHolder.getAdapterPosition());
                viewModel.deleteContact(c);
            }
        }).attachToRecyclerView(recyclerView);
    }
}