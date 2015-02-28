package com.github.vignesh_iopex.confirmdialog;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class SupportDialogRenderer extends DialogRenderer {
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
    Bundle args = new Bundle();
    args.putInt(Confirm.CONFIRM_TAG_HOLDER, overlayTagHolder.getId());
    dummy.setArguments(args);
    fragment.setArguments(args);
    fragmentManager.beginTransaction().add(contentId, dummy).commit();

    FragmentTransaction transaction = fragmentManager.beginTransaction();
    transaction.setCustomAnimations(R.anim.slide_from_bottom, R.anim.slide_to_bottom,
        R.anim.slide_to_bottom, R.anim.slide_to_bottom);
    transaction.replace(contentId, fragment).addToBackStack(null).commit();
  }

  @Override public void dismissDialog() {
    fragmentManager.popBackStack();
    // popping backstack doesn't removes from the fragmentManager.. had to fix this.
    new Handler().postDelayed(new Runnable() {
      @Override public void run() {
        SupportDialogRenderer.super.dismissDialog();
      }
    }, 1200);
  }
}
