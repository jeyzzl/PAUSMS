<%@ page import= "java.util.List"%>
<%@ page import= "aca.vista.spring.AlumnoCurso"%>
<%@ page import= "aca.alumno.spring.AlumPersonal"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import="java.util.*"%>

<jsp:useBean id="acu" scope="page" class="aca.vista.AlumnoCursoUtil"/>

<style>
	table.border{
		border: 1px solid #888;
	}
	table.border th,table.border td{
		border: 1px solid #888;
	}
</style>

<!-- inicio de estructura -->
<%
 	String codigoPersonal= (String) session.getAttribute("codigoEmpleado");
	String cursoCargaId	= request.getParameter("CursoCargaId");
	String maestro 		= request.getParameter("Maestro");
	String materia 		= request.getParameter("Materia");	
	
	List<AlumnoCurso> lisAlumnos 					= (List<AlumnoCurso>) request.getAttribute("lisAlumnos");	
	HashMap<String, AlumPersonal> mapPersonal 		= (HashMap<String, AlumPersonal>) request.getAttribute("mapPersonal");
	HashMap<String, String> mapaModoCargaAlumnos 	= (HashMap<String, String>) request.getAttribute("mapaModoCargaAlumnos");
	
	String sFraseBaja		= "xxxxxxxxxx.DROP.OUT.xxxxxxxxxxx";
	String sFraseTitulo		= "xxxxxxxxxx.T.I.T.U.L.O.xxxxxxxx";
	String modalidad		= "";
	int j=0, i=0, row=0;
%>
<div class="container-fluid">
<h2>Student Attendance List <small class="text-muted fs-6">(<%=codigoPersonal%> <%=maestro%> <%=materia%>)</small></h2>
<div class="alert alert-info" align="left">
	<a class="btn btn-primary" href="cursos"><i class="fas fa-arrow-left"></i></a>
</div>
<table style="align:center">
	<tr>
		<td>
			<b>ATTENDANCE LIST</b>
		</td>
	</tr>
</table>
<table class="table table-sm table-fontsmall border">
  <tr align="CENTER"> 
    <th width="3%" rowspan="2" valign="top" class="left"><b><spring:message code="aca.Numero"/></b></th>
    <th width="5%" rowspan="2" valign="top" class="left"><b>Student ID</b></th>
    <th width="8%" rowspan="2" valign="top" class="left"><b>Surname</b></th>
    <th width="8%"rowspan="2" valign="top" class="left"><b>Maiden Name</b></th>
    <th width="8%" rowspan="2" valign="top" class="left"><b><spring:message code="aca.Nombre"/></b></th>
    <th width="8%" rowspan="2" valign="top" class="left"><b><spring:message code="aca.Plan"/></b></th>    
    <th width="8%" rowspan="2" valign="top" class="left"><b>Modality</b></th>
    <th colspan="30" class="center"><b>Periods</b></th>
  </tr>
  <tr> 
<%
	for(j=1;j<31;j++){ %>
    <th><b><%=j%></b></th>
    <%	} %>
  </tr>
<%
	for (AlumnoCurso ac : lisAlumnos){
		
		if (!ac.getTipoCalId().equals("M")){ 
			row++;
			String paterno 	= "-";
			String materno 	= "-";
			String nombre	= "-";
			if (mapPersonal.containsKey(ac.getCodigoPersonal())){
				AlumPersonal alumno = mapPersonal.get(ac.getCodigoPersonal());
				paterno		= alumno.getApellidoPaterno();
				materno 	= alumno.getApellidoMaterno();
				nombre		= alumno.getNombre();
			}
			
			String modo = "";
			if (mapaModoCargaAlumnos.containsKey(ac.getCodigoPersonal()+ac.getCargaId()+ac.getBloqueId())){
				modo = mapaModoCargaAlumnos.get(ac.getCodigoPersonal()+ac.getCargaId()+ac.getBloqueId());
			}		
%>
  <tr class="tr2"> 
    <td class="center"  style="font-size: 07pt;"><%=row%></td>
    <td class="left" style="font-size: 07pt;"><%=ac.getCodigoPersonal()%></td>    
    <td class="left"  style="font-size: 07pt;"><%=paterno%></td>        
    <td class="left"  style="font-size: 07pt;"><%=materno%></td>
    <td class="left"  style="font-size: 07pt;"><%=nombre%></td>
    <td style="font-size: 07pt;" align="center"><%=ac.getCursoId().substring(0,8)%></td>
    <td class="left"  style="font-size: 07pt;"><%=modo.equals("P")?"Face-to-Face":"Online"%></td>
<% 			for (j=0;j<30;j++){
				if (ac.getTipoCalId().equals("3")){ %>
    <td width="2%" class="center"> <font color="#990000" size="2" face="Arial Narrow, Arial"><b><%=sFraseBaja.substring(j,j+1)%></b></font></td>
<%				}else if (ac.getTitulo().equals("S")){  %>    
	<td width="2%" class="center"> <font color="#990000" size="2" face="Arial Narrow, Arial"><b><%=sFraseTitulo.substring(j,j+1)%></b></font></td>
<%				}else{  %>
    <td width="2%">&nbsp;</td>
<%				}
			}
		} // fin de tipoCal!= 'M'
%>
  </tr>
<%	}%>
</table>
</div>
<!-- fin de estructura -->