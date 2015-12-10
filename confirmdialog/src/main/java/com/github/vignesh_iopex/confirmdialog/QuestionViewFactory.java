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

import android.content.Context;
import android.view.View;
import android.widget.TextView;

interface QuestionViewFactory {
  View getQuestionView(Context context, String question);

  /** Presents with textview with additional padding */
  QuestionViewFactory DEFAULT = new QuestionViewFactory() {
    @Override public View getQuestionView(Context context, String question) {
      TextView confirmText = new TextView(context);
      confirmText.setTextSize(20);
      confirmText.setPadding(10, 100, 10, 100);
      confirmText.setText(question);
      return confirmText;
    }
  };
}
