<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.carga.spring.CargaAlumno"%>
<%@page import="aca.financiero.spring.FesCcobro"%>
<%@page import="aca.plan.spring.MapaPlan"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.carga.spring.CargaBloque"%>
<%@page import="aca.alumno.spring.AlumAcademico"%>
<%@page import="aca.catalogo.spring.CatModalidad"%>

<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String codigoPersonal		= (String)session.getAttribute("codigoPersonal");
	String opcion 				= request.getParameter("Opcion")==null?"L":request.getParameter("Opcion");
	
	List<CargaAlumno> lisCargas 				= (List<CargaAlumno>)request.getAttribute("lisCargasAlumnos");
	HashMap<String,String> mapaPagos			= (HashMap<String,String>)request.getAttribute("mapaPagos");
	HashMap<String,String> mapaAlumnos			= (HashMap<String,String>)request.getAttribute("mapaAlumnos");	
	HashMap<String,FesCcobro> mapaCobros		= (HashMap<String,FesCcobro>)request.getAttribute("mapaCobros");
	HashMap<String,MapaPlan> mapaPlanes			= (HashMap<String,MapaPlan>)request.getAttribute("mapaPlanes");
	HashMap<String,Carga> mapaCargas			= (HashMap<String,Carga>)request.getAttribute("mapaCargas");
	HashMap<String,String> mapaMaterias			= (HashMap<String,String>)request.getAttribute("mapaMaterias");
	HashMap<String,AlumAcademico> mapAcademico	= (HashMap<String,AlumAcademico>)request.getAttribute("mapAcademico");
	HashMap<String,CatModalidad> mapaModalidad	= (HashMap<String,CatModalidad>)request.getAttribute("mapaModalidad");
	HashMap<String,String> mapaTotalCreditosConfirmadas	= (HashMap<String,String>)request.getAttribute("mapaTotalCreditosConfirmadas");
%>
<div class="container-fluid">
	<h2>Students Closing Enrollment</h2>
	<form name="frmOpcion" action="listado" method="post">
		<div class="alert alert-info d-flex align-items-center">
			Filter:&nbsp;
			<select name="Opcion" class="form-select" onchange="document.frmOpcion.submit();" style="width:200px;">				
				<option value="A" <%=opcion.equals("L")?"selected":""%>>Ready Students</option>
				<option value="T" <%=opcion.equals("T")?"selected":""%>>All</option>
			</select>
			&nbsp;&nbsp;
			<button class="btn btn-primary" type="submit"><i class="fas fa-sync-alt"></i></button>
			
		</div>
	</form>
	<table class="table table-sm table-bordered">
		<tr class="table-info">
			<th>#</th>
			<th>ID</th>
			<th>Name</th>
			<th>Modality</th>
			<th><spring:message code="aca.Plan"/></th>
			<th>Plan Name</th>
			<th><spring:message code="aca.Carga"/></th>
			<th>Load Name</th>
			<th><spring:message code="aca.Bloque"/></th>
			<th class="text-end">Payment</th>
			<th>Confirmed</th>
			<th>Credits</th>
			<th>Enrolled</th>
			<th>Subjects</th>
		</tr>
<%
	int row=0;	
	for (CargaAlumno carga : lisCargas){
		
		String pago = "0";
		if ( mapaPagos.containsKey(carga.getCodigoPersonal()+carga.getCargaId()+carga.getBloqueId() )){
			pago = mapaPagos.get(carga.getCodigoPersonal()+carga.getCargaId()+carga.getBloqueId());
		}
		
		String alumnoNombre = "-";
		if ( mapaAlumnos.containsKey(carga.getCodigoPersonal())){
			alumnoNombre = mapaAlumnos.get(carga.getCodigoPersonal());
		}
		
		String inscrito 	= "N";
		boolean tieneCobro 	= false;
		if ( mapaCobros.containsKey(carga.getCodigoPersonal()+carga.getCargaId()+carga.getBloqueId())){
			inscrito 	= mapaCobros.get(carga.getCodigoPersonal()+carga.getCargaId()+carga.getBloqueId()).getInscrito();
			tieneCobro 	= true;
		}
		
		String nombrePlan = "-";
		if (mapaPlanes.containsKey(carga.getPlanId())){
			nombrePlan = mapaPlanes.get(carga.getPlanId()).getNombrePlan();
		}
		
		String nombreCarga = "-";
		if (mapaCargas.containsKey(carga.getCargaId())){
			nombreCarga = mapaCargas.get(carga.getCargaId()).getNombreCarga();
		}

		String materias = "0";
		if (mapaMaterias.containsKey(carga.getCodigoPersonal()+carga.getCargaId()+carga.getBloqueId())){
			materias = mapaMaterias.get(carga.getCodigoPersonal()+carga.getCargaId()+carga.getBloqueId());
		}
		
		String modalidad = "";
		AlumAcademico alumAcademico = new AlumAcademico();
		if (mapAcademico.containsKey(carga.getCodigoPersonal())){
			alumAcademico = mapAcademico.get(carga.getCodigoPersonal());
		}
		
		String creditos = "0";
		if(mapaTotalCreditosConfirmadas.containsKey(carga.getCodigoPersonal()+carga.getBloqueId())){
			creditos = mapaTotalCreditosConfirmadas.get(carga.getCodigoPersonal()+carga.getBloqueId());
		}
			
		//if (carga.getCodigoPersonal().equals("1210358")) System.out.println("Datos:"+inscrito+":"+carga.getConfirmar()+":"+pago+":"+opcion);
		if ( (inscrito.equals("N") && carga.getConfirmar().equals("S") && Double.valueOf(pago) >= -100) || opcion.equals("T")){
			if(opcion.equals("T")){
				row++;
%>
		<tr>
			<td><%=row%></td>
			<td><%=carga.getCodigoPersonal()%></td>
			<td><%=alumnoNombre%></td>
			<td><%=alumAcademico.getModalidadId()%></td>
			<td><%=carga.getPlanId()%></td>
			<td><%=nombrePlan%></td>
			<td><%=carga.getCargaId()%></td>
			<td><%=nombreCarga%></td>
			<td><%=carga.getBloqueId()%></td>
			<td class="text-end"><%=pago%></td>
			<td><%=carga.getConfirmar().equals("S")?"YES":"NO"%></td>
			<td><%=creditos%></td>
			<td><%=inscrito.equals("S")?"YES":"NO"%></td>
			<td><%=materias%></td>
<%
			}else if(!materias.equals("0") && tieneCobro){
				row++;
%>
		<tr>
			<td><%=row%></td>
			<td><%=carga.getCodigoPersonal()%></td>
			<td><%=alumnoNombre%></td>
			<td><%=alumAcademico.getModalidadId()%></td>
			<td><%=carga.getPlanId()%></td>
			<td><%=nombrePlan%></td>
			<td><%=carga.getCargaId()%></td>
			<td><%=nombreCarga%></td>
			<td><%=carga.getBloqueId()%></td>
			<td class="text-end"><%=pago%></td>
			<td><%=carga.getConfirmar().equals("S")?"YES":"NO"%></td>
			<td><%=creditos%></td>
			<td><%=inscrito.equals("S")?"YES":"NO"%></td>
			<td><%=materias%></td>
<%
			}
		}		
	}
%>
		</tr>
	</table>
</div>