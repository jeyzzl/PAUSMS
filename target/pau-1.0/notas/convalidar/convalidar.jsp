<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@ page import= "aca.kardex.ConvalidarPlan"%>
<%@ page import= "aca.kardex.ConvalidarMaterias"%>

<html>
<head>
<title><spring:message code='aca.DocumentoSinTitulo'/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<jsp:useBean id="alumnoUtil" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="carreraUtil"  scope="page" class="aca.catalogo.CarreraUtil"/>
<jsp:useBean id="convUtil"  scope="page" class="aca.kardex.ConvalidarUtil"/>
<jsp:useBean id="importadoUtil"  scope="page" class="aca.kardex.ImportadoUtil"/>
<jsp:useBean id="planUtil" scope="page" class="aca.plan.PlanUtil"/>
<jsp:useBean id="krdImport" scope="page" class="aca.kardex.KrdxCursoImp">
<jsp:setProperty name="krdImport" property="*"/></jsp:useBean> 

<script>
	function Valida(){
		document.frmConvalida.Accion.value = "1";
		document.frmConvalida.submit();
	}	
</script>
<%
//variables
	String codigoPersonal				= (String)session.getAttribute("codigoPersonal");
	String codigoAlumno					= (String)session.getAttribute("codigoAlumno");
	String accion 						= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String planConvalidar 				= request.getParameter("planConvalidar")==null?"0":request.getParameter("planConvalidar");
	
	String CarreraId					= alumnoUtil.getCarreraId(conEnoc,alumnoUtil.getPlanActivo(conEnoc,codigoAlumno) );
	String planActual					= alumnoUtil.getPlanActivo(conEnoc,codigoAlumno);	 
	String NombreCarrera 				= carreraUtil.getNombreCarrera(conEnoc, CarreraId, "1");
	String NombreAlumno					= alumnoUtil.getNombre(conEnoc,codigoAlumno, "NOMBRE");
	
	String cursoIdConvalidar			= "";
	String fecha 						= "";
	int cicloTem						= 0;
	int nota							= 0;
	String convalidacion				= "";
	ArrayList<String> noAcreditadas 	= new ArrayList<String>();	
	
	// Lista de planes de estudio del alumno
	ArrayList<aca.kardex.ConvalidarPlan> lisPlanes = convUtil.getListPlan(conEnoc,codigoAlumno);
	
	// Lista de materias del plan de estudios actual que no están acreditadas
	ArrayList<aca.kardex.ConvalidarMaterias> lisActualesNoAcreditadas = convUtil.getListMateriasActuales(conEnoc, codigoAlumno,  planActual);
	
	// Materias acreditadas en el plan anterior
	ArrayList<aca.kardex.ConvalidarMaterias> lisAnteriorAcreditadas = convUtil.getListMateriasCursadas(conEnoc, codigoAlumno, planConvalidar);
	
	/* Map de la materia no acreditada, el numero y la clave*/
	java.util.HashMap<String,String> mapCompara	= new java.util.HashMap<String,String>();
	
	// Busca equivalencias
	for(aca.kardex.ConvalidarMaterias conv: lisActualesNoAcreditadas){	
		noAcreditadas.add(conv.clave);
	}
	
	noAcreditadas.clear();

	//Grabar datos
	if(accion != "0"){		
		java.util.HashMap<String,String> mapActuales	= new java.util.HashMap<String,String>();
		
		for(int i= 0; i < lisActualesNoAcreditadas.size(); i++){	
			mapActuales.put(lisActualesNoAcreditadas.get(i).clave.trim(), String.valueOf(i));
		}
			
		for(int i= 0; i< lisAnteriorAcreditadas.size(); i++){
			ConvalidarMaterias conv = (ConvalidarMaterias) lisAnteriorAcreditadas.get(i);
			cursoIdConvalidar	= conv.cursoId;
			fecha				= conv.fecha;
			nota 				= conv.nota;
			convalidacion		= conv.convalidacion;
			
			String	indice 		= request.getParameter("NumMateria"+i)==null?"":request.getParameter("NumMateria"+i);

			if(indice != ""){
				ConvalidarMaterias cActual = (ConvalidarMaterias) lisActualesNoAcreditadas.get(Integer.parseInt(indice)-1);
				krdImport.setCodigoPersonal(codigoAlumno);
				krdImport.setCursoId(cActual.cursoId);
				krdImport.setNota(String.valueOf(nota));
				krdImport.setFCreada(fecha);
				krdImport.setCursoId2(cursoIdConvalidar);
				krdImport.setFolio(Integer.toString(importadoUtil.getFolioAlumno(conEnoc, codigoAlumno)));
				krdImport.setUsuario(codigoPersonal);
				krdImport.setFecha(aca.util.Fecha.getHoy());
				if(krdImport.insertConvalida(conEnoc)){
					
				}
			}else{	
				if(mapActuales.containsKey(lisAnteriorAcreditadas.get(i).clave.trim())){
					/*Obtenemos el num guardado en el map que corresponde a la clave que comparamos*/
					ConvalidarMaterias cActual = (ConvalidarMaterias) lisActualesNoAcreditadas.get(Integer.parseInt(mapActuales.get(lisAnteriorAcreditadas.get(i).clave.trim())));
					krdImport.setCodigoPersonal(codigoAlumno);
					krdImport.setCursoId(cActual.cursoId);
					krdImport.setNota(String.valueOf(nota));
					krdImport.setFCreada(fecha);
					krdImport.setCursoId2(cursoIdConvalidar);
					krdImport.setFolio(Integer.toString(importadoUtil.getFolioAlumno(conEnoc, codigoAlumno)));
					krdImport.setUsuario(codigoPersonal);
					krdImport.setFecha(aca.util.Fecha.getHoy());
					if(krdImport.insertConvalida(conEnoc)){
						
					}
				}
			}
		}
%>
		<script>document.location="convalidar?Mensaje=1";</script>
<%	
	}
%>

<body>
<div class="container-fluid">
	<h2>Convalidaciones<small class="text-muted fs-4"> ( [<%=codigoAlumno%>] [<%=NombreAlumno%>] )</small></h2>
	<%
	if(request.getParameter("Mensaje")!=null){
	%>
		<div class='alert alert-info'>Se convalidaron las materias</div>
	<%
	}
	%>
	<form name="frmimportplan" method="post" action="convalidar">
	<div class="alert alert-info">
		<select name="planConvalidar" id="planConvalidar" onChange="document.frmimportplan.submit()">
      		<option>Seleccione un plan de estudios</option>
<%	
	for(aca.kardex.ConvalidarPlan conv : lisPlanes){		
		if(conv.planId.equals(planConvalidar)){
			out.print(" <option value='"+conv.planId+"' Selected>"+conv.planId +"-"+conv.nombre+"</option>");
		}else{
			out.print(" <option value='"+conv.planId+"'>"+ conv.planId +"-"+conv.nombre +"</option>");
		}	
	}	
%>
	</select>
	</div>
<%-- 	<%if(!msj.equals("")) out.print(msj); %> --%>
	</form>
	
	<div class="row">
		<div class="col">
			<form name="frmConvalida" method="post" action="convalidar">
			<input name="Accion" type="hidden"> 
<%
			for(int i= 0; i<lisActualesNoAcreditadas.size(); i++){
				mapCompara.put(lisActualesNoAcreditadas.get(i).clave.trim(), String.valueOf(i+1));
			}

			cicloTem = 0;
			for(int i= 0; i < lisAnteriorAcreditadas.size();i++){
				ConvalidarMaterias conv = (ConvalidarMaterias) lisAnteriorAcreditadas.get(i);
				
				if(cicloTem != conv.ciclo){
					cicloTem = conv.ciclo;
%>      
				<table id="table" class="table table-sm table-bordered">
				<thead>	 
					<tr>
					   	<td colspan="3" class="etiqueta">Materias cursadas por el alumno</td>
					</tr>
				</thead>
				<thead>	 
					<tr>
				  		<td colspan="4" class="etiqueta">Semestre : <%=cicloTem%> </td>
					</tr>    
				</thead>
				<thead class="table-info">	 
					<tr>
						<th width="6%"><spring:message code="aca.Numero"/></th>
					  	<th width="10%"><spring:message code="aca.Clave"/></th>
					  	<th width="78%"><spring:message code="aca.Materia"/></th>
					  	<th width="16%">Calificaci&oacute;n</th>
					</tr>
				</thead>
<%
				 }	
	
				String num = "";	
				if(mapCompara.containsKey(lisAnteriorAcreditadas.get(i).clave.trim())){ 
					num = mapCompara.get(lisAnteriorAcreditadas.get(i).clave.trim());
				}
%>	 
					<tr>
						<td><input name="NumMateria<%=i%>" type="text" class="text" id="NumMateria<%=i%>" size="3" maxlength="3" onKeypress="if (window.event.keyCode < 48 || window.event.keyCode > 57) window.event.returnValue = false;" value="<%=num%>"></td>				  	
					  	<td><%=conv.clave%></td>
					  	<td><%=conv.nombre%></td>        
					  	<td><%=conv.nota%></td>        
					</tr>
				
<%			
			}	
			if(lisAnteriorAcreditadas.size() != 0 ){
%>	   
					<tr>
				  		<th align="center">
							<input name="planConvalidar" type="hidden" id="planConvalidar" value="<%=planConvalidar%>">
							<input type="button" class="btn btn-primary" name="Submit" value="Convalidar" onClick="Valida()">
				  		</th>
					</tr>
	      		</table>
<%
			}
%>	  	
			</form>	
		</div>
		<div class="col">
			<table id="table" class="table table-sm table-bordered">
			<thead>	 
			   	<tr>
			       	<td colspan="3" class="etiqueta">Plan actual del alumno [<%=planUtil.getNombrePlan(conEnoc, planActual )%>] </td>
				</tr>
			</thead>
<%				
			for(int i= 0; i<lisActualesNoAcreditadas.size(); i++){
				ConvalidarMaterias conv = (ConvalidarMaterias) lisActualesNoAcreditadas.get(i);	
				
				if( cicloTem!= conv.ciclo ){
					cicloTem = conv.ciclo;
%>	    	 
			<thead>	 
				<tr>
		    		<td colspan="3" class="etiqueta"><h4>Semestre : <%=cicloTem%></h4></td>
				</tr>
			</thead>
			<thead class="table-info">	 
			    <tr>
			    	<th width="6%"><spring:message code="aca.Numero"/></th>
			    	<th width="10%"><spring:message code="aca.Clave"/></th>
			    	<th width="94%"><spring:message code="aca.Materias"/></th>
			    </tr>
			</thead>
<%
				}
%>	  
				<tr>
	 	        	<td><%=i+1%></td>
					<td><%=conv.clave%></td>
					<td><%=conv.nombre%></td>
				</tr>
<%
			}
%>	  			
			</table>	
		</div>
	</div>

</div>
</body>
</html>
<%@ include file= "../../cierra_enoc.jsp" %> 