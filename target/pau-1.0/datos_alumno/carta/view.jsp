<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>

<jsp:useBean id="vacacion" scope="page" class="aca.alumno.AlumVacacion" />
<jsp:useBean id="vacacionL" scope="page" class="aca.alumno.AlumVacacionLista" />
<jsp:useBean id="fecha" scope="page" class="aca.util.Fecha" />
<jsp:useBean id="calendario" scope="page" class="java.util.GregorianCalendar"/>
<jsp:useBean id="Parametros" scope="page" class="aca.parametros.Parametros"/>

<%	
	String codigoPersonal 		= (String) session.getAttribute("codigoAlumno");
	String nombreAlumno			= aca.alumno.AlumUtil.getNombreAlumno(conEnoc,codigoPersonal,"NOMBRE");
	String modalidadId			= aca.alumno.AcademicoUtil.getModalidadId(conEnoc,codigoPersonal);
	String carreraId			= request.getParameter("carreraId");
	String carrera				= aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc,carreraId);
	
	int diaSiguiente			= 0;
	String nivelId				= aca.alumno.PlanUtil.getCarreraNivel(conEnoc,carreraId);
	String fExamen				= aca.alumno.AlumVacacionLista.getFechaExamen(conEnoc, nivelId, modalidadId);
	String fInicio				= aca.alumno.AlumVacacionLista.getFechaInicio(conEnoc, nivelId, modalidadId);
	String fFinal				= aca.alumno.AlumVacacionLista.getFechaInicio(conEnoc, nivelId, modalidadId);
	
	if(fFinal != "X"){
	//	System.out.println("fFinal = "+fFinal);
		diaSiguiente = Integer.parseInt(fecha.getDia(fFinal));
		diaSiguiente = diaSiguiente+1;
	}
	
	Parametros.mapeaRegId(conEnoc, "1");

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
	    <table  cellpadding="2" align="center" valign="TOP" style="font-size: 12pt; font-family: arial;" bordercolor="#7D3E9A">
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
	            <%=Parametros.getConstancias()%>, Directora de Gestión Académica y Registro Escolar, 
	            por este medio <b>HACE CONSTAR QUE:</b><br>
	            <br>
	            <div align="center">
	            	<a href="javascript:window.print()" style="text-decoration: none;"> 
	            		<strong><%=nombreAlumno%></strong>
	            	</a>
	            </div>	
	            <br>           
	            con n&uacute;mero de matr&iacute;cula <strong><%=codigoPersonal%></strong>, es estudiante de esta Universidad en el
	            programa de <%=carrera%>.
	            <br>
	            <br>
	           	El Consejo Universitario ha votado el calendario para el curso escolar <%=fecha.getPeriodoActual()%> estableciendo que
	           	los exámenes concluyeron el <%=fecha.getDia(fExamen)%> de <%=fecha.getMesNombre(fExamen)%> y 
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
	            <b><%=Parametros.getConstancias()%></b>
	            <br>
	            <b>Directora de Gestión Académica y <br>Registro Escolar</b>
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
<%@ include file= "../../cierra_enoc.jsp" %>