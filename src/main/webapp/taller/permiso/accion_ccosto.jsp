<%@page import="aca.financiero.ContCcosto"%>

<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import= "java.util.* "%>

<jsp:useBean id="BecAccesoU" scope="page" class="aca.bec.BecAccesoUtil"/>
<jsp:useBean id="ContCcostoU" scope="page" class="aca.financiero.ContCcostoUtil"/>
<jsp:useBean id="BecPlazasU" scope="page" class="aca.bec.BecPlazasUtil"/>
<jsp:useBean id="BecFija" scope="page" class="aca.bec.BecFija"/>
<jsp:useBean id="BecFijaU" scope="page" class="aca.bec.BecFijaUtil"/>
<script>

		function elegirTodos(){
			var elegir = jQuery("#elegir");
			if (elegir.html()=="Todos"){
				jQuery(".depto").attr("checked",true);
				elegir.html("Ninguno");
			}else{				
				jQuery(".depto").attr("checked",false);
				elegir.html("Todos");
			}	
		}

		function modificarDepartamentos(ejercicioId, tipo){
			document.frmDepartamentos.Accion.value = "1";
			document.frmDepartamentos.submit();			
		}
		
</script>

<%

	//String ejercicioId							= (String)session.getAttribute("ejercicioId");
	String ejercicioId								= request.getParameter("EjercicioId");
	String accion 									= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String tipo 									= request.getParameter("Tipo");	
	String opcion									= request.getParameter("Opcion")==null?"0":request.getParameter("Opcion");
	
	String usuarios 								= "";
	int cont 										= 1;
	
	ArrayList <aca.financiero.ContCcosto> lisDeptos	= ContCcostoU.getListCentrosCosto(conEnoc, ejercicioId,"'S'","ORDER BY ID_CCOSTO");
	ArrayList <String> seleccionados 				= BecFijaU.getListCentros(conEnoc, ejercicioId, "");
	
	 
	if (accion.equals("1")){
		for(aca.financiero.ContCcosto depto : lisDeptos){
				BecFija.setIdEjercicio(ejercicioId);
				BecFija.setIdCcosto(depto.getIdCcosto());
				if(!BecFijaU.existeReg(conEnoc, ejercicioId, depto.getIdCcosto())){
					if(request.getParameter(depto.getIdCcosto())!=null){
						BecFija.setFecha(aca.util.Fecha.getHoy());
						BecFija.setUsuario((String)session.getAttribute("codigoPersonal"));
						BecFijaU.insertReg(conEnoc, BecFija);					
					}
				}else{
					if(request.getParameter(depto.getIdCcosto()) == null){
						BecFijaU.deleteReg(conEnoc, ejercicioId, depto.getIdCcosto());
					}
				}
		}
		seleccionados 	= BecFijaU.getListCentros(conEnoc, ejercicioId, "");
	} 
%>

<html>
<head>
	<style>
		.empleados:hover{
			font-weight: bold;
		}
 	body{
 		background : white;
 	}
	</style>
	<body>
		<div class="container-fluid">
			<h1>Centros de Costo</h1>
			<div class="alert alert-info">
			<a href="fija?ejercicioId=<%=ejercicioId%>" class="btn btn-primary" align="center"><i class="icon-arrow-left icon-white"></i><spring:message code='aca.Regresar'/></a>			
			
			<a class="btn btn-success" onclick="modificarDepartamentos(<%=ejercicioId%>,<%=tipo%>);" ><i class="icon-ok icon-white"></i> Guardar centros de costo</a>
			
			<a class="btn btn-primary" href="accion_ccosto?EjercicioId=<%=ejercicioId%>&Tipo=<%=tipo%>&Opcion=<%=opcion.equals("0")?"1":"0"%>">
			  <%=opcion.equals("0")?"Mostrar Todos":"Mostrar elegidos"%>
			</a>
			</div>
			<form name="frmDepartamentos" accion="accion_ccosto">
			<input type="hidden" name="EjercicioId" value="<%=ejercicioId%>">
			<input type="hidden" name="Tipo" value="<%=tipo%>">
			<input type="hidden" name="Accion" value="<%=accion%>">
			
			<table class="table  table-bordered " align="center">
				<thead>
					<tr>
						<th width="10%" align="center"><a id="elegir" class="btn btn-small btn-info" onclick="javascript:elegirTodos();" style="cursor:pointer; font-size:12px;">Todos</a></th>					
						<th width="15%">Costo</th>
						<th width="50%"><spring:message code="aca.Nombre"/></th>
					</tr>
				</thead>
				<tbody>
						
<%	for(aca.financiero.ContCcosto depto : lisDeptos){
		if(!seleccionados.contains(depto.getIdCcosto()) && opcion.equals("0") ){
			continue;
		}		
%>
				<tr>					
					<td style="text-align:center;"><input type="checkbox" class="depto" id="<%=depto.getIdCcosto()%>" name="<%=depto.getIdCcosto()%>" value="S" <%if(seleccionados.contains(depto.getIdCcosto())){%>checked<%} %> /></td>					
					<td align="center"><%=depto.getIdCcosto() %></td>
					<td align="center"><%=depto.getNombre()%></td>
				</tr>				
<% 		cont++;
	}
%>
				</tbody>
			</table>
			</form>
		</div>
	</body>
</html>
<%@ include file= "../../cierra_enoc.jsp" %>