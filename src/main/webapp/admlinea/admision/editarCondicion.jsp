<%@ page import="aca.admision.spring.AdmSolicitud"%>
<%@ page import="aca.admision.spring.AdmCarta"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<head>
<link href="../admision.css" rel="STYLESHEET" type="text/css">
<%	
	String folio 			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
	String condicionId  	= request.getParameter("CondicionId")==null?"0":request.getParameter("CondicionId");
	AdmCarta admCarta			= (AdmCarta) request.getAttribute("admCarta");
	AdmSolicitud admSolicitud	= (AdmSolicitud) request.getAttribute("admSolicitud");
	
	String nombreAlumno 		= admSolicitud.getNombre()+" "+(admSolicitud.getApellidoMaterno()==null?"":admSolicitud.getApellidoMaterno())+" "+admSolicitud.getApellidoPaterno();
%>
<script type="text/javascript">
	function Grabar(){
		if(document.frmVobo.CondicionNombre != ""){
			document.frmVobo.submit();			
		}else{
			alert("Complete el formulario ..! ");
		}
	}	
</script>

</head>
<body>
<div class="container-fluid">
	<h2>Editar condici�n <small class="text-muted fs-5">( <%=nombreAlumno %> )</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="condiciones?Folio=<%=folio%>"><spring:message code="aca.Regresar"/></a>
		&nbsp;&nbsp;&nbsp;Folio: <span class="badge badge-inverse"><%=folio%></span>		
	</div>
	<form name="frmVobo" action="grabarCondicion" method="post">
	<input type="hidden" name="Folio" value="<%=folio%>">
	<input type="hidden" name="CondicionId" value="<%=condicionId%>">
	<label>Comentario</label>
	<textarea name="CondicionNombre" cols="70" rows="5"><%=admCarta.getCondicionNombre()%></textarea><br><br>
	<div class="alert alert-info">					
		<button class="btn btn-primary" href="javascript:Grabar() type="submit">Grabar</button>
	</div>			
	</form>
</div>	
</body>