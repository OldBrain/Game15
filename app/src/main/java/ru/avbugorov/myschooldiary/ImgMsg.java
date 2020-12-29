package ru.avbugorov.myschooldiary;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ImgMsg {
    Context context;
    String ms;

    public ImgMsg(Context context, String ms) {
        this.context = context;
        this.ms = ms;
        Toast toast = Toast.makeText(context, ms, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        LinearLayout toastContainer = (LinearLayout) toast.getView();
        ImageView myImageView = new ImageView(context);
        myImageView.setImageResource(R.drawable.puzzle15);
        toastContainer.addView(myImageView,0);
        toast.show();
    }






}
