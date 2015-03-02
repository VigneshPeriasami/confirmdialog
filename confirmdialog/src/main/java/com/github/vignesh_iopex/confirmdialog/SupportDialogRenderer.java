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

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

class SupportDialogRenderer extends DialogRenderer {
  private Fragment fragment;
  private Fragment dummy;
  private FragmentManager fragmentManager;

  public SupportDialogRenderer(FragmentManager fragmentManager, Fragment fragment,
                               View overlay, View overlayTagHolder, ViewGroup parent, ViewBinder viewBinder) {
    super(overlay, overlayTagHolder, parent, viewBinder);
    this.fragment = fragment;
    this.dummy = new DummyFragment();
    this.fragmentManager = fragmentManager;
  }

  @Override public void render(final int contentId) {
    super.render(contentId);
    Bundle args = getBundleArgs();
    dummy.setArguments(args);
    fragment.setArguments(args);
    fragmentManager.beginTransaction().add(contentId, dummy).commit();

    FragmentTransaction transaction = fragmentManager.beginTransaction();
    transaction.setCustomAnimations(R.anim.slide_from_bottom, R.anim.slide_to_bottom,
        R.anim.slide_to_bottom, R.anim.slide_to_bottom);
    transaction.replace(contentId, fragment).addToBackStack(null).commit();
  }

  private void cleanFragmentManager() {
    try {
      fragmentManager.beginTransaction().remove(fragment).remove(dummy).commitAllowingStateLoss();
    } catch (IllegalStateException e) {
      Log.i(Confirm.TAG, "Cleaning done by activity state changes");
    }
  }

  @Override public void dismissDialog() {
    fragmentManager.popBackStack();
    viewBinder.dismissView();
    // had to fix this hard coded delay for the custom animation.
    new Handler().postDelayed(new Runnable() {
      @Override public void run() {
        cleanFragmentManager();
        SupportDialogRenderer.super.dismissDialog();
      }
    }, 600);
  }
}
