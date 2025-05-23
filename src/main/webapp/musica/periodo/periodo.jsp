<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%@ page import= "aca.musica.MusiPeriodo"%>
<%@ page import= "aca.musica.MusiPeriodoUtil"%>

<head>
	<script type="text/javascript">
		function Borrar( PeriodoId ){
			if(confirm("Estas seguro de eliminar el registro: "+PeriodoId)==true){
		  		document.location="accion?Accion=4&PeriodoId="+PeriodoId;
		  	}
		}	
	</script>
</head>
<%	
	MusiPeriodoUtil periodoU	= new MusiPeriodoUtil();
	ArrayList  lisPeriodo		= periodoU.getListAll(conEnoc, " ORDER BY ENOC.MUSI_PERIODO.F_INICIO DESC, PERIODO_ID");
	String bgColor				= "";
%>
<div class="container-fluid">
<h1>Listado de Periodos de Inscripcion</h1>
<div class="alert alert-info">
 	<a class="btn btn-primary" href="accion?Accion=1"><spring:message code='aca.Añadir'/></a>	
</div>
<body>
<table style="width:90%"  class="table table-condensed table-striped">
<tr>
  <th width="3%" align="center"><spring:message code="aca.Operacion"/></th>
  <th width="3%" align="center"><spring:message code="aca.Numero"/></th>
  <th width="5%" align="center">Periodo</th>
  <th width="29%" align="center"><spring:message code="aca.Nombre"/></th>    
  <th width="13%" align="center">Inicio</th>
  <th width="12%" align="center">Fin</th>  
  <th width="7%" align="center">ciclo</th>
  <th width="10%" align="center"><spring:message code="aca.Estado"/></th>
  <th width="10%" align="center">#Pagare</th>
  <th width="16%" align="center"><spring:message code="aca.Costo"/> Pagare</th>
</tr>
<%
	for (int i=0; i< lisPeriodo.size(); i++){
		MusiPeriodo periodo = (MusiPeriodo) lisPeriodo.get(i);
		
%>				
  <tr>
  	<td align="center">
	  <a class="fas fa-edit" href="accion?Accion=5&PeriodoId=<%= periodo.getPeriodoId() %>">
	  </a>
	  <a class="fas fa-trash-alt" href="javascript:Borrar('<%=periodo.getPeriodoId()%>')">
	  </a>
	</td>
    <td align="center">
	  <%=i+1 %>
	</td>
    <td align="center"><%= periodo.getPeriodoId() %></td>
    <td><%= periodo.getPeriodoNombre() %></td>
	<td align="center"><%= periodo.getFInicio() %></td>
	<td align="center"><%= periodo.getFFinal() %></td>
	<td align="center"><%= periodo.getCicloEscolar() %></td>
	<td align="center">
	<%	if (periodo.getEstado().equals("1"))
			out.print("Activo");
		else 
			out.print("Cerrado");
	%>
	</td>
	<td align="center"><%= periodo.getNumPagare() %></td>	
	<td align="center"><%= periodo.getCostoPagare() %></td>	
  </tr>  
<%  
	}
%>  
	
<tr>
  <td style="text-align:center;" colspan="9">
   <i class="fas fa-pencil-alt"></i>¡ Modifica ! &nbsp;&nbsp;
   <i class="fas fa-trash-alt"></i>¡ Borra !   
  </td>
</tr>
</table>
</body> 
<%
	lisPeriodo 	= null;	
%>
<%@ include file= "../../cierra_enoc.jsp" %>