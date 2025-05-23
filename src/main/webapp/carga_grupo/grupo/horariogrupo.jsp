
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.carga.spring.CargaGrupo"%>
<%@page import="aca.carga.spring.CargaGrupoCurso"%>
<%@page import="aca.carga.spring.CargaGrupoHora"%>
<%@page import="aca.vista.spring.CargaAcademica"%>
<%@page import="aca.catalogo.spring.CatHorario"%>
<%@page import="aca.catalogo.spring.CatHorarioFacultad"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<html>
<head>
	<script type="text/javascript">
		function BorrarHora(maestro,cargaId,carreraId,planId,cursoCargaId,cursoId,horarioId,dia,periodo){
			if (confirm("Are you sure you want to delete this period from the timetable?")){
				document.location.href="borrarHora?Maestro="+maestro+"&CargaId="+cargaId+"&CarreraId="+carreraId+"&PlanId="+planId+"&CursoCargaId="+cursoCargaId+"&CursoId="+cursoId+"&HorarioId="+horarioId+"&Dia="+dia+"&PeriodoId="+periodo;
			}	
		}
	</script>
</head>
<%
	String cursoCargaId 		= (String)session.getAttribute("cursoCargaId");
	String carreraId 			= (String)session.getAttribute("carreraId");
	String planId 			    = (String)session.getAttribute("planId");
	String cursoId 				= request.getParameter("CursoId")==null?"0":request.getParameter("CursoId");
	String cargaId 				= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
	String modifica 	   		= request.getParameter("Modifica")==null?"N":request.getParameter("Modifica");	
	String mensaje	 	   		= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");	
	String maestro	 	   		= request.getParameter("Maestro")==null?"0":request.getParameter("Maestro");	
	CargaGrupo grupo			= (CargaGrupo) request.getAttribute("grupo");
	String verGrupo				= (String)session.getAttribute("verGrupo");
	CatHorario catHorario		= (CatHorario) request.getAttribute("catHorario");
	String bloqueNombre			= (String) request.getAttribute("bloqueNombre");
	String horario[][] 			= new String[7][30];

	String horarioOrigen		= (String) request.getAttribute("horarioOrigen");
	
	List<CargaAcademica> lisMaterias				= (List<CargaAcademica>) request.getAttribute("lisMaterias");
	List<CargaGrupoHora> lisHorarios				= (List<CargaGrupoHora>) request.getAttribute("lisHorarios");
	List<CatHorarioFacultad> lisPeriodos			= (List<CatHorarioFacultad>) request.getAttribute("lisPeriodos");
	HashMap<String,String> mapaMaestros				= (HashMap<String,String>) request.getAttribute("mapaMaestros");
	HashMap<String,String> mapaHorarios				= (HashMap<String,String>) request.getAttribute("mapaHorarios");
	HashMap<String,String> mapaHoras				= (HashMap<String,String>) request.getAttribute("mapaHoras");
	
	//System.out.println("Horario:"+catHorario.getHorarioId() );
	//System.out.println("Size:"+lisPeriodos.size());
	//Inicializar horario;
	for (int i=0; i<7; i++){
		for (int j=0; j<30;j++){
			horario[i][j]="0";
		}
	}	
%>
<body>
<div class="container-fluid">
	<h2>Assign Timetable <small class="text-muted fs-5">( Group:<%=verGrupo%> - <%=catHorario.getDescripcion()%> - Block:<%=grupo.getBloqueId().trim()%> - <%=bloqueNombre%> - Load:<%=cargaId%> )</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="grupo?CarreraId=<%=carreraId%>&PlanId=<%=planId %>"><i class="fas fa-arrow-left"></i></a>
	</div>
<%  if(mensaje.equals("1")){%>
	<div class="alert alert-success">Saved</div>
<%  }else if(mensaje.equals("2")){%>
	<div class="alert alert-danger"> The schedule is registered, but the lecturer has a subject assigned in this period </div>
<%  }%>
<%-- 	<%	if (!choca.equals("-")){%><div class="alert alert-danger"><%=choca%></div><% }%> --%>
	<div class="row">
		<div class="col">
		<table class="table table-bordered">
		<thead class="table-info">
			<tr>
				<th width="5%">#</th>
				<th width="5%"><i class="fas fa-check-circle" ></i></th>
				<th width="5%">Record</th>		
				<th width="40%">Timetable</th>
				<th width="5%">Group</th>
				<th width="25%">Lecturer</th>
				<th width="5%">HR</th>
				<th width="5%">HG</th>
				<th width="5%">HF</th>		
			</tr>
		</thead>
	<%
		int row = 0;
		for (CargaAcademica carga : lisMaterias){
			row++;
			
			String maestroNombre = "-";
			if (mapaMaestros.containsKey(carga.getCodigoPersonal())){
				maestroNombre = mapaMaestros.get(carga.getCodigoPersonal());
			}
			
			String horasMateria = "0";
			if (mapaHoras.containsKey(carga.getCursoCargaId())){
				horasMateria = mapaHoras.get(carga.getCursoCargaId());
			}
			
			int horasFaltan = 0;
			horasFaltan = Integer.parseInt(carga.getHh()) - Integer.parseInt(horasMateria);
			
			String modificaMateria = carga.getOrigen().equals("O")?"S":"N";
			
			String colorRow = "<span class='badge bg-dark'>"+String.valueOf(row)+"</span>";
			if (carga.getOrigen().equals("E")) colorRow = "<span class='badge bg-warning'>"+String.valueOf(row)+"</span>";		
						
	%>
			<tr>
				<td class="right"><%=colorRow%></td>
				<td>
	<% 		if(carga.getCursoCargaId().equals(cursoCargaId)){%>
				<i class="fas fa-check-circle" ></i>
	<%		}else{%>
				<a href="horariogrupo?CarreraId=<%=carreraId%>&PlanId=<%=planId%>&CursoCargaId=<%=carga.getCursoCargaId()%>&CursoId=<%=carga.getCursoId()%>&Modifica=<%=modificaMateria%>"><i class="fas fa-circle" ></i></a>
	<% 		}%>
				</td>
				<td><%=carga.getCursoCargaId()%></td>
				<td title="<%=carga.getCursoCargaId()%>"><%=carga.getNombreCurso()%></td>
				<td><%=carga.getGrupo()+":"+carga.getGrupoHorario()%></td>
				<td><%=maestroNombre%></td>
				<td><%=carga.getHh()%></td>
				<td><%=horasMateria%></td>
				<td><%=horasFaltan%></td>
			</tr>
	<%	} %>		
			</table>
		</div>
		<div class="col">
		<table class="table table-bordered">
		<thead class="table-info">
			<tr>
				<th width="3%">#</th>
				<th width="5%">Start</th>
				<th width="5%">End</th>
				<th width="11%">Sunday</th>				
				<th width="11%">Monday</th>
				<th width="11%">Tuesday</th>
				<th width="11%">Wednesday</th>
				<th width="11%">Thursday</th>
				<th width="11%">Friday</th>
				<th width="11%">Saturday</th>
			</tr>
			</thead>
<%
		row = 0;
		for (CatHorarioFacultad periodo : lisPeriodos){
			row++;
%>
			<tr>
				<td><%=periodo.getPeriodo()%></td>
				<td><%=periodo.getHoraInicio()+":"+periodo.getMinutoInicio()%></td>
				<td><%=periodo.getHoraFinal()+":"+periodo.getMinutoFinal()%></td>						
<%		
			for (int dia=1; dia<=7; dia++){
				String datos 			= "";
				String fondo			= "";
				boolean encontre		= false;
				 
				out.print("<td>");
				for(CargaGrupoHora horaMateria : lisHorarios){					
					if (String.valueOf(dia).equals(horaMateria.getDia()) && periodo.getPeriodo().equals(horaMateria.getPeriodo())){						
						encontre = true;
						if (horaMateria.getCursoCargaId().equals(cursoCargaId)){
							fondo 	= "style='background-color:#e3e3e1'";
							//Si es una materia de Origen(materia que pertenece a la carrera en revisión)	
							boolean encontreNombre 	= false;
							for (CargaAcademica carga : lisMaterias){
								if (carga.getCursoCargaId().equals(horaMateria.getCursoCargaId())){
									if(encontreNombre == false){
										datos = carga.getNombreCurso();		
									}
									encontreNombre = true;
								}
							}
							if (modifica.equals("S")){
						%>								
								<span class="badge bg-warning"><a href="javascript:BorrarHora('<%=maestro%>','<%=cargaId%>','<%=carreraId%>','<%=planId%>','<%=cursoCargaId%>','<%=cursoId%>','<%=catHorario.getHorarioId()%>','<%=dia%>','<%=periodo.getPeriodo()%>')" style="color:black"><i class="fas fa-trash" ></i></a></span>&nbsp;<span style="color:orange;font-weight:bold"><%=datos%></span><br>
						<%
							}else{
								out.print("<span class='badge bg-warning'>*</span> <span style='color:orange;font-weight:bold'>"+datos+"</span><br>");
							}
						}else{
							for (CargaAcademica carga : lisMaterias){
								if (carga.getCursoCargaId().equals(horaMateria.getCursoCargaId())){
									datos = carga.getNombreCurso();
									out.print("<span class='badge bg-success'>*</span> "+datos+"<br>");
								}
							}
						}
					}
				}
				if (!encontre && periodo.getTipo().equals("1")){
					if(horarioOrigen.equals("O")){
						
		%>				
					<a href="grabarHora?Maestro=<%=maestro%>&CarreraId=<%=carreraId%>&PlanId=<%=planId%>&CursoCargaId=<%=cursoCargaId%>&CargaId=<%=cargaId%>&Modifica=<%=modifica%>&CursoId=<%=cursoId%>&HorarioId=<%=catHorario.getHorarioId()%>&Dia=<%=dia%>&PeriodoId=<%=periodo.getPeriodo()%>&BloqueId=<%=grupo.getBloqueId().trim()%>"><i class="fas fa-calendar-alt"></i></a>
		<%		
					}
				}else if (periodo.getTipo().equals("2")){
					out.print(periodo.getNombre());
				}
				
				out.print("</td>");
			}			
		%>			
			</tr>
	<%	} %>				
			</table>
		</div>			
	</div>		
</div>	
</body>
</html>