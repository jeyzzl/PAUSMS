var __$j = jQuery; //Ligar a la variable de jQuery
var __vmSistemaId; //Id del sistema que estx cargando este js
var __vmOwner; //Id del usuario del sistema que estx cargando este js
var __vmWWidth, __vmWHeight, __vmWCenter, __vmWMiddle; //Dimensiones de ventana/documento

function __vmInicializa(){
	__vmDimensiones();

	__$j(document.body).append('<div id="_vmAcceso" style="border-radius: 5px; -moz-border-radius: 5px;" onclick="__vmMuestraPanel();">Reuniones Virtuales</div>');
	var _vmAcceso = __$j("#_vmAcceso");
	//Tamaxo y vista
	_vmAcceso.css({
		backgroundColor: "white",
		border: "solid 1px grey",
		position: "absolute",
		zIndex: "5000",
		height: "20px",
		width: "150px",
		cursor: "pointer"
	});
	//Posicionamiento
	_vmAcceso.css({
		left: (__vmWWidth-_vmAcceso.width()-100)+"px",
		top: (-_vmAcceso.height()+2)+"px"
	});
	
	_vmAcceso.hover(function(e){
		__$j("#_vmAcceso").animate({top: "0px"});
	},function(e){
		__$j("#_vmAcceso").animate({top: (-_vmAcceso.height()+2)+"px"});
	});
}

function __vmDimensiones(){
	__vmWWidth = __$j(document).width();
	__vmWHeight = __$j(document).height();
	__vmWCenter = __vmWWidth/2;
	__vmWMiddle = __vmWHeight/2;
}

function __vmMuestraPanel(){
	__$j(document.body).append('<div id="_vmBloqueo" style="position: absolute;">&nbsp;</div>')
	var __vmTmp = __$j("#_vmBloqueo");
	__vmTmp.css({
		backgroundColor: "black",
		zIndex: "5001",
		opacity: "0"
	}).css({
		left: "0px",
		top: "0px",
		width: (__vmWWidth)+"px",
		height: (__vmWHeight)+"px"
	}).animate({
		opacity: "0.7"
	});
	__vmTmp.click(function(e){
		__vmOcultaPanel();
	});
	
	__$j(document.body).append('<div id="_vmPanel" style="border-radius: 15px; -moz-border-radius: 15px;">'+
								'<iframe src="vmeeting/panelUsuario.jsp?sistemaId='+__vmSistemaId+'&owner='+__vmOwner+'" style="width: 100%; height: 100%; border: none; border-radius: 15px; -moz-border-radius: 15px;" />'+
							   '</div>');
	__vmTmp = __$j("#_vmPanel");
	__vmTmp.css({
		position: "absolute",
		zIndex: "5002",
		backgroundColor: "white",
		width: (__vmWWidth*.8)+"px",
		height: (__vmWHeight*.8)+"px",
		left: (__vmWWidth*.1)+"px",
		top: (__vmWHeight*.1)+"px",
		opacity: "0"
	}).animate({opacity: "1"});
}

function __vmOcultaPanel(){
	__$j("#_vmBloqueo").animate({opacity: "0"},function(){__$j("#_vmBloqueo").remove()});
	__$j("#_vmPanel").animate({opacity: "0"},function(){__$j("#_vmPanel").remove()});
}

function __vmCargaSistema(sistemaId, owner){
	__vmSistemaId = sistemaId;
	__vmOwner = owner;
}


setTimeout("__vmInicializa();",2000);