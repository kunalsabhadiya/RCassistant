package com.example.rcassistant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class commaandadepter extends RecyclerView.Adapter<commaandadepter.ViewModel> {
    ArrayList<commandmodel> list;
    Context context;
    String phonenumber;
    Activity activity;
    ArrayList<String> selectedphonelist;
    private OnItemClickListener listener;
    private  boolean isrunningmorethenonetime=false;



    public interface OnItemClickListener {
        void onItemClick(int position);

    }

    public commaandadepter(ArrayList<commandmodel> list, Context context, String phonenumber,OnItemClickListener listner,Activity activity) {
        this.list = list;
        this.context = context;
        this.phonenumber = phonenumber;
        this.listener = listner;
        this.activity = activity;
    }
    public commaandadepter(ArrayList<commandmodel> list, Context context, OnItemClickListener listner,Activity activity,ArrayList<String> selectedlist) {
        this.list = list;
        this.context = context;
        this.listener = listner;
        this.activity = activity;
        this.selectedphonelist = selectedlist;
        isrunningmorethenonetime =true;
    }

    @NonNull
    @Override
    public commaandadepter.ViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.commandrow,parent,false);
        return new ViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull commaandadepter.ViewModel holder, int position) {
        commandmodel commandmodel=list.get(position);
        holder.commandname.setText(commandmodel.getCommandname());
        holder.img.setImageResource(commandmodel.getImg());

        holder.isvalid.setImageResource(commandmodel.isIsvalid()?R.drawable.right:R.drawable.wrong);

        holder.btn1.setText(commandmodel.getBtn1());
        holder.btn1.setVisibility(commandmodel.getBtn1() != null ? View.VISIBLE : View.GONE);

        holder.btn2.setText(commandmodel.getBtn2());
        holder.btn2.setVisibility(commandmodel.getBtn2() != null ? View.VISIBLE : View.GONE);

        holder.btn3.setText(commandmodel.getBtn3());
        holder.btn3.setVisibility(commandmodel.getBtn3() != null ? View.VISIBLE : View.GONE);

        holder.btn4.setText(commandmodel.getBtn4());
        holder.btn4.setVisibility(commandmodel.getBtn4() != null ? View.VISIBLE : View.GONE);


        boolean isExpandable=list.get(position).isExpandable();
        holder.relativeLayout.setVisibility(isExpandable?View.VISIBLE:View.GONE);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewModel extends RecyclerView.ViewHolder {
        Button btn1,btn2,btn3,btn4;
        ImageView img,isvalid;
        TextView commandname;
        RelativeLayout relativeLayout;
        CardView cardView;
        public ViewModel(@NonNull View itemView) {
            super(itemView);
            btn1=itemView.findViewById(R.id.btn1);
            btn2=itemView.findViewById(R.id.btn2);
            btn3=itemView.findViewById(R.id.btn3);
            btn4=itemView.findViewById(R.id.btn4);
            img=itemView.findViewById(R.id.img);
            isvalid=itemView.findViewById(R.id.validornot);
            commandname=itemView.findViewById(R.id.commandname);
            relativeLayout=itemView.findViewById(R.id.relativelayout);
            cardView=itemView.findViewById(R.id.cardview);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    commandmodel model=list.get(getAdapterPosition());
                    model.setExpandable(!model.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });

            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String btn1text=btn1.getText().toString();
                    if(isrunningmorethenonetime){
                        for(String phone:selectedphonelist) {
                            switch (btn1text) {
                                case "Enable Wifi":
                                    sendTextMessage.sendsms(phone, "rc enable wifi");
                                    break;
                                case "Enable Blutooth":
                                    sendTextMessage.sendsms(phone, "rc enable bluetooth");
                                    break;
                                case "Get Location":
                                    sendTextMessage.sendsms(phone, "rc get location");
                                    break;
                                case "Play Sound":
                                    sendTextMessage.sendsms(phone, "rc play sound");
                                    break;
                                case "Front Camera":
                                    sendTextMessage.sendsms(phone, "rc take picture with front cam");
                                    break;
                                case "Enable Wifi-Hotspot":
                                    sendTextMessage.sendsms(phone, "rc enable hotspot");
                                    break;
                                case "Get Battery Level":
                                    sendTextMessage.sendsms(phone, "rc get battery");
                                    break;
                                case "Get Brightness":
                                    sendTextMessage.sendsms(phone, "rc get brightness");
                                    break;
                                default:
                                    Toast.makeText(context, "message was not sent", Toast.LENGTH_SHORT).show();
                                    break;

                            }
                        }
                        showToast();
                    }else{
                        switch (btn1text) {
                            case "Enable Wifi":
                                sendTextMessage.sendsms(phonenumber, "rc enable wifi");
                                showToast();
                                break;
                            case "Enable Blutooth":
                                sendTextMessage.sendsms(phonenumber, "rc enable bluetooth");
                                showToast();
                                break;
                            case "Get Location":
                                sendTextMessage.sendsms(phonenumber, "rc get location");
                                showToast();
                                break;
                            case "Play Sound":
                                sendTextMessage.sendsms(phonenumber, "rc play sound");
                                showToast();
                                break;
                            case "Grant Module":
                                if (listener != null) {
                                    listener.onItemClick(0);
                                }
                                break;
                            case "Front Camera":
                                sendTextMessage.sendsms(phonenumber, "rc take picture with front cam");
                                showToast();
                                break;
                            case "Enable Wifi-Hotspot":
                                sendTextMessage.sendsms(phonenumber, "rc enable hotspot");
                                showToast();
                                break;
                            case "Get Battery Level":
                                sendTextMessage.sendsms(phonenumber, "rc get battery");
                                showToast();
                                break;
                            case "Get Brightness":
                                sendTextMessage.sendsms(phonenumber, "rc get brightness");
                                showToast();
                                break;
                            default:
                                Toast.makeText(context, "message was not sent", Toast.LENGTH_SHORT).show();
                                break;

                        }
                    }
                }
            });

            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String btn1text=btn2.getText().toString();
                    if(isrunningmorethenonetime){
                        if(btn1text.equals("Play Sound For Duration")){
                            if(listener!=null){
                                listener.onItemClick(1);
                            }
                        }else {
                            for (String phone : selectedphonelist) {
                                switch (btn1text) {
                                    case "Disable Wifi":
                                        sendTextMessage.sendsms(phone, "rc disable wifi");
                                        break;
                                    case "Disable Blutooth":
                                        sendTextMessage.sendsms(phone, "rc disable bluetooth");
                                        break;
                                    case "Back Camera":
                                        sendTextMessage.sendsms(phone, "rc take picture with back cam");
                                        break;
                                    case "Disable Wifi-Hotspot":
                                        sendTextMessage.sendsms(phone, "rc disable hotspot");
                                        break;
                                    case "Is battery Charging":
                                        sendTextMessage.sendsms(phone, "rc is battery charging");
                                        break;
                                    case "Set Brightness":
                                        sendTextMessage.sendsms(phone, "rc set brightness");
                                        break;
                                    default:
                                        Toast.makeText(context, "message was not sent", Toast.LENGTH_SHORT).show();
                                        break;

                                }
                            }
                        }
                        showToast();
                    }else{
                        switch (btn1text){
                            case "Disable Wifi":
                                sendTextMessage.sendsms(phonenumber,"rc disable wifi");
                                showToast();
                                break;
                            case "Disable Blutooth":
                                sendTextMessage.sendsms(phonenumber,"rc disable bluetooth");
                                showToast();
                                break;
                            case "Play Sound For Duration":
                                if(listener!=null){
                                    listener.onItemClick(1);
                                }
                                break;
                            case "Grant All Modules":
                                if (listener != null) {
                                    listener.onItemClick(2);
                                }
                                break;
                            case "Back Camera":
                                sendTextMessage.sendsms(phonenumber,"rc take picture with back cam");
                                showToast();
                                break;
                            case "Disable Wifi-Hotspot":
                                sendTextMessage.sendsms(phonenumber,"rc disable hotspot");
                                showToast();
                                break;
                            case "Is battery Charging":
                                sendTextMessage.sendsms(phonenumber,"rc is battery charging");
                                showToast();
                                break;
                            case "Set Brightness":
                                sendTextMessage.sendsms(phonenumber,"rc set brightness");
                                showToast();
                                break;
                            default:
                                Toast.makeText(context, "message was not sent", Toast.LENGTH_SHORT).show();
                                break;

                        }
                    }


                }
            });

            btn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String btn1text=btn3.getText().toString();
                    if(isrunningmorethenonetime){
                        for(String phone:selectedphonelist) {
                            switch (btn1text){
                                case "Is Wifi Enable":
                                    sendTextMessage.sendsms(phone,"rc is enabled wifi");
                                    break;
                                case "Is Enable Blutooth":
                                    sendTextMessage.sendsms(phone,"rc is enablee bluetooth");
                                    break;
                                case "Front With FlashLight":
                                    sendTextMessage.sendsms(phone,"rc take picture with front cam, flash on");
                                    break;
                                case "Is Hotspot Enable":
                                    sendTextMessage.sendsms(phone,"rc is enabled hotspot");
                                    break;
                                case "Turn Display Off":
                                    sendTextMessage.sendsms(phone,"rc turn display off");
                                    break;
                                default:
                                    Toast.makeText(context, "message was not sent", Toast.LENGTH_SHORT).show();
                                    break;

                            }
                        }
                        showToast();
                    }else{
                        switch (btn1text){
                            case "Is Wifi Enable":
                                sendTextMessage.sendsms(phonenumber,"rc is enabled wifi");
                                showToast();
                                break;
                            case "Is Enable Blutooth":
                                sendTextMessage.sendsms(phonenumber,"rc is enablee bluetooth");
                                showToast();
                                break;
                            case "Front With FlashLight":
                                sendTextMessage.sendsms(phonenumber,"rc take picture with front cam, flash on");
                                showToast();
                                break;
                            case "Is Hotspot Enable":
                                sendTextMessage.sendsms(phonenumber,"rc is enabled hotspot");
                                showToast();
                                break;
                            case "Turn Display Off":
                                sendTextMessage.sendsms(phonenumber,"rc turn display off");
                                showToast();
                                break;
                            default:
                                Toast.makeText(context, "message was not sent", Toast.LENGTH_SHORT).show();
                                break;

                        }
                    }

                }
            });

            btn4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String btn1text=btn4.getText().toString();
                    if(isrunningmorethenonetime){
                        if(btn1text.equals("Set Display off Timeout")){
                            if(listener!=null){
                                listener.onItemClick(3);
                            }
                        }else {
                            for(String phone:selectedphonelist) {
                                if (btn1text.equals("Back With FlashLight")) {
                                    sendTextMessage.sendsms(phone, "rc take picture with back cam, flash on");
                                } else {
                                    Toast.makeText(context, "message was not sent", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        showToast();
                    }else{
                        switch (btn1text){
                            case "Back With FlashLight":
                                sendTextMessage.sendsms(phonenumber,"rc take picture with back cam, flash on");
                                showToast();
                                break;
                            case "Set Display off Timeout":
                                if(listener!=null){
                                    listener.onItemClick(3);
                                }
                                break;
                            default:
                                Toast.makeText(context, "message was not sent", Toast.LENGTH_SHORT).show();
                                break;

                        }
                    }


                }
            });


        }
        public void showToast(){
            Toast.makeText(context, "Message sent successfully.", Toast.LENGTH_SHORT).show();
            activity.finish();
        }
    }
}
