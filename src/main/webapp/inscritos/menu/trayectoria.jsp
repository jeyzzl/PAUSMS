<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.alumno.spring.AlumEstado"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.financiero.spring.FinSaldo"%>
<%@page import="aca.inscrito.spring.InsAlumno"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">
	function cambiaPeriodo(periodoId){
		document.location.href="inscritos_per?PeriodoId="+periodoId+"&cambioPeriodo=1";
	}	
</script>

<!-- inicio de estructura -->
<%		
	java.text.DecimalFormat frmDecimal= new java.text.DecimalFormat("###,##0.00; -###,##0.00");
	
	String cargas 		= (String)session.getAttribute("cargas");

	List<AlumEstado> lisAlumnos 					= (List<AlumEstado>)request.getAttribute("lisAlumnos");
	List<InsAlumno> lisInscripciones				= (List<InsAlumno>)request.getAttribute("lisInscripciones");
	HashMap<String,CatFacultad> mapaFacultades	 	= (HashMap<String,CatFacultad>)request.getAttribute("mapaFacultades");
	HashMap<String,CatCarrera> mapaCarreras	 		= (HashMap<String,CatCarrera>)request.getAttribute("mapaCarreras");
	HashMap<String,AlumPersonal> mapaAlumno		 	= (HashMap<String,AlumPersonal>)request.getAttribute("mapaAlumno");
	HashMap<String,FinSaldo> mapaSaldos		 		= (HashMap<String,FinSaldo>)request.getAttribute("mapaSaldos");
	HashMap<String,String> mapaEdad		 			= (HashMap<String,String>)request.getAttribute("mapaEdad");
	
%>
<div class="container-fluid">
	<h2>
		<a class="btn btn-primary btn-small" href="menu"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
		Trayectoria de alumnos inscritos<small class="text-muted fs-5">(<%=cargas%>)</small>
	</h2>
	<table class="table table-sm table-bordered">
		<thead>
			<tr>
				<th align="text-center">#</th>
				<th align="text-center">Matrícula</th>
				<th align="text-center">Nombre</th>
				<th align="text-center">Apellidos</th>
				<th align="text-center">Sexo</th>
				<th align="text-center">Edad</th>
				<th align="text-center">Plan de estudios</th>
				<th align="text-center">Semestre que cursa</th>
				<th align="text-center">Saldo financiero actual</th>
				<th align="text-center">Regular o irregular</th>
			</tr>
		</thead>
		<tbody>
<% 		
	int row = 0;
	for(AlumEstado alumno : lisAlumnos){
		AlumPersonal personal = new AlumPersonal();
		row++;
		if(mapaAlumno.containsKey(alumno.getCodigoPersonal())){
			personal =  mapaAlumno.get(alumno.getCodigoPersonal());
		}

		String saldoTotal 	= "";
		FinSaldo finSaldo = new FinSaldo();
		
		if (mapaSaldos.containsKey(alumno.getCodigoPersonal())){
			finSaldo = mapaSaldos.get(alumno.getCodigoPersonal());
			saldoTotal = frmDecimal.format(Float.parseFloat(finSaldo.getSaldoSP()))+" Db";
			if(Float.parseFloat(finSaldo.getSaldoSP()) < 0){
				saldoTotal = frmDecimal.format(Float.parseFloat(finSaldo.getSaldoSP().substring(1)))+" Cr";
			}
		}
		
		String edad = "";
		if(mapaEdad.containsKey(alumno.getCodigoPersonal())){
			edad =  mapaEdad.get(alumno.getCodigoPersonal());
		}
		
		String estado = "-";
		int total = 0;
		for (InsAlumno insAlumno : lisInscripciones){
			if ( insAlumno.getCodigoPersonal().equals(alumno.getCodigoPersonal()) && insAlumno.getCargaId().equals(alumno.getCargaId()) 
				&& insAlumno.getPlanId().equals(alumno.getPlanId()) ){
				total++;
			}
		}
		if (total==1) estado = "Regular"; else estado = "Irregular";
%>
			<tr>
				<td><%=row%></td>
				<td><%=alumno.getCodigoPersonal()%></td>
				<td><%=personal.getNombre()%></td>
				<td><%=personal.getApellidoPaterno()%> <%=personal.getApellidoMaterno()%></td>
				<td><%=personal.getSexo().equals("M")?"M":"F"%></td>
				<td><%=edad%></td>
				<td><%=alumno.getPlanId()%></td>
				<td><%=alumno.getGrado()%></td>
				<td class="text-end"><%=saldoTotal%></td>
				<td><%=estado%></td>
			</tr>
<% 		}%>
		</tbody>
	<tbody>
	</table>
</div>