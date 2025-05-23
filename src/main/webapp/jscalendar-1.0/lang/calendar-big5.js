// ** I18N

// Calendar big5 language
// Author: Gary Fu, <gary@garyfu.idv.tw>
// Encoding: big5
// Distributed under the same terms as the calendar itself.

// For translators: please use UTF-8 if possible.  We strongly believe that
// Unicode is the answer to a real internationalized world.  Also please
// include your contact information in the header, as can be seen above.
	
// full day names
Calendar._DN = new Array
("xPxxxx",
 "xPxxx@",
 "xPxxxG",
 "xPxxxT",
 "xPxxx|",
 "xPxxxx",
 "xPxxxx",
 "xPxxxx");

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
 "x@",
 "xG",
 "xT",
 "x|",
 "xx",
 "xx",
 "xx");

// full month names
Calendar._MN = new Array
("x@xx",
 "xGxx",
 "xTxx",
 "x|xx",
 "xxxx",
 "xxxx",
 "xCxx",
 "xKxx",
 "xExx",
 "xQxx",
 "xQx@xx",
 "xQxGxx");

// short month names
Calendar._SMN = new Array
("x@xx",
 "xGxx",
 "xTxx",
 "x|xx",
 "xxxx",
 "xxxx",
 "xCxx",
 "xKxx",
 "xExx",
 "xQxx",
 "xQx@xx",
 "xQxGxx");

// tooltips
Calendar._TT = {};
Calendar._TT["INFO"] = "xxxx";

Calendar._TT["ABOUT"] =
"DHTML Date/Time Selector\n" +
"(c) dynarch.com 2002-2005 / Author: Mihai Bazon\n" + // don't translate this this ;-)
"For latest version visit: http://www.dynarch.com/projects/calendar/\n" +
"Distributed under GNU LGPL.  See http://gnu.org/licenses/lgpl.html for details." +
"\n\n" +
"xxxxxܤxk:\n" +
"- xϥx \xab, \xbb xxxsxixxܦ~xx\n" +
"- xϥx " + String.fromCharCode(0x2039) + ", " + String.fromCharCode(0x203a) + " xxxsxixxܤxx\n" +
"- xxxxWxxxxxxxsxixHx[xֿxx";
Calendar._TT["ABOUT_TIME"] = "\n\n" +
"xɶxxxܤxk:\n" +
"- xIxxxxx󪺮ɶxxxxxxixWx[xxx\n" +
"- xPxɫxShiftxxAxIxxxixx֨xx\n" +
"- xIxxxé즲xix[x֧xxܪxxx";

Calendar._TT["PREV_YEAR"] = "xWx@x~ (xxxxxx)";
Calendar._TT["PREV_MONTH"] = "xUx@x~ (xxxxxx)";
Calendar._TT["GO_TODAY"] = "x줵xx";
Calendar._TT["NEXT_MONTH"] = "xWx@xx (xxxxxx)";
Calendar._TT["NEXT_YEAR"] = "xUx@xx (xxxxxx)";
Calendar._TT["SEL_DATE"] = "xxܤxx";
Calendar._TT["DRAG_TO_MOVE"] = "x즲";
Calendar._TT["PART_TODAY"] = " (xxxx)";

// the following is to inform that "%s" is to be the first day of week
// %s will be replaced with the day name.
Calendar._TT["DAY_FIRST"] = "xN %s xxܦbxe";

// This may be locale-dependent.  It specifies the week-end days, as an array
// of comma-separated numbers.  The numbers are from 0 to 6: 0 means Sunday, 1
// means Monday, etc.
Calendar._TT["WEEKEND"] = "0,6";

Calendar._TT["CLOSE"] = "xxxx";
Calendar._TT["TODAY"] = "xxxx";
Calendar._TT["TIME_PART"] = "xIxxorx즲xixxxܮɶx(xPxɫxShiftxxxx)";

// date formats
Calendar._TT["DEF_DATE_FORMAT"] = "%Y-%m-%d";
Calendar._TT["TT_DATE_FORMAT"] = "%a, %b %e";

Calendar._TT["WK"] = "xg";
Calendar._TT["TIME"] = "Time:";
