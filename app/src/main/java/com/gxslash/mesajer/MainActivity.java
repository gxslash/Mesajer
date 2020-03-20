package com.gxslash.mesajer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.my_et);
    }

    public void sendMessage(View view) {

        switch (view.getId()){

            case R.id.my_ib_direct:
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                    if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED){

                        String textMessage = editText.getText().toString();
                        new MyAsyncTask(MainActivity.this, textMessage).execute();

                    }else {
                        requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 0);
                    }

                }else{

                    String textMessage = editText.getText().toString();

                    if (!textMessage.equals("")) {
                        new MyAsyncTask(MainActivity.this, textMessage).execute();
                        editText.setText("");
                    }
                    else Toast.makeText(this, "Boş mesaj gönderilemez", Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.my_ib_select:
                Intent selectContactsIntent = new Intent(MainActivity.this, SelectContactsActivity.class);
                selectContactsIntent.putExtra("message", editText.getText().toString());
                startActivity(selectContactsIntent);
                break;
        }





    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case 0:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    String textMessage = editText.getText().toString();
                    new MyAsyncTask(MainActivity.this, textMessage).execute();

                } else {

                    Toast.makeText(this, "Mesajı gönderebilmemiz için izin vermeniz lazım", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }

    }
}
