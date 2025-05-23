<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>

<jsp:useBean id="vacacion" scope="page" class="aca.alumno.AlumVacacion" />
<jsp:useBean id="vacacionU" scope="page" class="aca.alumno.AlumVacacionLista" />

<%
	String codigoPersonal	= (String) session.getAttribute("codigoAlumno");
	String planId			= aca.alumno.PlanUtil.getPlanActual(conEnoc,codigoPersonal);
	String carreraId		= aca.plan.PlanUtil.getCarreraId(conEnoc,planId);	
	String nivelId			= aca.alumno.PlanUtil.getCarreraNivel(conEnoc,carreraId);
	String modalidadId		= aca.alumno.AcademicoUtil.getModalidadId(conEnoc,codigoPersonal);	
	boolean esInscrito		= aca.alumno.AlumUtil.esInscrito(conEnoc, codigoPersonal);
	boolean datosCapturados = false;
	
	vacacion.setNivelId(nivelId);
	vacacion.setModalidadId(modalidadId);
	if (vacacionU.existeReg(conEnoc, nivelId, modalidadId)){
		datosCapturados = true;
	}
%>
<body>
<table cellpadding="2" cellspacing="2"  width="95%" align="center">
  <tr>
	<th>Impresión de Constancias [ <a href="javascript:history.back()">&lsaquo;&lsaquo; <spring:message code="aca.Regresar"/></a> ]</th>
  </tr>
  <tr>
	<td>
		Dele <b>click</b> sobre el <b>nombre</b> del alumno para imprimir la carta.
	</td>
  </tr>
<%	if ( datosCapturados){ %>  
<%
		if(nivelId.equals("5")){ %>
  <tr>
	<td>
	  <iframe width="100%" height="400" src="view2?carreraId=<%=carreraId%>"></iframe>
	</td>
  </tr>
<%		}else{ %>  
  <tr>
	<td>
	  <iframe width="100%" height="400" src="view?carreraId=<%=carreraId%>"></iframe>
	</td>
  </tr>
<%		}	
	}else{ %>
  <tr>
	<td>¡ No están capturadas las fechas de vacaciones para <%=aca.catalogo.CatNivelUtil.getNivelNombre(conEnoc, nivelId) %> !</td>
  </tr>	
<%	} %>  
</table>
</body>
<%@ include file= "../../cierra_enoc.jsp" %>