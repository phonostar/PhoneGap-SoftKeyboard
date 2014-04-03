package de.phonostar;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;

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

    public boolean sendKey(final int keyCode, final CallbackContext callbackContext) {
      if (!isKeyBoardShowing()) {
        callbackContext.error("Unable to send key press for " + keyCode + ": Softkeyboard is not showing");
        return false;
      }

      cordova.getActivity().runOnUiThread(new Runnable() {
        public void run() {
          boolean downResult, upResult;
          downResult = webView.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, keyCode));
          if (!downResult) {
            callbackContext.error("Failed sending keydown event for key " + keyCode);
            return;
          }

          upResult = webView.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, keyCode));
          if (upResult) {
            callbackContext.success();
          } else {
            callbackContext.error("Failed sending keyup event for key " + keyCode);
          }
        }
      });
      return true;
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {
    if (action.equals("show")) {
      this.showKeyBoard();
      callbackContext.success("done");
      return true;
    }
    else if (action.equals("hide")) {
      this.hideKeyBoard();
      callbackContext.success();
      return true;
    }
    else if (action.equals("isShowing")) {
      callbackContext.success(Boolean.toString(this.isKeyBoardShowing()));
      return true;
    }
    else if (action.equals("sendKey")) {
      try {
        int keyCode = args.getInt(0);
        return this.sendKey(keyCode, callbackContext);
      } catch (JSONException jsonEx) {
        callbackContext.error(jsonEx.getMessage());
        return false;
      }
    }
    else {
      return false;
    }
  }
}

