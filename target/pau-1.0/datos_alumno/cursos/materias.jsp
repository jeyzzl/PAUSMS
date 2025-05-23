<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.alumno.spring.AlumAcademico"%>
<%@page import="aca.vista.spring.AlumnoCurso"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.carga.spring.CargaBloque"%>
<%@page import="aca.plan.spring.MapaCurso"%>
<%@page import="aca.catalogo.spring.CatTipoCurso"%>
<%@page import="aca.catalogo.spring.CatModalidad"%>
<%@page import="aca.catalogo.spring.CatTipoCal"%>
<%@page import="aca.vista.spring.AlumnoNota"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%	
	java.text.DecimalFormat getFormato	= new java.text.DecimalFormat("###,##0.00;-###,##0.00");

	String codigoPersonal 				= (String) session.getAttribute("codigoPersonal");
	String codigoAlumno					= (String) session.getAttribute("codigoAlumno");
	String cargaId						= (String) request.getAttribute("cargaId");
	String bloqueId						= (String) request.getAttribute("bloqueId");
	String planId						= (String) request.getAttribute("planId");
	String carreraId					= (String) request.getAttribute("carreraId");
	String carreraNombre				= (String) request.getAttribute("carreraNombre");
	double promedio						= (double) request.getAttribute("promedio");
	
	AlumPersonal personal				= (AlumPersonal) request.getAttribute("personal");
	AlumAcademico academico				= (AlumAcademico) request.getAttribute("academico");	
	
	List<Carga> lisCargas		 		= (List<Carga>)request.getAttribute("lisCargas");
	List<CargaBloque> lisBloques		= (List<CargaBloque>)request.getAttribute("lisBloques");
	List<AlumnoCurso> lisCursos			= (List<AlumnoCurso>)request.getAttribute("lisCursos");
	
	HashMap<String, MapaCurso> mapaCursos  			= (HashMap<String, MapaCurso>)request.getAttribute("mapaCursos");
	HashMap<String, String> mapaCursosOrigen 		= (HashMap<String, String>)request.getAttribute("mapaCursosOrigen");
	HashMap<String, String> mapaMaestros 			= (HashMap<String, String>)request.getAttribute("mapaMaestros");
	HashMap<String, CatTipoCurso> mapaTipoCurso		= (HashMap<String, CatTipoCurso>)request.getAttribute("mapaTipoCurso");	
	HashMap<String, CatModalidad> mapaModalidades	= (HashMap<String, CatModalidad>)request.getAttribute("mapaModalidades");
	HashMap<String, CatTipoCal> mapaTipoCal 		= (HashMap<String, CatTipoCal>)request.getAttribute("mapaTipoCal");
	HashMap<String, AlumnoNota> mapaNotas 			= (HashMap<String, AlumnoNota>)request.getAttribute("mapaNotas");
%>
<html>
<body>	
<div class="container-fluid">
	<h2> Student Subjects <small class="text-muted fs-5">( <%=codigoAlumno%> - <%=personal.getNombre()%> <%=personal.getApellidoPaterno()%> <%=personal.getApellidoMaterno()%> )</small></h2>
	<form  name="frmmaterias" action="materias">	
	<div class="alert alert-info d-flex align-items-center">
		<spring:message code="aca.Carga"/>:&nbsp;
		<select name="CargaId"class="form-select" style="width:350px" onChange="javascritp:frmmaterias.submit()">
<%		 	
	for( Carga carga : lisCargas){		
		if (carga.getCargaId().equals(cargaId)){			
			out.print(" <option value='"+carga.getCargaId()+"' Selected> ["+carga.getCargaId()+"] - "+carga.getNombreCarga()+"</option>");
		}else{
			out.print(" <option value='"+carga.getCargaId()+"'>  ["+carga.getCargaId()+"] - "+carga.getNombreCarga()+"</option>");
		}
	}	
%>
		</select>&nbsp;&nbsp;&nbsp;
		<spring:message code="aca.Bloque"/>:&nbsp;		
		<select name="BloqueId" class="form-select" style="width:250px" onChange="javascritp:frmmaterias.submit()">
<%
	for (CargaBloque bloque: lisBloques){		
%>
			<option value="<%=bloque.getBloqueId()%>" <%=bloqueId.equals(bloque.getBloqueId())?"selected":""%>><%=bloque.getBloqueId()%>-<%=bloque.getNombreBloque()%></option>
<%		
	}	
%>			
		</select>
		
	</div>
	</form>
	<div class="alert alert-info">
		<spring:message code="aca.Plan" />: [<b><%=planId%></b>]&nbsp;&nbsp;
		<spring:message code="aca.Carrera" />: [<b><%=carreraNombre%></b>]&nbsp;&nbsp;
		<spring:message code="aca.Residencia" />: [<b><%if (academico.getResidenciaId().equals("E"))out.print("Externo"); else out.print("Interno"); %></b>]&nbsp;&nbsp;
		<spring:message code="aca.FechaNac" />: [<b><%=personal.getFNacimiento()%></b>]&nbsp;&nbsp;
		<spring:message code="aca.Nacionalidad" />: [<b><%=personal.getNacionalidad()%></b>]&nbsp;&nbsp;
		<spring:message code="aca.Modalidad"/>: [<b><%=academico.getModalidad()%></b>]
	</div>	
	<table class="table table-sm">
		<tr>
			<th>&nbsp;</th>
		    <th><spring:message code="aca.Curso" /></th>
		    <th align="center"><spring:message code="aca.Creditos"/></th>
		    <th>&nbsp;</th>
		    <th><spring:message code="aca.Estado"/></th>
		</tr>
<%
    float totCreditos	= 0;           
    int row				= 0;
	for(AlumnoCurso alumnoCurso : lisCursos){
				
		row++;
		String tipoNombre		= "-";
		String tipoCurso = "0";
		if (mapaCursos.containsKey(alumnoCurso.getCursoId())){
			tipoCurso = mapaCursos.get(alumnoCurso.getCursoId()).getTipoCursoId();
			if (mapaTipoCurso.containsKey(tipoCurso)){
				tipoNombre = mapaTipoCurso.get(tipoCurso).getNombreTipoCurso();
			}
		}
		String nombreMaestro = "-";
		if (mapaMaestros.containsKey(alumnoCurso.getMaestro())){
			nombreMaestro = mapaMaestros.get(alumnoCurso.getMaestro());
		}
		
		String notaMateria = alumnoCurso.getNota();
		if (notaMateria.equals("0")){
			if (mapaNotas.containsKey(alumnoCurso.getCodigoPersonal()+alumnoCurso.getCursoCargaId())){
				notaMateria = mapaNotas.get(alumnoCurso.getCodigoPersonal()+alumnoCurso.getCursoCargaId()).getNota();
			}			
		}
		
		// En caso de ser materia optativa		
		String nombreOptativa = "-";
		if ( alumnoCurso.getOptativa().length()>5){
			nombreOptativa = "("+alumnoCurso.getOptativa()+")";
		}
		
		String cursoOrigen 			= "-";
		String cursoOrigenNombre 	= "-";
		if (mapaCursosOrigen.containsKey(alumnoCurso.getCursoCargaId())){
			cursoOrigen = mapaCursosOrigen.get(alumnoCurso.getCursoCargaId());
			if (!alumnoCurso.getCursoId().equals(cursoOrigen)){
				if (mapaCursos.containsKey(cursoOrigen)){
					cursoOrigenNombre = "("+ mapaCursos.get(cursoOrigen).getNombreCurso()+")";
				}						
			}
		}
		
		String modalidadNombre = "-";
		if (mapaModalidades.containsKey(alumnoCurso.getModalidadId()) ){
			modalidadNombre = mapaModalidades.get(alumnoCurso.getModalidadId()).getNombreModalidad();
		}
		
		String fechaIni 	= "";
		String fechaFin		= "";		
%>
		<tr  class="ayuda mensaje 			
  			Tipo: <%=tipoNombre%>
  			<br>
  			Materia Origen: <%=alumnoCurso.getNombreCurso2()%>
  			<br> 
  			Maestro: <%=alumnoCurso.getMaestro()%>" 
  			style='cursor:pointer;' onClick="document.location.href='detallecal?CursoCargaId=<%=alumnoCurso.getCursoCargaId()%>'">
			<td width="5%" align="center" valign="top">
		  		<span class="badge bg-info"><%=row%></span>
		  	</td>
		 	<td width="55%">
		 		<b><%=alumnoCurso.getNombreCurso()%> <%=nombreOptativa.equals("-")?"":nombreOptativa%></b> &nbsp; <span class="badge bg-info" title="<%=cursoOrigenNombre%>"><%= alumnoCurso.getCursoCargaId() %></span>
				<li><%= nombreMaestro %></li>
				<li><%=alumnoCurso.getCiclo()%>o. Sem de <%=alumnoCurso.getPlanId()%>
				- [<%=modalidadNombre%>] - <strong>Blq[<%=alumnoCurso.getBloqueId()%>]</strong>
				- <strong>Gpo[<%= alumnoCurso.getGrupo() %>]</strong>
				</li>
		  	</td>
		  	<td  width="10%"  nowrap>[ <%=alumnoCurso.getCreditos()%> ]</td>
		  	<td align="center" width="20%" nowrap>
		    Nota: [ 
<%		
		String tipoCalNombre = "-";
		if (mapaTipoCal.containsKey(alumnoCurso.getTipoCalId())){
			tipoCalNombre = mapaTipoCal.get(alumnoCurso.getTipoCalId()).getNombreTipoCal();
		}
		
		if (tipoCurso.equals("3")||tipoCurso.equals("4")){				
		  	out.print(tipoCalNombre);
		}else 
			out.print(notaMateria);
%> ]
<%		if (!alumnoCurso.getNotaExtra().equals("0") && alumnoCurso.getNotaExtra()!=null){
			out.println("Extra:["+alumnoCurso.getNotaExtra()+"]");
		}
%>
			</td>								  
			<td width="10%" >[<%=tipoCalNombre%>]</td>
		
		</tr>							  
<%
		if (!alumnoCurso.getTipoCalId().equals("3"))
			totCreditos += Float.parseFloat(alumnoCurso.getCreditos());
	}
%>		
	</table>
	<div class="alert alert-info right">
		<strong><spring:message code="analisis.avance.TotalCreditos"/> <spring:message code="aca.Inscritos"/>: [ <%=totCreditos %> ]</strong>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<strong><spring:message code="aca.Promedio"/>: [ <%=getFormato.format(promedio) %> ]</strong>
	</div>	
</div>