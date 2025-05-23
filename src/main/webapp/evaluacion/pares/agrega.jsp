<%@page import="aca.edo.Edo"%>
<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="EdoPar" class="aca.edo.EdoPar" scope="page"/>
<jsp:useBean id="EdoParU" class="aca.edo.EdoParUtil" scope="page"/>

<script type="text/javascript">	
	function Modificar( EdoId, Maestro, facultad ){
		document.location="agrega?EdoId="+EdoId+"&CodigoMaestro="+Maestro+"&facultad="+facultad+"&Accion=1";
	}
	
	function Borrar( EdoId, Maestro, facultad ){
		document.location="agrega?EdoId="+EdoId+"&CodigoMaestro="+Maestro+"&facultad="+facultad+"&Accion=4";
	}
</script>

<%
	String codigoPersonal 	= (String) session.getAttribute("codigoPersonal");
	String codigoMaestro	= request.getParameter("CodigoMaestro")==null?"0":request.getParameter("CodigoMaestro");
	String edoId 			= request.getParameter("EdoId")==null?"0":request.getParameter("EdoId");
	String facultad 		= request.getParameter("facultad");
	String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");	
	String maestroSesion 	= session.getAttribute("codigoEmpleado").toString();
	
	String evaluadores 		= aca.edo.EdoParUtil.getMaestros(conEnoc, edoId, codigoMaestro);	
	String[] arreglo		= evaluadores.split("-");
	
	conEnoc.setAutoCommit(false);
	// Grabar el maestro que evalua
	if (accion.equals("1")){
		EdoPar.setEdoId(edoId);
		EdoPar.setCodigoPersonal(codigoMaestro);
		EdoPar.setFecha(aca.util.Fecha.getHoy());
		EdoPar.setUsuario(codigoPersonal);
		//System.out.println("Datos:"+edoId+":"+codigoMaestro+":"+codigoPersonal);
		if (EdoParU.existeReg(conEnoc, codigoMaestro, edoId )){
			//System.out.println("update:"+edoId+":"+codigoMaestro+":"+codigoPersonal);
			evaluadores += maestroSesion+"-";
			EdoPar.setMaestros(evaluadores);
			if (EdoParU.updateReg(conEnoc, EdoPar)) conEnoc.commit();
		}else{
			//System.out.println("insert:"+edoId+":"+codigoMaestro+":"+codigoPersonal);
			EdoPar.setMaestros("-"+maestroSesion+"-");
			if (EdoParU.insertReg(conEnoc, EdoPar)) conEnoc.commit();
		}
		
		// Actualizar el String de maestros que evaluan
		evaluadores 		= aca.edo.EdoParUtil.getMaestros(conEnoc, edoId, codigoMaestro);
		arreglo		= evaluadores.split("-");
	}
	
	//Borra al maestro seleccionado
	if(accion.equals("4")){
		EdoPar.setEdoId(request.getParameter("EdoId"));
		EdoPar.setCodigoPersonal(request.getParameter("CodigoMaestro"));
		EdoPar.setFecha(aca.util.Fecha.getHoy());
		EdoPar.setUsuario(request.getParameter("codigoPersonal"));
		if (EdoParU.existeReg(conEnoc, request.getParameter("CodigoMaestro"), edoId)){
			if (EdoParU.deleteReg(conEnoc, request.getParameter("CodigoMaestro"), edoId))
				conEnoc.commit();
		}			
	}
	conEnoc.setAutoCommit(true);
%>
<body>
<body>
<div class="container-fluid">
	<h2>Agregar Maestro<small class="text-muted fs-4"> ( <%=maestroSesion%> - <%=aca.vista.MaestrosUtil.getNombreMaestro(conEnoc, maestroSesion, "NOMBRE")%> )</small></h2>
	<div class="alert alert-info">
		<a href="asignaPar?EdoId=<%=edoId%>&facultad=<%=facultad%>" class="btn btn-primary"><spring:message code="aca.Regresar"/></a>
		<a class="btn btn-info" href="javascript:Modificar('<%=edoId%>','<%=codigoMaestro%>','<%=facultad%>')">Agregar Maestro</a>
	</div>
	<table  " class="table table-condensed table-nohover">
	  	<tr>
			<td style="text-align:center;">
				<b>Evaluación: <%=aca.edo.EdoUtil.getEdoNombre(conEnoc, edoId) %></b>
			</td>
	  	</tr>
	</table>	
	<table  class="table table-sm table-bordered">
		<tr class=table-info>
			<th width="20%"><spring:message code="aca.Codigo"/></th>
			<th width="70%"><spring:message code="aca.Maestro"/></th>
			<th width="30%"><spring:message code='aca.Borrar'/></th>
		</tr>
<%	
	for (int i=0; i<arreglo.length; i++){
		if ( arreglo[i].length() > 1 ){
%>
		<tr>
			<td><%=arreglo[i]%></td>
			<td><%=aca.vista.MaestrosUtil.getNombreMaestro(conEnoc, arreglo[i],"NOMBRE") %></td>
 			<td><a href="javascript:Borrar('<%=edoId%>','<%=codigoMaestro%>','<%=facultad%>')" class="btn btn-danger"><i class="fas fa-trash-alt"></i></a></td> 
		</tr>		
<%			
		}
	} 
%>
	</table>
</div>
</body>
<%@ include file= "../../cierra_enoc.jsp" %>