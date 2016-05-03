package com.hilary.humanresource;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TabHost;

public class BoardTabHost extends TabHost {

    private int currentTab = 0;
    int duration = 1000;// ms; the bigger the slower

    public BoardTabHost(Context context) {
        super(context);
    }

    public BoardTabHost(Context context, AttributeSet attr) {
        super(context, attr);
    }

    @Override
    public void setCurrentTab(int index) {
        // we need two animation here: first one is fading animation, 2nd one is coming animation
        // translateAnimation of fading fragment
        if (index > currentTab) {// fly right to left and leave the screen
            TranslateAnimation translateAnimation = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF/* fromXType */, 0f/* fromXValue */,
                    Animation.RELATIVE_TO_SELF/* toXType */, -1.0f/* toXValue */,
                    Animation.RELATIVE_TO_SELF, 0f,
                    Animation.RELATIVE_TO_SELF, 0f
            );
            translateAnimation.setDuration(duration);
            getCurrentView().startAnimation(translateAnimation);
        } else if (index < currentTab) {// fly left to right
            TranslateAnimation translateAnimation = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0f,
                    Animation.RELATIVE_TO_SELF, 1.0f,
                    Animation.RELATIVE_TO_SELF, 0f,
                    Animation.RELATIVE_TO_SELF, 0f
            );
            translateAnimation.setDuration(duration);
            getCurrentView().startAnimation(translateAnimation);
        }
        super.setCurrentTab(index);// the current tab is index now
        // translateAnimation of adding fragment
        if (index > currentTab) {
            TranslateAnimation translateAnimation = new TranslateAnimation(
                    Animation.RELATIVE_TO_PARENT, 1.0f,/* fly into screen */
                    Animation.RELATIVE_TO_PARENT, 0f,  /* screen location */
                    Animation.RELATIVE_TO_PARENT, 0f,
                    Animation.RELATIVE_TO_PARENT, 0f
            );
            translateAnimation.setDuration(duration);
            getCurrentView().startAnimation(translateAnimation);
        } else if (index < currentTab) {
            TranslateAnimation translateAnimation = new TranslateAnimation(
                    Animation.RELATIVE_TO_PARENT, -1.0f,
                    Animation.RELATIVE_TO_PARENT, 0f,
                    Animation.RELATIVE_TO_PARENT, 0f,
                    Animation.RELATIVE_TO_PARENT, 0f
            );
            translateAnimation.setDuration(duration);
            getCurrentView().startAnimation(translateAnimation);
        }
        currentTab = index;
    }
}
