package de.phonostar;

import org.json.JSONArray;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;

public class SoftKeyboard extends CordovaPlugin {

    public SoftKeyboard() {
    }

    public void showKeyBoard() {
      InputMethodManager mgr = (InputMethodManager) cordova.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
      mgr.showSoftInput(webView, InputMethodManager.SHOW_IMPLICIT);

      ((InputMethodManager) cordova.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(webView, 0);
    }

    public void hideKeyBoard() {
      InputMethodManager mgr = (InputMethodManager) cordova.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
      mgr.hideSoftInputFromWindow(webView.getWindowToken(), 0);
    }

    public boolean isKeyBoardShowing() {
      int heightDiff = webView.getRootView().getHeight() - webView.getHeight();
      return (100 < heightDiff); // if more than 100 pixels, its probably a keyboard...
    }

    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) {
    final SoftKeyboard _this = this;
    if (action.equals("show")) {
      cordova.getThreadPool().execute(new Runnable() {
        public void run() {
          _this.showKeyBoard();
          callbackContext.success("done");
        }
      });
      return true;
    }
    else if (action.equals("hide")) {
      cordova.getThreadPool().execute(new Runnable() {
        public void run() {
          _this.hideKeyBoard();
          callbackContext.success();
        }
      });
      return true;
    }
    else if (action.equals("isShowing")) {
      cordova.getThreadPool().execute(new Runnable() {
        public void run() {
          callbackContext.success(Boolean.toString(_this.isKeyBoardShowing()));
        }
      });
      return true;
    }
    else {
      return false;
    }
  }
}

