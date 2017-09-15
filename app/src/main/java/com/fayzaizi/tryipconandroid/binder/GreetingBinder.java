package com.fayzaizi.tryipconandroid.binder;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/**
 * Created by Fayzaizi on 2017/8/27.
 */

public class GreetingBinder extends Binder {
    public static final String DESCRIPTOR = "com.fayzaizi.tryipconandroid.binder.GreetingIInterface";

    public static final int TRANSACTION_GREET = IBinder.FIRST_CALL_TRANSACTION;
    public static final int TRANSACTION_SHAKE_HANDS = IBinder.FIRST_CALL_TRANSACTION + 1;

    private GreetingIInterface iinterface;

    public GreetingBinder(GreetingIInterface greetingIInterface) {
        iinterface = greetingIInterface;
        attachInterface(iinterface, DESCRIPTOR);
    }

    public static GreetingIInterface asInterface(IBinder obj) {
        if (obj == null) return null;

        IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
        if (iin != null && iin instanceof GreetingIInterface) {
            return (GreetingIInterface) iin;
        } else {
            return new GreetingProxy(obj);
        }
    }

    @Override
    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        switch (code) {
            case INTERFACE_TRANSACTION: {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            case TRANSACTION_GREET: {
                data.enforceInterface(DESCRIPTOR);
                String name = data.readString();
                int friendVal = data.readInt();
                String result = iinterface.greet(name, friendVal == 1);
                reply.writeNoException();
                reply.writeString(result);
                return true;
            }
            case TRANSACTION_SHAKE_HANDS: {
                data.enforceInterface(DESCRIPTOR);
                Hand hand = data.readParcelable(Hand.class.getClassLoader());
                iinterface.shakeHands(hand);
                reply.writeNoException();
                return true;
            }
        }
        return super.onTransact(code, data, reply, flags);
    }
}
