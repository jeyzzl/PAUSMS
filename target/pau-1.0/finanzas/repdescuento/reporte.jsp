<%@page import="aca.alumno.AlumDescuentoUtil"%>
<%@page import="aca.catalogo.CatDescuento"%>
<%@page import="java.util.HashMap"%>
<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="DescuentoU" scope="page" class="aca.alumno.AlumDescuentoUtil"/>
<jsp:useBean id="EmpleadoU" scope="page" class="aca.emp.EmpleadoUtil"/>
<jsp:useBean id="CatDescuentoU" scope="page" class="aca.catalogo.CatDescuentoUtil"/>
<jsp:useBean id="AlumnoU" scope="page" class="aca.alumno.AlumUtil"/>

<%
	java.text.DecimalFormat formato = new java.text.DecimalFormat("###,##0.00;-###,##0.00");

	String hoy  		= aca.util.Fecha.getHoy();
	String fechaInicio	= request.getParameter("fechaInicio")==null?hoy:request.getParameter("fechaInicio");
	String fechaFinal	= request.getParameter("fechaFinal")==null?hoy:request.getParameter("fechaFinal");
	
	// Map de catalogo de descuento
	HashMap<String, aca.catalogo.CatDescuento> mapDescuento = CatDescuentoU.mapDescuentos(conEnoc);
	
	// Map de empleados
	HashMap<String,String> mapEmpleados 	= EmpleadoU.mapEmpleado(conEnoc);	

	//Lista de alumnos con descuentos
	HashMap<String, String> mapAlumno 		= AlumnoU.mapAlumDescuento(conEnoc);
	
	//Map de los importes cobrados en los conceptos del cálculo de cobro
	HashMap<String, String> mapImporte 		= DescuentoU.mapImporteConcepto(conEnoc, "'01','02','03'");
	
	// lista de desceuntos en un rango de fechas
	ArrayList<aca.alumno.AlumDescuento> lista = DescuentoU.lisPorFechas(conEnoc, fechaInicio, fechaFinal, " ORDER BY CODIGO_PERSONAL");
	double internado = 0, matricula = 0, ensenanza = 0; 
	
%>
&nbsp
<div class="container-fluid">
<h1>Reporte de Descuentos</h1>
<form id="forma" name="forma" action="reporte" method="post" id="noayuda">
<div class="alert alert-info d-flex align-items-center">	
	<font size="2" style="margin-top: 3px"><b><spring:message code="aca.FechaInicio"/>:</b></font> 
	<input id="fechaInicio" data-date-format="dd/mm/yyyy" name="fechaInicio" class="form-control" style="width:120px" value="<%=fechaInicio%>" >&nbsp;
	<font size="2" style="margin-top:3px"><b><spring:message code="aca.FechaFinal"/>:</b></font>
	<input id="fechaFinal" data-date-format="dd/mm/yyyy" name="fechaFinal" class="form-control" style="width:120px" value="<%=fechaFinal%>">
	<input type="submit" value="Mostrar">
</div>
</form>			
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
		<tr>
			<th title="Numero">#</th>
			<th title="Matr&iacute;cula del Alumno"><spring:message code="aca.Alumno"/></th>
			<th title="Nombre"><spring:message code="aca.Nombre"/></th>
			<th title="Carga ID"><spring:message code="aca.Carga"/></th>
			<th title="Descueto ID">Descuento</th>
			<th title="Fecha"><spring:message code="aca.Fecha"/></th>
			<th title="Matr&iacute;cula">$Mat.</th>
			<th title="Tipo Matricula"><spring:message code="aca.Tipo"/></th>
			<th title="Ensenanza">$Enza.</th>
			<th title="Tipo Ensenanza"><spring:message code="aca.Tipo"/></th>
			<th title="Internado">$Int.</th>
			<th title="Tipo Internado"><spring:message code="aca.Tipo"/></th>
			<th title="Total"><spring:message code="aca.Total"/></th>			
			<th title="Observaci&oacute;n">Observacion</th>
			<th title="Usario">Usario</th>
			<th title="Aplicado">Aplicado</th>	
		</tr>
	</thead>
<%		
	for(int i = 0; i < lista.size(); i++){
		aca.alumno.AlumDescuento descuento = (aca.alumno.AlumDescuento) lista.get(i);
		
		
		// Obtener el nombre del empleado
		String nombreE ="";
		if(mapEmpleados.containsKey(descuento.getUsuario())){
			nombreE = mapEmpleados.get(descuento.getUsuario());			
		}
		
		// Obtener el nombre del tipo de descuento
		String nombreD ="";
		if(mapDescuento.containsKey(descuento.getDescuentoId())){
			nombreD = mapDescuento.get(descuento.getDescuentoId()).getDescuentoNombre();
		}
		
		// Obtener el Nombre del alumno
		
		String nombreA ="";
		if(mapAlumno.containsKey(descuento.getCodigoPersonal() )){			
			nombreA = mapAlumno.get(descuento.getCodigoPersonal());
		}		
		
		double impMat = 0, descMat = 0;
		if (descuento.getTipoMatricula().equals("P")){
			
			if (mapImporte.containsKey(descuento.getCodigoPersonal()+descuento.getCargaId()+"01")){
				impMat = Double.valueOf(mapImporte.get(descuento.getCodigoPersonal()+descuento.getCargaId()+"01"));
				descMat = (impMat* Double.valueOf(descuento.getMatricula()) ) /100;
				matricula += descMat;
			}	
		}else{
			descMat = Double.valueOf(descuento.getMatricula());
			matricula += descMat;
		}
		
		double impEns = 0, descEns = 0;
		if (descuento.getTipoEnsenanza().equals("P")){
			if (mapImporte.containsKey(descuento.getCodigoPersonal()+descuento.getCargaId()+"02")){
				impEns = Double.valueOf(mapImporte.get(descuento.getCodigoPersonal()+descuento.getCargaId()+"02"));
				descEns = (impEns * Double.valueOf(descuento.getEnsenanza()) ) / 100 ;
				ensenanza += descEns;
			}	
		}else{
			descEns = Double.valueOf(descuento.getEnsenanza());
			ensenanza += descEns;
		}		
		
		double impInt = 0, descInt = 0;
		if (descuento.getTipoInternado().equals("P")){
			if (mapImporte.containsKey(descuento.getCodigoPersonal()+descuento.getCargaId()+"03")){
				impInt = Double.valueOf(mapImporte.get(descuento.getCodigoPersonal()+descuento.getCargaId()+"03"));
				descInt = (impInt * Double.valueOf(descuento.getInternado()) ) / 100 ;
				internado += descInt;
			}
		}else{
			descInt = Double.valueOf(descuento.getInternado());
			internado += descInt;
		}	
		double total = descMat+descEns+descInt;
		// if (descuento.getCodigoPersonal().equals("1070176")) System.out.println("Datos:"+impMat+":"+impEns+":"+impInt);
		// if (descuento.getCodigoPersonal().equals("1070176")) System.out.println("Datos:"+descMat+":"+descEns+":"+descInt);
%>		
		<tr >
			<td style="text-align:right"><%=i+1%></td>
			<td style="text-align:center" ><%=descuento.getCodigoPersonal()%></td>
			<td><%=nombreA %></td>
			<td style="text-align:center"><%=descuento.getCargaId()%></td>
			<td><%=nombreD%></td>
			<td><%=descuento.getFecha()%></td>
			<td style="text-align:right"><%=formato.format(descMat)%></td>
			<td style="text-align:right"><%=descuento.getTipoMatricula().equals("P")?descuento.getMatricula()+"%":"$" %></td>
			<td style="text-align:right"><%=formato.format(descEns)%></td>
			<td style="text-align:right"><%=descuento.getTipoEnsenanza().equals("P")?descuento.getEnsenanza()+"%":"$"%></td>
			<td style="text-align:right"><%=formato.format(descInt)%></td>
			<td style="text-align:right"><%=descuento.getTipoInternado().equals("P")?descuento.getInternado()+"%":"$" %></td>
			<td style="text-align:right"><%=formato.format(total) %></td>			
			<td><%=descuento.getObservaciones() %></td>
			<td><%=nombreE %></td>
			<td><%= descuento.getAplicado().equals("S")?"SI":"NO" %></td>
		</tr>
<%
	}
%>		
	</table>		
	<div class="alert alert-info">
		<h3>
		<strong>TOTAL MATRICULA: &nbsp;&nbsp;$<%=formato.format(matricula)%></strong>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<strong>TOTAL ENSEÑANZA: &nbsp;&nbsp;$<%=formato.format(ensenanza)%></strong>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<strong>TOTAL INTERNADO:&nbsp;&nbsp; $<%=formato.format(internado)%></strong>
		</h3>
	</div>
</div>	
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
	<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
	<script>jQuery('#fechaInicio').datepicker();
			jQuery('#fechaFinal').datepicker();
	</script>
	
<%@ include file= "../../cierra_enoc.jsp" %>