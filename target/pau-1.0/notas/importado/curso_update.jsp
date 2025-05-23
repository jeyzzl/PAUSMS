<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatTipoCal"%>
<%@ page import= "aca.catalogo.spring.CatGradePoint"%>
<%@ page import="aca.kardex.spring.KrdxCursoImp"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%> 

<script type="text/javascript" src ="../../validacion.js"></script>
<script>
	function actualiza(){
		var fecha 	= document.frmImport.fCreada.value;
		var nota 	= document.frmImport.nota.value;
		
		if(fecha==""){
			alert("La fecha de creacion no puede ir vacia");		
			document.frmImport.fCreada.focus();
		}else if(nota ==""){
			alert("La nota no puede ir vacia");			
			document.frmImport.nota.focus();
		}else{		
			document.frmImport.submit();
		}		
	}	
</script>
<%
//variables
	String codigoPersonal		= (String)session.getAttribute("codigoPersonal");
	String codigoAlumno			= (String)session.getAttribute("codigoAlumno");
	String planId 				= (String)request.getAttribute("planId");
	String nombreAlumno			= (String)request.getAttribute("nombreAlumno");
	String carreraId 			= (String)request.getAttribute("carreraId");
	String nombreCarrera 		= (String)request.getAttribute("nombreCarrera");
	
	String folio				= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
	String accion 				= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String mensaje				= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	
	KrdxCursoImp krdxCursoImp	= (KrdxCursoImp)request.getAttribute("krdxCursoImp");	
		
	String convalidacion	= "N";
	String titulo			= "N";
	String optativa			= "N";
	String notaExtra		= "";
	String fechaExtra		= "";
	String notaConva		= "";
	String observaciones	= "";
	String nota				= "";
	String nombreOptativa 	= "";
	
	List<CatTipoCal> lisTipos		= (List<CatTipoCal>)request.getAttribute("lisTipos");	
	List<CatGradePoint> lisGrades	= (List<CatGradePoint>)request.getAttribute("lisGrades");
	HashMap<String,String> mapaGradePoint 	= (HashMap<String,String>) request.getAttribute("mapaGradePoint");

	
	if (accion.equals("0")){		
		if(krdxCursoImp.getConvalidacion()!=null )	convalidacion = krdxCursoImp.getConvalidacion();
		if (convalidacion.equals("S")){
			nota = krdxCursoImp.getNotaConva();
		}else{
			nota = krdxCursoImp.getNota();
		}		
		if(krdxCursoImp.getTitulo()!=null)		titulo = krdxCursoImp.getTitulo();	 
		if(krdxCursoImp.getOptativa()!=null) 	optativa = krdxCursoImp.getOptativa();
		if(krdxCursoImp.getNotaExtra()!=null) 	notaExtra = krdxCursoImp.getNotaExtra();
		if(krdxCursoImp.getFExtra()!=null)	 	fechaExtra = krdxCursoImp.getFExtra();	
		if(krdxCursoImp.getObservaciones()!=null) observaciones = krdxCursoImp.getObservaciones();
	}
%>
<div class="container-fluid">
	<h2>Imported Courses <small class="text-muted fs-6">( <%=codigoAlumno%>] [<%=nombreAlumno%>] -- [<%=planId%>] [<%=nombreCarrera%> )</small></h2>
	<div class="alert alert-info">
		<a href="cursos" class="btn btn-primary">Return</a> 
	</div>	
	<form name="frmImport" action="modificar" method="post">
	<input type="hidden" name="convalidacion" value="<%=convalidacion%>">
	<input type="hidden" name="optativa" value="<%=optativa%>">
	
	<table class="table table-sm table-bordered">
	  	<tr>	  		
	   		<td><strong>Subject</strong></td>
	   		<td><input name="cursoId" type="text" class="text" id="cursoId" value="<%=krdxCursoImp.getCursoId()%>" size="17" maxlength="15" readonly></td>
	  	</tr>
	  	<tr>	  		
	 		<td><strong>CourseId 2</strong></td>
	  		<td><input name="cursoId2" type="text" class="text" id="cursoId2" value="<%=krdxCursoImp.getCursoId2()%>" size="17" maxlength="15" readonly></td>
	 	</tr>
		<tr>	    	
	    	<td><strong>Creation Date </strong></td>
	    	<td><input name="fCreada" type="text" class="text" id="fCreada" onChange="validaFecha(this.value); this.value=borrar" value="<%=krdxCursoImp.getFCreada()==null?aca.util.Fecha.getHoy():krdxCursoImp.getFCreada()%>"  size="12" maxlength="10">
	    	<span class="Estilo1">(DD/MM/YYYY)</span></td>
		</tr>
		<tr>			
			<td><strong>Title</strong></td>
			<td><input name="titulo" type="checkbox" id="titulo" value="S" <%if(titulo.equals("S")){%>checked<%}%>></td>
		</tr>
	  	<tr>	    	
	    	<td><strong>Grade Type</strong></td>
	    	<td><select name="tipoCalId" id="tipoCalId">
<%
		String gradePointNombre = "";
		if (mapaGradePoint.containsKey(nota)){
			gradePointNombre 	= mapaGradePoint.get(nota);
			gradePointNombre	= gradePointNombre.split(";")[0];
		}

		for(int j= 0; j<lisTipos.size(); j++){
			CatTipoCal tipocalf	= (CatTipoCal) lisTipos.get(j);
			if(tipocalf.getTipoCalId().equals(krdxCursoImp.getTipoCalId())){
				out.print(" <option value='"+tipocalf.getTipoCalId()+"'");			
				out.print("Selected>"+ tipocalf.getNombreTipoCal()+"</option>");
				
			}else{
				out.print(" <option value='"+tipocalf.getTipoCalId()+"'");			
				out.print(" >"+ tipocalf.getNombreTipoCal()+"</option>");
			
			}		
		}	
%>	
    		</select>
    		</td>
		</tr>
		<tr>  			
    		<td><strong>Mark</strong></td>
    		<td><input name="nota" type="text" class="text" id="nota" value="<%=nota%>" size="4" maxlength="4" onKeypress="if ((window.event.keyCode < 48 || window.event.keyCode > 57) && window.event.keyCode != 46) window.event.returnValue = false;"></td>
  		</tr>  
  		<tr>
  			<td><strong>Grade</strong></td>
	    	<td><select name="grade" id="grade">
	    		<option value="N" selected="selected">NA</option>
<%
		for(CatGradePoint grade : lisGrades){
%>
				<option value="<%=grade.getFin() %>" <%if(grade.getGpNombre().equals(gradePointNombre)){ out.print("selected"); }%>><%=grade.getGpNombre() %></option>
<%		} %>
  		</tr>
  		<tr>    		
    		<td><strong>Extra Mark </strong></td>
    		<td><input name="notaExtra" type="text" class="text" id="notaExtra" value="<%=notaExtra%>" size="3" maxlength="3" onKeypress="if ((window.event.keyCode < 48 || window.event.keyCode > 57) && window.event.keyCode != 46) window.event.returnValue = false;"></td>
  		</tr>  		
  		<tr>    		
    		<td><strong>Extra Date </strong></td>
    		<td>
    			<input name="fechaExtra" type="text" class="text" id="fechaExtra" value="<%=fechaExtra%>" size="12" maxlength="10" onChange="validaFecha(this.value); this.value=borrar">
      			<span class="Estilo1">(DD/MM/YYYY)</span>
      		</td>
  		</tr>
  		<tr>    		
    		<td><strong>Comments: </strong></td>
    		<td><input name="observaciones" type="text" class="text" id="observaciones" value="<%=observaciones%>" size="25" maxlength="200"></td>
  		</tr>
		<tr>    		
    		<td><strong>Elective Name:</strong></td>
    		<td><input name="nombreOptativa" type="text" class="text" id="nombreOptativa" value="<%=krdxCursoImp.getOptativaNombre() %>" size="25" maxlength="30"></td>
  		</tr>  		
	</table>
	<div class="alert alert-info">
    	<input name="Folio" type="hidden" id="Folio" value="<%=folio%>">      	
      	<input type="button" name="Submit" class="btn btn-primary" value="Update" onClick="actualiza()">
      	&nbsp;
      	<%=!mensaje.equals("-")?mensaje:""%>
    </div>
	</form>
</div> 