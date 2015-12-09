package com.github.vignesh_iopex.confirmdialog;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.PopupWindow;
import android.widget.TextView;

class ConfirmWindow extends PopupWindow implements Dialog {
  private static final int ANIM_DELAY = 500;
  private final Activity activity;
  private ConfirmView confirmContent;

  public ConfirmWindow(Activity activity) {
    this.activity = activity;
    initDialog();
  }

  private void initDialog() {
    confirmContent = new ConfirmView(activity, getDefaultConfirmContent());
    confirmContent.onPositive(new View.OnClickListener() {
      @Override public void onClick(View view) {
        dismissDialog();
      }
    });
    confirmContent.onNegative(new View.OnClickListener() {
      @Override public void onClick(View view) {
        dismissDialog();
      }
    });

    setTouchable(true);
    setFocusable(true);
    setBackgroundDrawable(new ColorDrawable(Color.parseColor("#80000000")));
    setAnimationStyle(R.style.FadeIn);
    setContentView(confirmContent);
    setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
    setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
  }

  public void showDialog() {
    TranslateAnimation trans = new TranslateAnimation(
        Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF,
        0, Animation.RELATIVE_TO_SELF, 1,
        Animation.RELATIVE_TO_SELF, 0);

    showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM,
        0, 0);
    trans.setDuration(ANIM_DELAY);
    trans.setInterpolator(new LinearInterpolator());

    confirmContent.startAnimation(trans);
  }

  @Override public void dismissDialog() {
    new Handler().postDelayed(new Runnable() {
      @Override public void run() {

        TranslateAnimation trans = new TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
            Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1);

        trans.setDuration(ANIM_DELAY);
        trans.setInterpolator(new LinearInterpolator());
        trans.setAnimationListener(new Animation.AnimationListener() {

          @Override
          public void onAnimationStart(Animation animation) {

          }

          @Override
          public void onAnimationRepeat(Animation animation) {
          }

          @Override
          public void onAnimationEnd(Animation animation) {
            dismiss();
          }
        });

        confirmContent.startAnimation(trans);
      }
    }, 300);
  }

  private View getDefaultConfirmContent() {
    TextView confirmText = new TextView(activity);
    confirmText.setPadding(10, 100, 10, 100);
    confirmText.setText(R.string.default_question);
    return confirmText;
  }
}
