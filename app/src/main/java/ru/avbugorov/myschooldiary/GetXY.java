package ru.avbugorov.myschooldiary;

import java.util.Stack;

public class GetXY {
    int num;
    int i;
    int j;


    public GetXY(int i, int j) {
        this.i = i;
        this.j = j;
        this.num =  this.i * MainActivity.ROWS+ this.j;
//        System.out.println("Num>"+this.num);
    }


}
