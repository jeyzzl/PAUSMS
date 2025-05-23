<%@page import="java.util.List"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.carga.spring.CargaEnLinea"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<script type="text/javascript">

	function checaCuantasLetras(i, txtarea){
		var txt=txtarea.value;
		var n=txt.length;
		if (n>i)
			txtarea.value=txt.substring(0,i);
	}
	
	function Grabar(){
		var fInicio =  document.forma.fInicio.value;
		var fFinal 	= document.forma.fFinal.value;
		if(document.forma.Nombre.value!="" && document.forma.Descripcion!="" && fInicio!="" && fFinal!=""){
			var arrIni = fInicio.split('/');
			var fIni = new Date(arrIni[1]+"/"+arrIni[0]+"/"+arrIni[2]);
			
			var arrFin = fFinal.split('/');
			var fFin = new Date(arrFin[1]+"/"+arrFin[0]+"/"+arrFin[2]);
			if(fIni=='Invalid Date' || fFin=='Invalid Date'){
				alert("<spring:message code="aca.JSVerificarFechas"/>");
			}
			else{
				if(fIni<fFin || fInicio==fFinal){
					document.forma.submit();	
				}
				else{
					alert("<spring:message code="cargaGrupo.enLinea.JSMensaje"/>");
				}				
			}
		}else{
			alert("<spring:message code='aca.JSCompletar'/>");
		}
	}
	
	function refrescar(){
		document.forma.submit();
	}
</script>
<%
	// Declaracion de variables
	String cargaId				= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
	String mensaje				= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");

	List<Carga> lisCargas		= (List<Carga>)request.getAttribute("lisCargas");
	CargaEnLinea cargaEnLinea	= (CargaEnLinea)request.getAttribute("carga");
%>
<div class="container-fluid">
	<h1><spring:message code="cargasGrupo.enLinea.AnadirCargas"/></h1>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="carga"><i class="fas fa-arrow-left"></i></a>
	</div>
<%	if (!mensaje.equals("-")){%>			
	<div class="alert alert-success"><%=mensaje%></div>
<%	}%>	
	<form action="grabar" method="post" name="forma" target="_self">	
	<table class="table table-condensed" style="width:40%" >
	<tr> 
		<td><strong><spring:message code="aca.Carga"/>:</strong></td>
		<td>
			<select id="CargaId" name="CargaId" onchange="javascript:refrescar()" style="width:350px">
	<%		for (Carga carga : lisCargas){						
	%>
				<option <%=carga.getCargaId().equals(cargaId)?"Selected":""%> value="<%=carga.getCargaId()%>"><%=carga.getCargaId()%> - <%=carga.getNombreCarga()%></option>
	<%		}%>          	    
	   	    </select>
	   </td>
	</tr>
	<tr> 
		<td><strong><spring:message code="aca.Nombre"/></strong></td>
		<td><input id="Nombre" name="Nombre" type="text" class="text" value="<%=cargaEnLinea.getNombre()%>" size="45" maxlength="40"></td>
	</tr>
	<tr> 
		<td><strong><spring:message code="aca.Descripcion"/>:</strong></td>
		<td>
	  		<textarea id="Descripcion" name="Descripcion" cols="50" rows="5" onkeyup='checaCuantasLetras(150, this);'><%=cargaEnLinea.getDescripcion() %></textarea>
	  	</td>
	</tr>
	<tr> 
		<td><strong><spring:message code="aca.Inicio"/> <spring:message code="aca.Fecha"/>:</strong></td>
		<td>
	  		<input type="text" class="text" data-date-format="dd/mm/yyyy" id="fInicio" name="fInicio" value="<%=cargaEnLinea.getfInicio()%>" onfocus="focusFecha(this);" size="13" maxlength="10">
        	<spring:message code="aca.DDMMAAAA"/><br>
		</td>
	</tr>
	<tr> 
		<td><strong><spring:message code="aca.Final"/> <spring:message code="aca.Fecha"/>:</strong></td>
		<td>
	  		<input type="text" class="text" data-date-format="dd/mm/yyyy" id="fFinal" name="fFinal" value="<%=cargaEnLinea.getfFinal()%>" onfocus="focusFecha(this);" size="13" maxlength="10">
          	<spring:message code="aca.DDMMAAAA"/><br>
		</td>
	</tr>
	<tr> 
		<td><strong><spring:message code="aca.Status"/>:</strong></td>
		<td>
	  		<select id="Estado" name="Estado" class="input input-small">
	  			<option value="A"><spring:message code="aca.Activa"/></option>
	  			<option value="I" <%if(cargaEnLinea.getEstado().equals("I"))out.print("selected"); %>><spring:message code="aca.Inactiva"/></option>
	  		</select>
	  	</td>
	</tr>		
	<tr> 
		<td><strong>Enrollment Proof:</strong></td>
		<td>
	  		<select id="Carta" name="Carta" class="input input-small">
	  			<option value="S">Yes</option>
	  			<option value="N" <%if(cargaEnLinea.getCarta().equals("N"))out.print("selected"); %>>No</option>
	  		</select>
	  	</td>
	</tr>		
	</table>	
	<div class="alert alert-info">	
	    <a class="btn btn-primary" href="javascript:Grabar()"><spring:message code="aca.Guardar"/></a>
	</div>	
	</form>
</div>

<script>
	jQuery('#fInicio').datepicker();
	jQuery('#fFinal').datepicker();
</script>