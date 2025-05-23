// ** I18N

// Calendar ZH language
// Author: muziq, <muziq@sina.com>
// Encoding: GB2312 or GBK
// Distributed under the same terms as the calendar itself.

// full day names
Calendar._DN = new Array
("xxxxxx",
 "xxxxһ",
 "xxxڶx",
 "xxxxxx",
 "xxxxxx",
 "xxxxxx",
 "xxxxxx",
 "xxxxxx");

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
("xx",
 "һ",
 "xx",
 "xx",
 "xx",
 "xx",
 "xx",
 "xx");

// full month names
Calendar._MN = new Array
("һxx",
 "xxxx",
 "xxxx",
 "xxxx",
 "xxxx",
 "xxxx",
 "xxxx",
 "xxxx",
 "xxxx",
 "ʮxx",
 "ʮһxx",
 "ʮxxxx");

// short month names
Calendar._SMN = new Array
("һxx",
 "xxxx",
 "xxxx",
 "xxxx",
 "xxxx",
 "xxxx",
 "xxxx",
 "xxxx",
 "xxxx",
 "ʮxx",
 "ʮһxx",
 "ʮxxxx");

// tooltips
Calendar._TT = {};
Calendar._TT["INFO"] = "xxxx";

Calendar._TT["ABOUT"] =
"DHTML Date/Time Selector\n" +
"(c) dynarch.com 2002-2005 / Author: Mihai Bazon\n" + // don't translate this this ;-)
"For latest version visit: http://www.dynarch.com/projects/calendar/\n" +
"Distributed under GNU LGPL.  See http://gnu.org/licenses/lgpl.html for details." +
"\n\n" +
"ѡxxxxxx:\n" +
"- xxx \xab, \xbb xxťѡxxxxx\n" +
"- xxx " + String.fromCharCode(0x2039) + ", " + String.fromCharCode(0x203a) + " xxťѡxxx·x\n" +
"- xxxxxxxϰxťxɴӲ˵xxпxxxѡxxxxݻxx·x";
Calendar._TT["ABOUT_TIME"] = "\n\n" +
"ѡxxʱxx:\n" +
"- xxxСʱxxxxӿxʹxxxxֵxxһ\n" +
"- xxסShiftxxxxxСʱxxxxӿxʹxxxxֵxxһ\n" +
"- xxxx϶xxxxɽxxпxxxѡxx";

Calendar._TT["PREV_YEAR"] = "xxһxx (xxסxxx˵x)";
Calendar._TT["PREV_MONTH"] = "xxһxx (xxסxxx˵x)";
Calendar._TT["GO_TODAY"] = "תxxxxxx";
Calendar._TT["NEXT_MONTH"] = "xxһxx (xxסxxx˵x)";
Calendar._TT["NEXT_YEAR"] = "xxһxx (xxסxxx˵x)";
Calendar._TT["SEL_DATE"] = "ѡxxxxxx";
Calendar._TT["DRAG_TO_MOVE"] = "x϶x";
Calendar._TT["PART_TODAY"] = " (xxxx)";

// the following is to inform that "%s" is to be the first day of week
// %s will be replaced with the day name.
Calendar._TT["DAY_FIRST"] = "xxxxxxxʾ%s";

// This may be locale-dependent.  It specifies the week-end days, as an array
// of comma-separated numbers.  The numbers are from 0 to 6: 0 means Sunday, 1
// means Monday, etc.
Calendar._TT["WEEKEND"] = "0,6";

Calendar._TT["CLOSE"] = "xرx";
Calendar._TT["TODAY"] = "xxxx";
Calendar._TT["TIME_PART"] = "(Shift-)xxxxxxxx϶xxıxֵ";

// date formats
Calendar._TT["DEF_DATE_FORMAT"] = "%Y-%m-%d";
Calendar._TT["TT_DATE_FORMAT"] = "%A, %b %exx";

Calendar._TT["WK"] = "xx";
Calendar._TT["TIME"] = "ʱxx:";
