package com.android.systemui.keyguard;

import android.content.Context;
import android.net.Uri;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.android.systemui.R;
import pl.droidsonroids.gif.GifImageView;

public class KeyguardGifOverlay extends FrameLayout {
    private GifImageView mGifView;

    public KeyguardGifOverlay(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.keyguard_gif_overlay, this, true);
        mGifView = findViewById(R.id.keyguard_gif_view);
        
        // Add smooth transition animation
        mGifView.setAlpha(0f);
        mGifView.animate()
                .alpha(1f)
                .setDuration(getResources().getInteger(R.integer.lockscreen_gif_fade_duration))
                .start();
    }

    public void updateGifOverlay() {
        boolean isEnabled = Settings.Secure.getInt(getContext().getContentResolver(),
                Settings.Secure.LOCKSCREEN_GIF_ENABLED, 0) == 1;
        String gifPath = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.LOCKSCREEN_GIF_PATH);

        if (isEnabled && gifPath != null) {
            mGifView.setVisibility(View.VISIBLE);
            mGifView.setImageURI(Uri.parse(gifPath));
        } else {
            mGifView.setVisibility(View.GONE);
        }
    }
}
