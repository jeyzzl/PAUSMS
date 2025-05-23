<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="MapaNuevoProductoU" class="aca.plan.MapaNuevoProductoUtil" scope="page"/>
<jsp:useBean id="MapaNuevoProducto" class="aca.plan.MapaNuevoProducto" scope="page"/>

<script type="text/javascript">
	function revisaForma(){
		if( document.forma.Descripcion.value != "" ){
			document.forma.Accion.value = "2";
			document.forma.submit();
		}else{
			alert("Llene todos los campos para poder grabar");
		}
	}
	function nuevo(){		
		document.forma.Accion.value = "0";
		document.forma.Descripcion.value = "";	
		document.forma.productoId.value = "0";	
		document.forma.submit();
	}
</script>

<%
	//ArrayList<CatCarrera> listCarreras	= catCarreraU.getListAll(conEnoc, "ORDER BY ENOC.FACULTAD_NOMBRE(ENOC.FACULTAD(CARRERA_ID)), NOMBRE_CARRERA");
	String planId			= request.getParameter("planId");
	String cursoId			= request.getParameter("cursoId");
	String versionId		= request.getParameter("versionId");
	String unidadId			= request.getParameter("unidadId");
	String productoId		= request.getParameter("productoId")==null?"0":request.getParameter("productoId");
	
	int	accion				= Integer.parseInt(request.getParameter("Accion")==null?"0":request.getParameter("Accion"));
	String respuesta 		= "-";
	
	switch(accion){
		case 0:{
			//Nuevo
			MapaNuevoProducto.setCursoId(cursoId);			
			MapaNuevoProducto.setUnidadId(unidadId);
			MapaNuevoProducto.setProductoId(MapaNuevoProductoU.maximoReg(conEnoc, cursoId, unidadId));
			MapaNuevoProducto.setDescripcion("-");
			MapaNuevoProducto.setTipo("0");
			productoId = MapaNuevoProductoU.maximoReg(conEnoc, cursoId, unidadId);
		}break;
		case 1:{
			MapaNuevoProducto.setCursoId(cursoId);			
			MapaNuevoProducto.setUnidadId(unidadId);
			MapaNuevoProducto.setProductoId(productoId);
			if(MapaNuevoProductoU.existeReg(conEnoc, cursoId, unidadId, productoId)){
				MapaNuevoProducto = MapaNuevoProductoU.mapeaRegId(conEnoc,cursoId, unidadId, productoId);
			}			
		}break;
		case 2:{//Guardar			
			MapaNuevoProducto.setCursoId(cursoId);
			MapaNuevoProducto.setUnidadId(unidadId);
			MapaNuevoProducto.setProductoId(productoId);			
			if(MapaNuevoProductoU.existeReg(conEnoc, cursoId, unidadId, productoId)){
				MapaNuevoProducto = MapaNuevoProductoU.mapeaRegId(conEnoc, cursoId, unidadId, productoId);
				MapaNuevoProducto.setDescripcion(request.getParameter("Descripcion"));
				MapaNuevoProducto.setTipo(request.getParameter("Tipo"));
				if(MapaNuevoProductoU.updateReg(conEnoc, MapaNuevoProducto)){
					respuesta = "<font size=2 color=blue><b>¡ Grabado !</b></font>";
				}else{
					respuesta = "<font size=2 color=red><b>Ocurri&oacute; un error al modificar el plan. Int&eacute;ntelo de nuevo</b></font>";
				}
			}else{				
				MapaNuevoProducto.setDescripcion(request.getParameter("Descripcion"));
				MapaNuevoProducto.setTipo(request.getParameter("Tipo"));
				if(MapaNuevoProductoU.insertReg(conEnoc, MapaNuevoProducto)){
					respuesta = "<font size=2 color=blue><b>¡ Grabado !</b></font>";
				}else{
					respuesta = "<font size=2 color=red><b>Ocurri&oacute; un error al graabar el producto. Int&eacute;ntelo de nuevo</b></font>";
				}
				
			}
			
		}break;
		
		case 3:{
			MapaNuevoProducto.setCursoId(cursoId);			
			MapaNuevoProducto.setUnidadId(unidadId);
			MapaNuevoProducto.setProductoId(productoId);
			if(MapaNuevoProductoU.existeReg(conEnoc, cursoId, unidadId, productoId)){
				if(MapaNuevoProductoU.deleteReg(conEnoc, cursoId, unidadId, productoId)){
					respuesta = "<font size=2 color=blue><b>¡ Borrado !</b></font>";
				}else{
					respuesta = "<font size=2 color=red><b>Ocurri&oacute; un error al borrar producto!</b></font>";
				}
			}			
		}break;
	}
	
	// Lista de productos
	ArrayList<aca.plan.MapaNuevoProducto> listProductos 	= MapaNuevoProductoU.getListUnidad(conEnoc, cursoId, unidadId, " ORDER BY TIPO, PRODUCTO_ID");
%>
<body>
<div class="container-fluid">	
	<h2> <%=planId%> - <%=aca.plan.MapaNuevoCursoUtil.getNombre(conEnoc, cursoId)%><small class="text-muted fs-5">( <%=aca.plan.MapaNuevoUnidadUtil.getNombre(conEnoc, cursoId, unidadId)%> )</small></h2>
	<form id="frmProductos" name="forma" action="productoAccion">
	<input type="hidden" id="planId" name="planId" value="<%=planId%>" />
	<input type="hidden" id="cursoId" name="cursoId" value="<%=cursoId%>" />
	<input type="hidden" id="versionId" name="versionId" value="<%=versionId%>" />
	<input type="hidden" id="unidadId" name="unidadId" value="<%=unidadId%>" />
	<input type="hidden" id="productoId" name="productoId" value="<%=productoId%>" />
	<input type="hidden" id="Accion" name="Accion"/>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="editaUnidad?planId=<%=planId%>&versionId=<%=versionId%>&cursoId=<%=cursoId%>"><spring:message code="aca.Regresar"/></a>
	</div>	
<% if(!respuesta.equals("-")){%>
	<div><%=respuesta%></div><br>
<% }%>
	<div class="row">
		<div class="span3">
			<label><b>Producto de aprendizaje</b></label>
			<textarea id="Descripcion" name="Descripcion" cols="70" rows="3"><%=MapaNuevoProducto.getDescripcion()%></textarea>				
			<br><br>
			<label><b>Tipo</b></label>
			<select name="Tipo">
				<option value="1" <%=MapaNuevoProducto.getTipo().equals("1")?"selected":""%>>Cognitivos</option>
				<option value="2" <%=MapaNuevoProducto.getTipo().equals("2")?"selected":""%>>Psicométricos</option>
				<option value="3" <%=MapaNuevoProducto.getTipo().equals("3")?"selected":""%>>Afectivos</option>
				<option value="4" <%=MapaNuevoProducto.getTipo().equals("4")?"selected":""%>>Sociales</option>
				<option value="5" <%=MapaNuevoProducto.getTipo().equals("5")?"selected":""%>>Eticos</option>
			</select>
			<br>
		</div>
	</div>
	<div class="alert alert-info">
		<input type="button" class="btn btn-primary" style="button" value="Nuevo" onclick="nuevo();" />
		<input type="button" class="btn btn-primary" style="button" value="Guardar" onclick="revisaForma();" />
	</div>
	</form>
	<table  width="90%" class="table table-striped table-fontsmall">
		<tr>
			<th width="5%">Op.</th>
			<th width="5%">#</th>
			<th width="70%">Producto de aprendizaje</th>
			<th width="30%">Tipo</th>
		</tr>
<%
	int row = 0;
	for (aca.plan.MapaNuevoProducto producto : listProductos){
		row++;
		String tipoNombre = "-";
		if (producto.getTipo().equals("1")) tipoNombre = "Cognitivos";
		if (producto.getTipo().equals("2")) tipoNombre = "Psicométricos";
		if (producto.getTipo().equals("3")) tipoNombre = "Afectivos";
		if (producto.getTipo().equals("4")) tipoNombre = "Sociales";
		if (producto.getTipo().equals("5")) tipoNombre = "Eticos";
		
%>
		<tr>
		    <td>
		    	<a href="productoAccion?Accion=1&planId=<%=planId%>&cursoId=<%=cursoId%>&versionId=<%=versionId%>&unidadId=<%=unidadId%>&productoId=<%=producto.getProductoId()%>" class="fas fa-edit" title = "Editar"></a>&nbsp;
		    	<a href="productoAccion?Accion=3&planId=<%=planId%>&cursoId=<%=cursoId%>&versionId=<%=versionId%>&unidadId=<%=unidadId%>&productoId=<%=producto.getProductoId()%>"  class="fas fa-trash-alt" title = "Borrar"></a>
		    </td>
			<td><%=row%></td>
			<td><%=producto.getDescripcion()%></td>
			<td><%=tipoNombre%></td>
		</tr>
<%		
	}
%>	
	</table>
</div>
</body>
<%@ include file= "../../cierra_enoc.jsp" %>