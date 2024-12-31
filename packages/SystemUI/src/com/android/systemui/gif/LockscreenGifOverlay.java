package com.android.systemui.gif;

import android.content.Context;
import android.graphics.Movie;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class LockscreenGifOverlay extends ImageView {
    private Movie movie;
    private long movieStart;
    private Handler handler;
    private boolean isEnabled;

    public LockscreenGifOverlay(Context context, AttributeSet attrs) {
        super(context, attrs);
        handler = new Handler();
        updateSettings();
    }

    private void updateSettings() {
        isEnabled = Settings.System.getInt(getContext().getContentResolver(),
                Settings.System.LOCKSCREEN_GIF_ENABLED, 0) == 1;
        String gifPath = Settings.System.getString(getContext().getContentResolver(),
                Settings.System.LOCKSCREEN_GIF_PATH);
        if (isEnabled && gifPath != null) {
            loadGif(gifPath);
        }
    }

    private void loadGif(String path) {
        try {
            movie = Movie.decodeFile(path);
            if (movie != null) {
                startAnimation();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final Runnable updateGifFrame = new Runnable() {
        @Override
        public void run() {
            if (movie != null && isEnabled) {
                long now = SystemClock.uptimeMillis();
                if (movieStart == 0) movieStart = now;
                int duration = movie.duration();
                if (duration == 0) duration = 1000;
                int relTime = (int) ((now - movieStart) % duration);
                movie.setTime(relTime);
                invalidate();
                handler.postDelayed(this, 16);
            }
        }
    };

    private void startAnimation() {
        movieStart = 0;
        handler.post(updateGifFrame);
    }

    public void stopAnimation() {
        handler.removeCallbacks(updateGifFrame);
    }
}
