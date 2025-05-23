// ** I18N

// Calendar EN language
// Author: Mihai Bazon, <mihai_bazon@yahoo.com>
// Translation: Yourim Yi <yyi@yourim.net>
// Encoding: EUC-KR
// lang : ko
// Distributed under the same terms as the calendar itself.

// For translators: please use UTF-8 if possible.  We strongly believe that
// Unicode is the answer to a real internationalized world.  Also please
// include your contact information in the header, as can be seen above.

// full day names

Calendar._DN = new Array
("xϿxxx",
 "xxxxxx",
 "ȭxxxx",
 "xxxxxx",
 "xxxxx",
 "xݿxxx",
 "xxxxx",
 "xϿxxx");

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
 "xx",
 "ȭ",
 "xx",
 "xx",
 "xx",
 "xx",
 "xx");

// full month names
Calendar._MN = new Array
("1xx",
 "2xx",
 "3xx",
 "4xx",
 "5xx",
 "6xx",
 "7xx",
 "8xx",
 "9xx",
 "10xx",
 "11xx",
 "12xx");

// short month names
Calendar._SMN = new Array
("1",
 "2",
 "3",
 "4",
 "5",
 "6",
 "7",
 "8",
 "9",
 "10",
 "11",
 "12");

// tooltips
Calendar._TT = {};
Calendar._TT["INFO"] = "calendar xx xxxؼx";

Calendar._TT["ABOUT"] =
"DHTML Date/Time Selector\n" +
"(c) dynarch.com 2002-2005 / Author: Mihai Bazon\n" + // don't translate this this ;-)
"\n"+
"xֽx xxxxxx xxxxx÷xxx http://www.dynarch.com/projects/calendar/ xx x湮xϼxxx\n" +
"\n"+
"GNU LGPL xxx̼xxxxx xxxxx˴ϴx. \n"+
"xxx̼xxxxx xxxx xڼxxx xxxxxx http://gnu.org/licenses/lgpl.html xx xxxxxxxx." +
"\n\n" +
"xx¥ xxxx:\n" +
"- xxxxxx xxxxxϷxxx \xab, \xbb xxưxx xxxxմϴx\n" +
"- xxxx xxxxxϷxxx " + String.fromCharCode(0x2039) + ", " + String.fromCharCode(0x203a) + " xxưxx xxxxxxxx\n" +
"- xxx xxxxxx xxxxxx xx xxxxxx xxxxxx xxxxxϽx xx xֽxxϴx.";
Calendar._TT["ABOUT_TIME"] = "\n\n" +
"xðx xxxx:\n" +
"- xxx콺xx xxxxxx xðxxx xxxxxմϴx\n" +
"- Shift Űxx xԲx xxxxxx xxxxxմϴx\n" +
"- xxxx xxx¿xxx xxx콺xx xxxxx̸x xx xx xxxxxx xxxx xxxմϴx.\n";

Calendar._TT["PREV_YEAR"] = "xxxx xx (xxx xxxxxx xxx)";
Calendar._TT["PREV_MONTH"] = "xxxx xx (xxx xxxxxx xxx)";
Calendar._TT["GO_TODAY"] = "xxxx xx¥xx";
Calendar._TT["NEXT_MONTH"] = "xxxx xx (xxx xxxxxx xxx)";
Calendar._TT["NEXT_YEAR"] = "xxxx xx (xxx xxxxxx xxx)";
Calendar._TT["SEL_DATE"] = "xx¥xx xxxxxϼxxx";
Calendar._TT["DRAG_TO_MOVE"] = "xxx콺 x巡x׷x x̵x xϼxxx";
Calendar._TT["PART_TODAY"] = " (xxxx)";
Calendar._TT["MON_FIRST"] = "xxxxxxxx xx xxxx xxxx xxxϷx";
Calendar._TT["SUN_FIRST"] = "xϿxxxxx xx xxxx xxxx xxxϷx";
Calendar._TT["CLOSE"] = "xݱx";
Calendar._TT["TODAY"] = "xxxx";
Calendar._TT["TIME_PART"] = "(Shift-)Ŭxx xǴx x巡xx xϼxxx";

// date formats
Calendar._TT["DEF_DATE_FORMAT"] = "%Y-%m-%d";
Calendar._TT["TT_DATE_FORMAT"] = "%b/%e [%a]";

Calendar._TT["WK"] = "xx";
