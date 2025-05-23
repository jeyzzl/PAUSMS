<%@page errorPage="paginaerrorReloj.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean id="colorAlum" scope="page" class="aca.portal.Alumno"/>
<%@page import="aca.util.Fecha"%>

<html>
<head>
<!-- 	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> -->
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Hora del Servidor</title>
</head>
<!-- ----------------------------------------RELOJ-------------------------------------------------------------------- -->
<%
	String colorReloj = (String)session.getAttribute("colorReloj");
	String color1 = colorAlum.modificarColor(colorReloj, 80);
	String color2 = colorAlum.modificarColor(colorReloj, -70);

	String coloLetra = "#FFF; text-shadow: 0 1px 0 black";
	if(colorAlum.colorNegroLetra(colorReloj)){
		coloLetra = "#000";
	}
	
aca.util.Fecha f 		= new aca.util.Fecha();
String sFecha 			= f.getFecha("2");
String tmp="";
int diag=0;
for(int i=0;i<sFecha.length();i++){
	if(sFecha.substring(i,i+1).equals("/") && diag==0){ tmp += " de";diag++;}
	else if(sFecha.substring(i,i+1).equals("/") && diag==1){tmp += " de";diag++;}
	else tmp += sFecha.substring(i,i+1);
}
sFecha=tmp;

String fecha 			= "";

if(sFecha.indexOf(",")>=0){
	String [] split = sFecha.split(",");
	if(split.length>=2)fecha=split[1];
}

String horaActual 	= Fecha.getHora();
	
String minActual 	= Fecha.getMinutos();
String segActual 	= Fecha.getSegundos();

String AMPM = Fecha.getAMPM()==0 ? "AM" : "PM";

if(horaActual.equals("0")) horaActual = "12";

String tiempoActual = horaActual+":"+minActual+":"+segActual;
%>

<script type="text/javascript">
// Recogemos la fecha del servidor.
// Pasamos la fecha a javascript
var fecha_js = new Date("January 01, 2000 " + "<%=tiempoActual%>");
var segundos = fecha_js.getSeconds();
var hora = fecha_js.getHours(); 
var minutos = fecha_js.getMinutes();
var recargar = <%=request.getParameter("Accion")%>;

function mostrar() { 
	segundos++;
   // Ha pasado un segundo
  
   if(((segundos==30) || ((hora==12 && minutos==0) && segundos==1)) && recargar!=1){
	   document.location.href='reloj.jsp?Accion=1';
	   //parent.relojFrame.location.reload()
	   /*
	   var scFecha	= new Date()
	   var scHora	= scFecha.getHours()
	   var scMin	= scFecha.getMinutes()
	   var scSeg	= scFecha.getSeconds()
	   
	   
	   if(scMin != minutos){
		   hora 	= scHora;
		   minutos 	= scMin;
		   segundos	= scSeg;
	   }*/
   }
   
   recargar = 2;
   
   if(segundos == 60) {
	 segundos = 0;
     minutos++;
     /*if(minutos%5 == 0){
 	 	parent.relojFrame.location.reload()
     }*/
     if(minutos == 60) {
     	minutos = 0;
     	hora++;
     	if(hora==13) {
  	   		hora = 1;
     	}
     }
   }
   
	var strHora 	= new String (hora);
	var strMinutos  = new String (minutos);
	var strSegundos  = new String (segundos);
	
   if (strHora.length==1){ 
   		strHora="0"+hora;
   }
	
   
   if (strMinutos.length==1){  
   		strMinutos="0"+minutos;
   }

   if (strSegundos.length==1){  
   		strSegundos="0"+segundos; 
   }

   document.getElementById("hora").value =  " "  + strHora + ":" + strMinutos + ":" + strSegundos; 
    
   window.setTimeout("mostrar()",1000); 
}
</script>
<style type="text/css">
	.reloj{
		position: absolute;
		top: 0px;
		right: 27px;
		z-index: 200000;
	}
	.fecha{
		position: absolute;
		top: 25px;
		right: 5px;
		z-index: 200000000;
	}
	.fondoReloj{
		position: absolute;
		top: 10px;
		right: 10px;
		width:140px; 
		height:42px;
		z-index: 200;
		
		background-color: <%=colorReloj%>;
		
		
		background: -webkit-gradient(linear, left top, left bottom, from(<%=color1%>), to(<%=colorReloj%>));
		background: -webkit-linear-gradient(<%=color1%>, <%=colorReloj%> 50%);
		background: -moz-linear-gradient(<%=color1%>, <%=colorReloj%> 50%);
		background: -o-linear-gradient(<%=color1%>, <%=colorReloj%> 50%);
				
				
		border: 5px solid <%=color2%>;
		border-radius:1em;
	}
	.palito1{
		position: absolute;
		top: -2px;
		right: 120px;
		z-index: 2000;
		width: 5px;
		height: 15px;
		background: <%=color2%>;
	}
	.palito2{
		position: absolute;
		top: -2px;
		right: 45px;
		z-index: 2000;
		width: 5px;
		height: 15px;
		background: <%=color2%>;
	}
	.AMPM{
		position: absolute;
		top: 25px;
		right: 22px;
		z-index: 2000000000000000;
	}
	a {text-decoration: none;} 
	a:hover {text-decoration: none;} 
</style>
<body onLoad="window.setTimeout('mostrar()', 0);">
	<div class="relojEfecto">
		<div id="effect" class="relojEfecto" >
			<div class="palito1"></div>
			<div class="palito2"></div>
			<div class="AMPM">
				<font face="verdana" style="color:<%=coloLetra%>;" size="1px"><%=AMPM%></font>
			</div>
			<div class="fondoReloj" >	
				<div class="reloj">
					<input type='text' id='hora' size=9 style="cursor: hand;cursor: pointer;text-align:right;background-color:transparent; color:<%=coloLetra%>; border-width:0; font-size:25px; font-family:Arial;" readonly/> 	
				</div>
				 <div class="fecha">
				 	<table style="width:135px" align="right">
				 		<tr>
				 			<td style="font-size:10px; color:<%=coloLetra%>;" align="right"><font face="arial"><%=fecha %></font></td>
				 		</tr>
				 	</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>