<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="evalU" scope="page" class="aca.carga.CargaGrupoEvaluacionUtil"/>
<jsp:useBean id="curso" scope="page" class="aca.carga.CargaGrupoCurso"/>
<jsp:useBean id="cursoU" scope="page" class="aca.carga.CargaGrupoCursoUtil"/>
<jsp:useBean id="grupo" scope="page" class="aca.carga.CargaGrupo"/>
<jsp:useBean id="grupoU" scope="page" class="aca.carga.CargaGrupoUtil"/>
<jsp:useBean id="cargaAcaU" scope="page" class="aca.vista.CargaAcaUtil"/>

<%	
	String sCarga				= request.getParameter("CargaId");
	String sCursoCargaId		= "";
	String sCurso				= "";
	int i=0,j=0;
	
	ArrayList lisCarga			= cargaAcaU.getListSinAlumno(conEnoc, sCarga, "Order by 1");	
	ArrayList lisEvaluacion		= new ArrayList();
	
	for (i=0; i<lisCarga.size(); i++){
		aca.vista.CargaAcademica cargaA	= (aca.vista.CargaAcademica) lisCarga.get(i);
		sCursoCargaId = cargaA.getCursoCargaId();
		sCurso = cargaA.getCursoId();	
		
		// Codigo que borra las estrategias de evaluacion.
		lisEvaluacion  = evalU.getLista(conEnoc, sCursoCargaId, "Order by 1");
		for (j=0; j<lisEvaluacion.size(); j++){
			aca.carga.CargaGrupoEvaluacion eval = (aca.carga.CargaGrupoEvaluacion) lisEvaluacion.get(j);
			if (evalU.existeReg(conEnoc, sCursoCargaId, eval.getEvaluacionId()) == true){		
				evalU.deleteReg(conEnoc, sCursoCargaId, eval.getEvaluacionId());
			}
		}
		
		// Codigo que borra los cursos y el Grupo asociados a este numero de acta.
		curso.setCursoCargaId(sCursoCargaId);
		curso.setCursoId(sCurso);
		if (cursoU.existeReg(conEnoc, sCursoCargaId, sCurso ) == true){
			if (cursoU.deleteGrupo(conEnoc, sCursoCargaId)){
				grupo.setCursoCargaId(sCursoCargaId);
				if (grupoU.existeReg(conEnoc, sCursoCargaId ) == true){
					grupoU.deleteReg(conEnoc, sCursoCargaId);
				}
			}
		}
	}
	lisCarga 			= null;
	cargaAcaU			= null;
	lisEvaluacion		= null;
	evalU				= null;
%>
	
<meta http-equiv='REFRESH' content='0;URL=sinalumno?CargaId=<%=sCarga%>'>
<%@ include file= "../../cierra_enoc.jsp" %>
