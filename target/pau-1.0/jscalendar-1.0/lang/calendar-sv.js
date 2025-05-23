// ** I18N

// Calendar SV language (Swedish, svenska)
// Author: Mihai Bazon, <mihai_bazon@yahoo.com>
// Translation team: <sv@li.org>
// Translator: Leonard Norrgxrd <leonard.norrgard@refactor.fi>
// Last translator: Leonard Norrgxrd <leonard.norrgard@refactor.fi>
// Encoding: iso-latin-1
// Distributed under the same terms as the calendar itself.

// For translators: please use UTF-8 if possible.  We strongly believe that
// Unicode is the answer to a real internationalized world.  Also please
// include your contact information in the header, as can be seen above.

// full day names
Calendar._DN = new Array
("sxndag",
 "mxndag",
 "tisdag",
 "onsdag",
 "torsdag",
 "fredag",
 "lxrdag",
 "sxndag");

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
Calendar._SDN_len = 2;
Calendar._SMN_len = 3;

// full month names
Calendar._MN = new Array
("januari",
 "februari",
 "mars",
 "april",
 "maj",
 "juni",
 "juli",
 "augusti",
 "september",
 "oktober",
 "november",
 "december");

// tooltips
Calendar._TT = {};
Calendar._TT["INFO"] = "Om kalendern";

Calendar._TT["ABOUT"] =
"DHTML Datum/tid-vxljare\n" +
"(c) dynarch.com 2002-2005 / Author: Mihai Bazon\n" + // don't translate this this ;-)
"Fxr senaste version gx till: http://www.dynarch.com/projects/calendar/\n" +
"Distribueras under GNU LGPL.  Se http://gnu.org/licenses/lgpl.html fxr detaljer." +
"\n\n" +
"Val av datum:\n" +
"- Anvxnd knapparna \xab, \xbb fxr att vxlja xr\n" +
"- Anvxnd knapparna " + String.fromCharCode(0x2039) + ", " + String.fromCharCode(0x203a) + " fxr att vxlja mxnad\n" +
"- Hxll musknappen nedtryckt px nxgon av ovanstxende knappar fxr snabbare val.";
Calendar._TT["ABOUT_TIME"] = "\n\n" +
"Val av tid:\n" +
"- Klicka px en del av tiden fxr att xka den delen\n" +
"- eller skift-klicka fxr att minska den\n" +
"- eller klicka och drag fxr snabbare val.";

Calendar._TT["PREV_YEAR"] = "Fxregxende xr (hxll fxr menu)";
Calendar._TT["PREV_MONTH"] = "Fxregxende mxnad (hxll fxr menu)";
Calendar._TT["GO_TODAY"] = "Gx till dagens datum";
Calendar._TT["NEXT_MONTH"] = "Fxljande mxnad (hxll fxr menu)";
Calendar._TT["NEXT_YEAR"] = "Fxljande xr (hxll fxr menu)";
Calendar._TT["SEL_DATE"] = "Vxlj datum";
Calendar._TT["DRAG_TO_MOVE"] = "Drag fxr att flytta";
Calendar._TT["PART_TODAY"] = " (idag)";
Calendar._TT["MON_FIRST"] = "Visa mxndag fxrst";
Calendar._TT["SUN_FIRST"] = "Visa sxndag fxrst";
Calendar._TT["CLOSE"] = "Stxng";
Calendar._TT["TODAY"] = "Idag";
Calendar._TT["TIME_PART"] = "(Skift-)klicka eller drag fxr att xndra tid";

// date formats
Calendar._TT["DEF_DATE_FORMAT"] = "%Y-%m-%d";
Calendar._TT["TT_DATE_FORMAT"] = "%A %d %b %Y";

Calendar._TT["WK"] = "liska";
