package com.fayzaizi.tryipconandroid;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.fayzaizi.tryipconandroid.binder.GreetingBinder;
import com.fayzaizi.tryipconandroid.binder.GreetingIInterface;
import com.fayzaizi.tryipconandroid.binder.Hand;

/**
 * Created by Fayzaizi on 2017/8/27.
 */

public class GreetingService extends Service {

    private GreetingIInterface service = new MyGreeting();
    private GreetingBinder greetingBinder;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        greetingBinder = new GreetingBinder(service);
        ((MyGreeting) service).setBinder(greetingBinder);
        return greetingBinder;
    }

    private class MyGreeting implements GreetingIInterface {
        private GreetingBinder mBinder;

        public void setBinder(GreetingBinder binder) {
            mBinder = binder;
        }

        @Override
        public String greet(String name, boolean friend) throws RemoteException {
            if (friend) {
                return name + "\'s coming!!!";
            } else {
                return "Hello! " + name + ".";
            }
        }

        @Override
        public void shakeHands(Hand myHand) throws RemoteException {
            myHand.shake();
        }

        @Override
        public IBinder asBinder() {
            return mBinder;
        }
    }
}
