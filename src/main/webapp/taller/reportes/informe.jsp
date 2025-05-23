<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean scope="page" id="ContEjercicioU"  class="aca.financiero.ContEjercicioUtil" />
<jsp:useBean scope="page" id="BecInformeU"  class="aca.bec.BecInformeUtil" />

<script>

	function elegirTodos(){
		var elegir = jQuery("#elegir");
		if (elegir.html()=="Todos"){
			jQuery(".informes").attr("checked",true);
			elegir.html("Ninguno");
		}else{				
			jQuery(".informes").attr("checked",false);
			elegir.html("Todos");
		}	
	}
	
	function grabarInformes(){
		document.frmInformes.accion.value = "1";
		document.frmInformes.submit();
	}
		
</script>
<%	
	//Lista de ejercicios
	ArrayList<aca.financiero.ContEjercicio> ejercicios 	= ContEjercicioU.getListAll(conEnoc, "ORDER BY 1 DESC");	

	String ejercicioId 		= (String) session.getAttribute("ejercicioId");	
	
	if(request.getParameter("ejercicioId")!=null){
		ejercicioId = request.getParameter("ejercicioId");	
		session.setAttribute("ejercicioId", ejercicioId);
	}
	
	String estadoInforme	= ejercicioId.equals(aca.util.Fecha.getEjercicioHoy())?"2":"3";
	
	String accion = request.getParameter("accion")==null?"":request.getParameter("accion");
	String informes = "";
	ArrayList<aca.bec.BecInforme> lisInformes = BecInformeU.getInfomesConAlumnos(conEnoc, ejercicioId, estadoInforme, " ORDER BY NIVEL, ORDEN");
	if(accion.equals("1")){
		for(int i = 1; i<= lisInformes.size(); i++){
			if(request.getParameter(i+"")!=null){
				informes += "'"+request.getParameter(i+"")+"',";
			}
		}
		informes = informes.substring(0,informes.length()-1);		
		response.sendRedirect("informesContabilizados?informes="+informes);
	}
	
%>

<div class="container-fluid">

	<h2>Informe para contabilidad</h2>	
	<form name="forma" id="forma" method="post">
		<div class="alert alert-info">
			<a class="btn btn-primary" href="menu"><i class="fas fa-arrow-left"></i></a>				
			<select name="ejercicioId" id="ejercicioId" style="width:150px;" onchange="document.forma.submit()">
				<%for(aca.financiero.ContEjercicio ejercicio: ejercicios){%>
					<option value="<%=ejercicio.getIdEjercicio() %>" <%if(ejercicioId.equals(ejercicio.getIdEjercicio()))out.print("selected"); %>><%=ejercicio.getIdEjercicio() %></option>
				<%}%>
			</select>
		</div>
	</form>
	
	<form name="frmInformes" id="frmInformes" method="post">
		<input type="hidden" name="accion" value="<%=accion%>">
		<table id="table" class="table table-sm table-bordered">
		<thead class="table-info">
			<tr>
				<th width="10%" align="center"><a id="elegir" class="btn btn-small btn-primary" onclick="javascript:elegirTodos();" style="cursor:pointer; font-size:12px;">Todos</a></th>	
				<th><spring:message code="aca.Nombre"/></th>
			</tr>
		</thead>
			<%
				int cont = 0;
				String nivelTmp = "";
				for(aca.bec.BecInforme inf : lisInformes){
					cont++;
			%>
					<%
						if(!nivelTmp.equals(inf.getNivel())){
							nivelTmp = inf.getNivel();
					%>
							<tr>
								<td colspan="100" class="alert alert-info"><%=inf.getNivel().equals("P")?"Preparatoria":"Universidad" %></td>
							</tr>
					<%
						} 
					%>
					<tr>
						<td><input type="checkbox" value="<%=inf.getInformeId()%>" class="informes" id="<%=cont%>" name="<%=cont%>" /></td>
						<td><%=inf.getInformeNombre() %></td>
					</tr>
			<%
				} 
			%>
		</table>
		
		<div class="alert alert-info">
			<a class="btn btn-primary btn-large" onclick="grabarInformes();"><spring:message code="aca.Mostrar"/></a>
		</div>
	</form>
</div>

 <style>
 	body{
 		background : white;
 	}
 </style>
<%@ include file= "../../cierra_enoc.jsp" %>