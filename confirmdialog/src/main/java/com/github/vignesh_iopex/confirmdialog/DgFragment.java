package com.github.vignesh_iopex.confirmdialog;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static com.github.vignesh_iopex.confirmdialog.Confirm.getDialogRenderer;

public class DgFragment extends Fragment {
  public static final String TAG = DgFragment.class.getSimpleName();
  DialogRenderer dialogRenderer;

  @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                     @Nullable Bundle savedInstanceState) {
    return inflater.inflate(dialogRenderer.getLayoutId(), container, false);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    dialogRenderer.bindView(view);
  }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    dialogRenderer = getDialogRenderer(activity, getArguments(), this);
  }

  @Override public void onPause() {
    dialogRenderer.dismissDialog();
    super.onPause();
  }
}
