package com.fjz.demo.androidx.fcm;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.orhanobut.logger.Logger;
import com.tencent.mmkv.MMKV;

public class FcmMsgService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Logger.i("on message received data: %s", remoteMessage.getData());
    }

    @Override
    public void onDeletedMessages() {

        Logger.i("on deleted message");
    }

    @Override
    public void onMessageSent(String s) {
        Logger.i("on message send:%s", s);
    }

    @Override
    public void onSendError(String s, Exception e) {
        Logger.e("on send error:%s, %s", s, e);
    }

    @Override
    public void onNewToken(String s) {
        Logger.i("on new fcm token:%s", s);

        MMKV.defaultMMKV().putString("fcm_token", s);

    }
}
