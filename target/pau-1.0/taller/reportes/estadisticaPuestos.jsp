<%@page import="aca.util.Fecha"%>
<%@page import="aca.afe.FesCcAfeAcuerdosUtil"%>
<%@page import="aca.bec.BecPuestoAlumno"%>
<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.bec.BecCategoriaUtil"%>
<%@page import="java.util.*" %>
<%@page import="java.text.*" %>

<jsp:useBean scope="page" id="ContEjercicioU"  class="aca.financiero.ContEjercicioUtil" />
<jsp:useBean scope="page" id="BecPeriodoU" class="aca.bec.BecPeriodoUtil"/>
<jsp:useBean scope="page" id="BecCategoriaU" class="aca.bec.BecCategoriaUtil"/>

<%
	java.text.DecimalFormat getFormato		= new java.text.DecimalFormat("###,##0.00;-###,##0.00");
	
	String ejercicioId 		= request.getParameter("EjercicioId")==null?session.getAttribute("ejercicioId").toString():request.getParameter("EjercicioId");
	String fecha 			= request.getParameter("Fecha")==null?aca.util.Fecha.getHoy():request.getParameter("Fecha");

	int totCategorias		= 0;
	
	//Lista de ejercicios
	ArrayList<aca.financiero.ContEjercicio> lisEjercicios 	= ContEjercicioU.getListAll(conEnoc, "ORDER BY 1 DESC");	
	
	// Lista de periodos 
	ArrayList<aca.bec.BecCategoria> lisCategorias 			= BecCategoriaU.getListAll(conEnoc, " ORDER BY CATEGORIA_NOMBRE");
	
	// Map de numero de puestos ocupados en una categoria
	java.util.HashMap<String,String> mapCategoria 			= aca.bec.BecPuestoAlumnoUtil.mapTotPuestosPorCategoria(conEnoc, ejercicioId, fecha); 
%>
<div class="container-fluid">
	<h1>Estadistica de puestos</h1>
	<form name="forma" action="estadisticaPuestos" method="post">
	<div class="alert alert-info">	
	<a class="btn btn-primary" href="menu"><i class="fas fa-arrow-left"></i></a>
		Carga: &nbsp;
		<select name="EjercicioId" id="EjercicioId" style="width:150px;" onchange="document.forma.submit()">
		<%
			for(aca.financiero.ContEjercicio ejercicio: lisEjercicios){	
		%>
				<option value="<%=ejercicio.getIdEjercicio() %>" <%if(ejercicio.getIdEjercicio().equals(ejercicioId))out.print("selected"); %>><%=ejercicio.getIdEjercicio() %></option>
		<%
			}
		%>
		</select>
		Fecha de revisión: <input type="text" data-date-format="dd/mm/yyyy" id="Fecha" name="Fecha" value="<%=fecha%>"/>
		&nbsp;&nbsp;
		<a class="btn btn-success" onclick="javascript:document.forma.submit();">Buscar</a>
	</div>
	</form>
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">
	<tr>
		<th>#</th>
		<th>Categoria</th>
		<th class="right"><spring:message code="aca.Total"/></th>
	</tr>
	</thead>
<%	
	int cont = 0;
	for(aca.bec.BecCategoria categoria: lisCategorias){
		cont++;
		
		String total = "0";
		if (mapCategoria.containsKey(categoria.getCategoriaId())){
			total = mapCategoria.get(categoria.getCategoriaId());
		}
%>
	<tr>
		<td><%= cont %></td>
		<td>
			<a href="categorias?CategoriaId=<%=categoria.getCategoriaId()%>&CategoriaNombre=<%=categoria.getCategoriaNombre()%>&EjercicioId=<%=ejercicioId%>&Fecha=<%=fecha%>"><%= categoria.getCategoriaNombre()%></a>
		</td>
		<td class="right"><%= total %></td>
	</tr>	
<%
		totCategorias += Integer.parseInt(total);
%>	
<%		
	}
%>	
	<tr>
		<td></td>
		<td style="text-align:center">TOTAL</td>
		<td style="text-align:right"><%= totCategorias %></td>
	</tr>
	</table>
</div>
<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<%@ include file= "../../cierra_enoc.jsp" %>