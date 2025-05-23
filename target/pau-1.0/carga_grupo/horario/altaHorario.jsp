<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="aca.catalogo.spring.CatHorarioFacultad"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<html>
<head>
	<script type="text/javascript">
		function nuevo() {
			if (confirm("<spring:message code="catalogos.horarioFac.JSCrearPeriodo"/>")) {
				document.fmrHorario.Accion.value = "2";
				document.fmrHorario.submit();
			}
		}
	
		function grabar() {
			document.fmrHorario.Accion.value = "2";
			document.fmrHorario.submit();				
		}
	
		function Borrar(horarioId, periodo, facultadId) {
			if (confirm("<spring:message code="catalogos.horarioFac.JSEliminarPeriodo"/>")) {
				document.location = "altaHorario?Accion=3&horarioId="+horarioId+"&Periodo="+periodo+"&facultadId="+facultadId;
			}
		}
	</script>
</head>
<%	
	String horarioId 		= request.getParameter("horarioId");
	String facultadId		= request.getParameter("facultadId")==null?"0":request.getParameter("facultadId");
	String accion 			= request.getParameter("Accion") == null ? "1" : request.getParameter("Accion");
	int numAccion 			= Integer.parseInt(accion);
	String facultadNombre	= (String)request.getAttribute("facultadNombre");	
	String mensaje			= (String)request.getAttribute("mensaje");
	
	CatHorarioFacultad HorarioFac			= (CatHorarioFacultad)request.getAttribute("HorarioFac");	
	List<CatHorarioFacultad> lisHorarios 	= (List<CatHorarioFacultad>)request.getAttribute("lisHorarios");	
%>
<body>
<div class="container-fluid">
	<h2>Edit Timetable <small class="text-muted fs-5"> (<%=facultadNombre%>)</small></h2>
	<form action="altaHorario?horarioId=<%=horarioId%>&facultadId=<%=facultadId %>" method="post"name="fmrHorario" target="_self">
	<input type="hidden"name="Accion">
	<div class="alert alert-info">		
		<a class="btn btn-primary" onclick="document.location.href='listado'"><i class="fas fa-arrow-left"></i></a>&nbsp;
		<a class="btn btn-primary" href="javascript:nuevo()"><spring:message code="aca.Anadir"/></a>&nbsp;&nbsp;
		<a class="btn btn-primary" href="javascript:grabar()"><spring:message code="aca.Guardar"/></a> &nbsp;
		<%=!mensaje.equals("-")?"&nbsp;" + mensaje: "" %>
	</div>	
	<table class="table table-sm table-bordered" style="width:77%">
		<tr>
			<td width="20%">
				<b><spring:message code="catalogos.horarioFac.Turno"/> and <spring:message code="catalogos.horarioFac.Periodo"/></b>
			</td>
			<td class="d-flex align-items-center" >
				<select id="Turno" name="Turno" class="form-select" style="width:120px;">
			<%
				String turno = HorarioFac.getTurno() == null ? "" : HorarioFac.getTurno();
			%>
					<option value="1" <%=turno.equals("1") ? "Selected" : ""%>><spring:message code="aca.Matutino"/></option>
					<option value="2" <%=turno.equals("2") ? "Selected" : ""%>><spring:message code="aca.Vespertino"/></option>
					<option value="3" <%=turno.equals("3") ? "Selected" : ""%>><spring:message code="aca.Nocturno"/></option>
				</select>
				&nbsp;&nbsp;
				<select id="Periodo" name="Periodo" class="form-select" style="width:80px;">
		<%
		String periodo = HorarioFac.getPeriodo() == null ? " " : HorarioFac.getPeriodo();
								
		if(turno.equals("2")) periodo = (Integer.parseInt(periodo)-10)+"";
		else if(turno.equals("3")) periodo = (Integer.parseInt(periodo)-20)+"";
		%>
					<option value="1" <%=periodo.equals("1") ? "Selected" : ""%>>1</option>
					<option value="2" <%=periodo.equals("2") ? "Selected" : ""%>>2</option>
					<option value="3" <%=periodo.equals("3") ? "Selected" : ""%>>3</option>
					<option value="4" <%=periodo.equals("4") ? "Selected" : ""%>>4</option>
					<option value="5" <%=periodo.equals("5") ? "Selected" : ""%>>5</option>
					<option value="6" <%=periodo.equals("6") ? "Selected" : ""%>>6</option>
					<option value="7" <%=periodo.equals("7") ? "Selected" : ""%>>7</option>
					<option value="8" <%=periodo.equals("8") ? "Selected" : ""%>>8</option>
					<option value="9" <%=periodo.equals("9") ? "Selected" : ""%>>9</option>
					<option value="10" <%=periodo.equals("10") ? "Selected" : ""%>>10</option>
				</select>
			</td>
		</tr>
		<tr>
			<td><b><spring:message code="aca.Tipo"/>:</b></td>
			<td>
				<select id="Tipo" name="Tipo" class="form-select" style="width:120px;">			
					<option value="1" <%=HorarioFac.getTipo().equals("1")?"Selected":""%>>Editable</option>
					<option value="2" <%=HorarioFac.getTipo().equals("2")?"Selected":""%>>Informative</option>					
				</select>		
			</td>
		</tr>
		<tr>
			<td><b><spring:message code="catalogos.horarioFac.Inicia"/>:</b></td>
			<td>
				&nbsp;&nbsp;<b><spring:message code="aca.Hora"/></b>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<b><spring:message code="aca.Minutos"/></b>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td class="d-flex align-items-center">
				<select name="HoraInicio" id="HoraInicio" class="form-select" style="width:70px;"> 
			<%		String horaIni = HorarioFac.getHoraInicio().equals("") ? "00" : HorarioFac.getHoraInicio();
					for(int j=0; j<24; j++){
						if(j<10){%>
							<option <%if(horaIni.equals("0"+j))out.print(" Selected ");%> value="<%="0"+j%>"><%="0"+j%></option>
					<%	}else{%>
							<option <%if(horaIni.equals(j+""))out.print(" Selected ");%> value="<%=j%>"><%=j%></option>
					<%	}
					}
					%>
				</select> 
				&nbsp;:&nbsp; 
				<select name="MinInicio" id="MinInicio" class="form-select" style="width:70px;">
			<%		String minIni = HorarioFac.getMinutoInicio().equals("") ? "00" : HorarioFac.getMinutoInicio();
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
					}%>
				</select>
				<i><font color="#AE2113">&nbsp;<spring:message code="catalogos.horarioFac.HoraMilitar"/></font></i>
			</td>
		</tr>
		<tr>
			<td><b><spring:message code="catalogos.horarioFac.Finaliza"/>:</b></td>
			<td>
				&nbsp;&nbsp;<b><spring:message code="aca.Hora"/></b>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<b><spring:message code="aca.Minutos"/></b>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td class="d-flex align-items-center">
				<select name="HoraFinal" id="HoraFinal" class="form-select" style="width:70px;">
			<%		String horaFin = HorarioFac.getHoraFinal().equals("") ? "00" : HorarioFac.getHoraFinal();
					for(int j=0; j<24; j++){
						if(j<10){%>
							<option <%if(horaFin.equals("0"+j))out.print(" Selected ");%> value="<%="0"+j%>"><%="0"+j%></option>
					<%	}else{%>
							<option <%if(horaFin.equals(j+""))out.print(" Selected ");%> value="<%=j%>"><%=j%></option>
					<%	}
					}
					%>
				</select>
				&nbsp;:&nbsp; 
				<select name="MinFinal" id="MinFinal" class="form-select" style="width:70px;">
			<%		String minFin = HorarioFac.getMinutoFinal().equals("") ? "00" : HorarioFac.getMinutoFinal();
					for(int j=0; j<12; j++){
						if(j<2){
							if(j==0){%>
								<option <%if(minFin.equals("0"+j))out.print(" Selected ");%> value="<%="0"+j%>"><%="0"+j%></option>
						<%	}else{%>
								<option <%if(minFin.equals("0"+(j*5)))out.print(" Selected ");%> value="<%="0"+(j*5)%>"><%="0"+(j*5)%></option>
						<%	}
						}else{%>
							<option <%if(minFin.equals((j*5)+""))out.print(" Selected ");%> value="<%=j*5%>"><%=j*5%></option>
					<%	}
					}%>
				</select>
				<i><font color="#AE2113">&nbsp;<spring:message code="catalogos.horarioFac.HoraMilitar"/></font></i>
			</td>
		</tr>
		<tr>
			<td><b><spring:message code="aca.Nombre"/>:</b></td>
			<td>
				<input type="text" class="input-xlarge" name="Nombre" id="Nombre" value="<%=HorarioFac.getNombre()%>" width="50%"/>				
			</td>
		</tr>						
	</table>	
	<table class="table table-bordered table-sm" style="width:77%">
	<thead class="table-info">
		<tr>
			<th>Op.</th>
			<th>#</th>
			<th><spring:message code="aca.Nombre"/></th>
			<th><spring:message code="catalogos.horarioFac.Turno"/></th>
			<th><spring:message code="catalogos.horarioFac.HoraInicio"/></th>
			<th><spring:message code="catalogos.horarioFac.HoraFinal"/></th>
		</tr>
	</thead>
<%	
	int row = 0;
	for ( CatHorarioFacultad hora : lisHorarios ){
		row++;
		String turnoNombre = "-";
		if (hora.getTurno().equals("1")) turnoNombre = "Morning";
		if (hora.getTurno().equals("2")) turnoNombre = "Evening";
		if (hora.getTurno().equals("3")) turnoNombre = "Night";
%>
		<tr>			
			<td style="text-align: center">
				<a href="altaHorario?Accion=4&horarioId=<%=hora.getHorarioId()%>&Periodo=<%=hora.getPeriodo()%>&facultadId=<%=facultadId%>" class="fas fa-edit" title="Modificar"></a>
				&nbsp;
				<a href="javascript:Borrar('<%=hora.getHorarioId()%>','<%=hora.getPeriodo()%>','<%=facultadId%>')" class="fas fa-trash-alt" title="Eliminar"></a>				
			</td>
			<td><%=row%></td>
			<td><%=hora.getNombre()%></td>
			<td><%=turnoNombre%></td>
			<td><%=hora.getHoraInicio()%>:<%=hora.getMinutoInicio() %></td>
			<td><%=hora.getHoraFinal()%>:<%=hora.getMinutoFinal() %></td>
		<tr>
<%		
	}
%>
	</table>
	</form>
</div>	
</body>
</html>