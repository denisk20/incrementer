package com.denisk.incrementer;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.media.VolumeProviderCompat;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.MEDIA_BUTTON");
        MediaButtonReceiver receiver = new MediaButtonReceiver();
        registerReceiver(receiver, intentFilter);

        MediaSessionCompat mediaSession = new MediaSessionCompat(this, "My Player");

        mediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSession.setActive(true);
        mediaSession.setCallback(new MediaSessionCompat.Callback() {
            @Override
            public boolean onMediaButtonEvent(Intent mediaButtonEvent) {
                Log.e("===", "Here!" + mediaButtonEvent.getAction());

                return super.onMediaButtonEvent(mediaButtonEvent);
            }
        });

        VolumeProviderCompat volumeProviderCompat = new VolumeProviderCompat(VolumeProviderCompat.VOLUME_CONTROL_RELATIVE, 10, 5) {
            @Override
            public void onSetVolumeTo(int volume) {
                super.onSetVolumeTo(volume);
            }

            @Override
            public void onAdjustVolume(int direction) {
                Log.e("===", "Volume: " + direction);
                super.onAdjustVolume(direction);
            }
        };

        mediaSession.setPlaybackToRemote(volumeProviderCompat);
    }
}
