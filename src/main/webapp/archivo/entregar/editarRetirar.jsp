<%@ page import="java.util.HashMap"%>

<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "../../conectadbp.jsp" %>
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
	String codigoAlumno		= (String)session.getAttribute("codigoAlumno");
	String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String folio			= request.getParameter("Folio");
	String mensaje			= "x";
	String color			= "";	
	alumno 					= alumnoUtil.mapeaRegId(conEnoc,codigoAlumno);
	
	// ArrayList que trae los datos
	ArrayList<aca.archivo.ArchDocAlumno> lisDocumento = ArchDocAlumnoUtil.getListAll(conEnoc, codigoAlumno, "ORDER BY ENOC.DOC_ORDEN(da.iddocumento)");
	
	if(accion.equals("1")){
		String documentos 	= "-";
		String documentoChek	= "";
		for (int i=0; i < lisDocumento.size(); i++){
			aca.archivo.ArchDocAlumno alumDoc = (aca.archivo.ArchDocAlumno)lisDocumento.get(i);  
			documentoChek = request.getParameter("check"+i)==null?"*":request.getParameter("check"+i)+"-";
			
			if (!documentoChek.equals("*")){
				documentos += documentoChek;
				ArchDocAlumnoUtil.updateUbicacion(conEnoc, codigoAlumno, alumDoc.getIdDocumento(), "8");
			}else if ( alumDoc.getUbicacion().equals("8") ){
				ArchDocAlumnoUtil.updateUbicacion(conEnoc, codigoAlumno, alumDoc.getIdDocumento(), "1");
			}
		}
		ArchEntrega = ArchEntregaU.getEntrega(conEnoc, codigoAlumno, folio);
		ArchEntrega.setDocumentos(documentos);	
		
		if(ArchEntregaU.updateReg(conEnoc, ArchEntrega)){
			mensaje = "Guardado";
			color = "success";
		}else{
			mensaje = "No guardo";
			color = "danger";
		}
	}	
	HashMap<String, String> mapaDoc	= ArchEntregaU.getDocId(conEnoc, codigoAlumno, folio);
%>
<div class="container-fluid">
	<h2>Entrega de documentos <small class="text-muted fs-5">( <%=codigoAlumno%> - <%=alumno.getNombre()%> <%=alumno.getApellidoPaterno()%> <%=alumno.getApellidoMaterno()%> )</small></h2>
	<form id="forma" name="forma" action="editarRetirar?Accion=1" method='post'>
		<input name="Folio" type="hidden" value="<%=folio%>">
		<div class="alert alert-info d-flex align-items-center">	
			<a class="btn btn-primary" href="documentos"><i class="fas fa-arrow-left"></i></a>
		</div>
<% 		if(!mensaje.equals("x")){%>
		<div class="alert alert-<%=color%>"><%=mensaje%></div>
<% 		}%>
		<table class="table table-condensed" width="90%">
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
					<input name="check<%=i%>" type="checkbox" value="<%=doc.getIdDocumento()%>" <%=mapaDoc.containsKey(doc.getIdDocumento())?"checked":""%>>
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
<%@ include file= "../../cierradbp.jsp" %>
<%@ include file= "../../cierra_enoc.jsp" %>