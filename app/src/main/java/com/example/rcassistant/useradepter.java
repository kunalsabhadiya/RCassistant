package com.example.rcassistant;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.rcassistant.Database.dbMain;
import com.example.rcassistant.Database.users;
import com.example.rcassistant.Database.usersDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class useradepter extends RecyclerView.Adapter<useradepter.ViewModel> {
    private Context context;
    private List<users> list,filterdlist;
    private Button proceed,delete;
    private List<users> selectedlist=new ArrayList<>();
    private Set<Integer> selectedItems = new HashSet<>();

    public useradepter(Context context, List<users> list,Button proceed,Button delete) {
        this.context = context;
        this.list = list;
        this.filterdlist = list;
        this.proceed =proceed;
        this.delete = delete;
    }

   public void setdeletechanges(){
       proceed.setVisibility(View.GONE);
       delete.setVisibility(View.GONE);
       selectedlist.clear();
       selectedItems.clear();

   }

    @NonNull
    @Override
    public useradepter.ViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.userrow,parent,false);
        return new ViewModel(view);
    }

    public List<users> getSelectedlist() {
        return selectedlist;
    }

    public ArrayList<String> getphonenumberlist(){
        ArrayList<String> phonenumberlist =new ArrayList<>();
        for(int item:selectedItems){
            phonenumberlist.add(list.get(item).getPhonenumber());
        }
        return phonenumberlist;
    }


    @Override
    public void onBindViewHolder(@NonNull useradepter.ViewModel holder, int position) {
         users model=filterdlist.get(position);

        String[] names = model.getUsername().split("\\s+");
        String firstNameInitial = names[0].substring(0, 1).toUpperCase();

        String initials;
        if (names.length == 2) {
            String lastNameInitial = names[1].substring(0, 1).toUpperCase();
            initials = firstNameInitial + lastNameInitial;
        } else {
            initials = model.getUsername().substring(0,2).toUpperCase();
        }

         holder.name.setText(model.getUsername());
         holder.number.setText(model.getPhonenumber());
         if(!model.getRelation().equals("--select--")){
             holder.relation.setText(model.getRelation());
         }else{
             holder.relation.setVisibility(View.GONE);
         }
         holder.imagetv.setText(initials);

        boolean isSelected = selectedItems.contains(position);
            holder.itemView.setSelected(isSelected);



        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                toggleSelection(position);
                return true;
            }
        });

          holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if (!selectedItems.isEmpty()) {
                     toggleSelection(position);
                } else {
                    Intent intent = new Intent(context, commandActivity.class);
                    intent.putExtra("phonenumber", list.get(position).getPhonenumber());
                    context.startActivity(intent);
                }
            }
        });


     }
    public void filter(String query) {
        filterdlist = new ArrayList<>();
        if (query.isEmpty()) {
            filterdlist = list;
        } else {
            for (users item : list) {
                if (item.getUsername().toLowerCase().contains(query.toLowerCase())) {
                    filterdlist.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return filterdlist.size();
    }
    private void toggleSelection(int position) {
        if (selectedItems.contains(position)) {
            selectedItems.remove(position);
            selectedlist.remove(list.get(position));
        } else {
            selectedItems.add(position);
            selectedlist.add(list.get(position));
        }
        if(selectedItems.size()>0){
            String text="Proceed"+" ("+selectedItems.size()+")";
            proceed.setText(text);
        }

        if (selectedItems.isEmpty()) {
            proceed.setVisibility(View.GONE);
            delete.setVisibility(View.GONE);
        } else {
            proceed.setVisibility(View.VISIBLE);
            delete.setVisibility(View.VISIBLE);
        }
        notifyDataSetChanged();
    }

    public class ViewModel extends RecyclerView.ViewHolder {
        TextView name,number;
        TextView imagetv,relation;
        RelativeLayout relativeLayout;
        public ViewModel(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            number=itemView.findViewById(R.id.phonenumber);
            imagetv=itemView.findViewById(R.id.imagetv);
            relation=itemView.findViewById(R.id.relation);
            relativeLayout=itemView.findViewById(R.id.relativelayout);

        }
    }
}
