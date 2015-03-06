package com.github.vignesh_iopex.confirmdialog;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentTransaction;

class AnimationResources {
  private int enter;
  private int exit;
  private int animTime;

  public AnimationResources(int enter, int exit, int animTime) {
    this.enter = enter;
    this.exit = exit;
    this.animTime = animTime;
  }

  @SuppressLint("NewApi")
  public void applyAnimation(Object fragmentTransaction) {
    if (fragmentTransaction instanceof android.app.FragmentTransaction) {
      ((android.app.FragmentTransaction) fragmentTransaction).setCustomAnimations(enter, exit, exit, exit);
    } else if (fragmentTransaction instanceof FragmentTransaction) {
      ((FragmentTransaction) fragmentTransaction).setCustomAnimations(enter, exit, exit, exit);
    } else {
      throw new UnsupportedOperationException("Unknown fragment transaction object");
    }
  }

  public int getAnimTime() {
    if (animTime == 0) {
      animTime = Confirm.ANIMATION_TIMER;
    }
    return animTime;
  }
}
