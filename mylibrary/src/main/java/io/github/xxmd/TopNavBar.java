package io.github.xxmd;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleableRes;

import org.apache.commons.lang3.StringUtils;

import java.util.function.Consumer;

import io.github.xxmd.databinding.TopNavBarBinding;

public class TopNavBar extends FrameLayout {
    public static Consumer<TopNavBarBinding> beforeInit;
    public static Consumer<TopNavBarBinding> afterInit;

    private TopNavBarBinding binding;
    private TypedArray typedArray;

    public TopNavBar(@NonNull Context context) {
        super(context);
        init();
    }

    public TopNavBarBinding getBinding() {
        return binding;
    }

    public TopNavBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TopNavBar);
        init();
    }

    private void init() {
        binding = TopNavBarBinding.inflate(LayoutInflater.from(getContext()), this, true);

        if (beforeInit != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                beforeInit.accept(binding);
            }
        }
        initView();
        bindEvent();
        if (afterInit != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                afterInit.accept(binding);
            }
        }
    }

    private void bindEvent() {
        binding.leftIconTouchArea.setOnClickListener(v -> {
            Activity activity = (Activity) getContext();
            activity.finish();
        });
    }

    private void initView() {
        ViewGroup.LayoutParams layoutParams = binding.statusBar.getLayoutParams();
        layoutParams.height = getStatusBarHeight();
        binding.statusBar.setLayoutParams(layoutParams);

        if (typedArray != null) {
            initViewByTypedArray();
        }
    }

    private int getStatusBarHeight() {
        int statusBarHeightId = getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = getContext().getResources().getDimensionPixelSize(statusBarHeightId);
        return statusBarHeight;
    }

    private void initViewByTypedArray() {
        int tintColor = typedArray.getColor(R.styleable.TopNavBar_tint, Integer.MIN_VALUE);
        if (tintColor != Integer.MIN_VALUE) {
            binding.ivLeftIcon.setColorFilter(tintColor);
            binding.tvTitle.setTextColor(tintColor);
            binding.tvSubtitle.setTextColor(tintColor);
            binding.ivRightIcon.setColorFilter(tintColor);
            binding.tvRightText.setTextColor(tintColor);
        }


        setIcon(R.styleable.TopNavBar_leftIcon, R.styleable.TopNavBar_leftIconTint, binding.ivLeftIcon, false);

        setText(R.styleable.TopNavBar_title, R.styleable.TopNavBar_titleColor, binding.tvTitle);
        setText(R.styleable.TopNavBar_subtitle, R.styleable.TopNavBar_subtitleColor, binding.tvSubtitle);
        setText(R.styleable.TopNavBar_rightText, R.styleable.TopNavBar_rightTextColor, binding.tvRightText);

        setIcon(R.styleable.TopNavBar_rightIcon, R.styleable.TopNavBar_rightIconTint, binding.ivRightIcon, true);
    }

    private void setIcon(@StyleableRes int index, @StyleableRes int colorIndex, ImageView imageView, boolean autoHidden) {
        Drawable icon = typedArray.getDrawable(index);
        if (icon != null) {
            imageView.setImageDrawable(icon);
        } else {
            if (autoHidden) {
                imageView.setVisibility(GONE);
            }
        }
        setColorFilter(colorIndex, imageView);
    }

    private void setText(@StyleableRes int index, @StyleableRes int colorIndex, TextView textView) {
        String text = typedArray.getString(index);
        if (StringUtils.isEmpty(text)) {
            textView.setVisibility(GONE);
        } else {
            textView.setVisibility(VISIBLE);
            textView.setText(text);
        }

        setColor(colorIndex, textView);
    }

    private void setColor(@StyleableRes int index, TextView textView) {
        int color = typedArray.getColor(index, Integer.MIN_VALUE);
        if (color != Integer.MIN_VALUE) {
            textView.setTextColor(color);
        }
    }

    private void setColorFilter(@StyleableRes int index, ImageView imageView) {
        int color = typedArray.getColor(index, Integer.MIN_VALUE);
        if (color != Integer.MIN_VALUE) {
            imageView.setColorFilter(color);
        }
    }
}