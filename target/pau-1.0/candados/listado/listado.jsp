<%@page import="java.util.Date,java.text.SimpleDateFormat" %>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.catalogo.spring.CatPeriodo"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.vista.spring.Estadistica"%>
<%@page import="aca.acceso.spring.Acceso"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>
<script type="text/javascript">

	function CambioPeriodo(){
		var periodoId = document.getElementById("PeriodoId").value;
		location.href = "listado?PeriodoId="+periodoId;
	}	
	
	function CambioCarga(){
		var periodoId 	= document.getElementById("PeriodoId").value;
		var cargaId 	= document.getElementById("CargaId").value;
		location.href = "listado?PeriodoId="+periodoId+"&CargaId="+cargaId;
	}

	function Mostrar(){
		var periodoId 	= document.getElementById("PeriodoId").value;
		var cargaId 	= document.getElementById("CargaId").value;
		location.href = "listado?PeriodoId="+periodoId+"&CargaId="+cargaId+"&Mostrar=1";
	}

</script>
<%
	String codigoPersonal			= (String) session.getAttribute("codigoPersonal");

	String periodoId				= (String) request.getAttribute("PeriodoId");
	String cargaId					= (String) request.getAttribute("CargaId");
	
	List<CatPeriodo> lisPeriodos 	= (List<CatPeriodo>)request.getAttribute("lisPeriodos");
	List<Carga> lisCargas		 	= (List<Carga>)request.getAttribute("lisCargas");	
	
	// Lista de carreras
	List<CatCarrera> lisCarreras						= (List<CatCarrera>)request.getAttribute("lisCarreras");
	List<Estadistica> lisAlumnos						= (List<Estadistica>)request.getAttribute("lisAlumnos");
	HashMap<String,CatFacultad> mapaFacultades			= (HashMap<String,CatFacultad>)request.getAttribute("mapaFacultades");
	HashMap<String,String> mapaArchivos					= (HashMap<String,String>)request.getAttribute("mapaArchivos");
	HashMap<String,String> mapaExtranjeros				= (HashMap<String,String>)request.getAttribute("mapaExtranjeros");
	HashMap<String,String> mapaCandados					= (HashMap<String,String>)request.getAttribute("mapaCandados");
	HashMap<String,String> mapModalidades				= (HashMap<String,String>)request.getAttribute("mapModalidades");
	HashMap<String,String> mapTipoAlumno				= (HashMap<String,String>)request.getAttribute("mapTipoAlumno");
	HashMap<String,AlumPersonal> mapCorreoAlumno		= (HashMap<String,AlumPersonal>)request.getAttribute("mapCorreoAlumno");
%>
<div class="container-fluid">
	<h2> Locks Report by Load <small class="text-muted fs-4"> [ <%= cargaId %> ] </small> </h2>	
	<div class="alert alert-info">
		<b>Period:</b>
		<select id="PeriodoId" name="PeriodoId" onchange="javascript:CambioPeriodo();" class="form-select d-inline-flex" style="width: 15rem;" >
<%		for(CatPeriodo periodo : lisPeriodos){%>
			<option value="<%=periodo.getPeriodoId()%>" <%=periodo.getPeriodoId().equals(periodoId)?"selected":""%>><%=periodo.getNombre()%></option>
<%		}%>
		</select>&nbsp;&nbsp;
		<b>Load:</b>
		<select id="CargaId" name="CargaId" onchange="javascript:CambioCarga();" class="form-select d-inline-flex" style="width: 20rem;">
<%		for(Carga carga : lisCargas){%>
			<option value="<%=carga.getCargaId()%>" <%=carga.getCargaId().equals(cargaId)?"selected":""%>><%=carga.getCargaId()%>-<%=carga.getNombreCarga()%></option>
<%		}%>
		</select>
		&nbsp;
		<a href="javascript:Mostrar();" class="btn btn-primary">Show</a>
	</div>
	<div class="alert alert-info">
		<span class='badge bg-warning'>YES</span><b> = The student has 1+ locks</b> &nbsp; &nbsp;<span class='badge bg-success'>NO</span><b> = The student has NO locks</b>
	</div>
	<table id="table" class="table table-sm table-bordered">
<%	
	String facultadId		= "";
	String facultadNombre 	= "";
	for(CatCarrera catCarrera : lisCarreras){
		if(!facultadId.equals(catCarrera.getFacultadId())){
			facultadId = catCarrera.getFacultadId();
			if (mapaFacultades.containsKey(facultadId)){
				facultadNombre = mapaFacultades.get(facultadId).getNombreFacultad(); 
			}
%>
	<thead>	 
	<tr>
		<td colspan="17"><h2><%=facultadNombre%></h2></td>
	</tr>
	</thead>	 
<%		}%>
	<thead>	 
	<tr>
		<td colspan="17"><h4><%=catCarrera.getCarreraId() %>:<%=catCarrera.getNombreCarrera() %></h4></td>
	</tr>
	</thead>	 
	<thead class="table-info">	 
	<tr>
		<th><spring:message code="aca.Matricula"/></th>
		<th><spring:message code="aca.Nombre"/></th>
		<th>Email</th>
		<th>Plan</th>
		<th title="Digital Archive">D.A</th>
		<th>Disc.</th>
		<th>Ing.</th>		
		<th>Bib.</th>
		<th>Fin.</th>
		<th>CTP</th>
		<%-- <th>Extra.</th> --%>
		<th>VRE</th>
		<%-- <th>Conv.</th> --%>
		<th>Resi.</th>
		<th>VRF</th>
		<th>Status</th>
		<th><spring:message code="aca.Modalidad"/></th>
		<th><spring:message code="aca.Tipo"/></th>
	</tr>
	</thead>	 
<%
		String matricula	= "";
		for(Estadistica estadistica :lisAlumnos){
			if (estadistica.getCarreraId().equals(catCarrera.getCarreraId())){
				boolean vencioFM3 = false;
				if(!matricula.equals(estadistica.getCodigoPersonal())){
					matricula = estadistica.getCodigoPersonal();
					
					String archivo = "N";
					if (mapaArchivos.containsKey(matricula)){
						archivo = mapaArchivos.get(matricula);
					}
					
					String info = "";
					if(archivo.equals("P")){
						if(archivo.equals("N")){
							info = "<span class='badge'>Pro</span>";
						}else{
							info = "<span class='badge bg-info'>Pro</span>";
						}
						
					}else if(archivo.equals("N")){
						info = "<span class='badge bg-success'>NO</span>";
					}else{
						info = "<span class='badge bg-warning'>YES</span>";
					}
					/*
					String extranjero = "N";
					if (mapaExtranjeros.containsKey(matricula)){
						extranjero = mapaExtranjeros.get(matricula);
					}
					*/
					String disciplina = "0";
					if (mapaCandados.containsKey(matricula+"01")){
						disciplina =  mapaCandados.get(matricula+"01");
					}
					
					String ingreso = "0";
					if (mapaCandados.containsKey(matricula+"02")){
						ingreso =  mapaCandados.get(matricula+"02");
					}
					
					String biblioteca = "0";
					if (mapaCandados.containsKey(matricula+"03")){
						biblioteca =  mapaCandados.get(matricula+"03");
					}
					
					String finanzas = "0";
					if (mapaCandados.containsKey(matricula+"04")){
						finanzas =  mapaCandados.get(matricula+"04");
					}					
					
					String colportores = "0";
					if (mapaCandados.containsKey(matricula+"05")){
						colportores =  mapaCandados.get(matricula+"05");
					}
					
					String extranjero = "0";
					if (mapaCandados.containsKey(matricula+"06")){
						extranjero =  mapaCandados.get(matricula+"06");
					}
					
					String vre = "0";
					if (mapaCandados.containsKey(matricula+"07")){
						vre =  mapaCandados.get(matricula+"07");
					}
					
					String convalidaciones = "0";
					if (mapaCandados.containsKey(matricula+"08")){
						convalidaciones =  mapaCandados.get(matricula+"08");
					}					
					
					String residencia = "0";
					if (mapaCandados.containsKey(matricula+"09")){
						residencia =  mapaCandados.get(matricula+"09");
					}
					
					String vrf = "0";
					if (mapaCandados.containsKey(matricula+"10")){
						vrf =  mapaCandados.get(matricula+"10");
					}
					
					String modalidad = "-";
					if (mapModalidades.containsKey(estadistica.getModalidadId())){
						modalidad =  mapModalidades.get(estadistica.getModalidadId());
					}
					
					String tipoAlumno = "-";
					if (mapTipoAlumno.containsKey(estadistica.getTipoAlumnoId())){
						tipoAlumno =  mapTipoAlumno.get(estadistica.getTipoAlumnoId());
					}
					
					String correo = "-";
					if(mapCorreoAlumno.containsKey(estadistica.getCodigoPersonal())){
						correo = mapCorreoAlumno.get(estadistica.getCodigoPersonal()).getEmail();
					}
%>
		<tr class="tr2">
			<td><%=estadistica.getCodigoPersonal() %></td>
			<td><%=estadistica.getApellidoPaterno() %> <%=estadistica.getApellidoMaterno() %> <%=estadistica.getNombre() %></td>
			
			<td><%=correo%></td>
			<td><%=estadistica.getPlanId()%></td>
			<td><%=info%></td>
			<td><%=disciplina.equals("0")?"<span class='badge bg-success'>NO</span>":"<span class='badge bg-warning'>YES</span>"%></td>
			<td><%=ingreso.equals("0")?"<span class='badge bg-success'>NO</span>":"<span class='badge bg-warning'>YES</span>"%></td>
			<td><%=biblioteca.equals("0")?"<span class='badge bg-success'>NO</span>":"<span class='badge bg-warning'>YES</span>"%></td>
			<td><%=finanzas.equals("0")?"<span class='badge bg-success'>NO</span>":"<span class='badge bg-warning'>YES</span>"%></td>
			<td><%=colportores.equals("0")?"<span class='badge bg-success'>NO</span>":"<span class='badge bg-warning'>YES</span>"%></td>
			<%-- <td><%=extranjero.equals("0")?"<span class='badge bg-success'>NO</span>":"<span class='badge bg-warning'>YES</span>"%></td> --%>
			<td><%=vre.equals("0")?"<span class='badge bg-success'>NO</span>":"<span class='badge bg-warning'>YES</span>"%></td>
			<%-- <td><%=convalidaciones.equals("0")?"<span class='badge bg-success'>NO</span>":"<span class='badge bg-warning'>YES</span>"%></td> --%>
			<td><%=residencia.equals("0")?"<span class='badge bg-success'>NO</span>":"<span class='badge bg-warning'>YES</span>"%></td>
			<td><%=vrf.equals("0")?"<span class='badge bg-success'>NO</span>":"<span class='badge bg-warning'>YES</span>"%></td>			
			<td><%=estadistica.getResidenciaId().equals("E")?"Day Student":"Boarding" %></td>
			<td><%=modalidad%></td>
			<td><%=tipoAlumno%></td>
		</tr>
<%
				}
			}
		}
		
	}
%>
				</table>
			</td>
		</tr>
		<tr class="oculto">
			<td>&nbsp;</td>
		</tr>
		<tr class="oculto">
			<td align="center" class="end"></td>
		</tr>
	</table>
</div>