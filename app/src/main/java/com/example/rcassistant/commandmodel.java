package com.example.rcassistant;

import android.widget.ImageView;

public class commandmodel {
   private  String commandname,btn1,btn2,btn3,btn4;
   private int img;
   private boolean expandable;
   private boolean isvalid;


   public commandmodel(String commandname, String btn1, int img,boolean isvalid) {
      this.commandname = commandname;
      this.btn1 = btn1;
      this.img = img;
      expandable =false;
      this.isvalid=isvalid;
   }
   public commandmodel(String commandname, String btn1, String btn2, int img,boolean isvalid) {
      this.commandname = commandname;
      this.btn1 = btn1;
      this.btn2 = btn2;
      this.img = img;
      expandable =false;
      this.isvalid=isvalid;

   }
   public commandmodel(String commandname, String btn1, String btn2, String btn3, int img,boolean isvalid) {
      this.commandname = commandname;
      this.btn1 = btn1;
      this.btn2 = btn2;
      this.btn3 = btn3;
      this.img = img;
      expandable =false;
      this.isvalid=isvalid;

   }
   public commandmodel(String commandname, String btn1, String btn2, String btn3, String btn4, int img,boolean isvalid) {
      this.commandname = commandname;
      this.btn1 = btn1;
      this.btn2 = btn2;
      this.btn3 = btn3;
      this.btn4 = btn4;
      this.img = img;
      expandable =false;
      this.isvalid=isvalid;

   }

   public boolean isIsvalid() {
      return isvalid;
   }

   public void setIsvalid(boolean isvalid) {
      this.isvalid = isvalid;
   }

   public String getCommandname() {
      return commandname;
   }

   public void setCommandname(String commandname) {
      this.commandname = commandname;
   }

   public String getBtn1() {
      return btn1;
   }

   public void setBtn1(String btn1) {
      this.btn1 = btn1;
   }

   public String getBtn2() {
      return btn2;
   }

   public void setBtn2(String btn2) {
      this.btn2 = btn2;
   }

   public String getBtn3() {
      return btn3;
   }

   public void setBtn3(String btn3) {
      this.btn3 = btn3;
   }

   public String getBtn4() {
      return btn4;
   }

   public void setBtn4(String btn4) {
      this.btn4 = btn4;
   }

   public int getImg() {
      return img;
   }

   public void setImg(int img) {
      this.img = img;
   }

   public boolean isExpandable() {
      return expandable;
   }

   public void setExpandable(boolean expandable) {
      this.expandable = expandable;
   }
}
