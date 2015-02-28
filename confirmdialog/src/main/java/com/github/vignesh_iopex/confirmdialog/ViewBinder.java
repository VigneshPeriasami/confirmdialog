package com.github.vignesh_iopex.confirmdialog;

import android.view.View;

public interface ViewBinder {
  void bindView(View view);
  int getLayoutId();

  public interface ViewHolder {
    public void construct();
  }
}
