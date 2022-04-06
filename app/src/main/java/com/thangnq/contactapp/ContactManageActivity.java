package com.thangnq.contactapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;

public class ContactManageActivity extends AppCompatActivity {

    FloatingActionButton btnAdd;
    public static final String ACTION_INSERT="insert";
    public static final String ACTION_UPDATE="update";
    final int INSERT_CODE=1001;
    final int UPDATE_CODE=1002;
    ArrayList<Contact> lstContact = new ArrayList<>();
    ListView lvContact;
    ContactAdapter contactAdapter;
    DatabaseHelper databaseHelper;

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result != null && result.getResultCode() == 1000) {
                        Intent intent = result.getData();
                        String name = intent.getStringExtra("name");
                        String phone = intent.getStringExtra("phone");

                        databaseHelper = new DatabaseHelper(ContactManageActivity.this);
                        //Thêm đối tượng vào database
                        databaseHelper.addContact(new Contact(name, phone));
                        //Cập nhập dữ liệu trên listView
                        //contactAdapter.notifyDataSetChanged();
                        Toast.makeText(ContactManageActivity.this, "[" + name + " : " + phone + "]",
                                Toast.LENGTH_LONG).show();
                    }
                    else if (result != null && result.getResultCode() == 1001) {
                        Intent intent = result.getData();
                        String name = intent.getStringExtra("name");
                        String phone = intent.getStringExtra("phone");

                        databaseHelper = new DatabaseHelper(ContactManageActivity.this);

                        databaseHelper.updateContact(new Contact(name, phone), intent.getStringExtra("key"));
                        Toast.makeText(ContactManageActivity.this, "[" + name + " : " + phone + "]",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_manage);

        getEmailInfo();

        lvContact = findViewById(R.id.lvContact);
        contactAdapter = new ContactAdapter(ContactManageActivity.this, lstContact);
        lvContact.setAdapter(contactAdapter);

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactManageActivity.this, MainActivity.class);
                intent.putExtra("message", "Mo add new contac activity (MainActivity) de them moi contact");
                activityResultLauncher.launch(intent);
            }
        });
        getEmailInfo();
        registerForContextMenu(lvContact);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.searchView).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                searchContact(text);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchContact(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void searchContact(String keyword) {
        databaseHelper = new DatabaseHelper(ContactManageActivity.this);
        lstContact = databaseHelper.searchContact(keyword);
        contactAdapter = new ContactAdapter(ContactManageActivity.this, lstContact);
        lvContact.setAdapter(contactAdapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if(v.getId()==R.id.lvContact)
            getMenuInflater().inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        //lay ra doi tuong Contact tai vi tri position
        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int position = adapterContextMenuInfo.position;
        Contact contact = contactAdapter.getItem(position);
        switch (item.getItemId()){
            case R.id.mn_update:
                Toast.makeText(ContactManageActivity.this, "update contact : " + contact.getName(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ContactManageActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("contact", contact);
                intent.putExtras(bundle);
                intent.setAction(ACTION_UPDATE);
                activityResultLauncher.launch(intent);
                break;
            case R.id.mn_delete:
                Toast.makeText(ContactManageActivity.this, "delete contact : " + contact.getName(), Toast.LENGTH_LONG).show();
                AlertDialog.Builder deleteDialog = new AlertDialog.Builder(ContactManageActivity.this);
                deleteDialog.setTitle("Xóa Contact ...");
                deleteDialog.setCancelable(true);
                deleteDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(ContactManageActivity.this, "You choose cancel button", Toast.LENGTH_LONG).show();
                        dialogInterface.cancel();
                    }
                });
                deleteDialog.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        databaseHelper = new DatabaseHelper(ContactManageActivity.this);
                        databaseHelper.deleteContact(contact.getName());
                        dialogInterface.dismiss();
                        getListContact();
                    }
                });
                AlertDialog alertDialog = deleteDialog.create();
                alertDialog.show();
                break;
        }
        return true;
    }

    private void getListContact(){
        databaseHelper = new DatabaseHelper(ContactManageActivity.this);
        lstContact = databaseHelper.getAllContact();
        contactAdapter  =new ContactAdapter(ContactManageActivity.this, lstContact);
        //Set adapter cho listView
        lvContact.setAdapter(contactAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Cập nhập dữ liệu trên ListView
        getListContact();
    }

    private void getEmailInfo(){
        String email =new PreferencesManager(ContactManageActivity.this).getEmail();
        Toast.makeText(ContactManageActivity.this, "Email login: " + email,
                Toast.LENGTH_LONG).show();
    }
}