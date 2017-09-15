package com.fayzaizi.tryipconandroid.binder;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.util.Log;

/**
 * Created by Fayzaizi on 2017/9/2.
 */

public class Hand implements Parcelable {

    public static final int LEFT = 0;
    public static final int RIGHT = 1;

    private int whichHand;

    public Hand(int which) {
        whichHand = which;
    }

    public void shake() {
        if (whichHand != LEFT && whichHand != RIGHT) {
            Log.d("Greet", "Process(" + Process.myPid() + "): " + "So, maybe a leg...");
        } else {
            Log.d("Greet", "Process(" + Process.myPid() + "): " + "Shake...sha...s...");
        }
    }

    public static final Parcelable.Creator<Hand> CREATOR = new Parcelable.Creator<Hand>() {
        @Override
        public Hand createFromParcel(Parcel source) {
            return new Hand(source.readInt());
        }

        @Override
        public Hand[] newArray(int size) {
            return new Hand[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(whichHand);
    }
}
