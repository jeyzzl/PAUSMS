<%@ page import= "java.util.* "%>
<%@ page import= "aca.bec.spring.BecAcceso"%>
<%@ page import= "aca.financiero.spring.ContCcosto"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%
	String codigoPersonal					= (String)session.getAttribute("codigoPersonal");
	String ejercicioId						= (String)session.getAttribute("ejercicioId");
	String codigoEmpleado					= (String)session.getAttribute("codigoEmpleado");
	
	String filtro							= request.getParameter("Filtro")==null?"1.01":request.getParameter("Filtro");	
	String mostrar  						= request.getParameter("mostrar")==null?"":request.getParameter("mostrar");
	
	String deptosUsuario 					= (String)request.getAttribute("deptosUsuario");
	String usuarioNombre					= (String)request.getAttribute("usuarioNombre");	
	int row									= 0;
	
	List<ContCcosto> lisCcostos				= (List<ContCcosto>)request.getAttribute("lisCcostos");
	HashMap<String, ContCcosto> mapCcosto 	= (HashMap<String, ContCcosto>)request.getAttribute("mapCcosto");
%>
<style>
	body{
		background:white;
	}
</style>

<div class="container-fluid">
	<h2>Privilegios del Usuario <small class="text-muted fs-4"><%=codigoEmpleado%> | <%=usuarioNombre%></small></h2>
	<br />
	<form name="frmFiltro" action="grabar" method="post">
	<input name="mostrar" type="hidden" value="<%=mostrar%>" />
	<div class="alert alert-info">
		<a href="usuario" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
		Filtro: <input name="Filtro" id="Filtro" value="<%=filtro%>" size="5" />
		<a href="javascript:Filtrar();" class="btn btn-primary"><i class="fas fa-filter"></i> Filtrar</a>						
<%	if(mostrar.equals("")){ %>
		<a href="editar?mostrar=S&Filtro=<%=filtro %>" class="btn btn-info"><i class="fas fa-plus"></i> Mostrar Todos</a>
<%	}else{ %>
		<a href="editar?Filtro=<%=filtro %>" class="btn btn-info"><i class="fas fa-minus"></i>Mostrar Elegidos</a>			
<%	} %>
		&nbsp;&nbsp;
		<a href="javascript:Grabar();" class="btn btn-primary"><i class="fas fa-check"></i> Grabar</a>
	</div>
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
	<tr>
		<th width="5%">#</th>
		<th width="15%">Ccosto</th>
		<th width="50%"><spring:message code="aca.Nombre"/></th>				
	</tr>
	</thead>
		
<%	
	String deptoTemp	= "";	
	for(ContCcosto depto : lisCcostos){	
		
		// Revisa si el usuario tiene privilegios en el centro de costo
		String marcado = "";
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
	
	function Grabar(){
		document.frmFiltro.submit();		
	}	
</script>