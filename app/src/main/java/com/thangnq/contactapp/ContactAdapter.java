package com.thangnq.contactapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ContactAdapter extends ArrayAdapter<Contact> implements View.OnCreateContextMenuListener {
    public ContactAdapter(Context context, ArrayList<Contact> lstContact){
        super(context,0,lstContact);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentView = convertView;
        if(currentView == null){
            //ghep view tu layout item_contact.xml
            currentView = LayoutInflater.from(getContext()).inflate(R.layout.item_contact,parent,false);
        }
        //lay ra duoc position cua item_contact
        Contact contact = getItem(position);

        TextView textViewName = currentView.findViewById(R.id.tvName);
        TextView textViewPhoneNumber = currentView.findViewById(R.id.tvPhoneNumber);
        ImageButton btnCall = currentView.findViewById(R.id.btnCall);
        ImageButton btnSend = currentView.findViewById(R.id.btnSend);

        textViewName.setText(contact.getName());
        textViewPhoneNumber.setText(contact.getPhoneNumber());

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = "tel:"+textViewPhoneNumber.getText().toString();
                Uri uri = Uri.parse(data);
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(uri);
                getContext().startActivity(intent);
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = "sms://"+textViewPhoneNumber.getText().toString();
                Uri uri = Uri.parse(data);
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(uri);
                getContext().startActivity(intent);
            }
        });
        currentView.setOnCreateContextMenuListener(this::onCreateContextMenu);
        return currentView;
    }


    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

    }
}
