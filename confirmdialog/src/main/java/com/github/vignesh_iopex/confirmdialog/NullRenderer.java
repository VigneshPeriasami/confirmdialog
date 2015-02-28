package com.github.vignesh_iopex.confirmdialog;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

public abstract class NullRenderer extends DialogRenderer {

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
