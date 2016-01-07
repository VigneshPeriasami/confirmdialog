confirmdialog
=============

A custom animated Android confirm dialog

[![travis][travis-image]][travis-url] [![Android Arsenal][android-arsenal-img]][android-arsenal-link] [![maven-central][release-version]][maven-url]

![Confirm Dialog GIF][demo-gif]

[travis-image]: https://img.shields.io/travis/vignesh-iopex/confirmdialog.svg?style=flat
[travis-url]: https://travis-ci.org/vignesh-iopex/confirmdialog
[demo-gif]: https://raw.githubusercontent.com/vignesh-iopex/confirmdialog/master/demo.gif
[android-arsenal-img]: https://img.shields.io/badge/Android%20Arsenal-confirmdialog-brightgreen.svg?style=flat
[android-arsenal-link]: http://android-arsenal.com/details/1/1907
[release-version]: https://img.shields.io/maven-central/v/com.github.vignesh-iopex/confirmdialog.svg?style=flat
[maven-url]: http://repo1.maven.org/maven2/com/github/vignesh-iopex/confirmdialog/


Usage
-----
```java
public class MainActivity extends Activity {
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Confirm.using(this).ask("Fire missles?").onPositive("Yes", new Dialog.OnClickListener() {
                           @Override public void onClick(Dialog dialog, int which) {
                             launchMissles();
                           }}).onNegative("No",  new Dialog.OnClickListener() {
                           @Override public void onClick(Dialog dialog, int which) {
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
    <version>2.0-rc1</version>
</dependency>
```
or Gradle:
```groovy
compile 'com.github.vignesh-iopex:confirmdialog:2.0-rc1'
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
