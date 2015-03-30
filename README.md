confirmdialog
=============

A custom animated Android confirm dialog

![Confirm Dialog GIF](https://github.com/vignesh-iopex/confirmdialog/blob/master/demo.gif)

Usage
-----
```java
public class MainActivity extends Activity {
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Confirm.using(this).ask("Fire missles?").onPositive("Yes", new DialogEventListener.OnClickListener() {
                           @Override public void onClick(DialogEventListener dialog, int which) {
                             launchMissles();
                           }}).onNegative("No",  new DialogEventListener.OnClickListener() {
                           @Override public void onClick(DialogEventListener dialog, int which) {
                             sendFalseAlarm();
                           }}).build().show();
  }
}
```
Download
--------

Download via maven:
```xml
<dependency>
    <groupId>com.github.vignesh-iopex</groupId>
    <artifactId>confirmdialog</artifactId>
    <version>1.0</version>
</dependency>
```
or Gradle:
```groovy
compile 'com.github.vignesh-iopex:confirmdialog:1.0'
```

License
-------

    Copyright 2014 Vignesh Periasami

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
