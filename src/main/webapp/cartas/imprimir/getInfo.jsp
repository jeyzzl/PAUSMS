<%@ include file= "../../con_enoc.jsp" %>
<%@ include file="../../idioma.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<jsp:useBean id="alumno" scope="page" class="aca.alumno.AlumPersonal"/>
<jsp:useBean id="AlumUtil" scope="page" class="aca.alumno.AlumUtil"/>

<%
	String codigoPersonal 	= request.getParameter("codigoPersonal");
	String constanciaId 	= request.getParameter("constanciaId");
	
	alumno = AlumUtil.mapeaRegId(conEnoc, codigoPersonal);
	
%>	
	
	<div class="alert alert-info">
		<p>
			<strong>Alumno:</strong> <%=codigoPersonal %> | <%=alumno.getNombre() %> <%=alumno.getApellidoPaterno() %> <%=alumno.getApellidoMaterno() %>
		</p>
		<p>
			<strong><spring:message code="aca.Nacionalidad"/>:</strong> <%= aca.catalogo.PaisUtil.getNacionalidad(conEnoc,alumno.getNacionalidad()) %> &nbsp;&nbsp;&nbsp; <strong>Extranjero:</strong> <%=!alumno.getPaisId().equals("91")?"SI":"NO" %>
		</p>
		<p>
			<strong><spring:message code='aca.Genero'/>:</strong> <%if (alumno.getSexo().equals("M")) out.print("Masculino");else out.print("Femenino");%>
		</p>
		<p>
			<strong>Fecha Nacimiento:</strong> <%=alumno.getFNacimiento() %>
		</p>
		<p>
			<strong><spring:message code="aca.Estado"/>:</strong> <%if (aca.alumno.AlumUtil.esInscrito(conEnoc, codigoPersonal)){ out.println("INSCRITO");}else{ out.println("No Inscrito");}%>
		</p>
	</div>
	
	
<%	
	ArrayList<aca.alumno.AlumConstanciaImpresion> fechas = aca.alumno.AlumConstanciaImpresionUtil.getListUsuario(conEnoc, constanciaId, codigoPersonal, "ORDER BY FECHA_IMPRESION DESC");
	
	if(fechas.size()>0){
%>
	<div class="alert">
		<p>
			<h4>Fechas de Impresión de este documento para este alumno</h4>
		</p>
		
		<ul style="margin-bottom:0;">
			<%for(aca.alumno.AlumConstanciaImpresion fecha : fechas){ %>
				<li><%=fecha.getFechaImpresion() %></li>
			<%}%>
		</ul>	
	</div>
<%
	}else{ 
%>
		<div class="alert">
			<h4>Este documento nunca ha sido impreso para este alumno</h4>
		</div>
<%
	} 
%>


<%@ include file= "../../cierra_enoc2.jsf" %>