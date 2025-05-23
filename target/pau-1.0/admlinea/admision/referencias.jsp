<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.alumno.spring.AlumReferencia"%>
<%@page import="aca.alumno.spring.AlumPlan"%>
<%@page import="aca.plan.spring.MapaPlan"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<html>
	<head>
	<script type="text/javascript">
		function ActualizarPlan(){  		
	  		document.frmPlanes.submit();
		}	
	</script>
	</head>
<%	
	String folio 				= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
	String referencia			= (String) request.getAttribute("referencia");
	String numCuenta			= (String) request.getAttribute("numCuenta");
	String nombre				= (String) request.getAttribute("nombre");
	String institucion			= (String) request.getAttribute("institucion");
	boolean tieneScotiabank		= (Boolean) request.getAttribute("tieneScotiabank");
	boolean tieneSantander 		= (Boolean) request.getAttribute("tieneSantander");
	boolean tieneBanorte 		= (Boolean) request.getAttribute("tieneBanorte");
	boolean esCovoprom 			= (Boolean) request.getAttribute("esCovoprom");
	String clabeScotiabank		= esCovoprom?"044597253000656097":"044597253000003611";
	
	// Actualización hecha el 26-11-2014, actualmente Scotiabank y Santander utilizan el mismo algoritmo para obtener el digito verificador.
	String cuenta 				= (String) request.getAttribute("cuenta");
	AlumReferencia alumno 		= (AlumReferencia) request.getAttribute("alumno");	
	
	String referenciaLinea = folio;
	String cero = "0";
	while(referenciaLinea.length() < 7){
		referenciaLinea = cero+referenciaLinea;
	}
%>
<body>
<div class="container-fluid">
	<h2>Referencias Bancarias<small class="text-muted fs-5">(Alumno: <b><%=nombre%></b>)</small></h2>		
	<div class="alert alert-success">		
		<a href="mostrarProceso?Folio=<%=folio%>" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
	
	<table style="margin: 0 auto;" style=" width:90%;">
		<tr>
			<td>
				<table style="margin: 0 auto;">
					<tr>		
<!-- 						<td> -->
<%-- 						<%	if (esCovoprom==false){%> --%>
<!-- 							<img src="../../imagenes/logoColor.jpg" width="90"> &nbsp; &nbsp; -->
<%-- 						<%	}else{%> --%>
<!-- 							<img src="../../imagenes/logoCovoprom.png" width="90"> &nbsp; &nbsp; -->
<%-- 						<%	}%>	 --%>
<!-- 						</td> -->
						<td>
							<font size="6">
						<%	if (esCovoprom==false){%>
							<%=institucion%> A.C.
						<%	}else{%>
							COLEGIO VOCACIONAL Y PROFESIONAL MONTEMORELOS A.C
						<%	}%>
							</font><br>&nbsp;
						</td>						
					</tr>
				</table>
			</td>		
		</tr>	
	</table>	
	<div class="row">
		<div class="col-sm-4"></div>
		<div class="col-sm-6">
			<div class="card bg-light text-center"  style="width: 600px;">
				<div class="card-header"><img src="../../imagenes/url.png" width="50px"> Pago en línea<small class="text-muted"> ( ¡ Mejor opción ! ) </small></div>
				<div class="card-body">
					<p style="font-size: 14px">
						<em>Comentario:</em>
					</p>
					<p class="card-text">
						El pago en línea es la mejor opción, porque el movimiento se refleja inmediatamente en tu estado de cuenta.
						Tu referencia en línea es <span class="badge badge-info"><%=referenciaLinea%></span>.
						Haz clic en la siguiente liga <a href="https://www.um.edu.mx/pagos/pago-en-linea/" target="_blank">https://www.um.edu.mx/pago-en-linea/</a>.
					</p>		
				</div>
			</div>
		</div>
	</div>
	<table style="margin: 0 auto;" style=" width:90%;">
		<tr>
			<td>
				<table style="margin: 0 auto;">
					<tr>		
						<td>
							<font size="6">
							</font><br>&nbsp;
							<font size="4"><b>Cuentas de banco para pagos de pagarés referenciados.</b></font>
						</td>						
					</tr>
				</table>
			</td>		
		</tr>	
		<tr>
			<td align="center">&nbsp;<font size="3">Depósito a nombre de:
				<u><b>
				<%	if (esCovoprom==false){%>
					<%=institucion%> A.C.
				<%	}else{%>
					COLEGIO VOCACIONAL Y PROFESIONAL MONTEMORELOS A.C
				<%	}%>				
				</b>
				</u></font><br>&nbsp;
			</td>
		</tr>
	</table>
	<table style="margin: 0 auto;" style=" width:90%;">
		<tr>
			<td style="width:33%">
				<table style=" margin: 0 auto; border-collapse: separate; border-spacing: 2px; ">
					<tr><td class="center" colspan="2"><br><img src="../../imagenes/logo-scotiabank.jpg" width="150"><br></td></tr>
					<tr><td style="text-align:center; font-size:10pt; height:30px" colspan="2">Depósito en ventanilla</td></tr>
<%			if(tieneScotiabank){%>
					<tr>
						<td style="text-align:center; border:1px solid; font-size:12pt;">&nbsp;<b><%= numCuenta %></b>&nbsp;</td>
						<td style="text-align:center; border:1px solid; font-size:12pt;">&nbsp;<b><%=referencia%></b>&nbsp;</td>
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
					<tr><td class="center" colspan="2"><br><img src="../../imagenes/logo-scotiabank.jpg" width="150"><br></td></tr>
					<tr><td style="text-align:center; font-size:10pt; height:30px" colspan="2">Transferencia Electrónica</td></tr>
<%			if(tieneScotiabank){%>
					<tr>												
						<td style="text-align:center; border:1px solid; font-size:12pt;">&nbsp;<b><%=clabeScotiabank%></b>&nbsp;</td>
						<td style="text-align:center; border:1px solid; font-size:12pt;">&nbsp;<b><%=referencia%></b>&nbsp;</td>
					</tr>
					<tr>												
						<td class="center">Clabe Interbancaria</td>
						<td class="center">Referencia</td>
					</tr>				
<%			}else{
				out.print("<tr><td align='center' colspan='2'>Información no disponible</td></tr>");
			}
%>					
				</table>
			</td>
		</tr>
		<tr>
			<td style="width:33%">
<%			if(tieneSantander){%>
				<table style=" margin: 0 auto; border-collapse: separate; border-spacing: 2px; ">
					<tr><td class="center" colspan="3"><img src="../../imagenes/logo-santander.png" width="150"></td></tr>
					<tr><td style="text-align:center; font-size:10pt; height:25px" colspan="3">Depósito en ventanilla</td></tr>
<%				if(tieneSantander){/*5256*/%>
					<tr>
						<td style="text-align:center; border:1px solid; font-size:12pt;">&nbsp;<b>8592</b>&nbsp;</td>
						<td style="text-align:center; border:1px solid; font-size:12pt;">&nbsp;<b>92001950195</b>&nbsp;</td>
						<td style="text-align:center; border:1px solid; font-size:12pt;">&nbsp;<b><%=referencia%></b>&nbsp;</td>
					</tr>
					<tr>
						<td class="center">Convenio</td>
						<td class="center"><spring:message code="aca.Cuenta"/></td>
						<td class="center">Referencia</td>
					</tr>				
<%				}else{
					out.print("<tr><td align='center' colspan='3'>Información no disponible</td></tr>");
				}
%>					
				</table>
<%			
			}
%>					
			</td>
			<td style="width:33%">
<%			if(tieneSantander){%>
				<table style=" margin: 0 auto; border-collapse: separate; border-spacing: 2px; ">
					<tr><td class="center" colspan="4"><img src="../../imagenes/logo-santander.png" width="150"></td></tr>
					<tr><td style="text-align:center; font-size:10pt; height:25px" colspan="4">Transferencia Electrónica</td></tr>
<%				if(tieneSantander){%>
					<tr>
						<td style="text-align:center; border:1px solid; font-size:12pt;">&nbsp;<b>8592</b>&nbsp;</td>
						<td style="text-align:center; border:1px solid; font-size:12pt;">&nbsp;<b>014597920019501959</b>&nbsp;</td>
						<td style="text-align:center; border:1px solid; font-size:12pt;">&nbsp;<b><%=referencia%></b>&nbsp;</td>
						<td style="text-align:center; border:1px solid; font-size:12pt;">&nbsp;<b>9999</b>&nbsp;</td>
					</tr>
					<tr>
						<td class="center">Convenio</td>
						<td class="center">Clabe Interbancaria</td>
						<td class="center">Referencia</td>
						<td class="center">Concepto</td>
					</tr>				
<%				}else{
					out.print("<tr><td align='center' colspan='3'>Información no disponible</td></tr>");
				}
%>
				</table>
<%			
			}
%>			
			</td>
		</tr>
		<tr>
			<td style="width:33%">
<%			if(tieneBanorte /*&& ( codigoPersonal.equals("9800308") || codigoPersonal.equals("9800354") )*/ ){%>
				<table style=" margin: 0 auto; border-collapse: separate; border-spacing: 2px; ">
					<tr><td class="center" colspan="3"><img src="../../imagenes/logo-banorte.png" width="150"></td></tr>
					<tr><td style="text-align:center; font-size:10pt; height:25px" colspan="3">Depósito en ventanilla</td></tr>
<%				if(tieneBanorte){/*5256*/%>
					<tr>
						<td style="text-align:center; border:1px solid; font-size:12pt;">&nbsp;<b>150239</b>&nbsp;</td>
						<td style="text-align:center; border:1px solid; font-size:12pt;">&nbsp;<b>1111405753</b>&nbsp;</td>
						<td style="text-align:center; border:1px solid; font-size:12pt;">&nbsp;<b><%=referencia%></b>&nbsp;</td>
					</tr>
					<tr>
						<td class="center"><spring:message code="aca.Cuenta"/> Emisora</td>
						<td class="center"><spring:message code="aca.Cuenta"/></td>
						<td class="center">Referencia</td>
					</tr>				
<%				}else{
				out.print("<tr><td align='center' colspan='3'>Información no disponible</td></tr>");
				}
%>			
				</table>
<%			
			}
%>			
			</td>
			<td style="width:34%">
<%			if(tieneBanorte){%>
				<table style=" margin: 0 auto; border-collapse: separate; border-spacing: 2px; ">
					<tr><td class="center" colspan="4"><img src="../../imagenes/logo-banorte.png" width="150"></td></tr>
					<tr><td style="text-align:center; font-size:10pt; height:25px" colspan="4">Transferencia Electrónica</td></tr>
<%				if(tieneBanorte){%>
					<tr>
						<td style="text-align:center; border:1px solid; font-size:12pt;">&nbsp;<b>150239</b>&nbsp;</td>
						<td style="text-align:center; border:1px solid; font-size:12pt;">&nbsp;<b>072597011114057534</b>&nbsp;</td>
						<td style="text-align:center; border:1px solid; font-size:12pt;">&nbsp;<b><%=referencia%></b>&nbsp;</td>						
					</tr>
					<tr>
						<td class="center"><spring:message code="aca.Cuenta"/> Emisora</td>
						<td class="center">Clabe Interbancaria</td>
						<td class="center">Referencia</td>						
					</tr>				
<%				}else{
					out.print("<tr><td align='center' colspan='3'>Información no disponible</td></tr>");
				}
				out.print("</table>");
%>			
				</table>
<%			
			}
%>				
			</td>
		</tr>
	</table>
</div>	
</body>
</html>