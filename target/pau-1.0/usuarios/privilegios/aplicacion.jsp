<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.vista.spring.Usuarios"%>

<%@ include file= "id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>
<html>
<%
	String filtro			= request.getParameter("Filtro")==null?"T":request.getParameter("Filtro");
	String codigoPersonal	= session.getAttribute("codigoUltimo").toString();
	String nombre			= (String)request.getAttribute("nombre");
	
	List<aca.menu.spring.Menu> lisMenus 	 		= (List<aca.menu.spring.Menu>)request.getAttribute("lisMenus");	
	List<aca.menu.spring.Modulo> lisModulos 	 	= (List<aca.menu.spring.Modulo>)request.getAttribute("lisModulos");
	List<aca.menu.spring.ModuloOpcion> lisOpciones 	= (List<aca.menu.spring.ModuloOpcion>)request.getAttribute("lisOpciones");
	
	HashMap<String,String> mapaTotales				= (HashMap<String,String>)request.getAttribute("mapaTotales");
	HashMap<String,String> mapaAsignados			= (HashMap<String,String>)request.getAttribute("mapaAsignados");
	HashMap<String,String> mapaOpciones				= (HashMap<String,String>)request.getAttribute("mapaOpciones");
%>
<body>
<div class="container-fluid">
	<h2>Application Privileges<small class="text-muted fs-5"> ( <%=codigoPersonal%> - <%=nombre%> )</small></h2>
	<form name="frmFiltro" action="aplicacion" method="post">
	<div class="alert alert-info d-flex align-items-center">
		<a href="usuario" class="btn btn-primary btn-sm"><spring:message code="aca.Regresar"/></a>&nbsp;
		Filter:&nbsp;
		<select name="Filtro" class="form-select" onchange="document.frmFiltro.submit();" style="width:200px;">
			<option value="T" <%=filtro.equals("T")?"selected":""%>>All</option>
			<option value="A" <%=filtro.equals("A")?"selected":""%>>Active</option>				
			<option value="R" <%=filtro.equals("R")?"selected":""%>>In Review</option>
		</select>	
	</div>
	</form>
	<div class="col-xs-9">
  	<table class="table table-sm" >
<%	for(aca.menu.spring.Menu menu : lisMenus){%>
  		<tr class="alert alert-success">
			<td style="text-align:center;"><h3><%=menu.getNombreIngles()%></h3></td>
		</tr>				
<%		
		for(aca.menu.spring.Modulo modulo : lisModulos){
			if (modulo.getMenuId().equals(menu.getMenuId())){
				
				String total = "0";
				if (mapaTotales.containsKey(modulo.getModuloId())){
					total = mapaTotales.get(modulo.getModuloId());
				}
				
				String asignados = "0";
				if (mapaAsignados.containsKey(modulo.getModuloId())){
					asignados = mapaAsignados.get(modulo.getModuloId());
				}				
%>
		<tr>			
			<td class="text-left" onclick="jQuery('.well<%=modulo.getModuloId()%>').slideToggle();">
				<span class="badge bg-<%=asignados.equals("0")?"warning":"success"%>"><%=asignados%></span> of <span class="badge bg-dark"> <%=total%></span>&nbsp;&nbsp;<%=modulo.getNombreIngles()%>
			</td>
		</tr>
		<tr>			
			<td>
			<table style="width:100%" class="well<%=modulo.getModuloId()%> well table table-bordered table-sm table-striped">
			<thead>
				<th width="10%">Status</th>				
				<th width="20%">Option</th>				
				<th width="70%">Comment</th>					
			</thead>
		<%	
			for(int i=0; i<lisOpciones.size(); i++){
				aca.menu.spring.ModuloOpcion opcion = lisOpciones.get(i);
				
				String active = "<span class='m-1 badge bg-success rounded-pill'>Active</span> ";
				if(opcion.getIcono().equals("I")){
					active = "<span class='m-1 badge bg-danger rounded-pill'>Inactive</span>";
				}else if (opcion.getIcono().equals("R")){
					active = "<span class='m-1 badge bg-warning rounded-pill'>In Review</span>";
				}
				
				if (opcion.getModuloId().equals(modulo.getModuloId())){								
					out.print("<tr>");			
		%>			
				<td><%=active%></td>	
				<td width="30%">
					<input name="chk<%=opcion.getOpcionId()%>" id="<%=opcion.getOpcionId()%>" type="checkbox" <%=mapaOpciones.containsKey(opcion.getOpcionId())?"Checked":""%>>&nbsp;
					<%=opcion.getNombreIngles()%>
				</td>				
				<td><%=opcion.getUsuarios()%></td>				
		<%			
					out.print("</tr>");				
				}
			}
		%>
			</table>
			</td>
		</tr>
<%			
			}
		}		
	}
%>
  	</table>
  	<div class="alert alert-info">
  		<a href="javascript:grabar()" id="Grabar" class="btn btn-primary">Save</a>&nbsp; &nbsp;<span id="Respuesta"></span>
	</div>
  	</div>
</div> 		
</body>
	<script>
	
		jQuery("table[class^='well']").hide();
		
		function grabar(){			
			var agregados = "";	
			jQuery("input:checkbox[name^='chk']").each(function(){
				var chk = jQuery(this);
				if(chk.is(':checked')){
					agregados+=(chk.attr("id")+"-->");
				}
			});
			
			jQuery.get("accion?Agregados="+agregados, function(r){
				var datos = jQuery.trim(r);				
				jQuery("#Respuesta").html(datos);
			});
			jQuery("#Grabar").removeClass("btn-primary");			
			jQuery("#Grabar").html("<img src='../../imagenes/loading.gif'>");			
			jQuery("#Grabar").addClass("btn-success");
			
			setTimeout(function() {
				jQuery("#Grabar").removeClass("btn-success");
				jQuery("#Grabar").html("Save");
				jQuery("#Grabar").addClass("btn-primary");
			},200);
		}
		
		jQuery(document).ready(function(){			
			//jQuery("table[class^='well']").toggle();
		});
	</script>	
</html>