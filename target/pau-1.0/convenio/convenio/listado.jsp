<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.convenio.spring.ConConvenio"%>
<%@page import="aca.convenio.spring.ConArchivo"%>
<%@page import="aca.convenio.spring.ConTipo"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%	
	String estado						= request.getParameter("Estado")==null?"0":request.getParameter("Estado");
    String tipo				     		= request.getParameter("Tipo")==null?"0":request.getParameter("Tipo");
	String codigo						= (String) session.getAttribute("codigoPersonal");
	List<ConConvenio> lisConvenios 		= (List<ConConvenio>)request.getAttribute("lisConvenios");	
	List<ConTipo> listaTipos 			= (List<ConTipo>)request.getAttribute("listaTipos");	
	HashMap<String,ConArchivo> mapa		= (HashMap<String,ConArchivo>)request.getAttribute("mapa");	
	HashMap<String,ConTipo> mapaConTipo	= (HashMap<String,ConTipo>)request.getAttribute("mapaConTipo");	
%>
<body>
<div class="container-fluid">
	<h2>Convenios UM</h2>
	<form name="frmConvenio" action="listado" method="post">	
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="editar"><spring:message code='aca.Nuevo'/></a>
		&nbsp;Estado:&nbsp;
		<select name="Estado" class="form-select" style="width:140px" onchange="document.frmConvenio.submit();">
			<option value="0" <%=estado.equals("0") ? "selected": ""%>>Todos</option>
			<option value="1" <%=estado.equals("1") ? "selected": ""%>>Vigente</option>
			<option value="2" <%=estado.equals("2") ? "selected": ""%>>Indefinido</option>
			<option value="3" <%=estado.equals("3") ? "selected": ""%>>Vencido</option>			
		</select>
		&nbsp;Tipo:&nbsp;	
		<select name="Tipo" class="form-select" style="width:340px" onchange="document.frmConvenio.submit();">
			<option value="0" <%=tipo.equals("0") ? "selected": ""%>>Todos</option>	
<% 		for(ConTipo conTipo : listaTipos){%>
			<option value="<%=conTipo.getTipoId()%>" <%=tipo.equals(conTipo.getTipoId()) ? "selected": ""%>><%=conTipo.getTipoNombre()%></option>
<% 		}%>
		</select>	
		&nbsp;&nbsp;		
		<input type="text" class="form-control search-query" placeholder="Buscar" id="buscar" style="width:200px">
	</div>
	</form>
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
	<tr>
		<th width="2%" class="text-center">Op.</th>
    	<th width="2%" class="text-center"><spring:message code="aca.Numero"/></th>    	
    	<th width="17%" class="text-start"><spring:message code="aca.Nombre"/></th>
    	<th width="5%" class="text-center">Tipo</th>  
    	<th width="7%" class="text-center">F.Firma</th>  
    	<th width="5%" class="text-center">F.Vigencia</th>
    	<th width="5%" class="text-center">Días</th>
    	<th width="5%" class="text-center">Estado</th>
    	<th width="10%" class="text-center">Programa</th>
    	<th width="30%" class="text-center">Objetivo</th>
    	<th width="10%" class="text-center">Documentos</th>
    	<th width="2%" class="text-center">Ver</th>  		  
	</tr>
	</thead>
	<tbody>
<%	
	int row = 0;
	String estadoNombre = "";
	String fechaVigencia = "";
	String tipoNombre = "";
	for (ConConvenio convenio : lisConvenios){
		row++;		
		if(convenio.getEstado().equals("1")){
			estadoNombre = "Vigente";
			fechaVigencia = convenio.getFechaVigencia();
		}else if(convenio.getEstado().equals("2")){
			estadoNombre = "Indefinido";
			fechaVigencia = "-";
		}else if(convenio.getEstado().equals("3")){			
			estadoNombre = "Vencido";
			fechaVigencia = convenio.getFechaVigencia();
		}else{
			estadoNombre = "X";
			fechaVigencia = convenio.getFechaVigencia();
		}
		int dias 		= 0;
		String hoy 		= aca.util.Fecha.getHoy();
		if (!fechaVigencia.equals("-")){
			String fechaTemp[] = fechaVigencia.split("/"); 
			dias = aca.util.Fecha.getDiasEntreFechas(hoy, fechaTemp[2]+"/"+fechaTemp[1]+"/"+fechaTemp[0]);
		}
		
		if(mapaConTipo.containsKey(convenio.getTipoId())){
			tipoNombre = mapaConTipo.get(convenio.getTipoId()).getTipoNombre();
		}
%>
	<tr>
		<td class="center">
			<a href="editar?Id=<%=convenio.getId()%>" title="<spring:message code="aca.Modificar"/>"><i class="fas fa-pencil-alt"></i></a>&nbsp;&nbsp;
<% 			if(!mapa.containsKey(convenio.getId()+"1") && !mapa.containsKey(convenio.getId()+"2") && !mapa.containsKey(convenio.getId()+"3")){%>
			<a href="javascript:Borrar('<%=convenio.getId()%>')" title="<spring:message code="aca.Borrar"/>"><i class="fas fa-trash" ></i></a>
<% 			}%>
		</td>
    	<td class="text-center"><%=row%></td>    	
    	<td class="text-start"><%=convenio.getNombre() %></td>
    	<td class="text-center"><%=tipoNombre%></td>
    	<td class="text-center"><%=convenio.getFechaFirma() %></td>
    	<td class="text-center"><%=fechaVigencia %></td>
<%       if(fechaVigencia.equals("-")){%>	
        <td class="text-center"><span class="badge bg-success rounded-pill"><i class="fas fa-infinity"></i></span></td>
<%       }else if(dias<=0){%>	
        <td class="text-center"><span class="badge bg-danger rounded-pill"><%=dias%></span></td>
<%       }else if(dias>0 && dias<=90){%>	
        <td class="text-center"><span class="badge bg-warning rounded-pill"><%=dias%></span></td>
<% 		 }else if( dias>90){%>	
        <td class="text-center"><span class="badge bg-success rounded-pill"><%=dias%></span></td>
<%  	 }%>
    	<td class="text-center"><%=estadoNombre%></td>
    	<td class="text-start"><%=convenio.getPrograma()%></td>
    	<td class="text-start"><%=convenio.getObjetivo()%></td>
    	<td class="text-center">
    		<a href="imagen?Id=<%=convenio.getId()%>&Folio=1"><span class="<%=mapa.containsKey(convenio.getId()+"1")?"badge bg-success":"badge bg-secondary"%>">1</span></a>
    		 <% if(mapa.containsKey(convenio.getId()+"1")){%>
    		<a href="descargaImagen?IdConvenio=<%=convenio.getId()%>&Folio=1"><i class="fas fa-download"></i></a>&nbsp;
    		<% } %>
    		<a href="imagen?Id=<%=convenio.getId()%>&Folio=2"><span class="<%=mapa.containsKey(convenio.getId()+"2") ? "badge bg-success" : "badge bg-secondary"%>">2</span></a>
    		 <% if(mapa.containsKey(convenio.getId()+"2")){%>
    		<a href="descargaImagen?IdConvenio=<%=convenio.getId()%>&Folio=2"><i class="fas fa-download"></i></a>&nbsp;
    		<% }%>
    		<a href="imagen?Id=<%=convenio.getId()%>&Folio=3"><span class="<%=mapa.containsKey(convenio.getId()+"3") ? "badge bg-success" : "badge bg-secondary"%>">3</span></a>
    		<% if(mapa.containsKey(convenio.getId()+"3")){%>
    		<a href="descargaImagen?IdConvenio=<%=convenio.getId()%>&Folio=3"><i class="fas fa-download"></i></a>&nbsp;
    		<% }%></td>
    		<td class="text-center"> 
    	<% if(mapa.containsKey(convenio.getId()+"1")){%>
    		<a href="ver?ConvenioId=<%=convenio.getId()%>&Folio=1" target="_blank"><i class="fas fa-search"></i></a>
    	<% }else if(mapa.containsKey(convenio.getId()+"2")){%>
    		<a href="ver?ConvenioId=<%=convenio.getId()%>&Folio=2" target="_blank"><i class="fas fa-search"></i></a>
    	<% }else if(mapa.containsKey(convenio.getId()+"3")){%>
    		<a href="ver?ConvenioId=<%=convenio.getId()%>&Folio=3" target="_blank"><i class="fas fa-search"></i></a>
    	<% } %> </td>
	</tr>	
	<%} %>
	</tbody>
	</table>
</div>
</body>
<script src="../../js/search.js"></script>
<script>	
	jQuery('#buscar').focus().search({table:jQuery("#table")});
	
	function Borrar(id){
		if (confirm("¿Estás seguro de borrar el convenio?")){
			document.location.href="borrar?Id="+id;
		}
	}
</script>
</html>