<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="alumno" scope="page" class="aca.alumno.AlumPersonal"/>
<jsp:useBean id="plan" scope="page" class="aca.alumno.AlumPlan"/>
<jsp:useBean id="alumnoUtil" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="archivo" scope="page" class="aca.archivo.ArchivoUtil"/>
<jsp:useBean id="ArchDocAlumnoUtil" scope="page" class="aca.archivo.ArchDocAlumnoUtil"/>
<jsp:useBean id="ArchEntregaU" scope="page" class="aca.archivo.ArchEntregaUtil"/>
<jsp:useBean id="ArchEntrega" scope="page" class="aca.archivo.ArchEntrega"/>
<jsp:useBean id="ArchDocAlum" scope="page" class="aca.archivo.ArchDocAlumno"/>

<%
	String codigoAlumno	= (String)session.getAttribute("codigoAlumno");
	String accion 		= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String mensaje		= "x";
	String color		= "";
	
	alumno = alumnoUtil.mapeaRegId(conEnoc,codigoAlumno);
	
	// ArrayList que trae los datos (la ubicacion 8 corresponde a los documentos entregados)
	ArrayList<aca.archivo.ArchDocAlumno> lisDocumento = ArchDocAlumnoUtil.getListAlumno(conEnoc, codigoAlumno,"8","ORDER BY ENOC.DOC_ORDEN(iddocumento)");
	
	if(accion.equals("1")){
		String documentosId = "-";
		for (int i=0; i < lisDocumento.size(); i++){
			documentosId += request.getParameter("check"+i)==null?"":request.getParameter("check"+i)+"-";
			ArchDocAlumnoUtil.updateUbicacion(conEnoc, codigoAlumno, request.getParameter("check"+i), "8");
		}
		
		ArchEntrega.setCodigoPersonal(codigoAlumno);
		ArchEntrega.setFolio(ArchEntregaU.maximoFolio(conEnoc, codigoAlumno));
		ArchEntrega.setFecha(aca.util.Fecha.getHoy());
		ArchEntrega.setDocumentos(documentosId);		
		
		if(ArchEntregaU.insertReg(conEnoc, ArchEntrega)){
			mensaje = "Guardado";
			color = "success";
		}else{
			mensaje = "No guardo";
			color = "danger";
		}
		
		lisDocumento = ArchDocAlumnoUtil.getListAlumno(conEnoc, codigoAlumno,"8","ORDER BY ENOC.DOC_ORDEN(iddocumento)");
	}

%>
<div class="container-fluid">
	<h2>Entrega de documentos <small class="text-muted fs-4">( <%=codigoAlumno%> - <%=alumno.getNombre()%> <%=alumno.getApellidoPaterno()%> <%=alumno.getApellidoMaterno()%> )</small></h2>
	<form id="forma" name="forma" action="retirar?Accion=1" method='post'>
		<div class="alert alert-info d-flex align-items-center">		
			<a class="btn btn-primary" href="documentos"><i class="fas fa-arrow-left"></i></a>
		</div>
<% 		if(!mensaje.equals("x")){%>
		<div class="alert alert-<%=color%>"><%=mensaje%></div>
<% 		}%>
		<table class="table table-sm table-condensed">
			<tr>
				<td colspan="8" style="font-size:14px">
				  <b>Estudia:</b> <%=alumnoUtil.getCarrera(conEnoc,codigoAlumno)%> [<%=plan.getPlanId()%>] - &nbsp; &nbsp; 
				  <b><spring:message code="aca.Estado"/>:</b> <%=archivo.autorizaAlumno(conEnoc,codigoAlumno)%>
				</td>
			</tr>	  	
		  	<tr>
				<td></td>
				<td align="center"><strong>N°</strong></td>
				<td><strong>Documento</strong></td>
				<td><strong>Status</strong></td>
				<td><strong>Ubicación</strong></td>
		  	</tr>	
		  	<%		// Aplicacion para acomodar los datos
	for (int i=0; i< lisDocumento.size(); i++){
		aca.archivo.ArchDocAlumno doc = (aca.archivo.ArchDocAlumno) lisDocumento.get(i);
		
		String ubicacion = "-";
		if(doc.getUbicacion().equals("1")){
			ubicacion = "Archivo";
		} else if(doc.getUbicacion().equals("2")){
			ubicacion = "Equivalencias";
		}else if(doc.getUbicacion().equals("3")){
			ubicacion = "Convalidaciones";
		}else if(doc.getUbicacion().equals("4")){
			ubicacion = "Certificación";
		}else if(doc.getUbicacion().equals("5")){
			ubicacion = "Titulación";
		}else if(doc.getUbicacion().equals("6")){
			ubicacion = "Prestamo";
		}else if(doc.getUbicacion().equals("7")){
			ubicacion = "Tramite";
		}else if(doc.getUbicacion().equals("8")){
			ubicacion = "Entregado";		
		}else if(doc.getUbicacion().equals("9")){
			ubicacion = "Enviado";
		}else if(doc.getUbicacion().equals("10")){
			ubicacion = "Validaciones";
		}		  	
%> 							
		  	<tr class="tr2">
		    	<td align="center">
					<input name="check<%=i%>" type="checkbox" value="<%=doc.getIdDocumento()%>">
			    </td>
				<td align="center"><%=doc.getIdDocumento()%></td>
				<td><%=aca.archivo.ArchDocumentosUtil.getDescripcion(conEnoc, doc.getIdDocumento())%></td>
				<td><%=aca.archivo.ArchStatusUtil.getDescripcion(conEnoc, doc.getIdStatus())%></td>
				<td><%=ubicacion%></td>
		  	</tr>
<%				}	%>
		</table>
		<input class="btn btn-primary" type="submit" value="Guardar">
	</form>
</div>
<%@ include file= "../../cierra_enoc.jsp" %>