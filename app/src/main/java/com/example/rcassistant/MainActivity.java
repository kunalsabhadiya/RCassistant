package com.example.rcassistant;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcassistant.Database.dbMain;
import com.example.rcassistant.Database.users;
import com.example.rcassistant.Database.usersDao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
RecyclerView rvuser;
List<users> list;
SearchView search;
ImageView logo,adduserimg;
LinearLayout add;
useradepter adepter;
TextView nofoundtv;
Button proceed,delete;

private ActivityResultLauncher<String[]> permissionLauncher;
private boolean isReadPermissionGranted = false;
private boolean isphonePermissionGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        permissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(),
                permissions -> {
                    isReadPermissionGranted = permissions.get(Manifest.permission.READ_EXTERNAL_STORAGE) != null ? Boolean.TRUE.equals(permissions.get(Manifest.permission.READ_EXTERNAL_STORAGE)) : isReadPermissionGranted;
                    isphonePermissionGranted = permissions.get(Manifest.permission.READ_CONTACTS) != null ? Boolean.TRUE.equals(permissions.get(Manifest.permission.READ_CONTACTS)) : isphonePermissionGranted;

                     }
        );
      requestPermission();
      rvuser=findViewById(R.id.rvuser);
      search=findViewById(R.id.search);
      add=findViewById(R.id.filter);
      logo=findViewById(R.id.logo);
      adduserimg=findViewById(R.id.adduserimg);
      nofoundtv=findViewById(R.id.notfoundtv);
      proceed=findViewById(R.id.next);
      delete=findViewById(R.id.delete);



        EditText searchEditText = search.findViewById(androidx.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.black));
        search.clearFocus();

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adepter.filter(newText);
                return false;
            }

        });

      list =new ArrayList<>();



        Drawable drawable = adduserimg.getDrawable();
        drawable.setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_IN);
        adduserimg.setImageDrawable(drawable);

        Drawable drawable2 = logo.getDrawable();
        drawable2.setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_IN);
        logo.setImageDrawable(drawable2);

        add.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              startActivity(new Intent(getApplicationContext(),adduser.class));
          }
      });

        dbMain db= Room.databaseBuilder(getApplicationContext(),dbMain.class,"users").allowMainThreadQueries().build();
        usersDao dao=db.userDao();
        list = dao.getallUsers();
        if(list.size()==0){
            nofoundtv.setVisibility(View.VISIBLE);
        }

        adepter=new useradepter(this,list,proceed,delete);
        adepter.notifyDataSetChanged();
        rvuser.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvuser.setAdapter(adepter);

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent=new Intent(MainActivity.this,commandActivity.class);
                intent.putStringArrayListExtra("selecteditemlist",adepter.getphonenumberlist());
                adepter.setdeletechanges();
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<users> proceedlist= adepter.getSelectedlist();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Delete ?");
                        builder.setMessage("Are you sure you want to delete user?");
                        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                            for(users user:proceedlist){
                                dao.deleteuser(user);
                                list.remove(user);
                                adepter.notifyDataSetChanged();
                            }
                            adepter.setdeletechanges();
                        });
                        builder.setNegativeButton("No", (dialogInterface, i) -> {

                        });
                        builder.create().show();

                    }
                });


            }
        });
    }


    private void requestPermission(){

        isReadPermissionGranted = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.SEND_SMS
        ) == PackageManager.PERMISSION_GRANTED;

        isphonePermissionGranted = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED;

        List<String> permissionRequest = new ArrayList<>();

        if (!isReadPermissionGranted){
            permissionRequest.add(Manifest.permission.SEND_SMS);
        }

        if (!isphonePermissionGranted){
            permissionRequest.add(Manifest.permission.READ_CONTACTS);
        }

        if (!permissionRequest.isEmpty()){
            permissionLauncher.launch(permissionRequest.toArray(new String[0]));
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adepter.setdeletechanges();
        for (int i = 0; i < rvuser.getChildCount(); i++) {
            View itemView = rvuser.getChildAt(i);
            itemView.setSelected(false);
        }
    }
}