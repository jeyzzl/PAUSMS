<%@ page import= "java.util.List"%>
<%@ page import= "aca.plan.spring.MapaPlan"%>
<%@ page import= "aca.plan.spring.MapaCurso"%>
<%@ page import= "aca.catalogo.spring.CatTipoCal"%>
<%@ page import= "aca.catalogo.spring.CatGradePoint"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" href="../../js/chosen/chosen.css" />
<script type="text/javascript" src ="../../validacion.js"> </script>
<script type="text/javascript" src="../../js/chosen/chosen.jquery.js"></script>
<%
	String codigoPersonal	= (String)session.getAttribute("codigoPersonal");
	String codigoAlumno		= (String)session.getAttribute("codigoAlumno");
	String planId			= (String)request.getAttribute("planId");	
	String nombreAlumno	 	= (String)request.getAttribute("nombreAlumno");		
	
	int cicloTem			= 0;
	int ciclo				= 0;
	int cont				= 0;
	int folio				= 0;	
	
	List<MapaPlan> lisPlanes			= (List<MapaPlan>)request.getAttribute("lisPlanes");
	List<MapaCurso> lisMaterias			= (List<MapaCurso>)request.getAttribute("lisMaterias");
	List<CatTipoCal> lisTipos			= (List<CatTipoCal>)request.getAttribute("lisTipos");	
	List<CatGradePoint> lisGrades	 	= (List<CatGradePoint>)request.getAttribute("lisGrades");
	
%>
<script>
	function grabar(){
		var error = true;
		var recorrido = 0;
<%		for(int i = 0 ; i < lisMaterias.size(); i++){ %>	
			if(recorrido < <%=lisMaterias.size()%>){
				if(frmimportcalif.check<%=i%>.checked){
					error = false;
					if(frmimportcalif.calificacion<%=i%>.value ==""){
						error = true;
						recorrido = <%=lisMaterias.size() + 1%>
						alert("The grade can't be empty");
						frmimportcalif.calificacion<%=i%>.focus();
						
					}else if (frmimportcalif.fecha<%=i%>.value == ""){
						error = true;
						recorrido = <%=lisMaterias.size() + 1%>
						alert("The date can't be empty");						
						frmimportcalif.fecha<%=i%>.focus();				
					}				
				}
			}	
<%		} %>		
		if(!error){			
			frmimportcalif.submit();									
		
		}
	}
</script>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
	<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
</head>
<body>
<div class="container-fluid">
	<h2>Imported Subjects <small class="text-muted fs-5"> (<b><%=codigoAlumno%></b> <%=nombreAlumno%>)</small></h2>
	<form name="frmimportplan" action="accion" method="post">
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="cursos"><strong><i class="fas fa-arrow-left"></i></strong></a>&nbsp;&nbsp;&nbsp;
		Add Subjects from:&nbsp;
    	<select name="PlanId" id="PlanId" class="form-select chosen" onChange="document.frmimportplan.submit()" style="width:550px;">
<% 	

	for(MapaPlan plan : lisPlanes){ %>	
			<option value="<%=plan.getPlanId()%>" <%=plan.getPlanId().equals(planId)?"selected":""%>><%=plan.getPlanId()%>-<%=plan.getNombrePlan()%></option>
<%	} 
%>	
    	</select>       
    </div>  
	</form>  	
	<form name="frmimportcalif" action="grabar" method="post">
	<input name="PlanId" type="hidden" value="<%=planId%>">
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
<% 
	for(int i=0; i < lisMaterias.size(); i++){
		MapaCurso curso = (MapaCurso) lisMaterias.get(i);
		ciclo	= Integer.parseInt(curso.getCiclo());
		if(ciclo != cicloTem){
			cicloTem = ciclo;
%> 	  		  
	</thead>	 
  	<tr class="alert alert-success"><th colspan="12"><strong>Semester <%=ciclo%></strong></th>
	<thead class="table-info">	 
  	<tr>
    	<th width="4%" height="16"><span class="Estilo5">Add</span></th>
	    <th width="21%"><span class="Estilo5"><spring:message code="aca.Materia"/></span></th>
	    <th width="5%"><span class="Estilo5 ">Mark</span></th>
	    <th width="12%"><span class="Estilo5">Date </span></th>
	    <th width="7%"><span class="Estilo5">Grade</span></th>
	    <th width="5"><span class="Estilo5">Cycle</span></th>
	    <th width="5%"><span class="Estilo5 ">Remedial M. </span></th>
	    <th width="10%"><span class="Estilo5 ">Remedial Date</span></th>
	    <th width="20%"><span class="Estilo5 ">Observations</span></th>
	    <th width="6"><span class="Estilo5 ">Status</span></th>	    	    
	    <th width="5%"><span class="Estilo5">Elective</span></th>
	</tr>		
	</thead>	 
<%	} %>	
	<tr>
		<input name="titulo<%=i%>" type="hidden" id="titulo<%=i%>" value="N">
		<input name="convalidada<%=i%>" type="hidden" id="convalidada<%=i%>" value="N">
    	<td align="center"><input name="check<%=i%>" type="checkbox"  value="SI"></td>
    	<td><%=curso.getNombreCurso()%><input name="cursoId<%=i%>" type="hidden" id="cursoId<%=i%>" value="<%=curso.getCursoId()%>">
    	<%if(curso.getTipoCursoId().equals("2")||curso.getTipoCursoId().equals("7")){%>
    		<input name="tipoCursoId<%=i%>" type="text" id="tipoCursoId<%=i%>" value="<%=curso.getNombreCurso() %>"> 
    	<%} %>
    	</td>    
    	<td align="center"><input class="input input-mini"  name="calificacion<%=i%>" type="text" class="text"  size="3" maxlength="4" onKeypress="if (window.event.keyCode < 48 || window.event.keyCode > 57) window.event.returnValue = false;"></td>
    	<td align="center"><input class="input input-small" name="fecha<%=i%>" type="text" id="fecha<%=i%>" data-date-format="dd/mm/yyyy" size="9" maxlength="10" onChange="validaFecha(this.value); this.value=borrar"> DD/MM/YYYY</td>
    	<td align="center">
			<select class="form-select" name="grade<%=i%>" id="grade<%=i%>">
				<option value="N" selected="selected">NA</option>
	<%	for(CatGradePoint grade : lisGrades){%>		
				<option value="<%=grade.getFin()%>"><%=grade.getGpNombre()%></option>
	<%	} %>	
			</select>
		</td>
		<td align="center"><input class="input input-mini"  name="ciclo<%=i%>" type="text" class="text"  size="3" maxlength="4" onKeypress="if (window.event.keyCode < 48 || window.event.keyCode > 57) window.event.returnValue = false;"></td>
    	<td align="center"><input class="input input-mini" name="califExtra<%=i%>" type="text" class="text"  size="3" maxlength="3" onKeypress="if (window.event.keyCode < 48 || window.event.keyCode > 57) window.event.returnValue = false;"> </td>
    	<td align="center"><input class="input input-small" name="fechaExtra<%=i%>" type="text" class="text"  size="9" maxlength="10" onChange="validaFecha(this.value); this.value=borrar"></td>
    	<td align="left"><input name="observaciones<%=i%>" type="text" class="text"  size="25" maxlength="200"></td>
    	<td align="center">
    		<select class="form-select" name="condicion<%=i%>" id="condicion<%=i%>">
<%		for(CatTipoCal tipo : lisTipos){ %>		
			<option value="<%=tipo.getTipoCalId()%>"><%=tipo.getNombreTipoCal()%></option>
<%		} %>	
    		</select>
    	</td>    	
    	<td align="center"><input name="optativa<%=i%>" type="checkbox" id="optativa<%=i%>" value="S"></td>
  	</tr>
<% 	} 
%>	
  	</table>
  	<div class="alert alert-success">
  		<input name="PlanId" type="hidden" id="PlanId" value="<%=planId%>"></th>  		
  		<input class="btn btn-primary" type="button" name="Submit" value="Save" onClick="grabar()">
  	</div>  	
	</form>
</div>
</body>
<script>
	jQuery(".chosen").chosen();	
	<% for(int i = 0; i < lisMaterias.size(); i++) { %>
			jQuery("#fecha<%=i%>").datepicker();
		<%}    %>
</script>
</html>