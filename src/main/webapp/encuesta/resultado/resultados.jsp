<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.encuesta.spring.EncPeriodo"%>
<%@ page import= "aca.encuesta.spring.EncPeriodoRes"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%	
	String periodoId = (String)request.getAttribute("periodoId");

	List<EncPeriodo> lisPeriodo 	= (List<EncPeriodo>)request.getAttribute("lisPeriodo");	
	List<EncPeriodoRes> resultados 	= (List<EncPeriodoRes>)request.getAttribute("resultados");
	
	HashMap<String, String> mapaDatos 			= (HashMap<String, String>)request.getAttribute("mapaDatos");
	HashMap<String, String> mapCarreraPlan 		= (HashMap<String, String>)request.getAttribute("mapCarreraPlan");
	HashMap<String, CatCarrera> mapaCarreras 	= (HashMap<String, CatCarrera>)request.getAttribute("mapaCarreras");
	HashMap<String, CatFacultad> mapaFacultad 	= (HashMap<String, CatFacultad>)request.getAttribute("mapaFacultad");
	
	int cont = 1;
%>
<html>
<div class="container-fluid">
	<h2>Resultados</h2>
	<form id="form" name="form" action="resultados" method="POST">
		<div class="d-flex alert alert-info">
			Periodo:&nbsp;&nbsp; 
			<select class="form-select" name="PeriodoId" onchange="cambiarPeriodo();">
<%			for (EncPeriodo periodo : lisPeriodo){%>
			  	<option <%=periodoId.equals(periodo.getPeriodoId())?"selected":""%> value="<%=periodo.getPeriodoId()%>"><%=periodo.getPeriodoNombre()%></option>
<%  		}%>
			</select>
		</div>
	</form>
	<table class="table table-bordered border-dark">
		<thead>
		<tr align="center" class="table-primary"> 
	    	<th colspan="5">Datos</th>
	    	<th colspan="5">Autofinanciamiento</th>
	    	<th colspan="2">Planes de regresar</th>
	    	<th colspan="2">Residencia</th>
	    	<th colspan="2">Obs. Financiero</th>
	    	<th colspan="2">Obs. Académico</th>
	    	<th colspan="3">Obs. Personal</th>
	    	<th colspan="8">Planes de vacaciones</th>
	  	</tr>
		<tr class="table-light"> 
	    	<th>#</th>
	    	<th>Matrícula</th>
	    	<th>Nombre</th>
	    	<th>Fac.</th>
	    	<th>Carrera</th>
	    	<th>Autofin.</th>
	    	<th>Por.</th>
	    	<th>Col.</th>
	    	<th>UM.</th>
	    	<th>Otro.</th>
	    	<th>Regr.</th>
	    	<th>Prac.</th>
	    	<th>Int.</th>
	    	<th>Ext.</th>
	    	<th>Sal.Pen.</th>
	    	<th>Fin.Prox.Sem.</th>
	    	<th>Mat.Pen.</th>
	    	<th>Doc.Pen.</th>
	    	<th>Sal.</th>
	    	<th>Adap.</th>
	    	<th>Fam.</th>
	    	<th>Plan est.</th>
	    	<th>Otro</th>
	    	<th>Plan tra.</th>
	    	<th>Plan col.</th>
	    	<th>Otro</th>
	    	<th>Desc.</th>
	    	<th>Nin.</th>
	    	<th>Ori.</th>
	  	</tr>
	  </thead>
<%	for (EncPeriodoRes resultado : resultados){
	String externo = "Padres";
	if(resultado.getExterno().equals("F")){
		externo = "Familiares";
	}else if(resultado.getExterno().equals("E")){
		externo = "Empleado";
	}else if(resultado.getExterno().equals("C")){
		externo = "Cónyuge";
	}else if(resultado.getExterno().equals("S")){
		externo = "Sólo";
	}
	
	String nombre = "";
	if(mapaDatos.containsKey(resultado.getCodigoPersonal())){
		nombre = mapaDatos.get(resultado.getCodigoPersonal());
	}

	String carreraId = "";
	if(mapCarreraPlan.containsKey(resultado.getPlanId())){
		carreraId = mapCarreraPlan.get(resultado.getPlanId());
	}

	CatCarrera carrera = new CatCarrera();
	if(mapaCarreras.containsKey(carreraId)){
		carrera = mapaCarreras.get(carreraId);
	}
	
	CatFacultad facultad = new CatFacultad();
	if(mapaFacultad.containsKey(carrera.getFacultadId())){
		facultad = mapaFacultad.get(carrera.getFacultadId());
	}
%>
	<tbody>
	  	<tr> 
			<td align="left"><%=cont++%></td>
			<td align="left"><%=resultado.getCodigoPersonal()%></td>
			<td align="left"><%=nombre%></td>
			<td align="left"><%=facultad.getNombreCorto()%></td>
			<td align="left"><%=carrera.getNombreCarrera()%></td>
	    	<td align="left"><%=resultado.getFinAlumno().equals("S")?"Si":"No"%></td>
	    	<td align="left"><%=resultado.getFinPor()%></td>
	    	<td align="left"><%=resultado.getFinColpor().equals("S")?"Si":"No"%></td>
	    	<td align="left"><%=resultado.getFinTrabajo().equals("S")?"Si":"No"%></td>
	    	<td align="left"><%=resultado.getFinOtro()%></td>
	    	<td align="left"><%=resultado.getRegresar().equals("S")?"Si":resultado.getRegresar().equals("N")?"No":"Inseguro"%></td>
	    	<td align="left"><%=resultado.getPracticas().equals("S")?"Si":"No"%></td>
	    	<td align="left"><%=resultado.getInterno()%></td>
	    	<td align="left"><%=externo%></td>
	    	<td align="left"><%=resultado.getObsSaldo().equals("S")?"Si":"No"%></td>
	    	<td align="left"><%=resultado.getObsFin().equals("S")?"Si":"No"%></td>
	    	<td align="left"><%=resultado.getObsMat().equals("S")?"Si":"No"%></td>
	    	<td align="left"><%=resultado.getObsDoc().equals("S")?"Si":"No"%></td>
	    	<td align="left"><%=resultado.getObsSalud().equals("S")?"Si":"No"%></td>
	    	<td align="left"><%=resultado.getObsAdaptacion().equals("S")?"Si":"No"%></td>
	    	<td align="left"><%=resultado.getObsFamiliar().equals("S")?"Si":"No"%></td>
	    	<td align="left"><%=resultado.getPlanEstudiar().equals("S")?"Si":"No"%></td>
	    	<td align="left"><%=resultado.getPlanOtroEst()%></td>
	    	<td align="left"><%=resultado.getPlanTrabajo().equals("S")?"Si":"No"%></td>
	    	<td align="left"><%=resultado.getPlanColportar().equals("S")?"Si":"No"%></td>
	    	<td align="left"><%=resultado.getPlanOtroTrabajo()%></td>
	    	<td align="left"><%=resultado.getPlanDescansar().equals("S")?"Si":"No"%></td>
	    	<td align="left"><%=resultado.getPlanNinguno().equals("S")?"Si":"No"%></td>
	    	<td align="left"><%=resultado.getOrientacion().equals("S")?"Si":"No"%></td>
	  	</tr>
  	</tbody>
<%
	}
%>
	</table>
</div>
<script type="text/javascript">
	function cambiarPeriodo(){
		document.getElementById("form").submit(); 
	}
</script>
</body>
</html> 