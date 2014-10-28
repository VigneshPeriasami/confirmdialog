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
package com.vikki.logs.dialog.confirmdialog.sample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.vignesh_iopex.confirmdialog.ConfirmDialog;
import com.github.vignesh_iopex.confirmdialog.DialogEventListener;

public class MainActivity extends ActionBarActivity implements View.OnClickListener,
    DialogEventListener.OnClickListener, DialogEventListener.OnDismissListener {
  private static final String TAG = MainActivity.class.getSimpleName();
  private Button btnShowDialog;
  private Button btnShowCustomDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    injectViews();
  }

  public void injectViews() {
    btnShowDialog = (Button) findViewById(R.id.btn_show_dialog);
    btnShowCustomDialog = (Button) findViewById(R.id.btn_show_custom_dialog);
    btnShowDialog.setOnClickListener(this);
    btnShowCustomDialog.setOnClickListener(this);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btn_show_dialog:
        ConfirmDialog.Builder builder = new ConfirmDialog.Builder(this);
        builder.setContextText("Can you see confirmation dialog?")
            .setConfirmButton("Yes", this).setCancelButton("No", this).setOnDismissListener(this)
            .create().show();
        break;
      case R.id.btn_show_custom_dialog:
        TextView textView = new TextView(this);
        textView.setPadding(10, 100, 10, 100);
        textView.setText("This is custom view dynamically built by the builder.");
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(25);
        new ConfirmDialog.Builder(this).setContentView(textView).setConfirmButton("Ok", this)
            .create().show();
        break;
      default:
        throw new UnsupportedOperationException("Unsupported click event");
    }
  }

  @Override
  public void onClick(DialogEventListener dialog, int which) {
    switch (which) {
      case ConfirmDialog.POSITIVE_BUTTON:
        Toast.makeText(MainActivity.this, "Confirmed action", Toast.LENGTH_SHORT).show();
        break;
      case ConfirmDialog.NEGATIVE_BUTTON:
        Toast.makeText(MainActivity.this, "Cancelled Action", Toast.LENGTH_SHORT).show();
        break;
      default:
        throw new UnsupportedOperationException("Unknown event detected");
    }
  }

  @Override
  public void onDismiss(DialogEventListener dialog) {
    Toast.makeText(MainActivity.this, "Dismissed", Toast.LENGTH_SHORT).show();
  }
}
