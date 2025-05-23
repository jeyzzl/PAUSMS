<%@ include file= "../../con_enoc.jsp"%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import="java.text.DecimalFormat"%>

<jsp:useBean id="AdmSolicitud" scope="page" class="aca.admision.AdmSolicitud" />
<jsp:useBean id="AdmEncuesta" scope="page" class="aca.admision.AdmEncuesta" />
<jsp:useBean id="AdmRecomienda" scope="page" class="aca.admision.AdmRecomienda" />

<html>
	<head></head>
<%
	DecimalFormat formatoDecimal= new DecimalFormat("###,##0.0; -###,##0.0");

	String folio = request.getParameter("Folio");

	AdmSolicitud.mapeaRegId(conEnoc, folio);

	String colorBien 	= "#87CB5B";
	String colorMal 	= "#FF8181";
	String color23 		= "#FAFF8E";
%>
	<body>
	<div class="container-fluid">
	<h2>Recomendaciones de <%=AdmSolicitud.getNombre()+" "+(AdmSolicitud.getApellidoMaterno()==null?"":AdmSolicitud.getApellidoMaterno())+" "+AdmSolicitud.getApellidoPaterno() %></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="mostrarProceso?Folio=<%=folio %>"><spring:message code="aca.Regresar"/></a>
	</div>
		<table style="margin: 0 auto;  width:100%">
			<tr>
				<td>
					<table class="tabla" align="center" width="7%">
						<tr>
							<th><spring:message code="aca.Recomendado"/></th>
						</tr>
						<tr>
							<td id="tdPorciento" align="center" class="th2"><div id="divPorciento"></div></td>
						</tr>
					</table>
		</table>
		<br>
		<table class="tabla" align="center" width="80%">
			<tr>
				<th width="22%"><spring:message code="aca.Recomendantes"/></th>
				<th width="5%" class="ayuda mensaje <%="Motivaci�n y perseverancia" %>">1</th>
				<th width="5%" class="ayuda mensaje <%="Confiabilidad (realiza su trabajo concienzudamente y con responsabilidad)" %>">2</th>
				<th width="5%" class="ayuda mensaje <%="Habilidad Intelectual" %>">3</th>
				<th width="5%" class="ayuda mensaje <%="Integridad" %>">4</th>
				<th width="5%" class="ayuda mensaje <%="Estabilidad emocional" %>">5</th>
				<th width="5%" class="ayuda mensaje <%="Empat�a: muestra sensibilidad hacia las necesidades de otros, consideraci�n y tacto" %>">6</th>
				<th width="5%" class="ayuda mensaje <%="Consistencia del desempe�o acad�mico" %>">7</th>
				<th width="5%" class="ayuda mensaje <%="Madurez (personalidad equilibrada, habilidad para enfrentarse a las situaciones de la vida diaria)" %>">8</th>
				<th width="5%" class="ayuda mensaje <%="Disposici�n para acatar reglas" %>">9</th>
				<th width="5%" class="ayuda mensaje <%="Las amistades que frecuenta son personas de buena reputaci�n y principios morales" %>">10</th>
				<th width="5%" class="ayuda mensaje <%="Apariencia personal (en ambos sexos evaluar nitidez y limpieza, en el sexo femenino adem�s recato y decencia en su vestimenta)." %>">11</th>
				<th width="5%" class="ayuda mensaje <%="Relaciones Sociales" %>">12</th>
				<th width="5%" class="ayuda mensaje <%="Inter�s por el aspecto espiritual" %>">13</th>
				<th width="13%">Promedio recomendantes</th>
			</tr>
		<%	float sumaTotal = 0;
			int cantTotal = 0;
			int cant1 = 0;
			int cant2 = 0;
			int cant3 = 0;
			int cant4 = 0;
			int cant5 = 0;
			int cant6 = 0;
			int cant7 = 0;
			int cant8 = 0;
			int cant9 = 0;
			int cant10 = 0;
			int cant11 = 0;
			int cant12 = 0;
			int cant13 = 0;
			int promedio1 = 0;
			int promedio2 = 0;
			int promedio3 = 0;
			int promedio4 = 0;
			int promedio5 = 0;
			int promedio6 = 0;
			int promedio7 = 0;
			int promedio8 = 0;
			int promedio9 = 0;
			int promedio10 = 0;
			int promedio11 = 0;
			int promedio12 = 0;
			int promedio13 = 0;
		
			for(int i=1; i<=5; i++){
				float promedioR = 0;
				AdmEncuesta.setFolio(folio);
				AdmEncuesta.setRecomendacionId(i+"");
				AdmRecomienda.mapeaRegId(conEnoc, folio, i+"");
				AdmRecomienda.setFolio(folio);
				AdmRecomienda.setRecomendacionId(i+"");
				boolean existe = AdmRecomienda.existeReg(conEnoc);
		%>
				<tr class="tr2">
				<%	if(existe){%>
						<td>&nbsp;<%=i+")&nbsp; <b>"+AdmRecomienda.getNombre()+"</b>" %></td>
				<%	}
					else{%>
						<td>&nbsp;<%=i+")&nbsp; <b>-</b>" %></td>
				<%	}%>
				<%	if(AdmEncuesta.existeReg(conEnoc)){
						int cantR = 0;
						
						AdmEncuesta.mapeaRegId(conEnoc, folio, i+"");
						int num1 = Integer.parseInt(AdmEncuesta.getR1())==5 ? 0 : Integer.parseInt(AdmEncuesta.getR1());
						int num2 = Integer.parseInt(AdmEncuesta.getR2())==5 ? 0 : Integer.parseInt(AdmEncuesta.getR2());
						int num3 = Integer.parseInt(AdmEncuesta.getR3())==5 ? 0 : Integer.parseInt(AdmEncuesta.getR3());
						int num4 = Integer.parseInt(AdmEncuesta.getR4())==5 ? 0 : Integer.parseInt(AdmEncuesta.getR4());
						int num5 = Integer.parseInt(AdmEncuesta.getR5())==5 ? 0 : Integer.parseInt(AdmEncuesta.getR5());
						int num6 = Integer.parseInt(AdmEncuesta.getR6())==5 ? 0 : Integer.parseInt(AdmEncuesta.getR6());
						int num7 = Integer.parseInt(AdmEncuesta.getR7())==5 ? 0 : Integer.parseInt(AdmEncuesta.getR7());
						int num8 = Integer.parseInt(AdmEncuesta.getR8())==5 ? 0 : Integer.parseInt(AdmEncuesta.getR8());
						int num9 = Integer.parseInt(AdmEncuesta.getR9())==5 ? 0 : Integer.parseInt(AdmEncuesta.getR9());
						int num10 = Integer.parseInt(AdmEncuesta.getR10())==5 ? 0 : Integer.parseInt(AdmEncuesta.getR10());
						int num11 = Integer.parseInt(AdmEncuesta.getR11())==5 ? 0 : Integer.parseInt(AdmEncuesta.getR11());
						int num12 = Integer.parseInt(AdmEncuesta.getR12())==5 ? 0 : Integer.parseInt(AdmEncuesta.getR12());
						int num13 = Integer.parseInt(AdmEncuesta.getR13())==5 ? 0 : Integer.parseInt(AdmEncuesta.getR13());

						if(num1!=0){cant1++;promedio1+=num1;cantR++;}
						if(num2!=0){cant2++;promedio2+=num2;cantR++;}
						if(num3!=0){cant3++;promedio3+=num3;cantR++;}
						if(num4!=0){cant4++;promedio4+=num4;cantR++;}
						if(num5!=0){cant5++;promedio5+=num5;cantR++;}
						if(num6!=0){cant6++;promedio6+=num6;cantR++;}
						if(num7!=0){cant7++;promedio7+=num7;cantR++;}
						if(num8!=0){cant8++;promedio8+=num8;cantR++;}
						if(num9!=0){cant9++;promedio9+=num9;cantR++;}
						if(num10!=0){cant10++;promedio10+=num10;cantR++;}
						if(num11!=0){cant11++;promedio11+=num11;cantR++;}
						if(num12!=0){cant12++;promedio12+=num12;cantR++;}
						if(num13!=0){cant13++;promedio13+=num13;cantR++;}
						
						float sum = ((float)(num1+num2+num3+num4+num5+num6+num7+num8+num9+num10+num11+num12+num13));
						
						if(sum==0){
							for(int j=0; j<13; j++){%>
								<td align="center" style="background-color:<%=color23%>;">0</td>
						<%	}%>
							<td align="center" class="th4" style="background-color:<%=colorMal%>;"><b>0</b></td>
					<%	}
						else{
							cantTotal++;
							promedioR = sum/cantR;
							sumaTotal+=promedioR;
						%>
							<td align="center"><%=num1 %></td>
							<td align="center"><%=num2 %></td>
							<td align="center"><%=num3 %></td>
							<td align="center"><%=num4 %></td>
							<td align="center"><%=num5 %></td>
							<td align="center"><%=num6 %></td>
							<td align="center"><%=num7 %></td>
							<td align="center"><%=num8 %></td>
							<td align="center"><%=num9 %></td>
							<td align="center"><%=num10 %></td>
							<td align="center"><%=num11 %></td>
							<td align="center"><%=num12 %></td>
							<td align="center"><%=num13 %></td>
							<td align="center" class="th4"><b><%=formatoDecimal.format(promedioR) %></b></td>
					<%	}
					}
					else{
						for(int j=0; j<13; j++){%>
							<td align="center">-</td>
				<%		}%>
						<td align="center" class="th4"><b>-</b></td>
				<%	} %>
				</tr>
		<%	}
			float promedioFinal = sumaTotal/cantTotal;
		%>
			<tr>
				<td class="th2" align="center"><spring:message code="aca.PromedioDeRecomendacion"/></td>
				<td class="th4" align=center><b><%=formatoDecimal.format((float)promedio1/cant1) %></b></td>
				<td class="th4" align=center><b><%=formatoDecimal.format((float)promedio2/cant2) %></b></td>
				<td class="th4" align=center><b><%=formatoDecimal.format((float)promedio3/cant3) %></b></td>
				<td class="th4" align=center><b><%=formatoDecimal.format((float)promedio4/cant4) %></b></td>
				<td class="th4" align=center><b><%=formatoDecimal.format((float)promedio5/cant5) %></b></td>
				<td class="th4" align=center><b><%=formatoDecimal.format((float)promedio6/cant6) %></b></td>
				<td class="th4" align=center><b><%=formatoDecimal.format((float)promedio7/cant7) %></b></td>
				<td class="th4" align=center><b><%=formatoDecimal.format((float)promedio8/cant8) %></b></td>
				<td class="th4" align=center><b><%=formatoDecimal.format((float)promedio9/cant9) %></b></td>
				<td class="th4" align=center><b><%=formatoDecimal.format((float)promedio10/cant10) %></b></td>
				<td class="th4" align=center><b><%=formatoDecimal.format((float)promedio11/cant11) %></b></td>
				<td class="th4" align=center><b><%=formatoDecimal.format((float)promedio12/cant12) %></b></td>
				<td class="th4" align=center><b><%=formatoDecimal.format((float)promedio13/cant13) %></b></td>
				<td class="th2" align=center><font size="3"><b><%=formatoDecimal.format(promedioFinal) %></b></font></td>
			</tr>
		</table>
		<br>
		<table class="table table-condensed" align="center" width="90%">
			<tr>
				<th><spring:message code="aca.Recomendantes"/></th>
				<th class="ayuda mensaje <%="Cuanto tiempo que conoce al aspirante" %>">1</th>
				<th class="ayuda mensaje <%="Cuan bien lo considera conocerlo" %>">2</th>
				<th class="ayuda mensaje <%="Relaci�n con el aspirante" %>">3</th>
				<th class="ayuda mensaje <%="Conoce alguna raz�n de suspensi�n o expulsi�n del aspirante" %>">4</th>
				<th class="ayuda mensaje <%="Cuanto recomienda al aspirante" %>">5</th>
				<th class="ayuda mensaje <%="Comentario" %>">6</th>
			</tr>
		<%	for(int i=1; i<=5; i++){
				AdmEncuesta.setFolio(folio);
				AdmEncuesta.setRecomendacionId(i+"");
				AdmRecomienda.mapeaRegId(conEnoc, folio, i+"");
		%>
				<tr class="tr2">
					<td>&nbsp;<%=i+")&nbsp; <b>"+AdmRecomienda.getNombre()+"</b>" %></td>
				<%	if(AdmEncuesta.existeReg(conEnoc)){
						AdmEncuesta.mapeaRegId(conEnoc, folio, i+"");
						String [] arrTiempo = AdmEncuesta.getTiempo().split(",");
						String conoce = AdmEncuesta.getConocer();
						if(conoce.equals("1")) conoce = "Poco";
						else if(conoce.equals("2")) conoce = "Bien";
						else conoce = "Muy bien";
						
						String relacion = AdmEncuesta.getRelacion();
						if(relacion.equals("1")) relacion = "Jefe";
						else if(relacion.equals("2")) relacion = "Preceptor";
						else relacion = AdmEncuesta.getOtra();
						
						String recomendado = AdmEncuesta.getOpinion();
						if(recomendado.equals("1")) recomendado = "Altamente recomendado";
						else if(recomendado.equals("2")) recomendado = "Recomendado con reservas";
						else if(recomendado.equals("3")) recomendado = "Recomendado";
						else recomendado = "No recomendado";
				%>
						<td><%=arrTiempo[2].equals("")?"0":arrTiempo[2] %> a�os, <%=arrTiempo[1].equals("")?"0":arrTiempo[1] %> meses, <%=arrTiempo[0].equals("")?"0":arrTiempo[0] %> d�as </td>
						<td><%=conoce %></td>
						<td><%=relacion %></td>
						<td><%=AdmEncuesta.getCensura().equals("S") ? "Si: "+AdmEncuesta.getConducta() : "No" %></td>
						<td><%=recomendado %></td>
						<td width="40%"><%=(AdmEncuesta.getAdicional()==null || AdmEncuesta.getAdicional().equals("")) ? "-" : AdmEncuesta.getAdicional() %></td>
				<%	}
					else{
						for(int j=0; j<6; j++){%>
							<td align="center">-</td>
				<%		}%>
				<%	} %>
				</tr>
		<%	} %>
		</table>
		<br>
	<%	String color = "";
		float porcentaje = promedioFinal*100/4;
		if(porcentaje<60) color = colorMal;
		else if(porcentaje<80) color = color23;
		else color = colorBien;
	%>
		<script>
			document.getElementById("divPorciento").innerHTML = "<font size='3'><b>"+<%=formatoDecimal.format(porcentaje)%>+" %</b></font>";
			jQuery("#tdPorciento").css({
				"background-color": "<%=color %>"
			});
		</script>
		<table class="table table-condensed" align="center">
			<tr>
			  <th width="22%"><spring:message code="aca.Recomendantes"/></th>
			  <th width="22%"><spring:message code="aca.Correo"/></th>
			  <th width="22%"><spring:message code="aca.Telefono"/></th>
			<tr>
	  <%	for(int i=1; i<=5; i++){
		  		AdmRecomienda.mapeaRegId(conEnoc, folio,i+"");%>
		  		<tr class="tr2">
		    		<td><%= AdmRecomienda.getNombre()%></td>
		    		<td><%= AdmRecomienda.getEmail()%></td>
		    		<td><%= AdmRecomienda.getTelefono()%></td>
		  		</tr>  
	  <% 	}%>
		</table>
		</div>
	</body>
</html>

<%@ include file= "../../cierra_enoc.jsp" %>