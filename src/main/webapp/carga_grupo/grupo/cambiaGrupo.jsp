<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<head>
<script type="text/javascript">
	function grabar(){
			document.frmGrupo.submit();
	}
</script>
</head>
<%		
	String carreraId		= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");
	String planId			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
	String cursoCargaId		= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
	String cursoId			= request.getParameter("CursoId")==null?"0":request.getParameter("CursoId");
		
	String nombreMateria	= (String) request.getAttribute("nombreMateria");
	String nombreCarrera	= (String) request.getAttribute("nombreCarrera");
	String grupo			= (String) request.getAttribute("grupo");
	
	String sResultado		= "";

%>
<body>
<div class="container-fluid">
	<h2>Update the group's hours<small class="text-muted fs-5"> ( <%=nombreMateria%> - <%=nombreCarrera%> )</small></h2>
	
	<form id="frmGrupo" name="frmGrupo" action="guardarGrupo" method="post">
	<input name="CarreraId" type="hidden" value="<%=carreraId%>">
	<input name="PlanId" type="hidden" value="<%=planId%>">
	<input name=CursoCargaId type="hidden" value="<%=cursoCargaId%>">
	<input name="CursoId" type="hidden" value="<%=cursoId%>">
	
	<div class="alert alert-info">
		<a class="btn btn-primary" href="grupo?CarreraId=<%=carreraId%>&PlanId=<%=planId%>"><spring:message code="aca.Regresar"/></a>&nbsp;&nbsp;
		<input name="Grupo" type="text" value="<%=grupo%>">&nbsp;&nbsp;
		<a class="btn btn-primary" href="javascript:grabar();"><i class="icon-ok icon-white"></i>Save</a>
	</div>
	</form>
</div>
</body>