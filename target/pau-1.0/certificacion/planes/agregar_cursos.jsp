<%@ page import= "java.util.List"%>

<%@ page import= "aca.cert.spring.CertPlan"%>
<%@ page import= "aca.cert.spring.CertCurso"%>
<%@ page import= "aca.catalogo.spring.CatTipoCurso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<head>
<script type="text/javascript">		
	function Grabar(){				
		if(document.forma.creditos.value != "" && document.forma.orden.value != "" && document.forma.nombre.value != ""){			  
			document.forma.Accion.value="1";
			document.forma.submit();
		}else{
			alert("Los campos con * deben ser llenados para poder guardar");
		}	
	}
</script>
</head>
<%
	String accion 		= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String facultad 	= (String) request.getAttribute("facultad"); 
	String planId 		= (String) request.getAttribute("planId");	 
	String cicloId		= (String) request.getAttribute("cicloId"); 
	String cursoId 		= (String) request.getAttribute("cursoId");	 
	
	CertPlan certPlan 	= (CertPlan) request.getAttribute("certPlan"); 
	CertCurso certCurso = (CertCurso) request.getAttribute("certCurso"); 
	boolean inserta		= (boolean) request.getAttribute("inserta"); 

	List<CatTipoCurso> lis = (List<CatTipoCurso>)request.getAttribute("lis");	 
%>
<div class="container-fluid">
	<h2>Editar Curso<small class="text-muted fs-5">(<%=certPlan.getCarrera() %> [<%=certPlan.getPlanId() %>])</small></h2>
	<form id="forma" name="forma" action="agregar_cursos?ciclo=<%=cicloId %>" method="post">
	<input name="cursoId" type="hidden" value="<%=cursoId%>">
	<input name="Accion" type="hidden" value="<%=accion%>">
	<div class="alert alert-info">
		<a href="ciclos?plan=<%=planId%>&facultad=<%=facultad%>" class="btn btn-primary"><spring:message code="aca.Regresar"/></a>
	</div>
<%
	if(accion.equals("1") && inserta){
		out.print("<div class='alert alert-success'>Grabado.</div>");
	}else if(accion.equals("1")){
%>
		<div class="alert alert-danger"><b>¡Error al grabar el curso. Int&eacute;ntelo de nuevo!</font></div>
<%
	}	
%>	
	<table class="table table-sm">
	<tr><th colspan="2">&nbsp;</th></tr>
	<tr>
		<td><spring:message code="aca.Plan"/></td>
		<td>
		  <%=certPlan.getCarrera() %> [<%=planId %>]
		  <input type="hidden" id=plan name="plan" value="<%=planId %>" maxlength="100" size="5" class="form-control"/>
		</td>
	  </tr>
	  <tr>
	    <td>Tipo Curso:</td>
		<td>
		  <select name="tipoCursoId" id="tipoCursoId" class="form-select" style="width:350px">
<%			

	for(CatTipoCurso tcurso : lis){
		if(certCurso.getTipoCursoId().equals(tcurso.getTipoCursoId())){
			out.println("<option value="+tcurso.getTipoCursoId()+" selected>"+tcurso.getNombreTipoCurso()+"</option>");
		}else{
			out.println("<option value="+tcurso.getTipoCursoId()+">"+tcurso.getNombreTipoCurso()+"</option>");
		}				
	}
%>		  </select>
        </td>
	  </tr>
	  <tr>
	    <td><spring:message code="aca.Clave"/></td>
	    <td>		
		  <input type="text" class="form-control" id=clave name="clave" value="<%=certCurso.getClave() %>" maxlength="100" size="5" />					
		</td>
	  </tr>
	  <tr>
		<td><spring:message code="aca.Nombre"/><b><font color="#AE2113"> *</font></b></td>						
		<td>
		  <input type="text" class="form-control" id=nombre name="nombre" value="<%=certCurso.getCursoNombre()%>" maxlength="120" size="40" />
		</td>
	  </tr>
	  <tr>
		<td><%=certPlan.getTitulo1() %></td>
		<td>
		  <input type="text" class="form-control" id=fst name="fst" value="<%=certCurso.getFst() %>" maxlength="100" size="1" />
		</td>
	  </tr>
	  <tr>
		<td><%=certPlan.getTitulo2() %></td>
		<td>
		  <input type="text" class="form-control" id=fsp name="fsp" value="<%=certCurso.getFsp()%>" maxlength="100" size="1" />
		</td>
	  </tr>
	  <tr>
		<td><%=certPlan.getTitulo3() %><b><font color="#AE2113"> *</font></b></td>
		<td>
		  <input type="text" class="form-control" id=creditos name="creditos" value="<%=certCurso.getCreditos()%>" maxlength="100" size="1" />
		</td>
	  </tr>
	  <tr>
		<td>Orden<b><font color="#AE2113"> *</font></b></td>
		<td class="d-flex align-items-baseline">
		  <input type="text" class="form-control" id=orden name="orden" value="<%=certCurso.getOrden()%>" maxlength="100" size="1" style="width:100px" />
			&nbsp;&nbsp;orden de materias en el certificado
		</td>
	  </tr>	  
	  <tr>
		<th style="text-align:left;"colspan="2"><input type="submit" class="btn btn-primary" value="Grabar" onclick="javascript:Grabar();"/></th>
	  </tr>
	</table>
	</form>
</div>