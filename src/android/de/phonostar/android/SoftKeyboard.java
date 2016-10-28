package de.phonostar;

import org.json.JSONArray;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.view.View;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;

public class SoftKeyboard extends CordovaPlugin {

    public SoftKeyboard() {
    }

    public void showKeyBoard() {
      InputMethodManager mgr = (InputMethodManager) cordova.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
      mgr.showSoftInput(getAndroidViewByCordovaWebView(), InputMethodManager.SHOW_IMPLICIT);

      ((InputMethodManager) cordova.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(getAndroidViewByCordovaWebView(), 0);
    }

    public void hideKeyBoard() {
      InputMethodManager mgr = (InputMethodManager) cordova.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
      mgr.hideSoftInputFromWindow(getAndroidViewByCordovaWebView().getWindowToken(), 0);
    }

    public boolean isKeyBoardShowing() {
      int heightDiff = getAndroidViewByCordovaWebView().getRootView().getHeight() - getAndroidViewByCordovaWebView().getHeight();
      if(100 < heightDiff){
        return heightDiff;
      } else {
        return false;
      }
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
        else {
          return false;
        }
    }

    private View getAndroidViewByCordovaWebView(){
        if (webView instanceof View){
            return webView;
        } else {
            return webView.getView();
        }
    }
}
