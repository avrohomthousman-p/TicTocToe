package com.example.tic_toc_toe_app.Models;
import androidx.annotation.NonNull;

public enum Player {
    NONE, X, O;


    @NonNull
    @Override
    public String toString(){
        if(this == NONE){
            return "";
        }
        else{
            return super.toString();
        }
    }
}
