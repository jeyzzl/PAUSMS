<%@ page import="java.text.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>

<%@ page import="aca.catalogo.spring.CatTipoCal" %>
<%@ page import="aca.alumno.spring.AlumPlan" %>
<%@ page import="aca.vista.spring.AlumnoCurso" %>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	function cambiaVer(){
		if(document.forma.ver.value=="N")document.forma.ver.value="C";
		else document.forma.ver.value='N';
		document.forma.submit();
	}
	function recarga(){
		document.forma.submit();
	}
</script>
<link rel="stylesheet" href="../../js/tablesorter/themes/blue/style.css"/>
<%
	DecimalFormat getFormato	= new DecimalFormat("###,##0.00;(###,##0.00)");
	String matricula			= (String) session.getAttribute("codigoAlumno");
	String estado 				= "";
	String sBgcolor				= "";
	String alumnoNombre			= (String) request.getAttribute("alumnoNombre");
	String planActual			= (String) request.getAttribute("planActual");
	List<AlumPlan> lisPlanes				= (List<AlumPlan>) request.getAttribute("lisPlanes");
	List<AlumnoCurso> lisCursos		 		= (List<AlumnoCurso>) request.getAttribute("lisCursos");
	HashMap<String,String> mapaMaestros 	= (HashMap<String,String>) request.getAttribute("mapaMaestros");
	HashMap<String,CatTipoCal> mapaTipos	= (HashMap<String,CatTipoCal>) request.getAttribute("mapaTipos");
	HashMap<String,String> mapaPromedios 	= (HashMap<String,String>) request.getAttribute("mapaPromedios");
%>
<div class="container-fluid">
	<h2>Corrección de Calificaciones<small class="text-muted fs-4">( <%=matricula%> - <%=alumnoNombre%> - <%=planActual%> )</small></h2>
	<form name="forma" action="notas" method='post'>
	<div class="alert alert-info d-flex align-items-center">	
		Planes: &nbsp;
    	<select name="PlanId" onchange='javascript:recarga()'>
<%
	for(AlumPlan plan : lisPlanes){%>
  			<option value='<%=plan.getPlanId()%>' <%if(planActual.equals(plan.getPlanId()))out.print("Selected");%> ><%=plan.getPlanId()%></option>
<%	}%>
  		</select> 
  		
  		&nbsp;&nbsp;
		Filtro:&nbsp;<input id="buscar" type="text" class="form-control search-query" style="width:200px" placeholder="Buscar...">   		
	</div>
	</form>
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
	<tr>
	
    	<th width="3%"><spring:message code="aca.Numero"/></th>
		<th width="5%"><spring:message code="aca.Acta"/></th>
  	  	<th width="6%">Estado</th>
  	  	<th width="4%">Ciclo</th>
	  	<th width="28%"><spring:message code="aca.Materia"/></th>
	  	<th width="3%">Crd.</th>
	  	<th width="27%"><spring:message code="aca.Maestro"/></th>
	  	<th width="4%" class="right"><spring:message code="aca.Nota"/></th>
	  	<th width="4%"><spring:message code="aca.Tipo"/></th>
	  	<th width="7%">F.Nota</th>
	  	<th width="3%" class="right">Ex.</th>
	  	<th width="7%">F.Extra</th>
	  	<th width="7%">Correc.</th>
	</tr>
	</thead>
	
	
<%	for (int i=0; i < lisCursos.size(); i++){
		AlumnoCurso alumnoCurso = (AlumnoCurso) lisCursos.get(i);
		if (alumnoCurso.getEstado().equals("1")) estado = "Creada";
		else if (alumnoCurso.getEstado().equals("2")) estado = "Ordinario";
		else if (alumnoCurso.getEstado().equals("3")) estado = "Extra";
		else if (alumnoCurso.getEstado().equals("4")) estado = "Cerrada";
		else if (alumnoCurso.getEstado().equals("5")) estado = "Registro";
		else estado = "error";
		
		String maestroNombre = "-";
		if (mapaMaestros.containsKey(alumnoCurso.getMaestro()) ){
			maestroNombre = mapaMaestros.get(alumnoCurso.getMaestro());
		}
		
		String tipoNombre = "-";
		if (mapaTipos.containsKey(alumnoCurso.getTipoCalId()) ){
			tipoNombre = mapaTipos.get(alumnoCurso.getTipoCalId()).getNombreTipoCal();
		}		
		
		String textoCorreccion 	= "<span class='badge bg-dark'>NO</span>";
		String textoNota		= "<span class='badge bg-dark'>"+alumnoCurso.getNota()+"</span>";
		if (alumnoCurso.getCorreccion().equals("S")){			
			String notaAnterior = "0";
			if (mapaPromedios.containsKey(alumnoCurso.getCursoCargaId())){
				notaAnterior = mapaPromedios.get(alumnoCurso.getCursoCargaId());
			}
			textoCorreccion = "<span class='badge bg-success'>SI ["+notaAnterior+"]</span>";
			textoNota		= "<span class='badge bg-success'>"+alumnoCurso.getNota()+"</span>";
		}	
%>
	<tr class="tr2" <%=sBgcolor%>>
    	<td align="center"><strong><em><%=i+1%></em></strong></td>
	  	<td><%=alumnoCurso.getCursoCargaId()%></td>
	  	<td><%=estado%></td>
	  	<td><%=alumnoCurso.getCiclo()%></td>
	  	<td title="Cambiar Nota">
<%		if (!alumnoCurso.getTipoCalId().equals("3") && !alumnoCurso.getEstado().equals("2")&& !alumnoCurso.getEstado().equals("3")){%>
			<a href="editar?CursoCargaId=<%=alumnoCurso.getCursoCargaId()%>&Materia=<%=alumnoCurso.getNombreCurso()%>&PlanId=<%=planActual%>">
			<%=alumnoCurso.getNombreCurso()%>
			</a>
		<%}else{%>
				<%=alumnoCurso.getNombreCurso()%>
		<%}%>
		</td>
	  	<td><%=alumnoCurso.getCreditos()%></td>
	  	<td><%=maestroNombre%></td>
	  	<td class="right"><strong><%=textoNota%></strong></td>
	  	<td><%=tipoNombre%></td>
	  	<td><%=alumnoCurso.getFEvaluacion()%></td>
	  	<td class="right">
<%		if (alumnoCurso.getNotaExtra().equals("0")){
			out.println("-");
		}else{
			out.println(alumnoCurso.getNotaExtra());
		}
%>
	  	</td>
	  	<td class="right">
<%		if (alumnoCurso.getNotaExtra().equals("0")){
			out.println("");
		}else{
			out.println(alumnoCurso.getFExtra());
		}
%>
	  	</td>
	  	<td align="center"><strong><%=textoCorreccion%></strong></td>
	</tr>  
<%	} // finaliza for de cursos del alumno %>
	</table> 
</div>
<script src="../../js/search.js"></script>
<script>
	$('#buscar').search();
</script>
<script type="text/javascript" src="../../js/search.js"></script>
<script src="../../js/tablesorter/jquery.tablesorter.js"></script>
<script type="text/javascript">
	jQuery('#buscar').focus().search({table:jQuery("#tableGrupo")});
	jQuery('#tableGrupo').tablesorter();
</script>
