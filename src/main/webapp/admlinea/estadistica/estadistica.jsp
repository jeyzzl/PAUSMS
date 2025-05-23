<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.text.DecimalFormat"%>

<html>
	<head></head>
<%
	DecimalFormat formato = new DecimalFormat("###,##0.0;-###,##0.0");

	String year = (String) request.getAttribute("year");
	
	List<Integer> listaAnios = (List<Integer>) request.getAttribute("listaAnios");
	List<Integer> listaMeses = (List<Integer>) request.getAttribute("listaMeses");
	
	HashMap<String, int[]> catidadPorMeses = (HashMap<String, int[]>) request.getAttribute("catidadPorMeses");
%>
	<body>
		<div class="container-fluid">
			<h1><spring:message code='aca.EstadisticaDeAdmisionEnLinea'/></h1>
			<div class="alert alert-info d-flex align-items-center">
				<a href="menu" class="btn btn-primary">Return</a>&nbsp;
				<b><spring:message code='aca.AspirantesDel'/></b>&nbsp;
				<select id="Year" name="anio" onchange="document.location.href='estadistica?Year='+document.getElementById('Year').value;" class="form-control" style="width:120px">
				<%	for(int yearTemp : listaAnios){ %>
					<option <%=yearTemp == Integer.parseInt(year) ? "Selected" : "" %> value="<%=yearTemp%>"><%=yearTemp%></option>
				<%	}%>
				</select>
			</div>
			<table style="width:50%">
				<tr>
					<td>
						
					</td>
				</tr>
			</table>
<%		int cantidadReg = (int) request.getAttribute("cantidadReg");
		int cantidadSol = (int) request.getAttribute("cantidadSol");
		int cantidadDoc = (int) request.getAttribute("cantidadDoc");
		int cantidadAdm = (int) request.getAttribute("cantidadAdm");
		int cantidadCar = (int) request.getAttribute("cantidadCar");%>
			<table style="width:70%" class="table table-sm table-condensed">
				<tr>
					<th width="20%"><spring:message code='aca.Procesos'/></th>
					<th width="20%" class="text-center";><spring:message code='aca.Cantidad'/></th>
					<th width="60%" >&nbsp;</th>
				</tr>
				<tr>
					<td><spring:message code='aca.Se'/> <b><spring:message code='aca.Registraron'/></b></td>
					<td style="text-align:center"><b><%=cantidadReg %></b></td>
					<td style="text-align:center"; >&nbsp;</td>
				</tr>
				<tr>
					<td><spring:message code='aca.Enviaron'/> <b><spring:message code='aca.Solicitud'/></b></td>
					<td style="text-align:center;"><b><%=cantidadSol %></b></td>
					<td style="text-align:center">&nbsp;</td>
				</tr>
				<tr>
					<td>
						<a href="documentos?Year=<%=year%>">
						<spring:message code='aca.Autorizaron'/>&nbsp;<b><spring:message code='aca.Documentos'/></b>
						</a>
					</td>
					<td style="text-align:center;">
						<a href="documentos?Year=<%=year%>">
						<b><%=cantidadDoc %></b>
						</a>
					</td>
					<td >&nbsp;</td>
				</tr>
				<tr>
					<td>
						<a href="aceptados?Year=<%=year%>">
						<spring:message code='aca.Fueron'/>&nbsp;<b><spring:message code='aca.Admitidos'/></b>
						</a>
					</td>
					<td style="text-align:center;">
						<a href="aceptados?Year=<%=year%>">
							<b><%=cantidadAdm %></b>
						</a>
					</td>
					<td class="center">&nbsp;</td>
				</tr>
				<tr>
					<td><spring:message code='aca.Revisaron'/> <b><spring:message code='aca.Carta'/></b> <spring:message code='aca.DeAdmision'/></td>
					<td style="text-align:center;">
					<b><%=cantidadCar %></b>
					</td>
					<td class="right">&nbsp;</td>
				</tr>
			</table><br>
			<table style="width:70%" class="table table-sm table-bordered">
				<tr>
					<th class="left"><spring:message code='aca.Mes'/></th>
					<th class="right"><spring:message code='aca.Se'/> <b><spring:message code='aca.Registraron'/></b></th>
					<th class="right"><spring:message code='aca.Enviaron'/> <b><spring:message code='aca.Solicitud'/></b></th>
					<th class="right"><spring:message code='aca.Autorizaron'/> <b><spring:message code='aca.Documentos'/></b></th>
					<th class="right"><spring:message code='aca.Fueron'/> <b><spring:message code='aca.Admitidos'/></b></th>
					<th class="right"><spring:message code='aca.Revisaron'/> <b><spring:message code='aca.Carta'/> </b><spring:message code='aca.DeAdmision'/></th>
				</tr>
<%			for(int numMes : listaMeses){
				String tmpMes = numMes < 10 ? "0"+numMes : ""+numMes;
				String mes = aca.util.Fecha.getMesNombre(numMes);
				
				int cantidadRegMes = 0;
				int cantidadSolMes = 0;
				int cantidadDocMes = 0;
				int cantidadAdmMes = 0;
				int cantidadCarMes = 0;
				
				if(catidadPorMeses.containsKey(mes)){
					cantidadRegMes = catidadPorMeses.get(mes)[0];
					cantidadSolMes = catidadPorMeses.get(mes)[1];
					cantidadDocMes = catidadPorMeses.get(mes)[2];
					cantidadAdmMes = catidadPorMeses.get(mes)[3];
					cantidadCarMes = catidadPorMeses.get(mes)[4];
				}%>
				<tr>
					<td class="left"><font size="3"><b><%=mes %></b></font></td>
					<td style="text-align:center;">
<% 					if(cantidadRegMes > 0){%>
						<a href="cantidadRegMes?Year=<%=year%>&Mes=<%=tmpMes%>"><b><%=cantidadRegMes %></b></a>
<% 					}else{%>
						<b><%=cantidadRegMes %></b>
<% 					}%>
					</td>
					<td style="text-align:right;">
<% 					if(cantidadSolMes > 0){%>
						<a href="cantidadSolMes?Year=<%=year%>&Mes=<%=tmpMes%>"><b><%=cantidadSolMes %></b></a>
<% 					}else{%>
						<b><%=cantidadSolMes %></b>
<% 					}%>
					</td>
					<td style="text-align:right;">
<% 					if(cantidadDocMes > 0){%>
						<a href="cantidadDocMes?Year=<%=year%>&Mes=<%=tmpMes%>"><b><%=cantidadDocMes %></b></a>
<% 					}else{%>
						<b><%=cantidadDocMes %></b>
<% 					}%>
					</td>
					<td style="text-align:right;">
<% 					if(cantidadAdmMes > 0){%>
						<a href="cantidadAdmMes?Year=<%=year%>&Mes=<%=tmpMes%>"><b><%=cantidadAdmMes %></b></a>
<% 					}else{%>
						<b><%=cantidadAdmMes %></b>
<% 					}%>
					</td>
					<td style="text-align:right;">
<% 					if(cantidadCarMes > 0){%>
						<a href="cantidadCarMes?Year=<%=year%>&Mes=<%=tmpMes%>"><b><%=cantidadCarMes %></b></a>
<% 					}else{%>
						<b><%=cantidadCarMes %></b>
<% 					}%>
					</td>
				</tr>
<%			} %>
			</table>
		</div>
	</body>
</html>