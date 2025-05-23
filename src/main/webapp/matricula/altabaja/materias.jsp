<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.acceso.spring.Acceso"%>
<%@ page import= "aca.carga.spring.Carga"%>
<%@ page import= "aca.carga.spring.CargaBloque"%>
<%@ page import= "aca.plan.spring.MapaPlan"%>
<%@ page import= "aca.carga.spring.CargaGrupo"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.catalogo.spring.CatModalidad"%>
<%@ page import= "aca.catalogo.spring.CatTipoCal"%>
<%@ page import= "aca.kardex.spring.KrdxCursoAct"%>
<%@ page import= "aca.alumno.spring.AlumPersonal"%>
<%@ page import= "aca.alumno.spring.AlumPlan"%>
<%@ page import= "aca.alumno.spring.AlumAcademico"%>
<%@ page import= "aca.alumno.spring.AlumEstado"%>
<%@ page import= "aca.vista.spring.AlumnoCurso"%>
<%@ page import= "aca.vista.spring.CargaAcademicaCarga"%>

<%@ include file= "id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>
<script>
	function ModificarEstado(cursoId){
		document.frmmaterias.accion.value = "1";
		document.frmmaterias.cursoCargaId.value = cursoId;
		document.frmmaterias.submit();
	}
	function EliminarMateria(cursoId){
		if (confirm("Are you sure you want to drop the subject?")==true){
			document.frmmaterias.accion.value = "2";
			document.frmmaterias.cursoCargaId.value = cursoId;
			document.frmmaterias.submit();
		}
	}
	
	function EliminarNotas(cursoId){
		if (confirm("Are you sure you want to drop the subject? This subject has already been graded")==true){
			document.frmmaterias.accion.value = "10";
			document.frmmaterias.cursoCargaId.value = cursoId;
			document.frmmaterias.submit();
		}
	}	
	
	function EnviarCursosDisp(){
		document.frmmaterias.accion.value = "4";
		document.frmmaterias.submit();
	}
	
	function BajaTotal(){
		if (confirm("All of the student's subjects will be dropped. Are you sure you want to completely drop the student?")==true){
			document.frmmaterias.accion.value = "5";
			document.frmmaterias.submit();
		}
	}
	function MateriaTitulo(cursoId){
		document.frmmaterias.accion.value = "6";
		document.frmmaterias.cursoCargaId.value = cursoId;
		document.frmmaterias.submit();		
	}
	function BajaTotalCoordinador(){
		if (confirm("All of the student's subjects will be dropped. Are you sure you want to completely drop the student?")==true){
			document.frmmaterias.accion.value = "8";
			document.frmmaterias.submit();
		}
	}
	
	function EnviarCursosGenerales(){
		document.frmmaterias.accion.value = "12";
		document.frmmaterias.submit();
	}
</script>
<%
	String matricula 		= (String) request.getAttribute("matricula");
	String codigoUsuario 	= (String) request.getAttribute("codigoUsuario");
	String cargaId 			= (String) request.getAttribute("cargaId");
	String bloqueId 		= (String) request.getAttribute("bloqueId");
	String cursoCargaId 	= (String) request.getAttribute("cursoCargaId");
	String accion 			= (String) request.getAttribute("accion");
	String planId 			= (String) request.getAttribute("planId");	
	boolean esAdmin 		= (boolean) request.getAttribute("esAdmin"); 
	String fraseTitulo 		= "";
	String carreraOrigen	= "";
	boolean okCarga			= false;	
	
	Acceso acceso = (Acceso) request.getAttribute("acceso");
	
	String planAlumno		= (String) request.getAttribute("planAlumno");	
	String carreraId		= (String) request.getAttribute("carreraId");	
	String nombrePlan		= (String) request.getAttribute("nombrePlan");	
	
	if ( matricula!=null && (matricula.substring(0,1).equals("1") || matricula.substring(0,1).equals("0") || matricula.substring(0,1).equals("2")) ){
	
	// Lista de cargas
	List<Carga> lisCarga 			= (List<Carga>) request.getAttribute("lisCarga");	
	List<CargaBloque> lisBloques	= (List<CargaBloque>) request.getAttribute("lisBloques");	
	List<MapaPlan> planes 			= (List<MapaPlan>) request.getAttribute("planes");	
	List<KrdxCursoAct> lisActual 	= (List<KrdxCursoAct>) request.getAttribute("lisActual");	
	List<CatTipoCal> lisTipoCal 	= (List<CatTipoCal>) request.getAttribute("lisTipoCal");	
  	List<AlumnoCurso> lisCursos 	= (List<AlumnoCurso>) request.getAttribute("lisCursos");	
  	
  	List<CargaAcademicaCarga> listCursosDisponibles 	= (List<CargaAcademicaCarga>) request.getAttribute("listCursosDisponibles");
  	
	HashMap<String, CargaGrupo> cargaGrupo			= (HashMap<String, CargaGrupo>) request.getAttribute("cargaGrupo");	
	HashMap<String, CatCarrera> mapCarrera 			= (HashMap<String, CatCarrera>) request.getAttribute("mapCarrera");	
	HashMap<String, CatModalidad> mapModalidad 		= (HashMap<String, CatModalidad>) request.getAttribute("mapModalidad");	
	HashMap<String, String> mapOrigen 				= (HashMap<String, String>) request.getAttribute("mapOrigen");
	HashMap<String, String> mapTipoCal 				= (HashMap<String, String>) request.getAttribute("mapTipoCal");
	HashMap<String, String> mapaNotaMateriasAlumno 	= (HashMap<String, String>) request.getAttribute("mapaNotaMateriasAlumno");
	HashMap<String,String> mapaPrerrequisitos  		= (HashMap<String, String>) request.getAttribute("mapaPrerrequisitos");
	HashMap<String, String> mapaMaestros 			= (HashMap<String, String>) request.getAttribute("mapaMaestros");
	HashMap<String, String> mapaExistenEvaluaciones = (HashMap<String, String>) request.getAttribute("mapaExistenEvaluaciones");
	HashMap<String, KrdxCursoAct> mapaKardex 		= (HashMap<String, KrdxCursoAct>) request.getAttribute("mapaKardex");
		
	AlumPersonal alumno 		= (AlumPersonal) request.getAttribute("alumno");	
	AlumPlan plan 				= (AlumPlan) request.getAttribute("plan");	
	AlumAcademico academico 	= (AlumAcademico) request.getAttribute("academico");	
	boolean cargaActiva			= (boolean) request.getAttribute("cargaActiva");	
	AlumEstado alumEstado		= (AlumEstado) request.getAttribute("alumEstado");	
	boolean esLinea 			= (boolean) request.getAttribute("esLinea");
%>
<body>
<div class="container-fluid">
	<h2>Additions and Dismissals <small class="text-muted fs-5">( <%=matricula%> - <%=alumno.getNombre()%> <%=alumno.getApellidoPaterno()%> <%=alumno.getApellidoMaterno()%> - <%=planAlumno%> - <%=nombrePlan%> - <%=academico.getModalidadId()%> - <%=academico.getResidenciaId()%> )</small></h2>
	<form name="frmmaterias" action="materias" target="_self" method="post">
	<input type="hidden" value="<%=cursoCargaId%>" name="cursoCargaId">
	<input type="hidden" name="accion">
	<div class="alert alert-info d-flex align-items-center">
		<b>Load:&nbsp;</b>
		<select name="CargaId" onChange="javascript:frmmaterias.submit()" class="form-select" style="width:350px;">
<%
			int i=0,j=0;
			String sCargaId="";
				// Buscando la carga
			for( i=0;i<lisCarga.size();i++){
				Carga carga = lisCarga.get(i);
				if (carga.getCargaId().equals(sCargaId)) okCarga = true;
			}
			
			for( i=0;i<lisCarga.size();i++){
				Carga carga = lisCarga.get(i);
				if ( lisCarga.size()==1 ){	sCargaId = carga.getCargaId(); }
				if (okCarga==false && i==0){ sCargaId= carga.getCargaId(); }
				if ( (carga.getCargaId().equals(cargaId)) || (okCarga==false && i==0)){
					out.print(" <option value='"+carga.getCargaId()+"' Selected>"+carga.getCargaId()+" - "+carga.getNombreCarga()+"</option>");
				}else{
					out.print(" <option value='"+carga.getCargaId()+"'>"+carga.getCargaId()+" - "+carga.getNombreCarga()+"</option>");
				}				
			}			
%>
		</select>&nbsp;&nbsp;
		<select name="BloqueId" onChange="javascript:frmmaterias.submit()" class="form-select" style="width:350px;">
<%			for (CargaBloque bloque : lisBloques){ %>
					<option value="<%=bloque.getBloqueId()%>" <%=bloqueId.equals(bloque.getBloqueId())?"selected":""%>><%=bloque.getBloqueId()%> - <%=bloque.getNombreBloque()%></option>
<%			} %>		
		</select>
<%		
  			for(int k=0; k<lisActual.size(); k++){
  				KrdxCursoAct tmpAct = lisActual.get(k);
  				if((tmpAct.getTipoCalId().equals("M") && tmpAct.getTipo().equals("A")) || (tmpAct.getTipoCalId().equals("I") && tmpAct.getTipo().equals("B"))){%>
  				&nbsp; &nbsp;<input type="button" class="btn btn-success" value="Formato" onclick="document.location.href='materiasPDF?CodigoAlumno=<%=matricula%>&CargaId=<%=cargaId%>&BloqueId=<%=bloqueId%>'"/>
<%				break;
	  			}
  			}
%>
	</div>								  
	<table class="table table-sm table-bordered table-striped" style="width:100%">
	<thead class="table-info">
    	<tr class="text-center">
	   		<th colspan="11">Assigned Subjects</th>
	   </tr>
	</thead>
<%	
	String nombreOptativa	= "";                     
	for( i=0;i<lisCursos.size();i++){		
		AlumnoCurso alumnoCurso = lisCursos.get(i);
				
		// Agrega el nombre de la  al nombre del curso
		if (alumnoCurso.getOptativa().length()>5){
			 nombreOptativa = alumnoCurso.getOptativa();
		}else{
			 nombreOptativa	= "";
		}
		
		// Busca el nombre de la carrera de origen de la materia							
		if (mapOrigen.containsKey( alumnoCurso.getCursoCargaId() )){
			carreraOrigen = mapOrigen.get( alumnoCurso.getCursoCargaId());
			if (mapCarrera.containsKey(carreraOrigen)) {
				carreraOrigen = mapCarrera.get(carreraOrigen).getNombreCarrera();									
			}								
		}						

		boolean existeEvaluacion = false;
		if (mapaExistenEvaluaciones.containsKey(alumnoCurso.getCursoCargaId())){
			existeEvaluacion = true;
		}		
		
		KrdxCursoAct kardex = new KrdxCursoAct();
		
		if (mapaKardex.containsKey(alumnoCurso.getCodigoPersonal()+alumnoCurso.getCursoCargaId())){
			kardex = mapaKardex.get(alumnoCurso.getCodigoPersonal()+alumnoCurso.getCursoCargaId());
		}						

		if (kardex.getTitulo().equals("S")) fraseTitulo="[T.S.]"; else fraseTitulo="[-]";
		
		String carreraNombre = "-";
		if(mapCarrera.containsKey(alumnoCurso.getCarreraId())) carreraNombre = mapCarrera.get(alumnoCurso.getCarreraId()).getNombreCarrera();
		String modalidadNombre = "-";
		if(mapModalidad.containsKey(alumnoCurso.getModalidadId())) modalidadNombre = mapModalidad.get(alumnoCurso.getModalidadId()).getNombreModalidad();
		String comentario = "";
		if(cargaGrupo.containsKey(alumnoCurso.getCursoCargaId())) comentario = cargaGrupo.get(alumnoCurso.getCursoCargaId()).getComentario();
		
		String nombreMaestro = "";
		if(mapaMaestros.containsKey(alumnoCurso.getMaestro())){
			nombreMaestro = mapaMaestros.get(alumnoCurso.getMaestro());
		}		
%>						  		
		<tr>
			<td width="7%" class="text-center"><span class="badge rounded-pill bg-dark"><%=i+1%></span></td>
			<td width="57%" title="<%=carreraOrigen%>"><b><%=alumnoCurso.getNombreCurso()%> (<%=nombreOptativa%>) - [ Group: <%=alumnoCurso.getGrupo()%> ] 
				<a href="javascript:MateriaTitulo('<%=alumnoCurso.getCursoCargaId()%>')" title="Sufficiency Title"><b><%=fraseTitulo%></b></a></b>
				<span class="badge bg-dark"><%=alumnoCurso.getCursoCargaId()%></span>&nbsp;&nbsp;
				<b>Lecturer:</b>&nbsp;<%=nombreMaestro%> : <%=comentario%>
			</td>
			<td width="7%" align="center"><b>[ Cr: <%=alumnoCurso.getCreditos()%> ]</b></td>
			<td width="9%" align="center"><b> [ Grade: <%=alumnoCurso.getNota()%> ]</b></td>								  
			<td width="12%" align="center">
<%		if (accion.equals("1") && alumnoCurso.getCursoCargaId().equals(cursoCargaId) && acceso.getAdministrador().equals("S")){%>
	  			<select name="nuevoEstado" onChange="javascript:frmmaterias.submit()" class="form-select" style="width:120px;">
<%			for( j=0;j<lisTipoCal.size();j++){
				CatTipoCal catTipoCal = lisTipoCal.get(j);%>
					<option value="<%=catTipoCal.getTipoCalId()%>" <%if(alumnoCurso.getTipoCalId().equals(catTipoCal.getTipoCalId())) out.print("selected");%>>
						<%=catTipoCal.getNombreTipoCal()%>
					</option>
<%			}%>
					<script>frmmaterias.accion.value="3";</script>
				</select>
<%		}else{
			String nombreTipoCal = "";						
			if(mapTipoCal.containsKey(alumnoCurso.getTipoCalId())){
				nombreTipoCal = mapTipoCal.get(alumnoCurso.getTipoCalId());
			}									
%>			
			<b>[ <%=nombreTipoCal%> ]</b>
<%		}
%>			</td>
			<td width="8%" align="center">
<% 		if(acceso.getAdministrador().equals("S")){%>
				<a href="javascript:ModificarEstado('<%=alumnoCurso.getCursoCargaId()%>')" title="Edit Status"><i class="fas fa-edit"></i></a>
<%  		if(existeEvaluacion){ %>		
				<a href="javascript:EliminarNotas('<%=alumnoCurso.getCursoCargaId()%>')" title="Delete Grades"><i class="fas fa-exclamation-triangle"></i></a>
<% 			}else{ %>
				<a href="javascript:EliminarMateria('<%=alumnoCurso.getCursoCargaId()%>')" title="Delete Subject"><i class="fas fa-trash-alt"></i></a>		
<% 			}
		}else{
			kardex = new KrdxCursoAct();			
			if (mapaKardex.containsKey(matricula+alumnoCurso.getCursoCargaId())){
				kardex = mapaKardex.get(matricula+alumnoCurso.getCursoCargaId());
			}						
			if(acceso.getAccesos().contains(carreraId)){
				if((kardex.getTipoCalId().equals("I")) && (kardex.getTipo().equals("O"))){%>
					<a href="materias?cursoCargaId=<%=alumnoCurso.getCursoCargaId()%>&accion=7&CargaId=<%=cargaId%>&BloqueId=<%=bloqueId%>" title="Drop"><img src="../../imagenes/alta.png"  width="16"></a>
<%				}else if ((kardex.getTipoCalId().equals("I")) && (kardex.getTipo().equals("B"))){ %>
					<a href="materias?cursoCargaId=<%=alumnoCurso.getCursoCargaId()%>&accion=9&CargaId=<%=cargaId%>&BloqueId=<%=bloqueId%>" title="Add New"><img src="../../imagenes/baja.png"  height="16"></a>
<% 				}
			}
		}		
%>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>				
	    		<%=alumnoCurso.getPlanId()%> &nbsp;Semester: <%=alumnoCurso.getCiclo()%> &nbsp;Block: <%=alumnoCurso.getBloqueId()%> &nbsp;<%=modalidadNombre%> &nbsp;
	    		[<strong>
<%							
		String tipo = "";
		if (kardex.getTipo().equals("A") ){
			tipo = "Addition";
		}else if (kardex.getTipo().equals("B") ){
			tipo = "Drop";
		}else{
			tipo = "Origin";
		}
		if(kardex.getTipo().equals("A") || kardex.getTipo().equals("B") && esAdmin){%>
			<a href="materias?accion=11&cursoCargaId=<%=alumnoCurso.getCursoCargaId()%>&CargaId=<%=cargaId%>"><%=tipo%></a>
<%		}else{out.print(tipo);}%>
				</strong>]
			</td>
		</tr>								  
<%	} %>
	</table>                        
<%	if (esAdmin){%>                        
		<a class="btn btn-danger" href="javascript:BajaTotal()"><font size="2"><b>Complete Drop Out (A) </b></font></a>
<% 	}else{
		if(acceso.getAccesos().contains(carreraId)){%>
		<a class="btn btn-primary" href="javascript:BajaTotalCoordinador()" title="Request dismissal"><font size="2"><b>Complete Drop Out (C)</b></font></a>
<%		}
	}				

	if(acceso.getAccesos().contains(carreraId) || acceso.getAdministrador().equals("S")){ 
%>
	<table  class="mt-4 table table-bordered table-sm table-striped">
	<thead class="table-info">
		<tr><td align="center" colspan="5" class="titulo2"><b>Available Subjects</b></td></tr>
		<tr>
		  	<th width="9%"><spring:message code="aca.Semestre"/></th>
		  	<th width="70%">Subject / Lecturer</th>
		  	<th width="7%" class="text-center">Cr.</th>
		  	<th width="7%" class="text-center">Hr.</th>
		  	<th width="7%" class="text-center"><spring:message code="aca.Operacion"/></th>
  		</tr>
  	</thead>						
<%				
		String sNombreCurso 	= "";
		String sModalidad 		= (String) request.getAttribute("sModalidad");	
		String sColorCurso 		= "";
		
		int contador = 1;
		
		// Alumnos presenciales pueden llevar materias en linea UM
		if (academico.getModalidadId().equals("1")) sModalidad = "1,5";
		
		// Alumnos de verano pueden llevar materias presenciales
		if (academico.getModalidadId().equals("3")) sModalidad = "1,3";
		
		if(carreraId.equals("107")) sModalidad = "1,4";
		
		boolean preOK = false;
		i = 0;
				
		for (CargaAcademicaCarga cargaAcademicaCarga : listCursosDisponibles){
			
			sNombreCurso = cargaAcademicaCarga.getNombreCurso();			
			if (cargaAcademicaCarga.getOptativa().length()>5){
				sNombreCurso = sNombreCurso+" ("+cargaAcademicaCarga.getOptativa()+")";
			}	
			
		  	String comentario = "-";			  
		  	if (cargaGrupo.containsKey(cargaAcademicaCarga.getCursoCargaId())){
				if (mapCarrera.containsKey(cargaAcademicaCarga.getCursoCargaId())){
					carreraOrigen =  mapCarrera.get(cargaAcademicaCarga.getCursoCargaId()).getNombreCarrera();
				}
				
				comentario = cargaGrupo.get(cargaAcademicaCarga.getCursoCargaId()).getComentario();
					
				if(comentario==null){
					comentario = "-";
				}
		  	}	 
			
		  	if(mapaPrerrequisitos.containsKey(cargaAcademicaCarga.getCursoId())){
				if (mapaPrerrequisitos.get(cargaAcademicaCarga.getCursoId()).equals("S")){
					preOK = true;
				}else{
					preOK = false;
				}
		  	}
						
			if (preOK) sColorCurso = "000066"; else sColorCurso = "FF0000";
			
			String nomCorto ="-";				  	
			if (mapCarrera.containsKey(cargaAcademicaCarga.getCarreraId())){
				nomCorto = mapCarrera.get(cargaAcademicaCarga.getCarreraId()).getNombreCorto();
			}
%>				  			
		<tr>
  			<td width="9%" align="center"><font color="#<%=sColorCurso%>"><%=cargaAcademicaCarga.getCiclo()%></font></td>
  			<td width="70%" class="ayuda mensaje Base: <%=carreraOrigen%> <%=comentario%>">
  				<font color="#<%=sColorCurso%>">
  				<b><%=sNombreCurso%></b>
  				<span class="badge bg-dark"><%=cargaAcademicaCarga.getGrupo()%></span>  			
  			<%if(cargaAcademicaCarga.getEstado().equals("1")){%>
            	<span class="badge bg-warning">Empty</span> 
            <%}else if(cargaAcademicaCarga.getEstado().equals("2")){%>
            	<span class="badge bg-info">Ordinary</span>            				
            <%}else if(cargaAcademicaCarga.getEstado().equals("3")){%>
            	<span class="badge bg-success">Remedial</span>            				 
            <%}else if(cargaAcademicaCarga.getEstado().equals("4")){%>
            	<span class="badge bg-danger">Closed</span> 
            <%}else if(cargaAcademicaCarga.getEstado().equals("5")){%>
            	<span class="badge bg-dark">Registered</span> 
            <%}%>
  				<span class="badge bg-dark"><%=cargaAcademicaCarga.getCursoCargaId()%></span>
  				<br>
  				<b><i>Lecturer </i>:</b> <%=cargaAcademicaCarga.getProfesor()%> &nbsp;<b>Block: </b><%=cargaAcademicaCarga.getBloque()%> &nbsp;<i><%=nomCorto%></i>&nbsp; <b>Comment :</b> <%=comentario%>
  				</font>  				
			</td>
  			<td width="7%" align="center"><font color="#<%=sColorCurso%>"><%=cargaAcademicaCarga.getCreditos()%></font></td>
  			<td width="7%" align="center"><font color="#<%=sColorCurso%>"><%=cargaAcademicaCarga.getHoras()%></font></td>
  			<td width="7%" align="center">                       
<%			if(cargaAcademicaCarga.getEstado().equals("1") || cargaAcademicaCarga.getEstado().equals("2")){ %>
<%				if ( !cargaAcademicaCarga.getProfesor().equals("Sin Maestro") && preOK || codigoUsuario.equals("9800058")){ %>
				<input type="hidden" name="cursoCargaId<%=contador%>" value="<%=cargaAcademicaCarga.getCursoCargaId()%>">
				<input type="hidden" name="cursoId<%=contador%>" value="<%=cargaAcademicaCarga.getCursoId()%>">
				<input type="checkbox" name="chkMateria<%=contador%>" value="1">
<%				}else{%>							 	
				<font color="#<%=sColorCurso%>" size="1">Does not meet requirements</font>
<%				}
			}else{
				out.print("&nbsp;");										
			}
%>
			</td>
		</tr>					
<%				
			contador++;
		} // Termina for
%>				<input type="hidden" name="Total" value="<%=listCursosDisponibles.size()%>">  
		<tr valign="middle" height="60">
			<td colspan="5" style="text-align:center"><input class="btn btn-primary" name="EnviarCursos" onClick="javascript:EnviarCursosDisp()" type="button" value="Add Subjects"></td>
		</tr>
	</table>			 				
<%						 
	} 
%>
	</form>
	<table style="width:100%"><tr><td background="../../imagenes/shadow.gif" height="4"></td></tr></table>
<% 
	}else{	
%>
	<h2>� Select a student before entering this screen !</h2>
<% }%>
</div>