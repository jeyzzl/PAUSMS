<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatPeriodo"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.catalogo.spring.CatTipoAlumno"%>
<%@ page import= "aca.carga.spring.Carga"%>
<%@page import="aca.candado.spring.CandAlumno"%>
<%@page import="aca.vista.spring.Estadistica"%>
<%@ page import="java.util.Date,java.text.SimpleDateFormat,java.text.DecimalFormat" %>

<script type="text/javascript">
	function Aplicar(deudores){
		if (confirm("Are you sure you want to add this lock?")){
			document.location.href="grabarDeudores?Deudores="+deudores;
		}
	}

	function Mostrar(){		
		document.forma.submit();
	}
</script>
<%
	DecimalFormat dmf		= new DecimalFormat("###,##0.00; -###,##0.00");
	String mensaje 			= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	
	String periodoId							= (String) request.getAttribute("periodoId");
	String cargaId								= (String) request.getAttribute("cargaId");
	
	List<CatPeriodo> lisPeriodos 				= (List<CatPeriodo>) request.getAttribute("lisPeriodos");
	List<Carga> lisCargas 						= (List<Carga>) request.getAttribute("lisCargas");
	List<Estadistica> lisInscritos 				= (List<Estadistica>) request.getAttribute("lisInscritos");
	HashMap<String,String> mapaDeudores 		= (HashMap<String,String>)request.getAttribute("mapaDeudores");
	HashMap<String,CandAlumno> mapaCandado 		= (HashMap<String,CandAlumno>)request.getAttribute("mapaCandado");
	HashMap<String,CatCarrera> mapaCarreras		= (HashMap<String,CatCarrera>)request.getAttribute("mapaCarreras");
	HashMap<String,CatTipoAlumno> mapaTipos 	= (HashMap<String,CatTipoAlumno>)request.getAttribute("mapaTipos");
%>
<body>
<div class="container-fluid">
	<h2>Minor Debt Students</h2><br>
	<form name="forma" action="candado" method="post">
	<div class="alert alert-info">
		<font size="2"><spring:message code="aca.Periodo"/>:</font>
		<select onchange="javascript:document.forma.submit();" name="PeriodoId" class="input input-medium">
	<%	for(CatPeriodo periodo : lisPeriodos){%>
		<option <% if(periodoId.equals(periodo.getPeriodoId()))out.print(" Selected ");%> value="<%=periodo.getPeriodoId()%>"><%=periodo.getNombre().replace("Periodo ", "")%></option>
	<%	}%>
    	</select>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<b><spring:message code="aca.Carga"/>: </b>
		<select onchange='javascript:document.forma.submit();' name="CargaId" style="width:350px;" class="input input-xlarge">
			<option <%=cargaId.equals("000000")?" Selected":""%> value="000000">[000000] Elige una carga</option>			
	<%	for(Carga carga : lisCargas){			
	%>
			<option <% if(cargaId.equals(carga.getCargaId()))out.print(" Selected ");%> value="<%=carga.getCargaId()%>">[<%=carga.getCargaId() %>] <%=carga.getNombreCarga()%></option>
	<%	}%>
		</select>		
		&nbsp;&nbsp;
		<a href="javaScript:Mostrar()" class="btn btn-primary">Mostrar</a>
	</div>
	</form>
<%
	if (!mensaje.equals("-")) out.print("<div class='alert alert-info'>"+mensaje+"</div>");
%>		
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
		<tr>
			<th>#</th>
			<th>Candado</th>
			<th>Matricula</th>
			<th>Alumno</th>
			<th>Tipo</th>
			<th>Carrera</th>
			<th class="right">Saldo</th>
		</tr>
	</thead>
	<tbody>
<% 		
	int row = 0;
	String deudores = "";
	for(Estadistica est : lisInscritos){		
		
		boolean esDeudor 	= false;
		String deuda 		= "0";
		if (mapaDeudores.containsKey(est.getCodigoPersonal()) ){
			esDeudor 	= true;
			deuda 		= mapaDeudores.get(est.getCodigoPersonal());
		} 
		
		String carreraNombre = "";
		if (mapaCarreras.containsKey(est.getCarreraId()) ){
			carreraNombre = mapaCarreras.get(est.getCarreraId()).getNombreCarrera();
		}
		
		String tipoNombre = "";
		if (mapaTipos.containsKey(est.getTipoAlumnoId()) ){
			tipoNombre 	= mapaTipos.get(est.getTipoAlumnoId()).getNombreTipo();
		}
		
		String tieneCandado = "NO";
		if (mapaCandado.containsKey(est.getCodigoPersonal()) ){
			tieneCandado = "SI";
		}
		if (esDeudor){
			row++;
			if (row==1){deudores = est.getCodigoPersonal(); }else { deudores += ","+est.getCodigoPersonal();} 
%>
		<tr>
			<td><%=row%></td>
			<td><%=tieneCandado%></td>
			<td><%=est.getCodigoPersonal()%></td>
			<td><%=est.getApellidoPaterno()+" "+est.getApellidoMaterno()%>, <%=est.getNombre()%></td>
			<td><%=tipoNombre%></td>
			<td><%=carreraNombre%></td>
			<td class="right"><%=dmf.format(Float.valueOf(deuda))%></td>
		</tr>
<% 	
		}
	}
%>
	</tbody>
	</table><br>
	<div class="alert alert-info">
		<a href="javaScript:Aplicar('<%=deudores%>')" class="btn btn-primary">Aplicar candado</a>
	</div>
</div>
</body>