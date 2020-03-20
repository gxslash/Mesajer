package com.gxslash.mesajer;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ModelListGenerator {

    public List<ContactsModel> modelGenerator(Cursor cursor, ContentResolver contentResolver){

        List<ContactsModel> contactsModelList = new ArrayList<>();

        Uri phoneNumUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        String ID = ContactsContract.Contacts._ID;
        String NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String NUMBER_OF_PHONES = ContactsContract.Contacts.HAS_PHONE_NUMBER;

        String PHONE_NUM = ContactsContract.CommonDataKinds.Phone.NUMBER;
        String PHONE_NUM_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;


        if(cursor.moveToFirst()){

            do {
                ContactsModel contactsModel = new ContactsModel();

                contactsModel.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                contactsModel.setName(cursor.getString(cursor.getColumnIndex(NAME)));

                String registerID = String.valueOf(cursor.getInt(cursor.getColumnIndex(ID)));

                StringBuilder phoneNumbers = new StringBuilder();

                int hasPhoneNum = Integer.parseInt(cursor.getString(cursor.getColumnIndex(NUMBER_OF_PHONES)));

                if(hasPhoneNum > 0){
                    Cursor c = contentResolver.query(phoneNumUri, null,
                            PHONE_NUM_ID + " = ?", new String[] {registerID}, null);
                    if(c.moveToFirst()){
                        String phoneNum = c.getString(c.getColumnIndex(PHONE_NUM));
                        phoneNumbers.append(phoneNum);
                    }
                }

                contactsModel.setPhoneNum(String.valueOf(phoneNumbers));
                int iterator = 0;
                boolean hasSameNumber = false;
                while(iterator < contactsModelList.size()) {
                    if (contactsModelList.get(iterator).getPhoneNum().equals(contactsModel.getPhoneNum())){
                        hasSameNumber = true;
                        break;
                    }
                    ++iterator;
                }

                if (!hasSameNumber)
                    contactsModelList.add(contactsModel);

            }while (cursor.moveToNext());
        }else{
            contactsModelList = null;
        }

        return contactsModelList;

    }
}
