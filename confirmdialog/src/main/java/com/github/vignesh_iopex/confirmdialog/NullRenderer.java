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

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

abstract class NullRenderer extends DialogRenderer {

  public NullRenderer() {
    super(null, null, null, null);
  }

  public static NullRenderer getNullRenderer(Object fragment) {
    if (fragment instanceof Fragment) {
      return new SupportNullRenderer((Fragment) fragment);
    } else {
      return null;
    }
  }

  @Override public void dismissDialog() {
    // super.dismissDialog();
    removeFragment();
  }

  public abstract void removeFragment();

  public static class SupportNullRenderer extends NullRenderer {
    private Fragment fragment;

    public SupportNullRenderer(Fragment fragment) {
      this.fragment = fragment;
      // remove the fragment as it lost the DialogRenderer.
      removeFragment();
    }

    @Override public void bindView(View view) {
    }

    @Override public int getLayoutId() {
      removeFragment();
      return R.layout.dialog_fragment;
    }

    @Override public void removeFragment() {
      Log.i(TAG, "Removing the fragment --- " + fragment.getClass().getSimpleName());
      fragment.getFragmentManager().beginTransaction().remove(fragment).commit();
    }
  }
}
