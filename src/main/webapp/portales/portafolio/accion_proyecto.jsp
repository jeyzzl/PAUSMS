<%@page import="aca.proyectos.Logro"%>
<%@page import="aca.proyectos.InstMetas"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="aca.proyectos.EmpLogro"%>
<%@ include file="../../con_enoc.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.proyectos.OPProyectos"%>
<%@page import="aca.proyectos.OPMetas"%>
<%@page import="aca.proyectos.DatosEncabezado"%>


<%
	if (request.getParameter("accion") == null) {
        response.sendRedirect("actividades");
    }
    String returnpage="actividades";
    if(request.getParameter("returnpage")!=null){
        returnpage = request.getParameter("returnpage");
    }
    
    if (request.getParameter("guardar") != null) {
        EmpLogro op = new EmpLogro(conEnoc);
        if (request.getParameter("accion").equals("agregar")) {
            op.addEmpLogro(request);
        }else if(request.getParameter("accion").equals("editar")){
			if(request.getParameter("eliminaLogro")!=null){
				op.eliminaEmpLogro(request);
				returnpage="actividades";
			}else{
				op.editaEmpLogro(request);
			}
        }            
        response.sendRedirect(returnpage);
    }
    
    EmpLogro el = new EmpLogro(conEnoc);
    OPMetas mi = new OPMetas(conEnoc);
    
    Logro logro=null;

    if(request.getParameter("accion").equals("editar")){
    	EmpLogro emlo = new EmpLogro(conEnoc);
    	logro = emlo.getLogro(new Integer((String)request.getParameter("idlogro")));
    } 
%>

<div class="container-fluid">

	<h2>Agregar Proyecto</h2>

	<div class="alert alert-info">
		<a href="<%=returnpage%>" class="btn btn-primary"><i
			class="icon-arrow-left icon-white"></i>
		<spring:message code='aca.Regresar' /></a>
	</div>

	<form action="" method="post" name="accion_meta">
		<input type="hidden" name="id"
			value="<%=request.getParameter("idlogro") %>" /> <input
			name="accion" type="hidden" id="accion"
			value="<%=request.getParameter("accion")%>"> <input
			name="codigopersonal" type="hidden" id="codigopersonal"
			value="<%= session.getAttribute("codigoPersonal") %>"> <input
			name="returnpage" type="hidden" id="returnpage"
			value="<%=returnpage%>">
		<% if(request.getParameter("accion").equals("editar")){ %>
		<input name="idlogro" type="hidden" id="idlogro"
			value="<%=request.getParameter("idlogro")%>"> <label
			for="checkbox">Eliminar permanentemente logro</label> <input
			type="checkbox" name="eliminaLogro" id="eliminaLogro" />

		<% } %>
		<p>
			<label for="deptos">Elija el departamento donde aplicara el
				proyecto</label> <select name="depto" id="depto">
				<%
				Iterator<String> itEmplPuesto  = el.deptosEmpleado((String)session.getAttribute("codigoPersonal"),"001-2014").iterator();
				Iterator<String> itPuestos = el.deptos("001-2014").iterator();
					List<String> lsPuestosJefe = el.deptosByJefes((String)session.getAttribute("codigoPersonal"),"001-2014");
			    Iterator<String> itPuestosJefe = lsPuestosJefe.iterator();
			    
				%>
				<optgroup label="Departamentos de trabajo">
					<% 
					if(lsPuestosJefe.size()>0){
						while(itPuestosJefe.hasNext()){
							String  ccosto= itPuestosJefe.next();  %>
					<option value="<%= ccosto %>"
						<% if(logro!=null){ if(ccosto.equals(logro.getDepto())){ %>
						selected <% } }%>><%= el.getCcostoNombre(ccosto, "001-2014") %></option>
					<%						}
						
					}else{
						while(itEmplPuesto.hasNext()){ 
							String ccosto = itEmplPuesto.next(); %>
					<option value="<%= ccosto %>"
						<% if(logro!=null){ if(ccosto.equals(logro.getDepto())){ %>
						selected <% } }%>><%= el.getCcostoNombre(ccosto, "001-2014") %></option>
					<% 	}
					}
					%>
				</optgroup>
				<optgroup label="Otros Departamentos">
					<% while(itPuestos.hasNext()){ 
					String ccosto = itPuestos.next(); %>
					<option value="<%= ccosto %>"><%= el.getCcostoNombre(ccosto, "001-2014") %></option>
					<% } %>
				</optgroup>
			</select>
		</p>
		<p>
			<label for="metas">Elija una meta a la que aportara con el
				proyecto </label> <select name="id_metainst" id="id_metainst"
				onchange="showComentario();">
				<optgroup label="Experiencias de aprendizaje">
					<%
				Iterator<InstMetas> itMetasIns = mi.getInsMetas(2014).iterator();
				while(itMetasIns.hasNext()){
					InstMetas inme = itMetasIns.next();
					if( inme.getId() ==  9 ){
						out.print("</optgroup><optgroup label='Factores de apoyo'>");
					}
			%>
					<option value="<%= inme.getId() %>"
						<% if(logro!=null){ if(inme.getId()==logro.getId_metainst()){ %>
						selected <% } }%>><%= inme.getDescripcion() %></option>
					<% 
				} 
			%>
				</optgroup>
			</select>
			<%
				itMetasIns = mi.getInsMetas(2014).iterator();
				while(itMetasIns.hasNext()){
					InstMetas inme = itMetasIns.next(); 
			%>
		
		<div class="comentario<%=inme.getId() %> comentarios"
			style="display: none; font-size: 12px; border: 1px solid #ccc; padding: 15px;">
			<%=inme.getComentario() %>
		</div>
		<% } %>
		</p>
		<p>
			<label for="descripcion">Descripcion del proyecto</label>
			<textarea name="descripcion" cols="60" id="descripcion">
				<% if(logro!=null){ %><%= logro.getDescripcion() %>
				<% } %>
			</textarea>
		</p>
		<p>
			<label for="indicador">Indicador de alcance</label>
			<textarea name="indicador" cols="60" id="indicador">
				<% if(logro!=null){ %><%= logro.getIndicador() %>
				<% } %>
			</textarea>
		</p>
		<p>
			<label for="fecha_final">Fecha limite para cumplir el
				proyecto</label> <input id="fecha_final" name="fecha_final" class="input"
				style="margin: 0;" type="text" maxlength="10"
				data-date-format="dd/mm/yyyy" <% if(logro!=null){ %>
				value="<%= logro.getFecha_final() %>" <% } %> />
		</p>

		<div class="alert alert-info">
			<input name="guardar" type="submit" value="Guardar"
				class="btn btn-primary btn-large">
		</div>
	</form>

</div>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script src="../../bootstrap/datepicker/datepicker.js"
	type="text/javascript"></script>


<script type="text/javascript">
	jQuery('#fecha_final').datepicker();
</script>

<script>
	function showComentario(){
		var e = document.getElementById("id_metainst");
		var value = e.options[e.selectedIndex].value;
		
		jQuery(".comentario"+value).show().siblings('.comentarios').hide();
	}
	
	showComentario();
</script>

<%@ include file="../../cierra_enoc.jsp"%>


