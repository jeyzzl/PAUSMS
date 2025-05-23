// ** I18N

// Calendar RU language
// Translation: Sly Golovanov, http://golovanov.net, <sly@golovanov.net>
// Encoding: any
// Distributed under the same terms as the calendar itself.

// For translators: please use UTF-8 if possible.  We strongly believe that
// Unicode is the answer to a real internationalized world.  Also please
// include your contact information in the header, as can be seen above.

// full day names
Calendar._DN = new Array
("xxxxxxxxxxx",
 "xxxxxxxxxxx",
 "xxxxxxx",
 "xxxxx",
 "xxxxxxx",
 "xxxxxxx",
 "xxxxxxx",
 "xxxxxxxxxxx");

// Please note that the following array of short day names (and the same goes
// for short month names, _SMN) isn't absolutely necessary.  We give it here
// for exemplification on how one can customize the short day names, but if
// they are simply the first N letters of the full name you can simply say:
//
//   Calendar._SDN_len = N; // short day name length
//   Calendar._SMN_len = N; // short month name length
//
// If N = 3 then this is not needed either since we assume a value of 3 if not
// present, to be compatible with translation files that were written before
// this feature.

// short day names
Calendar._SDN = new Array
("xxx",
 "xxx",
 "xxx",
 "xxx",
 "xxx",
 "xxx",
 "xxx",
 "xxx");

// full month names
Calendar._MN = new Array
("xxxxxx",
 "xxxxxxx",
 "xxxx",
 "xxxxxx",
 "xxx",
 "xxxx",
 "xxxx",
 "xxxxxx",
 "xxxxxxxx",
 "xxxxxxx",
 "xxxxxx",
 "xxxxxxx");

// short month names
Calendar._SMN = new Array
("xxx",
 "xxx",
 "xxx",
 "xxx",
 "xxx",
 "xxx",
 "xxx",
 "xxx",
 "xxx",
 "xxx",
 "xxx",
 "xxx");

// tooltips
Calendar._TT = {};
Calendar._TT["INFO"] = "x xxxxxxxxx...";

Calendar._TT["ABOUT"] =
"DHTML Date/Time Selector\n" +
"(c) dynarch.com 2002-2005 / Author: Mihai Bazon\n" + // don't translate this this ;-)
"For latest version visit: http://www.dynarch.com/projects/calendar/\n" +
"Distributed under GNU LGPL.  See http://gnu.org/licenses/lgpl.html for details." +
"\n\n" +
"xxx xxxxxxx xxxx:\n" +
"- xxx xxxxxx xxxxxx \xab, \xbb xxxxx xxxxxxx xxx\n" +
"- xxx xxxxxx xxxxxx " + String.fromCharCode(0x2039) + ", " + String.fromCharCode(0x203a) + " xxxxx xxxxxxx xxxxx\n" +
"- xxxxxxxxx xxx xxxxxx xxxxxxxx, xxxxx xxxxxxxxx xxxx xxxxxxxx xxxxxx.";
Calendar._TT["ABOUT_TIME"] = "\n\n" +
"xxx xxxxxxx xxxxx:\n" +
"- xxx xxxxx xx xxxxx xxx xxxxxxx xxx xxxxxxxxxxxxx\n" +
"- xxx xxxxx x xxxxxxx xxxxxxxx Shift xxx xxxxxxxxxxx\n" +
"- xxxx xxxxxx x xxxxxxx xxxxxx xxxxx/xxxxxx, xxx xxxxx xxxxxxxx xxxxxxx.";

Calendar._TT["PREV_YEAR"] = "xx xxx xxxxx (xxxxxxxxxx xxx xxxx)";
Calendar._TT["PREV_MONTH"] = "xx xxxxx xxxxx (xxxxxxxxxx xxx xxxx)";
Calendar._TT["GO_TODAY"] = "xxxxxxx";
Calendar._TT["NEXT_MONTH"] = "xx xxxxx xxxxxx (xxxxxxxxxx xxx xxxx)";
Calendar._TT["NEXT_YEAR"] = "xx xxx xxxxxx (xxxxxxxxxx xxx xxxx)";
Calendar._TT["SEL_DATE"] = "xxxxxxxx xxxx";
Calendar._TT["DRAG_TO_MOVE"] = "xxxxxxxxxxxxxx xxxxxx";
Calendar._TT["PART_TODAY"] = " (xxxxxxx)";

// the following is to inform that "%s" is to be the first day of week
// %s will be replaced with the day name.
Calendar._TT["DAY_FIRST"] = "xxxxxx xxxx xxxxxx xxxxx %s";

// This may be locale-dependent.  It specifies the week-end days, as an array
// of comma-separated numbers.  The numbers are from 0 to 6: 0 means Sunday, 1
// means Monday, etc.
Calendar._TT["WEEKEND"] = "0,6";

Calendar._TT["CLOSE"] = "xxxxxxx";
Calendar._TT["TODAY"] = "xxxxxxx";
Calendar._TT["TIME_PART"] = "(Shift-)xxxx xxx xxxxxx x xxxxxxx";

// date formats
Calendar._TT["DEF_DATE_FORMAT"] = "%Y-%m-%d";
Calendar._TT["TT_DATE_FORMAT"] = "%e %b, %a";

Calendar._TT["WK"] = "xxx";
Calendar._TT["TIME"] = "xxxxx:";
