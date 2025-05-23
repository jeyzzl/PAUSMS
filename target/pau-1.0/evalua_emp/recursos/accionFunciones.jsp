
<%@page import="aca.puestos.PuestoFuncionRH"%>
<%@page import="aca.puestos.Seccion"%>
<%@page import="java.util.Iterator"%>
<%@page import="aca.puestos.CatFunciones"%>
<%@page import="java.util.List"%>
<%@page import="aca.puestos.OpPuestos"%>
<%@ include file="../../con_enoc.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" href="../../js/chosen/chosen.css" />
<%




OpPuestos op = new OpPuestos(conEnoc);

if(request.getParameter("add")!=null){
	op.insertFunciones(request);
}

List<CatFunciones> lsVer = op.lsCatFunciones();
List<PuestoFuncionRH> lsPf = op.lsFuncionesSeccion(new Integer(request.getParameter("sec")));

Seccion seccion = op.aronPuesto(new Integer(request.getParameter("sec")));

String strPuesto = "";
String strCategoria = "";

if(seccion!=null){
	strPuesto = seccion.getDescripcion_nueva();
	strCategoria = seccion.getCategoria_id()+"";
}

if(request.getParameter("rm")!=null){
	op.removePueFuncion(new Integer(request.getParameter("v")), new Integer(request.getParameter("sec")), new Integer(request.getParameter("idf")));
	response.sendRedirect("accionFunciones?sec="+request.getParameter("sec"));
}


%>

<div class="container">
	<div class="alert alert-info">
		<h2>Agregar Funciones a <%= strPuesto %> </h2>
	</div>

	<form action="" method="post" name="verboform" id="verboform" class="form-horizontal">
	<table class="table" align="center">
	<tr>
	<td><strong>Verbo descriptor</strong></td>
	<td>
		<select name="verbo" class="chosen" id="verbo" onchange="otroInp()">
		<%
		Iterator<CatFunciones> itFun = lsVer.iterator();
		while(itFun.hasNext()){
			CatFunciones cfu = itFun.next();
		%>
		<option value="<%= cfu.getId() %>"><%= cfu.getVerbo() %></option>
		<%
		}
		%>
		<option value="00"><spring:message code='aca.Otro'/></option>
	</select><br>
	<input type="text" name="otro" id="otro" style="display: none;" />
	</td>
	<td><strong>Descripción complementaria</strong></td>
	<td>
	<textarea name="descripcion" id="descripcion" cols="45" class="form-control"></textarea>
	</td>
	</tr>
				<tr>
				
				<td colspan=4>
				  <input type="hidden" name="id_seccion" id="id_seccion" value="<%= request.getParameter("sec") %>"/>
				<input type="hidden" name="sec" id="sec" value="<%= request.getParameter("sec") %>"/>
				</td>
			</tr>
	<tr>
	<td colspan=4><a href="accionPuestoRH?idseccion=<%= request.getParameter("sec") %>" class="btn btn-primary"><spring:message code="aca.Regresar"/></a> <input type="submit" name="add" id="add" value="Agregar" class="btn btn-primary" /></td>
	</tr>
	</table>
	
	</form>
	
	
	<table class="table" align="center">
	<tr>
	<th><strong>#</strong>
	</th>
	<th><strong>Verbo</strong></th>
	<th><strong><spring:message code='aca.Descripcion'/></strong></th>
	<th></th>
	</tr>
	<%
	Iterator<PuestoFuncionRH> itPuRH = lsPf.iterator();
	while(itPuRH.hasNext()){
		PuestoFuncionRH pf = itPuRH.next();
	%>
	<tr>
	<td><a href="accionFunciones?rm=true&v=<%= pf.getVerbo() %>&sec=<%= pf.getId_seccion() %>&idf=<%= pf.getId_funcion() %>" class="btn btn-danger">REMOVER</a></td>
	<td><%= op.getVerbo(pf.getVerbo()) %></td>
	<td><%= pf.getDescripcion() %></td>
	<td>	</td>
	<%
	}
	%>
	</tr>
	
	</table>
	
	</div>
<script src="../../js/chosen/chosen.jquery.js" type="text/javascript"></script>
<script type="text/javascript"> 
		jQuery(".chosen").chosen(); 
		
		function otroInp(){
			if(document.verboform.verbo.options[document.verboform.verbo.selectedIndex].value=='00'){
				document.getElementById('otro').show();
			}else{
				document.getElementById('otro').hide();
			}
		}
		
</script>


<%@ include file="../../cierra_enoc.jsp"%>