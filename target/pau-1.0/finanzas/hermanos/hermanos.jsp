<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file="../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.alumno.spring.AlumFamilia"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<html>
	<head>
		<style>
			.table-small th{
				font-size: 10pt;
			}
		</style>
		<script>
			function autorizarFamilia(familiaId){
				new Ajax.Request("hermanosAccion?Accion=1&FamiliaId="+familiaId, {
					method: "get",
					onSuccess: function(req){
						if(req.responseText.indexOf("Error")!=-1) mensaje("<font size='2' color='#AE2113'>Ocurrió un error al autorizar la familia</font>");
						else form.submit();
					},
					onFailure: function(req){
						alert("Ocurri&oacute; un error al autorizar");
					}
				});
			}
			function noAutorizarFamilia(familiaId){
				if(confirm("¿Estás seguro de eliminar esta familia?")){
					new Ajax.Request("hermanosAccion?Accion=2&FamiliaId="+familiaId, {
						method: "get",
						onSuccess: function(req){
							if(req.responseText.indexOf("Error")!=-1) mensaje("<font size='2' color='#AE2113'>Ocurrió un error al eliminar la familia.</font>");
							else form.submit();
						},
						onFailure: function(req){
							alert("Ocurri&oacute; un error al eliminar");
						}
					});
				}
			}
			function cancelarFamilia(familiaId){
				new Ajax.Request("hermanosAccion?Accion=3&FamiliaId="+familiaId, {
					method: "get",
					onSuccess: function(req){
						if(req.responseText.indexOf("Error")!=-1) mensaje("<font size='2' color='#AE2113'>Ocurrió un error al cancelar la autorización de la familia</font>");
						else form.submit();
					},
					onFailure: function(req){
						alert("Ocurri&oacute; un error al cancelar");
					}
				});
			}
			function autorizarHermano(mat){
				new Ajax.Request("hermanosAccion?Accion=5&Matricula="+mat, {
					method: "get",
					onSuccess: function(req){
						if(req.responseText.indexOf("Error")!=-1) mensaje("<font size='2' color='#AE2113'>Ocurrió un error al autorizar al alumno.</font>");
						else form.submit();
					},
					onFailure: function(req){
						alert("Ocurri&oacute; un error al autorizar");
					}
				});
			}
			function noAutorizarHermano(mat){
				if(confirm("¿Estás seguro de eliminar este alumno de la familia?")){
					new Ajax.Request("hermanosAccion?Accion=6&Matricula="+mat, {
						method: "get",
						onSuccess: function(req){
							if(req.responseText.indexOf("Error")!=-1) mensaje("<font size='2' color='#AE2113'>Ocurrió un error al eliminar el alumno.</font>");
							else form.submit();
						},
						onFailure: function(req){
							alert("Ocurri&oacute; un error al eliminar");
						}
					});
				}
			}
			function cancelarHermano(mat){
				new Ajax.Request("hermanosAccion?Accion=4&Matricula="+mat, {
					method: "get",
					onSuccess: function(req){
						if(req.responseText.indexOf("Error")!=-1) mensaje("<font size='2' color='#AE2113'>Ocurrió un error al cancelar la autorización del alumno.</font>");
						else form.submit();
					},
					onFailure: function(req){
						alert("Ocurri&oacute; un error al cancelar");
					}
				});
			}
			function mensaje(mensaje){
				jQuery("#mensaje").html(mensaje);
			}
		</script>
	</head>
<%
	String filtro 		= request.getParameter("Filtro")==null?"A":request.getParameter("Filtro");
	String msj    		= (String)request.getAttribute("mensaje");

	List<AlumFamilia> listaFamilias 					= (List<AlumFamilia>)request.getAttribute("listaFamilias");
	
	HashMap<String, List<AlumPersonal>> mapaFamilias 	= (HashMap<String, List<AlumPersonal>>)request.getAttribute("mapaFamilias");
	HashMap<String, AlumPersonal> mapaInscritos 		= (HashMap<String, AlumPersonal>)request.getAttribute("mapaInscritos");
	
	boolean inscrito = true;	
%>
<body>
<div class="container-fluid">
	<h2>Autorizacion de Familias y Hermanos</h2>
	<form method="post" name="form" action="hermanos">
	<div class="alert alert-info d-flex align-items-center">
		<select name="Filtro" onChange="submit();" class="form-select" style="width:140px;">
			<option value="A" <%=filtro.equals("A")?"Selected":""%>>Pendientes</option>
			<option value="F" <%=filtro.equals("F")?"Selected":""%>>Autorizados</option>
		</select>	
<%	if(filtro.equals("F")){ %>
		&nbsp;&nbsp;<input type="button" value="Reporte" class="btn btn-info" onClick="document.location='reporte?Filtro=<%=filtro%>'">
<%	} %>
		&nbsp;<a href="cancelarAlumno?Filtro=<%=filtro %>" class="btn btn-info">Cancelar Alumnos No Inscritos</a>
		&nbsp;<a href="cancelarFamilia?Filtro=<%=filtro %>" class="btn btn-info">Cancelar Familias con 1 o menos hermanos autorizados</a>
	</div>
	<table>
	<tr>
		<td align="center">
			<div id="mensaje"></div>
			<%=msj %>
		</td>
	</tr>
	</table>
<%	
	int row 	= 0;
	String famId = "X";
	for(AlumFamilia alumFamilia : listaFamilias){
		row++;
		
		List<AlumPersonal> lisAlumPersona = new ArrayList<AlumPersonal>();
		
		if(mapaFamilias.containsKey(alumFamilia.getFamiliaId())){
			lisAlumPersona = mapaFamilias.get(alumFamilia.getFamiliaId());
		}
%>
			<table style="width:60%" class="table table-condensed table-noHover">
				<tr>
					<th>Familia # <%= row%></th>
					<th width="20%">Fecha de creación</th>
					<th width="30%"><spring:message code="aca.Estado"/></th>
				</tr>
<%			for(AlumPersonal alumno : lisAlumPersona){
				if(!famId.equals(alumFamilia.getFamiliaId())){
				%>
				<tr>
					<td><a href="javascript:noAutorizarFamilia('<%=alumFamilia.getFamiliaId()%>');" class="fas fa-trash-alt" title="Eliminar"></a> <font size="2"><b><%=alumno.getApellidoPaterno()+" "+(alumno.getApellidoMaterno()!=null&&!alumno.getApellidoMaterno().equals("-")?alumno.getApellidoMaterno():"")%></b></font></td>
					<td><%=alumFamilia.getFecha()%></td>
					<td>
					<%	if(alumFamilia.getEstado().equals("A")){%>
							<input type="button" class="btn btn-sm btn-success" onClick="autorizarFamilia('<%=alumFamilia.getFamiliaId()%>');" value="Autorizar" id="btnAutoriza">
					<%	}
						else if(alumFamilia.getEstado().equals("F")){%>
							<input type="button" class="btn btn-success btn-sm disabled" value="Autorizar" id="btnAutoriza">
							<input type="button" class="btn btn-danger btn-sm" onClick="cancelarFamilia('<%=alumFamilia.getFamiliaId()%>');" value="Cancelar" id="btnCancelar">
					<%	}%>
					</td>
				</tr>
<%				}
				%>
				<tr>
					<td colspan="3" style="text-align:center;">
						<table style="width:95%" class="table-small">
<%						if(!famId.equals(alumFamilia.getFamiliaId())){%>
							<tr>
								<th width="10%"><spring:message code="aca.Matricula"/></th>
								<th width="55%"><spring:message code="aca.Nombre"/></th>
								<th width="5%"><spring:message code="aca.Inscrito"/></th>
								<th width="30%"><spring:message code="aca.Estado"/></th>
							</tr>
<%						}

						if(mapaInscritos.containsKey(alumno.getCodigoPersonal())){
							inscrito = true;
						}else{
							inscrito = false;
						}
%>
							<tr class="ayuda alumno <%=alumno.getCodigoPersonal() %>">
								<td width="10%" style="text-align:center;font-size:9pt;">
									<a href="javascript:noAutorizarHermano('<%=alumno.getCodigoPersonal()%>');" class="fas fa-trash-alt" title="Eliminar"></a> <%=alumno.getCodigoPersonal()%>
								</td>
								<td width="55%" style="font-size:9pt;"><%=alumno.getNombreLegal()%></td>
								<td width="5%" style="font-size:9pt;"><b><%=!inscrito?"<font color='#AE2113'>NO</font>":"<font color='green'>SI</font>"%></b></td>
								<td width="30%" style="font-size:9pt;">
								<%	if(alumno.getEstado().equals("A")){%>
										<input type="button" class="btn btn-sm btn-primary" onClick="autorizarHermano('<%=alumno.getCodigoPersonal()%>');" value="Auto.Al." id="btnAutoriza">
								<%	}else if(alumno.getEstado().equals("F")){%>
										<input type="button" class="btn btn-primary btn-sm disabled" value="Auto.B" id="btnAutoriza">
										<input type="button" class="btn btn-danger btn-sm" onClick="cancelarHermano('<%=alumno.getCodigoPersonal()%>');" value="Cancelar" id="btnCancelar">
								<%	}%>
								</td>
							</tr>
						</table>
					</td>
				</tr>
<%			
				famId = alumFamilia.getFamiliaId();
			}
		}%>
			</table>
		</form>
	</body>
</html>