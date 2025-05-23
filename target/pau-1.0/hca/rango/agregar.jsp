<%@page import="aca.util.Fecha"%>
<%@ include file= "../../con_enoc.jsp" %> 
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="nivelU" scope="page" class="aca.catalogo.CatNivelUtil"/>
<jsp:useBean id="modalidadU" scope="page" class="aca.catalogo.ModalidadUtil"/>
<jsp:useBean id="rango"  class="aca.hca.HcaRango" scope="page"/>
<jsp:useBean id="rangoU"  class="aca.hca.HcaRangoUtil" scope="page"/>

<html>
<body>
<%
	String accion		= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	int nAccion			= Integer.parseInt(accion);
	String nivelId		= request.getParameter("nivelId")==null?"0":request.getParameter("nivelId");
	String modalidadId	= request.getParameter("modalidadId")==null?"0":request.getParameter("modalidadId");
	String rangoId		= request.getParameter("rangoId")==null?"0":request.getParameter("rangoId");
	int accionFmt		= 0;	
	String maximo 		= "0";
	
	ArrayList<aca.catalogo.CatNivel> lisNivel 					= nivelU.getListAll(conEnoc, "ORDER BY NIVEL_ID");
	ArrayList<aca.catalogo.CatModalidad> lisModalidad 			= modalidadU.getListAll(conEnoc, "ORDER BY MODALIDAD_ID");
	
	if(nAccion==1){	
		rango.setModalidadId(modalidadId);
		rango.setNivelId(nivelId);
		maximo = rangoU.maximoReg(conEnoc, nivelId, modalidadId);
		rango.setRangoId(maximo);
		rangoId = maximo;
		rango.mapeaRegId(conEnoc, nivelId, modalidadId, rangoId);
	}
	
	
	switch(nAccion){
		case 2 : //Grabar
		rango.setModalidadId(modalidadId);
		rango.setNivelId(nivelId);
		rango.setRangoFin(request.getParameter("rangoFinal"));
		rango.setRangoId(rangoId);
		rango.setRangoIni(request.getParameter("rangoInicial"));
		rango.setValor(request.getParameter("valor"));
		
		
			if(rangoU.existeReg(conEnoc, nivelId, modalidadId, rangoId)==false){
				if(rangoU.insertReg(conEnoc, rango)){
					accionFmt = 1;					
				}else{
					accionFmt = 2;
				}
			}else{//Modifica
				if(rangoU.updateReg(conEnoc, rango)){
					accionFmt = 3;					
				}else{
					accionFmt = 4;
				}
				
			}
			break;
		case 3 ://Elimina
			rango.setModalidadId(modalidadId);
			rango.setNivelId(nivelId);
			rango.setRangoId(rangoId);
			if(rangoU.existeReg(conEnoc, nivelId, modalidadId, rangoId)){
				if(rangoU.deleteReg(conEnoc, nivelId, modalidadId, rangoId)){
					accionFmt = 5;					
					//response.sendRedirect("rango?accionFmt="+1);
					out.print("<div class='alert alert-success'><a class='btn btn-primary' href='rango?accionFmt="+1+"'>Regresar</a></div>");
				}else{
					accionFmt = 6;
				}
			}else{
				accionFmt = 7;
			}
			break;
		case 4 : //Consulta
			rango.setModalidadId(modalidadId);
			rango.setNivelId(nivelId);
			rango.setRangoId(rangoId);	
			if(rangoU.existeReg(conEnoc, nivelId, modalidadId, rangoId)){
				rango.mapeaRegId(conEnoc, nivelId, modalidadId, rangoId);
			}else{
				accionFmt = 7;
			}
		break;
	}
	rango.mapeaRegId(conEnoc, nivelId, modalidadId, rangoId);	
%>
	<div class="container-fluid">
		<div align="center">
			<h1>Agregar Rangos</h1>
			
		</div>

<%		if(accionFmt==1){%>
		<div class="alert alert-info"><h3><spring:message code='aca.SeGuardoCorrectamente'/></h3></div>
<%	
}		if(accionFmt==2){%>
		<div class="alert alert-danger"></div><h3><spring:message code='aca.NoSePudoGuardar'/></h3>
<%	
}		if(accionFmt==3){%>
		<div class="alert alert-info"><h3><spring:message code='aca.SeModifico'/></h3></div>
<%	
}		if(accionFmt==4){%>
		<div class="alert alert-danger"><h3><spring:message code='aca.NoSePudoModificar'/></h3></div>
<%	
}		if(accionFmt==5){%>
		<div class="alert alert-info"><h3><spring:message code='aca.SeElimino'/></h3></div>
<%	
}		if(accionFmt==6){%>
		<div class="alert alert-danger"><h3><spring:message code='aca.NoSePudoEliminar'/></h3></div>
<%	
}		if(accionFmt==7){%>
		<div class="alert alert-danger"><h3><spring:message code='aca.NoExiste'/></h3></div>
<%	
}
%>  
		
		<div class="alert alert-info">
			<a href="rango" class="btn btn-primary"><i class="icon-arrow-left icon-white"></i><spring:message code='aca.Regresar'/></a>
		</div>
		<form name="frmRango" action="agregar">
			<input name="Accion" type="hidden" value="<%=accion%>"/>
			<input name="rangoId" type="hidden" value="<%=rango.getRangoId()%>"/>
			<div class="row">
				<div class="span3">
					<label for="nivelId">NivelId</label>			
					<select name="nivelId" id="nivelId" style="width:150px;" onchange="document.frmRango.submit()">
						<%
							for(aca.catalogo.CatNivel nivel: lisNivel){	
						%>
								<option value="<%=nivel.getNivelId()%>" <%if(nivelId.equals(nivel.getNivelId()))out.print("selected"); %>><%=nivel.getNombreNivel() %></option>
						<%
							}
						%>
					</select>
					<br><br>
					<label for="modalidadId">ModalidadId*</label>					
					<select name="modalidadId" id="modalidadId" style="width:200px;" onchange="document.frmRango.submit()">
						<%
							for(aca.catalogo.CatModalidad modalidad: lisModalidad){	
						%>
								<option value="<%=modalidad.getModalidadId()%>" <%if(modalidadId.equals(modalidad.getModalidadId()))out.print("selected"); %>><%=modalidad.getNombreModalidad() %>|| <%=modalidad.getModalidadId() %></option>
						<%
							}
						%>
					</select>
					<br><br>
					<label for="rangoInicial">Rango Inicial*</label>
					<input type="text" id="rangoInicial" name="rangoInicial" maxlength="2" value="<%=rango.getRangoIni()%>"/>
					<br><br>
					<label for="rangoFinal">Rango Final*</label>					
					<input type="text" id="rangoFinal" name="rangoFinal" value="<%=rango.getRangoFin()%>" maxlength="2"/>
					<br><br>
					<label for="valor">Valor*</label>
					<input type="text" id="valor" name="valor" value="<%=rango.getValor()%>" maxlength="6"/>
				</div>
			</div>
			<br>
			<div class="alert alert-info">
				<a class="btn btn-primary" onclick="javascript:Guardar();"><i class="icon-ok icon-white"></i> Guardar</a>
				<a class="btn btn-danger" onclick="javascript:Eliminar();"><i class="icon-remove icon-white"></i> Eliminar</a>
			</div>
		</form>
		
	</div>
</body>

<script>
	function Guardar() {
		if (document.frmRango.rangoId.value != ""
		 && document.frmRango.nivelId.value != ""
		 && document.frmRango.modalidadId.value != ""
		 && document.frmRango.rangoInicial.value != ""
		 && document.frmRango.rangoFinal.value != ""
		 && document.frmRango.valor.value != ""){
			document.frmRango.Accion.value = "2";
			document.frmRango.submit();
		} else {
			alert("¡Completa todos los campos!");
		}
	}
	function Eliminar(){
		if (document.frmRango.nivelId.value != ""
				&& document.frmRango.rangoId.value != ""
				&& document.frmRango.modalidadId.value != "") {
			if (confirm("¿Estás seguro que deseas eliminar este registro?") == true) {
				document.frmRango.Accion.value = "3";
				document.frmRango.submit();
			}
		}else{
			alert("¡Completa todos los campos!");
		}
	}
</script>
</html>
<%@ include file= "../../cierra_enoc.jsp" %>