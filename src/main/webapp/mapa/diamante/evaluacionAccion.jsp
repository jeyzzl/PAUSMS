<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="MapaNuevoUnidad" class="aca.plan.MapaNuevoUnidad" scope="page"/>
<jsp:useBean id="MapaNuevoUnidadU" class="aca.plan.MapaNuevoUnidadUtil" scope="page"/>

<script type="text/javascript">
	function revisaForma(){
		if( document.forma.Metodo.value != "" ){				
			document.forma.submit();				
		}else{
			alert("Llene todos los campos para poder grabar");
		}
	}
</script>

<%
	//ArrayList<CatCarrera> listCarreras	= catCarreraU.getListAll(conEnoc, "ORDER BY ENOC.FACULTAD_NOMBRE(ENOC.FACULTAD(CARRERA_ID)), NOMBRE_CARRERA");
	String planId			= request.getParameter("planId");
	String cursoId			= request.getParameter("cursoId");
	String versionId		= request.getParameter("versionId");
	String unidadId			= request.getParameter("unidadId");
	
	int	accion				= Integer.parseInt(request.getParameter("Accion")==null?"0":request.getParameter("Accion"));
	String respuesta 		= "-";
	
	switch(accion){
		case 0:{			
			MapaNuevoUnidad.setCursoId(cursoId);
			MapaNuevoUnidad.setUnidadId(unidadId);
			if(MapaNuevoUnidadU.existeReg(conEnoc,cursoId,unidadId)){
				MapaNuevoUnidad.mapeaRegId(conEnoc, cursoId, unidadId);
			}			
		}break;
		case 1:{//Guardar			
			MapaNuevoUnidad.setCursoId(cursoId);
			MapaNuevoUnidad.setUnidadId(unidadId);			
			if(MapaNuevoUnidadU.existeReg(conEnoc, cursoId, unidadId)){
				MapaNuevoUnidad.mapeaRegId(conEnoc, cursoId, unidadId);
				MapaNuevoUnidad.setAccionDocente(request.getParameter("Metodo"));				
				if(MapaNuevoUnidadU.updateReg(conEnoc, MapaNuevoUnidad)){
					respuesta = "<font size=2 color=blue><b>¡ Grabado !</b></font>";
				}else{
					respuesta = "<font size=2 color=red><b>Ocurri&oacute; un error al modificar la acci&oacute;n docente</b></font>";
				}
			}
		}break;
	}	
%>
<body>
<div class="container-fluid">
	<h1>Evidencias de evaluación <small>(<%=planId%> - <%=aca.plan.MapaNuevoCursoUtil.getNombre(conEnoc, cursoId)%>)</small></h1>
	<form id="frmProductos" name="forma" action="evaluacionAccion">
	<input type="hidden" id="planId" name="planId" value="<%=planId%>" />
	<input type="hidden" id="cursoId" name="cursoId" value="<%=cursoId%>" />
	<input type="hidden" id="versionId" name="versionId" value="<%=versionId%>" />
	<input type="hidden" id="unidadId" name="unidadId" value="<%=unidadId%>" />
	<input type="hidden" id="Accion" name="Accion" value="1" />
<% if(!respuesta.equals("-")){%>
	<div><%=respuesta%></div><br>
<% }%>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="editaUnidad?planId=<%=planId%>&versionId=<%=versionId%>&cursoId=<%=cursoId%>"><spring:message code="aca.Regresar"/></a>
	</div>
		<div class="row">
			<div class="span3">
				<label><b>Evidencias</b></label>
				<textarea id="Metodo" name="Metodo" cols="70" rows="10"><%=MapaNuevoUnidad.getAccionDocente()%></textarea>		
				<br><br>				
			</div>
		</div>
		<div class="alert alert-info">
			<input type="button" class="btn btn-primary" style="button" value="Guardar" onclick="javascript:revisaForma();" />
		</div>

	</form>
</div>
</body>
<%@ include file= "../../cierra_enoc.jsp" %>