/*!
 * Messages 0.1 - JavaScript Library to display Messages like ubuntu
 * Copyright 2010, Universidad de Montemorelos
 */
 
var TOP = 75;		// Initial position of the messages from the top
var __messageWidth = 250;		// Fixed width of the messages
 
var __messageCounter = 0;
var __messagePosition = new Object();
var __$j = jQuery;

function showMessage(title, message, bgColor, textColor, img){
	var html = '<div id="message-'+__messageCounter+'">'+
					'<b class="rtop">'+
					  '<b class="r1"></b> <b class="r2"></b> <b class="r3"></b> <b class="r4"></b>'+
					'</b>'+
					'<div class="rmsg">'+
						'<table style="width:90%;" align="center">'+
							'<tr>';
	if(img){
		html +=					'<td width="20%"><img src="'+img+'" width="100%" /></td>';
	}
	html +=						'<td>'+
									'<font color="'+textColor+'" size="4"><b>'+title+'</b></font><br />';
	if(message){
		html +=						'<font color="'+textColor+'" size="2">'+message+'</font>';
	}
	html +=						'</td>'+
							'</tr>'+
						'</table>'+
					'</div>'+
					'<b class="rbottom">'+
					  '<b class="r4"></b> <b class="r3"></b> <b class="r2"></b> <b class="r1"></b>'+
					'</b>'+
				 '</div>';
	__$j(document.body).append(html);
	var m = __$j("#message-"+__messageCounter);
	m.css({
		position: 'absolute',
		zIndex: 10000,
		opacity: 0,
		width: __messageWidth,
		left: (__$j(window).width()-__messageWidth-10),
		top: (TOP)
	});
	putMessage(m, __messageCounter);
	
	__$j("#message-"+__messageCounter+" b b").css({backgroundColor: bgColor});
	__$j("#message-"+__messageCounter+" .rmsg").css({backgroundColor: bgColor});
	
	setTimeout('clearMessage('+__messageCounter+')', 5000);
	
	__messageCounter++;
}

function clearMessage(number){
	if(__messagePosition.number == number){
		__messagePosition.number = null;
		__messagePosition.bottom = null;
	}
	__$j("#message-"+number).animate({opacity: 0}, function(){
		__$j(this).remove();
	});
}

function putMessage(message, number){
	message = __$j(message);
	if(__messagePosition.bottom){
		message.css({
			top: (__messagePosition.bottom + 5)
		});
	}
	message.animate({opacity: 0.8});
	__messagePosition.number = number;
	__messagePosition.bottom = message.offset().top + message.height();
}
//#165398
function noticeMessage(title, message, mat){
	//alert('Matricula:'+mat);
	showMessage(title, message, '#585858', 'white', mat==''?'':'foto?Codigo='+mat);
}

function warningMessage(title, message){
	showMessage(title, message, '#585858', 'white', 'imagenes/warning.png');
}

function errorMessage(title, message){
	showMessage(title, message, '#585858', 'white', 'imagenes/error.png');
}
