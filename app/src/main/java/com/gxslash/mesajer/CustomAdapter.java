package com.gxslash.mesajer;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends BaseAdapter {

    Context context;
    List<ContactsModel> contactsModelList;

    ContactsModel contactsModel;

    public ArrayList<String> selectedStrings = new ArrayList<String>();

    public static String[] str;

    public CustomAdapter(Context context, List<ContactsModel> contactsModelList){
        this.context = context;
        this.contactsModelList = contactsModelList;
    }

    @Override
    public int getCount() {
        if (contactsModelList == null)
            return 0;
        return contactsModelList.size();
    }

    @Override
    public Object getItem(int position) {
        if (contactsModelList == null)
            return null;
        return contactsModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        if (contactsModelList == null)
            return 0;
        return contactsModelList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LinearLayout container = (LinearLayout)((Activity)context).getLayoutInflater().inflate(R.layout.custom_list_view, null);

        final CheckBox mRadioBtn = container.findViewById(R.id.radio_btn);
        TextView textView = container.findViewById(R.id.tv);

        contactsModel = new ContactsModel();
        contactsModel = contactsModelList.get(position);
        mRadioBtn.setText(contactsModel.getName());
        mRadioBtn.setText(contactsModel.getPhoneNum());
        mRadioBtn.setTag(contactsModel.getId());
        textView.setText(contactsModel.getName());

        final SelectContactsActivity selectContactsActivity = new SelectContactsActivity();

        Log.e("whichHasIsClicked : ", selectContactsActivity.getIsChecked(mRadioBtn.getTag().toString()) + " which of :" + mRadioBtn.getTag().toString());
        Log.e("isFinished : ",SelectContactsActivity.isActivityFinished + " ");

        mRadioBtn.setChecked(false);

        if (!selectContactsActivity.getIsCheckedController().isEmpty()){
            mRadioBtn.setChecked(selectContactsActivity.getIsChecked(mRadioBtn.getTag().toString()));
            Log.e("CheckedController : ", selectContactsActivity.getIsChecked(mRadioBtn.getTag().toString()) + " ");
        }
        Log.e("Name", contactsModel.getName());
        Log.e("Name", contactsModel.getId() + " ");


        mRadioBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    selectedStrings.add(mRadioBtn.getTag().toString());
                    Log.e("Tag : ", mRadioBtn.getTag() + " ");
                    selectContactsActivity.setIsCheckedController(mRadioBtn.getTag().toString(), true);
                }else{
                    selectContactsActivity.setIsCheckedController(mRadioBtn.getTag().toString(), false);
                    selectedStrings.remove(mRadioBtn.getTag().toString());
                }

                str  = new String[selectedStrings.size()];
                str = selectedStrings.toArray(str);

                for (String s : str)
                    Log.e("ArrayElement : ", s);

                System.out.println(selectedStrings);

                System.out.println(str.length);

            }
        });

        if (container.hasFocusable())
            Log.e("Focus : " , " Yes ");

        return container;
    }

}

