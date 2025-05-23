<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.saum.spring.SaumReceta"%>
<%@page import="aca.saum.spring.SaumEtapa"%>
<%@page import="aca.saum.spring.SaumIngrediente"%>
<%@page import="aca.saum.spring.SaumMateria"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<html>
<head>
<script type="text/javascript">
	function Refrescar(){
		document.frmProceso.submit();
	}
</script>
</head>
<%
	java.text.DecimalFormat formato = new java.text.DecimalFormat("###,##0.00;-###,##0.00");
	
	SaumReceta receta 		= (SaumReceta)request.getAttribute("receta");

	String recetaId			= request.getParameter("RecetaId")==null?"0":request.getParameter("RecetaId");	
	String rendimiento		= request.getParameter("Rendimiento")==null?receta.getRendimiento():request.getParameter("Rendimiento");
	
	ArrayList<SaumEtapa> lisEtapas 				= (ArrayList<SaumEtapa>)request.getAttribute("lisEtapas");
	ArrayList<SaumIngrediente> lisIngredientes 	= (ArrayList<SaumIngrediente>)request.getAttribute("lisIngredientes");
	HashMap<String,SaumMateria> mapaMateria		= (HashMap<String, SaumMateria>)request.getAttribute("mapaMateria");
	HashMap<String,String> mapaIngrediente		= (HashMap<String,String>)request.getAttribute("mapaIngrediente");
%>
<body>
<div class="container-fluid">
	<h2>Etapas de la receta <small class="text-muted fs-4"> ( <%=receta.getNombre()%> ) </small></h2>
	<form name="frmProceso" action="proceso" method="post">
	<input type="hidden" name="RecetaId" value="<%=recetaId%>">
	<div class="alert alert-info">
		<a href="receta" class="btn btn-primary btn-sm"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
		Rendimiento: <input type="text" class="input input-mini" name="Rendimiento" id="Rendimiento" value="<%=rendimiento%>"/>&nbsp;
		<a href="javascript:Refrescar()"class="btn btn-primary btn-sm"><i class="fas fa-sync-alt"></i></a>
	</div>
	</form>
	<table id="table" class="table table-condensed table-bordered">
	<thead>
	<tr>		
		<th width="15%">Etapa</th>		
		<th width="50%">Procedimiento</th>
		<th width="35%">Ingredientes</th>
	</tr>
	</thead>
	<tbody>
<%	
	for(SaumEtapa etapa : lisEtapas){			
%>	
	<tr>		
		<td><%=etapa.getNombre()%></td>		
		<td><%=etapa.getProcedimiento()%></td>			
		<td>
<%		
		for (SaumIngrediente ingrediente : lisIngredientes){
			if (ingrediente.getEtapaId().equals(etapa.getId())){
				String nombreMateria = "";
				if (mapaMateria.containsKey(ingrediente.getMateriaId())){
					nombreMateria = mapaMateria.get(ingrediente.getMateriaId()).getNombre();
				}
				
				String valor = "*";
				if (mapaIngrediente.containsKey(etapa.getId()+ingrediente.getMateriaId())){
					float cantidad 	= 0;
					valor 	= mapaIngrediente.get(etapa.getId()+ingrediente.getMateriaId());
					
					if (valor.contains("g")){
						cantidad = Float.valueOf(valor.replace("g", ""));
						// Convertir a kilogramos
						if (cantidad >= 1000){
							cantidad = cantidad / 1000;
							valor = formato.format(cantidad)+" kg";
						}else{
							// Dejar en gramos
							valor = formato.format(cantidad)+" g";
						}
					}else if (valor.contains("ml")){
						cantidad = Float.valueOf(valor.replace("ml", ""));
						// Convertir a litros
						if (cantidad >= 1000){
							cantidad = cantidad / 1000;
							valor = formato.format(cantidad)+" l";
						}else{
							// Dejar en mililitros
							valor = formato.format(cantidad)+" ml";
						}
					}else{
						cantidad = Float.valueOf(valor.replace("pza", ""));
						valor = formato.format(cantidad)+" pza";
					}					
				}
				
				out.print(nombreMateria+" <span class='badge bg-success'>"+valor+"</span> ( "+ingrediente.getPresentacion()+" )<br>");
			}
 		}
%>
		</td>
	</tr>
<%		
	}
%>	
	</tbody>
	</table>
</div>		
</body>
</html>
