package com.ahmedxmujtaba;

import com.ahmedxmujtaba.UI.StartingUI;
import com.ahmedxmujtaba.Security.Log;



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

//todo
// make sure the course and lectures , names do not take blank and in valid characters and have digits
// more than 1 Character
// Course
// lecture
// user


/*
 * How does the program work?
 */


