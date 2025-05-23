<%@ include file= "../../con_enoc.jsp"%>
<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="AlumEgresoU" scope="page" class="aca.alumno.AlumEgresoUtil"/>
<jsp:useBean id="Evento" scope="page" class="aca.alumno.AlumEvento"/>
<jsp:useBean id="eventoU" scope="page" class="aca.alumno.AlumEventoUtil"/>
<jsp:useBean id="catCarreraU" scope="page" class="aca.catalogo.CatCarreraUtil"/>
<jsp:useBean id="AlumPlan" scope="page" class="aca.alumno.AlumPlan"/>

<script>
	function recarga(){	
			document.location.href="desercion?eventoId="+document.forma.EventoId.value;
	}
</script>

<%
	String eventoId = request.getParameter("eventoId");
	
	ArrayList<aca.alumno.AlumEvento> lisEvento = eventoU.getListAll(conEnoc, " ORDER BY TO_CHAR(FECHA, 'YYYY/MM/DD')");
	
	if(eventoId==null && lisEvento.size()>0) eventoId = lisEvento.get(lisEvento.size()-1).getEventoId();
	
	ArrayList<aca.alumno.AlumEgreso> lisAlumEgreso = aca.alumno.AlumEgresoUtil.getListFechaIngresoUm(conEnoc, " ORDER BY ENOC.FACULTAD(CARRERA_ID), ENOC.CARRERA_NIVEL(CARRERA_ID), ENOC.NOMBRE_CARRERA(CARRERA_ID), ENOC.ALUM_NOMBRE(CODIGO_PERSONAL)", eventoId);

	ArrayList<aca.catalogo.CatCarrera> lisCarreras = catCarreraU.getListAll(conEnoc, "ORDER BY FACULTAD_ID, CARRERA_ID");
	
	Evento = eventoU.mapeaRegId(conEnoc, eventoId);
	
	int yearGraduacion = Integer.parseInt(Evento.getFecha().split("/")[2]);

	java.text.DecimalFormat getformato= new java.text.DecimalFormat("###,##0.0;-###,##0.0");
%>
<style>
	.total td{
		padding:10px
	}
</style>
<body>
<div class="container-fluid">
<h1>Índice de Deserción</h1>
<form name="forma">
<div class="alert alert-info">
	<strong>Evento: </strong>
    <select id="EventoId" name="EventoId" onchange="javascript:recarga()" >
<%		

	for(aca.alumno.AlumEvento evento : lisEvento){
%>
		<option value="<%=evento.getEventoId()%>" <%if(evento.getEventoId().equals(eventoId))out.print("selected"); %>><%=evento.getEventoNombre() %> [<%=evento.getFecha() %>]</option>
		
<%	} %>
	</select>
</div>
<table style="width:80%" class="table table-condensed">
<%
	int prepa 			= 2;
	int lic				= 5;
	int maestria 		= 2;
	int doctorado 		= 3;
	
	int totalEgresados 	= 0;
	int totalPI			= 0;
	int totalPINUEVO	= 0;
	
	int totalEgresadosGeneral = 0;
	int totalPIGeneral		  = 0;
	int totalPIGeneralNUEVO   = 0;
	
  	String facultadId = "";
	for(aca.catalogo.CatCarrera carrera: lisCarreras){ 
		
		//Si alguno de los alumnos egresados tiene esta carrera entonces mostrarla, y contar cuantos alumnos hay en cada carrera
		boolean encontro = false;
		int contAlumnos = 0;
		
		for(aca.alumno.AlumEgreso alumno: lisAlumEgreso){
			if(alumno.getCarreraId().equals(carrera.getCarreraId())){
				encontro=true;
				contAlumnos++;
			}
		}
		
		if(!encontro)continue;
		//------------
		
		if(!facultadId.equals(carrera.getFacultadId())){
			
			if(!facultadId.equals("")){
%>
	  <tr bgcolor="#E6E6E6">
	  	<td colspan="2"><font size="4">Totales:</font></td>
	  	<td colspan="1" align="center"><font size="3"><%=totalEgresados %></font></td>
	  	<td colspan="1" align="center"><font size="3"><%=totalEgresados-totalPI %></font></td>
	  	<td colspan="1" align="center"><font size="3"><%=totalPI %></font></td>
	  	<td colspan="1" align="center"><font size="3"><%=totalPINUEVO %></font></td>
	  	<td colspan="1" align="center"><font size="3"><%=getformato.format(( ((float)totalPINUEVO-(float)totalEgresados) /(float)totalPINUEVO)*100).replaceAll(",", ".") %> %</font></td>
	  </tr>
	<%
			}
		totalEgresados=0;
		totalPI=0;
		totalPINUEVO=0;
	%>
	  <tr>
	  	<td><font size="4">&nbsp;<br><%= aca.catalogo.CatFacultadUtil.getNombreFacultad(conEnoc, carrera.getFacultadId())  %></font></td>
	  </tr>	
	  
	  <tr>
	  	<th><spring:message code="aca.Carrera"/></th>
	  	<th>Años</th>
	  	<th>Egresados</th>
	  	<th>Rezagados</th>
	  	<th>Regulares</th>
	  	<th>Primer Ingreso</th>
	  	<th>ID</th>
	  </tr> 
<%			facultadId = carrera.getFacultadId();
		} 


		int years = 0;
		if(carrera.getNivelId().equals("1"))years = prepa;
		else if(carrera.getNivelId().equals("2"))years = lic;
		else if(carrera.getNivelId().equals("3"))years = maestria;
		else if(carrera.getNivelId().equals("4"))years = doctorado;
		
		if(carrera.getCarreraId().equals("10301")){
			years+=2;
		}
		
		
		int contAlumnosPrimerIngreso = 0;
		
		int contAlumnosPrimerIngresoNUEVO = aca.alumno.PlanUtil.getContPrimerIngreso(conEnoc, (yearGraduacion-years)+"", carrera.getCarreraId());
		
		for(aca.alumno.AlumEgreso alumno: lisAlumEgreso){
			if(alumno.getCarreraId().equals(carrera.getCarreraId())){
				//System.out.println(alumno.getCodigoPersonal()+" - "+alumno.getFecha());
				
				
				if(Integer.parseInt( alumno.getFecha().split("/")[2])>=(yearGraduacion-years)){
					if(Integer.parseInt( alumno.getFecha().split("/")[2])==(yearGraduacion-years) && Integer.parseInt(alumno.getFecha().split("/")[1])<8)continue;
					contAlumnosPrimerIngreso++;
				}
				
				
				//if(alumno.getFecha()==null)System.out.println(alumno.getCodigoPersonal());
			}
		}
		//System.out.println("--------------->>");
		
		totalEgresados+=contAlumnos;
		totalPI+=contAlumnosPrimerIngreso;
		totalPINUEVO+=contAlumnosPrimerIngresoNUEVO;
		
		totalEgresadosGeneral +=contAlumnos;
		totalPIGeneral +=contAlumnosPrimerIngreso;
		totalPIGeneralNUEVO+=contAlumnosPrimerIngresoNUEVO;
%>	
	  <tr>
	  	<td class="button" onclick="location.href='detalles?carreraId=<%=carrera.getCarreraId()%>&eventoId=<%=eventoId%>&year=<%=years%>'"><%= carrera.getNombreCarrera() %></td>
	  	<td align="center"><%= years %></td>
	  	<td align="center"><%= contAlumnos %></td>
	  	<td align="center"><%= contAlumnos-contAlumnosPrimerIngreso %></td>
	  	<td align="center"><%= contAlumnosPrimerIngreso %></td>
	  	<td align="center"><%= contAlumnosPrimerIngresoNUEVO %></td>
	  	<td align="center"><%= getformato.format(( ((float)contAlumnosPrimerIngresoNUEVO-(float)contAlumnos) /(float)contAlumnosPrimerIngresoNUEVO)*100).replaceAll(",", ".") %> %</td>
	  </tr>  
<%			
	}
%>
	<tr bgcolor="#E6E6E6">
	  	<td colspan="2"><font size="4">Totales:</font></td>
	  	<td colspan="1" align="center"><font size="3"><%=totalEgresados %></font></td>
	  	<td colspan="1" align="center"><font size="3"><%=totalEgresados-totalPI %></font></td>
	  	<td colspan="1" align="center"><font size="3"><%=totalPI %></font></td>
	  	<td colspan="1" align="center"><font size="3"><%=totalPINUEVO %></font></td>
	  	<td colspan="1" align="center"><font size="3"><%=getformato.format(( ((float)totalPINUEVO-(float)totalEgresados) /(float)totalPINUEVO)*100).replaceAll(",", ".") %> %</font></td>
	  </tr>
  
</table>
	<table class="tabla total">
		<tr>
			<th colspan="2">TOTALES GENERALES</th>
		</tr>
		<tr>
			<td>Egresados:</td>
			<td><%=totalEgresadosGeneral %></td>
		</tr>
		<tr>
			<td>Rezagados:</td>
			<td><%=totalEgresadosGeneral-totalPIGeneral %></td>
		</tr>
		<tr>
			<td>Regulares</td>
			<td><%=totalPIGeneral %></td>
		</tr>
		<tr>
			<td>Primer Ingreso</td>
			<td><%=totalPIGeneralNUEVO %></td>
		</tr>
		<tr>
			<td>ID</td>
			<td><%=getformato.format(( ((float)totalPIGeneralNUEVO-(float)totalEgresadosGeneral) /(float)totalPIGeneralNUEVO)*100).replaceAll(",", ".")%> %</td>
		</tr>
	</table>
</form>	
</div>
</body>
<%@ include file= "../../cierra_enoc.jsp" %>