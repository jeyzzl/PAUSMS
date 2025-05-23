<%@ page buffer= "none" %>
<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>

<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.vista.spring.Inscritos"%>
<%@ page import= "aca.acceso.spring.Acceso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%
	String codigoPersonal	= (String) session.getAttribute("codigoPersonal");
	String accion 			= request.getParameter("Accion")==null?accion="1":request.getParameter("Accion");
	
	String dia				= (String) request.getAttribute("dia");
	String mes				= (String) request.getAttribute("mes");
	String mesNombre		= "";
	String matTemp			= "";
	
	aca.util.Fecha fecha	= new aca.util.Fecha();
	int numAccion				= Integer.parseInt(accion);
	String resultado		= "";
	int i = 0;
	Acceso acceso			= (Acceso) request.getAttribute("acceso");
	
	// Lista de alumnos
	List<Inscritos> lisAlumnos				= (List<Inscritos>) request.getAttribute("lisAlumnos");
	HashMap<String,CatCarrera> mapaCarreras	= (HashMap<String, CatCarrera>) request.getAttribute("mapaCarreras");
	  
	// Operaciones a realizar en la pantalla
	switch (numAccion){
		case 1: { // Captura Parametros
			//resultado = "Llene el formulario correctamente ..¡¡";
			break;
		}		
	}
	
	int 	numMes				= Integer.parseInt(mes);
	switch (numMes){
		case 1 : { mesNombre = "Enero"; 		break; }
		case 2 : { mesNombre = "Febrero";		break; }
		case 3 : { mesNombre = "Marzo"; 		break; }
		case 4 : { mesNombre = "Abril"; 		break; }
		case 5 : { mesNombre = "Mayo";			break; }
		case 6 : { mesNombre = "Junio"; 		break; }
		case 7 : { mesNombre = "Julio"; 		break; }
		case 8 : { mesNombre = "Agosto"; 		break; }
		case 9 : { mesNombre = "Septiembre";	break; }
		case 10: { mesNombre = "Octubre"; 		break; }
		case 11: { mesNombre = "Noviembre"; 	break; }
		case 12: { mesNombre = "Diciembre"; 	break; }
	}
		
	if (!mes.equals("0")){
		resultado += " Mes [ "+mesNombre+" ]";
		if (!dia.equals("0")){
			resultado += " -- Dia [ "+dia+" ]";
		}
	}	
%>
<div class="container-fluid">
	<h2><spring:message code="datosAlumno.cumple.Titulo"/></h2>
	<form action="form" method="post" name="frmcumple">
	<input type="hidden" name="Accion" value="2">
	<div class="alert alert-info">		
		<select name="Mes" id="Mes">
<%	for(int j=1; j<=12; j++){
      	switch (j){
    		case 1 : { mesNombre = "Enero"; 		break; }
    		case 2 : { mesNombre = "Febrero";		break; }
    		case 3 : { mesNombre = "Marzo"; 		break; }
    		case 4 : { mesNombre = "Abril"; 		break; }
    		case 5 : { mesNombre = "Mayo";			break; }
    		case 6 : { mesNombre = "Junio"; 		break; }
    		case 7 : { mesNombre = "Julio"; 		break; }
    		case 8 : { mesNombre = "Agosto"; 		break; }
    		case 9 : { mesNombre = "Septiembre";	break; }
    		case 10: { mesNombre = "Octubre"; 		break; }
    		case 11: { mesNombre = "Noviembre"; 	break; }
    		case 12: { mesNombre = "Diciembre"; 	break; }
    	}%>
		      			<option value="<%=j>=10?j:("0"+j)%>" <%=j==numMes?"Selected":""%>><%=mesNombre%></option>
<%	} %>
		</select>
		&nbsp;&nbsp;
		<spring:message code="aca.Dia"/>:
		<input name="Dia" type="text" class="text" id="Dia" value="<%=dia%>" size="3" maxlength="3">
		&nbsp;&nbsp;
		<spring:message code="datosAlumno.cumple.Mensaje"/>&nbsp;&nbsp;&nbsp;&nbsp;
		<input class="btn btn-primary" name="Buscar" type="submit" id="Buscar" value="<spring:message code="aca.Buscar"/>">	
	</div>
	</form>	
	<div class="alert alert-success"><strong><font size="2" face="Arial, Helvetica, sans-serif"><%=resultado%></font></strong></div>
	<table class="table table-sm">
	<tr>     
		<th width="6%"><spring:message code="aca.Numero"/></th>
		<th width="9%"><spring:message code="aca.Matricula"/></th>	
	    <th width="33%"><spring:message code="aca.Nombre"/></th>    
	    <th width="52%"><spring:message code="aca.Carrera"/></th>
	    <th><spring:message code="aca.Foto"/></th>
	</tr>
<%	
	int row = 0;
	for (Inscritos alumno : lisAlumnos){
		if(acceso.getAccesos().indexOf(alumno.getCarreraId())!=-1 || acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S") || acceso.getPortalAlumno().equals("S")){
			
			if ( !fecha.getDia(alumno.getfNacimiento()).equals(dia) ){
				dia = fecha.getDia( alumno.getfNacimiento());%>
				<tr><td colspan="7" style="text-align:center;"><h3><spring:message code="aca.Dia"/>: &nbsp; <%=dia%></h3></td>
		<%	}
			if(!alumno.getCodigoPersonal().equals(matTemp)){
				row++;
				
				String carreraNombre = "-";
			if (mapaCarreras.containsKey(alumno.getCarreraId()))
				carreraNombre = mapaCarreras.get(alumno.getCarreraId()).getNombreCarrera();
				
		%>  
			  <tr> 
			    <td><%=row%></td>
			    <td><%=alumno.getCodigoPersonal()%></td>    
			    <td><%=alumno.getNombre()+" "+alumno.getApellidoPaterno()+" "+alumno.getApellidoMaterno()%></td>    
			    <td><%=carreraNombre%></td>
			    <td><img src="../../foto?Codigo=<%=alumno.getCodigoPersonal()%>&Tipo=O" width="70"></td>
			  </tr>
		<%	}
		}
		matTemp = alumno.getCodigoPersonal();
	}%>  
	</table>
</div>
<br><div align="center"><strong><font size="2" face="Arial, Helvetica, sans-serif"></font></strong></div>