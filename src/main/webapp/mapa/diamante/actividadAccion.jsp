<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="MapaNuevoActividadU" class="aca.plan.MapaNuevoActividadUtil" scope="page"/>
<jsp:useBean id="MapaNuevoActividad" class="aca.plan.MapaNuevoActividad" scope="page"/>

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
		document.forma.actividadId.value = "0";	
		document.forma.submit();
	}
</script>

<%
	String planId			= request.getParameter("planId");
	String cursoId			= request.getParameter("cursoId");
	String versionId		= request.getParameter("versionId");
	String unidadId			= request.getParameter("unidadId");
	String actividadId		= request.getParameter("actividadId")==null?"0":request.getParameter("actividadId");
	
	int	accion				= Integer.parseInt(request.getParameter("Accion")==null?"0":request.getParameter("Accion"));
	String respuesta 		= "-";
	
	switch(accion){
		case 0:{
			//Nuevo
			MapaNuevoActividad.setCursoId(cursoId);			
			MapaNuevoActividad.setUnidadId(unidadId);
			MapaNuevoActividad.setActividadId(MapaNuevoActividadU.maximoReg(conEnoc, cursoId, unidadId));
			MapaNuevoActividad.setDescripcion("-");
			MapaNuevoActividad.setTipo("0");
			actividadId = MapaNuevoActividadU.maximoReg(conEnoc, cursoId, unidadId);
		}break;
		case 1:{
			MapaNuevoActividad.setCursoId(cursoId);			
			MapaNuevoActividad.setUnidadId(unidadId);
			MapaNuevoActividad.setActividadId(actividadId);
			if(MapaNuevoActividadU.existeReg(conEnoc, cursoId, unidadId, actividadId)){
				MapaNuevoActividad.mapeaRegId(conEnoc,cursoId, unidadId, actividadId);
			}			
		}break;
		case 2:{//Guardar			
			MapaNuevoActividad.setCursoId(cursoId);
			MapaNuevoActividad.setUnidadId(unidadId);
			MapaNuevoActividad.setActividadId(actividadId);			
			if(MapaNuevoActividadU.existeReg(conEnoc, cursoId, unidadId, actividadId)){
				MapaNuevoActividad.mapeaRegId(conEnoc, cursoId, unidadId, actividadId);
				MapaNuevoActividad.setDescripcion(request.getParameter("Descripcion"));
				MapaNuevoActividad.setTipo(request.getParameter("Tipo"));
				if(MapaNuevoActividadU.updateReg(conEnoc, MapaNuevoActividad)){
					respuesta = "<font size=2 color=blue><b>¡ Grabado !</b></font>";
				}else{
					respuesta = "<font size=2 color=red><b>Ocurri&oacute; un error al modificar el plan. Int&eacute;ntelo de nuevo</b></font>";
				}
			}else{				
				MapaNuevoActividad.setDescripcion(request.getParameter("Descripcion"));
				MapaNuevoActividad.setTipo(request.getParameter("Tipo"));
				if(MapaNuevoActividadU.insertReg(conEnoc, MapaNuevoActividad)){
					respuesta = "<font size=2 color=blue><b>¡ Grabado !</b></font>";
				}else{
					respuesta = "<font size=2 color=red><b>Ocurri&oacute; un error al graabar el producto. Int&eacute;ntelo de nuevo</b></font>";
				}
				
			}
			
		}break;
		
		case 3:{
			MapaNuevoActividad.setCursoId(cursoId);			
			MapaNuevoActividad.setUnidadId(unidadId);
			MapaNuevoActividad.setActividadId(actividadId);
			if(MapaNuevoActividadU.existeReg(conEnoc, cursoId, unidadId, actividadId)){
				if(MapaNuevoActividadU.deleteReg(conEnoc, cursoId, unidadId, actividadId)){
					respuesta = "<font size=2 color=blue><b>¡ Borrado !</b></font>";
				}else{
					respuesta = "<font size=2 color=red><b>Ocurri&oacute; un error al borrar producto!</b></font>";
				}
			}			
		}break;
	}
	
	// Lista de productos
	ArrayList<aca.plan.MapaNuevoActividad> listActividades 	= MapaNuevoActividadU.getListUnidad(conEnoc, cursoId, unidadId, " ORDER BY TIPO, ACTIVIDAD_ID");
%>
<body>
<div class="container-fluid">	
	<h2> <%=planId%> - <%=aca.plan.MapaNuevoCursoUtil.getNombre(conEnoc, cursoId)%><small class="text-muted fs-5">( <%=aca.plan.MapaNuevoUnidadUtil.getNombre(conEnoc, cursoId, unidadId)%> )</small></h2>
	<form id="frmActividades" name="forma" action="actividadAccion">
	<input type="hidden" id="planId" name="planId" value="<%=planId%>" />
	<input type="hidden" id="cursoId" name="cursoId" value="<%=cursoId%>" />
	<input type="hidden" id="versionId" name="versionId" value="<%=versionId%>" />
	<input type="hidden" id="unidadId" name="unidadId" value="<%=unidadId%>" />
	<input type="hidden" id="actividadId" name="actividadId" value="<%=actividadId%>" />
	<input type="hidden" id="Accion" name="Accion"/>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="editaUnidad?planId=<%=planId%>&versionId=<%=versionId%>&cursoId=<%=cursoId%>"><spring:message code="aca.Regresar"/></a>
	</div>	
<% if(!respuesta.equals("-")){%>
	<div><%=respuesta%></div><br>
<% }%>
	<div class="row">
		<div class="span3">
			<label><b><spring:message code="aca.mapa.diamante.actividadAccion.Frase1"/></b></label>
			<textarea id="Descripcion" name="Descripcion" cols="70" rows="3"><%=MapaNuevoActividad.getDescripcion()%></textarea>				
			<br><br>
			<label><b><spring:message code="aca.Estrategias"/></b></label>
			<select name="Tipo">
				<option value="1" <%=MapaNuevoActividad.getTipo().equals("1")?"selected":""%>><spring:message code="aca.mapa.diamante.actividadAccion.Proyectos"/></option>
				<option value="2" <%=MapaNuevoActividad.getTipo().equals("2")?"selected":""%>><spring:message code="aca.mapa.diamante.actividadAccion.EstudioDeCaso"/></option>
				<option value="3" <%=MapaNuevoActividad.getTipo().equals("3")?"selected":""%>><spring:message code="aca.mapa.diamante.actividadAccion.ResolucionProblemas"/></option>
				<option value="4" <%=MapaNuevoActividad.getTipo().equals("4")?"selected":""%>><spring:message code="aca.mapa.diamante.actividadAccion.Simulacion"/></option>
				<option value="5" <%=MapaNuevoActividad.getTipo().equals("5")?"selected":""%>><spring:message code="aca.mapa.diamante.actividadAccion.AprendizajeServicio"/></option>
				<option value="6" <%=MapaNuevoActividad.getTipo().equals("6")?"selected":""%>><spring:message code="aca.mapa.diamante.actividadAccion.AprendizajeExperencia"/></option>
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
			<th width="70%"><spring:message code="aca.Actividades"/></th>
			<th width="20%"><spring:message code="aca.Estrategias"/></th>
		</tr>
<%
	int row = 0;
	for (aca.plan.MapaNuevoActividad actividad : listActividades){
		row++;
		String tipoNombre = "-";
		if (actividad.getTipo().equals("1")) tipoNombre = "Proyectos";
		if (actividad.getTipo().equals("2")) tipoNombre = "Estudios de casos";
		if (actividad.getTipo().equals("3")) tipoNombre = "Resolución de problemas";
		if (actividad.getTipo().equals("4")) tipoNombre = "Simulación";
		if (actividad.getTipo().equals("5")) tipoNombre = "Aprendizaje-servicio";
		if (actividad.getTipo().equals("6")) tipoNombre = "Aprendizaje por experiencia";		
%>
		<tr>
		    <td>
		    	<a href="actividadAccion?Accion=1&planId=<%=planId%>&cursoId=<%=cursoId%>&versionId=<%=versionId%>&unidadId=<%=unidadId%>&actividadId=<%=actividad.getActividadId()%>" class="fas fa-edit" title = "Editar"></a>&nbsp;
		    	<a href="actividadAccion?Accion=3&planId=<%=planId%>&cursoId=<%=cursoId%>&versionId=<%=versionId%>&unidadId=<%=unidadId%>&actividadId=<%=actividad.getActividadId()%>"  class="fas fa-trash-alt" title = "Borrar"></a>
		    </td>
			<td><%=row%></td>
			<td><%=actividad.getDescripcion()%></td>
			<td><%=actividad.getTipo()%></td>
		</tr>
<%		
	}
%>	
	</table>
</div>
</body>
<%@ include file= "../../cierra_enoc.jsp" %>