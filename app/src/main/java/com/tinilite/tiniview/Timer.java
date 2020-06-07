package com.tinilite.tiniview;

import static java.lang.System.currentTimeMillis;

public class Timer {
    static private long interval;
    static private long timer0;
    static long now_ms(){

        return currentTimeMillis();
    }
   public static void tmr_init( long p_interval){

        timer0 = now_ms();
        interval = p_interval * 1000;
    }

    public static long tmr_get_timer0(){
        return timer0;
    }

    public static boolean tmr_poll(){
        if ((now_ms() - timer0) >= interval ){
            timer0 = timer0 + interval;
            return true;
        }
        else {
            return false;
        }
    }
}

