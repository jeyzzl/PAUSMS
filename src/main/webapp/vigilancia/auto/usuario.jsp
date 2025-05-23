<%@page import="aca.vigilancia.spring.VigAuto"%>
<%@page import="java.util.List"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%	
	String codigoUsuario 	= (String) session.getAttribute("codigoUltimo"); 
	VigAuto auto 			= (VigAuto) request.getAttribute("auto");
	String nombreUsuario	= (String) request.getAttribute("nombreUsuario");
	boolean existeUsuario	= (boolean) request.getAttribute("existeUsuario");
	String mensaje 			=  request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
%>
<div class="container-fluid">	
	<h2>Registro de Autos<small class="text-muted fs-5">( <%=auto.getUsuario()%> - <%=nombreUsuario%> )</small></h2>
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="listado"><i class="fas fa-arrow-left"></i></a>	
	</div>
	<div class="row">
		<div class="col-lg-6 d-flex justify-content-start">			
			<div class="card border-dark text-center" style="background-color:white">
				<div class="card-header bg-transparent border-dark">
					<img class="img-fluid rounded float-start" src="../../foto?Codigo=<%=auto.getUsuario()%>&Tipo=O" width="150px"/>
				</div>
				<div class="card-body">
			<%	if (existeUsuario){ %>			
					<a class="btn btn-danger" href="modificaUsuario?AutoId=<%=auto.getAutoId()%>&Usuario=0">Quitar</a>	
			<%	}else{ %>
					<a class="btn btn-success" href="modificaUsuario?AutoId=<%=auto.getAutoId()%>&Usuario=<%=auto.getUsuario()%>">Grabar</a>
			<%	} %>			
				</div>
			</div>	
		</div>
	</div>			
</div>