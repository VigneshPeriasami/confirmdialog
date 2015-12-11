/*
 * Copyright 2014 Vignesh Periasami
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.github.vignesh_iopex.confirmdialog;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.PopupWindow;

class ConfirmWindow extends PopupWindow implements PopupWindow.OnDismissListener, WindowDelegate {
  private static final int ANIM_DELAY = 500;
  private final Activity activity;
  private final Confirm dialog;
  private ConfirmView vwConfirm;
  private final Dialog.OnDismissListener onDismissListener;

  public ConfirmWindow(Activity activity, Confirm dialog, ConfirmView confirmView,
                       Dialog.OnDismissListener onDismissListener) {
    this.activity = activity;
    this.dialog = dialog;
    this.vwConfirm = confirmView;
    this.onDismissListener = onDismissListener;
    initDialog();
  }

  private void initDialog() {
    setTouchable(true);
    setFocusable(true);
    setBackgroundDrawable(new ColorDrawable(Color.parseColor("#80000000")));
    setOnDismissListener(this);
    setAnimationStyle(R.style.FadeIn);
    setContentView(vwConfirm);
    setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
    setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
  }

  @Override public void showDialog() {
    TranslateAnimation trans = new TranslateAnimation(
        Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF,
        0, Animation.RELATIVE_TO_SELF, 1,
        Animation.RELATIVE_TO_SELF, 0);

    showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM,
        0, 0);
    trans.setDuration(ANIM_DELAY);
    trans.setInterpolator(new LinearInterpolator());

    vwConfirm.startAnimation(trans);
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

        vwConfirm.startAnimation(trans);
      }
    }, 300);
  }

  @Override public void onDismiss() {
    dialog.cleanup();
    onDismissListener.onDismiss(dialog);
  }
}
