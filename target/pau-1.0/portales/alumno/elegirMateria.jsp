<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="alumno" scope="page" class="aca.alumno.AlumPersonal"/>
<jsp:useBean id="AlumUtil" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="cargaGrupo" scope="page" class="aca.carga.CargaGrupo"/>
<jsp:useBean id="plan" scope="page" class="aca.alumno.AlumPlan"/>
<jsp:useBean id="alumAca" scope="page" class="aca.alumno.AlumAcademico"/>
<jsp:useBean id="AcademicoU" scope="page" class="aca.alumno.AcademicoUtil"/>
<jsp:useBean id="MatriculaCursos" scope="page" class="aca.matricula.MatriculaUtil"/>
<jsp:useBean id="alumnoCurso" scope="page" class="aca.vista.AlumnoCurso"/>
<jsp:useBean id="AlumnoCursoU" scope="page" class="aca.vista.AlumnoCursoUtil"/>

<%-- <jsp:include page="../menu.jsp" /> --%>
<%@ include file= "menu.jsp" %>

<% 
	String matricula = (String) session.getAttribute("codigoAlumno");

	String accion = request.getParameter("Accion")==null?"":request.getParameter("Accion");

	if(accion.equals("1")){
		aca.kardex.KrdxCursoAct KrdxCursoAct = new aca.kardex.KrdxCursoAct();
		
		conEnoc.setAutoCommit(false);	
		
		KrdxCursoAct.setCodigoPersonal(matricula);
		KrdxCursoAct.setCursoCargaId(request.getParameter("cursoCargaIdAnterior"));
		if(KrdxCursoAct.deleteReg(conEnoc)){//Eliminar cursoCargaId anterior
			
			//Insertar cursoCargaId nuevo
			KrdxCursoAct.setCodigoPersonal(matricula);
			KrdxCursoAct.setCursoCargaId(request.getParameter("CursoCargaId"));
			KrdxCursoAct.setCursoId(request.getParameter("CursoId"));
			KrdxCursoAct.setCursoId2(aca.carga.CargaGrupoCursoUtil.cursoIdOrigen(conEnoc, request.getParameter("CursoCargaId")));
			KrdxCursoAct.setTipoCalId("I");
			KrdxCursoAct.setTipo("O");
			if(KrdxCursoAct.insertReg(conEnoc)){
				conEnoc.commit();
				//response.sendRedirect("materias");
				out.print("<div class='alert alert-success'><b>Registrado...<a class='btn btn-primary' href='materias'>Regresar</a></div>");
			}else{
				conEnoc.rollback();
				//System.out.println("Ocurrio un error al grabar nuevo cursoCargaId");
				out.print("<div class='alert alert-success'><b>Error al grabar...<a class='btn btn-primary' href='materias'>Regresar</a></div>");
				//response.sendRedirect("materias");
			}
		}else{
			conEnoc.rollback();
			//System.out.println("Ocurrio un error al eliminar cursoCargaId Anterior");
			out.print("<div class='alert alert-success'><b>Error al grabar...<a class='btn btn-primary' href='materias'>Regresar</a></div>");
			//response.sendRedirect("materias");
		}
		
		conEnoc.setAutoCommit(true);
		
	}else{

	
	String cursoId = request.getParameter("cursoId");
	String cargaId = request.getParameter("cargaId");
	String cursoCargaId = request.getParameter("cursoCargaId");
	String materia = request.getParameter("materia");
	String profesor = request.getParameter("profesor");

	alumno = AlumUtil.mapeaRegId(conEnoc,matricula);
	cargaGrupo.mapeaRegId(conEnoc, cursoCargaId);
	plan.mapeaRegId(conEnoc, matricula);
	
	String planId = plan.getPlanId();
	
	alumAca = AcademicoU.mapeaRegId(conEnoc, matricula);
	String modalidadId = alumAca.getModalidadId();
	
	alumnoCurso = AlumnoCursoU.mapeaRegId(conEnoc, matricula, cursoCargaId);
%>


<head>
<style>
	#actual td{
		border:1px solid green;
	}
</style>
</head>
<body>
<table style="width:80%; margin:0 auto;">
	<tr>
		<td align="center">
			<h3><%=alumno.getNombre()+" "+alumno.getApellidoPaterno()+" "+alumno.getApellidoMaterno() %></h3>
			<h4>Materia: <%=materia %></h4>
			<h4>Maestro: <%=profesor %></h4>
			<h3>Grupo Actual: <%=cargaGrupo.getComentario() %></h3>
		</td>
	</tr>
</table>
<br>
<%
	ArrayList<String> materiasDisponibles = MatriculaCursos.getListCursosDispAsignados(conEnoc, matricula, cargaId, planId, modalidadId, cursoId);
	
	aca.vista.AlumnoCurso AlumnoCurso = new aca.vista.AlumnoCurso();
	aca.carga.CargaGrupo CargaGrupo = new aca.carga.CargaGrupo();
	
	String sOrden 			= "";
	String sCarreraId 		= "";
	String sCicloId 		= "";
	String sCursoId 		= "";
	String sCursoCargaId 	= "";
	String sNombreCurso 	= "";
	String sGrupo 			= "";
	String sLetra 			= "";
	String sProfesor 		= "";
	String sCarrera 		= "";
	String sLCarrera 		= "";
	String sHoras 			= "";
	String sCreditos 		= "";
	String sModalidad 		= "";
	String sBloque 			= "";
	String optativa			= "";
	boolean tieneHorario	= false;
	boolean recuperacion = false;
	float credXCiclo 		= 84;
	credXCiclo = aca.vista.AlumnoCursoUtil.getCreditosPermitidosXPeriodo(conEnoc, matricula, cargaId);
%>

<table class="table table-condensed table-small table-noHover table-bordered table-striped" width="50%" align="center">
	<tr>
		<td colspan="6"><h5>Haz click sobre el grupo al que te deseas cambiar</h5></td>
	</tr>
	<tr>
		<th><spring:message code="aca.Maestro"/></th>
		<th width="1%">Grupo</th>
		<th width="1%"><spring:message code="aca.Bloque"/></th>
		<th width="1%">Horas</th>
		<th width="1%"><spring:message code="aca.Modalidad"/></th>
	</tr>
<%	for(int i=0; i<materiasDisponibles.size(); i++){
		String [] arr =  materiasDisponibles.get(i).split("~");
		sCursoCargaId	= arr[4];
		sOrden 			= arr[0];
		sCarreraId 		= arr[1];
		sCicloId 		= arr[2];
		sCursoId 		= arr[3];
		sNombreCurso 	= arr[5];
		sGrupo			= arr[6];
		sLetra			= arr[7];
		sProfesor 		= arr[8];
		sCarrera 		= arr[9];
		sLCarrera 		= arr[10];
		sHoras 			= arr[11];
		sCreditos 		= arr[12];
		sModalidad 		= arr[13];
		sBloque 		= arr[14];
		optativa 		= arr[15];
		tieneHorario 	= Boolean.parseBoolean(arr[16]);
		
		if(aca.carga.CargaPermisoUtil.esCarreraRecuperacion(conEnoc, cargaId, sCarreraId) && sModalidad == "1"){
			if(aca.vista.AlumnoCursoUtil.yaLlevoLaMateria(conEnoc, matricula, cursoId)) recuperacion = true;
			else if(Boolean.parseBoolean(session.getAttribute("admin")+"")) recuperacion = true;
			else if(!aca.vista.AlumnoCursoUtil.terminoCiclo(conEnoc, matricula, cargaId, credXCiclo) || cargaId.substring(4).equals("1F")) recuperacion = true;
		}
		else{
			recuperacion=true;
		}
		
		boolean esLinea = aca.catalogo.ModalidadUtil.esLinea(conEnoc, aca.carga.CargaGrupoUtil.getModalidad(conEnoc, sCursoCargaId));
		CargaGrupo.mapeaRegId(conEnoc, sCursoCargaId);
		String nivelCarrera = aca.catalogo.CatCarreraUtil.getNivelId(conEnoc, CargaGrupo.getCarreraId());
		String tipoMat = aca.plan.CursoUtil.getTipoCurso(conEnoc, sCursoId);
		
		String mensaje = "";
		if(sProfesor.equals("Sin Maestro") || sProfesor.equals("0000000") || sProfesor.equals("VACIO")) mensaje="Sin Maestro";
		if(!recuperacion) mensaje = "Sin Recuperación";
		if(!tieneHorario&&!esLinea&&!nivelCarrera.equals("5")&&!tipoMat.equals("5")&&!modalidadId.equals("19")&&!modalidadId.equals("9")&&!modalidadId.equals("8")){
			mensaje="Sin Horario (reporta en SOPORTE esta materia)";
		}
		
		boolean elegible = false;
		String condiciones = "class='ayuda mensaje "+mensaje+"'"; 
		if(mensaje.equals("")){
			condiciones = "style=\"cursor:pointer;\" class=\""+(CargaGrupo.getComentario()!=null&&!CargaGrupo.getComentario().trim().equals("")?"ayuda mensaje "+CargaGrupo.getComentario():"")+"\" onclick=\"asignarMateria(this,'"+sCursoCargaId+"','"+cursoId+"','"+cursoCargaId+"');\"";
			elegible = true;
		}
		
		if(!elegible)continue;
	%>
		<tr <%=condiciones%> <%if(CargaGrupo.getComentario().equals(cargaGrupo.getComentario()))out.print("id='actual'");%>>
			<td style="text-align:left;"><%=sProfesor%></td>
			<td style="text-align:center;"><%=CargaGrupo.getComentario()%></td>
			<td style="text-align:center;"><%=cursoId%></td>
			<td style="text-align:center;"><%=sHoras%></td>
			<td style="text-align:center;"><%=sModalidad%></td>
		</tr>
	<%} %>
</table>

<script>
	function asignarMateria($this, cursoCargaId, cursoId, cursoCargaIdAnterior){
		if(jQuery($this).attr("id")){
			alert("Tu te encuentras actualmente en este grupo");
		}else{
			if(confirm("¿Esta seguro que deseas cambiarte a este grupo?")){
				location.href="elegirMateria?Accion=1&CursoCargaId="+cursoCargaId+"&CursoId="+cursoId+"&cursoCargaIdAnterior="+cursoCargaIdAnterior;
			}
		}
	}

	$('.nav-tabs').find('.materias').addClass('active');
</script>
<%} %>
</body>
<%@ include file= "../../cierra_enoc.jsp" %>
