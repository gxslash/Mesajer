package com.gxslash.mesajer;

import android.content.Context;

import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class ActionMessage {

    private Context context;
    private List<ContactsModel> contactsModels;
    private ContactsModel contactsModel = new ContactsModel();

    public ActionMessage(Context context, List<ContactsModel> contactsModels){
        this.context = context;
        this.contactsModels = contactsModels;
    }

    public void sendMessage(String message){
        int position = 0;

        for (ContactsModel contactsModel: contactsModels) {
            try {
                if (!contactsModel.getPhoneNum().equals("")) {
                    SmsManager manager = SmsManager.getDefault();
                    Log.e("Numbers : ", contactsModel.getPhoneNum() +" ");
                    if (textDesigner(message, position) != null) {
                        manager.sendTextMessage(contactsModel.getPhoneNum(), null, textDesigner(message, position), null, null);
                        Toast.makeText(context, "Toplu mesajınız başarıyla gönderildi", Toast.LENGTH_SHORT).show();
                    }
                    else Toast.makeText(context, "Boş mesaj gönderilemez", Toast.LENGTH_SHORT).show();
                }else Toast.makeText(context, "Seçili bir numara bulunamadı", Toast.LENGTH_SHORT).show();
                position++;
            }catch (Exception e){
                Toast.makeText(context, "Boş mesaj gönderilemez", Toast.LENGTH_SHORT).show();
            }

        }

    }

    public String textDesigner(String oldText, int position){

        contactsModel = contactsModels.get(position);
        String newText = null;

        if(oldText.contains("/name/"))
            newText = oldText.replace("/name/", contactsModel.getName());
        else newText = oldText;

        return newText;
    }

}

