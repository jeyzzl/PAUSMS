<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatPeriodo"%>
<%@ page import= "aca.carga.spring.Carga"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%		
	String periodoId 				= (String)request.getAttribute("periodoId");
	String cargaId 					= (String)request.getAttribute("cargaId");
	
	List<CatPeriodo> lisPeriodos 	= (List<CatPeriodo>)request.getAttribute("lisPeriodos");
	List<Carga> lisCargas 			= (List<Carga>)request.getAttribute("lisCargas");	
%>
<div class="container-fluid">	
	<h2>Reportes de Extranjeros</h2>
	<form name="frmExtranjero" method="post" action="elige_carga">
	<div class="alert alert-info d-flex align-items-center">
		Periodo:&nbsp;
        <select name="PeriodoId" class="form-control" style="width:180px" onchange="document.frmExtranjero.submit();">
<%		
	for(CatPeriodo periodo : lisPeriodos){
%>		
			<option value="<%=periodo.getPeriodoId()%>" <%=periodoId.equals(periodo.getPeriodoId())?" selected":""%>>
				<%=periodo.getNombre()%>
			</option>
<%	}%>
		</select>
		&nbsp;&nbsp;Carga:&nbsp;
        <select name="CargaId" style="width:315px;" class="form-control"  onchange="document.frmExtranjero.submit();">
<%		
	for(Carga carga : lisCargas){
%>		
			<option value="<%=carga.getCargaId()%>" <%=cargaId.equals(carga.getCargaId())?" selected":""%>>
				[<%=carga.getCargaId()%>]-<%=carga.getNombreCarga()%>
			</option>
<%			
	}		
%>
        </select>
        <input class="btn btn-primary" type="submit" name="Aceptar" value="Aceptar">
    </div>
	</form>	
	<div class="row-fluid">       	
    	<ul class="nav nav-list">
			<li><a href="extranjeros?CargaId=<%=cargaId%>"><i class="fas fa-arrow-circle-right"></i>Por Facultad y Carrera</a></li> &nbsp;&nbsp;&nbsp;
			 
			<li><a href="extranjerospais?CargaId=<%=cargaId%>"><i class="fas fa-arrow-circle-right" ></i>Por Nacionalidad</a></li>         
        </ul>       	 
	</div>
</div>
<!-- fin de estructura -->