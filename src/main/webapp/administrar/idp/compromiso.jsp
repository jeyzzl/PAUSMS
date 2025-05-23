<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.vista.spring.Maestros"%>
<%@page import="aca.portafolio.spring.PorCompromiso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script type = "text/javascript">
	function Enviar(codigoPersonal, periodoId){
		if(confirm("Estas seguro de enviar el registro!")==true){
  			document.location.href="enviarEstado?CodigoPersonal="+codigoPersonal+"&PeriodoId="+periodoId+"&Estado=A";
		}			
	}	
</script>

<% 
	String periodoId 			            = aca.util.Fecha.getHoy().substring(6,10);
	List<Maestros> lisMaestros 			    = (List<Maestros>) request.getAttribute("lisMaestros");	 
	
	HashMap<String,PorCompromiso> mapaCompromiso	= (HashMap<String,PorCompromiso>) request.getAttribute("mapaCompromiso");
	HashMap<String,String> mapaEducar 	    		= (HashMap<String,String>) request.getAttribute("mapaEducar");
	HashMap<String,String> mapaModelar  		 	= (HashMap<String,String>) request.getAttribute("mapaModelar");
	HashMap<String,String> mapaInvestigar 			= (HashMap<String,String>) request.getAttribute("mapaInvestigar");
	HashMap<String,String> mapaServir 	  		  	= (HashMap<String,String>) request.getAttribute("mapaServir");
	HashMap<String,String> mapaProclamar		 	= (HashMap<String,String>) request.getAttribute("mapaProclamar");
	HashMap<String,String> mapaEsperanza 			= (HashMap<String,String>) request.getAttribute("mapaEsperanza");
%>
<body>
<div class="container-fluid">
	<h2>IDP 2021<small class="text-muted fs-5">( Compromise )</small></h2>
	<div class="alert alert-info">
		<input type="text" class="form-control search-query" placeholder="Search" id="buscar" style="width:200px;">
	</div>
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">
	<tr>
		<th>#</th>
		<th>Id</th>
		<th>Employee</th>
		<th class="text-end">Educate</th>
		<th class="text-end">Model</th>
	    <th class="text-end">Investigate</th>
	    <th class="text-end">Serve</th>
        <th class="text-end">Proclaim</th>
	    <th class="text-end">Hope</th>
		<th class="text-center">State</th>
		<th>Date</th>
		<th class="text-center">Pdf</th>
	</tr>
	</thead>
	<tbody>
<%	
	int row = 0; 
	for (Maestros maestro : lisMaestros){
		row++;
		
		String educar 	 = "0"; 
		if (mapaEducar.containsKey(maestro.getCodigoPersonal()+periodoId)){
			educar 		 = mapaEducar.get(maestro.getCodigoPersonal()+periodoId);
		}
		
		String modelar 	 = "0"; 
		if (mapaModelar.containsKey(maestro.getCodigoPersonal()+periodoId)){
			modelar 	 = mapaModelar.get(maestro.getCodigoPersonal()+periodoId);
		}
		String investigar= "0"; 
		if (mapaInvestigar.containsKey(maestro.getCodigoPersonal()+periodoId)){
			investigar	 = mapaInvestigar.get(maestro.getCodigoPersonal()+periodoId);
		}
		String servir 	 = "0"; 
		if (mapaServir.containsKey(maestro.getCodigoPersonal()+periodoId)){
			servir 		= mapaServir.get(maestro.getCodigoPersonal()+periodoId);
		}
		String proclamar = "0"; 
		if (mapaProclamar.containsKey(maestro.getCodigoPersonal()+periodoId)){
			proclamar 	= mapaProclamar.get(maestro.getCodigoPersonal()+periodoId);
		}
		String esperanza = "0"; 
		if (mapaEsperanza.containsKey(maestro.getCodigoPersonal()+periodoId)){
			esperanza	= mapaEsperanza.get(maestro.getCodigoPersonal()+periodoId);
		}		
		
		PorCompromiso porCompromiso = new PorCompromiso();
		if (mapaCompromiso.containsKey(maestro.getCodigoPersonal()) ){
			porCompromiso 		= mapaCompromiso.get(maestro.getCodigoPersonal());
		}
%>		
	<tr>
		<td><%=row%></td>
		<td><%=maestro.getCodigoPersonal()%></td>
		<td><%=maestro.getApellidoPaterno()%> <%=maestro.getApellidoMaterno() %> <%=maestro.getNombre()%></td>
		<td class="text-end"><%=educar%></td>
		<td class="text-end"><%=modelar%></td>
		<td class="text-end"><%=investigar%></td>
		<td class="text-end"><%=servir%></td>
		<td class="text-end"><%=proclamar%></td>
		<td class="text-end"><%=esperanza%></td>				
		<td class="text-center"><%=porCompromiso.getEstado().equals("A")?"<span class='badge bg-success'>Capture</span>":"<span class='badge bg-dark'>Sent</span>"%>
<% 		if(porCompromiso.getEstado().equals("E")){%>
			<a href="javascript:Enviar('<%=maestro.getCodigoPersonal()%>&PeriodoId=<%=periodoId%>')"><i class="fas fa-undo-alt"></i></a>
<% 		}	%>
		</td>
		<td><%=porCompromiso.getFecha()%></td>		
		<td class="text-center"><a href="../../portales/portafolio/compromisoPdf?CodigoPersonal=<%=maestro.getCodigoPersonal()%>&PeriodoId=<%=periodoId%>"><i class="fas fa-file-pdf"></i></a></td>
	</tr>
<%	} %>
	</tbody>
</div>
</body>
<script src="../../js/search.js"></script>
<script>
	jQuery('#buscar').focus().search({table:jQuery("#table")});
</script>

