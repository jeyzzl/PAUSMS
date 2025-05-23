<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<html>
<%
	String rolId				= request.getParameter("rolId")==null?"0":request.getParameter("rolId");
	String filtro				= request.getParameter("Filtro")==null?"T":request.getParameter("Filtro");
	String rolNombre			= (String)request.getAttribute("rolNombre");
	String codigoPersonal 		= session.getAttribute("codigoUltimo").toString();
	
	List<aca.menu.spring.Menu> lisMenus				= (List<aca.menu.spring.Menu>) request.getAttribute("lisMenus"); 
	List<aca.menu.spring.Modulo> lisModulos 		= (List<aca.menu.spring.Modulo>) request.getAttribute("lisModulos");
	List<aca.menu.spring.ModuloOpcion> lisOpciones 	= (List<aca.menu.spring.ModuloOpcion>) request.getAttribute("lisOpciones");
	HashMap<String, String> mapaOpcionesMenu		= (HashMap<String, String>) request.getAttribute("mapaOpciones");
	HashMap<String, String> mapaPorModulo			= (HashMap<String, String>) request.getAttribute("mapaPorModulo");
	
%>
	<body>
<div class="container-fluid">
	<h2>Roles <small class="text-muted fs-4">( <%=rolNombre %> )</small> </h2>
	<form name="frmFiltro" action="opciones" method="post">
	<input type="hidden" name="rolId" value="<%=rolId%>"/>
	<div class="alert alert-info d-flex align-items-center">
		<a href="roles" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
		Filter:&nbsp;
		<select name="Filtro" class="form-select" onchange="document.frmFiltro.submit();" style="width:200px;">
			<option value="T" <%=filtro.equals("T")?"selected":""%>>All</option>
			<option value="A" <%=filtro.equals("A")?"selected":""%>>Active</option>				
			<option value="R" <%=filtro.equals("R")?"selected":""%>>In Review</option>
		</select>
	</div>
	</form>
  	<table class="table table-sm table-bordered">
 <%	for(aca.menu.spring.Menu menu : lisMenus){%>
	<thead class="table-info">
		<tr>
			<td style="text-align:center;"><h4><%=menu.getNombreIngles()%></h4></td>
		</tr>
	</thead>
<%			
		for(aca.menu.spring.Modulo modulo : lisModulos){
			if (modulo.getMenuId().equals(menu.getMenuId())){
				String numOptions = "0";
				if (mapaPorModulo.containsKey(modulo.getModuloId() )){
					numOptions = mapaPorModulo.get(modulo.getModuloId());
				}
%>
		<tr>
			<td>
			<a href="javascript:Mostrar('<%=modulo.getModuloId()%>')" style="color:black; font-size:14px;"><i class="fas fa-plus-circle fa-lg"></i></a>
			<%=modulo.getNombreIngles	()%>&nbsp;&nbsp;
			Options: <%=numOptions.equals("0")?"<span class='badge bg-warning rounded-pill'>0</span>":"<span class='badge bg-dark rounded-pill'>"+numOptions+"</span>"%>&nbsp; &nbsp;
			<a href="javascript:Todos('<%=modulo.getModuloId()%>');" class="btn btn-success btn-sm"><spring:message code='aca.Todos'/></a>
		  	&nbsp;/&nbsp;
		  	<a href="javascript:Ninguno('<%=modulo.getModuloId()%>');" class="btn btn-danger btn-sm">None</a>			
			</td>
		</tr>
		<tr>
			<td>
				<table style="width:100%" class="well<%=modulo.getModuloId()%> well table table-bordered table-sm table-striped">
				<thead>
					<th width="5%">Choose</th>
					<th width="20%">Option</th>
					<th width="10%">Status</th>
					<th width="65%">Comment</th>
				</thead>
<%	
				int row=-1;
				for( aca.menu.spring.ModuloOpcion opcion : lisOpciones){
					if (opcion.getModuloId().equals(modulo.getModuloId())){      
						row++;					
				%>									
				<tr>				
					<td>
					<%
						boolean checar = false;
						if(mapaOpcionesMenu.containsValue(opcion.getOpcionId())){											
							checar = true;
						}
						
						String active = "<span class='m-1 badge bg-dark rounded-pill'>Active</span> ";
						if(opcion.getIcono().equals("I")){
							active = "<span class='m-1 badge bg-danger rounded-pill'>Inactive</span>";
						}else if (opcion.getIcono().equals("R")){
							active = "<span class='m-1 badge bg-primary rounded-pill'>In Review</span>";
						}						 
					%>
						<input name="chk<%=opcion.getOpcionId()%>" id="<%=opcion.getOpcionId()%>" class="<%=modulo.getModuloId()%>" type="checkbox" <%=checar?"Checked":""%>>&nbsp;
					</td>					
					<td><%=opcion.getNombreIngles()%></td>
					<td><%=active%></td>
					<td><%=opcion.getUsuarios()%></td>
				</tr>	
			<%		}
				}%>
				</table>
			</td>
		</tr>
<%			}
		}%>
<%	}%>
  	</table>
  	<table style="position:relative; top:50%;">
  		<tr> 
			<td align="center" id="grabar"> 
				<input type="button" value="Save" class="btn btn-primary" onclick="grabar();">
			</td>
    	</tr>
  	</table>
  	<br>
  	<br>
</div>
</body>
<script>
	function grabar(){
		$("#grabar").html("<img src='../../imagenes/loading.gif'>");
		var agregados = "";
		$("input:checkbox[name^='chk']").each(function(){
			var chk = $(this);
			if(chk.is(':checked')){
				console.log($("input:checkbox[name^='chk']"));
				agregados+=(chk.attr("id")+",");
			}
		});
		var rolId = <%=rolId%>
		jQuery.get("accion?Agregados="+agregados+"&rolId="+rolId)
		$("#grabar").html('<input type="button" value="Saved" class="btn btn-success" onclick="grabar();">');
		setTimeout(function() {$("#grabar").html('<input type="button" value="Save" class="btn btn-primary" onclick="grabar();">');},500);
	}
	
	$(document).ready(function(){
		$("table[class^='well']").toggle();
	});

	function Mostrar(clase){
		$('.well'+clase).slideToggle();		
	}

	function Todos(clase){
		$("."+clase).prop('checked',true);
	}
	
	function Ninguno(clase){			
		$("."+clase).prop('checked',false);
	}
</script>
</html>