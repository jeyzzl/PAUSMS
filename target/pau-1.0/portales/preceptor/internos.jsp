<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%@ page import="java.util.Date,java.text.SimpleDateFormat,java.text.DecimalFormat" %>
<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>

<%@ page import= "aca.alumno.spring.AlumPersonal"%>
<%@ page import= "aca.alumno.spring.AlumAcademico"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.catalogo.spring.CatReligion"%>
<%@ page import= "aca.internado.spring.IntCuarto"%>
<%@ page import= "aca.internado.spring.IntDormitorio"%>
<%@ page import= "aca.internado.spring.IntAlumno"%>
<%@ page import= "aca.mentores.spring.MentAlumno"%>
<%@ page import= "aca.financiero.spring.FinSaldo"%>
<%@ page import= "aca.plan.spring.MapaPlan"%>
<%	
	//long inicio = System.currentTimeMillis(); 
	DecimalFormat dmf		= new DecimalFormat("###,##0.00; (###,##0.00)");

	boolean esAdmin			= (boolean)request.getAttribute("esAdmin");
	boolean esPreceptor		= (boolean)request.getAttribute("esPreceptor");
	String dormitorioId 	= (String)request.getAttribute("dormitorioId");	
	String orden 			= (String)request.getAttribute("orden");
	
	List<IntAlumno> lisAlumnos 						= (List<IntAlumno>)request.getAttribute("lisAlumnos");	
	HashMap<String, String> mapElogio 				= (HashMap<String, String>)request.getAttribute("mapElogio");
	HashMap<String, String> mapUnidades 			= (HashMap<String, String>)request.getAttribute("mapUnidades");
	HashMap<String, String> mapMaestros 			= (HashMap<String, String>)request.getAttribute("mapMaestros");
	HashMap<String, AlumPersonal> mapAlumnos 		= (HashMap<String, AlumPersonal>)request.getAttribute("mapAlumnos");
	HashMap<String, CatCarrera> mapCarrera 			= (HashMap<String, CatCarrera>)request.getAttribute("mapCarrera");
	HashMap<String, CatReligion> mapReligion 		= (HashMap<String, CatReligion>)request.getAttribute("mapReligion");
	HashMap<String, IntCuarto> mapCuarto 			= (HashMap<String, IntCuarto>)request.getAttribute("mapCuarto");
	HashMap<String, MentAlumno> mapMentor 			= (HashMap<String, MentAlumno>)request.getAttribute("mapMentor");
	HashMap<String, String> mapAlumPlan 			= (HashMap<String, String>)request.getAttribute("mapAlumPlan");
	HashMap<String, FinSaldo> mapSaldos 			= (HashMap<String, FinSaldo>)request.getAttribute("mapSaldos");
	HashMap<String, AlumAcademico> mapAcademico 	= (HashMap<String, AlumAcademico>)request.getAttribute("mapAcademico");
	HashMap<String, MapaPlan> mapaPlanes			= (HashMap<String, MapaPlan>)request.getAttribute("mapaPlanes");
	HashMap<String, String> mapaInscritos	 		= (HashMap<String, String>)request.getAttribute("mapaInscritos");
		
	IntDormitorio dormi 	= (IntDormitorio)request.getAttribute("dormi");
	String saldo 			= request.getParameter("Saldo")==null?"N":request.getParameter("Saldo");
%>
<%@ include file="portal.jsp" %>
<script>
	function orden(o){
		document.forma.orden.value=o;
		document.forma.submit();
	}
</script>
<body>
<div class="container-fluid">
	<h3><%=dormi.getNombre()%> Students</h3>
	<form name = "forma" method = "post" action = "internos">
	<input type = "hidden" name = "orden">
	<div class="alert alert-info d-flex align-items-center">
		Show Balance:
		<select name="Saldo" class="form-select" onchange="javascript:document.forma.submit();" style="width:100px;">
			<option value="S" <%=saldo.equals("S")?"selected":""%>>YES</option>
			<option value="N" <%=saldo.equals("N")?"selected":""%>>NO</option>
		</select>&nbsp;&nbsp;
		Filter:&nbsp;<input type="text" class="form-control search-query" placeholder="Search..." id="buscar" style="width:210px;">&nbsp;&nbsp;	
		<%
		if(esAdmin || esPreceptor){ out.print("&nbsp;<a href='../../internado/dormitorios/dormitorios' class='btn btn-info'><i class='icon-white icon-arrow-left'></i>Menu</a>&nbsp; &nbsp;"); }
		%>	
	</div>	 
	<table  id="tabla" class="table table-bordered table-striped">
	<thead class="table-info">		
		<tr>
			<th width='2%'><spring:message code="aca.Numero"/></th>			
			<th width='5%'>Options</th>
			<th width='5%'><a href="javascript:orden('matricula')" style="text-decoration:none; color:black"><i class="fas fa-sort-alpha-down"></i> <spring:message code="aca.Matricula"/></a></th>
			<th width='15%'><a href="javascript:orden('nombre')" style="text-decoration:none; color:black"><i class="fas fa-sort-alpha-down"></i> <spring:message code="aca.Alumno"/></a></th>
			<th width='15%'><a href="javascript:orden('carrera')" style="text-decoration:none; color:black"><i class="fas fa-sort-alpha-down"></i> <spring:message code="aca.Carrera"/></a></th>
			<th width='10%'>Mentor</th>						
			<th width='5%'>Religion</th>
			<th width='3%'>Enrrolled</th>
			<th width='4%'>Residence</th>
			<th width='10%'><a href="javascript:orden('cuarto')" style="text-decoration:none; color:black"><i class="fas fa-sort-alpha-down"></i>Hallway/Room</a></th>
			<th width='6%' class="text-end">Date assigned</th>	
			<th width='4%' class="text-end">Uni.</th>
			<th width='4%' class="text-end">Elo.</th>
			<th width='8%' class="center">Birthday</th>
		</tr>
	</thead>
	<tbody>	
<%				
	int i = 0;
	for (IntAlumno dormiAlumno : lisAlumnos){

		double saldoVencido	= 0.0;
		if (mapSaldos.containsKey(dormiAlumno.getCodigoPersonal())){
			saldoVencido = Double.parseDouble(mapSaldos.get(dormiAlumno.getCodigoPersonal()).getSaldoVencido());
		}	
		
		String planId 		= "0";
		if (mapAlumPlan.containsKey(dormiAlumno.getCodigoPersonal())){
			planId = mapAlumPlan.get(dormiAlumno.getCodigoPersonal());
		}
		
		String carreraNombre 	= "-";
		if (mapaPlanes.containsKey(planId)){			
			carreraNombre 	= mapaPlanes.get(planId).getCarreraSe();
		}		
		
		String mentorId = "0";
		if (mapMentor.containsKey(dormiAlumno.getCodigoPersonal())){
			MentAlumno mentAlumno = mapMentor.get(dormiAlumno.getCodigoPersonal());
			mentorId = mentAlumno.getMentorId();
		}
		
		String maestroNombre = "-";
		if (mapMaestros.containsKey( mentorId )){
			maestroNombre = mapMaestros.get( mentorId );
		}
		
		String elogio = "0";
		if(mapElogio.containsKey(dormiAlumno.getCodigoPersonal())){
			elogio = mapElogio.get(dormiAlumno.getCodigoPersonal());
		}
		
		String unidad = "0";
		if(mapUnidades.containsKey(dormiAlumno.getCodigoPersonal())){
			unidad = mapUnidades.get(dormiAlumno.getCodigoPersonal());
		}
		
		String alumnoNombre = "-";
		String alumnoReligion = "0";
		String nacimiento = "01/01/2000";
		if (mapAlumnos.containsKey( dormiAlumno.getCodigoPersonal() )){
			AlumPersonal alum =  mapAlumnos.get(dormiAlumno.getCodigoPersonal());
			alumnoNombre = (alum.getApellidoMaterno().equals("-")?"":alum.getApellidoMaterno())+" "+alum.getApellidoPaterno()+", "+alum.getNombre();
			alumnoReligion = alum.getReligionId();
			nacimiento = alum.getFNacimiento();
		}
		
		// Nombre de la religion del alumno
		String religionNombre = "";
		if (mapReligion.containsKey(alumnoReligion)){
			religionNombre = mapReligion.get(alumnoReligion).getNombreCorto();
		}		
		
		String pasillo = "-";
		if (mapCuarto.containsKey( dormiAlumno.getCuartoId())){
			pasillo = mapCuarto.get( dormiAlumno.getCuartoId()).getPasillo();
		}
		
		String bgColor="";
		if ((i+1) % 2==0) bgColor="bgcolor='#ffffff'";
		else bgColor = "";
		
		String residencia = "";
		if (mapAcademico.containsKey(dormiAlumno.getCodigoPersonal())){					
			residencia = mapAcademico.get( dormiAlumno.getCodigoPersonal()).getResidenciaId();
		}		
		if(residencia.equals("E")){ 
			residencia = "<span class='badge bg-warning rounded-pill'>Off-campus</span>"; 
		}else{ 
			residencia = "<span class='badge bg-dark rounded-pill'>Dormitory</span>";
		}
		
		String inscrito = "<span class='badge bg-warning rounded-pill'>NO</span>";
		if (mapaInscritos.containsKey(dormiAlumno.getCodigoPersonal())){		
			inscrito = "<span class='badge bg-dark rounded-pill'>YES</span>";	 
		}
		
%>				
		<tr <%=bgColor%>>
			<td align='center'><font color='0'><%=i+1%>.</font></td>			
			<td align='center'>
				<div class="d-flex">
					<a title="Personal Data" target="_blank" href="datosPersonales"><i class="fas fa-user"></i></a>&nbsp;
					<a title="Parents Data/Tutor" target="_blank" href="datosPadres"><i class="fas fa-people-arrows"></i></a>&nbsp;
					<a title="Student Portal" target="_blank" href="resumen?CodigoAlumno=<%=dormiAlumno.getCodigoPersonal()%>"><i class="fas fa-id-card"></i></a>
				</div>
			</td>
			<td align='center d-flex align-items-center'>
				<%=dormiAlumno.getCodigoPersonal()%>				
			</td>
			<td><%=alumnoNombre%></td>
			<td><font color='0'><%=carreraNombre%></font></td>
			<td><font color='0'><%=maestroNombre%></font></td>
			<td><%=religionNombre%></td>
			<td class="text-center"><%=inscrito%></td>
			<td><%=residencia%></td>
			<td align='center'>Bedroom <span class="badge bg-info rounded-pill"><%=dormiAlumno.getCuartoId()%></span>, Hallway <span class="badge bg-info rounded-pill"><%=pasillo%></span></td>
			<td class="text-end"><%= dormiAlumno.getFechaInicio()%></td>				
			<td class="text-end"><%=unidad%></td>
			<td class="text-end"><%=elogio%></td>
			<td class="center"><%=nacimiento.substring(0,2)%> of <%=aca.util.Fecha.getMontName(nacimiento)%></td>
		</tr>
<%	
		i++;
	}
%>
	</tbody>
	</table>	
	</form>
</div>
<script type="text/javascript" src="../../js/search.js"></script>
<script>
	jQuery('#buscar').focus().search({table:jQuery("#tabla")});
	jQuery('.internos').addClass('active');
</script>