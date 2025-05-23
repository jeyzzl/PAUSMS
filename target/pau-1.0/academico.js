
var menuOculto = 0;
var headOculto = 0;

function Menu(foto){
	if(menuOculto == 0){
		menuOculto = 1;
		foto.src="imagenes/flechaD.jpg";
		ocultaMenu();
	}else{
		document.getElementById("principal").style.width="850px";
		menuOculto = 0;
		foto.src="imagenes/flechaI.jpg";
		muestraMenu();
	}
}

ccM=17;			
ttM=0;			

function ocultaMenu(){
	
	if(ccM>2) {
			ttM-=ccM;			
			document.getElementById("menu").style.left=ttM+"px";
			document.getElementById("principal").style.left=150+ttM+"px";
			ccM--;

			setTimeout("ocultaMenu()",1);
	
	}else{
			ccM=17;
			
			ttM=-150;
			document.getElementById("principal").style.width="1000px";
	}
}

function muestraMenu(){
	if(ccM>2) {
			ttM+=ccM;			
			document.getElementById("menu").style.left=ttM+"px";
			document.getElementById("principal").style.left=150+ttM+"px";

			ccM--;


			setTimeout("muestraMenu()",1);
	
	}else{

			ccM=17;
			
			ttM=0;
			
	}
}

function Head(foto){
	if(headOculto == 0){
		headOculto = 1;
		foto.src="imagenes/flechaB.jpg";
		ocultaHead();
	}else{
		document.getElementById("principal").style.height="530px";
		document.getElementById("menu").style.height="530px";
		headOculto = 0;
		foto.src="imagenes/flechaA.jpg";
		muestraHead();
	}
}

var ccH=11;			
var ttH=0;			

function ocultaHead(){
	
	if(ccH>1) {
			ttH-=ccH;			
			document.getElementById("head").style.top=ttH+"px";
			document.getElementById("principal").style.top=65+ttH+"px";
			document.getElementById("menu").style.top=65+ttH+"px";

			ccH--;


			setTimeout("ocultaHead()",2);
	
	}else{

			ccH=11;
			
			ttH=-65;
			document.getElementById("principal").style.height="590px";
			document.getElementById("menu").style.height="590px";
	}
}

function muestraHead(){
	if(ccH>1) {
			ttH+=ccH;			
			document.getElementById("head").style.top=ttH+"px";
			document.getElementById("principal").style.top=65+ttH+"px";
			document.getElementById("menu").style.top=65+ttH+"px";

			ccH--;


			setTimeout("muestraHead()",2);
	
	}else{

			ccH=11;
			
			ttH=0;
			
	}
}

function myprint(marco) {
	marco.focus();
	marco.print();
}
