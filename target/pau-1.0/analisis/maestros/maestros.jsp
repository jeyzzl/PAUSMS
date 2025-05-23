<%@page import="aca.est.spring.EstCcosto"%>
<%@page import="aca.est.spring.EstMaestro"%>
<%@page import="aca.plan.spring.MapaCurso"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="AlumPlanU" scope="page" class="aca.alumno.PlanUtil"/>

<%
	java.text.DecimalFormat getFormato = new java.text.DecimalFormat("##0.0000;-##0.0000");
	java.text.DecimalFormat getFormato2 = new java.text.DecimalFormat("###,##0.00;-###,##0.00");

	String facultad			= request.getParameter("Facultad")==null?"1":request.getParameter("Facultad");
	String estado			= request.getParameter("Estado")==null?"1":request.getParameter("Estado");	
		
	/* LISTA DE MATERIAS DE LOS MAESTROS*/
	List<EstCcosto> lisMaestros 				= (List<EstCcosto>) request.getAttribute("lisMaestros");
	
	HashMap<String,String> mapaMaestros 		= (HashMap<String,String>) request.getAttribute("mapaMaestros");
	HashMap<String,String> mapaDeptos 			= (HashMap<String,String>) request.getAttribute("mapaDeptos");
	HashMap<String,MapaCurso> mapaCursos 		= (HashMap<String,MapaCurso>) request.getAttribute("mapaCursos");	
	HashMap<String,EstCcosto> mapaEstCcosto 	= (HashMap<String,EstCcosto>) request.getAttribute("mapaEstCcosto");
	HashMap<String,EstMaestro> mapaEstMaestro 	= (HashMap<String,EstMaestro>) request.getAttribute("mapaEstMaestro");
	
%>
<div class="container-fluid">
	<h2>Distribución/materias/maestros</h2>
	<form name="frmMaestro" action="maestros" method="post">
	<div class="alert alert-info">		
		Facultad:&nbsp;	 
		<select name="Facultad" onchange="document.frmMaestro.submit();">
			<option value="0" <%=facultad.equals("0")?"selected":""%>>OTRO</option>
			<option value="1" <%=facultad.equals("1")?"selected":""%>>FACSA</option>
			<option value="2" <%=facultad.equals("2")?"selected":""%>>ESTOMATOLOGÍA</option>
			<option value="3" <%=facultad.equals("3")?"selected":""%>>FIT</option>
			<option value="4" <%=facultad.equals("4")?"selected":""%>>EDUCACION</option>
			<option value="5" <%=facultad.equals("5")?"selected":""%>>FACEJ</option>
			<option value="6" <%=facultad.equals("6")?"selected":""%>>TEOLOGIA</option>
			<option value="7" <%=facultad.equals("7")?"selected":""%>>PREPA</option>
			<option value="8" <%=facultad.equals("8")?"selected":""%>>MUSICA</option>
			<option value="9" <%=facultad.equals("9")?"selected":""%>>UM-VIRTUAL</option>
			<option value="10" <%=facultad.equals("10")?"selected":""%>>PSICOLOGIA</option>
			<option value="11" <%=facultad.equals("11")?"selected":""%>>ARTE</option>			
		</select>
		&nbsp;&nbsp;
		Estado:&nbsp;	 
		<select name="Estado" onchange="document.frmMaestro.submit();">
			<option value="1" <%=estado.equals("1")?"selected":""%>>ACTIVO</option>
			<option value="0" <%=estado.equals("0")?"selected":""%>>INACTIVO</option>			
		</select>
		&nbsp;&nbsp;
		<input type="text" class="input-medium search-query" placeholder="Buscar" id="buscar">
		&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="actualizar?Facultad=<%=facultad%>&Estado=<%=estado%>" class="btn btn-primary">Recalcular</a>
	</div>
	</form>
	<table class="table table-bordered">
	<thead class="table-info">
	<tr>
		<th width="5%" class="center">Op.</th>		
		<th width="5%" class="center">#</th>
		<th width="5%" class="center"><spring:message code="aca.Nomina"/></th>
		<th width="15%" class="center"><spring:message code="aca.Maestro"/></th>
		<th width="5%" class="center">Acta</th>
		<th width="5%" class="center">Curso</th>
		<th width="15%" class="left">Nombre Curso</th>
		<th width="5%" class="center">Departamento</th>
		<th width="15%" class="left">Nombre Dep.</th>
		<th width="5%" class="right">Hrs.Mat.</th>
		<th width="5%" class="right">Tot.Hrs.</th>				
		<th width="5%" class="right">Alum.</th>
		<th width="5%" class="right">Tot.Al.</th>
		<th width="5%" class="right">% Mat.</th>			
		<th width="5%" class="right">% Tot.</th>		
		<th width="5%" class="right">$Recursos</th>
	</tr>
	</thead>
<%		
	String alert = "class='alert alert-success'";
	int row = 0;
	String codigo = "0";
	double total = 0;
	for(EstCcosto maestro : lisMaestros){
		row++;
		if (!maestro.getCodigoPersonal().equals(codigo)){
			if (alert.equals("class='alert alert-success'")) alert = ""; else alert = "class='alert alert-success'";
			codigo = maestro.getCodigoPersonal();
		}
		
		String nombreMaestro = "-";
		if (mapaMaestros.containsKey(maestro.getCodigoPersonal())){
			nombreMaestro = mapaMaestros.get(maestro.getCodigoPersonal());
		}		
		
		String nombreCurso 	= "-";		
		if (mapaCursos.containsKey(maestro.getCursoId())){
			nombreCurso = mapaCursos.get(maestro.getCursoId()).getNombreCurso();	
		}
		
		String nombreDepto = "-";
		if (mapaDeptos.containsKey(maestro.getcCostoId())){
			nombreDepto = mapaDeptos.get(maestro.getcCostoId());
		}
		
		double porGlobal 	= 0;
		double importe 		= 0; 
		if (mapaEstCcosto.containsKey(maestro.getId())){
			porGlobal 	= Double.valueOf(mapaEstCcosto.get(maestro.getId()).getPortotal());
			importe 	= Double.valueOf(mapaEstCcosto.get(maestro.getId()).getImporte());
		}
		total += importe;
		String totHoras = "0";
		if (mapaEstMaestro.containsKey(maestro.getCodigoPersonal())){
			totHoras = mapaEstMaestro.get(maestro.getCodigoPersonal()).getHoras();
		}
%>
	<tr <%=alert%> >
		<td  class="center">
			<a href="editar?Id=<%=maestro.getId()%>&Facultad=<%=facultad%>&Estado=<%=estado%>"><i class="fas fa-edit"></i></a>&nbsp;&nbsp;
			<a href="estado?Id=<%=maestro.getId()%>&Estado=1"><i class="fas fa-check"></i></a>&nbsp;&nbsp;
			<a href="estado?Id=<%=maestro.getId()%>&Estado=0"><i class="fas fa-trash-alt"></i></a>
		</td>		
		<td  class="center"><%=row%></td>		
		<td class="left"><%=maestro.getCodigoPersonal()%></td>
		<td class="left"><%=nombreMaestro%></td>
		<td class="center"><%=maestro.getCursoCargaId()%></td>
		<td class="center"><%=maestro.getCursoId() %></td>
		<td class="left"><%=nombreCurso%></td>
		<td class="center"><%=maestro.getcCostoId() %></td>
		<td class="left"><%=nombreDepto%></td>		
		<td class="right"><%=maestro.getHoras()%></td>
		<td class="right"><%=totHoras%></td>		
		<td class="right"><%=maestro.getAlumnos() %></td>
		<td class="right"><%=maestro.getTotal() %></td>
		<td class="right"><%=maestro.getPorcentaje()%></td>		
		<td class="right"><%=getFormato.format(porGlobal)%></td>
		<td class="right"><%=getFormato2.format(importe)%></td>
	</tr>	
<%	
	}
%>
	<tr>
		<th class="right" colspan="15">TOTAL:</th>
		<th class="right"><%=getFormato2.format(total)%></th>
	</tr>	
	</table>
</div>
<script src="../../js/jquery-1.9.1.min.js"></script>
<script src="../../js/search.js"></script>
<script>	
	jQuery('#buscar').focus().search({table:jQuery("#table")});
</script>	