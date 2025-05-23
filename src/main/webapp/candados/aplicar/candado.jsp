<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatPeriodo"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.catalogo.spring.CatTipoAlumno"%>
<%@ page import= "aca.carga.spring.Carga"%>
<%@page import="aca.candado.spring.CandAlumno"%>
<%@page import="aca.vista.spring.Estadistica"%>
<%@ page import="java.util.Date,java.text.SimpleDateFormat,java.text.DecimalFormat"%>

<script type="text/javascript">
	function Aplicar(deudores){
		if (confirm("¿Estás seguro de aplicar el candado?")){
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
	HashMap<String,String> mapaInscritos 		= (HashMap<String,String>)request.getAttribute("mapaInscritos");
%>
<body>
<div class="container-fluid">
	<h2>Alumnos con deudas mayores</h2>
	<form name="forma" action="candado?Mostrar=S" method="post">
	<div class="alert alert-info d-flex align-items-center">
		<font size="2"><spring:message code="aca.Periodo"/>:</font>
		<select name="PeriodoId" class="form-select" onchange="javascript:document.forma.submit();" style="width:140px;">
	<%	for(CatPeriodo periodo : lisPeriodos){%>
		<option <%if(periodoId.equals(periodo.getPeriodoId()))out.print(" Selected ");%> value="<%=periodo.getPeriodoId()%>"><%=periodo.getNombre().replace("Periodo ", "")%></option>
	<%	}%>
    	</select>
		&nbsp;&nbsp;&nbsp;
		<b><spring:message code="aca.Carga"/>: </b>
		<select name="CargaId" style="width:350px;" class="form-select" onchange="javascript:document.forma.submit();" style="width:520px;">
			<option <%=cargaId.equals("000000")?" Selected":""%> value="000000">[000000] Elige una carga</option>			
	<%	for(Carga carga : lisCargas){			
	%>
			<option <%if(cargaId.equals(carga.getCargaId()))out.print(" Selected ");%> value="<%=carga.getCargaId()%>">[<%=carga.getCargaId() %>] <%=carga.getNombreCarga()%></option>
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
		<tr class="success">
			<th>#</th>
			<th>Candado</th>
			<th>Matricula</th>
			<th>Alumno</th>
			<th>Tipo</th>
			<th>Inscrito</th>
			<th>Carrera</th>
			<th class="right">Saldo</th>
		</tr>
	</thead>
	<tbody>
<% 		
	int row = 0;
	int numCandados = 0;
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
		String inscrito = "NO";
		if (mapaInscritos.containsKey(est.getCodigoPersonal())){
			inscrito = "SI";
		}
		
		if (esDeudor){
			row++;
			if (inscrito.equals("NO") && tieneCandado.equals("NO")){
				if (row==1){deudores = est.getCodigoPersonal(); }else { deudores += ","+est.getCodigoPersonal();}
				numCandados++;
			}
%>
		<tr>
			<td><%=row%></td>
			<td><%=tieneCandado%></td>
			<td><%=est.getCodigoPersonal()%></td>
			<td><%=est.getApellidoPaterno()+" "+est.getApellidoMaterno()%>, <%=est.getNombre()%></td>			
			<td><%=tipoNombre%></td>
			<td><%=inscrito%></td>
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
		<a href="javaScript:Aplicar('<%=deudores%>')" class="btn btn-primary">Aplicar candado <%=numCandados%></a>
	</div>
</div>
</body>