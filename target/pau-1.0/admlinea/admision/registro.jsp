<%@page import="aca.admision.spring.AdmSolicitud"%>
<%@page import="aca.admision.spring.AdmBanco"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<head>
		
</head>
<%	
	String folio 				= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
	String accion 				= request.getParameter("Accion")==null? "0" : request.getParameter("Accion");
	boolean acceso 				= (boolean) request.getAttribute("acceso");
	boolean tieneMatricula 		= (boolean) request.getAttribute("tieneMatricula");
	AdmSolicitud admSolicitud 	= (AdmSolicitud) request.getAttribute("admSolicitud");	
	AdmBanco AdmBanco			= (AdmBanco) request.getAttribute("admBanco");

	String nombreNacionalidad 		= (String)request.getAttribute("nombreNacionalidad");
	String nombrePais				= (String)request.getAttribute("nombrePais");
	String nombreEstado				= (String)request.getAttribute("nombreEstado");
	String nombreCiudad				= (String)request.getAttribute("nombreCiudad");
	String nombreCultural			= (String)request.getAttribute("nombreCultural");
	String nombreRegion				= (String)request.getAttribute("nombreRegion");
	String nombreResPais			= (String)request.getAttribute("nombreResPais");
	String nombreResEstado			= (String)request.getAttribute("nombreResEstado");
	String nombreResCiudad			= (String)request.getAttribute("nombreResCiudad");
	String nombreReligion			= (String)request.getAttribute("nombreReligion");
	String nombreAcomodo			= (String)request.getAttribute("nombreAcomodo");
%>
<body>
<div class="container-fluid">
	<h2><%=admSolicitud.getNombre()+" "+(admSolicitud.getApellidoMaterno()==null?"":admSolicitud.getApellidoMaterno())+" "+admSolicitud.getApellidoPaterno() %> Personal Details	</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="mostrarProceso?Folio=<%=folio %>"><i class="fas fa-arrow-left"></i></a>
		<%	if(acceso){ %>
			<tr><td><a class="btn btn-primary" href="cambia?Folio=<%=folio%>">Update Password</a></td></tr>
	<% 	}%>
	</div>
	<form id="forma" name="forma" action="registro?Folio=<%=folio %>&Accion=1" method="post">
		<table class="table table-condensed">
			<tr>
				<td>&nbsp;<b><spring:message code="aca.Nombre"/>:</b></td>
				<td><input <%=!acceso ? "disabled" : ""%> type="text" id="Nombre" name="Nombre" value="<%=admSolicitud.getNombre() %>" size="30" maxlength="70" class="form-control" style="width: 16rem" /></td>
			</tr>
			<tr>
				<td>&nbsp;<b>Surname:</b></td>
				<td><input <%=!acceso ? "disabled" : ""%> type="text" id="Paterno" name="Paterno" value="<%=admSolicitud.getApellidoPaterno() %>" size="30" maxlength="70" class="form-control" style="width: 16rem" /></td>
			</tr>
			<tr>
				<td>&nbsp;<b>Maiden Name:</b></td>
				<td><input <%=!acceso ? "disabled" : ""%> type="text" id="Materno" name="Materno" value="<%=admSolicitud.getApellidoMaterno()==null ? "" :admSolicitud.getApellidoMaterno() %>" size="30" maxlength="70" class="form-control" style="width: 16rem" /></td>
			</tr>
			<tr>
				<td>&nbsp;<b><spring:message code="aca.Genero"/>:</b></td>
				<td>
					<input <%=!acceso ? "disabled" : ""%> type="radio" id="Genero" name="Genero" value="M" size="30" maxlength="70"<%=admSolicitud.getGenero().equals("M")?" Checked":"" %> /> Male&nbsp;
					<input <%=!acceso ? "disabled" : ""%> type="radio" id="Genero" name="Genero" value="F" size="30" maxlength="70"<%=admSolicitud.getGenero().equals("F")?" Checked":"" %> /> Female
				</td>
			</tr>
			<tr>
				<td>&nbsp;<b><spring:message code="aca.Religion"/>:</b></td>
				<td><input disabled type="text" id="religion" name="religion" value="<%=nombreReligion%>" maxlength="20"  class="form-control" style="width: 16rem" /></td>
			</tr>
			<tr>
				<td>&nbsp;<b><spring:message code="aca.Acomodo"/>:</b></td>
				<td><input disabled type="text" id="acomodo" name="acomodo" value="<%=nombreAcomodo%>" maxlength="20" class="form-control" style="width: 16rem" /></td>
			</tr>	
			<tr>
				<td colspan="2" class="text-primary"><b>Provenance Data</b></td>
			</tr>
			<tr>
				<td>&nbsp;<b><spring:message code="aca.Nacionalidad"/>:</b></td>
				<td><input disabled type="text" id="nacionalidad" name="nacionalidad" value="<%=nombreNacionalidad%>" maxlength="20" class="form-control" style="width: 16rem" /></td>
			</tr>
			<tr>
				<td>&nbsp;<b><spring:message code="aca.Pais"/>:</b></td>
				<td><input disabled type="text" id="pais" name="pais" value="<%=nombrePais%>" maxlength="20" class="form-control" style="width: 16rem" /></td>
			</tr>
			<tr>
				<td>&nbsp;<b><spring:message code="aca.Estado"/>:</b></td>
				<td><input disabled type="text" id="estado" name="estado" value="<%=nombreEstado%>" maxlength="20" class="form-control" style="width: 16rem" /></td>
			</tr>
			<tr>
				<td>&nbsp;<b><spring:message code="aca.Ciudad"/>:</b></td>
				<td><input disabled type="text" id="ciudad" name="ciudad" value="<%=nombreCiudad%>" maxlength="20" class="form-control" style="width: 16rem" /></td>
			</tr>
			<tr>
				<td>&nbsp;<b><spring:message code="aca.GrupoCultural"/>:</b></td>
				<td><input disabled type="text" id="cultural" name="cultural" value="<%=nombreCultural%>" maxlength="20" class="form-control" style="width: 16rem" /></td>
			</tr>
			<tr>
				<td>&nbsp;<b><spring:message code="aca.GrupoRegional"/>:</b></td>
				<td><input disabled type="text" id="region" name="region" value="<%=nombreRegion%>" maxlength="20" class="form-control" style="width: 16rem"  /></td>
			</tr>
			<tr>
				<td colspan="2" class="text-primary"><b>Residence Data</b></td>
			</tr>
			<tr>
				<td>&nbsp;<b><spring:message code="aca.Pais"/>:</b></td>
				<td><input disabled type="text" id="resPais" name="resPais" value="<%=nombreResPais%>" maxlength="20" class="form-control" style="width: 16rem"  /></td>
			</tr>
			<tr>
				<td>&nbsp;<b><spring:message code="aca.Estado"/>:</b></td>
				<td><input disabled type="text" id="resEstado" name="resEstado" value="<%=nombreResEstado%>" maxlength="20" class="form-control" style="width: 16rem" /></td>
			</tr>
			<tr>
				<td>&nbsp;<b><spring:message code="aca.Ciudad"/>:</b></td>
				<td><input disabled type="text" id="resCiudad" name="resCiudad" value="<%=nombreResCiudad%>" maxlength="20" class="form-control" style="width: 16rem" /></td>
			</tr>
			<tr>
				<td colspan="2" class="text-primary"><b>Contanct Info</b></td>
			</tr>
			<tr>
				<td>&nbsp;<b>Email:</b></td>
				<td><input <%=!acceso ? "disabled" : ""%> type="text" id="Email" name="Email" value="<%=admSolicitud.getEmail() %>" size="30" maxlength="70" class="form-control" style="width: 16rem"  /></td>
			</tr>
			<tr>
				<td>&nbsp;<b>Phone:</b></td>
				<td><input <%=!acceso ? "disabled" : ""%> type="text" id="Telefono" name="Telefono" value="<%=admSolicitud.getTelefono()==null?"":admSolicitud.getTelefono() %>" size="30" maxlength="10" class="form-control" style="width: 16rem" /></td>
			</tr>
			<tr>
				<td>&nbsp;<b><spring:message code="aca.Usuario"/>:</b></td>
				<td><input <%=!acceso ? "disabled" : ""%> type="text" id="usuario" name="usuario" value="<%=admSolicitud.getUsuario()==null ? "" : admSolicitud.getUsuario() %>" maxlength="20" class="form-control" style="width: 16rem" /></td>
			</tr>
			<tr>
				<td>&nbsp;<b><spring:message code="aca.Status"/>:</b></td>
     			<td> 
  	  			<% String estado = admSolicitud.getEstado()==null?"1":admSolicitud.getEstado(); %>
       				<select id="estado" name="estado" <%=tieneMatricula||!acceso ? "disabled" : ""%> class="form-select" style="width: 16rem">
         				<option value="1" <%=estado.equals("1")?"Selected":"" %>><spring:message code="aca.Cuenta"/></option>
         				<option value="2" <%=estado.equals("2")?"Selected":"" %>><spring:message code="aca.Solicitud"/></option>
         				<option value="3" <%=estado.equals("3")?"Selected":"" %>><spring:message code="aca.Documento"/></option>
         				<option disabled value="4" <%=estado.equals("4")||estado.equals("5")?"Selected":"" %>><spring:message code="aca.CartaDeAdmision"/></option>
       				</select>
     			</td>
			</tr>
		</table>
	</form>
<%	if(acceso){ %>
		<table>
			<tr>
		    	<td colspan="3" align="center">
		    		<input type="button" class="btn btn-primary my-3" value="Save" onclick="document.forma.submit();">
				</td>
		  	</tr>
		</table>
<%	} %>
</div>
</body>
<%	if(accion.equals("1")){ %>
		<table style="margin: 0 auto;"><tr><td><spring:message code="aca.LosDatosSeModificaronCorrectamente"/></td></tr></table>
<%-- 		<meta http-equiv="refresh" content="0.5;url=mostrarProceso?Folio=<%=folio %>" /> --%>
<%	} %>