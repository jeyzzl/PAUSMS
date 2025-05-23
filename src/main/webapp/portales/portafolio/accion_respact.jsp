<%@ include file="../../con_enoc.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.proyectos.DptoActividades"%>
<%@page import="aca.proyectos.OPActividad"%>
<%@page import="aca.proyectos.OPResponsableAct"%>
<%@page import="aca.proyectos.DptoProyectos"%>
<%@page import="java.util.Iterator"%>
<%@page import="aca.proyectos.OpResponsableProy"%>
<%@page import="java.util.List"%>
<%@page import="aca.proyectos.DptoPersonal"%>
<%@page import="aca.proyectos.OPProyectos"%>
<%@page import="aca.proyectos.OPMetas"%>
<%@page import="aca.proyectos.DatosEncabezado"%>


<%

    if (request.getParameter("accion") == null) {
        response.sendRedirect("actividad.jsp");
    }
    String returnpage="actividad.jsp";
    if(request.getParameter("returnpage")!=null){
        returnpage = request.getParameter("returnpage");
        if(!returnpage.contains(".jsp?")){
        	returnpage +="?logroid="+request.getParameter("logroid");
        }
    }
    
    if (request.getParameter("guardar") != null) {
        OPResponsableAct op = new OPResponsableAct(conEnoc);
        if (request.getParameter("accion").equals("agregar")) {
            op.addResponsable( request);
        }else if(request.getParameter("accion").equals("editar")){
            
        }            
        response.sendRedirect(returnpage);
    }
    
%>

<div class="container-fluid">

	<h2>Responsables</h2>

	<div class="alert alert-info">
		<a href="<%= returnpage %>" class="btn btn-primary"><i
			class="icon-arrow-left icon-white"></i>
		<spring:message code='aca.Regresar' /></a>
	</div>

	<form action="" method="post" name="accion_meta">
		<input name="accion" type="hidden" id="accion"
			value="<%= request.getParameter("accion")%>"> <input
			name="TKL_id_actividad" type="hidden" id="TKL_id_actividad"
			value="<%= request.getParameter("TKL_id_actividad")%>"> <input
			name="returnpage" type="hidden" id="accion" value="<%= returnpage %>">

		<% 
          OpResponsableProy opres = new OpResponsableProy(conEnoc);
        %>

		<p>
			<label for="clave">Seleccione un empleado</label> <select
				name="clave" id="clave" class="input-xxlarge">
				<optgroup label="Personal depto">
					<%
	          		Iterator<DptoPersonal> itPersonal = opres.listaEmpleadosDpto((String)request.getParameter("ccosto"), "001-2014").iterator();
	          		while(itPersonal.hasNext()){
	              		DptoPersonal personal= itPersonal.next();
	            %>
					<option value="<%= personal.getClave() %>"><%= personal.getAppaterno() %>
						<%= personal.getApmaterno() %>,
						<%= personal.getNombre() %></option>
					<%
	          		}
	            %>
				</optgroup>
				<optgroup label="Otro personal">
					<% 
	          		Iterator<DptoPersonal> itPersonalG = opres.listaEmpleadosGeneral( (String)request.getParameter("ccosto"), "001-2014").iterator();
	          		while(itPersonalG.hasNext()){
	              		DptoPersonal personal= itPersonalG.next();
	           	%>
					<option value="<%= personal.getClave() %>"><%= personal.getAppaterno() %>
						<%= personal.getApmaterno() %>,
						<%= personal.getNombre() %>
					</option>
					<%
	          		}                 
	           	%>
				</optgroup>
			</select>
		</p>

		<p>
			<label for="cargo">Cargo</label> <input type="text" name="cargo"
				id="cargo">
		</p>

		<div class="alert alert-info">
			<input name="guardar" type="submit" value="Guardar"
				class="btn btn-primary btn-large">
		</div>
	</form>

</div>

<link rel="stylesheet" href="../../js/chosen/chosen.css" />
<script src="../../js/chosen/chosen.jquery.js" type="text/javascript"></script>
<script type="text/javascript"> 
	jQuery(".chosen").chosen(); 
</script>

<%@ include file="../../cierra_enoc.jsp"%>