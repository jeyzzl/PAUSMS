<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.acceso.spring.Acceso"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.alumno.spring.AlumAcademico"%>
<%@page import="aca.carga.spring.CargaAlumno"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">	
	function grabaCarga(){
  		document.location.href="proceso?CargaId="+document.frmCarga.CargaId.value;
	}
	
	function borraMaterias(carga){
		if(confirm("Esta operación cancela las cargas académicas pendientes en el periodo...! ¿Estás seguro de cancelar?")==true){
  			document.location.href="borrarCarga?CargaId="+carga;
  		}	
	}
	
	function borraMateriasAlumno(carga,codigo){
		if(confirm("¿Estas seguro de cancelar la carga académica de este alumno?")==true){
  			document.location.href="borrarAlumno?CargaId="+carga+"&CodigoAlumno="+codigo;
  		}	
	}
</script>

<!-- inicio de estructura -->
<%	
	String codigoUsuario		= (String) session.getAttribute("codigoPersonal");
	
	String cargaId				= (String)request.getAttribute("cargaId");
	Acceso acceso				= (Acceso)request.getAttribute("acceso");
	boolean esAdmin				= (boolean)request.getAttribute("esAdmin");
	boolean cargaActiva			= (boolean)request.getAttribute("cargaActiva");
	
	String codigoAlumno 		= "";
	
	int cont = 1;
	int nCargas = 0, nCalculos = 0;
	int nInternos=0, nExternos =0;
	int nAcfe=0, nNoacfe=0;
	
	List<Carga> lisCargas 							= (List<Carga>)request.getAttribute("lisCargas");
	List<CargaAlumno> lisMaterias 					= (List<CargaAlumno>)request.getAttribute("lisMaterias");
	List<CargaAlumno> lisCalculos 					= (List<CargaAlumno>)request.getAttribute("lisCalculos");
	HashMap<String,String> mapaAlumnos				= (HashMap<String,String>)request.getAttribute("mapaAlumnos");
	HashMap<String,AlumAcademico> mapaAcademicos	= (HashMap<String,AlumAcademico>)request.getAttribute("mapaAcademicos");
	HashMap<String,String> mapaPlanes				= (HashMap<String,String>)request.getAttribute("mapaPlanes");
	HashMap<String,CatCarrera> mapaCarreras			= (HashMap<String,CatCarrera>)request.getAttribute("mapaCarreras");
	HashMap<String,CatFacultad> mapaFacultades		= (HashMap<String,CatFacultad>)request.getAttribute("mapaFacultades");
%>
<div class="container-fluid">
	<h1>Alumnos en Proceso</h1>
	<form name="frmCarga">
	<div class="alert alert-info">
		<spring:message code="aca.Carga"/>:
   		<select onchange='javascript:grabaCarga()' name="CargaId" class="input input-xxlarge">
<%
	for (Carga carga : lisCargas){
%>	
			<option <%if(cargaId.equals(carga.getCargaId()))out.print(" Selected ");%> value="<%=carga.getCargaId()%>">[<%=carga.getCargaId() %>] <%=carga.getNombreCarga()%></option>
<%	} %>
		</select>&nbsp;
<%	if(esAdmin && !cargaActiva){ %>
		<a class="btn btn-primary" href="javascript:borraMaterias('<%=cargaId %>')"><b>Cancelar alumnos en proceso</b></a>
<%	}%>		
	</div>
	</form>
<!-- Tabla Principal de alumnos en proceso con carga de materias-->
	<div class="alert alert-warning">
		<h3>Alumnos con carga de materias</h3>
	</div>
	<table style="width:100%" class="table table-condensed">    
<%
	String facultadId			= "X";
	String carreraId			= "X";
	String facultadRow			= "";
	String carreraRow			= "";
	String nombreFacultad		= "-";
	String nombreCarrera		= "-";
	for (CargaAlumno materia : lisMaterias){
		if (mapaPlanes.containsKey(materia.getPlanId())){
			carreraRow = mapaPlanes.get(materia.getPlanId());
		}
		if( acceso.getAccesos().indexOf(carreraRow)!= -1 || acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S") ){		
			nCargas++;
			if (mapaCarreras.containsKey(carreraRow)){				
				facultadRow = mapaCarreras.get(carreraRow).getFacultadId();
			}
			
			if(!facultadId.equals(facultadRow)){
		    	facultadId = facultadRow;
		    	nombreFacultad = "-";
		    	if (mapaFacultades.containsKey(facultadRow)){
		    		nombreFacultad = mapaFacultades.get(facultadRow).getNombreFacultad();
		    	}				
%>	 		
  		<tr>
    		<td align="center" colspan="8"><b><font color="#000099" size="3"><%=nombreFacultad%></font></b></td>
  		</tr>
<%  
       		}//fin del if de facultades diferentes
			if( !carreraId.equals( carreraRow) ){
	   			carreraId 		= carreraRow;		  
				nombreCarrera 	= "-";
				if (mapaCarreras.containsKey(carreraRow)){
		    		nombreCarrera = mapaCarreras.get(carreraRow).getNombreCarrera();
		    	}
%>		
		<tr><td colspan="7"><b><font color="#000099" size="2">Programa: <%=nombreCarrera%></font></b></td>
		</tr>
		<tr> 
			<th width="2%" align="center">&nbsp;</th>
		    <th width="5%" align="center"><spring:message code="aca.Numero"/></th>
		    <th width="5%" align="center"><spring:message code="aca.Matricula"/></th>
		    <th width="43%" align="center"><spring:message code="aca.Nombre"/></th>
		    <th width="10%" align="center">Plan</th>
		    <th width="10%" align="center">Resi.</th>
		    <th width="10%" align="center">Clas.Fin.</th>
		    <th width="15%" align="center"><spring:message code="aca.Modalidad"/></th>
		</tr>
<%        	cont = 1;
          	}//fin del if de carreras diferentes
			
			String nombreAlumno = "";
          	if(mapaAlumnos.containsKey(materia.getCodigoPersonal())){
          		nombreAlumno = mapaAlumnos.get(materia.getCodigoPersonal());
          	}
          	
          	AlumAcademico academico = new AlumAcademico();
          	if(mapaAcademicos.containsKey(materia.getCodigoPersonal())){
          		academico = mapaAcademicos.get(materia.getCodigoPersonal());
          	}
          	
          	if (academico.getResidenciaId().equals("E")) nExternos++; else nInternos++;
          	if (academico.getClasFin().equals("1")) nAcfe++; else nNoacfe++;
%>
		<tr> 
    		<td align="center">
<%			if(esAdmin && !cargaActiva){ %>
    			<a class="btn btn-danger" href="javascript:borraMateriasAlumno('<%=cargaId %>','<%=materia.getCodigoPersonal()%>')">borrar</a>
<%			}%>    	
    		</td>
    		<td align="center"><%=cont%></td>
    		<td align="center"><%=materia.getCodigoPersonal()%></td>
    		<td><%=nombreAlumno%></td>
    		<td><%=materia.getPlanId()%></td>
 			<td align="center"><%=academico.getResidenciaId().equals("E")?"Externo":"interno"%></td>
    		<td align="center"><%=academico.getClasFin().equals("1")?"SOCIO":"NO SOCIO"%></td>
    		<td align="center"><%=academico.getModalidad()%></td>
  		</tr> 
  <%  
     	cont++;
		} 
	 } // fin del while	 
%>
	</table> 
	<table style="width:80%"  class="table table-condensed">
  	<tr> 
    	<th width="12%">Cargas: <%=nCargas%></th>
    	<th width="16%">Internos: <%=nInternos%></th>
    	<th width="14%" align="center">Externos: <%=nExternos%></th>
    	<th width="14%" align="center">ACFE: <%=nAcfe%></th>
    	<th width="16%" align="center">NO ACFE: <%=nNoacfe%></th>
  	</tr>
	</table>
	<div class="alert alert-success">
		<h3>Alumnos con calculos de Cobro )</h3>
	</div>
<!-- Tabla Principal de alumnos en proceso con cálculo de cobro-->
	<table style="width:80%"    class="table table-condensed">
<%
	cont 				= 1; 	
	nInternos			= 0; 	
	nExternos			= 0;
	nAcfe				= 0; 	
	nNoacfe				= 0;
	
	facultadId			= "X";
	carreraId			= "X";
	facultadRow			= "0";
	carreraRow			= "0";
	nombreFacultad		= "-";
	nombreCarrera		= "-";
	
	for (CargaAlumno calculo : lisCalculos){
		
		if (mapaPlanes.containsKey(calculo.getPlanId())){
			carreraRow = mapaPlanes.get(calculo.getPlanId());
		}
		
		if( acceso.getAccesos().indexOf(carreraRow)!= -1 || acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S") ){
			
			nCalculos++;			
			if (mapaCarreras.containsKey(carreraRow)){
				facultadRow = mapaCarreras.get(carreraRow).getFacultadId();
			}
			
			if(!facultadId.equals(facultadRow)){
		    	facultadId = facultadRow;
		    	
		    	nombreFacultad = "-";
		    	if (mapaFacultades.containsKey(facultadRow)){
		    	
		    		nombreFacultad = mapaFacultades.get(facultadRow).getNombreFacultad();
		    	
		    	}		    	
%>
		<tr> 
		    <td align="center" colspan="7">&nbsp;</td>
		</tr>
		<tr> 
		    <td align="center" colspan="7"><b><font color="#000099" size="3"><%=nombreFacultad%></font></b></td>
		</tr>
<%  
       		}//fin del if de facultades diferentes
			
			if(!carreraId.equals( carreraRow)){
	   			carreraId = carreraRow;		  
	   			nombreCarrera 	= "-";
				if (mapaCarreras.containsKey(carreraRow)){
		    		nombreCarrera = mapaCarreras.get(carreraRow).getNombreCarrera();
		    }			
%>
		<tr>
		    <td colspan="7">&nbsp;</td>
		</tr>
		<tr> 
		    <td colspan="7"><b><font color="#000099" size="2">Programa: <%=nombreCarrera%></font></b></td>
		</tr>
		<tr> 
		    <th width="2%" align="center">&nbsp;</th>
		    <th width="5%" align="center"><spring:message code="aca.Numero"/></th>
		    <th width="5%" align="center"><spring:message code="aca.Matricula"/></th>
		    <th width="43%" align="center"><spring:message code="aca.Nombre"/></th>
		    <th width="10%" align="center">Plan</th>
		    <th width="10%" align="center">Resi.</th>
		    <th width="10%" align="center">Clas. Fin.</th>
		    <th width="15%" align="center"><spring:message code="aca.Modalidad"/></th>
		</tr>
  <%
     		cont = 1;
    	}//fin del if de carreras diferentes
		
		String nombreAlumno = "";
        if(mapaAlumnos.containsKey(calculo.getCodigoPersonal())){
        	nombreAlumno = mapaAlumnos.get(calculo.getCodigoPersonal());
        }
          	
        AlumAcademico academico = new AlumAcademico();
        if(mapaAcademicos.containsKey(calculo.getCodigoPersonal())){
        	academico = mapaAcademicos.get(calculo.getCodigoPersonal());
        }
        
        if (academico.getResidenciaId().equals("E")) nExternos++; else nInternos++;
      	if (academico.getClasFin().equals("1")) nAcfe++; else nNoacfe++;
%>
	<tr> 
    	<td align="center">
<%		if(esAdmin && !cargaActiva){ %>
    	<a class="btn btn-danger" href="javascript:borraMateriasAlumno('<%=cargaId %>','<%=calculo.getCodigoPersonal()%>')">borrar</a>
<%		}%>    
    	</td>
    	<td align="center"><%=cont%></td>
    	<td align="center"><%=calculo.getCodigoPersonal()%></td>
    	<td ><%=nombreAlumno%></td>
    	<td ><%=calculo.getPlanId()%></td>
 		<td align="center"><%=academico.getResidenciaId().equals("E")?"Externo":"interno"%></td>
    	<td align="center"><%=academico.getClasFin().equals("1")?"SOCIO":"NO SOCIO"%></td>
    	<td align="center"><%=academico.getModalidad()%></td>    	
  	</tr>
  <%
     	cont++;
		} 
 	} // fin del while 
%>
	</table>
	<table style="width:80%"  class="table table-condensed">
	<tr> 
    	<th width="12%">Calculos: <%=nCalculos%></th>
    	<th width="16%">Internos: <%=nInternos%></th>
    	<th width="14%" align="center">Externos: <%=nExternos%></th>
    	<th width="14%" align="center">ACFE: <%=nAcfe%></th>
    	<th width="16%" align="center">NO ACFE: <%=nNoacfe%></th>
  	</tr>
	</table>
</div>