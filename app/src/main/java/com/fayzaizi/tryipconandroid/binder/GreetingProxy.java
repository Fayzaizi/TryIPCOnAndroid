package com.fayzaizi.tryipconandroid.binder;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/**
 * Created by Fayzaizi on 2017/8/27.
 */

public class GreetingProxy implements GreetingIInterface {

    private IBinder mRemote;

    public GreetingProxy(IBinder remote) {
        mRemote = remote;
    }

    @Override
    public String greet(String name, boolean friend) throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        String result = "Oops, no one's here!";
        try {
            data.writeInterfaceToken(GreetingBinder.DESCRIPTOR);
            data.writeString(name);
            data.writeInt(friend ? 1 : 0);
            mRemote.transact(GreetingBinder.TRANSACTION_GREET, data, reply, 0);
            reply.readException();
            result = reply.readString();
        } finally {
            data.recycle();
            reply.recycle();
        }
        return result;
    }

    @Override
    public void shakeHands(Hand myHand) throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            data.writeInterfaceToken(GreetingBinder.DESCRIPTOR);
            data.writeParcelable(myHand, 0);
            mRemote.transact(GreetingBinder.TRANSACTION_SHAKE_HANDS, data, reply, 0);
            reply.readException();
        } finally {
            data.recycle();
            reply.recycle();
        }
    }

    @Override
    public IBinder asBinder() {
        return mRemote;
    }
}
