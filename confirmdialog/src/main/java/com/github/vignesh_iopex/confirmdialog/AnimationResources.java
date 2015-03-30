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
