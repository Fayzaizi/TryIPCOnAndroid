package com.fayzaizi.tryipconandroid;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.fayzaizi.tryipconandroid.binder.GreetingBinder;
import com.fayzaizi.tryipconandroid.binder.GreetingIInterface;
import com.fayzaizi.tryipconandroid.binder.Hand;

/**
 * Created by Fayzaizi on 2017/8/27.
 */

public class ClientActivity extends AppCompatActivity {

    private GreetingIInterface greetingIInterface;

    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // service is a BinderProxy here
            greetingIInterface = GreetingBinder.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        setTitle("Binder test client");

        findViewById(R.id.button_greet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (greetingIInterface != null) {
                    try {
                        String response = greetingIInterface.greet("Fayzaizi", true);
                        Log.d("Greet", "Process(" + Process.myPid() + "): " + response);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        findViewById(R.id.button_shake).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (greetingIInterface != null) {
                    try {
                        greetingIInterface.shakeHands(new Hand(Hand.LEFT));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = new Intent(this, GreetingService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();

        unbindService(mServiceConnection);
    }
}
