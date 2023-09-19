package com.example.rcassistant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rcassistant.Database.dbMain;
import com.example.rcassistant.Database.users;
import com.example.rcassistant.Database.usersDao;

public class adduser extends AppCompatActivity {
EditText number,name,password;
Spinner relation;
ImageView back;
Button save,addcontact;
String relationtxt,finalnumber;
static final int PICK_CONTACT = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adduser);
        number=findViewById(R.id.phonenumber);
        name=findViewById(R.id.name);
        relation=findViewById(R.id.relation);
        password=findViewById(R.id.password);
        back=findViewById(R.id.back);
        save=findViewById(R.id.save);
        addcontact=findViewById(R.id.addcontact);


        String[] list = {
                "--select--",
                "Son",
                "Daughter",
                "Friend",
                "Sister",
                "Brother",
                "Wife",
                "Husband",
                "Father",
                "Mother",
                "Aunt",
                "Uncle",
                "Grandmother",
                "Grandfather",
        };

        relation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                 relationtxt = parentView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list);
        relation.setAdapter(adapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        addcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT);

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(number.getText().toString().isEmpty() ){
                    number.setError("please enter valid mobilenumber");
                    number.requestFocus();
                } else if (number.getText().toString().replaceAll("\\D","").length()!=10 && number.getText().toString().replaceAll("\\D","").length() !=12) {
                    number.setError("please enter valid mobilenumber");
                    number.requestFocus();
                } else if(name.getText().toString().isEmpty()){
                    name.setError("please enter full name");
                }else {
                    finalnumber=number.getText().toString();
                    String cleanedNumber = finalnumber.replaceAll("[^0-9]", "");

                    if (cleanedNumber.length() == 10) {
                        finalnumber = "+91" + cleanedNumber;
                    } else if (cleanedNumber.length() == 12 && cleanedNumber.startsWith("91")) {
                        finalnumber = "+" + cleanedNumber;
                    }
                    dbMain db= Room.databaseBuilder(getApplicationContext(),dbMain.class,"users").allowMainThreadQueries().build();
                    usersDao dao=db.userDao();
                    boolean check=dao.userexist(finalnumber);
                    if(!check){
                        dao.insertuser(new users(0,name.getText().toString(),relationtxt,finalnumber,password.getText().toString()));
                        name.setText("");
                        number.setText("");
                        password.setText("");
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), "User already exist", Toast.LENGTH_SHORT).show();
                        number.requestFocus();
                    }
                }


            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void onActivityResult(int reqCode, int resultCode, Intent data)
    {
        super.onActivityResult(reqCode, resultCode, data);

        switch(reqCode) {
            case (PICK_CONTACT):
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c = managedQuery(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
                        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                        String hasPhone = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                                    null, null);
                            phones.moveToFirst();
                            String phn_no = phones.getString(phones.getColumnIndexOrThrow("data1"));
                            String nametxt = c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.StructuredPostal.DISPLAY_NAME));
                            number.setText(phn_no);
                            name.setText(nametxt);

                        }
                    }
                }
        }
    }




}