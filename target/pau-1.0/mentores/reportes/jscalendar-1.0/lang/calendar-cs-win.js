/* 
	calendar-cs-win.js
	language: Czech
	encoding: windows-1250
	author: Lubos Jerabek (xnet@seznam.cz)
	        Jan Uhlir (espinosa@centrum.cz)
*/

// ** I18N
Calendar._DN  = new Array('Nedxle','Pondxlx','xterx','Stxeda','xtvrtek','Pxtek','Sobota','Nedxle');
Calendar._SDN = new Array('Ne','Po','xt','St','xt','Px','So','Ne');
Calendar._MN  = new Array('Leden','xnor','Bxezen','Duben','Kvxten','xerven','xervenec','Srpen','Zxxx','xxjen','Listopad','Prosinec');
Calendar._SMN = new Array('Led','xno','Bxe','Dub','Kvx','xrv','xvc','Srp','Zxx','xxj','Lis','Pro');

// tooltips
Calendar._TT = {};
Calendar._TT["INFO"] = "O komponentx kalendxx";
Calendar._TT["TOGGLE"] = "Zmxna prvnxho dne v txdnu";
Calendar._TT["PREV_YEAR"] = "Pxedchozx rok (pxidrx pro menu)";
Calendar._TT["PREV_MONTH"] = "Pxedchozx mxsxc (pxidrx pro menu)";
Calendar._TT["GO_TODAY"] = "Dnexnx datum";
Calendar._TT["NEXT_MONTH"] = "Dalxx mxsxc (pxidrx pro menu)";
Calendar._TT["NEXT_YEAR"] = "Dalxx rok (pxidrx pro menu)";
Calendar._TT["SEL_DATE"] = "Vyber datum";
Calendar._TT["DRAG_TO_MOVE"] = "Chyx a txhni, pro pxesun";
Calendar._TT["PART_TODAY"] = " (dnes)";
Calendar._TT["MON_FIRST"] = "Ukax jako prvnx Pondxlx";
//Calendar._TT["SUN_FIRST"] = "Ukax jako prvnx Nedxli";

Calendar._TT["ABOUT"] =
"DHTML Date/Time Selector\n" +
"(c) dynarch.com 2002-2005 / Author: Mihai Bazon\n" + // don't translate this this ;-)
"For latest version visit: http://www.dynarch.com/projects/calendar/\n" +
"Distributed under GNU LGPL.  See http://gnu.org/licenses/lgpl.html for details." +
"\n\n" +
"Vxbxr datumu:\n" +
"- Use the \xab, \xbb buttons to select year\n" +
"- Pouxijte tlaxxtka " + String.fromCharCode(0x2039) + ", " + String.fromCharCode(0x203a) + " k vxbxru mxsxce\n" +
"- Podrxte tlaxxtko myxi na jakxmkoliv z txch tlaxxtek pro rychlejxx vxbxr.";

Calendar._TT["ABOUT_TIME"] = "\n\n" +
"Vxbxr xasu:\n" +
"- Kliknxte na jakoukoliv z xxstx vxbxru xasu pro zvxxenx.\n" +
"- nebo Shift-click pro snxenx\n" +
"- nebo kliknxte a txhnxte pro rychlejxx vxbxr.";

// the following is to inform that "%s" is to be the first day of week
// %s will be replaced with the day name.
Calendar._TT["DAY_FIRST"] = "Zobraz %s prvnx";

// This may be locale-dependent.  It specifies the week-end days, as an array
// of comma-separated numbers.  The numbers are from 0 to 6: 0 means Sunday, 1
// means Monday, etc.
Calendar._TT["WEEKEND"] = "0,6";

Calendar._TT["CLOSE"] = "Zavxxt";
Calendar._TT["TODAY"] = "Dnes";
Calendar._TT["TIME_PART"] = "(Shift-)Klikni nebo txhni pro zmxnu hodnoty";

// date formats
Calendar._TT["DEF_DATE_FORMAT"] = "d.m.yy";
Calendar._TT["TT_DATE_FORMAT"] = "%a, %b %e";

Calendar._TT["WK"] = "wk";
Calendar._TT["TIME"] = "xas:";
