<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatPeriodo"%>
<%@ page import= "aca.carga.spring.Carga"%>
<%@ page import= "aca.carga.spring.CargaBloque"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">
	function Borrar( cargaId, bloqueId ){
		if(confirm("<spring:message code="aca.JSEliminar"/>")==true){
	  		document.location="borrarBloque?CargaId="+cargaId+"&BloqueId="+bloqueId;
	  	}
	}	
	
	function grabaPeriodo(periodoId){
		document.location.href="bloque?cambioPeriodo=1&PeriodoId="+periodoId;
	}
	
	function grabaCarga(){
		document.location.href="bloque?accion=nuevaCarga&CargaId="+document.forma.carga.value+"&cambioCarga=S";
	}
</script>
<%	
	String periodoId 		= (String)session.getAttribute("periodo");	
	String cargaId 			= (String)session.getAttribute("cargaId");
	
	List<CatPeriodo> lisPeriodos 			= (List<CatPeriodo>) request.getAttribute("lisPeriodos");
	List<Carga> lisCargas 					= (List<Carga>) request.getAttribute("lisCargas");
	List<CargaBloque> lisBloques 			= (List<CargaBloque>) request.getAttribute("lisBloques");
	HashMap<String,String> mapaConMaterias	= (HashMap<String,String>)request.getAttribute("mapaConMaterias");
	
	int k = 0;	
%>
<div class="container-fluid">
	<h2>Blocks</h2>
	<form name="forma">
	<div class="alert alert-info d-flex align-items-center">			
		<b><spring:message code="aca.Ciclo"/>:</b>&nbsp;
 		<select onchange="javascript:grabaPeriodo(this.value);" name="PeriodoId" class="form-select" style="width:140px;">
<%	for(CatPeriodo periodo : lisPeriodos){%>
			<option <%if(periodoId.equals(periodo.getPeriodoId()))out.print(" Selected ");%> value="<%=periodo.getPeriodoId()%>"><%=periodo.getNombre().replace("Periodo ", "")%></option>
<%	}%>
	   	</select>
		&nbsp; &nbsp;
		<b><spring:message code="aca.Carga"/>:</b>&nbsp;
		<select onchange='javascript:grabaCarga()' name="carga" style="width:350px;" class="form-select" style="width:400px;">
<%	for(Carga carga : lisCargas){
%>
			<option <%if(cargaId.equals(carga.getCargaId()))out.print(" Selected ");%> value="<%=carga.getCargaId()%>">[<%=carga.getCargaId() %>] <%=carga.getNombreCarga()%></option>				
<%	}%>
		</select>
		&nbsp;&nbsp;
		<a class="btn btn-primary" href="editarBloque?CargaId=<%=cargaId%>"><spring:message code="aca.Anadir"/> Block</a>
	</div>
	<table class="table table-bordered">
	<thead class="table-info">
	<tr>
		<th width="5%">Op.</th>
		<th width="5%"><spring:message code="aca.Bloque"/></th>
		<th width="40%"><spring:message code="aca.Nombre"/></th>  
		<th width="10%"><spring:message code="aca.Inicio"/></th>	
		<th width="10%"><spring:message code="aca.Fin"/></th>	
		<th width="10%">Enrollment Closing</th>
		<th width="10%">Classes Start</th>
		<th width="10%">Sub. Reg.</th>
	</tr>
	</thead>
<%
	for (CargaBloque bloque : lisBloques){
		
		String totMat = "<span class='badge bg-warning rounded-pill'>0</span>";
		if (mapaConMaterias.containsKey(bloque.getBloqueId())){
			totMat = "<span class='badge bg-dark rounded-pill'>"+mapaConMaterias.get(bloque.getBloqueId())+"</span>";
		}	
%>				
	<tr class="tr2">
		<td align="center">
			<a title="Editar" href="editarBloque?CargaId=<%=bloque.getCargaId()%>&BloqueId=<%=bloque.getBloqueId()%>"><i class="fas fa-edit"></i></a>
<% 		if (!mapaConMaterias.containsKey(bloque.getBloqueId())){%>			
			<a title="Eliminar" href="javascript:Borrar('<%=bloque.getCargaId()%>','<%=bloque.getBloqueId()%>');">
				<i class="fas fa-trash text-danger"></i>
			</a>
<%		}%>			
		</td>    
		<td><%=bloque.getBloqueId()%></td>
		<td><%=bloque.getNombreBloque()%></td>
		<td><%=bloque.getFInicio()%></td>
		<td><%=bloque.getFFinal()%></td>	
		<td><%=bloque.getFCierre()%></td>		
		<td><%=bloque.getInicioClases()%></td>
		<td><%=totMat%></td>
	</tr>  
<%	} %>	
	</table>
	</form>
</div>
<!-- fin de estructura -->