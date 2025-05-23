<%@ page import="java.text.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>

<%@page import="aca.financiero.spring.FinSaldo"%>
<%@page import="aca.catalogo.spring.CatAvance"%>
<%@page import="aca.plan.spring.MapaPlan"%>
<%@page import="aca.parametros.spring.Parametros"%>
<%@page import="aca.ssoc.spring.SsocDocAlum"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	function recarga(){	
  		document.location.href="avance?PlanId="+document.frmGraduando.PlanId.value;
	}
</script>
<%
	DecimalFormat getformato 	= new DecimalFormat("###,##0.00;-###,##0.00");
	String institucion 		= (String)session.getAttribute("institucion");

	String codigoAlumno 	= (String) session.getAttribute("codigoAlumno");
	String nombreAlumno 	= (String) request.getAttribute("nombreAlumno");
	String planId 			= (String) request.getAttribute("planId");	
	String nivelId 			= (String) request.getAttribute("nivelId");
	String eventoDatos		= (String) request.getAttribute("eventoDatos");	
	String evento			= (String) request.getAttribute("evento");	
	boolean tieneCarta 		= (boolean) request.getAttribute("tieneCarta");
	boolean tieneEvento 	= (boolean) request.getAttribute("tieneEvento");
	
	Parametros parametros  	= (Parametros) request.getAttribute("parametros");
	String carrera 			= (String) request.getAttribute("carrera");
	String modalidad 		= (String) request.getAttribute("modalidad");
	String residencia 		= (String) request.getAttribute("residencia");
	String documentos 		= (String) request.getAttribute("documentos");
	String finanzas 		= "";
	String registro 		= (String) request.getAttribute("registro");
	String avance 			= (String) request.getAttribute("avance");
	String permiso 			= (String) request.getAttribute("permiso");	
	String textoPermiso 	= "-";

	float crdPlan 			= (float) request.getAttribute("crdPlan");
	float crdAlumno 		= (float) request.getAttribute("crdAlumno");
	double porcentaje 		= 0;
	double saldo 			= 0;
	
	FinSaldo finSaldo 		= (FinSaldo) request.getAttribute("finSaldo");
	int compPlan 			= (int) request.getAttribute("compPlan");
	int compAlumno 			= (int) request.getAttribute("compAlumno");
	
	List<CatAvance> lisAvance 				= (List<CatAvance>) request.getAttribute("lisAvance");
	List<String> lisPlanes 					= (List<String>) request.getAttribute("lisPlanes");	
	HashMap<String, MapaPlan> mapaPlan		= (HashMap<String, MapaPlan>) request.getAttribute("mapaPlan");

	if (residencia.equals("E"))
		residencia = "Externo";
	else
		residencia = "Interno";	
%>
<html>
<head>
<style TYPE="text/css">
	BODY {
		background: #ffffff;
	}	
	#para2 {
		margin-top: 10px;
		margin-right: 700px;
	}	
	.para1 {
		margin-top: -130px;
	}	
	.Estilo3 {
		font-size: 12px
	}	
	.Estilo4 {
		font-size: 18px
	}
</style>
</head>
<body bgcolor="#FFFFFF">
	<div align="center" id="para2"><img src="../../imagenes/logo_large2.png" width="120px"></div>
	<div CLASS="para1">
		<form name="frmGraduando">
		<table style="width:70%"  align="center">
		<tr>
			<td align="center" class="titulo"><strong><br>
			<br>
			<%=institucion%>, A. C. </strong></td>
		</tr>
		<tr>
			<td align="center"><strong>Autorización de Graduaci&oacute;n </strong></td>
		</tr>
		<tr>
			<td height="15" align="center"></td>
		</tr>
		<tr>
			<td align="center">
				<select name="PlanId" onchange="javascript:recarga()" style="width:500px;">
				<%
					for(String pla : lisPlanes){
				%>
				<option value='<%=pla%>' <%=planId.equals(pla)?"Selected":""%>> <%=pla%> - <%=mapaPlan.containsKey(pla)?mapaPlan.get(pla).getNombrePlan():"-"%></option>
				<%
					}
				%>
				</select>
			</td>
		</tr>
		<tr>
			<td height="15" class="center"></td>
		</tr>
		<tr>
			<th class="center"><b>Alumno: [ <%=codigoAlumno%> ] [ <%=nombreAlumno%>] [ <%=planId%> ] [ <%=carrera%> ] [ <%=modalidad%> ] [ <%=residencia%> ]</b></th>
		</tr>
		</table>
		</form>
<%
	try {
		saldo = Float.parseFloat(finSaldo.getSaldoSP());
		
		String status = "Db";
		if(saldo < 0){
			saldo = Float.parseFloat(finSaldo.getSaldoSP().substring(1));
			status = "Cr";
		}
		
		if (status.equals("Cr")) {
			finanzas = "<span style='color:blue'>¡Autorizado!" + " saldo = $" + getformato.format(saldo)+" "+status+"</span>";
		} else {
			finanzas = "<span style='color:red'>¡Saldo pendiente!" + " saldo = $" + getformato.format(saldo)+" "+status+"</span>";
		}

		porcentaje = (double) crdAlumno / crdPlan * 100;
		
		if (permiso.equals("S")) {
			textoPermiso = " <span style='color:blue'>( ¡Autorizado¡ )</span>";
		} else {
			textoPermiso = "<span style='color:red'>( ¡Sin Permiso¡)</span>";
		}
		
	} catch (Exception e) {
		System.out.println(e.toString());
	}

	String titulado = "<span style='color:green'>Pasante</span>";
	
	if (nivelId.equals("3") || nivelId.equals("4")) titulado = "<span style='color:blue'>Titulado</span>";
		
	if ( tieneCarta && tieneEvento){	
		titulado = "<span style='color:blue'>Titulado</span>";
	}else{
		titulado = "<span style='color:orange'>En proceso</span>";
	}	
%>
		<table style="width:70%" border="1" align="center" bordercolor="#000000" class="tabla">
		<tr height="20">
			<td align="left" colspan="2"><strong>&nbsp;Finanzas:</strong></td>
			<td align="left">&nbsp;<%=finanzas%></td>
		</tr>
		<tr height="20">
			<td align="left" colspan="2"><strong>&nbsp;Kardex: <%=planId%> ( Créditos <%=crdPlan%>)</strong></td>
			<td align="left">&nbsp;Crd. Aprobados: <%=crdAlumno%> - Equivale: <%=getformato.format(porcentaje)%></td>
		</tr>		
		<tr height="20">
			<td align="left" colspan="2"><strong>&nbsp;Estatus de grado:</strong></td>
			<td align="left">&nbsp;<%=titulado%></td>
		</tr>	
		<tr height="20">
			<td align="left" colspan="2"><strong>&nbsp;Evento:</strong></td>
			<td align="left">&nbsp;<%=eventoDatos%></td>
		</tr>
		<tr height="20">
			<td align="left" colspan="2"><strong>&nbsp;Registro</strong>&nbsp;<%=textoPermiso%></span></td>
			<td align="left">
			<% if (permiso.equals("N")){ %>
				<a class="btn btn-primary btn-sm" href="grabar?Permiso=S&PlanId=<%=planId%>">Autorizar</a>
			<%	}else{%>
				<a class="btn btn-danger btn-sm" href="grabar?Permiso=N&PlanId=<%=planId%>">Autorizado</a>
			<%	}%>
			<% if (evento.equals("N")){ %>
				<a class="btn btn-primary btn-sm" href="grabarEvento?Evento=S&PlanId=<%=planId%>">Entregar</a>
			<%	}else{%>
				<a class="btn btn-danger btn-sm" href="grabarEvento?Evento=N&PlanId=<%=planId%>">Entregado</a>
			<%	}%>
			</td>
		</tr>		
	</table>
	<br>
	<br>
	<table style="width:70%" border="1" align="center"   bordercolor="#000000" class="tabla">
		<tr>
			<td align="center" colspan="3"> <strong>COMPROBANTE DE ENTREGA Y DEVOLUCION<br> DE ATUENDOS DE GRADUACION</strong><br>
				Costos de reposición por extravío o daños
			</td>
		</tr>
		<tr>
			<td align="right" colspan="2">Recibí&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td align="center">Costo</td>
		</tr>
		<tr>
			<td width="50%">TOGA POSGRADO</td>
			<td width="25%"></td>
			<td align="center" width="25%">$700.00</td>
		</tr>
		<tr>
			<td width="50%">TOGA PREGRADO</td>
			<td width="25%"></td>
			<td align="center" width="25%">$700.00</td>
		</tr>
		<tr>
			<td width="50%">BORLA Y MEDALLA</td>
			<td width="25%"></td>
			<td align="center" width="25%">$300.00</td>
		</tr>
		<tr>
			<td width="50%">ESTOLA</td>
			<td width="25%"></td>
			<td align="center" width="25%">$600.00</td>
		</tr>		
		<tr>
			<td width="50%">BIRRETE</td>
			<td width="25%"></td>
			<td align="center" width="25%">$450.00</td>
		</tr>
		<tr>
			<td width="50%">CUELLO</td>
			<td width="25%"></td>
			<td align="center" width="25%">$250.00</td>
		</tr>
		<tr>
			<td width="50%">ESCLAVINA MAESTRIA</td>
			<td width="25%"></td>
			<td align="center" width="25%">$1,000.00</td>
		</tr>
		<tr>
			<td align="left" colspan="3">		
				<ul>
         			<li> La BORLA Y MEDALLA es para el estudiante.</li>
         			<li> Devolución: Durante una hora al terminar la ceremonia de colación en el loby de la iglesia universitaria.</li>
         			<li> Se aplicará un cobro de recargo por el primer día de demora en la devolución por $50.00 y $10.00 por día adicional.</li>
				</ul>			
			</td>
		</tr>
		<tr>
			<td align="center" colspan="3"><br><br><br> <strong>_____________________________<br> Firma del alumno</strong><br><br>
			Garantía:&nbsp; &nbsp; [ &nbsp; ] INE&nbsp;&nbsp;&nbsp;&nbsp;[ &nbsp; ] Licencia &nbsp; &nbsp; [ &nbsp; ] Otro. Especifique:_________________________<br><br>
			</td>
		</tr>
	</table>
	<br>
</div>
</body>
</html>