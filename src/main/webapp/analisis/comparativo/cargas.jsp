<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%@ page import= "aca.carga.CargaUtil"%>

<jsp:useBean id="CatPeriodoUtil" scope="page" class="aca.catalogo.CatPeriodoUtil"/>

<%
	String periodoActual			= aca.catalogo.CatPeriodoUtil.getPeriodo(conEnoc);

	ArrayList<aca.carga.Carga> lisCarga				= new ArrayList<aca.carga.Carga>();
	CargaUtil cargaU				= new CargaUtil();	
	String sel						= "";
	String modalidades	= (String)session.getAttribute("modalidades") == null?"'1'":session.getAttribute("modalidades").toString();
	if(modalidades.equals(""))modalidades="''";
	
	ArrayList<aca.catalogo.CatPeriodo> listaPeriodos = CatPeriodoUtil.getListAll(conEnoc, "ORDER BY PERIODO_ID DESC");	
	lisCarga						= cargaU.getListAll(conEnoc, "WHERE PERIODO IN ('"+periodoActual+"') AND ESTADO = '1' ORDER BY 4,1");	
	String lisModo[] 				= modalidades.replace("'", "").split(",");
%>
<div class="container-fluid">
	<h1>Comparativo de Cargas</h1>
	<form name="documento" method="post" action="listado">
	<div class="alert alert-info">
		Modalidades:
<%
	for(String mod:lisModo){
		String nombreModalidad = aca.catalogo.ModalidadUtil.getNombreModalidad(conEnoc, mod);
		out.print("["+nombreModalidad+"] ");	
	}		
%>		
		&nbsp; <a href="modalidades" class="btn btn-primary"><spring:message code="aca.Elegir"/></a>
	</div>
	<div class="alert alert-info">
		<font size="2"><spring:message code="analisis.comparativo.Periodo1"/>:
		<select onchange="refreshCarga1();" name="Periodo1" class="input input-big">
<%	for(int i=0; i<listaPeriodos.size(); i++){
		aca.catalogo.CatPeriodo periodo = listaPeriodos.get(i);
		if (periodo.getPeriodoId().equals(periodoActual)) sel = "selected"; else sel = " ";
%>
			<option value="<%=listaPeriodos.get(i).getPeriodoId()%>" <%=sel%>><%=listaPeriodos.get(i).getNombre()%></option>
<%
	}
%>
		</select>
      	</font>
      	<font size="2"><spring:message code="analisis.comparativo.Carga1"/>:
		<select name="carga1" id="carga1" style="width:370px">
<%
	for (int i=0; i<lisCarga.size(); i++){
		aca.carga.Carga carga = (aca.carga.Carga) lisCarga.get(i);
		out.print("<option value='"+carga.getCargaId()+"' ");
		out.print(" >"+carga.getCargaId()+" - "+carga.getNombreCarga()+"</option>");
	}
%>
		</select>
	   	</font>
	</div>
	<div class="alert alert-info">
      	<font size="2"><spring:message code="analisis.comparativo.Periodo2"/>:
 		<select onchange="refreshCarga2();" name="Periodo2" class="input input-big">
<%	for(int i=0; i<listaPeriodos.size(); i++){
		aca.catalogo.CatPeriodo periodo = listaPeriodos.get(i);
		if (periodo.getPeriodoId().equals(periodoActual)) sel = "selected"; else sel = " ";
%>
		<option value="<%=listaPeriodos.get(i).getPeriodoId()%>" <%=sel%>><%=listaPeriodos.get(i).getNombre()%></option>
<%
	}
%>
   	    </select>
 	 	</font>
 	 	<font size="2"><spring:message code="analisis.comparativo.Carga2"/>:
	        <select name="carga2" id="carga2"  style="width:370px">
<%
	for (int i=0; i<lisCarga.size(); i++){
		aca.carga.Carga carga = (aca.carga.Carga) lisCarga.get(i);
		out.print("<option value='"+carga.getCargaId()+"' ");
		out.print(" >"+carga.getCargaId()+" - "+carga.getNombreCarga()+"</option>");
	} 
%>
			</select>
		</font>
	</div>
	
	<div class="alert alert-success">
		<input class="btn btn-primary" type="submit" name="Aceptar" value="<spring:message code="aca.Aceptar"/>">	
	</div>
	</form>	
</div>
<script>
	function refreshCarga1(){
		var carga = jQuery('#carga1');		
		carga.html('<option><spring:message code="aca.Actualizando"/></option>');
		var periodo = document.documento.Periodo1.value;		
		jQuery.get('getCargas?periodo='+periodo, function(data){
			carga.html(data);
		});
	}
	
	function refreshCarga2(){
		var carga = jQuery('#carga2');		
		carga.html('<option><spring:message code="aca.Actualizando"/></option>');		
		var periodo = document.documento.Periodo2.value;		
		jQuery.get('getCargas?periodo='+periodo, function(data){
			carga.html(data);
		});
	}
</script>
<%@ include file= "../../cierra_enoc.jsp" %>