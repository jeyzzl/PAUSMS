<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="PorSeccionU" scope="page" class="aca.por.PorSeccionUtil"/>
<jsp:useBean id="Seccion" scope="page" class="aca.por.PorSeccion"/>

<script type="text/javascript">
	function Modificar(){
		if(document.frmSeccion.Nombre.value != ""){
			document.frmSeccion.Accion.value="1";
			document.frmSeccion.submit();
		}else{
			alert("Agregale un nombre a la nueva seccion");
		}
	}	
</script>
<%		
	String portafolioId		= request.getParameter("PortafolioId")==null?"0":request.getParameter("PortafolioId");	
	String seccionId 		= request.getParameter("SeccionId")==null?"0":request.getParameter("SeccionId");
	String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");	
	String msj = "";
	Seccion.setPorId(portafolioId);
	Seccion.setSeccionId(seccionId);
	if (Seccion.existeReg(conEnoc)){
		Seccion.mapeaRegId(conEnoc, portafolioId, seccionId);
	}
	boolean hijoEmp			= Seccion.seUtilizaEmp(conEnoc, portafolioId, seccionId);
	if(accion.equals("1")){
		Seccion.setPorId(portafolioId);
		Seccion.setSeccionId(seccionId);
		Seccion.setSeccionNombre(request.getParameter("Nombre"));
		Seccion.setSeccionSuperior(request.getParameter("SeccionSuperior"));
		Seccion.setTipo(request.getParameter("Tipo"));
		Seccion.setTitulo(request.getParameter("Titulo"));
		Seccion.setAcceso("T");
		Seccion.setOrden(request.getParameter("Orden"));
		Seccion.setEstado(request.getParameter("Estado"));
		Seccion.setInstrucciones(request.getParameter("Instrucciones"));
		if(Seccion.updateReg(conEnoc)){
			msj = "<div class='alert alert-success'>Guardado Correctamente</div>";
			Seccion.mapeaRegId(conEnoc, portafolioId, seccionId);
		}else{
			msj = "<div class='alert alert-danger'>Hubo un error al guardar</div>";
		}
	}else if(accion.equals("3")){
		Seccion.setPorId(portafolioId);
		Seccion.setSeccionId(seccionId);
		if(Seccion.deleteReg(conEnoc)){
			response.sendRedirect("porSeccion?porId="+portafolioId+"&Accion=3");
		}else{
			msj = "<div class='alert alert-danger'>Hubo un error al eliminar la seccion</div>";
		}
	}	
%>
<div class="container-fluid">
	<h1><spring:message code="aca.Secciones"/></h1>
	
	<div class="alert alert-info"><a href="porSeccion?porId=<%=portafolioId %>" class="btn btn-primary"><i class="icon-white icon-chevron-left"></i> Regresar</a></div> 
	
		<div class="row">
			<div class="col-5" class="form-control" >
	
	<%out.print(msj); %>
	<form name="frmSeccion" action="editarSeccion" method="post" >
	<input type="hidden" name="Accion">
	<input type="hidden" name="PortafolioId" value="<%=portafolioId%>">
	<input type="hidden" name="SeccionId" value="<%=seccionId%>">
	<%if(hijoEmp){ %>
	<input type="hidden" name="Tipo" value="<%=Seccion.getTipo()%>">
	<%} %>
	<input type="hidden" name="SeccionSuperior" value="<%=Seccion.getSeccionSuperior()%>">
	<label>Titulo</label>	
	<input name="Titulo" type="text"class="form-control" style="width:350px" value="<%=Seccion.getTitulo()%>">
	<br><br>
	<label>Nombre:</label>	
	<input name="Nombre" type="text" class="form-control" style="width:350px" value="<%=Seccion.getSeccionNombre()%>" class="input input-xxlarge">
	<br><br>
	<label>Instrucciones</label>	
	<textarea name="Instrucciones" class="form-control" style="width:350px"><%=Seccion.getInstrucciones()%></textarea>	
	<%if(!hijoEmp){ %>	
	<br><br>
	<label>Tipo:</label>	
	<select name="Tipo" class="form-control" style="width:350px">
		<option value="-" <%if(Seccion.getTipo().equals("-")) out.print("selected"); %>>Informativo</option>
		<option value="C" <%if(Seccion.getTipo().equals("C")) out.print("selected"); %>>Texto Corto</option>
		<option value="L" <%if(Seccion.getTipo().equals("L")) out.print("selected"); %>>Texto Largo</option>
		<option value="I" <%if(Seccion.getTipo().equals("I")) out.print("selected"); %>>Imagen</option>
		<option value="A" <%if(Seccion.getTipo().equals("A")) out.print("selected"); %>>Archivo</option>
		<option value="E" <%if(Seccion.getTipo().equals("E")) out.print("selected"); %>>Enlace externo</option>
	</select>	
	<%} %>	
	<br><br>
	<label>Orden:</label>
	<input name="Orden" type="text" class="form-control" style="width:350px" value="<%=Seccion.getOrden()%>">
	<br><br>	
	<label>Estado:</label>
	<select name="Estado" class="form-control" style="width:350px">
		<option value="A" <%if(Seccion.getEstado().equals("A")) out.print("selected"); %>>Activo</option>
		<option value="I" <%if(Seccion.getEstado().equals("I")) out.print("selected"); %>>Inactivo</option>
	</select>
	<br>
	<br>	
	<div class="alert alert-info">
	<a href="javascript: Modificar()" class="btn btn-primary">Guardar</a>
	</div>
	</form>	
</div>
<script>
</script>
<%@ include file="../../cierra_enoc.jsp" %>