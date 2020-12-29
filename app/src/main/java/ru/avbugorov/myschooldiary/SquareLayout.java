package ru.avbugorov.myschooldiary;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class SquareLayout extends LinearLayout {

    public SquareLayout(Context context) {
        super(context);
    }
    public SquareLayout(Context context, AttributeSet attrs){
        super(context, attrs);

           }
           @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
               if (widthMeasureSpec < heightMeasureSpec) {
                   super.onMeasure(widthMeasureSpec, widthMeasureSpec);
               } else {
                   super.onMeasure(heightMeasureSpec, heightMeasureSpec);

               }

           }


}
