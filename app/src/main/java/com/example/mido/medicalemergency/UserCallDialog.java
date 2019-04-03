package com.example.mido.medicalemergency;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.mido.medicalemergency.Models.User;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserCallDialog {




    public static void callDialog(final Context mContext, User user) {
        DialogPlus dialog = DialogPlus.newDialog(mContext)
                .setContentHolder(new ViewHolder(R.layout.call_dialog))
                .setGravity(Gravity.CENTER)
                .create();
        final String phone1 = user.getPhone1();
        final String phone2 = user.getPhone2();
        TextView phone1TextView=(TextView) dialog.findViewById(R.id.phone_number_1);
        TextView phone2TextView=(TextView) dialog.findViewById(R.id.phone_number_2);
        if(phone1!=null){
            phone1TextView.setText(phone1);
            phone1TextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callIntent(mContext, phone1);
                }
            });
        }else {
            phone1TextView.setVisibility(View.GONE);
        }
        if(phone2!=null){
            phone2TextView.setText(phone2);
            phone2TextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callIntent(mContext, phone2);
                }
            });
        }else {
            phone2TextView.setVisibility(View.GONE);
        }
        dialog.show();

    }


    private static void callIntent(Context context, String phoneNumber){
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        ((Activity)context).startActivity(intent);
    }


}
