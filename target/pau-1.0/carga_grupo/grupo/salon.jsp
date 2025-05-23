<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatEdificio"%>
<%@ page import= "aca.catalogo.spring.CatSalon"%>
<%@ page import="aca.vista.spring.CargaHorario"%>
<%@ page import="aca.plan.spring.MapaCurso"%>
<%@ page import="aca.carga.spring.CargaGrupo"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<head>
<script type="text/javascript">

	function BorrarSalon(folio,carreraId,planId,cursoCargaId,cursoId,bloqueId,edificioId,salonId){	
		if (confirm("Are you sure you want to delete the class room from this timetable?")){			
			document.location.href="borrarSalon?Folio="+folio+"&CarreraId="+carreraId+"&PlanId="+planId+"&CursoCargaId="+cursoCargaId+"&CursoId="+cursoId
			+"&Bloque="+bloqueId+"&EdificioId="+edificioId+"&SalonId="+salonId;
		}
	}
</script>
</head>
<%		
	String carreraId		= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");
	String planId			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
	String cursoCargaId		= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
	String cursoId			= request.getParameter("CursoId")==null?"0":request.getParameter("CursoId");
	String edificioId		= (String) request.getAttribute("edificioId");
	String salonId			= (String) request.getAttribute("salonId");
	String bloqueId			= (String) request.getAttribute("bloqueId");
	String mensaje			= (String) request.getAttribute("mensaje");
	
	String nombreMateria	= (String) request.getAttribute("nombreMateria");
	String nombreCarrera	= (String) request.getAttribute("nombreCarrera");
	String cicloMateria		= (String) request.getAttribute("cicloMateria");
	CargaGrupo cargaGrupo	= (CargaGrupo) request.getAttribute("cargaGrupo");
	String modo				= "-";
	
	if (cargaGrupo.getModo().equals("P")) modo = "Face to Face"; 
	if (cargaGrupo.getModo().equals("V")) modo = "Online";
	if (cargaGrupo.getModo().equals("H")) modo = "Hybrid";
	if (cargaGrupo.getModo().equals("R")) modo = "Mixed";
	
	List<CatEdificio> lisEdificios				= (List<CatEdificio>) request.getAttribute("lisEdificios");
	List<CatSalon> lisSalones					= (List<CatSalon>) request.getAttribute("lisSalones");
	List<CargaHorario> lisHorarios				= (List<CargaHorario>) request.getAttribute("lisHorarios");
	List<CargaHorario> lisHorariosRegistrados	= (List<CargaHorario>) request.getAttribute("lisHorariosRegistrados");
	HashMap<String,CatEdificio> mapaEdificios	= (HashMap<String,CatEdificio>) request.getAttribute("mapaEdificios");
	HashMap<String,CatSalon> mapaSalones		= (HashMap<String,CatSalon>) request.getAttribute("mapaSalones");
	HashMap<String,MapaCurso> mapaCursos		= (HashMap<String,MapaCurso>) request.getAttribute("mapaCursos");
	HashMap<String, String> mapaCursosOrigen	= (HashMap<String,String>) request.getAttribute("mapaCursosOrigen");
	
	String[] semana = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"}; 
	
	if(mensaje.equals("1")){
%>
	<script type="text/javascript">
		alert("This classroom has another subject assigned in this hour");
	</script>
<%	
	}
%>
<body>
<div class="container-fluid">
	<h2>Select the classroom<small class="text-muted fs-4">( <%=nombreMateria%> - Cycle: <%=cicloMateria%> - <%=nombreCarrera%> - Attendance/Classroom: <%=modo%>)</small></h2>
	<form id="frmSalon" name="frmSalon" action="salon" method="post">
		<input name="CarreraId" type="hidden" value="<%=carreraId%>">
		<input name="PlanId" type="hidden" value="<%=planId%>">
		<input name=CursoCargaId type="hidden" value="<%=cursoCargaId%>">
		<input name="CursoId" type="hidden" value="<%=cursoId%>">
		<input name="Bloque" type="hidden" value="<%=bloqueId%>">
		
		<div class="alert alert-info">
			<a class="btn btn-primary" href="grupo?CarreraId=<%=carreraId%>&PlanId=<%=planId%>"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
			<b>Building:</b>
			<select onchange="javascript:document.frmSalon.submit();" name="EdificioId" class="input input-medium">
<%	for(CatEdificio edificio : lisEdificios){%>
			<option <%if(edificioId.equals(edificio.getEdificioId()))out.print(" Selected ");%> value="<%=edificio.getEdificioId()%>"><%=edificio.getNombreEdificio()%></option>
<%	}%>
	    	</select>&nbsp;&nbsp;
			<b>Classroom: </b>
			<select onchange='javascript:document.frmSalon.submit();' name="SalonId" style="width:450px;" class="input input-medium">
<%	for(CatSalon salon : lisSalones){%>
				<option <%if(salonId.equals(salon.getSalonId()))out.print(" Selected ");%> value="<%=salon.getSalonId()%>">[<%=salon.getSalonId() %>]-[Capacity:<%=salon.getNumAlumnos()%>] <%=salon.getNombreSalon()%></option>
	<%	}%>
			</select>&nbsp;&nbsp;
		</div>
	</form>
	<table class="table table-bordered">
	<thead>
		<tr><td colspan="20"><h3>List of assigned hours and classrooms for the subject</h3></td></tr>
	</thead>
	<thead class="table-info">
		<tr>
			<th>#<th>
			<th>Op.<th>
			<th>Building<th>
			<th>Classroom<th>
			<th>Capacity<th>
			<th>Day<th>
			<th>Start<th>
			<th>End<th>
			<th>Crash of timetables<th>
		</tr>
	</thead>
<%
	int row = 0;
	for (CargaHorario hora : lisHorarios){
		row++;
		String edificio 		= "0";
		String edificioNombre	= "-";
		String salonNombre		= "-";
		String capacidad		= "0"; 
		if (mapaSalones.containsKey(hora.getSalonId()) ){
			edificio 		= mapaSalones.get(hora.getSalonId()).getEdificioId();
			salonNombre 	= mapaSalones.get(hora.getSalonId()).getNombreSalon();
			capacidad		= mapaSalones.get(hora.getSalonId()).getNumAlumnos();
			if (mapaEdificios.containsKey(edificio)){
				edificioNombre = mapaEdificios.get(edificio).getNombreEdificio();
			}
		}
		
		String chocaCon 	= "-";
		int iniciaHora 		= Integer.parseInt(hora.getDia()+hora.getHoraInicio()+hora.getMinutoInicio());
		int terminaHora		= Integer.parseInt(hora.getDia()+hora.getHoraFinal()+hora.getMinutoFinal());		
		for (CargaHorario registrado : lisHorariosRegistrados){		
			int iniciaReg  	= Integer.parseInt(registrado.getDia()+registrado.getHoraInicio()+registrado.getMinutoInicio());
			int terminaReg 	= Integer.parseInt(registrado.getDia()+registrado.getHoraFinal()+registrado.getMinutoFinal());	
			
			if((iniciaHora > iniciaReg && iniciaHora < terminaReg) || (terminaHora > iniciaReg && terminaHora < terminaReg) || (iniciaHora == iniciaReg && terminaHora == terminaReg)){
				String materiaChoca = "-";
				if (mapaCursosOrigen.containsKey(registrado.getCursoCargaId())){
					materiaChoca = mapaCursosOrigen.get(registrado.getCursoCargaId());
					if (mapaCursos.containsKey(materiaChoca)){
 						materiaChoca = mapaCursos.get(materiaChoca).getNombreCurso();
					}
				}
				if (chocaCon.equals("-")) 
					chocaCon = "<span class='label label-warning' title='"+materiaChoca+"'>"+registrado.getCursoCargaId()+"</span> "+materiaChoca;
				else 
					chocaCon += " <span class='label label-warning' title='"+materiaChoca+"'>"+registrado.getCursoCargaId()+"</span> "+materiaChoca;
			}
		}%>
		<tr>
			<td><%=row%><td>
			<td>
<%		if (hora.getSalonId().equals("0")){
			if (chocaCon.equals("-")){%>
				<a href="grabarSalon?Folio=<%=hora.getFolio()%>&CarreraId=<%=carreraId%>&PlanId=<%=planId%>&CursoCargaId=<%=cursoCargaId%>&CursoId=<%=cursoId%>
				&EdificioId=<%=edificioId%>&SalonId=<%=salonId%>&Bloque=<%=bloqueId%>&Dia=<%=hora.getDia()%>&Inicia=<%=hora.getHoraInicio()+hora.getMinutoInicio()%>
				&Termina=<%=hora.getHoraFinal()+hora.getMinutoFinal()%>"><i class="fas fa-home fa-1x" ></i></a>
<%		
			}else{
				out.print("<i class='fas fa-exclamation-triangle' ></i>");
			}
		}else{%>
				<a href="javascript:BorrarSalon('<%=hora.getFolio()%>','<%=carreraId%>','<%=planId%>','<%=cursoCargaId%>','<%=cursoId%>','<%=bloqueId%>','<%=edificioId%>','<%=salonId%>')"><i class="fas fa-trash" ></i></a>
<%		}%>
			<td>
			<td><%=edificioNombre%><td>
			<td>[<%=hora.getSalonId()%>] <%=salonNombre%><td>
			<td><%=capacidad%><td>
			<td><%=semana[Integer.parseInt(hora.getDia())-1]%><td>
			<td><%=hora.getHoraInicio()%>:<%=hora.getMinutoInicio()%><td>
			<td><%=hora.getHoraFinal()%>:<%=hora.getMinutoFinal()%><td>
			<td><%=chocaCon%><td>
		</tr>
<%	}%>
	</table>
</div>
</body>