<%@page import="java.util.*"%>
<%@page import="aca.catalogo.spring.CatPeriodo"%>
<%@page import="aca.catalogo.spring.CatModalidad"%>
<%@page import="aca.carga.spring.Carga"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%	
	String periodo 			= (String)request.getAttribute("periodo");
	String periodoPas 		= (String)request.getAttribute("periodoPas");
	String modalidades 		= (String)request.getAttribute("modalidades");	
	
	List<CatPeriodo> lisPeriodos 					= (List<CatPeriodo>)request.getAttribute("lisPeriodos");	 
	List<Carga> lisCargas 							= (List<Carga>)request.getAttribute("lisCargas");	 
	List<Carga> lisCargasPas  						= (List<Carga>)request.getAttribute("lisCargasPas");
	String[] lisModo 								= (String[]) request.getAttribute("lisModo");
	
	HashMap<String, CatModalidad> mapaModalidades 	= (HashMap<String, CatModalidad>) request.getAttribute("mapaModalidades");
%>
<head>
<script type="text/javascript" src="../../js/prototype.js"></script>
<script type="text/javascript">
	function enviarArchivo( Opcion ){
			var alumnos = "";
			var cont = "";
			for(var i = 0; i < <%=lisCargas.size()%>; i++){
				if($("envia-alumno-"+i).checked){
					cont++;
					if(alumnos != "")
						alumnos += ",";
					alumnos += "'" + $("envia-alumno-"+i).value+"'";
				}
			}
			for(var i = 0; i < <%=lisCargasPas.size()%>; i++){
				if($("envia-alumnos-"+i).checked){
					cont++;
					if(alumnos != "")
						alumnos += ",";
					alumnos += "'" + $("envia-alumnos-"+i).value+"'";
				}
			}
			
			if(alumnos != ""){
				$("alumnos").value = alumnos;
				
				if(Opcion=='3'){
					jQuery('#frmCargas').attr('action','actual');
				}else if (Opcion==2){
					jQuery('#frmCargas').attr('action','historico');
				}else{
					jQuery('#frmCargas').attr('action','accion');
				}
				
				document.frmCargas.Opcion.value = Opcion;
				document.frmCargas.submit()
			}else
				alert("Seleccione alguna carga a ver");
	}
	
	function seleccionaTodos(cantidadAlumnos){
		for(var i = 0; i < cantidadAlumnos; i++){
			$("envia-alumno-"+i).checked = "checked";
		}
	}
	
	function deseleccionaTodos(cantidadAlumnos){
		for(var i = 0; i < cantidadAlumnos; i++){
			$("envia-alumno-"+i).checked = "";
		}
	}
</script>
</head>
<div class="container-fluid">
	<h1>Selecciona las cargas</h1>
	<div class="alert alert-info">
		<b>Modalidades:</b>
<%
		for(String mod:lisModo){
			String nombreModalidad = "-";
			if(mapaModalidades.containsKey(mod)){
				nombreModalidad = mapaModalidades.get(mod).getNombreModalidad();
			}
			out.print("["+nombreModalidad+"] ");	
		}		
%>
		<a href="modalidades" class="btn btn-primary btn-sm"><i class="fas fa-edit"></i></a>&nbsp;	
	</div>
	<form action="reporte" method="post" id="frmPeriodos" name="frmPeriodos" target="_self">
	<div class="row">
	<div class="col">
		<div class="control-group ">
				<label for="Grado"> <spring:message code="aca.Periodo" />: </label>
				<select name="PeriodoPas" class="input-small" onchange="document.frmPeriodos.submit();">
<%		for (CatPeriodo per : lisPeriodos){ %>
				<option value="<%=per.getPeriodoId()%>" <%=per.getPeriodoId().equals(periodoPas)?"Selected":""%>><%=per.getPeriodoId()%></option>	
<%		}%>					
				</select>
			</div>	
		</div>
		<div class="col">
			<div class="control-group ">
				<label for="Grado"> <spring:message code="aca.Periodo" />: </label>							
				<select name="Periodo" class="input-small" onchange="document.frmPeriodos.submit();")>
<%		for (CatPeriodo per : lisPeriodos){ %>
				<option value="<%=per.getPeriodoId()%>" <%=per.getPeriodoId().equals(periodo)?"Selected":""%>><%=per.getPeriodoId()%></option>
<%		}%>					
				</select>
			</div>		
		</div>		
	</div>
	</form>
	
	<form action="accion" method="post" id="frmCargas" name="frmCargas" target="_self">
	<input type="hidden" id="alumnos" name="alumnos" />
	<input type="hidden" id="Opcion" name="Opcion" />
	
	<div class="row">
		<div class="col">
			<table class="table table-bordered">
			<thead class="table-info">
			<tr>
				<td width="30%" align="center"><strong>Selecciona</strong></td>
				<td width="70%" align="center"><strong><spring:message code="aca.Carga"/></strong></td>
			</tr>
			</thead>
					
<%	// Ciclo que acomoda las cargas pasadas
	for (int i=0; i< lisCargasPas.size(); i++){
		Carga cargaPas = (Carga) lisCargasPas.get(i);
%> 
			<tr>
				<td width="30%" align="center">
					<input type="checkbox" id="envia-alumnos-<%=i %>" name="cargaActId" value="<%=cargaPas.getCargaId()%>">
				</td>
				<td width="70%"><%=cargaPas.getCargaId()%> - <%=cargaPas.getNombreCarga()%></td>
			</tr>
<%} %>					
			</table>
		</div>
		<div class="col">						
			<table class="table table-bordered">
			<thead class="table-info">
			<tr>
				<td width="30%" align="center">
					<strong>Selecciona</strong>
				</td>
				<td width="70%" align="center"><strong><spring:message code="aca.Carga"/></strong></td>
			</tr>
			</thead>
					
<% //ciclo que acomoda las cargas actuales
	for (int i=0; i< lisCargas.size(); i++){
		Carga cargaAct = (Carga) lisCargas.get(i);
%> 
			<tr>
				<td width="30%" align="center">
					<input type="checkbox" id="envia-alumno-<%=i%>" name="cargaActId" value="<%=cargaAct.getCargaId()%>">
				</td>
				<td width="70%"><%=cargaAct.getCargaId()%> - <%=cargaAct.getNombreCarga()%></td>
			</tr>
<%} %>
					
			</table>
		</div>		
	</div>			
	</form>
	<div class="row">
		<div class="col">
			<input class="btn btn-primary" type="button" value="Actual-SE" onclick="enviarArchivo('3');" />&nbsp;&nbsp;&nbsp;	
			<input class="btn btn-primary" type="button" value="Histórico-SE" onclick="enviarArchivo('2');" />&nbsp;&nbsp;&nbsp;
			<input class="btn btn-primary" type="button" value="ReporteUM" onclick="enviarArchivo('1');" />&nbsp;&nbsp;&nbsp;				
			<input class="btn btn-danger" type="reset" value="Borrar">		
		</div><br>
	</div>
</div>