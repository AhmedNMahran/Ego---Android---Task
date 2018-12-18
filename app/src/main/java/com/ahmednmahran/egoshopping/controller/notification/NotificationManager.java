package com.ahmednmahran.egoshopping.controller.notification;

import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import com.ahmednmahran.egoshopping.R;
import com.ahmednmahran.egoshopping.view.activity.MainActivity;

/**
 * Created by Ahmed Nabil on 12/19/18.
 */
public class NotificationManager {
    public static final String CHANNEL_ID = "EgoShopping";
    public static final int NOTIFICATION_ID = 123456789;
    public static void sendNotification(Context context, String title, String messageBody) {

        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            CharSequence name = context.getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);
            int importance = android.app.NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system
            android.app.NotificationManager notificationManager =
                    (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
            notificationManager.notify(NOTIFICATION_ID /* ID of notification */, notificationBuilder.build());

        } else {
            NotificationManagerCompat notificationManager =
                    NotificationManagerCompat.from(context);

            notificationManager.notify(NOTIFICATION_ID /* ID of notification */, notificationBuilder.build());
        }
    }
}
