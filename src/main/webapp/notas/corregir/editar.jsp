<%@ page import="java.text.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="aca.kardex.spring.KrdxCursoAct" %>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	
	function Modificar(){
		if(document.frmkardex.NotaExtraOriginal > 0 && document.frmkardex.nota != document.frmkardex.NotaOriginal){
			if(confirm("Estás modificando la nota ordinaria de una materia que ya tiene una nota extraordinaria. ¿Estás seguro de continuar?")==true){				
				document.frmkardex.submit();
			}
		}else if(confirm("¿Estas seguro de cambiar esta nota?")==true){			
			document.frmkardex.submit();
		}
	}	
</script>
<%
	String codigoPersonal	= (String) session.getAttribute("codigoPersonal");
	String codigoAlumno 	= (String) session.getAttribute("codigoAlumno");
	String cursoCargaId		= request.getParameter("CursoCargaId");
	String nombreMateria	= request.getParameter("Materia");
	String planId			= request.getParameter("PlanId");
	String alumnoNombre		= (String) request.getAttribute("alumnoNombre");
	KrdxCursoAct kardex		= (KrdxCursoAct) request.getAttribute("kardex");
	
	String nota				= "";
	String fechaNota		= "";
	String notaExtra		= "";
	String fechaExtra		= "";
	String comentario		= "";
	String mensaje 			= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
%>
<div class="container-fluid">
	<h3>
		Corrección de Nota		 
		<small class="text-muted fs-5">( <%=codigoAlumno%> - <%=alumnoNombre%> - <%=planId%> - <%=nombreMateria%> )</small>
	</h3>
	<div class="alert alert-info">
		<a href="notas" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
	<form action="grabar" method="post" name="frmkardex" target="_self">
	
	<input type="hidden" name="NotaOriginal" value="<%=kardex.getNota()%>">
	<input type="hidden" name="NotaExtraOriginal" value="<%=kardex.getNotaExtra()%>">
	<input type="hidden" name="CursoCargaId" id="CursoCargaId" value="<%=cursoCargaId%>">
    <input type="hidden" name="Materia" id="Materia" value="<%=nombreMateria%>">
	<input type="hidden" name="PlanId"  id="PlanId" value="<%=planId%>">
	<div class="form-group">
		<label><strong>Nota Ord.</strong></label>
		<input name="nota" type="text" class="text" id="nota" size="3" maxlength="3" value="<%=kardex.getNota()%>">
		<br><br>
		<label><strong>Fecha Eval.</strong></label>
		<input name="fechaNota" type="text" class="text" id="fechaNota" data-date-format="dd/mm/yyyy" value="<%=kardex.getfNota()%>" size="11" maxlength="10"> (DD/MM/AAAA)
		<br><br>
		<label><strong>Nota Extra</strong></label>
		<input name="notaExtra" type="text" class="text" id="notaExtra" value="<%=kardex.getNotaExtra()%>" size="3" maxlength="3">
		<br><br>
		<label><strong>Fecha Extra</strong></label>
		<input name="fechaExtra" type="text" class="text" id="fechaExtra" data-date-format="dd/mm/yyyy" value="<%=kardex.getfExtra()==null?"":kardex.getfExtra()%>" size="11" maxlength="10"> (DD/MM/AAAA)
		<br><br>
		<label><strong>Tipo Calificación</strong></label>
		<select name="tipoCal" id="tipoCal">
			<option value="1" <%if (kardex.getTipoCalId().equals("1")) out.println("selected");%>>AC</option>
			<option value="2" <%if (kardex.getTipoCalId().equals("2")) out.println("selected");%>>NA</option>
			<option value="4" <%if (kardex.getTipoCalId().equals("4")) out.println("selected");%>>RA</option>
			<option value="6" <%if (kardex.getTipoCalId().equals("6")) out.println("selected");%>>CD</option>
        </select>
        <br><br>
		<label><strong>Comentario</strong></label>
		<input name="comentario" type="text" class="text" id="comentario" value="<%=kardex.getComentario()%>" size="50" maxlength="70">
	</div><br>
	<div class="alert alert-info">
		<a href="javascript:Modificar()" class="btn btn-primary"><spring:message code="aca.Grabar"/></a>			
	</div>
	</form>
</div>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script>
	jQuery('#fechaNota').datepicker();
	jQuery('#fechaExtra').datepicker();
</script>