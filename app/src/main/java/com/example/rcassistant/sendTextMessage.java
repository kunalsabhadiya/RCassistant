package com.example.rcassistant;
import android.telephony.SmsManager;


public class sendTextMessage {
    public static void sendsms(String mobileNumber, String message) {
        try {
            if (!mobileNumber.isEmpty() && mobileNumber.charAt(0) != '+') {
                mobileNumber = "+91" + mobileNumber;
            }

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(mobileNumber, null, message, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



