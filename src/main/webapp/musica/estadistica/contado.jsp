<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import= "aca.musica.MusiPeriodo"%>

<%@page import="aca.musica.MusiAlumno"%>
<jsp:useBean id="PeriodoU" scope="page" class="aca.musica.MusiPeriodoUtil"/>
<jsp:useBean id="alumnoU" scope="page" class="aca.musica.MusiAlumnoUtil"/>

<script type="text/javascript" src="../../js/jquery-1.5.1.min.js"></script>
<script type="text/javascript">
	
	function recarga(){
		document.forma.submit();
	}	
	  
	$(document).ready(function() {  
	     $(".botonExcel").click(function(event) {  
	     	$("#datos_a_enviar").val( $("<div>").append( $("#Exportar_a_Excel").eq(0).clone()).html());  
	     	$("#FormularioExportacion").submit();  
		});  
	});	
</script>

<% 
	java.text.DecimalFormat getformato= new java.text.DecimalFormat("###,##0.00;-###,##0.00");
	
	String codigoId			= (String) session.getAttribute("CodigoId");

	String periodoId 		= request.getParameter("PeriodoId");	
	if (periodoId==null || periodoId.equals("null") || periodoId.equals("")){
		periodoId 			= aca.musica.MusiPeriodo.getPeriodoActual(conEnoc);
	}
	String bgColor			= "";

	ArrayList lisPeriodos	= new ArrayList();
	lisPeriodos				= PeriodoU.getListAll(conEnoc,"ORDER BY PERIODO_ID");
	
	ArrayList lisContado	= new ArrayList();
	lisContado			= alumnoU.getListFormaPago(conEnoc, periodoId, "C", " ORDER BY APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE ");
	
	int cont = 0;
	
	double pago = 0;
	double pagoTotal = 0;
	double saldo = 0;
	String estado = "";
%>

<div class="container-fluid">
<h1>Alumnos con Pago de Contado</h1>
<div class="alert alert-info">
	<a href="opcion" class="btn btn-primary">Regresar</a>&nbsp;&nbsp;
	<form action="contado" name="forma">
	<b>Seleccione el Periodo:</b>
          <select onchange='javascript:recarga()' name="PeriodoId">
<%
	for (int i=0; i< lisPeriodos.size(); i++){
		aca.musica.MusiPeriodo periodo = (aca.musica.MusiPeriodo) lisPeriodos.get(i);
%>
			<option <%if( periodoId.equals(periodo.getPeriodoId() ))out.print(" Selected ");%> value="<%= periodo.getPeriodoId() %>">[<%= periodo.getPeriodoId() %>] <%= periodo.getPeriodoNombre() %></option>
<%
	}
%>
          </select>
    </form>      
</div>

<form action="../../excel" method="post" id="FormularioExportacion">
  <input type="hidden" id="datos_a_enviar" name="datos_a_enviar" />
  <input type="hidden" id="archivo" name="archivo" value="AlumnosConPagoDeContado.xls"/>
  <table style="width:90%" >
    <tr><td><a href="#"><img src="../../imagenes/excel-chico.png" width="30px" class="botonExcel" onmouseover="this.src='../../imagenes/excel-chico-over.png'" onmouseout="this.src='../../imagenes/excel-chico.png'"/></a></td></tr>    
  </table>
</form>

<table style="margin: 0 auto;" class="table table-fullcondensed table-striped" width="90%" id="Exportar_a_Excel"> 
  <tr> 
    <th width="8%"><spring:message code="aca.Numero"/></th>
    <th width="7%"><spring:message code="aca.Matricula"/></th>
    <th width="32%"> Nombre </th>
    <th width="25%"> Apellido</th>
    <th width="8%"> Pago</th>
    <th width="8%"> Saldo</th>
    <th width="8%"> Estado</th>    
  </tr>
  

<%

for (int j=0; j<lisContado.size(); j++){cont++;
	aca.musica.MusiAlumno alumno = (aca.musica.MusiAlumno) lisContado.get(j);
	
	pago = Double.valueOf(aca.musica.MusiCalculoUtil.getPagoInicial(conEnoc, alumno.getCodigoId(), periodoId )).doubleValue(); 
	pagoTotal = pagoTotal+pago;
	
	saldo = aca.musica.MusiMovimiento.saldoAlumno(conEnoc, alumno.getCodigoId());
  
	if(saldo >= 0)
		estado = "Pagado";
	else
		estado = "Pendiente";
%>

  <tr class="tr2"> 
    <td align="center"><font size="1"><%=cont%></font></td>
    <td align="center"><font size="1"><%=alumno.getCodigoId() %></font></td> 
    <td align="left"><font size="1"><%=alumno.getNombre()%></font></td>
    <td align="left"><font size="1"><%=alumno.getApellidoPaterno()%>&nbsp;&nbsp;<%=alumno.getApellidoMaterno()%></font></td> 
    <td align="right"><font size="1"><%=getformato.format(pago) %></font></td> 
    <td align="right"><font size="1"><%= getformato.format(saldo) %></font></td>
    <td align="center"><font size="1"><%=estado %></font></td>   
  </tr>
<% 
}
%>  
	<tr class="th2">
		<td colspan="4" align="center" ><b> T O T A L </b></td>
		<td align="right" ><b><%=getformato.format(pagoTotal) %></b></td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
</table>
</div>
<%@ include file = "../../cierra_enoc.jsp"%>