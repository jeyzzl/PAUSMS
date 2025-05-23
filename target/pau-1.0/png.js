var timerID = 0;
var ie = (document.all) ? true:false;
var nc = (document.layers)? true:false;
var n6 = (document.getElementById)? true:false;

function bg(imgb){
	document.body.background ="imagenes/w/"+imgb+".jpg";
}

var expDays = 30;
var exp = new Date(); 
exp.setTime(exp.getTime() + (expDays*24*60*60*1000));

function fondoW(w){
	var fondo = GetCookie('fondo');
	if(w=="-1"){
		if (fondo == null) {
			fondo = "8";
			SetCookie('fondo', fondo, exp);
		}
	}
	else{
		fondo=w;
		SetCookie('fondo', fondo, exp);
	}
	document.body.background ="imagenes/w/"+fondo+".jpg";
}
	
function getCookieVal (offset) {  
	var endstr = document.cookie.indexOf (";", offset);  
	if (endstr == -1)    
	endstr = document.cookie.length;  
	return unescape(document.cookie.substring(offset, endstr));
}

function GetCookie (name) {  
	var arg = name + "=";  
	var alen = arg.length;  
	var clen = document.cookie.length;  
	var i = 0;  
	while (i < clen) {    
		var j = i + alen;    
		if (document.cookie.substring(i, j) == arg)      
			return getCookieVal (j);    
		i = document.cookie.indexOf(" ", i) + 1;    
		if (i == 0) break;   
	}  
	return null;
}

function SetCookie (name, value) {  
	var argv = SetCookie.arguments;  
	var argc = SetCookie.arguments.length;  
	var expires = (argc > 2) ? argv[2] : null;  
	var path = (argc > 3) ? argv[3] : null;  
	var domain = (argc > 4) ? argv[4] : null;  
	var secure = (argc > 5) ? argv[5] : false;  
	document.cookie = name + "=" + escape (value) + 
	((expires == null) ? "" : ("; expires=" + expires.toGMTString())) + 
	((path == null) ? "" : ("; path=" + path)) +  
	((domain == null) ? "" : ("; domain=" + domain)) +    
	((secure == true) ? "; secure" : "");
}

function DeleteCookie (name) {  
	var exp = new Date();  
	exp.setTime (exp.getTime() - 1);  
	var cval = GetCookie (name);  
	document.cookie = name + "=" + cval + "; expires=" + exp.toGMTString();
}


function PNG_loader() { 
  if(ie && navigator.userAgent.indexOf('Opera')==-1){
   for(var i=0; i<document.images.length; i++) { 
      var img = document.images[i]; 
      var imgName = img.src.toUpperCase(); 
      if (imgName.substring(imgName.length-3, imgName.length) == "PNG") { 
         var imgID = (img.id) ? "id='" + img.id + "' " : ""; 
         var imgClass = (img.className) ? "class='" + img.className + "' " : ""; 
         var imgTitle = (img.title) ? "title='" + img.title + "' " : "title='" + img.alt + "' "; 
         var imgStyle = "display:inline-block;" + img.style.cssText; 
         if (img.align == "left") imgStyle += "float:left;"; 
         if (img.align == "right") imgStyle += "float:right;"; 
         if (img.parentElement.href) imgStyle += "cursor:hand;"; 
         var strNewHTML = "<span " + imgID + imgClass + imgTitle 
            + " style=\"" + "width:" + img.width + "px; height:" + img.height + "px;" + imgStyle + ";" 
            + "filter:progid:DXImageTransform.Microsoft.AlphaImageLoader" 
            + "(src=\'" + img.src + "\', sizingMethod='scale');\"></span>"; 
         img.outerHTML = strNewHTML; 
         i--; 
      } 
   } 
  }
} 
window.attachEvent("onload", PNG_loader);