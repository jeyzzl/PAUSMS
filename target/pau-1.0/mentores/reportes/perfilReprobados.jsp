<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="CatPeriodoUtil" scope="page" class="aca.catalogo.CatPeriodoUtil"/>
<jsp:useBean id="estadisticaU" scope="page" class="aca.vista.EstadisticaUtil"/>
<jsp:useBean id="alumnoCursoU" scope="page" class="aca.vista.AlumnoCursoUtil"/>
<jsp:useBean id="facultadU" scope="page" class="aca.catalogo.FacultadUtil"/>
<jsp:useBean id="empleadoU" scope="page" class="aca.emp.EmpleadoUtil"/>

<script type="text/javascript">
	function Mostrar(){		
		document.forma.submit();
	}
	
	function cambiaPeriodo(periodoId){
		document.location.href="perfilReprobados.jsp?Accion=0&PeriodoId="+periodoId+"&cambioPeriodo=1";
	}
	
	function cambiaCarga(){		
		document.location.href="perfilReprobados.jsp?Accion=0&CargaId="+document.forma.CargaId.value+"&cambioCarga=S";
	}
</script>	

<link rel="stylesheet" href="../../js/datepicker/datepicker.css" />

<%
	String fechaIni			= request.getParameter("FechaIni")==null?(String) session.getAttribute("fechaIni"):request.getParameter("FechaIni");
	String fechaFin			= request.getParameter("FechaFin")==null?(String) session.getAttribute("fechaFin"):request.getParameter("FechaFin");
	String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	
	if (accion.equals("1")){		
		session.setAttribute("fechaIni", fechaIni);
		session.setAttribute("fechaFin", fechaFin);
	}
	
	ArrayList<aca.catalogo.CatPeriodo> listaPeriodos = CatPeriodoUtil.getListAll(conEnoc, " ORDER BY PERIODO_ID");

	if(request.getParameter("cambioPeriodo")!=null && !request.getParameter("cambioPeriodo").equals("")){
		session.setAttribute("periodo", request.getParameter("PeriodoId"));
	}
	String periodoId = (String)session.getAttribute("periodo");
	
	ArrayList<aca.carga.Carga> lisCarga = new aca.carga.CargaUtil().getListAll(conEnoc,"WHERE PERIODO IN ('"+periodoId+"') AND ESTADO = '1' ORDER BY CARGA_ID");
	
	if(request.getParameter("cambioPeriodo")!=null&&!request.getParameter("cambioPeriodo").equals("")&&!lisCarga.isEmpty()){
		session.setAttribute("cargaId", lisCarga.get(0).getCargaId());		
	}else if(request.getParameter("cambioCarga")!=null&&!request.getParameter("cambioCarga").equals("")){
		session.setAttribute("cargaId", request.getParameter("CargaId"));		
	}
	
	String cargaId = (String)session.getAttribute("cargaId");	
	String cargasEditadas   = "";		
	
	if(lisCarga.isEmpty()){
		session.setAttribute("cargaId", "XXXXXX");
	}
	
	String modalidades			= (String)session.getAttribute("modalidades") == null?"'1'":session.getAttribute("modalidades").toString();
	String lisModo[] 			= modalidades.replace("'", "").split(",");
	
	//Lista de alumno inscritos
	ArrayList<aca.vista.Estadistica> lisInscritos 	= new ArrayList<aca.vista.Estadistica>();
	//Lista de alumno curso
	ArrayList<aca.vista.AlumnoCurso> lisAlumnoCurso = new ArrayList<aca.vista.AlumnoCurso>();
	
	//Mapa de las facultades
	java.util.HashMap<String,aca.catalogo.CatFacultad> getMapFacultades = facultadU.getMapAll(conEnoc , "" );
	
	//Editamos las cargas para pasarlas al metodo. Ej. '16171A' seria '1617'
	cargasEditadas = "'"+cargaId.substring(0, 4)+"'";
	
	//Mapa de los mentores
	java.util.HashMap<String,String> mapMentorAlumno = aca.mentores.MentAlumnoUtil.mapMentorAlumno(conEnoc, cargasEditadas);
	
	//Mapa de los empleado para sacar el nombre de los mentores
	java.util.HashMap<String,String> mapNombreMentor = empleadoU.mapEmpleado(conEnoc);
	
	if(accion.equals("1")){
		lisInscritos = estadisticaU.listInscritosCargasModalidadesFechas(conEnoc, "'"+cargaId+"'", modalidades, fechaIni, fechaFin, "");
	}
	
%>

<div class="container-fluid">
	<h1>
		Alumnos con materias reprobadas
	</h1>
	<form name="forma" action="perfilReprobados.jsp?Accion=1" method="post">
		<input type="hidden" name="Accion" value="1";>
		<div class="alert alert-info">
			<b>Período:</b>
	        <select onchange='javascript:cambiaPeriodo(this.value);' name="PeriodoId" class="input input-medium">
	<%		for(int j=0; j<listaPeriodos.size(); j++){
				aca.catalogo.CatPeriodo periodo = listaPeriodos.get(j);%>
				<option <%if(periodoId.equals(periodo.getPeriodoId()))out.print("Selected");%> value="<%=periodo.getPeriodoId()%>"><%=periodo.getNombre().replace("Periodo ", "")%></option>
	<%		}%>
	        </select>
	        &nbsp;
			<b>Carga:</b>
		    <select onchange='javascript:cambiaCarga();' name="CargaId" style="width:350px;" class="input input-xlarge">
	<%		for(int i=0; i<lisCarga.size(); i++){
				aca.carga.Carga carga = lisCarga.get(i);%>
				<option <%if(cargaId.equals(carga.getCargaId()))out.print("Selected");%> value="<%=carga.getCargaId()%>">[<%=carga.getCargaId() %>] <%=carga.getNombreCarga()%></option>
	<%		}%>				
	        </select>
	        
			&nbsp;&nbsp;
			<b>Modalidades:</b>
		<%
				for(String mod:lisModo){
					String nombreModalidad = aca.catalogo.ModalidadUtil.getNombreModalidad(conEnoc, mod);
					out.print("["+nombreModalidad+"] ");	
				}		
%>					
		</div>
		<div class="alert alert-info">
			<a href="menu" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
			Fecha Inicio: <input data-format="hh:mm:ss" id="FechaIni" name="FechaIni" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaIni%>" maxlength="10"/>&nbsp;&nbsp;
			<span class="add-on">
     		 	<i data-time-icon="icon-time" data-date-icon="icon-calendar">
     			 </i>
   			 </span>
			Fecha Final: <input data-format="hh:mm:ss" id="FechaFin" name="FechaFin" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaFin%>" maxlength="10"/>&nbsp;&nbsp;
			<span class="add-on">
     		 	<i data-time-icon="icon-time" data-date-icon="icon-calendar">
     			 </i>
   			 </span>
			<a href="javascript:Mostrar();" class="btn btn-primary btn-sm"><i class="fas fa-sync-alt"></i></a>	
		</div>	
	</form>

	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	
		<tr>
			<td>#</td>
			<td>Facultad</td>
			<td>Plan</td>
			<td>Matricula</td>
			<td>Apellidos</td>
			<td>Nombres</td>
			<td>Mentores</td>
			<td>Materias reprobadas</td>
		</tr>
	</thead>
<%
		int cont = 0;
		for(aca.vista.Estadistica inscrito : lisInscritos){
			
			lisAlumnoCurso = alumnoCursoU.getListAlumnoCargaReprobados(conEnoc, inscrito.getCodigoPersonal(), "'"+cargaId+"'", "");
			
			String facultad 		= "";
			String plan 			= inscrito.getPlanId();
			String codiPer 			= inscrito.getCodigoPersonal();
			String apellidos 		= inscrito.getApellidoPaterno()+" "+inscrito.getApellidoMaterno();
			String nombre 			= inscrito.getNombre();
			String mentor   		= "-";
			String matReprobadas 	= "";
			
			if(getMapFacultades.containsKey(inscrito.getFacultadId())){
				facultad = getMapFacultades.get(inscrito.getFacultadId()).getNombreCorto();
			}
			
			if(mapMentorAlumno.containsKey(inscrito.getCodigoPersonal())){
				mentor = mapMentorAlumno.get(inscrito.getCodigoPersonal());
			}
			
			if(mapNombreMentor.containsKey(mentor)){
				mentor = mapNombreMentor.get(mentor);
			}
			
			if(lisAlumnoCurso.size() > 1){
				
				cont++;
				int co = 0;
				for(aca.vista.AlumnoCurso alumno : lisAlumnoCurso){
					matReprobadas += alumno.getNombreCurso();
					co++;
					if(co < lisAlumnoCurso.size()){
						matReprobadas += ", ";
					}
				}
%>		
				<tr>
					<td><%=cont%></td>
					<td><%=facultad%></td>
					<td><%=plan%></td>
					<td><%=codiPer%></td>
					<td><%=apellidos%></td>
					<td><%=nombre%></td>
					<td><%=mentor%></td>
					<td><%=matReprobadas%></td>
				</tr>	
<%
			} else if(lisAlumnoCurso.size() == 1){ 
				cont++;
				matReprobadas 	= lisAlumnoCurso.get(0).getNombreCurso();
%>		
				<tr>
					<td><%=cont%></td>
					<td><%=facultad%></td>
					<td><%=plan%></td>
					<td><%=codiPer%></td>
					<td><%=apellidos%></td>
					<td><%=nombre%></td>
					<td><%=mentor%></td>
					<td><%=matReprobadas%></td>
				</tr>	
<%
			}	
		}
%>		
	</table>
</div>
<script type="text/javascript" src="../../js/datepicker/datepicker.js"></script>
<script>	
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();	
</script>
<%@ include file= "../../cierra_enoc.jsp" %>