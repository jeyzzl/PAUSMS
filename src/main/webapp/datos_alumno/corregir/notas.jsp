<%@ page import="java.text.*" %>
<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>

<jsp:useBean id="alumnoCurso" scope="page" class="aca.vista.AlumnoCurso"/>
<jsp:useBean id="alumnoCursoUtil" scope="page" class="aca.vista.AlumnoCursoUtil"/>
<jsp:useBean id="alumno" scope="page" class="aca.alumno.AlumPersonal"/>
<jsp:useBean id="plan" scope="page" class="aca.alumno.AlumPlan"/>
<jsp:useBean id="AlumPlanU" scope="page" class="aca.alumno.PlanUtil"/>
<jsp:useBean id="alumnoUtil" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="planUtil" scope="page" class="aca.plan.PlanUtil"/>

<script type="text/javascript">
	function cambiaVer(){
		if(document.forma.ver.value=="N")document.forma.ver.value="C";
		else document.forma.ver.value='N';
		document.forma.submit();
	}
	function recarga(){
		document.forma.submit();
	}
</script>
<%
	DecimalFormat getFormato	= new DecimalFormat("###,##0.00;(###,##0.00)");
	String matricula			= (String) session.getAttribute("codigoAlumno");
	String planid				= (String) request.getParameter("plan");
	String estado 				= "";
	String sBgcolor				= "";
	String institucion 			= (String)session.getAttribute("institucion");

	alumno = alumnoUtil.mapeaRegId(conEnoc, matricula);
	if(planid==null){
		plan.mapeaRegId(conEnoc, matricula);
		planid	= plan.getPlanId();
	}else 
		plan.mapeaRegId(conEnoc,matricula,planid);
	
	String planActual			= plan.getPlanId();
	ArrayList<aca.vista.AlumnoCurso> lisCursosAlumno 	= alumnoCursoUtil.getListAlumno(conEnoc,matricula," AND PLAN_ID ='"+planid+"' AND ESTADO != 'I' ORDER BY NOMBRE_CURSO");
	ArrayList<String> planes			= AlumPlanU.getPlanesAlumno(conEnoc, matricula);	
%>

<table style="width:100%"  cellpadding="10" align="center">
<tr>
  <td valign="top" align="center">
    <form name="forma" action="notas.jsp" method='post'>
    <select name="plan" onchange='javascript:recarga()'>
<%
	for(int i=0;i<planes.size();i++){%>
  		<option value='<%=(String)planes.get(i)%>' <%if(planActual.equals((String)planes.get(i)))out.print("Selected");%> ><%=planUtil.getNombrePlan(conEnoc,(String)planes.get(i))%></option>
<%	}%>
  	</select>  		
    </form>
    <font size="2"><strong><%=institucion.toUpperCase()%> - CORRECCION DE CALIFICACIONES</strong></font><br>
    <strong>Alumno: <strong>[<%=matricula%>] [<%=alumno.getNombre()%> <%=alumno.getApellidoPaterno()%> <%=alumno.getApellidoMaterno()%>]
  </td>
</tr>
<tr valign="top">
  <td align="center">
    <fieldset style="padding-bottom:10; margin-bottom: 6;">
	<font size="1"><legend>
	<strong>Plan: <strong>[<%=plan.getPlanId()%>] <strong>Carrera: <strong>[<%=alumnoUtil.getCarrera(conEnoc,matricula,planActual)%>]
	</legend></font>
	<table style="width:100%"  >
	<tr>
      <th width="2%"><spring:message code="aca.Numero"/></th>
	  <th width="9%"><spring:message code="aca.Acta"/></th>
  	  <th width="6%">St.Acta</th>	 
	  <th width="28%"><spring:message code="aca.Materia"/></th>
	  <th width="3%">Crd.</th>
	  <th width="29%"><spring:message code="aca.Maestro"/></th>
	  <th width="4%"><spring:message code="aca.Nota"/></th>
	  <th width="7%">F.Nota</th>
	  <th width="3%">Ex.</th>
	  <th width="9%">F.Extra</th>
	  <th width="9%">Correc.</th>
	  </tr>
<%	for (int i=0; i < lisCursosAlumno.size(); i++){
		alumnoCurso = (aca.vista.AlumnoCurso) lisCursosAlumno.get(i);
		if (alumnoCurso.getEstado().equals("1")) estado = "Creada";
		else if (alumnoCurso.getEstado().equals("2")) estado = "Ordinario";
		else if (alumnoCurso.getEstado().equals("3")) estado = "Extra";
		else if (alumnoCurso.getEstado().equals("4")) estado = "Cerrada";
		else if (alumnoCurso.getEstado().equals("5")) estado = "Registro";
		else estado = "error";
		if ((i % 2) == 0 ) sBgcolor = sColor; else sBgcolor = "";
%>
  	<tr <%=sBgcolor%>>
      <td align="center"><strong><em><%=i+1%></em></strong></td>
	  <td><%=alumnoCurso.getCursoCargaId()%></td>
	  <td><%=estado%></td>
	  <td title="Cambiar Nota">
<%		if (!alumnoCurso.getTipoCalId().equals("3") && !alumnoCurso.getEstado().equals("2")&& !alumnoCurso.getEstado().equals("3")){%>
		<a href="grabar.jsp?cursoCargaId=<%=alumnoCurso.getCursoCargaId()%>&materia=<%=alumnoCurso.getNombreCurso()%>&plan=<%=planActual%>">
			<%=alumnoCurso.getNombreCurso()%>
		</a>
<%}else{%>
		<%=alumnoCurso.getNombreCurso()%>
<%}%>
	  </td>
	  <td><%=alumnoCurso.getCreditos()%></td>
	  <td><%=aca.vista.MaestrosUtil.getNombreMaestro(conEnoc, alumnoCurso.getMaestro(), "NOMBRE" ) %></td>
	  <td align="right" class="ayuda mensaje Es una corrección de nota"><strong><%=alumnoCurso.getNota()%></strong></td>
	  <td><%=alumnoCurso.getFEvaluacion()%></td>
	  <td align="right">
<%		if (alumnoCurso.getNotaExtra().equals("0")){
			out.println("-");
		}else{
			out.println(alumnoCurso.getNotaExtra());
		}
%>
	  </td>
	  <td>
<%		if (alumnoCurso.getNotaExtra().equals("0")){
			out.println("");
		}else{
			out.println(alumnoCurso.getFExtra());
		}
%>
	  </td>
	  <td align="center">
	    <strong>
<%
	if (alumnoCurso.getCorreccion().equals("S")){
		out.println("SI "+"["+aca.kardex.KrdxCursoAct.getPromedioAntesDeCorreccion(conEnoc,alumnoCurso.getCursoCargaId(),alumnoCurso.getCodigoPersonal())+"]");
	}else{
		out.println("NO");	  
	}
%>		</strong></td>
	  </tr>  
<%	} // finaliza for de cursos del alumno %>
	</table>
    </fieldset>
  </td>
</tr>
</table>
<%@ include file= "../../cierra_enoc.jsp" %>