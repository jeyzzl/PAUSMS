<%@ page import="java.text.*" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.vista.spring.AlumnoCurso" %>
<%@ page import="aca.catalogo.spring.CatTipoCurso" %>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<br>
<!-- inicio de estructura -->
<%	
	String matricula			= request.getParameter("matricula");
	String carga				= request.getParameter("carga");
	String promedio 			= request.getParameter("promedio");
	String alumPromedio			= request.getParameter("alumPromedio");	
	DecimalFormat getformato	= new DecimalFormat("##0.00;(##0.00)");
	
	int n_row, n_nota, n_temp, n_evalua, n_creditos;
	double d_promedio;
	String nota = "";
	
	String nombre 				= (String)request.getAttribute("nombre");
	
	List<AlumnoCurso> lisCursos 				= (List<AlumnoCurso>)request.getAttribute("lisCursos");
	HashMap<String,CatTipoCurso> mapaTipos 		= (HashMap<String,CatTipoCurso>) request.getAttribute("mapaTipos");
%>
<div class="container-fluid">
<table id="noayuda" style="width:100%; margin:0 auto;">
  <tr><td colspan="8" align="center" class="titulo2">Alumno: [ <%=matricula%> ] -- [ <%=nombre%> ]</td></tr>
  <tr><td colspan="8" align="center">&nbsp;</td></tr>
</table> 
<table class="table table-bordered">
<thead class="table-info">
  <tr> 
    <th width="2%" height="21"><strong><spring:message code="aca.Numero"/></strong></th>
    <th width="8%" align="center"><strong><spring:message code="aca.Clave"/></strong></th>
    <th width="44%" align="center"><strong><spring:message code="aca.Materia"/></strong></th>
    <th width="5%" align="center"><strong><spring:message code="aca.Tipo"/></strong></th>
    <th width="6%" align="center"><strong>¿evalua?</strong></th>
	<th width="6%" align="center"><strong>Cred.</strong></th>
    <th width="11%" align="center"><strong><spring:message code="aca.Nota"/></strong></th>
    <th width="10%" align="center"><strong><spring:message code="aca.Extra"/></strong></th>
    <th width="14%" align="center"><strong><spring:message code="aca.Estado"/></strong></th>
  </tr>
 </thead>
<%
	int row =0;
	for (AlumnoCurso cursos: lisCursos){		
		row++;
		String evalua = cursos.getCreditos().equals("0")?"NO":"SI";
		
		String tipo = "X";
		if (mapaTipos.containsKey(cursos.getTipoCalId())){
			tipo = mapaTipos.get(cursos.getTipoCalId()).getCorto();
		}
%>
  <tr class="tr2"> 
    <td width="2%" height="21"><%=row%></td>
    <td width="8%" align="center"><%=cursos.getCursoId()%></td>
    <td width="49%" align="left"><%=cursos.getNombreCurso()%></td>
    <td width="49%" align="left"><%=tipo%></td>
    <td width="6%" align="center"><%=evalua%></td>
	<td width="6%" align="center"><%=cursos.getCreditos()%></td>
    <td width="11%" align="center"><%=cursos.getNota()%></td>
    <td width="10%" align="center"><%=cursos.getNotaExtra()%></td>
    <td width="14%" align="center"><%= cursos.getEstado()%></td>
  </tr>
  <%
	} // fin del for
%>
  <tr> 
    <th colspan="8" align="center"><strong>Promedio Ponderado del Alumno = [ <%=promedio%> 
      ]</strong></th>
  </tr>
  <tr>
  		<td colspan="8">El promedio no toma en cuenta materias <b>Asignadas</b>, <b>Inscritas</b>, <b>De Baja</b> y <b>Diferidas</b></td>
  </tr>
</table>
<br>
<table id="noayuda" style="width:100%; margin:0 auto;">    
  <tr> 
    <td colspan="8" align="center">   
    El promedio ponderado se calcula con la formula Suma(Nota*Creditos) / Suma(creditos)<br>
    ¡¡ Se consideran solamente las materias acreditadas !!    
    </td>
  </tr>
</table>
</div>