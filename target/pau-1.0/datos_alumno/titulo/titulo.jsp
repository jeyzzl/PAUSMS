<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="Titulo" scope="page" class="aca.kardex.KrdxAlumnoTitulo"/>
<head>
	<script type="text/javascript">
	
	function nuevo(){
		document.frmTitulo.CursoCargaId.value		= "0";
		document.frmTitulo.submit();
	}
	
	function grabar(){
		if( document.frmTitulo.Presidente.value!="" && document.frmTitulo.Secretario.value!="" && document.frmTitulo.Miembro.value!="" 
			&& document.frmTitulo.Comentario.value!="" && document.frmTitulo.Nota.value!=""){
			document.frmTitulo.Accion.value="2";
			document.frmTitulo.submit();
		}else{
			alert("<spring:message code="aca.JSCompletar" />");
		}
	}	
	
	function formato( CursoCargaId, CursoId ){
		
		if(confirm("<spring:message code="aca.JSSeguro"/>:"+CursoCargaId+CursoId )==true){
	  		document.location="formato?CursoCargaId="+CursoCargaId+"&CursoId="+CursoId;
	  	}
	}	
</script>	   
</head>	
<!-- inicio de estructura -->
<%
	String codigoPersonal 		= (String) session.getAttribute("codigoPersonal");
	String codigoAlumno 		= (String) session.getAttribute("codigoAlumno");
	String accion 				= request.getParameter("Accion")==null?"0":request.getParameter("Accion");	
	String cursoCargaId 		= request.getParameter("CursoCargaId");
	String cursoId 				= request.getParameter("CursoId");

	String planId 				= aca.plan.CursoUtil.getPlanId(conEnoc,cursoId);
	String carreraId 			= aca.plan.PlanUtil.getCarreraId(conEnoc,planId);	
	
	int numAccion				= Integer.parseInt(accion);
	boolean existeTitulo		= false;
	String resultado 			= " ";
	
	// Operaciones a realizar en la pantalla
	switch (numAccion){
		
		case 2: { // Grabar
			Titulo.setCursoCargaId(cursoCargaId);
			Titulo.setCursoId(cursoId);
			Titulo.setPresidente(request.getParameter("Presidente"));
			Titulo.setSecretario(request.getParameter("Secretario"));
			Titulo.setMiembro(request.getParameter("Miembro"));
			Titulo.setComentario(request.getParameter("Comentario"));
			Titulo.setNota(request.getParameter("Nota"));
			Titulo.setCodigoPersonal(codigoAlumno);
			Titulo.setUsuario(codigoPersonal);
			Titulo.setCarreraId(carreraId);			
			conEnoc.setAutoCommit(false);
			if (Titulo.existeReg(conEnoc) == false){
				if (Titulo.insertReg(conEnoc)){
					conEnoc.commit();
					resultado = "Grabado: " + Titulo.getCodigoPersonal();
				}else{
					resultado = "Error al grabar en KrdxAlumnoTitulo..! : "+Titulo.getCodigoPersonal();
				}
			}else{
				if (Titulo.updateReg(conEnoc)){
					conEnoc.commit();
					resultado = "Modificado: " + Titulo.getCodigoPersonal();		
				}else{
					resultado = "Error al modificar: "+Titulo.getCodigoPersonal();
				}
			}
			conEnoc.setAutoCommit(true);
			
			break;			
		}		
	} //fin de switch
	
	Titulo.setCodigoPersonal(codigoAlumno);
	Titulo.setCursoCargaId(cursoCargaId);
	if (Titulo.existeReg(conEnoc)){
		Titulo.mapeaRegId(conEnoc,codigoAlumno,cursoCargaId);
		existeTitulo = true;
	}else{
		existeTitulo = false;
	}
%>
<body>
<div class="container-fluid">
	<h3>
		<spring:message code="datosAlumno.titulo.Titulo2"/>
		<small class="text-muted fs-5">( <spring:message code="aca.Alumno"/>: <%= codigoAlumno %> - <%=aca.alumno.AlumUtil.getNombreAlumno(conEnoc, codigoAlumno,"NOMBRE")%> - <%= aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc,carreraId) %> )</small>
	</h3>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="materias" ><spring:message code="aca.Regresar" /></a>&nbsp;&nbsp;
		<a class="btn btn-primary" href="javascript:grabar();" ><spring:message code="aca.Guardar" /></a>&nbsp;&nbsp;
		<a class="btn btn-success" href="javascript:formato('<%= cursoCargaId %>', '<%= cursoId %>')" ><spring:message code="aca.Formato" /></a>
	</div>
	<form action="titulo" method="post" name="frmTitulo" target="_self">
	<input type="hidden" name="Accion">
	<input type="hidden" name="CursoCargaId" value="<%=cursoCargaId%>">
	<input type="hidden" name="CursoId" value="<%=cursoId%>">
	<table style="margin: 0 auto;"   class="table table-condensed">
  	<tr>
  		<th colspan="2"><spring:message code="datosAlumno.titulo.Titulo3" /></th></tr>
  	<tr>
    	<td><spring:message code="analisis.materias.Acta" />:</td>
    	<td><%= cursoCargaId %></td>
  	</tr>
  	<tr>
	    <td><spring:message code="aca.Materia" />:</td>
    	<td><%= aca.plan.CursoUtil.getMateria(conEnoc,cursoId) %></td>
  	</tr>
  	<tr>
		<td><spring:message code="datosAlumno.titulo.Presidente" />:</td>
    	<td><input name="Presidente" type="text" class="text" id="Presidente" size="30" maxlength="70" value= "<%= Titulo.getPresidente() %>"></td>
  	</tr>
  	<tr>
	    <td><spring:message code="datosAlumno.titulo.Secretario" /></td>
	    <td><input name="Secretario" type="text" class="text" id="Secretario" size="30" maxlength="70" value="<%= Titulo.getSecretario() %>"></td>
  	</tr>
  	<tr>
    	<td><spring:message code="datosAlumno.titulo.Miembro" /></td>
    	<td><input name="Miembro" type="text" class="text" id="Miembro" size="30" maxlength="70" value="<%= Titulo.getMiembro() %>"></td>
  	</tr>
  	<tr>
    	<td><spring:message code="aca.Comentario" /></td>
    	<td><input name="Comentario" type="text" class="text" id="Comentario" size="30" maxlength="300" value="<%= Titulo.getComentario() %>"></td>
  	</tr>
  	<tr>
	    <td><spring:message code="aca.Nota"/></td>
	    <td><input name="Nota" type="text" class="text" id="Nota" size="4" maxlength="3" value="<%= Titulo.getNota() %>"></td>
  	</tr>
  	<tr>
    	<td><spring:message code="aca.Usuario" /></td>
    	<td><%= codigoPersonal %></td>
  	</tr>  
  	<tr>
  		<td align="center" colspan="2"></td>
  	</tr>
	</table>
	</form>
	<div class="alert alert-info"><%= resultado %></div>	
</div>
</body>
<!-- fin de estructura -->
<%@ include file= "../../cierra_enoc.jsp" %>