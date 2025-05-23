<%@ include file= "../../con_enoc.jsp" %>
<jsp:useBean id="Kardex" scope="page" class="aca.kardex.KrdxCursoAct"/>
<jsp:useBean id="KardexU" scope="page" class="aca.kardex.ActualUtil"/>
<jsp:useBean id="Materia" scope="page" class="aca.plan.MapaCurso"/>
<jsp:useBean id="MateriaU" scope="page" class="aca.plan.CursoUtil"/>
<%
	int accion			= Integer.parseInt(request.getParameter("Accion"));	
	String cargaId		= request.getParameter("cargaId");
	int rowUpdate		= 0;	
	
	switch(accion){
		case 5:{	
			// Lista e materias con tipo 5(Pendiente) y 6(Diferida)
			ArrayList<aca.kardex.KrdxCursoAct> lisNotas = KardexU.getListDiferidasCarga(conEnoc,cargaId," ORDER BY CODIGO_PERSONAL");
			conEnoc.setAutoCommit(false);
			for (int i=0;i<lisNotas.size();i++){
				Kardex = (aca.kardex.KrdxCursoAct) lisNotas.get(i);
				Materia = MateriaU.mapeaRegId(conEnoc,Kardex.getCursoId());
				
				if (Integer.parseInt(Kardex.getNota().trim())>= Integer.parseInt(Materia.getNotaAprobatoria()))
					Kardex.setTipoCalId("1");
				else
					Kardex.setTipoCalId("2");
				
				if (Kardex.updateReg(conEnoc)) 
					rowUpdate++;
				
			}
			if (lisNotas.size() == rowUpdate){
				conEnoc.commit();
%>
				alert("Las notas diferidas han sido canceladas...");
				ocultaCarga('<%=cargaId%>');
<%
			}else{
				conEnoc.rollback();
%>
				alert("Error en la cancelación..¡¡");
<%
			}
			conEnoc.setAutoCommit(true);
		}break;
		
	}
%>
<%@ include file= "../../cierra_enoc.jsp" %>