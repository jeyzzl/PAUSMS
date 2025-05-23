<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.util.Fecha"%>
<%@ page import= "aca.catalogo.spring.CatEscuela"%>
<%@ page import= "aca.residencia.spring.ResDatos"%>
<%@ page import= "aca.alumno.spring.AlumAcademico"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String filtro				= request.getParameter("Filtro")==null?"1":request.getParameter("Filtro");
	String planId 				= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
	Fecha fecha					= new Fecha();
	String codigoAlumno 		= (String)request.getAttribute("codigoAlumno");
	String nombreAlumno 		= (String)request.getAttribute("nombreAlumno");
	String planNombre	 		= (String)request.getAttribute("planNombre");
	int materias 				= (int)request.getAttribute("materias");
	
	ResDatos resDatos 			= (ResDatos)request.getAttribute("resDatos");
	AlumAcademico alumAcademico = (AlumAcademico)request.getAttribute("alumAcademico");

	List<CatEscuela> lisEscuelas = (List<CatEscuela>)request.getAttribute("lisEscuelas");
%>
<html>
<body>
<div class="container-fluid">
	<h3><%=codigoAlumno%> - <%=nombreAlumno%> - <%=alumAcademico.getResidenciaId().equals("E")?"Day Student":"Boarding Student"%></h3>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="listado?facultad=<%=request.getParameter("FacultadId")%>"><spring:message code="aca.Regresar"/></a>
	</div>	
<% 	if(alumAcademico.getResidenciaId().equals("E") && !resDatos.getUsuario().equals("9800069") && !resDatos.getUsuario().equals("9801159") && !resDatos.getUsuario().equals("1170629")){%>
	<div class="alert alert-danger">
		Day student!
	</div>	
<% 	}%>
	<form action="grabarPlan?Materias=<%=materias%>" method="post" name="frmDatos">
	<input type="hidden" name="CodigoAlumno" value="<%=codigoAlumno%>">
	<table class="table table-sm table-bordered" style="width:100%">	    
	<tr> 
    	<td width="15%"><strong><spring:message code="aca.Plan"/>:</strong></td>
       	<td><%=planId%><input type="hidden" name="PlanId" value="<%=planId%>"> - <%=planNombre%></td>
    </tr>
    <tr> 
    	<td><strong><spring:message code="aca.Fecha"/>:</strong></td>
      	<td><input name="FInicio" type="text" class="form-control" size="12" maxlength="10" value="<%=fecha.getFecha("1")%>" style="width:120px;"></td>
	</tr>          
	<tr>
 		<td><strong><spring:message code="aca.EscOrigen"/>:</strong></td>
 		<td>
   			<select name="Escuela" class="form-select" style="width:320px;">
<%
				
		    for (CatEscuela escuela : lisEscuelas){
				out.print("<option value='"+escuela.getEscuelaId()+"'>"+ escuela.getNombreEscuela()+"</option>");
			}	
%>
			</select>
		</td>
	</tr>
	<tr>
		<td height="26"><strong><spring:message code="aca.Activo"/>:</strong></td>
		<td><input name="Estado" type="checkbox" value="1" checked></td>
	</tr>
	<tr>
		<td height="26"><strong><spring:message code="aca.Principal"/>:</strong></td>
		<td><input name="Principal" type="checkbox" value="1" checked></td>
	</tr>
	<tr>
		<td height="26"><strong><spring:message code="aca.Escala"/>:</strong></td>
		<td>
			<select name="Escala"  class="form-select" style="width:120px;">
				<option value="100">100</option>
				<option value="10">10</option>				
			</select>	
		</td>
	</tr>
	<tr>
<% 	if(alumAcademico.getResidenciaId().equals("I") || ( alumAcademico.getResidenciaId().equals("E") && (!resDatos.getUsuario().equals("9800946") || !resDatos.getUsuario().equals("9800078") || !resDatos.getUsuario().equals("9820389"))) ){%>
		<th colspan="2"><input name="submit" type="submit" value="<spring:message code="aca.Anadir"/>" class="btn btn-primary"></th>
<% 	}%>
	</tr>
    </table>	
	</form>
</div>
</body>
</html>