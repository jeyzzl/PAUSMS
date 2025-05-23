<%@ include file= "../../con_enoc.jsp"%>
<%@ include file= "id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.vista.MaestrosUtil"%>
<jsp:useBean id="cargaU" scope="page" class="aca.carga.CargaUtil"/>
<jsp:useBean id="carga" scope="page" class="aca.carga.Carga"/>
<jsp:useBean id="facultad" scope="page" class="aca.catalogo.CatFacultad"/>
<jsp:useBean id="alumno" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="kardex" scope="page" class="aca.kardex.KrdxCursoAct"/>
<jsp:useBean id="tipo" scope="page" class="aca.catalogo.CatTipoCal"/>
<jsp:useBean id="TipoCalU" scope="page" class="aca.catalogo.CatTipoCalUtil"/>
<jsp:useBean id="alumCurso" scope="page" class="aca.vista.AlumnoCurso"/>
<jsp:useBean id="AlumCursoU" class="aca.vista.AlumnoCursoUtil" scope="page"/>
<%
	String cargaId = request.getParameter("cargaId");
	String codigoPersonal = request.getParameter("codigoPersonal");
	String cursoCargaId = request.getParameter("cursoCargaId");
	String accion = request.getParameter("accion");
	String nota = request.getParameter("nota");
	String fTitulo = request.getParameter("fecha");
	String mensaje = "";
	if (cargaId==null) cargaId= (String) session.getAttribute("cargaId");
	if (codigoPersonal==null) codigoPersonal = "";
	if (cursoCargaId==null) cursoCargaId = "";
	if (accion==null) accion = "";
	java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("dd/MM/yyyy");
	if (accion.equals("guardar")){
		kardex.mapeaRegId(conEnoc,codigoPersonal,cursoCargaId);
		alumCurso = AlumCursoU.mapeaRegId(conEnoc,codigoPersonal,cursoCargaId);
		kardex.setNota(nota);
		kardex.setFTitulo(fTitulo);
		String notaAprobatoria = alumCurso.getNotaAprobatoria();
		if (Integer.parseInt(nota)<aca.kardex.TituloSuficiencia.notaAprobatoria(conEnoc,cursoCargaId))
			kardex.setTipoCalId("2");
		else kardex.setTipoCalId("1");
		if (kardex.updateRegTitulo(conEnoc)){
			mensaje = "OK... La nota ha sido actualizada.";
		}else mensaje = "NO se pudo poner la nota.";
	}
%>
<script>
	function modificar(id,matricula){
		document.forma.codigoPersonal.value = matricula;
		document.forma.cursoCargaId.value = id;
		document.forma.accion.value = "modificar";
		document.forma.submit();
	}
	function guardar(){
		document.forma.accion.value = "guardar";
		document.forma.submit();
	}
	
</script>
<div class="container-fluid">
	<h2>Sufficient Title</h2>
	<form action="evaluar" method='post' name='forma' id="noayuda">
	<input type='hidden' name='accion'>
	<input type='hidden' name='codigoPersonal' value='<%=codigoPersonal%>'>
	<input type='hidden' name='cursoCargaId' value='<%=cursoCargaId%>'>
	<div class="alert alert-info d-flex align-items-center">
		Load: 
    	<select class="form-select" style="width:350px" name="cargaId" onChange="forma.submit()">
<%
	int i=0;
	boolean okCarga=false;
	ArrayList<aca.carga.Carga> lisCarga = cargaU.getListAll(conEnoc, "order by nombre_carga");
	// Buscando la carga
	for( i=0;i<lisCarga.size();i++){
		carga = (aca.carga.Carga) lisCarga.get(i);
		if (carga.getCargaId().equals(cargaId)) okCarga = true;
	}
	for( i=0;i<lisCarga.size();i++){
		carga = (aca.carga.Carga) lisCarga.get(i);
		if ( lisCarga.size()==1 ){	cargaId = carga.getCargaId(); 	}
		if ( okCarga==false && i==0){ cargaId = carga.getCargaId(); }
		if (carga.getCargaId().equals(cargaId)){
			out.print(" <option value='"+carga.getCargaId()+"' Selected>"+ carga.getNombreCarga()+"</option>");
		}else{
			out.print(" <option value='"+carga.getCargaId()+"'>"+ carga.getNombreCarga()+"</option>");
		}				
	}
	lisCarga = null;				
%>
      	</select>
	</div>
	<table style="width:98%">
  	<tr><td><%=mensaje%></td></tr>
<%
	ArrayList<String> lisFacultad = aca.kardex.TituloSuficiencia.getFacTitulos(conEnoc,cargaId);		
	String fecha=df.format(new java.util.Date());
	for (i=0;i<lisFacultad.size();i++){
%>
	</table>
	<table class="table table-sm" style="width:80%">
	<tr>
			<td align='left' colspan='9'><h3><%=aca.catalogo.CatFacultadUtil.getNombreFacultad(conEnoc,(String)lisFacultad.get(i))%></h3></td>
	</tr>
	<tr>
		<th><spring:message code="aca.Maestro"/></th>
		<th><spring:message code="aca.Materia"/></th>
		<th>#<spring:message code="aca.Acta"/></th>
		<th><spring:message code="aca.Matricula"/></th>
		<th><spring:message code="aca.Alumno"/></th>
		<th><spring:message code="aca.Estado"/></th>
		<th><spring:message code="aca.Nota"/></th>
		<th><spring:message code="aca.Fecha"/></th>
		<th><spring:message code="aca.Operacion"/></th>
	</tr>
<%
		ArrayList<String> lisAlumnos = aca.kardex.TituloSuficiencia.getAlumnosTitulo(conEnoc,cargaId,(String)lisFacultad.get(i));
		for (int j=0;j<lisAlumnos.size();j+=7){
			if (lisAlumnos.get(j+4)!=null) fecha = (String)lisAlumnos.get(j+4);
			tipo = TipoCalU.mapeaRegId(conEnoc,(String)lisAlumnos.get(j+6));
%>			<tr class="tr2">
				<td><%=MaestrosUtil.getNombreMaestro(conEnoc,(String)lisAlumnos.get(j+1),"NOMBRE")%></td>
				<td><%=lisAlumnos.get(j+5)%></td>
				<td><%=lisAlumnos.get(j+2)%></td>
				<td><%=lisAlumnos.get(j)%></td>
				<td><%=alumno.getNombre(conEnoc,(String)lisAlumnos.get(j),"NOMBRE")%></td>
				<td align='center'><%=tipo.getNombre()%></td>
<%				if (cursoCargaId.equals((String)lisAlumnos.get(j+2)) && accion.equals("modificar") && codigoPersonal.equals((String)lisAlumnos.get(j))){
%>					
				<td><input type='text' size='3' name='nota' value = <%=lisAlumnos.get(j+3)%>></td>
				<td><input type='text' size='8' name='fecha' value = <%=fecha%>></td>
				<td><a class="btn btn-primary" href="javascript:guardar()"><spring:message code="aca.Guardar"/>:</a></td>
				<script>document.forma.nota.focus()</script>						
<%				}else{ %>
				<td><%=lisAlumnos.get(j+3)%></td>
				<td><%=lisAlumnos.get(j+4)%></td>
				<td style="text-align:center;">
					<a href="javascript:modificar('<%=lisAlumnos.get(j+2)%>','<%=lisAlumnos.get(j)%>');" title="Modificar"><i class="fas fa-pencil-alt"></i></a>
				</td>
<%				} %>				
			</tr>
<%	
		}
	}
%> 
</table>
</form>
</div>
<%@ include file= "../../cierra_enoc.jsp" %>