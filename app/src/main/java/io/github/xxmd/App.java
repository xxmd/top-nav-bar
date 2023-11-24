package io.github.xxmd;

import android.app.Application;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import java.util.function.Consumer;

import io.github.xxmd.databinding.TopNavBarBinding;


public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initTopNavBar();
    }

    private void initTopNavBar() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            TopNavBar.beforeInit = new Consumer<TopNavBarBinding>() {
                @Override
                public void accept(TopNavBarBinding topNavBarBinding) {
                    topNavBarBinding.ivLeftIcon.setImageResource(R.drawable.ic_baseline_arrow_back_ios_24);
                    topNavBarBinding.tvTitle.setTextColor(Color.RED);
                }
            };
        }
    }
}
