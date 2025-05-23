<%@page import="aca.util.Fecha"%>
<%@page import="aca.alumno.AlumVacacion"%>
<%@page import="aca.parametros.spring.Parametros"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="fecha" scope="page" class="aca.util.Fecha" />
<jsp:useBean id="calendario" scope="page" class="java.util.GregorianCalendar"/>
<%	
	String codigoPersonal 		= (String) session.getAttribute("codigoAlumno");
	String nombreAlumno			= (String) request.getAttribute("nombreAlumno");
	String modalidadId			= (String) request.getAttribute("modalidadId");
	String planId				= (String) request.getAttribute("planId");
	String carreraId			= (String) request.getAttribute("carreraId");
	String carrera				= (String) request.getAttribute("carrera");
	String institucion 			= (String) session.getAttribute("institucion");
	String nivelId				= (String) request.getAttribute("nivelId");
	String fExamen				= (String) request.getAttribute("fExamen");
	String fInicio				= (String) request.getAttribute("fInicio");
	String fFinal				= (String) request.getAttribute("fFinal");
	String sexo					= (String) request.getAttribute("sexo");
	String semestre				= (String) request.getAttribute("semestre");
	Parametros parametros 		= (Parametros) request.getAttribute("parametros");
	
	String periodo				="-";
	int diaSiguiente			= 0;
	String fechaHoy				= aca.util.Fecha.getHoy();
	if (fechaHoy.substring(3, 5).equals("11")||fechaHoy.substring(3, 5).equals("12")||fechaHoy.substring(3, 5).equals("01"))
		periodo = "agosto a diciembre";
	else if (fechaHoy.substring(3, 5).equals("04")||fechaHoy.substring(3, 5).equals("05")||fechaHoy.substring(3, 5).equals("06")||fechaHoy.substring(3, 5).equals("07"))
		periodo = "enero a mayo";
	else
		periodo = "agosto a diciembre";
	
	if(semestre.equals("1")){
		semestre = "primer";
	}else if(semestre.equals("2")){
		semestre = "segundo";
	}else if(semestre.equals("3")){
		semestre = "tercer";
	}else if(semestre.equals("4")){
		semestre = "cuarto";
	}else if(semestre.equals("5")){
		semestre = "quinto";
	}else if(semestre.equals("6")){
		semestre = "sexto";
	}else if(semestre.equals("7")){
		semestre = "séptimo";
	}else if(semestre.equals("8")){
		semestre = "octavo";
	}else if(semestre.equals("9")){
		semestre = "noveno";
	}else if(semestre.equals("10")){
		semestre = "décimo";
	}else if(semestre.equals("11")){
		semestre = "undécimo";
	}else if(semestre.equals("12")){
		semestre = "duodécimo";
	}
	
	if(carreraId.equals("10301")){
		carrera = carrera.substring(17, 33);
	}
	
	if(fFinal != "X"){
	//	System.out.println("fFinal = "+fFinal);
		diaSiguiente = Integer.parseInt(fecha.getDia(fFinal));
		diaSiguiente = diaSiguiente+1;
	}
%>
<%@page import="aca.util.Fecha"%>
<%@page import="aca.alumno.AlumVacacion"%>
<html>
<head>
	<style TYPE="text/css">		
		BODY { background: #ffffff;
			font-style: Arial Narrow, Arial, Times New Roman;
		}
		.para1 { margin-top: 0px; }
	</style>	
</head>
<%if(fExamen != "X"){ %>
<body bgcolor="#FFFFFF">
	<div CLASS="para1"> 
	  <form name="datos1">
	    <table  cellpadding="2" align="center" valign="TOP" style="font-size: 12pt; font-family: arial;">
	      <tr>             
	        <td colspan="2" align="JUSTIFY" width="600"> <br>
	          <br>
	          <br>
	          <br>
	          <br>
	          <br>
	          <br>	          
	          <br>
	          <br>	          
	          <div align="right"><img width="97" src="../../foto?Codigo=<%=codigoPersonal%>&Tipo=O" /></div>
	          <b>A QUIEN CORRESPONDA:</b><br>
	          <br>
	          <div align="JUSTIFY" style="font-size: 12pt;"> 
	            La que suscribe, 
	            <%=parametros.getConstancias()%>, Vicerrectora Académica Asociada de la <%=institucion%>, 
	            por este medio <b>HACE CONSTAR QUE:</b><br>
	            <br>
	            <div align="center">
	            	<a href="javascript:window.print()" style="text-decoration: none;"> 
	            		<strong><%=nombreAlumno%></strong>
	            	</a>
	            </div>	
	            <br>           
	            con n&uacute;mero de matr&iacute;cula <strong><%=codigoPersonal%></strong>, es alumn<%=sexo.equals("M")?"o":"a"%> de esta institución en el
	            programa de <%=carrera%>.
			<%if(!carreraId.equals("10301")){%>
	            Durante el período académico de <%=periodo%> se inscribió en el <%=semestre%> semestre de su plan de estudios.
	        <%}else{%>
	            Este estudiante deberá cumplir con requisitos propios de la carrera de Medicina fuera de la Universidad de Montemorelos, por lo que su salida de la institución es a partir del viernes
	            20 de abril.
	        <%}%>
	            <br>
	            <br>
	           	El Consejo Universitario ha votado el calendario para el curso escolar <%=fecha.getPeriodoActual()%> estableciendo que 
	           	las vacaciones se extienden desde el <%=fecha.getDia(fInicio)%> de <%=fecha.getMesNombre(fInicio)%> al <%=fecha.getDia(fFinal)%> 
	           	de <%=fecha.getMesNombre(fFinal)%> siendo el primer día de clases el <%=diaSiguiente %> de <%=fecha.getMesNombre(fFinal)%> del <%=fecha.getYear(fFinal)%>.
	            <br>
	            <br>
	            A petición de quien 
	            lo solicita y para los fines y usos que convengan, se extiende 
	            la presente <b>CONSTANCIA</b>, en la ciudad de Montemorelos, Nuevo 
	            Le&oacute;n, M&eacute;xico, a los <%=fecha.getDia(fecha.getHoy()) %> d&iacute;as del mes de 
	           <%=fecha.getMesNombre(fecha.getHoy())%> del a&ntilde;o <%=fecha.getYear(fecha.getHoy())%>.<br>
	            <br>
	            <br>
	            Atentamente,</div>
	            <br>
	            <br>
	            <br>
	            <br>
	            <b><%=parametros.getConstancias()%></b>
	            <br>
	            <b>Vicerrectora Académica Asociada</b>
	            <br>
	            <b>&nbsp;</b>
	        </td>
	      </tr>
	    </table>
	  </form>
	</div>
</body>
<%}else{ %>
	<script type="text/javascript">
		alert('Ingresa el periodo de Vacaciones');
	</script>
	<h3 align="center">No hay Periodos de Vacaciones dados de alta!</h3>
<%} %>

	<!-- fin de estructura -->
