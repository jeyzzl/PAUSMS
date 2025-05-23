<%@ include file="../../con_enoc.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="Estado" scope="page" class="aca.bitacora.BitEstado"/>
<jsp:useBean id="EstadoU" scope="page" class="aca.bitacora.BitEstadoUtil"/>

<%
	String accion 	= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String mensaje 	= "x";
	String colMens	= "";
	
	String nombre = request.getParameter("nombre")==null?"":request.getParameter("nombre");

	if(accion.equals("1")){
		Estado.setEstado(EstadoU.maximoReg(conEnoc));
		Estado.setEstado_nombre(nombre);

		if(EstadoU.insertReg(conEnoc, Estado)){
			mensaje = "Guardado";
			colMens = "success";
		}else{
			mensaje = "No se guardo";
			colMens = "danger";
		}
	}
%>

<div class="container-fluid">
	<h2>Nuevo Status</h2>
	<div class="alert alert-info">
		<a href="estado.jsp" style="text-align:center;" class="btn btn-primary"><i class="fas fa-arrow-left"></i> Regresar</a>&nbsp;
	</div>
<%	if(!mensaje.equals("x")){%>
	<div class="alert alert-"<%=colMens%>>
	<%=mensaje%>
	</div>
<%	}%>
	<form name="formEstado" method="post" action="nuevoEstado.jsp">
		<div class="row container-fluid">
			<div class="span3">
				<input type="hidden" name="Accion" value="1">
				<label for="nombre">Nombre</label>
				<input type="text" class="text form-control" name="nombre" size="50" maxlength="50" value="<%=nombre%>">
			</div>
		</div>
		<br>
<%	if(accion.equals("0")){%>
	<div class="alert alert-info">
		<input class="btn btn-primary" type="submit" value="Guardar">
	</div>
<%	}%>
	</form>
</div>
<%@ include file="../../cierra_enoc.jsp"%>