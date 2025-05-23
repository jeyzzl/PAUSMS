<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "../../conectadbp.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%-- <jsp:include page="../menu.jsp" /> --%>
<%@ include file= "menu.jsp" %>

<jsp:useBean id="alumno" scope="page" class="aca.alumno.AlumPersonal"/>
<jsp:useBean id="alumnoUtil" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="ArchEntregaU" scope="page" class="aca.archivo.ArchEntregaUtil"/>
<jsp:useBean id="ArchEntrega" scope="page" class="aca.archivo.ArchEntrega"/>

<%
	String codigoAlumno	= (String)session.getAttribute("codigoAlumno");
	alumno 				= alumnoUtil.mapeaRegId(conEnoc,codigoAlumno);
	String folio    	= request.getParameter("Folio");
	ArchEntrega 		= ArchEntregaU.getEntrega(conEnoc, codigoAlumno, folio);
	
	String lisDoc		=  ArchEntregaU.getDocumentos(conEnoc,codigoAlumno,folio);
	
	int numFotos 		= 0;
%>

<div class="container-fluid">
	<h2>Documentos entregados <small class="text-muted fs-4">( <%=codigoAlumno%> - <%=alumno.getNombre()%> <%=alumno.getApellidoPaterno()%> <%=alumno.getApellidoMaterno()%> )</small></h2>
	<div class="alert alert-info">
		<a href="docEntregados" class="btn btn-primary">Regresar</a>&nbsp;&nbsp;&nbsp;&nbsp;
<% 			
			String[] arrayDoc = lisDoc.split("-");
			for(String doc : arrayDoc){%>
				<span class="badge bg-success"><%=aca.archivo.ArchDocumentosUtil.getDescripcion(conEnoc,doc)%></span>
<% 			}%>
	</div>
	<div class="row">
<%
	if (ArchEntrega.getIdentificacion() != null){
		numFotos++;	
%>	
		<div class="span3">
			<div class="thumbnail">
				<label>Identificacion</label>
				<img src="imagen?Folio=<%=folio%>&Opcion=1" style="width: 100%;">
			</div>
		</div>
<% 
	}
	if (ArchEntrega.getPoder() != null){
		numFotos++;	
%>
		<div class="span3">
			<div class="thumbnail">
				<label>Poder</label>
				<img src="imagen?Folio=<%=folio%>&Opcion=2" style="width: 100%;">
			</div>
		</div>
<%
	}
	if (ArchEntrega.getEnvio() != null){
		numFotos++;
%>
		<div class="span3">
			<div class="thumbnail">
				<label>Envio</label>
				<img src="imagen?Folio=<%=folio%>&Opcion=3" style="width: 100%;">
			</div>
		</div>	
<%
	}
	if(numFotos  == 3){
%>
	</div>
	<div class="row">
<%
	}
	if (ArchEntrega.getFirma() != null){
		numFotos++;	
%>
		<div class="span3">
			<div class="thumbnail">
				<label>Firma</label>
				<img src="imagen?Folio=<%=folio%>&Opcion=4" style="width: 100%;">
			</div>
		</div>
<%
	}
	if(numFotos  == 3){
%>
	</div>
	<div class="row">
<%
	}
	if (ArchEntrega.getExtra() != null){
		numFotos++;	
%>
		<div class="span3">
			<div class="thumbnail">
				<label>Extra</label>
				<img src="imagen?Folio=<%=folio%>&Opcion=5" style="width: 100%;">
			</div>
		</div>
<%
	}
%>	
	</div>
</div>
<%@ include file= "../../cierradbp.jsp" %>
<%@ include file= "../../cierra_enoc.jsp" %>