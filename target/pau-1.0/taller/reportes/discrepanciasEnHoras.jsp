<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean scope="page" id="BecAcuerdoU" class="aca.bec.BecAcuerdoUtil"/>
<jsp:useBean scope="page" id="BecPuestoAlumnoU" class="aca.bec.BecPuestoAlumnoUtil"/>
<jsp:useBean scope="page" id="ContEjercicioU"  class="aca.financiero.ContEjercicioUtil" />

<%
	
	//Lista de ejercicios
	ArrayList<aca.financiero.ContEjercicio> ejercicios 	= ContEjercicioU.getListAll(conEnoc, "ORDER BY 1 DESC");

	String ejercicioId									= request.getParameter("idEjercicio")==null?ejercicios.get(0).getIdEjercicio():request.getParameter("idEjercicio");

	//Lista de ejercicios
	ArrayList<aca.bec.BecAcuerdo> acuerdos 	= BecAcuerdoU.getDiscrepanciasEnHoras(conEnoc, ejercicioId, " ORDER BY ID_CCOSTO, CODIGO_PERSONAL");
	
	java.util.HashMap<String, aca.financiero.ContCcosto> centroCostos = aca.financiero.ContCcostoUtil.getMapCentrosCosto(conEnoc, ejercicioId);
	
	java.util.HashMap<String, aca.bec.BecPuestoAlumno> puestos = BecPuestoAlumnoU.getMapPuestos(conEnoc, ejercicioId);
%>

 
<div class="container-fluid">
	<h1>Alumnos con beca básica y adicional</h1>
	
	<form name="frmPuestos" id="frmPuestos" method="post" action="discrepanciasEnHoras">
		<div class="alert alert-info">
			<a class="btn btn-primary" href="menu"><i class="fas fa-arrow-left"></i></a>
			<select  id=idEjercicio name="idEjercicio" onchange="document.frmPuestos.submit()">
				<%for(aca.financiero.ContEjercicio ej : ejercicios){%>
					<option value="<%=ej.getIdEjercicio()%>" <%if(ejercicioId.equals(ej.getIdEjercicio())){out.print("selected");}%>><%=ej.getIdEjercicio()%></option>											
				<%}%>
			</select>	
		</div>	
	</form>
	
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">
		<tr>
			<th>#</th>
			<th><spring:message code="aca.Alumno"/></th>
			<th><spring:message code="aca.TipoAlumno"/></th>
			<th>Departamento</th>
			<th><spring:message code="aca.FechaInicio"/></th>
			<th>Fecha Fin</th>
			<th>Tipo Beca Básica</th>
			<th>Tipo Acuerdo</th>
			<th>Horas Beca Básica</th>
			<th>Horas Acuerdo</th>
		</tr>
	</thead>
		<%int cont = 0;%>
		<%for(aca.bec.BecAcuerdo acuerdo : acuerdos){ %>
			<%
				cont++;
			
				String ccosto 	= "";
				String fInicio 	= "";
				String fFin 	= "";
				if(puestos.containsKey(acuerdo.getPuestoId())){
					ccosto 	= puestos.get(acuerdo.getPuestoId()).getIdCcosto();
					fInicio = puestos.get(acuerdo.getPuestoId()).getFechaIni();
					fFin 	= puestos.get(acuerdo.getPuestoId()).getFechaFin();
				}
			%>
			<tr>
				<td><%=cont %></td>
				<td><%=acuerdo.getCodigoPersonal() %> | <%=aca.alumno.AlumUtil.getNombreAlumno(conEnoc, acuerdo.getCodigoPersonal(), "NOMBRE") %></td>
				<td><%=aca.alumno.AcademicoUtil.getTipoAlumno(conEnoc, acuerdo.getCodigoPersonal()) %></td>
				<td><%=ccosto %> | <%=centroCostos.containsKey(ccosto)==false?"":centroCostos.get(ccosto).getNombre() %></td>
				<td><%=fInicio %></td>
				<td><%=fFin %></td>
				<td><%=aca.bec.BecTipoUtil.getTipoNombre(conEnoc, acuerdo.getTipoadicional(), ejercicioId) %></td>
				<td><%=aca.bec.BecTipoUtil.getTipoNombre(conEnoc, acuerdo.getTipo(), ejercicioId) %></td>
				<td><%=acuerdo.getValor() %></td>
				<td><%=acuerdo.getHoras() %></td>
			</tr>
		<%}%>
	</table>
	
</div>

<%@ include file= "../../cierra_enoc.jsp" %>