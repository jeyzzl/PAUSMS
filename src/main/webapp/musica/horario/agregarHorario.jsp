<%@page import="aca.musica.spring.MusiHorario"%>
<%@page import="java.util.ArrayList"%>
 
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<body>
<%
	String periodoId		= (String) request.getAttribute("PeriodoId");
	String cargaId			= (String) request.getAttribute("CargaId");
	String codigoPersonal	= (String) request.getAttribute("codigoPersonal");
	String nombreMaestro	= (String) request.getAttribute("nombreMaestro");
	String pag				= (String) request.getAttribute("pag");
	
	MusiHorario horario 	= (MusiHorario)request.getAttribute("horario");
%>
<div class="container-fluid">
	<h2>Horario <small>(<%=codigoPersonal%>)</small></h2>
	<div class="alert alert-info">
		<a href="<%=pag.equals("1")?"horario":"horarioMaestro"%>?PeriodoId=<%=periodoId%>&CargaId=<%=cargaId%>&MaestroId=<%=codigoPersonal%>" class="btn btn-primary"><i class="fas fa-arrow-left"></i> <spring:message code='aca.Regresar'/></a>
	</div>
	<form name="frmHorario" action="guardar" method="post">
		<input type="hidden" name="CargaId" value="<%=cargaId%>" />
		<input type="hidden" name="Folio" value="<%=horario.getFolio()%>" />
		<input type="hidden" name="MaestroId" value="<%=horario.getMaestroId()%>" />
	
		<label for="DepartamentoId">Cupo:</label>
		<input type="number" name="Cupo" value="<%=horario.getCupo()%>">
		<br><br>
		<label for="DepartamentoId">Dia:</label>
		<select name="Dia">
			<option value="1" <%=horario.getDia().equals("1")?"selected":""%>>Domingo</option>
			<option value="2" <%=horario.getDia().equals("2")?"selected":""%>>Lunes</option>
			<option value="3" <%=horario.getDia().equals("3")?"selected":""%>>Martes</option>
			<option value="4" <%=horario.getDia().equals("4")?"selected":""%>>Miércoles</option>
			<option value="5" <%=horario.getDia().equals("5")?"selected":""%>>Jueves</option>
			<option value="6" <%=horario.getDia().equals("6")?"selected":""%>>Viernes</option>
			<option value="7" <%=horario.getDia().equals("7")?"selected":""%>>Sábado</option>
		</select>
		<br><br>
		<label for="Year">Inicia:</label>
		<label for="Year">Hora&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Minuto</label>
		<select class="input-mini" name="HoraInicio" id="HoraInicio">
<%		
		String horaIni = horario.getHoraInicio().equals("") ? "00" : horario.getHoraInicio();
		for(int j=0; j<24; j++){
			if(j<10){%>
				<option <%if(horaIni.equals("0"+j))out.print(" Selected ");%> value="<%="0"+j%>"><%="0"+j%></option>
		<%	}else{%>
				<option <%if(horaIni.equals(j+""))out.print(" Selected ");%> value="<%=j%>"><%=j%></option>
		<%	}
		}
%>
		</select> 
			: 
		<select class="input-mini" name="MinInicio" id="MinInicio">
<%				
		String minIni = horario.getMinInicio().equals("") ? "00" : horario.getMinInicio();
		for(int j=0; j<12; j++){
			if(j<2){
				if(j==0){%>
					<option <%if(minIni.equals("0"+j))out.print(" Selected ");%> value="<%="0"+j%>"><%="0"+j%></option>
			<%	}else{%>
					<option <%if(minIni.equals("0"+(j*5)))out.print(" Selected ");%> value="<%="0"+(j*5)%>"><%="0"+(j*5)%></option>
			<%	}
			}else{%>
				<option <%if(minIni.equals((j*5)+""))out.print(" Selected ");%> value="<%=j*5%>"><%=j*5%></option>
		<%	}
		}
%>
		</select>
		<i><font color="#AE2113"><spring:message code="catalogos.horarioFac.HoraMilitar"/></font></i>
		<br><br>
		<label for="Year">Termina:</label>
		<label for="Year">Hora&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Minuto</label>
		<select class="input-mini" name="HoraFinal" id="HoraFinal">
<%			
			String horaFin = horario.getHoraFinal().equals("") ? "00" : horario.getHoraFinal();
			for(int j=0; j<24; j++){
				if(j<10){%>
					<option <%if(horaFin.equals("0"+j))out.print(" Selected ");%> value="<%="0"+j%>"><%="0"+j%></option>
			<%	}else{%>
					<option <%if(horaFin.equals(j+""))out.print(" Selected ");%> value="<%=j%>"><%=j%></option>
			<%	}
			}
%>
		</select>
			: 
		<select class="input-mini" name="MinFinal" id="MinFinal">
<%			
		String minFin = horario.getMinFinal().equals("") ? "00" : horario.getMinFinal();
			for(int j=0; j<12; j++){
				if(j<2){
					if(j==0){%>
					<option <%if(minFin.equals("0"+j))out.print(" Selected ");%> value="<%="0"+j%>"><%="0"+j%></option>
		<%			}else{%>
					<option <%if(minFin.equals("0"+(j*5)))out.print(" Selected ");%> value="<%="0"+(j*5)%>"><%="0"+(j*5)%></option>
		<%			}
				}else{%>
					<option <%if(minFin.equals((j*5)+""))out.print(" Selected ");%> value="<%=j*5%>"><%=j*5%></option>
		<%		}
			}%>
		</select>
		<i><font color="#AE2113"><spring:message code="catalogos.horarioFac.HoraMilitar"/></font></i>
		<br><br>
		<label for="valor">Valor</label>
		<select name="Valor">
			<option value="0.5" <%=horario.getValor().equals("0.5")?"selected":""%>>Media hora</option>
			<option value="1" <%=horario.getValor().equals("1")?"selected":""%>>1 hora</option>
			<option value="1.5" <%=horario.getValor().equals("1.5")?"selected":""%>>Hora y media</option>
			<option value="2" <%=horario.getValor().equals("2")?"selected":""%>>2 horas</option>
		</select>
		<div class="alert alert-info">
			<input class="btn btn-primary" type="submit" value="Guardar">
		</div>
	</form>
</div>
</html>