<%@page import="aca.util.Fecha"%>
<%@ include file= "../../con_enoc.jsp" %> 
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="CatHorario" scope="page" class="aca.catalogo.CatHorario"/>
<jsp:useBean id="CatHorarioU" scope="page" class="aca.catalogo.CatHorarioUtil"/>
<jsp:useBean id="FacultadU" scope="page" class="aca.catalogo.FacultadUtil"/>
<jsp:useBean  id="AccesoU" class="aca.acceso.AccesoUtil" scope="page" />
<html>
<body>
<%
	String accion		= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	int nAccion			= Integer.parseInt(accion);
	/*if(nAccion==2){
		CatHorario.setEventoId(request.getParameter("EventoId")==null?CatHorario.maximoReg(conEnoc):request.getParameter("EventoId"));	
	}*/
	String horarioId		= request.getParameter("horarioId")==null?"0":request.getParameter("horarioId");
	int accionFmt		= 0;	
	String maximo		= "";
	String facultadId	= request.getParameter("facultadId")==null?"1":request.getParameter("facultadId"); 
	
	String empleado 									= (String) session.getAttribute("codigoAlumno");
	boolean administrador = aca.acceso.AccesoUtil.esAdministrador(conEnoc, empleado);
	ArrayList <aca.catalogo.CatFacultad> lista;
	if(administrador == true){
		 lista	= FacultadU.getListAll(conEnoc, "");
	}else{
		lista	= FacultadU.filtroporAccesoFac(conEnoc, empleado, "");
	}
	
	if(nAccion==1){	
		CatHorario.setHorarioId(horarioId);
		maximo = CatHorario.maximoReg(conEnoc, horarioId);
		CatHorario.setHorarioId(maximo);
		CatHorario.maximoReg(conEnoc, horarioId);
	}
	
	
	switch(nAccion){
		case 2 : //Grabar
			CatHorario.setHorarioId(horarioId);
			CatHorario.setDescripcion(request.getParameter("Descripcion"));
			CatHorario.setFacultadId(request.getParameter("facultadId"));
			CatHorario.setEstado(request.getParameter("Estado"));
			if(CatHorario.existeReg(conEnoc)==false){
				if(CatHorario.insertReg(conEnoc)){
					accionFmt = 1;
				}else{
					accionFmt = 2;
				}
			}else{//Modifica
				if(CatHorario.updateReg(conEnoc)){
					accionFmt = 3;
				}else{
					accionFmt = 4;
				}
				
			}
			break;
		case 3 ://Elimina
			CatHorario.setHorarioId(horarioId);
			if(CatHorario.existeReg(conEnoc)){
				if(CatHorario.deleteReg(conEnoc)){
					accionFmt = 5;
				}else{
					accionFmt = 6;
				}
			}else{
				accionFmt = 7;
			}
			break;
		
		case 4 : //Consulta
			CatHorario.setHorarioId(horarioId);
			if(CatHorario.existeReg(conEnoc)){
				CatHorario.mapeaRegId(conEnoc, horarioId);
			}else{
				accionFmt = 7;
			}
		break;
	}
	CatHorario.mapeaRegId(conEnoc, horarioId);
%>
	<div class="container-fluid">
		<div align="center">
			<h1>Schedule</h1>
			
		</div>
<%		if(accionFmt==1){%>
		<div class="alert alert-info"><spring:message code='aca.SeGuardoCorrectamente'/></div>
<%	
}		if(accionFmt==2){%>
		<div class="alert alert-danger"><spring:message code='aca.NoSePudoGuardar'/></div>
<%	
}		if(accionFmt==3){%>
		<div class="alert alert-info"><spring:message code='aca.SeModifico'/></div>
<%	
}		if(accionFmt==4){%>
		<div class="alert alert-danger"><spring:message code='aca.NoSePudoModificar'/></div>
<%	
}		if(accionFmt==5){%>
		<div class="alert alert-info"><spring:message code='aca.SeElimino'/></div>
<%	
}		if(accionFmt==6){%>
		<div class="alert alert-danger"><spring:message code='aca.NoSePudoEliminar'/></div>
<%	
}		if(accionFmt==7){%>
		<div class="alert alert-danger">Does not exist </div>
<%	
}
%>  
		<div class="alert alert-info">
			<a href="listado" class="btn btn-primary"><spring:message code='aca.Regresar'/></a>
		</div>
		<form name="frmHorario" action="agregar.jsp">
			<input name="Accion" type="hidden" value="<%=accion%>"/>
			<table style="margin: 0 auto;" class="table table-nohover">
				<tr>
					<td>Schedule Id*</td>
					<td>
						<input type="text" class="input-xlarge" name="horarioId" id="horarioId" value="<%=CatHorario.getHorarioId()%>" width="50%"/>
					</td>
				</tr>
				<tr>
					<td><spring:message code='aca.Descripcion'/></td>
					<td>
						<textarea type="text" class="input-xlarge" style="width:275px;" id="Descripcion" name="Descripcion"><%=CatHorario.getDescripcion()%></textarea>
					</td>
				</tr>
				<tr>
					<td>School*</td>
					<td>
						<select onchange="document.forma.submit();" id="facultadId" name="facultadId">
<%					for(aca.catalogo.CatFacultad facultad : lista){%>
						<option value="<%=facultad.getFacultadId() %>" <%=facultad.getFacultadId().equals(facultadId)?"Selected":"" %>><%=facultad.getNombreFacultad()%></option>
<%					}
%>
					</select>
					</td>
				</tr>
				<tr>
					<td><spring:message code="aca.Estado"/></td>
					<td>
						<select  id="Estado" name="Estado">										
							<option value="A" <%if(CatHorario.getEstado().equals("A"))out.print("selected"); %>><spring:message code='aca.Activo'/></option>
							<option value="I" <%if(CatHorario.getEstado().equals("I"))out.print("selected"); %>><spring:message code='aca.Inactivo'/></option>
							
						</select>
					</td>
				</tr>
			</table>
			<table style="margin: 0 auto;" class="table table-nohover">
				<tr>
					<td>
						<a class="btn btn-primary" onclick="javascript:Guardar();"><i class="icon-ok icon-white"></i> Save</a>
						<a class="btn btn-danger" onclick="javascript:Eliminar();"><i class="fas fa-trash-alt icon-white"></i> Delete</a>
					</td>
				</tr>
			</table>
		</form>
		
	</div>
</body>
<script>
	function Guardar() {
		if (document.frmHorario.horarioId.value != ""
		 && document.frmHorario.facultadId.value != "") {
			document.frmHorario.Accion.value = "2";
			document.frmHorario.submit();
		} else {
			alert("Fill in all fields!");
		}
	}
	function Eliminar(){
		if (document.frmHorario.horarioId.value != ""
				&& document.frmHorario.FacultadId.value != "") {
			if (confirm("Are you sure you want to delete this record?") == true) {
				document.frmHorario.Accion.value = "3";
				document.frmHorario.submit();
			}
		}else{
			alert("Fill in all fields!");
		}
	}
	function Nuevo() {
		if(document.frmHorario.Nombre.value!=""){
			if(confirm("The name field is not empty, it is possible that the event has not been saved yet. Do you want to create a new event?")==true){
				document.frmHorario.Accion.value = "1";
				document.frmHorario.submit();		
			}
		}else{
			document.frmHorario.Accion.value = "1";
			document.frmHorario.submit();
		}
	}
</script>
</html>
<%@ include file= "../../cierra_enoc.jsp" %>