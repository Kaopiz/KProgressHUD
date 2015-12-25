# KProgressHUD
[![License: MIT](https://img.shields.io/cocoapods/l/MBProgressHUD.svg?style=flat)](http://opensource.org/licenses/MIT)

Inspired by [MBProgressHUD](https://github.com/jdg/MBProgressHUD) for iOS.

[![](http://dl.dropbox.com/u/378729/MBProgressHUD/1-thumb.png)](http://dl.dropbox.com/u/378729/MBProgressHUD/1.png)

## Compatibility

Android 2.3 and later

## Adding KProgressHUD to your project

### Gradle

### Source code
You can download and import the *library* folder as module to your project.

## Usage

The usage of KProgressHUD is pretty straight forward. You create the HUD, customize its style and show it on the UI thread. Then fire a background thread to work on long-running tasks. When done, call `dismiss()` to close it (if you use a determinate style, the HUD will automatically dismiss if progress reach its max).

```java
KProgressHUD.create(MainActivity.this)
	.setStyle(KProgressHUD.Style.INDETERMINATE)
	.setLabel("Please wait")
	.setDetailsLabel("Downloading data");
	.setCancellable(true)
	.setAnimationSpeed(2)
	.setDimAmount(0.5f)
	.show();
```

See [**Javadocs**](http://kaopiz.github.io/KProgressHUD/) for more information.

## License

This code is distributed under [MIT license](LICENSE).
