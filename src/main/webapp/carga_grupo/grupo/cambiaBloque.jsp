<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.carga.spring.CargaBloque"%>
<%@ page import= "aca.carga.spring.CargaGrupo"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<head>
<script type="text/javascript">
	function grabar(){
		document.frmBloque.submit();	
	}		
</script>
</head>
<%	
	String cargaId			= (String)session.getAttribute("cargaId");
	String carreraId 		= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");	
	String planId			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
	String cursoCargaId		= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
	String mensaje 			= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	
	String nombreMateria	= (String)request.getAttribute("nombreMateria");
	String nombreCarrera    = (String)request.getAttribute("nombreCarrera");
	String carga			= (String)request.getAttribute("carga");
	
	CargaGrupo cargaGrupo	= (CargaGrupo)request.getAttribute("cargaGrupo");
	
	List<CargaBloque> lisBloques	= (List<CargaBloque>) request.getAttribute("lisBloques");
%>
<body>
<div class="container-fluid">
	<h2><%=nombreMateria%><small class="text-muted fs-5"> ( <%=nombreCarrera%> - <%=cargaId%> - <%=carga%> )</small></h2>	
	<form id="frmBloque" name="frmBloque" action="grabarBloque?CursoCargaId=<%=cursoCargaId %>&CarreraId=<%=carreraId%>&Materia=<%=nombreMateria%>" method="post">	
	<input name="CarreraId" type="hidden" value="<%=carreraId%>">
	<input name="PlanId" type="hidden" value="<%=planId%>">
	
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary btn-sm" href="grupo?CarreraId=<%=carreraId%>&PlanId=<%=planId%>"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
		<strong><spring:message code="aca.Bloque" />:</strong>
    	<select id="BloqueId" name="BloqueId" class="form-select" style="width:400px;">
	<%	for(CargaBloque bloque : lisBloques){%>
			<option <%=cargaGrupo.getBloqueId().trim().equals(bloque.getBloqueId())?"Selected ":""%> value="<%=bloque.getBloqueId()%>" ><%=bloque.getBloqueId() %> - <%=bloque.getNombreBloque()%></option>
	<%	} %>
    	</select>
    	&nbsp;&nbsp;
    	<a href="javascript:grabar();" class="btn btn-primary btn-sm"><i class='fas fa-save'></i></a>
    	&nbsp;&nbsp;
<%		if (!mensaje.equals("-")){%>		
		&nbsp;&nbsp;&nbsp;&nbsp;<%=mensaje%>
<%		} %>
	</div>	
	</form>
</div>
</body>