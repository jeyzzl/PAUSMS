<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.plan.spring.MapaCurso"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.plan.spring.MapaCredito"%>
<%@page import="aca.alumno.spring.AlumGraduaMat"%>
<%@page import="aca.alumno.spring.AlumGradua"%>
<%@page import="aca.catalogo.spring.CatTipoCurso"%>
<%@page import="aca.plan.spring.MapaPlan"%>

<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>

<head>
  <style type="text/css">
	.etiqueta{
		font-size:9pt;
	}
  </style>
  <script type="text/javascript">
	
	function grabar(){
		if(document.frmSolicitud.FechaGradua.value != ""){
	  		document.frmSolicitud.Accion.value = "1";
	  		document.frmSolicitud.submit();
	  	}else{
	  		alert("Capture the tentative graduation date!");
	  		$("FechaGradua").focus();
	  	}
	}
	
	function borrar(){	 
		if(confirm("¿Would you like to eliminate the register?")){
	  		document.frmSolicitud.Accion.value = "2";
	  		document.frmSolicitud.submit();
		}
	}
	
	function recarga(){
		document.frmSolicitud.submit();
	}

  </script>
  
<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

</head>
<% 		
	String codigoAlumno 		= (String) session.getAttribute("codigoAlumno");
	String institucion 			= (String)session.getAttribute("institucion");
	int numAccion 				= (int)request.getAttribute("numAccion");
	String planId 				= (String)request.getAttribute("planId");	
	String carreraId 			= (String)request.getAttribute("carreraId");	
	
	float numMatAc				= (float)request.getAttribute("numMatAc");
	int numMatIns				= (int)request.getAttribute("numMatIns");
	int numMatPen				= (int)request.getAttribute("numMatPen");
	float numCreditos			= (float)request.getAttribute("numCreditos");
	int ciclo					= (int)request.getAttribute("ciclo");
	float optativo				= (float)request.getAttribute("optativo");
	float credOptaAC			= (float)request.getAttribute("credOptaAC");
	float credOptaFaltan		= (float)request.getAttribute("credOptaFaltan");	
	boolean existeSol			= (boolean)request.getAttribute("existeSol");	
	
	AlumPersonal alumPersonal 	= (AlumPersonal)request.getAttribute("alumPersonal");	
	AlumGradua alumGradua 		= (AlumGradua)request.getAttribute("alumGradua");	

	List<String> lisPlanes		= (List<String>)request.getAttribute("lisPlanes");
	List<MapaCurso> lisCursos 	= (List<MapaCurso>)request.getAttribute("lisCursos");
	
	HashMap<String, String> mapMateriaAc 				= (HashMap<String, String>)request.getAttribute("mapMateriaAc");
	HashMap<String, MapaPlan> mapaPlanes 				= (HashMap<String, MapaPlan>)request.getAttribute("mapaPlanes");
	HashMap<String, MapaCredito> mapaCreditos 			= (HashMap<String, MapaCredito>)request.getAttribute("mapaCreditos");
	HashMap<String, String> mapaCreditosAlumno			= (HashMap<String, String>)request.getAttribute("mapaCreditosAlumno");
	HashMap<String, AlumGraduaMat> mapaAlumnoMaterias	= (HashMap<String, AlumGraduaMat>)request.getAttribute("mapaAlumnoMaterias");
	HashMap<String, CatTipoCurso> mapaTipoCurso			= (HashMap<String, CatTipoCurso>)request.getAttribute("mapaTipoCurso");	
%>

<div class="container-fluid">
	<h2>Graduation Application<small class="text-muted fs-4"> ( <%=alumPersonal.getCodigoPersonal() %> - <%=alumPersonal.getApellidoPaterno()%>&nbsp;<%=alumPersonal.getApellidoMaterno()%>&nbsp;<%=alumPersonal.getNombre() %> ) </small></h2>
	
	<form name="frmSolicitud" action="solicitud" method="post" target="_self">
	<input type="hidden" name="Accion" value="<%=numAccion %>">
	<input type="hidden" name="MatAc" value="<%= numMatAc %>">
	<input type="hidden" name="MatPen" value="<%= numMatPen %>">
	<input type="hidden" name="MatIns" value="<%= numMatIns %>">
	
	<div class="alert alert-info d-flex align-items-center">
		<b>Degree:&nbsp;</b>
		<select name="plan" class="form-select" onchange='javascript:recarga()' style="width:400px;">
<%	for(String plan : lisPlanes){	
		String nombrePlan = "-";	
		if(mapaPlanes.containsKey(plan)){
			nombrePlan = mapaPlanes.get(plan).getCarreraSe();
		}
	%>
  			<option value='<%=plan%>' <%=planId.equals(plan)?"selected":""%> ><%=plan%> - <%=nombrePlan%></option>
<%	}	%>
  		</select>
	</div>		  	  
  	<table style="width:80%"  cellpadding="4" class="table table-sm table-bordered">
  	<tr class="table-info">
    	<td colspan="4"><b><font size="2">Analysis of Academic situation</font></b></td>
  	<tr>
  	<tr>
    	<td colspan="4">
      	1. The number of subjects I need to complete my degree (including this period) is: <b><u>&nbsp;&nbsp;<%= numMatPen+numMatIns %>&nbsp;&nbsp;</u></b>
    	</td>
	<tr>
	<tr>
    	<td>2. I plan to graduate in: 
		<td><input type="radio" name="Evento" value="R" <% if (alumGradua.getEvento().equals("R")) out.print("checked"); %> />End of regular course</td>
	 	<td><input type="radio" name="Evento" value="V" <% if (alumGradua.getEvento().equals("V")) out.print("checked"); %> />End of Summer</td>
     	<td>
       		Date: <input name="FechaGradua" type="text" data-date-format="dd/mm/yyyy" class="text" id="FechaGradua" size="10" maxlength="10" value="<%= alumGradua.getFechaGraduacion() %>" >
      		(DD/MM/AAAA)
     	</td>
  	</tr>
  	<tr>
    	<td>3. Ill walk for: 
	 	<td><input type="radio" name="Avance" value="P" <% if (alumGradua.getAvance().equals("P")) out.print("checked"); %> />Intern Diploma</td>
	 	<td><input type="radio" name="Avance" value="T" <% if (alumGradua.getAvance().equals("T")) out.print("checked"); %> />Title</td>
	 	<td>&nbsp;</td> 
  	</tr>
	<tr class="table-success">
    	<td colspan="4"><b>Note:</b>&nbsp;The student has the right to march in only one (1) graduation event per program. The only option
        available for postgraduate students is the degree.  
    	</td>
	</tr>
	<tr>
    	<td colspan="4">
       		Enrolled subjects in this period: <b><u>&nbsp;&nbsp;<%= numMatIns %>&nbsp;&nbsp;</u></b>
       		&nbsp;&nbsp;&nbsp;&nbsp;
       		Subjects to take <b><u>&nbsp;&nbsp;<%= numMatPen %>&nbsp;&nbsp;</u></b>
       		&nbsp;&nbsp;&nbsp;&nbsp;    
       		Credits to take <b><u>&nbsp;&nbsp;<%= numCreditos %>&nbsp;&nbsp;</u></b>
     	</td>
	</tr>
    <table style="width:80%"   class="table table-sm table-bordered">
    <tr class="table-info">
    	<th width="5%"  height="20">Sem.</th>
        <th width="55%">Subjects</th>
        <th width="5%">Crs.</th>
        <th width="15"><spring:message code="aca.Tipo"/></th>
        <th width="20">Programmed Date</th>
     </tr>
<%	
	numMatPen = 0;
	MapaCredito mapaCredito = new MapaCredito();
	AlumGraduaMat alumGraduaMat = new AlumGraduaMat();
	for (int i=0; i< lisCursos.size(); i++){
		MapaCurso curso = lisCursos.get(i);
		if ( ciclo != Integer.parseInt( curso.getCiclo()) ){
			
			ciclo = Integer.parseInt( curso.getCiclo());
			mapaCredito.setPlanId(planId);
			mapaCredito.setCiclo(curso.getCiclo());
			if (mapaCreditos.containsKey(planId+curso.getCiclo()) ){
				mapaCredito = mapaCreditos.get(planId+curso.getCiclo());							
				optativo 	= Float.parseFloat(mapaCredito.getOptativos().trim());
				if (mapaCreditosAlumno.containsKey(curso.getCiclo()+curso.getCursoId())){
				credOptaAC 	= Float.parseFloat(mapaCreditosAlumno.get(curso.getCiclo()+curso.getCursoId()));
				}
				credOptaFaltan = optativo-credOptaAC;						
			}else{
				optativo = 0;
			}
		}	
		
		if (mapaAlumnoMaterias.containsKey(codigoAlumno+curso.getCursoId())){
			alumGraduaMat = mapaAlumnoMaterias.get(codigoAlumno+curso.getCursoId());
		}
		
		String nombreTipo = "";
		if (mapaTipoCurso.containsKey(curso.getTipoCursoId())){
			nombreTipo = mapaTipoCurso.get(curso.getTipoCursoId()).getNombreTipoCurso();
		}
		
		if ( curso.getTipoCursoId().equals("1")||curso.getTipoCursoId().equals("3")||curso.getTipoCursoId().equals("2") ||
			 curso.getTipoCursoId().equals("4")||curso.getTipoCursoId().equals("5")||curso.getTipoCursoId().equals("8") )
		{			
			numMatPen++;
%>
	<tr class="tr2">
		<td align="center" height="20"><%= curso.getCiclo() %></td>
	    <td><%= curso.getNombreCurso() %></td>
	    <td align="center"><%= curso.getCreditos() %></td>
	    <td><%=nombreTipo%></td>
	    <td>
	      <input id="<%= curso.getCursoId() %>" class="input-small fecha" data-date-format="dd/mm/yyyy" type="text" name="<%= curso.getCursoId() %>" value="<%= alumGraduaMat.getProgramada() %>" size="20" maxlength="40">
	    </td>
	</tr>	
<%
		}else if (curso.getTipoCursoId().equals("7")){
			credOptaFaltan = credOptaFaltan - Float.parseFloat(curso.getCreditos());
			
			if ( !mapMateriaAc.containsKey(curso.getCursoId()) && credOptaFaltan >= 0){
				numMatPen++;
%>
	<tr>
	    <td align="center" height="20"><%= curso.getCiclo() %></td>
	    <td>Elective/Optional Subjects</td>
	    <td align="center"><%= curso.getCreditos() %> </td>
	    <td>Optional</td>
	    <td>
	      <input id="<%= curso.getCursoId() %>" class="input-small" type="text" data-date-format="dd/mm/yyyy" name="<%= curso.getCursoId() %>" value="<%= alumGraduaMat.getProgramada() %>" size="20" maxlength="40">
	    </td>
	</tr>						
<%			}
		}
	}	
%>  	  
   	</table>
	<table style="width:80%"  class="table table-sm table-bordered">
   	<tr>
    	<td colspan="4">
       	I hereby commit to taking the pending subjects before participating in the graduation exercises. I am aware that if I fail to honor this commitment I will lose the privilege of participating and must reapply when ready.
     	</td>
	</tr> 
	<tr>
   		<td colspan="4">I am aware that I must submit the photo packet no later than 3 weeks before the graduation date.
     	</td>
	</tr>
	<tr><td colspan="4">&nbsp;</td></tr>
	<tr>
   		<td colspan="4">
     	I want to give the diploma of gratitude to:<input name="Diploma" type="text" class="text" id="Diploma" size="70" maxlength="150" value="<%=alumGradua.getDiploma()%>">
     	</td>
	</tr>	
	<tr>
    	<td colspan="4">
     	<input type="button" class="btn btn-primary" name="Grabar" onClick="javascript:grabar();" value="Grabar">
     	<input type="hidden" name="NumMat" value="<%= numMatPen %>">
<% 	if (existeSol){ %>
		<input type="button"  class="btn btn-danger" name="Borrar" onClick="javascript:borrar();" value="Borrar">
		<input type="button" class="btn btn-primary"  onclick="document.location.href='formato?plan=<%= planId %>'" name="Formato" value="Formato">
<%	}%>  
     	</td>
    </tr>
    </table>	
	</form>
</div>
<script>
	jQuery('.fecha').datepicker();
	jQuery('#FechaGradua').datepicker();
</script>