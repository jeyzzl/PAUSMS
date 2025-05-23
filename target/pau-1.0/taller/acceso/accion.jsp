<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import= "java.util.* "%>

<jsp:useBean id="Usuario" scope="page" class="aca.vista.Usuarios"/>
<jsp:useBean id="BecAcceso" scope="page" class="aca.bec.BecAcceso"/>
<jsp:useBean id="BecAccesoU" scope="page" class="aca.bec.BecAccesoUtil"/>
<jsp:useBean id="ContCcostoU" scope="page" class="aca.financiero.ContCcostoUtil"/>

<%
	String codigoPersonal				= (String)session.getAttribute("codigoPersonal");
	String ejercicioId					= (String)session.getAttribute("ejercicioId");
	String codigo						= (String)session.getAttribute("codigoEmpleado");
	
	String filtro						= request.getParameter("Filtro")==null?"1.01":request.getParameter("Filtro");
	String accion 						= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String mostrar  					= request.getParameter("mostrar")==null?"":request.getParameter("mostrar");
	
	String deptosUsuario 				= aca.bec.BecAccesoUtil.getUsuarioCentrosCosto(conEnoc,ejercicioId, codigo);
	String nombreUsuario				= "";
	String resultado					= "Indefinido...";
	int row								= 0;
	
	Usuario.setCodigoPersonal(codigo);
	if (Usuario.existeReg(conEnoc)){
		Usuario.mapeaRegId(conEnoc, codigo);
		nombreUsuario	= Usuario.getNombre()+" "+Usuario.getApellidoPaterno()+" "+Usuario.getApellidoMaterno();
	}else{
		nombreUsuario = "¡ No existe !";
	}
	
	ArrayList<aca.financiero.ContCcosto> lisCcosto		= ContCcostoU.getListAcceso(conEnoc, ejercicioId, codigo, filtro, "ORDER BY ID_CCOSTO");
	HashMap<String, aca.financiero.ContCcosto> mapCcosto 	= aca.financiero.ContCcostoUtil.getMapCentrosCosto(conEnoc, ejercicioId);
	
	String marcado = "";
	if (accion.equals("1")){
		// Recorre el arreglo y graba y borra los privilegios
		for (int i=0; i<lisCcosto.size();i++){
			aca.financiero.ContCcosto depto = (aca.financiero.ContCcosto) lisCcosto.get(i);
			if (request.getParameter(depto.getIdCcosto())==null) marcado = "N"; else marcado = "S";		
			
			// Grabar el privilegio
			if (marcado.equals("S") && deptosUsuario.contains(depto.getIdCcosto())==false){
				BecAcceso.setCodigoPersonal(codigo);
				BecAcceso.setIdEjercicio(ejercicioId);
				BecAcceso.setIdCcosto(depto.getIdCcosto());
				BecAcceso.setFecha(aca.util.Fecha.getHoy());
				BecAcceso.setUsuario(codigoPersonal);
				if (BecAccesoU.insertReg(conEnoc, BecAcceso)){
				}
			}
			
			// Borrar el privilegio
			if (marcado.equals("N") && deptosUsuario.contains(depto.getIdCcosto()) == true){
				BecAcceso.setCodigoPersonal(codigo);
				BecAcceso.setIdEjercicio(ejercicioId);
				BecAcceso.setIdCcosto(depto.getIdCcosto());
				if (BecAccesoU.deleteReg(conEnoc, codigo, ejercicioId, depto.getIdCcosto())){
				}
			}		
		}
		
		// Actualizar la lista de privilegios del empleado
		deptosUsuario = aca.bec.BecAccesoUtil.getUsuarioCentrosCosto(conEnoc,ejercicioId, codigo);
	} 
%>
<style>
	body{
		background:white;
	}
</style>

<div class="container-fluid">
	<h2>Privilegios del Usuario <small class="text-muted fs-4"><%=codigo%> | <%=nombreUsuario%></small></h2>	
	<form name="frmFiltro" action="accion.jsp">
	<input name="Accion" type="hidden" value="<%=accion%>"/>
	<input name="mostrar" type="hidden" value="<%=mostrar %>" />
	<div class="alert alert-info">		
			Filtro: <input name="Filtro" id="Filtro" value="<%=filtro%>"/>
			<a href="javascript:Filtrar();" class="btn btn-primary"><i class="icon-filter icon-white"></i> Filtrar</a>
			<a href="javascript:Grabar();" class="btn btn-primary"><i class="icon-ok icon-white"></i> Grabar</a>
			<a href="usuario.jsp" class="btn btn-primary"><i class="icon-chevron-left icon-white"></i><spring:message code='aca.Regresar'/></a>	
			<%
				if(mostrar.equals("")){
			%>
					<a href="accion.jsp?mostrar=S&Filtro=<%=filtro %>" class="btn btn-info"><i class="icon-plus icon-white"></i> Mostrar Todos</a>
			<%
				}else{
			%>
					<a href="accion.jsp?Filtro=<%=filtro %>" class="btn btn-info"><i class="icon-minus icon-white"></i> Mostrar Elegidos</a>			
			<%
				}
			%>
	</div>
	<table class="table table-condensed table-nohover table-striped" >
		<tr>
			<th width="5%">#</th>
			<th width="15%">Ccosto</th>
			<th width="50%"><spring:message code="aca.Nombre"/></th>	
					
		</tr>
		
<%	
	String deptoTemp	= "";	
	for (int i=0; i<lisCcosto.size();i++){
		aca.financiero.ContCcosto depto = (aca.financiero.ContCcosto) lisCcosto.get(i);
		
		// Revisa si el usuario tiene privilegios en el centro de costo
		marcado = "";
		if (deptosUsuario.indexOf( "-"+depto.getIdCcosto()+"-" )>-1) marcado = "checked"; else marcado = "";
		
		if(mostrar.equals("") && marcado.equals("")){
			continue;
		}
		
		// obtiene un arreglo de los niveles
		String [] niveles 	= depto.getIdCcosto().split("\\.");
		String dep 			= niveles[0]+niveles[1]+niveles[2]+niveles[3];
		
		// Si es un titulo de ccosto diferente al anterior busca su nombre
		if (!dep.equals(deptoTemp)){
			
			StringBuilder ccosto	= new StringBuilder();
			StringBuilder titulo 	= new StringBuilder();
			String separador 		= "";
			
			for (int j=0;j<4;j++){
				
				if (j>0){
					ccosto.append("."+niveles[j]);
					separador = " | ";
				}else{
					ccosto.append(niveles[j]);
					separador = "";
				}
				
				// Si el map contiene el centro de costo trae las iniciales(si existen) o el nombre.
				if (mapCcosto.containsKey(ccosto.toString())){
						// Busca el nombre corto del centro de costo
						if (!mapCcosto.get(ccosto.toString()).getIniciales().equals("INIT"))
							titulo.append(separador+mapCcosto.get(ccosto.toString()).getIniciales());
						else
							titulo.append(separador+mapCcosto.get(ccosto.toString()).getNombre());
				}		
			}
			// Imprime un renglon con el titulo
			out.println("<tr><td colspan='3'><b>"+titulo.toString()+"</b></td></tr>");
			
			// Actualiza el departamento
			deptoTemp = dep;
		}	
%>		
		<tr>
			<td><input type="checkbox" name="<%=depto.getIdCcosto()%>" value="S" <%=marcado%>></td>
			<td><%=depto.getIdCcosto()%></td>
			<td><%=depto.getNombre()%></td>			
		</tr>
<%	} %>		
	</table>
	</form>
</div>


<script type="text/javascript">

	function Filtrar() {		
		if (frmFiltro.Filtro.value != "") {	
			document.frmFiltro.submit();
		}else{
			alert("¡El filtro no puede estar vacío!");
		}
	}
	
	function Grabar() {
		document.frmFiltro.Accion.value = "1";
		document.frmFiltro.submit();		
	}
	
</script>
<%@ include file= "../../cierra_enoc.jsp" %>