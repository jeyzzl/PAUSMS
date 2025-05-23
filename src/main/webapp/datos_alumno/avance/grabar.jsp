<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<jsp:useBean id="AlumPlan" scope="page" class="aca.alumno.AlumPlan"/>
<jsp:useBean id="AlumPlanU" scope="page" class="aca.alumno.PlanUtil"/>

<!-- inicio de estructura -->
<%
	String codigoAlumno		= (String) session.getAttribute("codigoAlumno");
	String plan				= request.getParameter("plan");
	String evento			= request.getParameter("evento");
	String avance			= request.getParameter("avance");
	boolean ok	= false;
	
	AlumPlan.setCodigoPersonal(codigoAlumno);
	AlumPlan.setPlanId(plan);
	if (AlumPlanU.existeReg(conEnoc,codigoAlumno, plan )){
		AlumPlan = AlumPlanU.mapeaRegId(conEnoc,AlumPlan.getCodigoPersonal(), AlumPlan.getPlanId());
		
		conEnoc.setAutoCommit(false);
		AlumPlan.setAvanceId(avance);
		AlumPlan.setEvento(evento);
		AlumPlan.setPermiso(evento);
		if (AlumPlanU.updateReg(conEnoc, AlumPlan)){
			if (evento.equals("S")){
				AlumPlan.setFGraduacion("01/06/2008");
			}else{
				AlumPlan.setFGraduacion(null);
			}
			if (AlumPlanU.updateFechaGraduacion(conEnoc, AlumPlan.getFGraduacion(), AlumPlan.getCodigoPersonal(), AlumPlan.getPlanId())){				
				ok=true;
			}else{
				conEnoc.rollback();
			}
		}
		conEnoc.setAutoCommit(true);
	}else{
%>	
<center><strong>Registro no encontrado</strong></center>	
<%	} 

	if(ok){ %>
<center><strong>Los datos fueron Actualizados</strong></center>		 
<%	}%>

<meta http-equiv='REFRESH' content='0;URL=avance?PlanId=<%=plan%>'>
<%@ include file= "../../cierra_enoc.jsp" %>
