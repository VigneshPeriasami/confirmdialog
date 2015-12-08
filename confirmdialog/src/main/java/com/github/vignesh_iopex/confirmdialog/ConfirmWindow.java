package com.github.vignesh_iopex.confirmdialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.PopupWindow;

public class ConfirmWindow extends PopupWindow {
  private final Context context;
  private View confirmContent;
  private Button btnPositive;
  private Button btnNegative;

  public ConfirmWindow(Context context) {
    this.context = context;
    initDialog();
  }

  private void initDialog() {
    confirmContent = LayoutInflater.from(context).inflate(R.layout.dialog_fragment, null);
    btnPositive = (Button) confirmContent.findViewById(R.id.btn_confirm);
    btnNegative = (Button) confirmContent.findViewById(R.id.btn_cancel);
    btnNegative.setVisibility(View.VISIBLE);
    btnPositive.setVisibility(View.VISIBLE);
    btnPositive.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        dismiss();
      }
    });
    btnNegative.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        dismiss();
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

  public void showPopWin(Activity activity) {
    TranslateAnimation trans = new TranslateAnimation(
        Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF,
        0, Animation.RELATIVE_TO_SELF, 1,
        Animation.RELATIVE_TO_SELF, 0);

    showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM,
        0, 0);
    trans.setDuration(400);
    trans.setInterpolator(new AccelerateDecelerateInterpolator());

    confirmContent.startAnimation(trans);
  }
}
