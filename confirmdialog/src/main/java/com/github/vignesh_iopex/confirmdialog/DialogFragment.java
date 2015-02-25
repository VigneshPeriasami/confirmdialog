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
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Will construct the confirm dialog fragment based on the builder values of
 * {@linkplain com.github.vignesh_iopex.confirmdialog.ConfirmDialog.Builder}
 */
abstract class DialogFragment extends Fragment {

  /**
   * Activity from which ConfirmDialog is constructed *
   */
  protected Activity activity;
  protected View dialogContentContainer;

  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);

    View view = inflater.inflate(getDialogViewId(), container, false);
    injectViews(view);
    injectListeners();
    constructViewElements();
    return view;
  }

  protected abstract int getDialogViewId();

  protected abstract void injectViews(View view);

  protected abstract void injectListeners();

  protected abstract void constructViewElements();

  /**
   * To render the ConfirmDialog overlay and slide the content of the confirm dialog.
   */
  public abstract void show();

  protected void renderDialogContent(int replaceId) {
    FragmentTransaction transaction = ((ActionBarActivity) activity).getSupportFragmentManager()
        .beginTransaction();
    // still working on to use popBackStack animation.
    transaction.setCustomAnimations(R.anim.slide_from_bottom, 0, 0, R.anim.slide_to_bottom);
    transaction.add(replaceId, this).addToBackStack(null).commit();
  }

  public void dismiss() {
    ((ActionBarActivity) activity).getSupportFragmentManager().popBackStack();
  }

  protected void dismissDialogContent() {
    Animation animation = AnimationUtils.loadAnimation(activity, R.anim.slide_to_bottom);
    animation.setAnimationListener(new Animation.AnimationListener() {
      @Override
      public void onAnimationStart(Animation animation) {

      }

      @Override
      public void onAnimationEnd(Animation animation) {
        dismiss();
      }

      @Override
      public void onAnimationRepeat(Animation animation) {

      }
    });
    this.dialogContentContainer.startAnimation(animation);
  }

  /*@Override
  public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
    int id;
    Log.i(TAG, "Entering state " + enter);
    if (enter) {
      id = R.anim.slide_from_bottom;
    } else {
      id = R.anim.slide_to_bottom;
    }
    return AnimationUtils.loadAnimation(getActivity(), id);
  }*/
}
