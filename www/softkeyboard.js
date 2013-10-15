function SoftKeyboard() {}

SoftKeyboard.prototype.show = function(win, fail) {
  return cordova.exec(
      function (args) { if(win !== undefined) { win(args); } },
      function (args) { if(fail !== undefined) { fail(args); } },
      "SoftKeyboard", "show", []);
};

SoftKeyboard.prototype.hide = function(win, fail) {
  return cordova.exec(
      function (args) { if(win !== undefined) { win(args); } },
      function (args) { if(fail !== undefined) { fail(args); } },
      "SoftKeyboard", "hide", []);
};

SoftKeyboard.prototype.isShowing = function(win, fail) {
  return cordova.exec(
      function (args) { if(win !== undefined) { win(args); } },
      function (args) { if(fail !== undefined) { fail(args); } },
      "SoftKeyboard", "isShowing", []);
};

module.exports = new SoftKeyboard();
