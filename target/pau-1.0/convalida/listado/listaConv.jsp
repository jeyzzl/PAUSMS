<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<jsp:useBean id="convEU" scope="page" class="aca.conva.ConvEventoUtil"/> 
<jsp:useBean id="convEvento" scope="page" class="aca.conva.ConvEvento"/> 
<jsp:useBean id="acceso" scope="page" class="aca.acceso.Acceso"/>
<jsp:useBean id="AccesoU" scope="page" class="aca.acceso.AccesoUtil" />
<jsp:useBean id="alumnoUtil"  class="aca.alumno.AlumUtil" scope="page"/>
<jsp:useBean id="convMU" scope="page" class="aca.conva.ConvMateriaUtil"/>
<jsp:useBean id="convM" scope="page" class="aca.conva.ConvMateria"/>

<head><link href="css/pa.css" rel="STYLESHEET" type="text/css">
</head>

<STYLE TYPE="text/css">
.tabbox
	{
		background: #eeeeee;
		border-left: 0pt gray solid;
		border-right: 0pt gray solid;
		border-bottom: 1pt gray solid;
	}
</STYLE>
<%	
	String codigo			= (String) session.getAttribute("codigoPersonal");
	String carreraId		= "";
	String carrera			= "";
	String facultadId		= "";
	String facultad			= "";
	String sBgcolor			= "";
	
	String year 			= aca.util.Fecha.getHoy().substring(6, 10);	
	String fechaInicio 		= request.getParameter("FechaInicio")==null?"01/01/"+year:request.getParameter("FechaInicio");
	String fechaFinal  		= request.getParameter("FechaFinal")==null?"31/12/"+year:request.getParameter("FechaFinal");
	
	String accion			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String estado			= request.getParameter("Estado")==null?"-":request.getParameter("Estado");
	
	ArrayList<aca.conva.ConvEvento> lisEvento = new ArrayList<aca.conva.ConvEvento>();
	
	if(estado.equals("-")){
		estado = "A','C','P','R','S','T','X','F','D";
		lisEvento = convEU.getListInscritosEstado(conEnoc, estado, " AND FECHA BETWEEN TO_DATE('"+fechaInicio+"','DD/MM/YYYY') AND TO_DATE('"+fechaFinal+"','DD/MM/YYYY') "
				 + " ORDER BY ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID)), ENOC.CARRERA(PLAN_ID)");
		
		estado = "-";
	}else{
		lisEvento = convEU.getListInscritosEstado(conEnoc, estado, " AND FECHA BETWEEN TO_DATE('"+fechaInicio+"','DD/MM/YYYY') AND TO_DATE('"+fechaFinal+"','DD/MM/YYYY') "
				 + " ORDER BY ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID)), ENOC.CARRERA(PLAN_ID)");
	}

%>
	<div class="container-fluid">
		<h1>Lista de Convalidaciones</h1>
		<form name="forma" action="listaConv?Accion=1" method='post'>
			<div class="alert alert-info d-flex align-items-center">
				Fecha Ini. <input class="form-control" type="text" data-date-format="dd/mm/yyyy" id="FechaInicio" name="FechaInicio" value="<%=fechaInicio%>" size="9"/>&nbsp;&nbsp;
				Fecha Fin. <input class="form-control" type="text" data-date-format="dd/mm/yyyy" id="FechaFinal" name="FechaFinal" value="<%=fechaFinal%>" size="9"/>&nbsp;&nbsp;
				<strong>Seleccione el estado:</strong>
			   	<select name="Estado" class="input input-medium form-select">
			   		<option <%if(estado.equals("-"))out.print("Selected"); %> value="-">Todos</option>
					<option <%if(estado.equals("A"))out.print("Selected"); %> value="A">Trámites</option>
					<option <%if(estado.equals("F"))out.print("Selected"); %> value="F">Sin Documento</option>
					<option <%if(estado.equals("G"))out.print("Selected"); %> value="G">Gradual</option>
					<option <%if(estado.equals("D"))out.print("Selected"); %> value="D">Sin Pago</option>					
					<option <%if(estado.equals("C"))out.print("Selected"); %> value="C">Confirmadas</option>
					<option <%if(estado.equals("P"))out.print("Selected"); %> value="P">Predictamen</option>
					<option <%if(estado.equals("R"))out.print("Selected"); %> value="R">Registrado</option>
					<option <%if(estado.equals("S"))out.print("Selected"); %> value="S"><spring:message code="aca.Solicitud"/></option>
					<option <%if(estado.equals("T"))out.print("Selected"); %> value="T">Terminado</option>
					<option <%if(estado.equals("X"))out.print("Selected"); %> value="X">Cancelado</option>
			       </select>&nbsp;
			       <input class="btn btn-success" type="submit" value="Mostrar"/>
			</div>
	</form>
<%	if (accion.equals("1")){%>
<table class="table table-sm table-bordered"> 
<%	
	acceso = AccesoU.mapeaRegId(conEnoc, codigo);
	if(AccesoU.existeReg(conEnoc, codigo)==true){
		for(int i = 0; i < lisEvento.size(); i++){
			convEvento = (aca.conva.ConvEvento) lisEvento.get(i);
			if(convEvento.getPlanId().equals("-")){
				
			}else{
			carrera = aca.plan.PlanUtil.getCarreraId(conEnoc,convEvento.getPlanId());
			facultad = aca.catalogo.CatCarreraUtil.getFacultadId(conEnoc,carrera);
			if( acceso.getAccesos().indexOf(carrera)!= -1 || acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S") ){
				if(!facultadId.equals(facultad)){
%>
</table>
<table class="table table-sm table-bordered"> 
		<thead>
		<tr>
			<td colspan="12" bgcolor="#CCCCCC"><h3><%=aca.catalogo.CatFacultadUtil.getNombreFacultad(conEnoc, facultad) %></h3></td>
		</tr>
		</thead>
<%
					facultadId = facultad;
				}
				if(!carreraId.equals(carrera)){
%>
		<thead>
		<tr>
			<td colspan="12"><font size="2"><b><%=aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc, carrera) %></b></font></td>
		</tr>
		</thead>
		<thead class="table-info">
		<tr>
			<th><spring:message code="aca.Matricula"/></th>
			<th><spring:message code="aca.Nombre"/></th>
			<th>Plan ID</th>
			<th><spring:message code="aca.Fecha"/></th>
			<th><spring:message code="aca.Estado"/></th>
			<th>N° Materias</th>
			<th>Aceptadas</th>
			<th>Rechazadas</th>
			<th><spring:message code="aca.Tipo"/></th>
			<th>Tipo Conv</th>
		</tr>
		</thead>
<%
					carreraId = carrera;
				}
%>
		<tr class="tr2">
			<td height="15"><%=convEvento.getCodigoPersonal() %></td>
			<td><%=alumnoUtil.getNombre(conEnoc,convEvento.getCodigoPersonal(), "NOMBRE") %></td>
			<td><%=convEvento.getPlanId() %></td>
			<td><%=convEvento.getFecha() %></td>
			<td><%if(convEvento.getEstado().equals("-")){
				out.print("Todos");
			}else if(convEvento.getEstado().equals("S")){
				out.print("Solicitud");
			}else if(convEvento.getEstado().equals("C")){
				out.print("Confirmadas");
			}else if(convEvento.getEstado().equals("G")){
				out.print("Gradual");
			}else if(convEvento.getEstado().equals("F")){
				out.print("Sin Documento");
			}else if(convEvento.getEstado().equals("D")){
				out.print("Sin Pago");
			}else if(convEvento.getEstado().equals("A")){
				out.print("Tramite");
			}else if(convEvento.getEstado().equals("T")){
				out.print("Terminado");
			}else if(convEvento.getEstado().equals("X")){
				out.print("Cancelado");
			}else if(convEvento.getEstado().equals("R")){
				out.print("Registrado");
			}
%>
			</td>
			<td align="center"><%=convMU.getNumMaterias(conEnoc, convEvento.getConvalidacionId()) %></td>
			<td align="center"><%=convMU.getAceptadas(conEnoc, convEvento.getConvalidacionId()) %></td>
			<td align="center"><%=convMU.getRechazadas(conEnoc, convEvento.getConvalidacionId()) %></td>
			<td><%if(convEvento.getTipo().equals("I")){
				out.print("Interna");
			}else if(convEvento.getTipo().equals("E")){
				out.print("Externa");
			}else if(convEvento.getTipo().equals("-")){
				out.print("-");
			}
%>
			</td>
			<td><%if(convEvento.getTipoConv().equals("M")){
				out.print("Materia");
			}else if(convEvento.getTipoConv().equals("G")){
			  	out.print("Grado");
			}
%>
			</td>
		</tr>
<%
				}
			}
		}
	}else{
%>
		<tr>
			<td><b><font color="#000099">No tiene Acceso..!</font> </b></td>
		</tr>
<%
	}
%>
	</table>
<%} %>
</div>
<script>
	jQuery('#FechaInicio').datepicker();
	jQuery('#FechaFinal').datepicker();
</script>
<%@ include file= "../../cierra_enoc.jsp" %>