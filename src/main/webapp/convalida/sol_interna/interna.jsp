<%@page import="com.itextpdf.text.Document"%>
<%@ page import= "aca.plan.MapaPlan"%>
<%@ page import= "aca.plan.MapaCurso"%>
<%@ page import= "aca.catalogo.CatTipoCal"%>
<%@ page import= "aca.conva.*"%>
<%@ page import= "aca.util.*"%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.conva.spring.ConvMateria"%>
<%@page import="aca.conva.spring.ConvEvento"%>
<%@page import="aca.kardex.spring.KrdxCursoAct"%>
<%@page import="aca.candado.spring.CandAlumno"%>
<%@page import="aca.alumno.spring.AlumAcademico"%>
<%@page import="aca.acceso.spring.Acceso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script>
	function borrar(mat, id){
		if (confirm("¿Esta seguro que desea borrar el registro?")){
			document.frmimportcalif.ConvalidacionId.value=id
			document.frmimportcalif.Accion.value=1
			document.frmimportcalif.submit();
		}
	}
		
	function nuevo(){
		document.frmimportcalif.Accion.value=2
		document.frmimportcalif.submit();
	}
		
	function grabaCom(convalidacionId){
		document.frmimportcalif.ConvalidacionId.value=convalidacionId
		document.frmimportcalif.Accion.value=3
		document.frmimportcalif.numeroCom.value=convalidacionId
		document.frmimportcalif.submit();
	}
		
	function cerrar(convalidacionId){
		document.frmimportcalif.ConvalidacionId.value=convalidacionId
		document.frmimportcalif.Accion.value=4
		document.frmimportcalif.submit();
	}
	
	function borraMateria(convalidacionId,cursoId){
		if (confirm("¿Esta seguro que desea borrar la materia?")){
			document.frmimportcalif.ConvalidacionId.value=convalidacionId;
			document.frmimportcalif.cursoId.value=cursoId;
			document.frmimportcalif.Accion.value=5;
			document.frmimportcalif.submit();
		}
	}
	
	function guarda(convId,estado){
		document.frmimportcalif.Accion.value = 6;
		document.frmimportcalif.ConvalidacionId.value = convId;
		document.frmimportcalif.estado.value = estado;
		document.frmimportcalif.submit();
	}
	
	function borrarCandado(mat,id){
		if (confirm("¿Esta seguro que desea remover candado?")){
			
			document.frmimportcalif.Accion.value=7;
			document.frmimportcalif.submit();
		}
	}

	function nuevoPorCiclo(){
		document.frmimportcalif.Accion.value=8
		document.frmimportcalif.submit();
	}
</script>
<%
//variables
// 	session.setAttribute("MateriaConNota", "0");
// 	session.setAttribute("MateriaSinNota", "0");

	String codigoAlumno		= (String)request.getAttribute("codigoAlumno");
	String codigoPersonal	= (String)request.getAttribute("codigoPersonal");
	
	String seleccion		= (String)request.getAttribute("seleccion");
	String cursoId			= (String)request.getAttribute("cursoId");
	String carreraId		= (String)request.getAttribute("carreraId");
	String planId			= (String)request.getAttribute("planId");
	String nombreCarrera 	= (String)request.getAttribute("nombreCarrera");
	String nombreAlumno		= (String)request.getAttribute("nombreAlumno");
	String escribir			= (String)request.getAttribute("escribir");
	String convalidacionId	= (String)request.getAttribute("convalidacionId");
	String comentarioNum	= (String)request.getAttribute("comentarioNum");
	String plan				= (String)request.getAttribute("plan");
	String nombreModalidad	= (String)request.getAttribute("nombreModalidad");
	
	if(comentarioNum == null || comentarioNum.equals("")){
		comentarioNum = "0";
	}

	String comentario		= (String)request.getAttribute("comentario");
	if(comentario == null || comentario.equals("null")){
		comentario = "";
	}

	int matAceptadas=0, matRechazadas=0;
	String proceso 			= (String)request.getAttribute("proceso");
	boolean esAdmin			= (boolean)request.getAttribute("esAdmin");
	
	HashMap<String, List<ConvMateria>> mapaLisConvMaterias	= (HashMap<String, List<ConvMateria>>)request.getAttribute("mapaLisConvMaterias");

	List<ConvMateria> lisMaterias	= new ArrayList<ConvMateria>();
	List<ConvEvento> lisConv		= (List<ConvEvento>)request.getAttribute("lisConv");
	List<KrdxCursoAct> lisActual	= (List<KrdxCursoAct>)request.getAttribute("lisActual");
	
	// Consulta la lista de candados de convalidación del alumno 
	List<CandAlumno> lisCandados		= (List<CandAlumno>)request.getAttribute("lisCandados");
	
	AlumAcademico alumAcademico = (AlumAcademico)request.getAttribute("alumAcademico");
	
	Acceso acceso = (Acceso)request.getAttribute("acceso");
	
	ConvEvento convEvento 	= (ConvEvento)request.getAttribute("convEvento");
	ConvMateria convMateria = (ConvMateria)request.getAttribute("convMateria");
	
	boolean convalidador 	= (boolean)request.getAttribute("convalidador");
	
	HashMap<String, String> mapCursos = (HashMap<String, String>)request.getAttribute("mapCursos");
	HashMap<String, String> mapaExisteConv = (HashMap<String, String>)request.getAttribute("mapaExisteConv");
%>

<%@page import="aca.catalogo.CatModalidad"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<style type="text/css">
<!--
.Estilo1 {font-size: 9px}
.Estilo3 {color: #993300}
-->
</style>
<div class="container-fluid">
	<h3>Convalidaciones internas<small class="text-muted fs-5">&nbsp;( <%=codigoAlumno%> - <%=nombreAlumno%> - <%=plan %> - <%=nombreCarrera%> )</small></h3>
	<div class="alert alert-info">	
<% 	
	String periodoId = (String)request.getAttribute("periodoId");

	if (!periodoId.equals("X") ){
		
		HashMap<String,String> mapCarreras 	= (HashMap<String,String>)request.getAttribute("mapCarreras");

		if(!mapCarreras.containsKey(carreraId)){
			out.print("¡La carrera de este alumno no está habilitada en este periodo para registrar convalidaciones!");
		}else{
%>
		<a onclick="nuevoPorCiclo();" class="btn btn-primary">Por ciclo</a>&nbsp;&nbsp;
		<a onclick="nuevo();" class="btn btn-primary">Por materia</a>
<%		
		}
	}	
%>	 
	</div>
	<div>
<% 
	if ( !codigoAlumno.substring(0,1).equals("9") ){
			if(!alumAcademico.getModalidadId().trim().equals(acceso.getModalidad()) && !acceso.getModalidad().equals("0")){
%>
		<font size="2" color="blue">El alumno es de modalidad <b><%=nombreModalidad%></b> y no puedes solicitarle una convalidaci&oacute;n</font>
<%
			}
	}else{
	   	out.print("¡ Busca la matricula de un alumno !");
	}%>
	</div>
	<!-- Mostrar candado si existe  -->  
<%  
	if(lisCandados.size()>0){
%>
	<div width="200px;">
		<div class="alert alert-info" role="alert">
			<table>
				<tr>
					<th>#</th>
					<th>Candado</th>
					<th><spring:message code='aca.Usuario'/></th>
					<th>estado</th>
				</tr>
<% 
		int row=0;
		for (CandAlumno candado : lisCandados){ 
			row++;
%>
				<tr>
					<td><%=row%></td>
					<td>
<% 			if (esAdmin||codigoPersonal.equals("9800400")){%>
						<a href="javascript:borrarCandado('<%=codigoAlumno%>','<%=convEvento.getConvalidacionId()%>')" title="Remover candado."><%= candado.getComentario() %></a>
<%	 		}else{
				out.print(candado.getComentario());
			}
%>
					</td>
					<td><%= candado.getUsAlta() %></td>
					<td><%= candado.getEstado() %></td>
				</tr>
<%
		}
%>		
			</table>
		</div>
	</div>	
  <!-- Fin de Mostrar candado-->
<%
	}
%>
	<div align="left"> 
<%	if(!escribir.equals("")){%>
	  <%=escribir%>
<%	}%>
	</div>
	<div class="alert alert-warning"> 
		<strong>Pasos para convalidar:</strong>&nbsp;&nbsp;
      	<span class="badge bg-info">1</span> Agregar una solicitud.&nbsp;&nbsp;&nbsp;&nbsp;
      	<span class="badge bg-info">2</span> Elegir las materias y grabarlas.&nbsp;&nbsp;&nbsp;&nbsp;    
      	<span class="badge bg-info">3</span> Confirmar la solicitud.&nbsp;&nbsp;&nbsp;&nbsp;
      	<span class="badge bg-info">4</span> Llenar e imprimir el Formulario.&nbsp;&nbsp;&nbsp;&nbsp;
      	<span class="badge bg-info">5</span> Anexar la documentación necesaria al formulario y enviarla.
    </div>
    		
	<form name="frmimportcalif" method="post" action="interna">
	<input name="Accion" type="hidden">
	<input name="codigoPersonal" type="hidden" >
	<input name="ConvalidacionId" type="hidden">
	<input name="numeroCom" type="hidden">
	<input name="cursoId" type="hidden">
	<input type="hidden" name="estado">
	<table class="table table-sm table-bordered"> 
	 
<% 
			for(int i=0; i < lisConv.size(); i++){
				convEvento = (ConvEvento) lisConv.get(i);
				
				String tipoConv = "-";
				if(convEvento.getTipoConv().contentEquals("M")){
					tipoConv = "Materia";
				}else{
					tipoConv = "Ciclo";
				}
				
				
				String existeConv = "0";
				if(mapaExisteConv.containsKey(convEvento.getConvalidacionId())){
					existeConv = mapaExisteConv.get(convEvento.getConvalidacionId());
				}
				
				if(convEvento.getEstado().equals("S")) proceso = "SOLICITUD";
				if(convEvento.getEstado().equals("P")) proceso = "PREDICTAMEN";
				if(convEvento.getEstado().equals("C")) proceso = "CONFIRMADO";
				if(convEvento.getEstado().equals("G")) proceso = "GRADUAL";
				if(convEvento.getEstado().equals("F")) proceso = "SIN DOCUMENTO";
				if(convEvento.getEstado().equals("D")) proceso = "SIN PAGO";
				if(convEvento.getEstado().equals("A")) proceso = "TRAMITANDO";
				if(convEvento.getEstado().equals("X")) proceso = "CANCELADO";
				if(convEvento.getEstado().equals("T")) proceso = "TERMINADO";
				if(convEvento.getEstado().equals("R")) proceso = "REGISTRADO";
%>  
  					<tr>
  
<%				convMateria.setConvalidacionId(convEvento.getConvalidacionId());%>
  						<td align="center" colspan="7">
  							<a  class="btn btn-primary btn-sm" href="editar?ConvalidacionId=<%=convEvento.getConvalidacionId()%>" title="Editar convalidación"><i class="far fa-file"></i></a>
<% 				if(existeConv.equals("0")){%>
							<a class="btn btn-danger btn-sm" href="javascript:borrar('<%=codigoAlumno%>','<%=convEvento.getConvalidacionId()%>');"><i class="fas fa-trash-alt" onclick="" title='Eliminar'></i></a>							
<%				}else{%>
							&nbsp;
<%				}
				if(convEvento.getEstado().equals("S")){ 
					if(tipoConv.equals("Materia")){ 
%>
							<a class="btn btn-success btn-sm" href ="solicitudInterna?ConvalidacionId=<%=convEvento.getConvalidacionId()%>">
								<i class="fa fa-file" title='Agregar notas' aria-hidden="true"></i>
							</a>
<%					}else if(tipoConv.equals("Ciclo")){ %>
							<a class="btn btn-success btn-sm" href ="solicitudInternaNotas?ConvalidacionId=<%=convEvento.getConvalidacionId()%>">
								<i class="fa fa-file" title='Agregar notas' aria-hidden="true"></i>
							</a>
<%					}%>
							<span class="badge bg-dark"><%=tipoConv%></span>&nbsp;&nbsp;[<%=convEvento.getConvalidacionId()%>] [<%=convEvento.getUsuario() %>] [<%=convEvento.getFecha() %>] [<%=convEvento.getPlanId() %>] 
<%					if(!convEvento.getPlanOrigen().equals("-")){ %>
							[<%=convEvento.getPlanOrigen()%>]<b>
<%					}%>
							<b>
							[<%
					if(convalidador){	
						if (!convEvento.getEstado().equals("R")){%>
								<select name="Estado" id="Estado" onchange="javascript:guarda('<%=convEvento.getConvalidacionId() %>',this.options[this.selectedIndex].value)">
								  	<option value="S" <%if(convEvento.getEstado().equals("S")) out.print("selected");%>>Solicitud (S)</option>
								  	<option value="P" <%if(convEvento.getEstado().equals("P")) out.print("selected");%>>Predictamen (P)</option>
								  	<option value="C" <%if(convEvento.getEstado().equals("C")) out.print("selected");%>>Confirmada (C)</option>
								  	<option value="G" <%if(convEvento.getEstado().equals("G")) out.print("selected");%>>Gradual (G)</option>
								  	<option value="F" <%if(convEvento.getEstado().equals("F")) out.print("selected");%>>Sin Documento (F)</option>
								  	<option value="D" <%if(convEvento.getEstado().equals("D")) out.print("selected");%>>Sin Pago (D)</option>
								  	<option value="A" <%if(convEvento.getEstado().equals("A")) out.print("selected");%>>Tramite (A)</option>
								  	<option value="T" <%if(convEvento.getEstado().equals("T")) out.print("selected");%>>Terminada (T)</option>
								  	<option value="X" <%if(convEvento.getEstado().equals("X")) out.print("selected");%>>Cancelada (X)</option>
								</select>
<%						}
					}else{
						out.print(proceso);
					}
%>							]</b>
							Comentario: <input type=text name="comentario<%=convEvento.getConvalidacionId()%>" value="<%if(convEvento.getComentario() == null) out.print(""); else out.print(convEvento.getComentario());%>"/>
							<input class="btn btn-primary" type=button value="Grabar" onclick="grabaCom(<%=convEvento.getConvalidacionId()%>);"/>
<!-- 							(existeConv && acceso.getModalidad().equals("0") -->
<%					if(acceso.getModalidad().equals("0")){%>
							<input class="btn btn-primary"type=button value="Confirmar" onclick="cerrar(<%=convEvento.getConvalidacionId()%>);"/>
<%					}
				}else{
					if(convalidador){	
						if(convEvento.getEstado().equals("C")){ %>
					  		<i class="fas fa-list" onclick="document.location.href='dictamen?ConvalidacionId=<%=convEvento.getConvalidacionId()%>'" class="button" alt="Modificar" title="Editar"></i>
<%						} %>
					  		<i class="fas fa-search" onclick="document.location.href='historial?ConvalidacionId=<%=convEvento.getConvalidacionId()%>'" class="button" alt="Modificar"></i>
<%					} %>
							<i class="fas fa-print" onclick="document.location.href='reporte?ConvalidacionId=<%=convEvento.getConvalidacionId()%>'" class="button" alt='Reporte para Imprimir'></i>
							<span class="badge bg-dark"><%=tipoConv%></span>&nbsp;&nbsp;[<%=convEvento.getConvalidacionId()%>][<%=convEvento.getUsuario() %>] [<%=convEvento.getFecha() %>] [<%=convEvento.getPlanId() %>] 
<%					if(!convEvento.getPlanOrigen().equals("-")){ %>
							[<%=convEvento.getPlanOrigen()%>]
<%					}%>
							<b>
						[
<%					if(convalidador){	
						if (!convEvento.getEstado().equals("R")){%>
								<select name="Estado" id="Estado" onchange="javascript:guarda('<%=convEvento.getConvalidacionId() %>',this.options[this.selectedIndex].value)">
								  	<option value="S" <%if(convEvento.getEstado().equals("S")) out.print("selected");%>>Solicitud (S)</option>								  	
								  	<option value="P" <%if(convEvento.getEstado().equals("P")) out.print("selected");%>>Predictamen (P)</option>
								  	<option value="C" <%if(convEvento.getEstado().equals("C")) out.print("selected");%>>Confirmada (C)</option>
								  	<option value="G" <%if(convEvento.getEstado().equals("G")) out.print("selected");%>>Gradual (G)</option>
								  	<option value="F" <%if(convEvento.getEstado().equals("F")) out.print("selected");%>>Sin Documento (F)</option>
								  	<option value="D" <%if(convEvento.getEstado().equals("D")) out.print("selected");%>>Sin Pago (D)</option>
								  	<option value="A" <%if(convEvento.getEstado().equals("A")) out.print("selected");%>>Tramite (A)</option>
								  	<option value="T" <%if(convEvento.getEstado().equals("T")) out.print("selected");%>>Terminada (T)</option>
								  	<option value="X" <%if(convEvento.getEstado().equals("X")) out.print("selected");%>>Cancelada (X)</option>
								</select>
<%						}
					}else{
						out.print(proceso);
					}
%>							]</b>&nbsp;
							Comentario: 
<%					if(convEvento.getComentario()== null) out.print(""); else out.print(convEvento.getComentario());%>
<%				}	%>
    						</td>
  						</tr>
 						<tr>  	
  							<td colspan="7">
  								<table class="table table-sm table-bordered">  
  								<thead class="table-info">
          							<tr> 
							            <th class="text-center">Op.</th>
							            <th width="10%">Alta</th>							            
							            <th width="10%">Clave origen</th>
							            <th width="20%">Materia origen</th>							            
							            <th width="10%">clave</th>
							            <th width="25%"><spring:message code="aca.Materia"/></th>
							            <th width="5%">Crédito</th>
							            <th width="5%"><spring:message code="aca.Nota"/></th>
							            <th width="10%"><spring:message code="aca.Fecha"/></th>
							            <th width="5%">Edo.</th>
							        </tr>
								</thead>
<%				if(mapaLisConvMaterias.containsKey(convEvento.getConvalidacionId())){
					lisMaterias = mapaLisConvMaterias.get(convEvento.getConvalidacionId());
				}
	
				for(int j=0; j < lisMaterias.size(); j++){ 
					String nombreCurso = "";
					String nombreOrigen = "";
					convMateria = (ConvMateria) lisMaterias.get(j);
					if (convMateria.getEstado().equals("S")) {matAceptadas++;}
					if (convMateria.getEstado().equals("N")) {matRechazadas++;}

					if (mapCursos.containsKey(convMateria.getCursoId())) {
						nombreCurso = mapCursos.get(convMateria.getCursoId());
					}

					if (mapCursos.containsKey(convMateria.getMateria_O())) {
						nombreOrigen = mapCursos.get(convMateria.getMateria_O());
					}
					
%>
							        <tr class="tr2"> 
<%					if(convEvento.getEstado().equals("S")){ %>
							            <td class="text-center" width="5%"><img onclick="borraMateria('<%=convEvento.getConvalidacionId()%>' , '<%=convMateria.getCursoId()%>');" class="button" title='Eliminar Materia'  src='../../imagenes/no.png'></img></td>
<%					}else{ %>
							            <td class="text-center" width="9%"><img src="../../imagenes/g1.gif" /></td>
<%					} %>
										<td align="center"><%=convMateria.getFecha()%></td>
							            <td><%=convMateria.getMateria_O()%></td>
							            <td><%=nombreOrigen%></td>
							            <td>&nbsp;<%=convMateria.getConvalidacionId() /*No es convalidacionId. Es el semestre y el cursoId*/%></td>
							            <td><%=nombreCurso%></td>
							            <td align="center"><%=convMateria.getCreditos_O() %></td>
							            <td align="center"><%=convMateria.getNota_O() %></td>							            
							            <td align="center"><%=convMateria.getfNota()%></td>
							            <td width="16%" align="center"><%=convMateria.getEstado()%></td>
							        </tr>
<%				}%>
						          	<tr> 
						            	<th colspan="9"> Materias: <%=lisMaterias.size()%> - Aceptadas: <%=matAceptadas%>- Rechazadas: <%=matRechazadas%></th>
						          	</tr>
		  							<tr><td colspan="9">&nbsp;</td></tr>
          							
            							
<% 				if(convEvento.getEstado().equals("S")){ %>
              								<i class="fas fa-trash-alt"></i> Borra la Solicitud  <i class="fas fa-pencil-alt"></i> Permite elegir las materias a convalidar 
<%				}else if(convEvento.getEstado().equals("C")||convEvento.getEstado().equals("P")){ %>
											
<%				}%>
            							</td>
          							</tr>         
        						</table>
  							</td>
  						</tr>
<%				if(i==lisConv.size()-1)continue; %>
   						<tr>
  							<td style="background: #BDBDBD;">&nbsp;</td>
  						</tr>
<% 
			}
%>	
	</table>
	</form>
			
</div>