<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="MapaNuevoCurso" class="aca.plan.MapaNuevoCurso" scope="page"/>
<jsp:useBean id="MapaNuevoCursoU" class="aca.plan.MapaNuevoCursoUtil" scope="page"/>
<jsp:useBean id="Acceso" class="aca.acceso.Acceso" scope="page"/>
<jsp:useBean id="AccesoU" scope="page" class="aca.acceso.AccesoUtil" />

<script type="text/javascript">
	function revisaForma(){
		if( document.forma.Descripcion.value != "" ){				
			document.forma.submit();				
		}else{
			alert("Llene todos los campos para poder grabar");
		}
	}
</script>

<%
	//ArrayList<CatCarrera> listCarreras	= catCarreraU.getListAll(conEnoc, "ORDER BY ENOC.FACULTAD_NOMBRE(ENOC.FACULTAD(CARRERA_ID)), NOMBRE_CARRERA");
	String codigoPersonal	= (String) session.getAttribute("codigoPersonal");
	
	String planId			= request.getParameter("planId");
	String cursoId			= request.getParameter("cursoId");
	String versionId		= request.getParameter("versionId");
	int	accion				= Integer.parseInt(request.getParameter("Accion")==null?"0":request.getParameter("Accion"));
	String carreraId		= aca.plan.PlanUtil.getCarreraId(conEnoc, planId);
	String tipoProducto		= "readonly";
	String respuesta 		= "-";
	
	Acceso = AccesoU.mapeaRegId(conEnoc, codigoPersonal);
	
	switch(accion){
		case 0:{
			MapaNuevoCurso.setPlanId(planId);
			MapaNuevoCurso.setCursoId(cursoId);
			MapaNuevoCurso.setVersionId(versionId);
			if(MapaNuevoCursoU.existeReg(conEnoc, cursoId, planId, versionId)){
				MapaNuevoCurso.mapeaRegId(conEnoc, cursoId, planId, versionId);
			}			
		}break;
		case 1:{//Guardar
			MapaNuevoCurso.setPlanId(planId);
			MapaNuevoCurso.setCursoId(cursoId);
			MapaNuevoCurso.setVersionId(versionId);			
			if(MapaNuevoCursoU.existeReg(conEnoc, cursoId, planId, versionId)){
				MapaNuevoCurso.mapeaRegId(conEnoc, cursoId, planId, versionId);
				MapaNuevoCurso.setDescripcion(request.getParameter("Descripcion"));
				MapaNuevoCurso.setProyecto(request.getParameter("Proyecto"));
				if(MapaNuevoCursoU.updateReg(conEnoc, MapaNuevoCurso)){
					respuesta = "<font size=2 color=blue><b>¡ Grabado !</b></font>";
				}else{
					respuesta = "<font size=2 color=red><b>Ocurri&oacute; un error al modificar el plan. Int&eacute;ntelo de nuevo</b></font>";
				}
			}
		}break;
	}	
%>
<body>
<div class="container-fluid">
	<h2><spring:message code="aca.mapa.diamante.cursoAccion.Frase1"/> <small class="text-muted fs-4">(<%=planId%> - <%=cursoId%>)</small></h2>
	<form id="frmProductos" name="forma" action="cursoAccion">
	<input type="hidden" id="planId" name="planId" value="<%=planId%>" />
	<input type="hidden" id="cursoId" name="cursoId" value="<%=cursoId%>" />
	<input type="hidden" id="versionId" name="versionId" value="<%=versionId%>" />
	<input type="hidden" id="Accion" name="Accion" value="1" />
	<div class="alert alert-info">
		<a class="btn btn-primary" href="editaUnidad?planId=<%=planId%>&versionId=<%=versionId%>&cursoId=<%=cursoId%>"><spring:message code="aca.Regresar"/></a>
	</div>
<% if(!respuesta.equals("-")){%>
	<div><%=respuesta%></div><br>
<% }%>
		<div class="row">
			<div class="span3">
				<label><b><spring:message code="aca.mapa.diamante.cursoAccion.Frase2"/></b></label>
<%		
		if(aca.catalogo.CatCarreraUtil.getCoordinador(conEnoc, carreraId ).equals(codigoPersonal) || Acceso.getAdministrador().equals("S")){
			tipoProducto = "";
		}		
%>				
				<textarea id="Descripcion" name="Descripcion" cols="100" rows="3" <%=tipoProducto%>><%=MapaNuevoCurso.getDescripcion()%></textarea>							
				<br><br>
				<label><b><spring:message code="aca.mapa.diamante.cursoAccion.Frase3"/></b></label>
				<textarea id="Proyecto" name="Proyecto" cols="100" rows="3"><%=MapaNuevoCurso.getProyecto()%></textarea>
			</div>
		</div>
		<div class="alert alert-info">
			<input type="button" class="btn btn-primary" style="button" value="Guardar" onclick="revisaForma();" />
		</div>

	</form>
</div>
</body>
<%@ include file= "../../cierra_enoc.jsp" %>