<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import= "aca.alumno.AlumPersonal"%>
<%@ page import= "aca.alumno.AlumUtil"%>
<jsp:useBean id="bOp" scope="page" class="aca.portal.Alumno"/>

<%
	String origen			= request.getParameter("Origen");
	if (origen==null)origen="X";
	String sCarpeta 		= sCarpetab;
%>
<script type="text/javascript">

	function initRequest() {
		if (window.XMLHttpRequest) {
			return new XMLHttpRequest();
		} else if (window.ActiveXObject) {
			isIE = true;
			return new ActiveXObject("Microsoft.XMLHTTP");
		}
	}

	function parseMessages(responseXML) {
		document.getElementById("tablaAlumnos").innerHTML = responseXML;
		
	}

	function Consultar(){
		document.frmalumno.Accion.value="1";
		document.frmalumno.submit();
	}
	
	function BuscarNombre( ){
		
		if (document.frmnombre.Nombre.value!="" || document.frmnombre.Paterno.value!="" || document.frmnombre.Materno.value!=""){
			document.getElementById("tablaAlumnos").innerHTML = "<table width=\"100%\"><tr><td align=\"center\">Espere un momento, se estan cargando los datos</td></tr></table>";
			var url = "../../buscar?Accion=1&Nombre="+encodeURIComponent(document.frmnombre.Nombre.value)+"&Paterno="+escape(document.frmnombre.Paterno.value)+"&Materno="+escape(document.frmnombre.Materno.value)+"&Origen=<%=origen %>&carpeta=<%=sCarpeta %>";
			
			var req = initRequest();
			req.onreadystatechange = function() {
				if(req.readyState == 4){
					
					if(req.status==404) {
						alert("esta pagina no existe");
					}
					if(req.status == 200){
						
						parseMessages(req.responseText);
						
					}else if (req.status == 204){
						alert("Ocurrió un error al solicitar la informacion");
						
					}
				}
			};
			req.open("get", url, true);
			req.send(null);
			
		}else{
			alert(" Es necesario por lo menos un dato..¡¡");
			document.frmnombre.Nombre.focus();
		}
	}
	
	function BuscarCodigo( ){
		if(document.frmcodigo.CodigoPersonal.value!=""){
			//document.frmcodigo.Accion.value="2";
			//document.frmcodigo.submit();
			
			var url = "../../buscar?Accion=2&CodigoPersonal="+encodeURIComponent(document.frmcodigo.CodigoPersonal.value)+"&Origen=<%=origen %>&carpeta=<%=sCarpetab %>";
			
			var req = initRequest();
			req.onreadystatechange = function() {
				//alert("onreadystatechange");
				if(req.readyState == 4){
					//alert("4");
					if(req.status==404) {
						alert("esta pagina no existe");
					}
					if(req.status == 200){
						//alert("el status es 200");
						//parseMessages(req.responseText);
						//if(req.responseText.lenght <= 7)
						if(req.responseText.charAt(0) === ".")
							document.location.href = req.responseText;
						else
							parseMessages(req.responseText);
						
					}else if (req.status == 204){
						alert("Ocurrió un error al solicitar la informacion");
						
					}
				}
			};
			req.open("GET", url, true);
			req.send(null);
		}else{
			alert("Ingrese el Código..! ");
			document.frmcodigo.CodigoPersonal.focus();
		}
	}
	
	function SubirCodigo( CodigoPersonal ){
			var url = "../../buscar?Accion=3&CodigoPersonal="+CodigoPersonal+"&Origen=<%=origen %>&carpeta=<%=sCarpeta %>";
			
			var req = initRequest();
			req.onreadystatechange = function() {
				if(req.readyState == 4){
					
					if(req.status==404) {
						alert("esta pagina no existe");
					}
					if(req.status == 200){
						
						
						//parseMessages("'redirect"+req.responseText+"'");
						document.location.href=req.responseText;
						
					}else if (req.status == 204){
						alert("Ocurrió un error al solicitar la informacion");
						
					}
				}
			};
			req.open("GET", url, true);
			req.send(null);
	  		//document.location="buscar?Accion=3&CodigoPersonal="+CodigoPersonal+"&Origen=<%=origen%>&carpeta=<%=sCarpeta%>";
	}	
</script>

<table style='margin:0 auto;'>
<%if(!origen.equals("X")){%>
  <tr>
    <td align="CENTER"><a href="../../<%=origen%>&carpeta=<%=sCarpeta%>"><strong>R e g r e s a r</strong></a></td>
  </tr>
<%}%>
  <tr>
    <th align="CENTER">B&uacute;squeda por Nombre</th>
  </tr>  
  <form name="frmnombre" method="get" action="BuscarAlumno">
  <input name="Accion" type="hidden">
  <tr align='CENTER'>
    <td>Nom.  
			<input type="Text" class="text" name="Nombre" size="3" maxlength="15">
		Pat. 
			<input type="Text" class="text" name="Paterno" size="3" maxlength="15">
		Mat.
			<input type="Text" class="text" name="Materno" size="3" maxlength="15">
	</td>
  </tr>
  <tr align='CENTER'>
    <td><a href="javascript:BuscarNombre()">Buscar</a></td>
  </tr>
</form>
  <tr>
    <th align="CENTER">B&uacute;squeda por C&oacute;digo</th>
  </tr>
<form name="frmcodigo" method="POST" action="javascript:BuscarCodigo()">
<input name="Accion" type="hidden">
  <tr align='CENTER'> 		
    <td> C&oacute;digo: 
      <input type="Text" class="text" name="CodigoPersonal" id="CodigoPersonal" size="8" maxlength="7">
	</td>
  </tr>
  <tr align='CENTER'>
    <td><a href="javascript:BuscarCodigo()">Buscar</a></td>
  </tr>
</form>
</table>
<br>
<p>
<div id="tablaAlumnos"></div>
</p>

<!-- fin de estructura -->
