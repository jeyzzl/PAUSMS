<%@ include file="../../headPortal.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.salida.spring.SalSolicitud"%>
<%@page import="aca.salida.spring.SalGrupo"%>
<%@page import="aca.salida.spring.SalInforme"%>
<head>
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
	<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
	<script type="text/javascript">
		function Mostrar(){
			document.frmBusqueda.submit();
		}
	</script>
</head>	
<%
	String codigoPersonal 	= (String)request.getAttribute("codigoPersonal");
	String periodo 			= (String)request.getAttribute("periodo");
	String grupoId 			= (String)request.getAttribute("periodo");
	
	String fechaIni			= (String)request.getAttribute("fechaIni");
	String fechaFin			= (String)request.getAttribute("fechaFin");
	
	List<SalGrupo> listGrupos 			= (List<SalGrupo>)request.getAttribute("listGrupos");
	List<SalSolicitud> lisSolicitud 	= (List<SalSolicitud>)request.getAttribute("lisSolicitud");

	HashMap<String,String> mapaMaestros 		= (HashMap<String,String>)request.getAttribute("mapaMaestros");
	HashMap<String,String> mapaGrupoNombre 		= (HashMap<String,String>)request.getAttribute("mapaGrupoNombre");
	HashMap<String,SalInforme> mapaInformes 	= (HashMap<String,SalInforme>)request.getAttribute("mapaInformes");
	HashMap<String,String> mapaAlumnos 			= (HashMap<String,String>)request.getAttribute("mapaAlumnos");
	

%>
<body>

<div class="container-fluid">
	<h2>Listado de Salidas</h2>
	<form id="frmBusqueda" name="frmBusqueda" method="post" action="listado">
	<input type="hidden" name="Accion">
	<div class="alert alert-info">
		Fecha Inicio: <input type="text" class="input input-medium" data-date-format="dd/mm/yyyy" id="FechaIni" name="FechaIni" value="<%=fechaIni%>" required/>&nbsp;&nbsp;
		Fecha Final: <input type="text" class="input input-medium" data-date-format="dd/mm/yyyy" id="FechaFin" name="FechaFin" value="<%=fechaFin%>" required/>&nbsp;&nbsp;
		<b>Grupo:</b>&nbsp;
		<select name="Grupo" id="Grupo">
			<option value="0">Todos</option>
		<%	for (int i = 0; i < listGrupos.size(); i++) {
				SalGrupo grupo = listGrupos.get(i);
			%>
				<option value="<%=grupo.getGrupoId()%>" <%=grupoId.equals(grupo.getGrupoId())?"Selected":""%>>
					<%=grupo.getGrupoNombre()%>
				</option>
			<%
			}
		%>
		</select>&nbsp;&nbsp;
		<a class="btn btn-primary btn-small" href="javascript:Mostrar();"><i class="icon-search icon-white"></i> Buscar</a>
	</div>
	</form>
	<table class="table table-sm table-bordered table-striped">
		<tr class="table-info">
			<th width="5%" class="center">#</th>
			<th width="10%" class="left">Grupos</th>
			<th width="10%" style="text-align:center">Salida</th>
			<th width="10%" style="text-align:center">Llegada</th>
			<th width="15%" style="text-align:left">Usuario/Registro</th>
			<th width="15%" style="text-align:left">Responsable/Salida</th>
			<th width="3%" style="text-align:center">D1</th>
			<th width="3%" style="text-align:center">D2</th>
			<th width="3%" style="text-align:center">D3</th>
			<th width="3%" style="text-align:center">D4</th>
			<th width="10%" style="text-align:left">Lugar</th>
			<th width="5%" style="text-align:center">PDF</th>
			<th width="8%" style="text-align:center">Estado</th>
			<th width="5%" style="text-align:center">Informe</th>
		</tr>
		<%
			String nombre 		= "";
			String usuario 		= "";
			String nombreGrupo 	= "";
			for (int i = 0; i < lisSolicitud.size(); i++) {
				SalSolicitud sol = lisSolicitud.get(i);
				if(mapaMaestros.containsKey(sol.getResponsable())){
					nombre = mapaMaestros.get(sol.getResponsable());
				}
				if(mapaMaestros.containsKey(sol.getUsuario())){
					usuario	= mapaMaestros.get(sol.getUsuario());
				}
				if(mapaGrupoNombre.containsKey(sol.getGrupoId())){
					nombreGrupo	= mapaGrupoNombre.get(sol.getGrupoId());
				}		
				String 	estado ="";
				if (sol.getAutorizo().equals("0") && sol.getFolio().equals("0")){					
					if (sol.getEstado().equals("S")){
						estado="<span class='badge bg-warning'>Solicitud</span>";
					}else{						
						estado="<span class='badge bg-success'>Enviada</span>";
					}
				}else if(!sol.getAutorizo().equals("0") && sol.getFolio().equals("0")){
					estado="<span class='badge bg-info'>Preautorizado</span>";
				}else if(!sol.getAutorizo().equals("0") && !sol.getFolio().equals("0")){
					estado="<span class=' badge bg-dark'>Autorizado</span>";
				}
				
				String existeInforme = "<span class='badge bg-warning'><a style='color:white'>NO</a></span>";
			    if (mapaInformes.containsKey(sol.getSalidaId())){
			    	existeInforme = "<span class='badge bg-info'><a href='verInforme?SalidaId="+sol.getSalidaId()+"' style='color:white'>SI</a></span>";
			    }			
			    
			    String  color = "badge rounded-pill bg-success";
			    String dormi1 = "1";
				if(mapaAlumnos.containsKey(sol.getSalidaId() + "-" + dormi1)){
					dormi1  = mapaAlumnos.get(sol.getSalidaId() + "-" + dormi1);
				}
				String dormi2 = "2";
				if(mapaAlumnos.containsKey(sol.getSalidaId() + "-" + dormi2)){
					dormi2  = mapaAlumnos.get(sol.getSalidaId() + "-" + dormi2);
				}
				String dormi3 = "3";
				if(mapaAlumnos.containsKey(sol.getSalidaId() + "-" + dormi3)){
					dormi3  = mapaAlumnos.get(sol.getSalidaId() + "-" + dormi3);
				}
				String dormi4 = "4";
				if(mapaAlumnos.containsKey(sol.getSalidaId() + "-" + dormi4)){
					dormi4  = mapaAlumnos.get(sol.getSalidaId() + "-" + dormi4);
				}
				
				
		%>
		<tr>
			<td style="text-align: center"><%=i+1%></td>
			<td class="left"><%=nombreGrupo%></td>
			<td style="text-align:center"><%=sol.getFechaSalida()%></td>
			<td style="text-align:center"><%=sol.getFechaLlegada()%></td>
			<td class="left"><%=usuario%></td>
			<td class="left"><%=nombre%></td>	
			<td class="text-center"><span class="<%=color%>"><%=dormi1%></span></td>
			<td class="text-center"><span class="<%=color%>"><%=dormi2%></span></td>
			<td class="text-center"><span class="<%=color%>"><%=dormi3%></span></td>
			<td class="text-center"><span class="<%=color%>"><%=dormi4%></span></td>	
			<td class="left"><%=sol.getLugar()%></td>	
			<td class="center"><a class="btn btn-info" href="../solicitud/pdf?salida=<%=sol.getSalidaId()%>">PDF</a></td>
			<td class="center"><%=estado%></td>	
			<td style="text-align:center;"><%=existeInforme%></td>
		</tr>
			<%
			}
		%>
	</table>	
</div>
</body>
<script type="text/javascript"> 
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();
</script>