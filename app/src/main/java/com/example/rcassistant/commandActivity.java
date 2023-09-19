package com.example.rcassistant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.rcassistant.Database.dbMain;
import com.example.rcassistant.Database.users;
import com.example.rcassistant.Database.usersDao;

import java.util.ArrayList;

public class commandActivity extends AppCompatActivity implements commaandadepter.OnItemClickListener {
RecyclerView commandrv;
commaandadepter adepter;
ImageView back;
ArrayList<commandmodel> list;
ArrayList<String> selecteditemlist;
View overlayView;
String phonenumber;
dbMain db;
usersDao dao;
String password1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_command);
        commandrv =findViewById(R.id.commandrv);
        back=findViewById(R.id.back);

        overlayView = LayoutInflater.from(this).inflate(R.layout.backgroundoverlay, null);
        addContentView(overlayView, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        overlayView.setVisibility(View.GONE);

        phonenumber=getIntent().getStringExtra("phonenumber");
        selecteditemlist=getIntent().getStringArrayListExtra("selecteditemlist");

       db= Room.databaseBuilder(getApplicationContext(), dbMain.class,"users").allowMainThreadQueries().build();
       dao=db.userDao();
       password1=dao.getuserpassword(phonenumber);


        list=new ArrayList<>();
        addDataInList();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if(selecteditemlist !=null){
            list.remove(4);
            adepter=new commaandadepter(list,getApplicationContext(),this,this,selecteditemlist);
        }else{
            adepter=new commaandadepter(list,getApplicationContext(),phonenumber,this,this);
        }
        commandrv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        commandrv.setAdapter(adepter);
    }



    @Override
    public void onItemClick(int position) {
        if (position == 0) {
            password();
        }
        if (position == 1) {
            soundduration();
        }
        if (position == 2) {
            grantallmodule();
        }
        if(position == 3){
            displayofftimeout();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    public void password(){
        View popupView = getLayoutInflater().inflate(R.layout.popup_password, null);
        PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                overlayView.setVisibility(View.GONE);
            }
        });

        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setElevation(3);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        EditText editTextpassword = popupView.findViewById(R.id.ppnewname);
        EditText modulename = popupView.findViewById(R.id.command);
        Button buttonSave = popupView.findViewById(R.id.ppsave);
        Button buttonCancel = popupView.findViewById(R.id.ppcancle);
        overlayView.setVisibility(View.VISIBLE);
        overlayView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overlayView.setVisibility(View.GONE);
            }
        });

        dbMain db= Room.databaseBuilder(getApplicationContext(), dbMain.class,"users").allowMainThreadQueries().build();
        usersDao dao=db.userDao();
        String password1=dao.getuserpassword(phonenumber);
        editTextpassword.setText(password1);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = editTextpassword.getText().toString();
                String module = modulename.getText().toString();
                sendTextMessage.sendsms(phonenumber, "rc grant " + password + " " + module);
                popupWindow.dismiss();
                overlayView.setVisibility(View.GONE);
                Toast.makeText(commandActivity.this, "Message sent successfully.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                overlayView.setVisibility(View.GONE);
            }
        });
    }

    public void grantallmodule(){
        View popupView = getLayoutInflater().inflate(R.layout.popup_grantallmudule, null);
        PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                overlayView.setVisibility(View.GONE);
            }
        });

        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setElevation(3);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        EditText modulename =popupView.findViewById(R.id.command);
        Button buttonSave = popupView.findViewById(R.id.ppsave);
        Button buttonCancel = popupView.findViewById(R.id.ppcancle);
        overlayView.setVisibility(View.VISIBLE);
        overlayView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overlayView.setVisibility(View.GONE);
            }
        });
        modulename.setText(password1);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String module = modulename.getText().toString();
                sendTextMessage.sendsms(phonenumber,"rc grant "+module+" all");
                popupWindow.dismiss();
                overlayView.setVisibility(View.GONE);
                Toast.makeText(commandActivity.this, "Message sent successfully.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                overlayView.setVisibility(View.GONE);
            }
        });
    }

    public void soundduration(){
        View popupView = getLayoutInflater().inflate(R.layout.popup_soundduration, null);
        PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                overlayView.setVisibility(View.GONE);
            }
        });

        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setElevation(3);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        EditText modulename =popupView.findViewById(R.id.command);
        Button buttonSave = popupView.findViewById(R.id.ppsave);
        Button buttonCancel = popupView.findViewById(R.id.ppcancle);
        overlayView.setVisibility(View.VISIBLE);
        overlayView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overlayView.setVisibility(View.GONE);
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String module = modulename.getText().toString();
                sendTextMessage.sendsms(phonenumber,"rc play sound for "+module);
                popupWindow.dismiss();
                overlayView.setVisibility(View.GONE);
                Toast.makeText(commandActivity.this, "Message sent successfully.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                overlayView.setVisibility(View.GONE);
            }
        });
    }


    public void displayofftimeout(){

        View popupView = getLayoutInflater().inflate(R.layout.popup_displayofftimeout, null);
        PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                overlayView.setVisibility(View.GONE);
            }
        });

        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setElevation(3);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        EditText modulename =popupView.findViewById(R.id.command);
        Button buttonSave = popupView.findViewById(R.id.ppsave);
        Button buttonCancel = popupView.findViewById(R.id.ppcancle);
        overlayView.setVisibility(View.VISIBLE);
        overlayView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overlayView.setVisibility(View.GONE);
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String module = modulename.getText().toString();
                sendTextMessage.sendsms(phonenumber,"rc set display off timeout to "+module);
                popupWindow.dismiss();
                overlayView.setVisibility(View.GONE);
                Toast.makeText(commandActivity.this, "Message sent successfully.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                overlayView.setVisibility(View.GONE);
            }
        });
    }
    private void addDataInList() {
        list.add(new commandmodel("Wi-Fi","Enable Wifi","Disable Wifi","Is Wifi Enable",R.drawable.wifi,true));
        list.add(new commandmodel("Blutooth","Enable Blutooth","Disable Blutooth","Is Enable Blutooth",R.drawable.bluetooth,true));
        list.add(new commandmodel("Location","Get Location",R.drawable.location,true));
        list.add(new commandmodel("Find Phone","Play Sound","Play Sound For Duration",R.drawable.audio,true));
        list.add(new commandmodel("Grant Phone Remotly","Grant Module","Grant All Modules",R.drawable.verified,true));
        list.add(new commandmodel("Camera","Front Camera","Back Camera","Front with Flashlight","Back With Flashlight",R.drawable.camera,true));
        list.add(new commandmodel("Wifi Hotspot","Enable Wifi-Hotspot","Disable Wifi-Hotspot","Is Hotspot Enable",R.drawable.hotspot,true));
        list.add(new commandmodel("Battery","Get Battery Level","Is Battery Charging",R.drawable.battery,true));
        list.add(new commandmodel("Display","Get Brightness","Set Brightness","Turn Display Off","Set Display off Timeout",R.drawable.display,true));
        list.add(new commandmodel("Mobile Internet","Is Enable Internet","Enable Internet","Disable Internet",R.drawable.mobiledata,false));
    }
}