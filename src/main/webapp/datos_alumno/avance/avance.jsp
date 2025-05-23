<%@ include file="../../con_enoc.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@ page import="java.text.*"%>

<jsp:useBean id="Archivo" scope="page" class="aca.archivo.ArchivoUtil" />
<jsp:useBean id="AlumPlan" scope="page" class="aca.alumno.AlumPlan" />
<jsp:useBean id="AlumPlanU" scope="page" class="aca.alumno.PlanUtil" />
<jsp:useBean id="Parametros" scope="page" class="aca.parametros.Parametros"/>

<script type="text/javascript">
	function recarga(){	
  		document.location.href="avance?PlanId="+document.frmGraduando.PlanId.value;
	}
</script>
<%
	DecimalFormat getformato = new DecimalFormat(
			"###,##0.00;-###,##0.00");

	String codigoAlumno = (String) session.getAttribute("codigoAlumno");
	String nombreAlumno = "";
	String plan = request.getParameter("PlanId");
	String institucion = (String)session.getAttribute("institucion");
	
	Parametros.mapeaRegId(conEnoc, "1");

	String carrera = "";
	String modalidad = "";
	String residencia = "";

	String documentos = "";
	String finanzas = "";
	String registro = "";
	String avance = "";
	String permiso = "";
	String evento = "";

	float crdPlan = 0;
	float crdAlumno = 0;
	double porcentaje = 0;
	double saldo = 0;

	int compPlan = 0;
	int compAlumno = 0;
	ArrayList lisAvance = new ArrayList();
	ArrayList<String> planes = AlumPlanU.getPlanesAlumno(conEnoc, codigoAlumno);

	if (plan == null) {
		plan = aca.alumno.PlanUtil.getPlanActual(conEnoc, codigoAlumno);
	}
	nombreAlumno = aca.alumno.AlumUtil.getNombreAlumno(conEnoc, codigoAlumno, "NOMBRE");
	carrera = aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc, aca.plan.PlanUtil.getCarreraId(conEnoc, plan));
	modalidad = aca.alumno.AcademicoUtil.getModalidad(conEnoc, codigoAlumno);
	residencia = aca.alumno.AcademicoUtil.getResidencia(conEnoc, codigoAlumno);

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

.para1 {
	margin-top: -400px;
}

.para2 {
	margin-top: 100px;
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
<div align="right"><br>
</div>
<div align="center" class="para2"><img alt="" 
	src="../../imagenes/logo_fondo.jpg" width=319 height="305"></div>
<div CLASS="para1">
<form name="frmGraduando">
<table style="width:70%"  align="center">
	<tr>
		<td align="center"><strong><br>
		<br>
		<%=institucion%>, A. C. </strong></td>
	</tr>
	<tr>
		<td align="center"><strong>Avance de Graduaci&oacute;n </strong></td>
	</tr>
	<tr>
		<td height="15" align="center"></td>
	</tr>
	<tr>
		<td align="center"><select name="PlanId"
			onchange="javascript:recarga()">
			<%
				for (int i = 0; i < planes.size(); i++) {
					if (plan == null && i == 0)
						plan = (String) planes.get(i);
			%>
			<option value='<%=(String)planes.get(i)%>'
				<%if(plan.equals((String)planes.get(i))){out.print("Selected");}else{out.print(plan.equals("")?((String)planes.get(i)).equals(plan)?"Selected":"":"");}%>><%=aca.plan.PlanUtil.getNombrePlan(conEnoc,
								(String) planes.get(i))%></option>
			<%
				}
			%>
		</select></td>
	</tr>
	<tr>
		<th align="center">Alumno: [ <%=codigoAlumno%> ] [ <%=nombreAlumno%>
		]</th>
	</tr>
	<tr>
		<td align="center">[ <%=plan%> ] [ <%=carrera%> ] [ <%=modalidad%>
		] [ <%=residencia%> ]</td>
	</tr>
	<tr>
		<td align="center">&nbsp;</td>
	</tr>
	<tr>
		<td align="center">&nbsp;</td>
	</tr>
</table>
</form>
<%
	try {
		documentos = Archivo.autorizaAlumno(conEnoc, codigoAlumno);
		saldo = aca.financiero.EstadoCuenta.saldoAlumnoD(conEnoc, codigoAlumno);
		if (saldo >= -11) {
			finanzas = "Autorizado..¡¡" + " saldo = " + getformato.format(saldo);
		} else {
			finanzas = "No cumple..¡¡" + " saldo = " + getformato.format(saldo);
		}

		crdPlan = aca.plan.CreditoUtil.creditosPlan(conEnoc, plan);
		crdAlumno = aca.vista.AlumnoCursoUtil.creditosAprobados(conEnoc, codigoAlumno, plan);	
		porcentaje = (double) crdAlumno / crdPlan * 100;

		compPlan = aca.plan.CursoUtil.cuentaMaterias(conEnoc, plan, "3");
		compAlumno = aca.vista.AlumnoCursoUtil.materiasAlumno(conEnoc, codigoAlumno, plan, "3");

		// AUTORIZACIÓN DE AVANCE DE KARDEX ( OFICINA DE REGISTRO )
		avance = aca.alumno.PlanUtil.getAvanceId(conEnoc, codigoAlumno, plan);
		registro = aca.catalogo.AvanceUtil.getNombreAvance(conEnoc, avance);
		permiso = aca.alumno.PlanUtil.getPermiso(conEnoc, codigoAlumno, plan);
		if (permiso.equals("S")) {
			permiso = "Autorizado..¡¡";
		} else {
			permiso = "Sin Permiso..¡¡";
		}

		// AUTORIZACIÓN PARA EVENTO
		evento = aca.alumno.PlanUtil.getEvento(conEnoc, codigoAlumno, plan);
		//if (sEvento.equals("S")){ sEvento = "Autorizado..¡¡"; }		

	} catch (Exception e) {
		System.out.println(e.toString());
	}
%>
<table style="width:77%" border="1" align="center" 
	 bordercolor="#000000">
	<tr height="20">
		<td align="left" colspan="2"><strong>&nbsp;Documentos:</strong></td>
		<td width="66%" align="left">&nbsp;<%=documentos%></td>
	</tr>
	<tr height="20">
		<td align="left" colspan="2"><strong>&nbsp;Finanzas:</strong></td>
		<td align="left">&nbsp;<%=finanzas%></td>
	</tr>
	<tr height="20">
		<td align="left" colspan="2"><strong>&nbsp;Registro:</strong></td>
		<td align="left">&nbsp;<%=permiso%> &nbsp; [ <%=registro%> ]</td>
	</tr>
	<tr height="20">
		<td width="5%" align="center"><strong>*</strong></td>
		<td width="29%" align="left">&nbsp;Plan: <%=plan%> ( Creditos <%=crdPlan%>)</td>
		<td align="left">&nbsp;Crd. Aprobados: <%=crdAlumno%> --
		Equivale: <%=getformato.format(porcentaje)%>%</td>
	</tr>
	<tr height="20">
		<td align="center"><strong>*</strong></td>
		<td align="left">&nbsp;Componentes ( <%=compPlan%>)</td>
		<td align="left">&nbsp;Aprobados: <%=compAlumno%></td>
	</tr>
	<form name="permiso" method="post" action="grabar">
	<tr>
		<td align="left" colspan="2"><strong>&nbsp;Avance de
		Estudios:</strong></td>
		<td align="left"><select name="avance"
			onChange="document.permiso.submit()">
			<%
				aca.catalogo.AvanceUtil avanceU = new aca.catalogo.AvanceUtil();
				lisAvance = avanceU.getListAll(conEnoc, "ORDER BY 2");
				for (int i = 0; i < lisAvance.size(); i++) {
					aca.catalogo.CatAvance avan = (aca.catalogo.CatAvance) lisAvance.get(i);
					if (avan.getAvanceId().equals(avance)) {
						out.print(" <option value='" + avan.getAvanceId()
								+ "' Selected>" + avan.getNombreAvance()
								+ "</option>");
					} else {
						out.print(" <option value='" + avan.getAvanceId() + "'>"
								+ avan.getNombreAvance() + "</option>");
					}
				}
				lisAvance = null;
				avanceU = null;
			%>
		</select></td>
	<tr>
	<tr>
		<td align="left" colspan="2"><strong>&nbsp;Permiso al
		Evento:</strong></td>
		<td align="left"><select name="evento"
			onChange="document.permiso.submit()">
			<%
				if (evento.equals("S")) {
			%>
			<option value="S" selected>Autorizado</option>
			<option value="N">Sin Permiso</option>
			<%
				} else {
			%>
			<option value="S">Autorizado</option>
			<option value="N" selected>Sin Permiso</option>
			<%
				}
			%>
		</select> <input name="codigoPersonal" type="hidden" id="codigoPersonal"
			value="<%=codigoAlumno%>"> <input name="plan" type="hidden"
			id="plan" value="<%=plan%>"></td>
	</tr>
	<tr>
		<td align="center" colspan="7"><input type="submit" name="Grabar"
			value="Grabar"></td>
	</tr>
	</form>
</table>
<br>
<table style="width:70%" align="center"  >
	<tr>
		<td width="34%" align="center"><strong><br>
		<br>
		__________________________________</strong></td>
	</tr>
	<tr>
		<td align="center"><%=Parametros.getCardex()%></td>
	</tr>
	<tr>
		<td align="center"><strong>Directora de Registro</strong></td>
	</tr>
</table>
</div>
</body>
</html>
<%@ include file="../../cierra_enoc.jsp"%>