<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="aca.util.Fecha"%>
<%@page import="aca.carga.spring.CargaEnLinea"%>
<%@page import="aca.carga.spring.CargaBloque"%>
<%@page import="aca.carga.spring.CargaAlumno"%>
<%@page import="aca.alumno.spring.AlumPlan"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.plan.spring.MapaPlan"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.catalogo.spring.CatNivel"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ include file= "menu.jsp" %>

<head>
<script>
	function refrescar(){		
		document.forma.submit();	
	}		
	function Borrar(carga,bloque,plan){
		if(confirm("Estás seguro de eliminar el registro!")==true){
  			document.location.href="borrarCarga?CargaId="+carga+"&BloqueId="+bloque+"&PlanId="+plan;
		}			
	}	
	function Activar(carga,bloque,plan){
		document.location.href="grabarCarga?CargaId="+carga+"&BloqueId="+bloque+"&PlanId="+plan;	
	}
</script>
</head>
<%
	String codigoPersonal			= (String) session.getAttribute("codigoPersonal");
	String matricula				= (String) session.getAttribute("codigoAlumno");
	String cargaId					= (String) request.getAttribute("cargaId");
	String planRegistrado			= (String) request.getAttribute("planRegistrado");
	String bloqueId					= request.getParameter("BloqueId")==null?"0":request.getParameter("BloqueId");
	String opcion 					= request.getParameter("Opcion")==null?"1":request.getParameter("Opcion");
	
	AlumPersonal alumPersonal 		= (AlumPersonal) request.getAttribute("alumPersonal");
	CargaAlumno cargaAlumno 		= (CargaAlumno) request.getAttribute("cargaAlumno");
	
	List<CargaEnLinea> lisCargas	= (List<CargaEnLinea>) request.getAttribute("lisCargas");
	List<CargaBloque> lisBloques	= (List<CargaBloque>) request.getAttribute("lisBloques");
	List<AlumPlan> lisPlanes 		= (List<AlumPlan>)request.getAttribute("lisPlanes");
	
	HashMap<String, MapaPlan> mapaPlanes 				= (HashMap<String, MapaPlan>) request.getAttribute("mapaPlanes");
	HashMap<String,CatCarrera> mapaCarreras				= (HashMap<String,CatCarrera>)request.getAttribute("mapaCarreras");
	HashMap<String,CatNivel> mapaNiveles				= (HashMap<String,CatNivel>)request.getAttribute("mapaNiveles");
	HashMap<String,String> mapaMateriasOfrecidas			= (HashMap<String,String>)request.getAttribute("mapaMateriasOfrecidas");
	HashMap<String,String> mapaMateriasAlumno			= (HashMap<String,String>)request.getAttribute("mapaMateriasAlumno");	
	
	String numMat 		= "0";
	String carreraPlan	= "0";
	String nivelPlan 	= "0";
	String nivelNombre	= "-";
	String nombrePlan 	= "-";
	String esc 			= "";
	boolean contiene    = false;
%>
<body>
<div class="container-fluid mt-1">
	<div class="alert alert-success">
		<a href="pasos"><i class="far fa-arrow-alt-circle-left fa-lg"></i></a>
		<span class="badge rounded-pill bg-dark">2A</span> <spring:message code="portal.alumno.cargas.CargaAcademica"/>	
		<small class="text-muted">( <%=codigoPersonal%> - <%=alumPersonal.getNombre()%> <%=alumPersonal.getApellidoPaterno()%> <%=alumPersonal.getApellidoMaterno()%> )</small>
	</div>
	<form name="forma" action="carga" method="post" target="_self">
		<input type="hidden" name="Opcion" value="3">
		<div class="alert alert-info">		
			<b><spring:message code="portal.alumno.cargas.Periodo"/>:</b>
			<select class="custom-select" name="CargaId" id="CargaId" onchange="javascript:refrescar()" style="width:350px">
<%			for (CargaEnLinea carga : lisCargas){%>
				<option <%=cargaId.equals(carga.getCargaId())?" Selected":""%> value="<%=carga.getCargaId()%>"><%=carga.getNombre()%></option>
<%			} %>
			</select>&nbsp;&nbsp;
			<b><spring:message code="portal.alumno.cargas.Bloques"/>:</b>
			<select class="custom-select" name="BloqueId" id="BloqueId" onchange="javascript:refrescar()" style="width:450px">
<%			for (CargaBloque bloque : lisBloques){%>
				<option <%=bloqueId.equals(bloque.getBloqueId())?" Selected":""%> value="<%=bloque.getBloqueId()%>"><%=bloque.getFInicio()%> al <%=bloque.getFInicio()%> - <%=bloque.getNombreBloque()%></option>
<%			} %>
			</select>
		</div>
	</form>
	<table class="table table-fullcondensed table-bordered">
	<thead>				
    <tr> 
		<th width="10%">Plan registrado<spring:message code="portal.alumno.cargas."/></th>
	    <th width="10%"><spring:message code="aca.Clave"/></th>
	    <th width="30%"><spring:message code="aca.Nombre"/><spring:message code="aca.Plan"/></th>
	    <th width="10%"><spring:message code="aca.Nivel"/></th>
	    <th width="10%" class="center"><spring:message code="aca.FechaInicio"/></th>	        
	    <th width="10%" class="center"><spring:message code="aca.Principal"/></th>
	    <th width="10%" class="center">Materias encontradas<spring:message code="portal.alumno.cargas.MateriasEncontradas"/></th>
	</tr>
	</thead>
	<tbody>
<%		
	for (AlumPlan plan : lisPlanes){	
		numMat = "0";
		carreraPlan	= "0";
		nivelPlan 	= "0";
		nivelNombre	= "-";
		nombrePlan 	= "-";
		
		if (mapaPlanes.containsKey(plan.getPlanId())){
			carreraPlan	= mapaPlanes.get(plan.getPlanId()).getCarreraId();
			nombrePlan 	= mapaPlanes.get(plan.getPlanId()).getNombrePlan();
			if (mapaCarreras.containsKey(carreraPlan)){
				nivelPlan = mapaCarreras.get(carreraPlan).getNivelId();
				if (mapaNiveles.containsKey(nivelPlan)){
					nivelNombre = mapaNiveles.get(nivelPlan).getNombreNivel();
				}
			}
		} 
		
		if (mapaMateriasAlumno.containsKey(plan.getPlanId())){
			numMat 		= mapaMateriasAlumno.get(plan.getPlanId());
		}
		
		// Despliega el plan de estudios si no ha sido seleccionado ninguno o solamente el que fue seleccionado
		if (planRegistrado.equals("0") || planRegistrado.equals(plan.getPlanId()) ) {
%>
       	<tr> 
          	<td>
<% 			if (planRegistrado.equals("0")){%>          	    
        	 	<a href="javascript:Activar('<%=cargaId%>','<%=bloqueId%>','<%=plan.getPlanId()%>')"> <i class="fas fa-circle " ></i></a>&nbsp;
<% 			}else{%>
				<a href="javascript:Activar('<%=cargaId%>','<%=bloqueId%>','<%=plan.getPlanId()%>')"><i class="fas fa-trash" ></i></a>&nbsp;
<% 			}%>        	 	          		
		   	</td>
           	<td title="<%=nombrePlan%>">
	           	<%if (contiene) out.print("<span style='font-size:15px;' class='badge bg-success'>"+plan.getPlanId()+"</span>"); else out.print(plan.getPlanId());%>
           	</td>
           	<td><%=nombrePlan%></td>
           	<td><%=nivelPlan%>-<%=nivelNombre%></td>
           	<td class="center"><%=plan.getFInicio()%></td>           	
           	<td class="center" style="font-size:12px;">
               <%if (plan.getPrincipal().equals("1")) out.print("<span style='font-size:10px;' class='badge bg-dark'>SI</span>"); else out.print("<span style='font-size:10px;' class='badge bg-warning'>NO</span>");%>
           	</td>
           	<%esc = plan.getEscala()==null?"":plan.getEscala();%>
           	<td align="center" style="text-align:center;">
              	<%=plan.getEscala()%>
           	</td>            
		</tr>
<%		}%>		
<%	}%>
	</tbody>		      
	</table>	
</div>
</body>