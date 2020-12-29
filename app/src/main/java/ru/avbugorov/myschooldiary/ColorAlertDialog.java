package ru.avbugorov.myschooldiary;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

public class ColorAlertDialog extends  DialogFragment{
    Context context;
    AlertDialog.Builder alDialog;
    int nNum;
    private final int IDD_THREE_BUTTONS = 0;




    @SuppressLint("ResourceType")
    public ColorAlertDialog(Context context) {

        this.context = context;

        alDialog = new AlertDialog.Builder(context, android.R.style.Theme_DeviceDefault_Dialog_NoActionBar);


     alDialog.setTitle("Выбор цвета");
//        alDialog.setMessage("Сообщение");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alDialog.setView(R.layout.dialog);

              }
//        setStyle(STYLE_NO_TITLE, 0);
        alDialog.show();
    }





}


