package com.example.telegram.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;

public class FixedHeaderBehavior extends CoordinatorLayout.Behavior<View> {

    public FixedHeaderBehavior() {
    }

    public FixedHeaderBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        // Di chuyển child (header) khi dependency (AppBarLayout) di chuyển
        child.setY(dependency.getY() + dependency.getHeight());
        return true;
    }
}
