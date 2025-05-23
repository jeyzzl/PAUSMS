<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatPeriodo"%>
<%@ page import= "aca.carga.spring.Carga"%>
<%@ page import= "aca.alumno.spring.AlumDescuento"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>


<jsp:useBean id="CatPeriodoUtil" scope="page" class="aca.catalogo.CatPeriodoUtil"/>
<jsp:useBean id="AlumDescuentoU" scope="page" class="aca.alumno.AlumDescuentoUtil"/>
<script>
	function Borrar(codigoAlumno, cargaId, descuentoId){
		if (confirm("Estás seguro de eliminar el registro")){
			document.location.href="borrar?CodigoAlumno="+codigoAlumno+"&CargaId="+cargaId+"&DescuentoId="+descuentoId;
		}		
	}	
</script>
<%
	String periodoId 		= (String)request.getAttribute("periodoId");
	String cargaId	 		= (String)request.getAttribute("cargaId");
	String mensaje			= request.getParameter("Mensaje")==null?"":request.getParameter("Mensaje");
	
	List<CatPeriodo> lisPeriodos 			= (List<CatPeriodo>)request.getAttribute("lisPeriodos");	
	List<Carga> lisCargas 					= (List<Carga>)request.getAttribute("lisCargas");	
	List<AlumDescuento> lisDescuentos		= (List<AlumDescuento>)request.getAttribute("lisDescuentos");
%>
<body>
<div class="container-fluid">
	<h2>Descuentos de alumnos</h2>
	<form name="frmDescuentos" action="alumnos" methgod="post">
	<div class="alert alert-info d-flex align-items-center">
		<b><spring:message code="aca.Periodo"/>:</b>
	 	<select name="PeriodoId" class="form-select" onchange="javascript:document.frmDescuentos.submit();" style="width:150px">
		<%	for(int j=0; j<lisPeriodos.size(); j++){
				CatPeriodo periodo = lisPeriodos.get(j);%>
				<option <%if(periodoId.equals(periodo.getPeriodoId()))out.print(" Selected ");%> value="<%=periodo.getPeriodoId()%>"><%=periodo.getNombre().replace("Periodo ", "")%></option>
		<%	}%>
	   	</select>
	   	&nbsp;
		<b><spring:message code="aca.Carga"/>:</b>
		<select name="CargaId" class="form-select" onchange="javascript:document.frmDescuentos.submit();" style="width:350px;" >
		<%	for(Carga carga : lisCargas){%>
			<option <%=cargaId.equals(carga.getCargaId())?" Selected ":""%> value="<%=carga.getCargaId()%>">[<%=carga.getCargaId() %>] <%=carga.getNombreCarga()%></option>
		<%	}%>
		</select>
		&nbsp;&nbsp;
		<a href="accion?CargaId=<%=cargaId %>" class="btn btn-primary"><i class="fas fa-plus"></i><spring:message code='aca.Añadir'/></a>	
	</div>		
	</form>
<%	if (!mensaje.equals("-")){%>
		<%=mensaje%>
<%	} %>
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
		<tr>
			<th>Op</th>
			<th><spring:message code="aca.Matricula"/></th>
			<th>Id</th>
			<th><spring:message code="aca.Fecha"/></th>
			<th>$Mat.</th>
			<th>Tipo Mat.</th>
			<th>$Enza.</th>
			<th>Tipo Enza.</th>
			<th>$Int.</th>
			<th>Tipo Int.</th>
			<th><spring:message code="aca.Total"/></th>
			<th>Observaciones</th>
			<th><spring:message code='aca.Usuario'/></th>
			<th>Aplicado</th>
		</tr>
	</thead>
		<% for(AlumDescuento descuento : lisDescuentos){%>
			<tr>
				<td>
					<a href="accion?Accion=2&CodigoAlumno=<%=descuento.getCodigoPersonal()%>&DescuentoId=<%=descuento.getDescuentoId()%>&CargaId=<%=cargaId%>"><i class="fas fa-edit"></i></a>
					<a href="javascript:Borrar('<%=descuento.getCodigoPersonal()%>','<%=cargaId%>','<%=descuento.getDescuentoId()%>');"><i class="fas fa-trash-alt"></i></a>
					<a href="contratoPDF?CodigoAlumno=<%=descuento.getCodigoPersonal() %>" ><i class="fas fa-print"></i></a>
				</td>
				<td><%=descuento.getCodigoPersonal()%></td>
				<td align="right"><%=descuento.getDescuentoId()%></td>
				<td><%=descuento.getFecha()%></td>
				<td class="right"><%=descuento.getMatricula()%></td>
				<td><%=descuento.getTipoMatricula()%></td>
				<td class="right"><%=descuento.getEnsenanza()%></td>
				<td><%=descuento.getTipoEnsenanza()%></td>
				<td class="right"><%=descuento.getInternado()%></td>
				<td><%=descuento.getTipoInternado()%></td>
				<td class="right"><%=descuento.getTotal()%></td>
				<td><%=descuento.getObservaciones()%></td>
				<td><%=descuento.getUsuario()%></td>
				<td><%=descuento.getAplicado().equals("S")?"SI":"NO"%></td>
			</tr>
		<%}%>
	</table>
	</div>
</body>