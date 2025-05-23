<%@ include file= "id.jsp" %>
<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="alumno" 	scope="page" class="aca.disciplina.CondAlumno" />
<jsp:useBean id="CondAlumnoU" 	scope="page" class="aca.disciplina.CondAlumnoUtil" />
<jsp:useBean id="reporteU" 	scope="page" class="aca.disciplina.CondReporteUtil" />
<jsp:useBean id="lugarU" 	scope="page" class="aca.disciplina.CondLugarUtil" />
<jsp:useBean id="juezU"	 	scope="page" class="aca.disciplina.CondJuezUtil" />

<script type="text/javascript">
	
	function Nuevo(){
		
		document.unidad.folio.sCodigo	= ""; 
		document.unidad.folio.value 	= "";
		document.unidad.comentario.value= "";
		document.unidad.Accion.value	= "1";
		document.unidad.submit();
		
	}
	
	function Grabar(){
		if(document.unidad.sCodigo.value!="" && document.unidad.folio.value!=""){			
			document.unidad.Accion.value="2";
			document.unidad.submit();
			
		}else{
			alert("Fill out the entire form1");
		}
		
	}
	
	function Modificar(){
		document.unidad.Accion.value="3";
		document.unidad.submit();
	}
	
	function Borrar( ){
		if(document.unidad.sCodigo.value!="" && document.unidad.folio.value!=""){
			if(confirm("Are you sure you want to delete this record?")==true){
	  			document.unidad.Accion.value="4";
				document.unidad.submit();
			}			
		}else{
			alert("Type in the key");
			document.unidad.sCodigo.focus(); 
	  	}
	}
	
	function Consultar(){
		document.unidad.Accion.value="5";
		document.unidad.submit();		
	}
	
	function setValue(strLink){
		strLink=strLink;
		window.open( strLink, "verplan", "scrollbars=yes, width=550, height=350, top=250, left=300")
	}
</script>

<%	String sCodigo				= request.getParameter("codigoAlumno");
	String sPeriodo				= request.getParameter("Periodo");
	String sFecha				= request.getParameter("fecha");	
	String sCantidad			= "1";		
		
	String sCodigoEmp			= request.getParameter("empleado");
	String sResultado			= "";
	String sReporte				= "X";
	String sLugar				= "X";
	String sJuez				= "X";
	int nAccion					= Integer.parseInt(request.getParameter("Accion"));
	
	if(nAccion == 1){
		alumno.setFolio(CondAlumnoU.maximoReg(conEnoc, sCodigo, sPeriodo)); 
		alumno.setFecha(aca.util.Fecha.getHoy());
		if( alumno.getFolio() == null){ alumno.setFolio("1"); }
	}else if(nAccion != 4){
		alumno.setMatricula(sCodigo);
		alumno.setPeriodoId(sPeriodo);
		alumno.setFolio(request.getParameter("folio"));
		alumno.setIdReporte("100");
		alumno.setIdLugar("5");
		alumno.setIdJuez("24");
		alumno.setFecha(request.getParameter("fecha"));
		alumno.setEmpleado(request.getParameter("empleado"));
		alumno.setCantidad(sCantidad);
		alumno.setComentario(request.getParameter("comentario"));
	}
	switch (nAccion){
		case 1: { // Nuevo			
			//sResultado = "Llene el formulario correctamente ..¡¡";
		
			break;
		}		
		case 2: { // Grabar
			if (CondAlumnoU.existeReg(conEnoc, sCodigo, sPeriodo, request.getParameter("folio")) == false && alumno.getCantidad()!= null){
				
				if (CondAlumnoU.insertReg(conEnoc, alumno)){
					
					sResultado = "Saved "+alumno.getMatricula();
				}else{
					sResultado = "Error saving: "+alumno.getMatricula();
				}
			}else{
				if (CondAlumnoU.updateReg(conEnoc, alumno)){
					sResultado = "Updated: "+alumno.getMatricula();
				}else{
					sResultado = "Error updating: "+alumno.getMatricula();
				}
			}
			break;
		}
	
		case 4: { // Borrar
			alumno.setMatricula(sCodigo);
			alumno.setPeriodoId(sPeriodo);
			alumno.setFolio(request.getParameter("folio"));
			if (CondAlumnoU.existeReg(conEnoc, sCodigo, sPeriodo, request.getParameter("folio")) == true){
				if (CondAlumnoU.deleteReg(conEnoc, sCodigo, sPeriodo, request.getParameter("folio"))){
					sResultado = "Deleted "+alumno.getMatricula();
					alumno.setFecha(aca.util.Fecha.getHoy());
				}else{
					sResultado = "Error deleting "+alumno.getMatricula();
				}	
			}else{
				sResultado = "Not found "+alumno.getMatricula();
			}
			break;
		}
		case 5: { // Consultar
			if (CondAlumnoU.existeReg(conEnoc, sCodigo, sPeriodo, request.getParameter("folio")) == true){
				alumno.mapeaRegId(conEnoc, alumno.getMatricula(), request.getParameter("folio"), sPeriodo);
				sResultado = "Query"+alumno.getMatricula();
			}else{
				sResultado = "Not found "+alumno.getMatricula();
			}	
			break;			
		}
	}
%>
	<form name="unidad" method="post" action="grabar?Periodo=<%=sPeriodo%>&codigoPersonal=<%=sCodigo%>">
	<input name="Accion" type="hidden" />
  	<table style="width:50%; margin;0 auto;" class="table table-condensed">
  	<tr/>
      <th colspan="2" align="center">Registro de Elogio</th>
      <th><a class="btn btn-primary" href="unidad?codigoAlumno=<%=sCodigo%>"><spring:message code="aca.Listado"/>:</a></th>
    </tr>
    <tr> 
      <td colspan="2" align="center">&nbsp;</td>
    </tr>
    <tr> 
      <td width="26%"><strong><spring:message code="aca.Matricula"/>:</strong></td>
      <td width="74%"><input name="sCodigo" type="text" class="text" id="sCodigo" value="<%=sCodigo%>" size="8" maxlength="7" readonly></td>
    </tr>
    <tr> 
      <td><strong><spring:message code='aca.Folio'/>:</strong></td>
      <td><input name="folio" type="text" class="text" id="folio" value="<%=alumno.getFolio()%>" size="3" maxlength="3" readonly></td>
    </tr>
    <tr> 
      <td><strong><spring:message code="aca.Comentario"/>:</strong></td>
      <td><input name="comentario" type="text" class="text" id="comentario" value="<%=alumno.getComentario()%>" size="45" maxlength="60" ></td>
    </tr>
    <tr> 
      <td><strong><spring:message code="aca.Fecha"/>:</strong></td>
      <td>
      <input name="fecha" type="text" class="text" id="fecha" value="<%=alumno.getFecha()%>" size="11" maxlength="15" > 
      <font size="2"> * dd/mm/yyyy </font></td>
    </tr>
    <tr> 
      <td colspan="2" align="center">&nbsp;</td>
    </tr> 
    <tr> 
      <td style="text-align:center" colspan="2" align="center"><%=sResultado%></td>
    </tr> 
    <tr>
      <th colspan="2" style="text-align:center"> <a class="btn btn-primary" href="javascript:Nuevo()"><spring:message code='aca.Nuevo'/></a>
      	&nbsp;<a class="btn btn-primary" href="javascript:Grabar()"><spring:message code="aca.Grabar"/></a> 
        &nbsp; <a class="btn btn-primary" href="javascript:Borrar()"><spring:message code='aca.Borrar'/></a> 
     </th>
   	</tr>
	</table>   	
	</form>
<%@ include file= "../../cierra_enoc.jsp" %> 