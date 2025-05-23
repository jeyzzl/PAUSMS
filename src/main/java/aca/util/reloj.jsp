<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="aca.util.Fecha"%>
<jsp:useBean id="fecha" scope="page" class="aca.util.Fecha"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Hora del Servidor</title>

<%
String horaActual 	= Fecha.getHora();
String minActual 	= Fecha.getMinutos();
String segActual 	= Fecha.getSegundos();

String AMPM = Fecha.getAMPM()==0 ? "AM" : "PM";

if(AMPM.equals("PM")&&Integer.parseInt(horaActual)!=12) horaActual = (Integer.parseInt(horaActual)+12)+"";

String tiempoActual = horaActual+":"+minActual+":"+segActual;

System.out.println(tiempoActual);
%>

<script type="text/javascript">
// Recogemos la fecha del servidor.
// Pasamos la fecha a javascript
var fecha_js = new Date("January 01, 2000 " + "<%=tiempoActual%>");
var segundos = fecha_js.getSeconds();
var hora = fecha_js.getHours(); 
var minutos = fecha_js.getMinutes();


function mostrar() { 
	segundos++;
   // Ha pasado un segundo
  
   
   if (segundos == 60) {
	 segundos = 0;
     minutos++;
     if (minutos == 60) {
       minutos = 0;
       hora++;
       if (hora == 24) {
         hora = 0;
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
</head>
<body onLoad="window.setTimeout('mostrar()',1000);">

<h1>Hora del Servidor</h1>


<form id="formulario"> 
<label for="hora">En estos momentos son las...</label> 
<input type='text' id='hora' size=10/> 
</form>
</body>
</html>