<%@page import="java.util.List"%>
<%@page import="aca.alumno.spring.AlumPlan"%>
<%@page import="aca.archivo.spring.ArchDocumentos"%>
<%@page import="aca.archivo.spring.ArchStatus"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%	
	// Declaracion de Variables
	String matricula 		= (String) session.getAttribute("codigoAlumno");
	String idDocumento		= request.getParameter("IdDocumento")==null?"0":request.getParameter("IdDocumento");
	String idStatus			= request.getParameter("IdStatus")==null?"0":request.getParameter("IdStatus");	
	
	AlumPlan plan 			= (AlumPlan)request.getAttribute("plan");
	
	// List que traen los documentos al combo y los acomoda
	List<ArchDocumentos> lisDocumentos 		= (List<ArchDocumentos>)request.getAttribute("lisDocumentos");
	List<ArchStatus> lisStatus 				= (List<ArchStatus>)request.getAttribute("lisStatus");
%>
<head>
<script type="text/javascript">
	function Grabar(){
		if (document.frmDocumento.IdDocumento.value!="" && document.frmDocumento.IdStatus.value!=""){
			document.frmDocumento.submit();
		}else{
			alert("Select file and status");
		}
	}
</script>	
</head>
<body>
<div class="container-fluid">
	<h3> Document Registration <small class="text-muted">( <%=matricula%> - <%=plan.getPlanId()%> )</small></h3>		
	<form name="frmDocumento" method="post" action="grabarNuevo">
		<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary btn-sm" href="documentos"><i class="fas fa-arrow-left"></i></a>
		&nbsp;&nbsp;
		Select document:
		<select name="IdDocumento" class="custom-select" onchange="javascript:document.location.href='elegir_documento?IdDocumento='+encodeURIComponent(this.options[this.selectedIndex].value);" style="width:350px;">
        <option value=""></option>
<%
		for(int i=0;i< lisDocumentos.size(); i++){
			ArchDocumentos doc = (ArchDocumentos) lisDocumentos.get(i);
%>
			<option value="<%=doc.getIdDocumento()%>" <%if(doc.getIdDocumento().equals(idDocumento)) out.print("selected");%>><%=doc.getDescripcion()%></option>
<%		} %>
        </select>
        &nbsp;&nbsp;
        Select Status: 
        <select name="IdStatus" class="custom-select" style="width:200px;">
<%
		for(int i=0;i< lisStatus.size(); i++){
			ArchStatus s = (ArchStatus) lisStatus.get(i); %>
			<option value="<%=s.getIdStatus()%>" <%if(s.getIdStatus().equals(idStatus)) out.print("selected");%>><%=s.getDescripcion()%></option>
<%		} %>
        </select>        
		&nbsp;
		<a href="javascript:Grabar()" class="btn btn-primary btn-sm">Save</a>
		</div>	
	</form>
</div>
</body>