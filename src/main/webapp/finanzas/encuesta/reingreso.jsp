<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="UnidadUtil" scope="page" class="aca.disciplina.CondAlumnoUtil"/>

<%  
	String fechaInicio		= request.getParameter("fechaInicio");
	String fechaFinal		= request.getParameter("fechaFinal");
	
	ArrayList lisUnidad 		= new ArrayList();
	lisUnidad = UnidadUtil.getListFechas(conEnoc, fechaInicio, fechaFinal, " ORDER BY SUBSTR(FECHA,7,4)||SUBSTR(FECHA,4,2)||SUBSTR(FECHA,1,2), IDREPORTE");
	
	String tipoReporte		= "";
%>

<table style="width:98%" align="center"   >
  <tr>
    <td height="24" colspan="7" align="center">
	  <strong><font size="4">Sistema de disciplina</font></strong>
    </td>
  </tr>
  <tr>
    <td colspan="7" align="center">
	  <strong><font size="3">Unidades aplicadas del <%=fechaInicio%> al <%=fechaFinal%></font></strong>
    </td>
  </tr>
  <tr align="center"> 
    <th width="4%" align="center"><spring:message code="aca.Numero"/></th>
    <th width="7%" align="center"><spring:message code="aca.Fecha"/></th>
    <th width="8%" align="center"><spring:message code="aca.Matricula"/></th>
    <th width="32%" align="center"><spring:message code="aca.Nombre"/></th>
    <th width="17%" align="center">Juez</th>
    <th width="20%" align="center">Reporte</th>
    <th width="8%" align="center"><spring:message code="aca.Tipo"/></th>    
    <th width="4%" align="center">Valor</th>
  </tr>
<% 
	for (int i=0;i<lisUnidad.size(); i++){ 
		aca.disciplina.CondAlumno alumUnidad = (aca.disciplina.CondAlumno) lisUnidad.get(i);
		tipoReporte = aca.disciplina.CondReporteUtil.tipoReporte(conEnoc,alumUnidad.getIdReporte());
		if (tipoReporte.equals("D")){
			tipoReporte = "Falta";
		}else if (tipoReporte.equals("C")){
			tipoReporte = "Elogio";
		}else{
			tipoReporte = "Otro";
		}
%>
  <tr align="center">
    <td align="center"><%=i+1%></td>
    <td align="left"><%=alumUnidad.getFecha()%></td>
    <td align="center"><%=alumUnidad.getMatricula()%></td>
    <td align="left"><%=aca.alumno.AlumUtil.getNombreAlumno(conEnoc,alumUnidad.getMatricula(),"NOMBRE")%></td>
    <td align="left"><%=aca.disciplina.CondJuezUtil.nombreJuez(conEnoc,alumUnidad.getIdJuez())%></td>
    <td align="left"><%=aca.disciplina.CondReporteUtil.nombreReporte(conEnoc,alumUnidad.getIdReporte())%></td>
    <td align="center"><%=tipoReporte%></td>
    <td align="center"><%=alumUnidad.getCantidad()%></td>
  </tr>
<% } %>  
</table>
<br> <br>
<div align="center">
  <font size="3" face="Times New Roman, Times, serif" color="#000099"><b>Fin del Listado...</b></font>
</div>

<%@ include file= "../../cierra_enoc.jsp" %>