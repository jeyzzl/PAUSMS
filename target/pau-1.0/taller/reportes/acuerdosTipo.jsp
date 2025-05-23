<%@page import="aca.afe.FesCcAfeAcuerdos"%>
<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="contEjercicioU"  class="aca.financiero.ContEjercicioUtil" scope="page"/>
<jsp:useBean id="becAcuerdo"  class="aca.bec.BecAcuerdo" scope="page"/>
<jsp:useBean id="becAcuerdoU"  class="aca.bec.BecAcuerdoUtil" scope="page"/>
<jsp:useBean id="becTipoU"  class="aca.bec.BecTipoUtil" scope="page"/>
<jsp:useBean id="becAccesoU"  class="aca.bec.BecAccesoUtil" scope="page"/>
<jsp:useBean id="becPuestoAlumnoU"  class="aca.bec.BecPuestoAlumnoUtil" scope="page"/>
<jsp:useBean id="fes"  class="aca.afe.FesCcAfeAcuerdos" scope="page"/>
<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<%
	
	String codigo		= (String)session.getAttribute("codigoPersonal");
	boolean admin		= aca.acceso.AccesoUtil.getBecas(conEnoc, codigo);

	//Variables
	String ejercicioId									= request.getParameter("ejercicioId")==null?"001-2013":request.getParameter("ejercicioId");
	
	// Lista de ejercicios
	ArrayList<aca.financiero.ContEjercicio> ejercicios = contEjercicioU.getListAll(conEnoc, "ORDER BY 1 DESC");
	
	String idEjercicio 	= request.getParameter("idEjercicio")==null?ejercicios.get(0).getIdEjercicio():request.getParameter("idEjercicio");

	//ArrayList<aca.bec.BecTipo> tipos = becTipoU.getListUsuario(conEnoc, idEjercicio, " AND ACUERDO NOT IN ('A', 'B') ORDER BY 1");	
	// Lista de acuerdos 
	ArrayList<aca.bec.BecTipo> tipos = becTipoU.getListAcuerdo(conEnoc, idEjercicio, " 'O','M','P', 'E' ", "");
	
	// Lista de privilegios del usuario 
	ArrayList<aca.bec.BecAcceso> acceso = becAccesoU.getListUsuario(conEnoc, codigo, "");
	
	// Mapa de Inscritos
	java.util.HashMap<String,String> mapInscritos = aca.vista.InscritosUtil.getMapaInscritos(conEnoc);
	
	// Mapa de puestos de alumnos
	java.util.HashMap<String,aca.bec.BecPuestoAlumno> mapPuestos = becPuestoAlumnoU.getPuestosAlumno(conEnoc, idEjercicio, aca.util.Fecha.getHoy());
	
	// Mapa de los departamentos
	java.util.HashMap<String,aca.financiero.ContCcosto> mapDepartamentos = aca.financiero.ContCcostoUtil.getMapCentrosCosto(conEnoc, idEjercicio);
	//Mapa de fechas
	String fechaHoy 	= aca.util.Fecha.getHoy();
	String fecha 		= request.getParameter("fechaParametro")==null?fechaHoy:request.getParameter("fechaParametro");
	String accion 		= request.getParameter("Accion")==null?"":request.getParameter("Accion");
	String estado 		= request.getParameter("estado")==null?"I":request.getParameter("estado");
	
	double totalBeca = 0;
	
	String fallback 		= "";
	String fechaInscrito 	= "";
	String departamento 	= "";
	String nombreDepto		= "";
	
	
	
	boolean mostrar = false;
	
	loop:
	for(aca.bec.BecTipo tipo: tipos){
		for(aca.bec.BecAcceso acces: acceso){
			if(tipo.getDepartamentos()==null)continue;
			
			if( tipo.getDepartamentos().contains("-"+acces.getIdCcosto())){
				if(fallback.equals("")){
					fallback = tipo.getTipo();
				}
				mostrar = true;
				break loop;
			}
		}
	}
	
	if(admin && tipos.size()>0){
		mostrar = true;
		fallback = tipos.get(0).getTipo();
	}
	
	String tipoId = request.getParameter("tipo")==null?fallback:request.getParameter("tipo");
	
	
	String msj 			= "";
	
	if(accion.equals("1")){
		becAcuerdo.setFolio(request.getParameter("folio"));
		becAcuerdo.setCodigoPersonal(request.getParameter("codigoPersonal"));
		
		if(becAcuerdoU.deleteReg(conEnoc, request.getParameter("folio"), request.getParameter("codigoPersonal"))){
			msj = "<div class='alert alert-success'>Se Eliminó Correctamente</div>";
		}else{
			msj = "<div class='alert alert-danger'>Ocurrió un Error al Eliminar</div>";
		}	
	}
	
	ArrayList<aca.bec.BecAcuerdo> acuerdos = becAcuerdoU.getListTipo(conEnoc, idEjercicio, fecha, tipoId, estado, " ORDER BY ESTADO ");
	
	//Total de beca
	java.util.HashMap <String, String> totalbeca	= aca.afe.FesCcAfeAcuerdosUtil.mapBecaAcuerdo(conEnoc, ejercicioId, fecha, tipoId);
	String total="";
	java.text.DecimalFormat getFormato= new java.text.DecimalFormat("###,##0.00;-###,##0.00");
	
%>
<style>
	body{
		background:white;
	}
</style>

<div class="container-fluid">
	
	<h1 style="margin-bottom:10px;">
		Acuerdo de Becas
	</h1>
	
	<%=msj %>
	
	<form action="acuerdosTipo" name="forma" method="post">
		<div class="alert alert-info">
			<a class="btn btn-primary" href="menu"><i class="fas fa-arrow-left"></i></a> &nbsp;&nbsp;&nbsp;
		<%if(mostrar){ %>			
			<select name="tipo" id="tipo" style="width:300px;" onchange="document.forma.submit()">
		<%
				for(aca.bec.BecTipo tipo: tipos){
					if(tipo.getDepartamentos()==null)continue;
					boolean permiso = false;
					for(aca.bec.BecAcceso acces: acceso){
						if( tipo.getDepartamentos().contains("-"+acces.getIdCcosto()) ){
							permiso = true;
							break;
						}
					}
					
					if(permiso == false && admin != true)continue;
					
		%>
					<option value="<%=tipo.getTipo() %>" <%if(tipoId.equals(tipo.getTipo()))out.print("selected"); %>><%=tipo.getNombre() %></option>
		<%
				}
		%>
			</select>&nbsp; &nbsp;
			<select name="estado" id="estado" style="width:150px;" onchange="document.forma.submit()">
				<option value="I" <%if(estado.equals("I"))out.print("selected"); %>>Ejercido</option>
				<option value="A" <%if(estado.equals("A"))out.print("selected"); %>>Alta</option>
			</select>
			<div style="float:right">
			Fecha de revisión <input type="text" data-date-format="dd/mm/yyyy" id="fechaParametro" name="fechaParametro" value="<%=fecha%>"/>
			<a class="btn btn-success" onclick="javascript:document.forma.submit();">Cargar fecha</a>
			
			<select name="idEjercicio" id="idEjercicio" style="width:150px;" onchange="document.forma.submit()">
			<%
				for(aca.financiero.ContEjercicio ejercicio: ejercicios){	
			%>
					<option value="<%=ejercicio.getIdEjercicio() %>" <%if(idEjercicio.equals(ejercicio.getIdEjercicio()))out.print("selected"); %>><%=ejercicio.getIdEjercicio() %></option>
			<%
				}
			%>
			</select>
			</div>					
			<%} %>		
		</div>
	</form>
	
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">
		<tr>
			<th>#</th>
			<th><spring:message code="aca.Codigo"/></th>
			<th><spring:message code="aca.Alumno"/></th>
			<th><spring:message code="aca.Fecha"/></th>
			<th><spring:message code="aca.Matricula"/></th>
			<th>Enseñanza</th>
			<th>Internado</th>
			<th>Valor</th>
			<th>Vigencia</th>
			<th><spring:message code="aca.Estado"/></th>
			<th><spring:message code="aca.Inscrito"/></th>
			<th>Lugar</th>
			<th><spring:message code="aca.Beca"/></th>
			<th><spring:message code='aca.Usuario'/></th>
		</tr>
	</thead>
<%		 
		int cont = 0;		
		for(aca.bec.BecAcuerdo acuerdo: acuerdos){
			cont++;
			
			// Obtiene la fecha de inscripcion de los alumnos
			if (mapInscritos.containsKey(acuerdo.getCodigoPersonal()) ) 
				fechaInscrito = mapInscritos.get(acuerdo.getCodigoPersonal());
			else
				fechaInscrito = "X";
			
			// Obtiene el departamento de trabajo del alumno
			nombreDepto = " ";
			if (mapPuestos.containsKey(acuerdo.getCodigoPersonal())){
				aca.bec.BecPuestoAlumno puesto = (aca.bec.BecPuestoAlumno) mapPuestos.get(acuerdo.getCodigoPersonal());
				departamento = puesto.getIdCcosto();
				if (mapDepartamentos.containsKey(puesto.getIdCcosto())){
					nombreDepto = mapDepartamentos.get(puesto.getIdCcosto()).getNombre();
				}else{
					
				}
			}else{
				departamento = "";
			}
			
			if (totalbeca.containsKey(acuerdo.getCodigoPersonal())) {
				total 		= getFormato.format(Double.parseDouble(totalbeca.get(acuerdo.getCodigoPersonal())));
				totalBeca 	+= Double.parseDouble(totalbeca.get(acuerdo.getCodigoPersonal()));
			} else {
				total = getFormato.format(Double.parseDouble("0"));
			}
%>
			<tr>
				<td><%=cont %></td>
				<td><%=acuerdo.getCodigoPersonal() %></td>
				<td><%=aca.alumno.AlumUtil.getNombreAlumno(conEnoc, acuerdo.getCodigoPersonal(), "NOMBRE") %></td>
				<td><%=acuerdo.getFecha() %></td>
				<td style="text-align:right"><%=acuerdo.getMatricula() %></td>
				<td style="text-align:right"><%=acuerdo.getEnsenanza() %> </td>
				<td style="text-align:right"><%=acuerdo.getInternado() %></td>
				<td><%=acuerdo.getValor().equals("C")?"Cantidad":"Porcentaje" %></td>
				<td><%=acuerdo.getVigencia() %></td>
				<td><%=acuerdo.getEstado().equals("I")?"Ejercido":"Alta" %></td>
				<td><%=fechaInscrito.equals("X")?" ":fechaInscrito%></td>
				<td class="ayuda mensaje <%=nombreDepto%>"><%=departamento%></td>
				<td style="text-align:right"><%=total%></td>
				<td style="text-align:right"><%= acuerdo.getUsuario() %></td>
			</tr>
<%		} %>
			<tr>
				<td colspan= "12" style="text-align:center"><b>T &nbsp; O &nbsp; T &nbsp; A &nbsp; L<b></td>		
				<td style="text-align:right"><b><%=getFormato.format(totalBeca)%></b></td>
			</tr>
	</table>
	
	
</div>

<script>
	jQuery('#fechaParametro').datepicker();
</script>

<%@ include file= "../../cierra_enoc.jsp" %>