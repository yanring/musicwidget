package com.example.yanring.a4yanring;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.RemoteViews;

/**
 * Created by Yanring on 2016/2/20.
 */
public class MusicWidget extends AppWidgetProvider {

    private final String WIDGET_button_play = "WIDGET_BUTTON_PLAY";
    private final String WIDGET_button_last_one = "WIDGET_BUTTON_LAST_ONE";
    private final String WIDGET_button_next_one = "WIDGET_BUTTON_NEXT_ONE";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent actionIntent = new Intent(context, MusicService.class);
        super.onReceive(context, intent);
        if(intent!=null && !TextUtils.equals(intent.getAction(),"MessageFromService")){
            if (TextUtils.equals(intent.getAction(), WIDGET_button_play)) {
                actionIntent.putExtra("action", "start");
            } else if (TextUtils.equals(intent.getAction(), WIDGET_button_last_one)) {
                actionIntent.putExtra("action", "pre");
            } else if (TextUtils.equals(intent.getAction(), WIDGET_button_next_one)) {
                actionIntent.putExtra("action", "next");
            }
            context.startService(actionIntent);
        }else if (intent!=null && TextUtils.equals(intent.getAction(),"MessageFromService")){
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.widget_music);
            if (intent.getBooleanExtra("state",false)){
                remoteViews.setImageViewResource(R.id.start,R.mipmap.button_stop);
            }else {
                remoteViews.setImageViewResource(R.id.start,R.mipmap.button_play);
            }
            remoteViews.setTextViewText(R.id.musicName,intent.getStringExtra("MusicName"));
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName componentName = new ComponentName(context, MusicWidget.class);
            appWidgetManager.updateAppWidget(componentName, remoteViews);


        }

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.widget_music);
        Intent intent = new Intent();
        intent.setClass(context,MusicWidget.class);//发给自己


        intent.setAction(WIDGET_button_play);
        PendingIntent pendingIntent_play = PendingIntent.getBroadcast(context, 0, intent, 0);//getBroadcast()的意思其实是，获取一个PendingIntent对象，而且该对象日后激发时所做的事情是启动一个新Broadcast
        remoteViews.setOnClickPendingIntent(R.id.start, pendingIntent_play);//点击时激发pendingIntent_play

        intent.setAction(WIDGET_button_last_one);
        PendingIntent pendingIntent_lastOne = PendingIntent.getBroadcast(context, 0, intent, 0);
        remoteViews.setOnClickPendingIntent(R.id.LastOne, pendingIntent_lastOne);


        intent.setAction(WIDGET_button_next_one);
        PendingIntent pendingIntent_NextOne = PendingIntent.getBroadcast(context, 0, intent, 0);
        remoteViews.setOnClickPendingIntent(R.id.NextOne, pendingIntent_NextOne);

        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }
}
