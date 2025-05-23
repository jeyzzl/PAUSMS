<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="InvComentario" scope="page" class="aca.investiga.InvComentario"/>
<jsp:useBean id="InvComentarioU" scope="page" class="aca.investiga.InvComentarioUtil"/>
<jsp:useBean id="Usuario" scope="page" class="aca.vista.Usuarios"/>

<% 
	String codigo			= (String) session.getAttribute("codigoPersonal");

	String accion 			= request.getParameter("Accion")==null?"":request.getParameter("Accion");
	String proyecto 		= request.getParameter("ProyectoId");
	
	String fechaHoy 		= aca.util.Fecha.getHoy();
		 
	String nombreUsuario	= " ";
	String msj				= " ";
	String maximo			= " ";
	
	Usuario.setCodigoPersonal(codigo);
	if (Usuario.existeReg(conEnoc)){
		Usuario.mapeaRegId(conEnoc, codigo);
		nombreUsuario	= Usuario.getNombre()+" "+Usuario.getApellidoPaterno()+" "+Usuario.getApellidoMaterno();
	}else{
		nombreUsuario = "¡ No existe !";
	}
	
	
	//OPERACIONES A REALIZAR	
	conEnoc.setAutoCommit(false);	
	
	if(accion.equals("0")){
		InvComentario.setProyectoId(proyecto);
		maximo = InvComentarioU.maximoReg(conEnoc, proyecto);
		InvComentario.setFolio(maximo);
	}else if(accion.equals("1")) {
		InvComentario.setProyectoId(proyecto);
		InvComentario.setCodigoPersonal(codigo);
		InvComentario.setFecha(fechaHoy);
		InvComentario.setFolio(request.getParameter("folio"));
		InvComentario.setComentario(request.getParameter("comentario"));
		InvComentario.setTipo(request.getParameter("tipo"));
		
		if(InvComentarioU.existeReg(conEnoc, proyecto, request.getParameter("folio"))==false){	
			if(InvComentarioU.insertReg(conEnoc, InvComentario)){
				msj = "<div class='alert alert-success'><button class='close' data-dismiss='alert'>&times;</button> El Registro se guardó correctamente</div>";
				conEnoc.commit();
			}else{
				msj = "<div class='alert alert-error'><button class='close' data-dismiss='alert'>&times;</button> Ocurrió un error al guardar el registro</div>";
			}
		}else{//Modifica
			
			if(InvComentarioU.updateReg(conEnoc, InvComentario)){
				msj = "<div class='alert alert-success'><button class='close' data-dismiss='alert'>&times;</button> El Registro se Editó correctamente</div>";
				conEnoc.commit();
			}else{
				msj = "<div class='alert alert-error'><button class='close' data-dismiss='alert'>&times;</button> Ocurrió un error al editar el registro</div>";
			}
			
		}
	}else if(accion.equals("2")){
		InvComentario.setProyectoId(proyecto);
		InvComentario.setFolio(request.getParameter("Folio"));
		if (InvComentarioU.existeReg(conEnoc, proyecto, request.getParameter("Folio")) == true) {
			if (InvComentarioU.deleteReg(conEnoc, proyecto, request.getParameter("Folio"))) {
				msj = "<div class='alert alert-success'><button class='close' data-dismiss='alert'>&times;</button> El Registro se Eliminó correctamente</div>";
				conEnoc.commit();
			} else {
				msj = "<div class='alert alert-error'><button class='close' data-dismiss='alert'>&times;</button> Ocurrió un error al eliminar el registro</div>";
			}
		} else {
			msj = "<div class='alert alert-error'><button class='close' data-dismiss='alert'>&times;</button> Este Registro no existe</div>";
		}
	}else if((accion.equals("3"))){
		InvComentario.setProyectoId(proyecto);
		InvComentario.setFolio(request.getParameter("Folio"));
		InvComentario.mapeaRegId(conEnoc, proyecto,request.getParameter("Folio"));
	}
	
	conEnoc.setAutoCommit(true);
%>
<html>
<body>
<div class="container-fluid">
	<h2>Comentarios <small class="text-muted fs-4">Proyecto: <%= proyecto %></small></h2>	
	<%=msj %>
	<div class="alert alert-info">
			<a href="comentario?ProyectoId=<%= proyecto %>" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;			 	
	</div>
	<form name="forma" id="forma" action="accionComment" method="post">
	<input  type="hidden" name="ProyectoId"  value="<%=proyecto%>"/>
	<input type="hidden" name="Accion" id="Accion" />
	
	<p>
		<label for="folio">Referencia</label>
		<input name="folio" type="text" id="folio"  value="<%= InvComentario.getFolio()%>" readonly>
	</p>
	<p>
		<label for="comentario" ><spring:message code="aca.Comentario"/></label>
		<textarea name="comentario" id="comentario" cols="100" rows="3"><%=InvComentario.getComentario()==null?"":InvComentario.getComentario() %></textarea>
	</p>
	<p> 
		<label for="tipo">Tipo de comentario</label>
		<select name="tipo" id="tipo">
			<option value="S" <%if(InvComentario.getTipo()!=null && InvComentario.getTipo().equals("S"))out.print("selected"); %>>Público</option>
			<option value="N" <%if(InvComentario.getTipo()!=null && InvComentario.getTipo().equals("N"))out.print("selected"); %>>Privado</option>
		</select>
	</p>
	<div class="alert alert-info">
		<a class="btn btn-primary btn btn-large" onclick="javascript:Guardar();"><i class="icon-ok icon-white"></i> Guardar</a>
		<a href="comentario" class="btn btn-large"><i class="fas fa-trash-alt"></i>Cancelar</a>
	</div>
</form>
</div>
</body>
<script>
	function Guardar() {
		if (document.forma.folio.value != ""
		 && document.forma.comentario.value != ""
		 && document.forma.tipo.value != "") {
			document.forma.Accion.value = "1";
			document.forma.submit();
		} else {
			alert("¡Completa todos los campos!");
		}
	}
</script>
</html>
<%@ include file= "../../cierra_enoc.jsp" %>