<%@ include file="../../con_enoc.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@ include file="menuPortal.jsp"%>

<%
	String periodoSesion	= (String) session.getAttribute("porPeriodo");
	String codigoPersonal	= (String) session.getAttribute("codigoPersonal");	
	String cambio			= request.getParameter("cambioPeriodo")==null?"0":request.getParameter("cambioPeriodo");
	
	if (cambio.equals("1")){
		String periodoId	= request.getParameter("PeriodoId")==null?"2014":request.getParameter("PeriodoId");
		session.setAttribute("porPeriodo", periodoId);
		periodoSesion 		= periodoId;
	} 
%>

<div class="container-fluid">
	<h2>
		PERIODO DEL PORTAFOLIO <small>(<%=periodoSesion%>)
		</small>
	</h2>
	<hr />

	<div style="margin-left: 20px; float: left; background: white;"
		class="alert alert-info ">

		<table style="width:100%" class="table"
			style="width: 700px; border-bottom: 1px solid gray">
			<tr>
				<th colspan=4>Elige el Periodo: <select name="Periodo"
					id="Periodo" onchange="cambiaPeriodo(this.value);">
						<option value='2014'
							<%if (periodoSesion.equals("2014")) out.print("selected");%>>2014</option>
						<option value='2015'
							<%if (periodoSesion.equals("2015")) out.print("selected");%>>2015</option>
				</select>
				</th>
			</tr>
		</table>

	</div>
</div>
<script type="text/javascript">

	function cambiaPeriodo(periodoId){
		document.location.href="periodo?PeriodoId="+periodoId+"&cambioPeriodo=1";
	}
	
</script>
<script>
	jQuery('.periodo').addClass('active');
</script>
<%@ include file="../../cierra_enoc.jsp"%>