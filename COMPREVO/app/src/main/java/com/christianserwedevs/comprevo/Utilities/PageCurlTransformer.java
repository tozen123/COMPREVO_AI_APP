package com.christianserwedevs.comprevo.Utilities;

import android.view.View;
import androidx.viewpager2.widget.ViewPager2;

public class PageCurlTransformer implements ViewPager2.PageTransformer {
    @Override
    public void transformPage(View page, float position) {
        if (position < -1) { // Page is way off-screen to the left
            page.setAlpha(0);
        } else if (position <= 0) { // Moving to the left page
            page.setAlpha(1 + position); // Fade out as it moves left
        } else if (position <= 1) { // Moving to the right page
            page.setAlpha(1 - position); // Fade out as it moves right
        } else { // Page is way off-screen to the right
            page.setAlpha(0);
        }
    }
}
