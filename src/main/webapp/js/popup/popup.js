/***************************/
//@Author: Adrian "yEnS" Mato Gondelle
//@website: www.yensdesign.com
//@email: yensamg@gmail.com
//@license: Feel free to use it, but keep this credits please!					
/***************************/

//SETTING UP OUR POPUP
//0 means disabled; 1 means enabled;
var popupStatus = 0;

//loading popup with jQuery magic!
function loadPopup(){
	//loads popup only if it is disabled
	if(popupStatus==0){
		jQuery("#popup").show();
		jQuery("#backgroundPopup").css({
			"opacity": "0.9"
		});
		jQuery("#backgroundPopup").fadeIn("normal");
		jQuery("#popupContact").fadeIn("normal");
		popupStatus = 1;
	}
}

//disabling popup with jQuery magic!
function disablePopup(){
	//disables popup only if it is enabled
	if(popupStatus==1){
		jQuery("#backgroundPopup").fadeOut("normal");
		jQuery("#popupContact").fadeOut("normal");
		popupStatus = 0;
		jQuery("#popupContact").hidden = true;
		jQuery("#popup").hide();
		jQuery("#popup").attr("src","../../js/popup/loading.html");
	}
}

//centering popup
function centerPopup(){
	//request data for centering
	var windowWidth 	= document.documentElement.clientWidth;
	var windowHeight 	= document.documentElement.clientHeight;
	var popupHeight 	= jQuery("#popupContact").height();
	var popupWidth 		= jQuery("#popupContact").width();
	//centering
	document.getElementById('popup').height = popupHeight-1;
	jQuery("#popupContact").css({
		"position": "fixed",
		"top": "5%",
		//"top": 20,
		//"top": windowHeight/2-popupHeight/2,
		"left": windowWidth/2-popupWidth/2
	});
	//only need force for IE6
	
	jQuery("#backgroundPopup").css({
		"height": "100%"
		//"height": windowHeight
	});
	
}

//CONTROLLING EVENTS IN jQuery
$(document).ready(function(){
	jQuery.noConflict();
	//LOADING POPUP
	//Click the button event!
	jQuery("#clickButton").click(function(){
		//centering with css
		centerPopup();
		//load popup
		loadPopup();
	});
				
	//CLOSING POPUP
	//Click the x event!
	jQuery("#popupContactClose").click(function(){
		disablePopup();
	});
	//Click out event!
	jQuery("#backgroundPopup").click(function(){
		disablePopup();
	});
	//Press Escape event!
	jQuery(document).keypress(function(e){
		if(e.keyCode==27 && popupStatus==1){
			disablePopup();
		}
	});
});

//---------------------------------------------Segundos--------------------------------------
$(document).ready(function(){
	jQuery.noConflict();
	jQuery(".clickButton2").click(function(){
		centerPopup();
		loadPopup();
	});
});