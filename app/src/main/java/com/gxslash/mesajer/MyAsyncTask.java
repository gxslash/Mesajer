package com.gxslash.mesajer;


import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ListView;

import java.util.List;

public class MyAsyncTask extends AsyncTask<Void, String, List<ContactsModel>> {

    Context context;
    ProgressDialog progressDialog;
    String mMessage;
    ListView listView;

    public MyAsyncTask(Context context, String mMessage){
        this.context = context;
        this.mMessage = mMessage;
    }

    public MyAsyncTask(Context context, ListView listView){
        this.context = context;
        this.listView = listView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(context, "İşleminiz yürütülüyor", "Lüften bekleyiniz");
    }

    @Override
    protected List<ContactsModel> doInBackground(Void... voids) {

        Uri contentUri = ContactsContract.Contacts.CONTENT_URI;
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(contentUri, null, null, null, null);

        return new ModelListGenerator().modelGenerator(cursor, contentResolver);
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        progressDialog.setMessage(values[0]);
    }

    @Override
    protected void onPostExecute(List<ContactsModel> contactsModels) {
        super.onPostExecute(contactsModels);

        if (context.getClass().getName().equals("com.gxslash.mesajer.SelectContactsActivity")) {

            CustomAdapter customAdapter = new CustomAdapter(context, contactsModels);
            listView.setAdapter(customAdapter);
        }
        if (context.getClass().getName().equals("com.gxslash.mesajer.MainActivity")){
            ActionMessage actionMessage = new ActionMessage(context, contactsModels);
            actionMessage.sendMessage(mMessage);
        }

        progressDialog.cancel();
    }
}
