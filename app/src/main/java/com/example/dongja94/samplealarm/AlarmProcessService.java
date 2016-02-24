package com.example.dongja94.samplealarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

public class AlarmProcessService extends Service {
    AlarmManager mAM;
    public AlarmProcessService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAM = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        processAlarmData();
        setAlarmTimer();
        return Service.START_NOT_STICKY;
    }

    private void processAlarmData() {
        long currentTime = System.currentTimeMillis();
        List<Integer> items = DataManager.getInstance().getItems();
        Calendar c = Calendar.getInstance(TimeZone.getDefault());
        c.setTimeInMillis(currentTime);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        if (items.contains(hour)) {
            Log.i("AlarmProcessSevice", "success");
        }
    }

    private void setAlarmTimer() {
        List<Integer> items = DataManager.getInstance().getItems();
        List<Integer> sorteditems = new ArrayList<Integer>(items);
        Collections.sort(sorteditems);
        long currentTime = System.currentTimeMillis();
        long settime = currentTime + 10000;
        Calendar c = Calendar.getInstance(TimeZone.getDefault());
        c.setTimeInMillis(settime);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int sethour = -1;
        for (int i = 0 ; i < sorteditems.size(); i++) {
            int item = sorteditems.get(i);
            if (item < hour) {
                continue;
            }
            sethour = item;
            break;
        }
        if (sethour == -1) {
            if (sorteditems.size() > 0) {
                sethour = sorteditems.get(0);
                c.add(Calendar.DAY_OF_YEAR, 1);
                c.set(Calendar.HOUR_OF_DAY, sethour);
            }
        } else {
            c.set(Calendar.HOUR_OF_DAY, sethour);
        }

        if (sethour != -1) {
            Intent intent = new Intent(this, AlarmProcessService.class);
            PendingIntent pi = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            mAM.set(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(), pi);
        }
    }
}
