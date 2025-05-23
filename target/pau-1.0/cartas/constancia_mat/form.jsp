<%@page import="java.util.List"%>
<%@page import="aca.util.Fecha"%>
<%@page import="aca.plan.spring.MapaPlan"%>
<%@page import="aca.parametros.spring.Parametros"%>
<%@page import="aca.vista.spring.AlumnoCurso"%>
<%@page import="aca.carga.spring.Carga"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%		
	String codigoPersonal 	= (String) session.getAttribute("codigoAlumno");
	String cargaId			= (String)request.getAttribute("cargaId");
	String planId			= (String)request.getAttribute("planId");
	String nombreAlumno 	= (String)request.getAttribute("nombreAlumno");
	String facultad			= (String)request.getAttribute("facultadId");
	String nombreFacultad	= (String)request.getAttribute("nomnbreFacultad");
	String carrera			= (String)request.getAttribute("carreraId");
	String nombreCarrera	= (String)request.getAttribute("nombreCarrera");
	
	String periodo			= Fecha.getPeriodoMes(Fecha.getHoy());	
	String curso			= aca.util.Fecha.getPeriodoActual();
	String semestre			= "1";
	int numSem				= (int)request.getAttribute("semestre");

	MapaPlan mapaPlan 		= (MapaPlan)request.getAttribute("mapaPlan");
	Parametros parametros	= (Parametros)request.getAttribute("parametros");	
	
	List<Carga> lisCargas 			= (List<Carga>)request.getAttribute("lisCargas");
	List<AlumnoCurso> lisCursos 	= (List<AlumnoCurso>)request.getAttribute("lisCursos");
%>
<%
	// Varaible Date
	java.util.Date fechaHoy		= new java.util.Date();
	
	// Obtiene el dia en dos caracteres '00'
	int dia				= fechaHoy.getDate();
	String strDia		= "X";
	if (dia<10){
		strDia = '0' + Integer.toString(dia); 
	}else{ 
		strDia = Integer.toString(dia);
	}	
	
	// Nombre del mes
	int mes			= fechaHoy.getMonth()+1;
	String strMes 	= aca.util.Fecha.getMesNombre(mes).toLowerCase();
	
	// Año actual
	int anio		= fechaHoy.getYear() + 1900;
	
	switch(numSem){
		case 1:  semestre = "primer"; break;
        case 2:  semestre = "segundo"; break;
        case 3:  semestre = "tercer"; break;
        case 4:  semestre = "cuarto"; break;
        case 5:  semestre = "quinto"; break;
        case 6:  semestre = "sexto"; break;
        case 7:  semestre = "séptimo"; break;
        case 8:  semestre = "octavo"; break;
        case 9:  semestre = "noveno"; break;
        case 10: semestre = "décimo"; break;
        case 11: semestre = "undécimo"; break;
	}
	
	
	// Periodos de prepa	
	if ( facultad.equals("107") ){		
		if (mes >= 8 && mes <=10){
			periodo="agosto-noviembre";			
		}else if (mes==11){
			if (dia<15) periodo = "agosto-noviembre";
			if (dia>=15) periodo = "noviembre-marzo";			
		}else if (mes==12||mes<=3){
			periodo = "noviembre-marzo";			
		}else if (mes>=4 && mes<=7){
			periodo = "marzo-Junio";			
		}		
	}
%>
<div class="container-fluid">
	<h1>Constancia de est. con materias</h1>
	<form name="datos1" action="view" method="POST">
	<div class="alert alert-info">
		Carga:
		<select name="CargaId" class="input input-xlarge" onchange="location='form?CargaId='+this.options[this.selectedIndex].value;">
<%	
	int i=0;
	for( Carga carga : lisCargas){		
		if (carga.getCargaId().equals(cargaId)){
			out.print(" <option value='"+carga.getCargaId()+"' Selected>"+ carga.getNombreCarga()+"</option>");					
		}else{
			out.print(" <option value='"+carga.getCargaId()+"'>"+ carga.getNombreCarga()+"</option>");
		}				
	}			
%>
		</select>
	</div>
 
		
	<table   align="left" class="table table-condensed">
	<tr>
  		<th style="text-align:left;"> 
    		<div align="left">
    		<b><font face="Arial, Helvetica, sans-serif" size="2"> 
    		<input type="text" class="text" name="f_destinatario" size="80" maxlength="100" value="A QUIEN CORRESPONDA:">
    		</font></b>
    		</div>
  		</th>
	</tr>
	<tr>		
  		<td> 
<%--   			<font face="Arial, Helvetica, sans-serif" size="2">La que suscribe, <%=parametros.getConstancias()%>, Vicerrectora Académica Asociada de la Universidad De Montemorelos, por este medio <b>HACE CONSTAR que</b>:</font><br> --%>
  			<font face="Arial, Helvetica, sans-serif" size="2">El que suscribe, <%=parametros.getCertificados()%>, Director de servicios escolares de la Universidad De Montemorelos, por este medio <b>HACE CONSTAR que</b>:</font><br>
  		</td>
	</tr>
	<tr>		
  		<td style="text-align:left;"> 
  			<textarea name="f_comentario" cols="60" rows="8" class="textarea span7">
    		<div align="center"><b><%=nombreAlumno%></b></div><br>con número de matrícula <% out.print(codigoPersonal); %>, es alumno(a) de la <%=nombreFacultad%>, con RVOE <%=mapaPlan.getRvoe() %>, dependiente de esta Universidad, en la carrera de <%=nombreCarrera%>. 
			Durante el período académico de <%=periodo%> del curso escolar <%=curso%>, inscribió y cursó las siguientes materias obteniendo las calificaciones como se establecen a continuación:</textarea>
			<ol>
<%	
 	String notaMateria = "0"; 	
	for( AlumnoCurso alumnoCurso : lisCursos){			
		if(!alumnoCurso.getOptativa().equals("-")){ 
%>
		<li><%=alumnoCurso.getNombreCurso()%> (<%=alumnoCurso.getOptativa()%>)</li>
<%		}else{ %>
		<li><%=alumnoCurso.getNombreCurso()%></li>
<%		} 
	}
%>
			</ol>
  		</td>
	</tr>
	<tr>
  		<td> <font face="Arial, Helvetica, sans-serif" size="2">A petici&oacute;n de quien 
    	lo solicita y para los fines y usos que convengan, se le extiende la 
    	presente <b>CONSTANCIA</b>, <br>
    	en la ciudad de Montemorelos, Nuevo Le&oacute;n, M&eacute;xico, a los 
    	<b><font face="Arial, Helvetica, sans-serif" size="2"> 
    	<input type="text" class="input input-moremini" name="f_dia" size="3" maxlength="3" value="<%=strDia%>">
    	</font></b>d&iacute;as del mes de<b><font face="Arial, Helvetica, sans-serif" size="2"> 
    	<b><font face="Arial, Helvetica, sans-serif" size="2"> <b><font face="Arial, Helvetica, sans-serif" size="2"><b><font face="Arial, Helvetica, sans-serif" size="2">
    	<input type="text" class="input input-small" name="f_mes" size="10" maxlength="10" value="<%=strMes%>">
    	</font></b></font></b></font></b></font></b>del a&ntilde;o <b><font face="Arial, Helvetica, sans-serif" size="2"> 
    	<input type="text" class="input input-mini" name="f_year" size="4" maxlength="4" value="<%=Integer.toString(anio)%>">
    	</font></b>.</font>
  		</td>
	</tr>
	<tr>
		<td>
			<select name="firma" id="firma">
			  <option value="con" Selected> Con firma </option>
	          <option value="sin"> Sin firma </option>          
	        </select>
    	</td>
	</tr>
	<tr>		
		<input type='Hidden' name='f_codigo_personal' value='<%=codigoPersonal%>'>
		<td style="text-align:left;">
		<input type="Submit" class="btn btn-primary" value="Enviar">
  		</td>
	</tr>
	</table>
	</form>
</div>
<!-- fin de estructura -->