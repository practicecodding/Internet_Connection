package com.hamidul.internetconnection;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

public class ConnectionReceiver extends BroadcastReceiver {
    Context mContext,nContext;
    @Override
    public void onReceive(Context context, Intent intent) {

        if (isConnected(context)){
            Toast.makeText(context, "Internet Connected", Toast.LENGTH_SHORT).show();
        }else {
            mContext=context;
            showDialog();
        }

    }

    public boolean isConnected(Context context){

        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return (networkInfo!=null && networkInfo.isConnected());
        }catch (NullPointerException e){
            e.printStackTrace();
            return false;
        }


    }

    public void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.alertdialog_layout,null);
        builder.setView(view);
        Button buttonOk = view.findViewById(R.id.buttonOk);

        final AlertDialog alertDialog = builder.create();

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

    }

}
