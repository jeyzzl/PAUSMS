<%@ page import= "java.util.List"%>
<%@ page import= "aca.catalogo.spring.CatModalidad"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="catModalidadU" scope="page" class="aca.catalogo.ModalidadUtil" />


</style>
<script type="text/javascript">
	function Mostrar(){		
		document.frmFecha.submit();
	}
</script>	
<%
	String modalidades 		= (String) session.getAttribute("modalidadesReportes");
	
	String fechaIni			= request.getParameter("FechaIni")==null?(String) session.getAttribute("fechaIni"):request.getParameter("FechaIni");
	String fechaFin			= request.getParameter("FechaFin")==null?(String) session.getAttribute("fechaFin"):request.getParameter("FechaFin");	
	String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	
	if (accion.equals("1")){		
		session.setAttribute("fechaIni", fechaIni);
		session.setAttribute("fechaFin", fechaFin);
	} 

	if(modalidades == null){
		modalidades =  "'1'";
		session.setAttribute("modalidadesReportes", modalidades);
	}

	List<CatModalidad> lisModalidades	 = (List<CatModalidad>) request.getAttribute("lisModalidades");

	if(request.getParameter("CambiarModalidad") != null){
		modalidades = "";
		for(CatModalidad mod : lisModalidades){
			if( request.getParameter("mod-"+mod.getModalidadId()) != null ){
				modalidades += "'"+mod.getModalidadId()+"', ";
			}	
		}
		if(modalidades.length()>0){
			modalidades = modalidades.substring(0, modalidades.length()-2);
		}else{
			modalidades = "'1'";
		}
		
		session.setAttribute("modalidadesReportes", modalidades);
	}
	
%>

<div class="container-fluid">
	<h1>Reportes de Extranjeros</h1>
	 <form name="frmFecha" action="elegir?Accion=1" method="post">
	 
		<div class="alert alert-info  d-flex align-items-center" >
			Fecha Inicio: <input id="FechaIni" name="FechaIni" class="form-control" style="width:120px" type="text"  data-date-format="dd/mm/yyyy" value="<%=fechaIni%>" maxlength="10" size="10" /> 
			&nbsp;&nbsp;
			Fecha Final: <input id="FechaFin" name="FechaFin" class="form-control" style="width:120px"  data-date-format="dd/mm/yyyy" value="<%=fechaFin%>" maxlength="10" size="10" /> 
			<input type="submit" class="btn btn-primary" class="form-control" value="Graba Fecha">
	
	</div>
	</form>
	<hr/>	
	<div class="row"> 
 		<div class="col-5">
		  	<ol>
		  		<li><a href="extranjerospais">Extranjeros por nacionalidad</a></li>
		  		<li><a href="extranjerosorigen">Extranjeros por lugar de nacimiento</a></li>		  		
		  		<li><a href="cambio">Cambio de Carrera</a></li>
		  		<li><a href="noAutorizadas">Carrera no autorizada</a></li>		  		
		  		<li><a href="inscritos">Total inscritos</a></li>
		  		<li><a href="meses">Vence pasaporte</a></li>		  		
		  		<li><a href="venceFM3">Vence FM3</a></li>
		  	</ol>
		</div>	  		  	
	  	<div class="col-5 alert  d-flex align-items-center">	  	
	  		<form action="" method="post" name="frmModalidad">	
	  			<input type="hidden" name="CambiarModalidad" value="1" />
	  		
		  		<h3><spring:message code="aca.Modalidades"/></h3>
		  		
		  		<p>
			  		<a href="javascript:checaTodos();">Todos</a> | <a href="javascript:checaNinguno();"><spring:message code='aca.Ninguno'/></a>
			  	</p>
		  		
		  		<p>
				  	<table >
						<%for(CatModalidad mod : lisModalidades){%>
							<tr>
								<td>
									<input <%if(modalidades.contains("'"+mod.getModalidadId()+"'")){out.print("checked");} %> type="checkbox" ALIGN="center" class="modalidades" id="mod-<%=mod.getModalidadId() %>" name="mod-<%=mod.getModalidadId() %>" value="<%=mod.getModalidadId() %>" />
									&nbsp;
									<%=mod.getNombreModalidad() %>
								</td>
							</tr>
						<%}%>
					</table>
				</p>
				<a href="javascript:document.frmModalidad.submit();" class="btn btn-primary"><i class="icon-ok icon-white"></i> Guardar</a>
			</form>			
		</div>	
	</div>
</div>
<script>
	var mod = jQuery('.modalidades');

	function checaTodos(){
		mod.prop('checked', true);
	}
	
	function checaNinguno(){
		mod.prop('checked', false);
	}
</script>
<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script>
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();	
</script>