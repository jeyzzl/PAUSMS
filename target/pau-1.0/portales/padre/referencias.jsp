<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="AlumReferencia" scope="page" class="aca.alumno.AlumReferencia"/>
<jsp:useBean id="AlumReferenciaU" scope="page" class="aca.alumno.AlumReferenciaUtil"/>
<jsp:useBean id="Parametros" scope="page" class="aca.parametros.Parametros"/>

<%-- <jsp:include page="../menuPadre.jsp" /> --%>
<%@ include file= "../menuPadre.jsp" %>

<html>
	<head></head>
<%
	String matricula		= session.getAttribute("codigoAlumno").toString();

	boolean tieneScotiabank	= false;
	boolean tieneSantander 	= false;
	// Actualización hecha el 26-11-2014, actualmente Scotiabank y Santander utilizan el mismo algoritmo para obtener el digito verificador.
	String digito 			= aca.alumno.AlumReferenciaUtil.generarReferenciaSantander(matricula);
	
	AlumReferencia.setCodigoPersonal(matricula);
	if (AlumReferenciaU.existeReg(conEnoc, matricula) ){
		AlumReferencia.mapeaRegId(conEnoc, matricula);
		tieneScotiabank = AlumReferencia.getScotiabank()!=null&&!AlumReferencia.getScotiabank().equals("");
		tieneSantander = AlumReferencia.getSantander()!=null&&!AlumReferencia.getSantander().equals("-");
		//System.out.println(" Ya tiene digito verificador "+matricula+" Scotiabank: "+tieneScotiabank+" Santander:"+tieneSantander);
		
		if (tieneScotiabank==false || tieneSantander ==false){			
			// Se actualiza el que no exista
			if (tieneScotiabank==false) AlumReferencia.setScotiabank(digito);
			if (tieneSantander==false) AlumReferencia.setSantander(digito);
			if (AlumReferenciaU.updateReg(conEnoc, AlumReferencia)){
				tieneScotiabank = true;
				tieneSantander 	= true;
			}
		}
	}else{
		
		AlumReferencia.setBanamex("-");
		AlumReferencia.setScotiabank(digito);
		AlumReferencia.setSantander(digito);
		if (AlumReferenciaU.insertReg(conEnoc, AlumReferencia)){
			//System.out.println("Inserte el digito verificador"+matricula+":"+digito);
			tieneScotiabank = true;
			tieneSantander 	= true;			
		}
	}
	
	Parametros.mapeaRegId(conEnoc, "1");
%>
<body>
	<br>
	<table style="margin: 0 auto;" style=" width:90%;">
		<tr>
			<td>
				<table style="margin: 0 auto;">
				<tr>		
				<td>
					<img src="../../imagenes/logoColor.jpg" width="90"> &nbsp; &nbsp;
				</td>
				<td>
					<font size="6"><%=Parametros.getInstitucion()%> A.C.</font><br>&nbsp;
					<font size="4"><b>Cuentas de banco para pagos de pagarés referenciados.</b></font>
				</td>
				</tr>
				</table>
			</td>		
		</tr>
		<tr>			
			<td align="center">
				<font size="3">Alumno: <b><%=aca.vista.UsuariosUtil.getNombreUsuario(conEnoc, matricula, "NOMBRE") %></b> </font>
			</td>			
		</tr>
		<tr>
			<td align="center">
				&nbsp;<br><br><font size="3">Depósito a nombre de: <u><b><%=Parametros.getInstitucion().toUpperCase()%> A.C.</b></u></font><br><br><br>&nbsp;
			</td>
		</tr>
	</table>	
	<table style="margin: 0 auto;" style=" width:90%;">
		<tr>
			<td style="width:33%">
				<table style=" margin: 0 auto; border-collapse: separate; border-spacing: 2px; ">
					<tr><td class="center" colspan="2"><img src="../../imagenes/logo-scotiabank.jpg" width="150"></td></tr>
					<tr><td style="text-align:center; font-size:10pt; height:30px"" colspan="2">Depósito en ventanilla</td></tr>
<%			if(tieneScotiabank){%>
					<tr>
						<td style="text-align:center; border:1px solid; font-size:12pt;">&nbsp;<b>25300000361</b>&nbsp;</td>
						<td style="text-align:center; border:1px solid; font-size:12pt;">&nbsp;<b><%=matricula+AlumReferencia.getScotiabank()%></b>&nbsp;</td>
					</tr>
					<tr>
						<td class="center"><spring:message code="aca.Cuenta"/></td>
						<td class="center">Referencia</td>
					</tr>				
<%			}else{
				out.print("<tr><td align='center' colspan='2'>Información no disponible</td></tr>");
			}
%>					
				</table>
			</td>
			<td style="width:33%">
				<table style=" margin: 0 auto; border-collapse: separate; border-spacing: 2px; ">
					<tr><td class="center" colspan="3"><img src="../../imagenes/logo-santander.png" width="150"></td></tr>
					<tr><td style="text-align:center; font-size:10pt; height:30px"" colspan="3">Depósito en ventanilla</td></tr>
<%			if(tieneSantander){%>
					<tr>
						<td style="text-align:center; border:1px solid; font-size:12pt;">&nbsp;<b>5256</b>&nbsp;</td>
						<td style="text-align:center; border:1px solid; font-size:12pt;">&nbsp;<b>92001950195</b>&nbsp;</td>
						<td style="text-align:center; border:1px solid; font-size:12pt;">&nbsp;<b><%=matricula+AlumReferencia.getScotiabank()%></b>&nbsp;</td>
					</tr>
					<tr>
						<td class="center">Convenio</td>
						<td class="center"><spring:message code="aca.Cuenta"/></td>
						<td class="center">Referencia</td>
					</tr>				
<%			}else{
				out.print("<tr><td align='center' colspan='3'>Información no disponible</td></tr>");
			}
%>					
				</table>
			</td>
			<td style="width:34%">
				<table style=" margin: 0 auto; border-collapse: separate; border-spacing: 2px; ">
					<tr><td class="center" colspan="4"><img src="../../imagenes/logo-santander.png" width="150"></td></tr>
					<tr><td style="text-align:center; font-size:10pt; height:30px"" colspan="4">Transferencia Electrónica</td></tr>
<%			if(tieneSantander){%>
					<tr>
						<td style="text-align:center; border:1px solid; font-size:12pt;">&nbsp;<b>5256</b>&nbsp;</td>
						<td style="text-align:center; border:1px solid; font-size:12pt;">&nbsp;<b>014597920019501959</b>&nbsp;</td>
						<td style="text-align:center; border:1px solid; font-size:12pt;">&nbsp;<b><%=matricula+AlumReferencia.getScotiabank()%></b>&nbsp;</td>
						<td style="text-align:center; border:1px solid; font-size:12pt;">&nbsp;<b>9999</b>&nbsp;</td>
					</tr>
					<tr>
						<td class="center">Convenio</td>
						<td class="center">Clave Interbancaria</td>
						<td class="center">Referencia</td>
						<td class="center">Concepto</td>
					</tr>				
<%			}else{
				out.print("<tr><td align='center' colspan='3'>Información no disponible</td></tr>");
			}
%>					
				</table>
			</td>
		</tr>
	</table>
</body>
<script>$('.nav-tabs').find('.finanzas').addClass('active');</script>
</html>
<%@ include file= "../../cierra_enoc.jsp" %>