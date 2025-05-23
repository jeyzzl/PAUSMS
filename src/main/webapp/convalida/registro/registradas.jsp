<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@ page import= "aca.util.*"%>

<jsp:useBean id="convEventoU" scope="page" class="aca.conva.ConvEventoUtil"/>
<jsp:useBean id="convEvento" scope="page" class="aca.conva.ConvEvento"/>
<jsp:useBean id="convMateriaU" scope="page" class="aca.conva.ConvMateriaUtil"/>
<jsp:useBean id="convMateria" scope="page" class="aca.conva.ConvMateria"/>

<jsp:useBean id="convHistorial" scope="page" class="aca.conva.ConvHistorial"/>
<jsp:useBean id="convHistorialU" scope="page" class="aca.conva.ConvHistorialUtil"/>
<script type="text/javascript">
	
</script><head><link href="css/pa.css" rel="STYLESHEET" type="text/css">
</head>
<STYLE TYPE="text/css">
.tabbox
	{
		background: #eeeeee;
		border-left: 0pt gray solid;
		border-right: 0pt gray solid;
		border-bottom: 1pt gray solid;
	}
</STYLE>

<div class="container-fluid">
<h1>Convalidaciones</h1>
<form name="forma" action="registradas" method='post' id="noayuda">
	<input type="hidden" name="Accion" value="" />
	<div class="container-fluid">
	<h3>Convalidaciones Tramitadas con Registro Incompleto</h3>
	<table class="table table-sm table-bordered"> 
		<thead class="table-info">
		<tr>						  
		  <th width="2%"><spring:message code="aca.Numero"/></th>
		  <th width="3%"><spring:message code="aca.Matricula"/></th>
		  <th width="30%"><spring:message code="aca.Nombre"/></th>
		  <th width="6%"><spring:message code="aca.Plan"/></th>
		  <th width="8%"><spring:message code="aca.Fecha"/></th>
		  <th width="5%"><spring:message code="aca.Tipo"/></th>
		  <th width="5%">Tipo Conv</th>
		</tr>
		</thead>
<%
	String usuario = (String)session.getAttribute("codigoPersonal");

	ArrayList lisMateria = convMateriaU.getListResgistradas(conEnoc,"");
	for(int i = 0; i < lisMateria.size(); i++){
		if(i%50 == 0)
		convMateria	= (aca.conva.ConvMateria) lisMateria.get(i);
		convMateria.setEstado("R");
		
		if(convHistorialU.existeEstado(conEnoc, convMateria.getConvalidacionId(), "R")){
			convHistorial.mapeaRegId(conEnoc,convMateria.getConvalidacionId(), "R");
			convHistorial.setFecha(Fecha.getHoy());
			convHistorial.setUsuario(usuario);
			convHistorialU.updateReg(conEnoc, convHistorial);
		}else{
			convHistorial.setConvalidacionId(convMateria.getConvalidacionId());
			convHistorial.setEstado("R");
			convHistorial.setFolio(convHistorialU.getMaxReg(conEnoc, convMateria.getConvalidacionId()));
			convHistorial.setFecha(Fecha.getHoy());
			convHistorial.setUsuario(usuario);
			convHistorialU.insertReg(conEnoc, convHistorial);
		}
		convMateriaU.updateReg(conEnoc, convMateria);
		
	}
	lisMateria = null;
	
	ArrayList lisEvento = convEventoU.getListEventosCumplidos(conEnoc, "");
	for(int i = 0; i < lisEvento.size(); i++){
		if(i%50 == 0)
		convEvento = (aca.conva.ConvEvento) lisEvento.get(i);
		convEvento.setEstado("R");
		
		if(convHistorialU.existeEstado(conEnoc, convEvento.getConvalidacionId(), "R")){
			convHistorial.mapeaRegId(conEnoc,convEvento.getConvalidacionId(), "R");
			convHistorial.setFecha(Fecha.getHoy());
			convHistorial.setUsuario(usuario);
			convHistorialU.updateReg(conEnoc, convHistorial);
		}else{
			convHistorial.setConvalidacionId(convEvento.getConvalidacionId());
			convHistorial.setEstado("R");
			convHistorial.setFolio(convHistorialU.getMaxReg(conEnoc, convMateria.getConvalidacionId()));
			convHistorial.setFecha(Fecha.getHoy());
			convHistorial.setUsuario(usuario);
			convHistorialU.insertReg(conEnoc, convHistorial);
		}
		convEventoU.updateReg(conEnoc, convEvento);
	}
	lisEvento = null;


	lisEvento = new ArrayList();
	lisEvento = convEventoU.getListEventosRegInc(conEnoc, "");
	for(int i = 0; i < lisEvento.size(); i++){
		convEvento = (aca.conva.ConvEvento) lisEvento.get(i);
%>
		<tr class="tr2">						  
		  <td><%=i+1 %></th>
		  <td><%=convEvento.getCodigoPersonal() %></th>
		  <td><%=aca.alumno.AlumUtil.getNombreAlumno(conEnoc, convEvento.getCodigoPersonal(), "NOMBRE") %></th>
		  <td><%=convEvento.getPlanId() %></th>
		  <td><%=convEvento.getFecha() %></th>
		  <td><%if(convEvento.getTipo().equals("I"))
					out.print("Interna");
				else if(convEvento.getTipo().equals("E"))	
					out.print("Externa");%></td>
			<td><%if(convEvento.getTipoConv().equals("M"))
					out.print("Materia");
				else if(convEvento.getTipoConv().equals("G"))
			  		out.print("Grado");%></td>
		</tr>
<%
	}
%>
	 </table>
	 <br>
	 <h3>Convalidaciones Registradas</h3>
	 <table     width="90%" class="table table-fullcondensed table-striped">
	 <tr>						  
		  <th
 width="2%"><spring:message code="aca.Numero"/></th>
		  <th width="3%"><spring:message code="aca.Matricula"/></th>
		  <th width="30%"><spring:message code="aca.Nombre"/></th>
		  <th width="6%"><spring:message code="aca.Plan"/></th>
		  <th width="8%"><spring:message code="aca.Fecha"/></th>
		  <th width="5%"><spring:message code="aca.Tipo"/></th>
		  <th width="5%">Tipo Conv</th>
		</tr>
<%
	lisEvento = null;
	lisEvento = convEventoU.getListAll(conEnoc, "WHERE ESTADO = 'R' ORDER BY PLAN_ID ");
	for(int i = 0; i < lisEvento.size(); i++){
		convEvento = (aca.conva.ConvEvento) lisEvento.get(i);
%>
	<tr class="tr2">						  
	  <td><%=i+1 %></th>
	  <td><%=convEvento.getCodigoPersonal() %></th>
	  <td><%=aca.alumno.AlumUtil.getNombreAlumno(conEnoc, convEvento.getCodigoPersonal(), "NOMBRE") %></th>
	  <td><%=convEvento.getPlanId() %></th>
	  <td><%=convEvento.getFecha() %></th>
	  <td><%if(convEvento.getTipo().equals("I"))
				out.print("Interna");
			else if(convEvento.getTipo().equals("E"))	
				out.print("Externa");%></td>
	  <td><%if(convEvento.getTipoConv().equals("M"))
				out.print("Materia");
			else if(convEvento.getTipoConv().equals("G"))
			  	out.print("Grado");%></td>
	</tr>
<%
	}
%>
  	</table>
</form>
</div>
<%@ include file= "../../cierra_enoc.jsp" %>