function SoftKeyboard() {}

SoftKeyboard.prototype.show = function(win, fail) {
  return cordova.exec(
      function (args) { if(win) { win(args); } },
      function (args) { if(fail) { fail(args); } },
      "SoftKeyboard", "show", []);
};

SoftKeyboard.prototype.hide = function(win, fail) {
  return cordova.exec(
      function (args) { if(win) { win(args); } },
      function (args) { if(fail) { fail(args); } },
      "SoftKeyboard", "hide", []);
};

SoftKeyboard.prototype.isShowing = function(win, fail) {
  return cordova.exec(
      function (isShowing) { 
        if(win) { 
          isShowing = isShowing === 'true' ? true : false
          win(isShowing); 
        } 
      },
      function (args) { if(fail) { fail(args); } },
      "SoftKeyboard", "isShowing", []);
};

module.exports = new SoftKeyboard();
