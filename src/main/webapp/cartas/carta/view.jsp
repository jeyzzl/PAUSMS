<%@page import="aca.util.Fecha"%>
<%@page import="aca.parametros.spring.Parametros"%>
<%@page import="aca.parametros.spring.Parametros"%>


<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%	
	String codigoPersonal 		= (String) session.getAttribute("codigoAlumno");
	String nombreAlumno			= (String)request.getAttribute("nombreAlumno");
	String modalidadId			= (String)request.getAttribute("modalidadId");	
	String planId				= (String)request.getAttribute("planId");
	String carreraId			= (String)request.getAttribute("carreraId");	
	String carrera				= (String)request.getAttribute("carrera");
	String facultad				= (String)request.getAttribute("facultad");	
	String nivelId				= (String)request.getAttribute("nivelId");
	String fExamen				= (String)request.getAttribute("fExamen");
	String fInicio				= (String)request.getAttribute("fInicio");
	String fFinal				= (String)request.getAttribute("fFinal");
	String sexo					= (String)request.getAttribute("sexo");
	String semestre				= (String)request.getAttribute("semestre");
	Parametros parametros 		= (Parametros)request.getAttribute("parametros");
	Fecha fecha 				= new Fecha();
	
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
		diaSiguiente = Integer.parseInt(fecha.getDia(fFinal));
		diaSiguiente = diaSiguiente+1;
	}
	
%>
<html>
	<%if(fExamen != "X"){ %>
		<body>
			<br>
		    <table  cellpadding="2" width="85%" align="center" style="background:#FFF;">
		      	<tr>             
		        	<td colspan="2" align="JUSTIFY" style="font-size: 12pt;">
						<br><br><br><br><br><br><br><br>
		          		<div align="right"><img width="150" src="../../foto?Codigo=<%=codigoPersonal%>&Tipo=O" /></div>
	          			<b>A QUIEN CORRESPONDA:</b><br>
		          		<br>
		          		<div align="JUSTIFY"> 
<%-- 		            		La que suscribe, <%=parametros.getConstancias()%>, Vicerrectora Académica Asociada, por este medio <b>HACE CONSTAR QUE:</b><br> --%>
		            		El que suscribe, <%=parametros.getCertificados()%>, Director de servicios escolares, por este medio <b>HACE CONSTAR QUE:</b><br>
		            	<br>
		            	<div align="center">
	            			<strong><%=nombreAlumno%></strong>
		            	</div>	
		            	<br>           
	            		con n&uacute;mero de matr&iacute;cula <strong><%=codigoPersonal%></strong>, es alumn<%=sexo.equals("M")?"o":"a"%> de esta institución en el
		            	programa de <%=carrera%>.<%if(!carreraId.equals("10301")){%> Durante el período académico de <%=periodo%> se inscribió en el <%=semestre%> semestre de su plan de estudios.
			            <%}else{%>
			            Este estudiante deberá cumplir con requisitos propios de la carrera de Medicina fuera de la Universidad de Montemorelos, por lo que su salida de la institución es a partir del viernes
			            20 de abril.
			            <%}%>
		            	<br>
		            	<br>
		           		El Consejo Universitario ha votado el calendario para el curso escolar <%=fecha.getPeriodoActual()%> estableciendo que
		           		el período termina el <%=fecha.getDia(fExamen)%> de <%=fecha.getMesNombre(fExamen)%> y 
		           		las vacaciones se extienden desde el <%=fecha.getDia(fInicio)%> de <%=fecha.getMesNombre(fInicio)%> hasta el <%=fecha.getDia(fFinal)%> 
			           	de <%=fecha.getMesNombre(fFinal)%> siendo el primer día de clases el <%=diaSiguiente %> de <%=fecha.getMesNombre(fFinal)%> del <%=fecha.getYear(fFinal)%>.
			           	<%		           	
			           	String carrerasVacaciones = "-10301-10303-10304-10314-";
			            if(carrerasVacaciones.indexOf("-"+carreraId+"-") != -1){
			            	if(carreraId.equals("10301")){
			            		//out.print("Los estudiantes de "+carrera+" concluyen su actividad en la institución el día 20 de abril del "+fecha.getYear(aca.util.Fecha.getHoy())+".");
			            	}else if(carreraId.equals("10303")){
			            		//out.print("Los estudiantes de "+carrera+" concluyen su actividad en la institución el día 20 de abril del "+fecha.getYear(aca.util.Fecha.getHoy())+".");
			            	}else{
			            		//out.print("Los estudiantes de "+carrera+" concluyen su actividad en la institución el día 20 de Abril del "+fecha.getYear(aca.util.Fecha.getHoy())+".");
			            	}	
			            }
			            
			            %>
			            <br>
			            <br>
			            A petición de quien 
			            lo solicita y para los fines y usos que convengan, se extiende 
			            la presente <b>CONSTANCIA</b>, en la ciudad de Montemorelos, Nuevo 
			            Le&oacute;n, M&eacute;xico, a los <%=fecha.getDia(aca.util.Fecha.getHoy()) %> d&iacute;as del mes de 
			           	<%=fecha.getMesNombre(aca.util.Fecha.getHoy())%> del a&ntilde;o <%=fecha.getYear(aca.util.Fecha.getHoy())%>.<br>
			            <br>
			            <br>
			            Atentamente,</div>
			            <br>
			            <br>
			            <br>
			            <br>
			            <br>
			            <b><%=parametros.getCertificados()%></b>
			            <br>
<!-- 			            <b>Vicerrectora Académica Asociada</b> -->
			            <b>Director de servicios escolares</b>
			            <br>		            
			            <b>&nbsp;</b>
					</td>
		      	</tr>
		    </table>
		</body>
	<%}else{ %>
		<script type="text/javascript">
			alert('Ingresa el periodo de Vacaciones');
		</script>
		<h3 align="center">No hay periodos de vacaciones dados de alta.</h3>
	<%} %>
</html>
<!-- fin de estructura -->