package com.fayzaizi.tryipconandroid.binder;

import android.os.IInterface;
import android.os.RemoteException;

/**
 * Created by Fayzaizi on 2017/8/27.
 */

public interface GreetingIInterface extends IInterface {

    String greet(String name, boolean friend) throws RemoteException;

    void shakeHands(Hand myHand) throws RemoteException;
}