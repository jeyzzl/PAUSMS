<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.catalogo.spring.CatModalidad"%>
<%@page import="aca.catalogo.spring.CatPeriodo"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.vista.spring.CargaAcademica"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	function Mostrar(){		
		document.forma.submit();
	}
	
	function cambiaPeriodo(periodoId){
		document.location.href="perfilMaterias?PeriodoId="+periodoId+"&cambioPeriodo=1";
	}
	
	function cambiaCarga(){		
		document.location.href="perfilMaterias?CargaId="+document.forma.CargaId.value+"&cambioCarga=S";
	}
</script>

<link rel="stylesheet" href="../../js/datepicker/datepicker.css" />

<%
	java.text.DecimalFormat formato = new java.text.DecimalFormat("0.0");
	formato.setRoundingMode(java.math.RoundingMode.DOWN);
	
	String periodoId = (String)request.getAttribute("periodoId");
	String cargaId = (String)request.getAttribute("cargaId");
	
	List<CatPeriodo> lisPeriodos 				= (List<CatPeriodo>) request.getAttribute("lisPeriodos");
	List<Carga> lisCargas 						= (List<Carga>) request.getAttribute("lisCargas");	

	//Lista de Materias reprobadas
	List<CargaAcademica> lisReprobadas			= (List<CargaAcademica>) request.getAttribute("lisReprobadas");
	//Mapa de inscritos
	HashMap<String,String> mapaInscritos 		= (HashMap<String,String>) request.getAttribute("mapaInscritos");
	//Mapa de reprobados
	HashMap<String,String> mapaReprobados 		= (HashMap<String,String>) request.getAttribute("mapaReprobados");
	//Mapa de reprobados por ausencia
	HashMap<String,String> mapaReproAusencias	= (HashMap<String,String>) request.getAttribute("mapaReproAusencias");
	//Mapa dados de baja
	HashMap<String,String> mapaBajas			= (HashMap<String,String>) request.getAttribute("mapaBajas");
	//Mapa de las carreras
	HashMap<String,CatCarrera> mapaCarreras 	= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
	//Mapa de las facultades
	HashMap<String,CatFacultad> mapaFacultades 	= (HashMap<String,CatFacultad>) request.getAttribute("mapaFacultades");
	HashMap<String,CatModalidad> mapaModalidades= (HashMap<String,CatModalidad>) request.getAttribute("mapaModalidades");
	
	String modalidades			= (String)session.getAttribute("modalidades") == null?"'1'":session.getAttribute("modalidades").toString();
	String lisModo[] 			= modalidades.replace("'", "").split(",");
%>

<div class="container-fluid">
	<h1>
		Materias con mas alumnos reprobados
	</h1>
	<form name="forma" action="perfilMaterias" method="post">		
		<div class="alert alert-info">
			<a href="menu" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
			<b>Período:</b>
	        <select onchange='javascript:document.forma.submit();' name="PeriodoId" class="input input-medium">
	<%		for(int j=0; j<lisPeriodos.size(); j++){
				CatPeriodo periodo = lisPeriodos.get(j);%>
				<option <%if(periodoId.equals(periodo.getPeriodoId()))out.print("Selected");%> value="<%=periodo.getPeriodoId()%>"><%=periodo.getNombre().replace("Periodo ", "")%></option>
	<%		}%>
	        </select>
	        &nbsp;
			<b>Carga:</b>
		    <select onchange='javascript:document.forma.submit();' name="CargaId" style="width:350px;" class="input input-xlarge">
	<%		for(int i=0; i<lisCargas.size(); i++){
				Carga carga = lisCargas.get(i);%>
				<option <%if(cargaId.equals(carga.getCargaId()))out.print("Selected");%> value="<%=carga.getCargaId()%>">[<%=carga.getCargaId() %>] <%=carga.getNombreCarga()%></option>
	<%		}%>				
	        </select>
	        
			&nbsp;&nbsp;
			<b>Modalidades:</b>
		<%
				for(String mod:lisModo){
					String modalidadNombre = "-";
					if (mapaModalidades.containsKey(mod)){
						modalidadNombre = mapaModalidades.get(mod).getNombreModalidad();
					}					
					out.print("["+modalidadNombre+"] ");
				}		
%>					
		</div>
	</form>	
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	
		<tr>
			<th>#</th>
			<th>Facultad</th>
			<th>Plan</th>
			<th>Materia</th>
			<th>Profesor</th>
			<th># Inscritos</th>
			<th># NA</th>
			<th>% NA</th>
			<th># RA</th>
			<th>% RA</th>
			<th># BA</th>
			<th>% BA</th>
		</tr>
	</thead>
<%
		int cont = 0;
		for(CargaAcademica materia : lisReprobadas){
			cont++;	
			
			String facultad 	= "";
			String plan 		= "";
			String alumIns 		= "0";
			String alumNoAc 	= "0";
			String porAlumNoAc 	= "0";
			String alumRa 		= "0";
			String porAlumRa	= "0";
			String alumBa 		= "0";
			String porAlumBa 	= "0";
			
			if(mapaCarreras.containsKey(materia.getCarreraId())){
				facultad = mapaCarreras.get(materia.getCarreraId()).getFacultadId();
				if(mapaFacultades.containsKey(facultad)){
					facultad = mapaFacultades.get(facultad).getNombreFacultad();
				}		
			}
			
			if(mapaInscritos.containsKey(materia.getCursoCargaId())){
				alumIns = mapaInscritos.get(materia.getCursoCargaId());
			}
			
			if(mapaReprobados.containsKey(materia.getCursoCargaId())){
				alumNoAc = mapaReprobados.get(materia.getCursoCargaId());
			}
			
			if(mapaReproAusencias.containsKey(materia.getCursoCargaId())){
				alumRa = mapaReproAusencias.get(materia.getCursoCargaId());
			}
			
			if(mapaBajas.containsKey(materia.getCursoCargaId())){
				alumBa = mapaBajas.get(materia.getCursoCargaId());
			}	

			porAlumNoAc = formato.format((Float.parseFloat(alumNoAc)*100) / Float.parseFloat(alumIns));
			porAlumRa   = formato.format((Float.parseFloat(alumRa)*100) / Float.parseFloat(alumIns));
			porAlumBa	= formato.format((Float.parseFloat(alumBa)*100) / Float.parseFloat(alumIns));	
			
			if(porAlumNoAc.equals("100.0")){
				porAlumNoAc = "100";
			}
			if(porAlumRa.equals("100.0")){
				porAlumRa = "100";
			}
			if(porAlumBa.equals("100.0")){
				porAlumBa = "100";
			}
%>
			<tr>
				<td><%=cont %></td>
				<td><%=facultad %></td>
				<td><%=materia.getPlanId() %></td>
				<td><%=materia.getNombreCurso() %></td>
				<td><%=materia.getNombre() %></td>
				<td><%=alumIns %></td>
				<td><%=alumNoAc %></td>
				<td><%=porAlumNoAc %></td>
				<td><%=alumRa %></td>
				<td><%=formato.format((Float.parseFloat(alumRa)*100) / Float.parseFloat(alumIns)) %></td>
				<td><%=alumBa %></td>
				<td><%=formato.format((Float.parseFloat(alumBa)*100) / Float.parseFloat(alumIns)) %></td>
			</tr>
<%				
		}
%>			
	</table>
</div>
<script type="text/javascript" src="../../js/datepicker/datepicker.js"></script>
<script>	
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();	
</script>