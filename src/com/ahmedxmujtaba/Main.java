package com.ahmedxmujtaba;

import com.ahmedxmujtaba.UI.StartingUI;
import com.ahmedxmujtaba.Security.Log;

import java.util.ArrayList;


public class Main {
    public static void main(String[] args)
    {
        makeLog();
        startUI();
    }

    private static void startUI(){
        new StartingUI().setVisible(true);
    }
    private static void makeLog(){ Log log = new Log(); }
}


/*
 * How does the program work?
 */


