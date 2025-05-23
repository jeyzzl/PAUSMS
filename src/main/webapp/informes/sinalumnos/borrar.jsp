<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import= "aca.carga.CargaGrupoEvaluacionUtil"	%>

<jsp:useBean id="curso" scope="page" class="aca.carga.CargaGrupoCurso"/>
<jsp:useBean id="cursoU" scope="page" class="aca.carga.CargaGrupoCursoUtil"/>
<jsp:useBean id="grupo" scope="page" class="aca.carga.CargaGrupo"/>
<jsp:useBean id="grupoU" scope="page" class="aca.carga.CargaGrupoUtil"/>
<jsp:useBean id="evalU" scope="page" class="aca.carga.CargaGrupoEvaluacionUtil"/>

<%
	String sCursoCargaId		= request.getParameter("CursoCargaId");
	String sCarga				= request.getParameter("CargaId");
	String sCurso				= request.getParameter("CursoId");
	String sEvaluaId			= "";
	String sElimina				= "X";
	int i						= 0;

	ArrayList lisEvaluacion		= evalU.getLista(conEnoc, sCursoCargaId, "Order by 1");
	
	curso.setCursoCargaId(sCursoCargaId);
	curso.setCursoId(sCurso);
	if (cursoU.existeReg(conEnoc, sCursoCargaId, sCurso) == true){
		cursoU.deleteGrupo(conEnoc, sCursoCargaId);
		sElimina = "true";
	} else {
		sElimina = "false";
	}
		
	if (!sElimina.equals("X")){
		grupo.setCursoCargaId(sCursoCargaId);
		if (grupoU.existeReg(conEnoc,sCursoCargaId) == true){
				grupoU.deleteReg(conEnoc, sCursoCargaId); 
		} else {
				sElimina = "false";
		}
	
		for (i=0; i<lisEvaluacion.size(); i++){
			aca.carga.CargaGrupoEvaluacion eval = (aca.carga.CargaGrupoEvaluacion) lisEvaluacion.get(i);
			if (evalU.existeReg(conEnoc, sCursoCargaId, eval.getEvaluacionId()) == true){		
				evalU.deleteReg(conEnoc, sCursoCargaId, eval.getEvaluacionId());
				sElimina = "true";
			} else {
				sElimina = "false";
			}
		}
	}
	
	if (sElimina.equals("true")){	
%>

<center>
  <font size="3"><strong>La Materia fue eliminada ....</strong></font> 
</center>
<%
	} else {
%>
<br>
<center>
  <strong><font color="#FF0000" size="3">La materia no pudo ser eliminada...</font></strong>
</center>
<%	
	}  
	lisEvaluacion		= null;
	evalU				= null;
%>	
<meta http-equiv='REFRESH' content='0;URL=sinalumno?CargaId=<%=sCarga%>'>
<%@ include file= "../../cierra_enoc.jsp" %>
