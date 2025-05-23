<%@ include file= "../../con_enoc.jsp"%>
<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="cargaU" scope="page" class="aca.carga.CargaUtil"/>
<jsp:useBean id="estU" scope="page" class="aca.vista.EstadisticaUtil"/>
<jsp:useBean id="alumPlan" scope="page" class="aca.alumno.AlumPlan"/>
<jsp:useBean id="alumEgreso" scope="page" class="aca.alumno.AlumEgreso"/>
<jsp:useBean id="AlumEgresoU" scope="page" class="aca.alumno.AlumEgresoUtil"/>
<jsp:useBean id="alumTit" scope="page" class="aca.tit.DocAlumno"/>
<jsp:useBean id="alumEventoU" scope="page" class="aca.alumno.AlumEventoUtil"/>


<%@ page import="java.util.HashMap"%>
<%@ page import="java.text.DecimalFormat"%>

<head>
<script>
	function refrescar(){
		document.location.href="eficiencia?carga="+document.forma.Cargas.value;
	}
</script>
<%
	DecimalFormat getFormato	= new DecimalFormat("###,##0.00;(###,##0.00)");
	ArrayList<aca.carga.Carga> cargas = cargaU.cargas(conEnoc, "");

	String cargaId = request.getParameter("carga")==null?cargas.get(0).getCargaId():request.getParameter("carga");
	
	ArrayList<aca.vista.Estadistica> alumnos = estU.getAlumnos(conEnoc, cargaId, "ORDER BY FACULTAD_ID, CARRERA_ID, NOMBRE");
	
	HashMap<String, String> eventos = alumEventoU.getMapEventos(conEnoc, "");

%>
</head>
<div class="container-fluid">
<h1>Reporte Eficiencia Terminal</h1>
<form name="forma">
<div class="alert alert-info">
	Carga:
		<select id="Cargas" name="Cargas" onchange="javascript:refrescar()" >
	<%		
			
			for(aca.carga.Carga carga : cargas){
	%>
				<option value="<%=carga.getCargaId() %>" <%if(carga.getCargaId().equals(cargaId))out.print("selected"); %>><%=carga.getCargaId() %> [<%=carga.getFInicio().split(" ")[0] %>]</option>
					
			<%	} %>
		</select>
</div>	
</form>
<body onLoad="document.getElementById('tablaT').innerHTML=document.getElementById('tablaTotales').innerHTML">
	<table style="width:100%">
	</table>
	<div id="tablaT"></div>	
<%
		int contador = 1;
		int contGraduandos 	= 0;
		int contTramites 	= 0;
		int contTitulados 	= 0;
		
		String facultad 	= "";
		String carrera		= "";
		
		for(aca.vista.Estadistica alumno : alumnos){
			
			if(!alumno.getFacultadId().equals(facultad)){
%>
			<table class="table table-bordered">
			<thead>
				<tr>
					<td colspan="6"><h2><%=aca.catalogo.CatFacultadUtil.getNombreFacultad(conEnoc, alumno.getFacultadId()) %></h2></td>
				</tr>
			</thead>
			<thead class="table-info">
			<tr>
				<th width="4%">#</th>
				<th width="30%"><spring:message code="aca.Carrera"/></th>
				<th width="4%"><spring:message code="aca.Numero"/></th>
				<th width="8%">Graduados</th>
				<th width="8%">No Graduados</th>
				<th width="8%">Con Trámite</th>
				<th width="8%">Sin Trámite</th>
				<th width="8%">Titulados</th>
				<th width="8%">No Titulados</th>
			</tr>
			</thead>
<%	
			}
			facultad = alumno.getFacultadId();
			
			if(!alumno.getCarreraId().equals(carrera)){
				
				
				int alum 		   = 0;
				int alumGraduandos = 0;
				int alumTramites   = 0;
				int alumTitulados  = 0;
				
				for(aca.vista.Estadistica alumn : alumnos){ 
					if(alumn.getCarreraId().equals(alumno.getCarreraId())){
						
						alumPlan.mapeaRegId(conEnoc, alumn.getCodigoPersonal(), alumn.getPlanId());
						

						String graduando = AlumEgresoU.existeAlumnoPlan(conEnoc, alumn.getCodigoPersonal(), alumn.getPlanId())==true?"Si":"No";
						String eventoId  = aca.alumno.AlumEgresoUtil.getEvento(conEnoc, alumn.getCodigoPersonal(), alumn.getPlanId());
						
						
						String titulado = "No";
						String tramite 	= "No";
						
						String tituloId = aca.tit.Titulo.getTituloId(conEnoc, alumn.getCodigoPersonal(), alumn.getPlanId());
						
						String fechaTitulacion = "";
						if(!tituloId.equals("")){
							ArrayList<aca.tit.DocAlumVO> alu = alumTit.getDocumentos(conEnoc, tituloId);
							
							for(aca.tit.DocAlumVO al: alu){
								if(al.getDocumentoId()==32 || al.getDocumentoId()==42 || al.getDocumentoId()==48 ){
									titulado = "Si";
									fechaTitulacion = al.getFecha();
									graduando = "Si";
									break;
								}
							}
							
							if(alu.size()>0){
								tramite = "Si";
								graduando = "Si";
							}
						}
						
						if(graduando.equals("Si")) {
							contGraduandos++;
							alumGraduandos++;
						}
						if(tramite.equals("Si")){
							contTramites++;
							alumTramites++;
						}
						if(titulado.equals("Si")){
							contTitulados++;
							alumTitulados++;
						}					
						alum++;
					}
				}				
		%>			
			<tr>
				<td><%=contador %></td>
				<td><%=aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc, alumno.getCarreraId()) %></td>
				<td><%=alum %></td>
				<td><%=alumGraduandos %></td>
				<td><%=alum-alumGraduandos %></td>
				<td><%=alumTramites %></td>
				<td><%=alum-alumTramites %></td>
				<td><%=alumTitulados %></td>
				<td><%=alum-alumTitulados %></td>
			</tr>
		<%  	
				contador++;
			}
			carrera = alumno.getCarreraId();
		} %>
		    </table>
			   
	<div id="tablaTotales">
		<table class="table table-bordered">
		<thead class="table-info">
			<tr>
				<th>Total de alumnos</th>
				<th colspan="1">Graduandos</th>
				<th colspan="1">% Tot</th>
				<th colspan="1">Trámites</th>
				<th colspan="1">% Tot.</th>
				<th colspan="1">% Grad.</th>
				<th colspan="1">Titulados</th>
				<th colspan="1">% Tot.</th>
				<th colspan="1">% Grad.</th>
			</tr>
		</thead>
			<tr class="tr2">
				<td><%=alumnos.size() %></td>
				<td><%=contGraduandos %></td>
				<td><font color="#AE2113"><%=getFormato.format((float)contGraduandos*100/(float)alumnos.size()) %>%</font></td>
				<td><%=contTramites %></td>
				<td><font color="#AE2113"><%=getFormato.format((float)contTramites*100/(float)alumnos.size()) %>%</font></td>
				<td><font color="#AE2113"><%=getFormato.format((float)contTramites*100/(float)contGraduandos) %>%</font></td>
				<td><%=contTitulados %></td>
				<td><font color="#AE2113"><%=getFormato.format((float)contTitulados*100/(float)alumnos.size()) %>%</font></td>
				<td><font color="#AE2113"><%=getFormato.format((float)contTitulados*100/(float)contGraduandos) %>%</font></td>
			</tr>
		</table>
	</div>
	</div>
</body>
<%@ include file= "../../cierra_enoc.jsp" %>