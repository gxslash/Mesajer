package com.gxslash.mesajer;


import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gxslash.mesajer.R;

import java.util.HashMap;
import java.util.Map;

public class SelectContactsActivity extends AppCompatActivity {

    ListView mListView;
    Button btnOK, btnCancel;
    public static boolean isActivityFinished;

    private static Map<String, Boolean> isCheckedController = new HashMap<>();

    public void setIsCheckedController(String key, Boolean isChecked){isCheckedController.put(key, isChecked);}
    public boolean getIsChecked(String key){
        if (isCheckedController.get(key) != null)
            return (boolean)isCheckedController.get(key);

        return false;
    }

    public Map<String, Boolean> getIsCheckedController(){return isCheckedController;}


    private View.OnClickListener okClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Uri contentUri = ContactsContract.Contacts.CONTENT_URI;
            String ID = ContactsContract.Contacts._ID;
            ContentResolver contentResolver = SelectContactsActivity.this.getContentResolver();

            String[] mStringArray = CustomAdapter.str;

            CustomAdapter.str = null;

            StringBuilder sb = new StringBuilder();
            String sqlIn = null;

            if (mStringArray != null) {
                for (String s : mStringArray) {
                    sb.append("?,");
                    Log.e("ArrayElements : ", s);
                }


                if (sb.length() > 0) {
                    sb.deleteCharAt(sb.length() - 1);
                    sqlIn = ID + " IN (" + sb.toString() + ")";
                }

                if (sqlIn != null) {
                    Cursor cursor = contentResolver.query(contentUri, null, sqlIn, mStringArray, null);

                    String mMessage = getIntent().getStringExtra("message");

                    ActionMessage actionMessage = new ActionMessage(SelectContactsActivity.this, new ModelListGenerator().modelGenerator(cursor, contentResolver));
                    if (mMessage != null) {
                        actionMessage.sendMessage(mMessage);
                        finish();
                        Intent homeIntent = new Intent(SelectContactsActivity.this, MainActivity.class);
                        startActivity(homeIntent);
                    }
                    else
                        Toast.makeText(SelectContactsActivity.this, "Boş mesaj yollayamazsınız", Toast.LENGTH_SHORT).show();
                }
            } else
                Toast.makeText(SelectContactsActivity.this, "Kimseyi seçmediniz", Toast.LENGTH_SHORT).show();


        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_select);

        isCheckedController.clear();
        isActivityFinished = false;

        mListView = findViewById(R.id.my_lv);

        new MyAsyncTask(SelectContactsActivity.this, mListView).execute();

        btnCancel = findViewById(R.id.cancel_btn);
        btnOK = findViewById(R.id.ok_btn);

        if (btnCancel.isPressed())
            isActivityFinished = true;

        btnOK.setOnClickListener(okClick);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isActivityFinished = true;
                finish();
                Intent homeIntent = new Intent(SelectContactsActivity.this, MainActivity.class);
                startActivity(homeIntent);
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        isActivityFinished = true;
        finish();

    }
}

