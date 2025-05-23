<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="PlanEval" scope="page" class="aca.carga.CargaPlanEval"/>
<jsp:useBean id="PlanEvalU" scope="page" class="aca.carga.CargaPlanEvalUtil"/>
<head>
<script type="text/javascript" src="../../js/popcalendar.js"></script>
<script type="text/javascript">

	function Limpiar(){		
		document.frmPlan.action += "&Accion=5";  return true;			
	}
	
	function Guardar(){
		if(document.frmPlan.Evaluacion.value != "" && 
		   document.frmPlan.Nombre.value != "" && 
		   document.frmPlan.Fecha.value != "" && 
		   document.frmPlan.Valor.value != "" ){
		   document.frmPlan.action += "&Accion=2";
			return true;
		}else{
			alert("Complete las casillas requeridas (*) para poder guardar");
		}
		return false;		
	}
	
	function Actualizar(){
		if (confirm("Esta operación actualiza las evaluaciones, concentrando los valores capturados en la sección de Organización y Evaluación") == true){
			document.frmPauta.action += "&Accion=4";			
			return true;
		}
		return false;	
	}

</script>
</head>
<% 
	java.text.DecimalFormat formato = new java.text.DecimalFormat("###,##0.00;-###,##0.00");

	String cursoCargaId			= (String) session.getAttribute("CursoCargaId");
	String maestro 	            = (String) session.getAttribute("Maestro");
	String materia 	            = (String) session.getAttribute("Materia");
    String evaluacionId         = request.getParameter("Evaluacion")==null?"":request.getParameter("Evaluacion");
    int accion 		            = request.getParameter("Accion")==null?0:Integer.parseInt(request.getParameter("Accion"));
    
    String Fecha                = aca.util.Fecha.getHoy();   
    String carreraId	        = aca.carga.CargaGrupoUtil.getCarreraId(conEnoc, cursoCargaId);
    String nivelId   	        = aca.catalogo.CatCarreraUtil.getNivelId(conEnoc, carreraId);
        
    double sumaEvaluaciones 	= 0;    
    String Resultado            = "";
    
    ArrayList listEvaluaciones  =  null;
    
    if (accion==5){
    	PlanEval.setEvaluacionId(PlanEvalU.maximoReg(conEnoc, cursoCargaId));
    }else if (accion == 1){ 
    	 
    	PlanEval.setCursoCargaId(cursoCargaId);
 		PlanEval.setEvaluacionId("1");
 		if (nivelId.equals("1")){
 			PlanEval.setEvaluacionNombre("Evaluación de Medio Tetramestre");
 		}else{
 			PlanEval.setEvaluacionNombre("Evaluación de Medio Semestre");
 		}
 		PlanEval.setFecha(Fecha);
 		PlanEval.setValor("2");
 		PlanEval.setActividadId("00");
 		
 		if (PlanEvalU.existeReg(conEnoc, cursoCargaId, "1") == false){
 			if (PlanEvalU.insertReg(conEnoc, PlanEval)){
 				Resultado = "Grabado: "+PlanEval.getCursoCargaId(); 				
 			}else{
 				Resultado = "No Grabó: "+PlanEval.getCursoCargaId();
 			}
 		}
 		
 		PlanEval.setCursoCargaId(cursoCargaId);
 		PlanEval.setEvaluacionId("2");
 		PlanEval.setEvaluacionNombre("Evaluación Final");
 		PlanEval.setFecha(Fecha);
 		PlanEval.setValor("3");
 		PlanEval.setActividadId("00");
 		
 		if (PlanEvalU.existeReg(conEnoc, cursoCargaId, "2") == false){
 			if (PlanEvalU.insertReg(conEnoc, PlanEval)){
 				Resultado = "Grabado: "+PlanEval.getCursoCargaId(); 				
 			}else{
 				Resultado = "No Grabó: "+PlanEval.getCursoCargaId();
 			}
 		}
 		
    	PlanEval.setCursoCargaId(cursoCargaId);
    	PlanEval.setEvaluacionNombre("");
    	PlanEval.setFecha("");
    	PlanEval.setValor("");
    	PlanEval.setEvaluacionId(PlanEvalU.maximoReg(conEnoc, cursoCargaId));
    	Resultado = "Nuevo...!!";
	}else{
		PlanEval.setCursoCargaId(cursoCargaId);
		PlanEval.setEvaluacionId(request.getParameter("Evaluacion"));
	}
     
     //operaciones a realizar 
	switch (accion){
	 
		case 0: { // Consulta
	    	Resultado = "Consulta..¡¡";
	   		if (PlanEvalU.existeReg(conEnoc, cursoCargaId, evaluacionId)){
	   			PlanEval.mapeaRegId(conEnoc, cursoCargaId, evaluacionId);	   			
	   		}
			break;
	    }
	       
	    case 2: { // Grabar y modificar
	    	PlanEval.setCursoCargaId(cursoCargaId);
	    	PlanEval.setEvaluacionId(request.getParameter("Evaluacion"));
	    	PlanEval.setEvaluacionNombre(request.getParameter("Nombre"));
	    	PlanEval.setFecha(request.getParameter("Fecha"));
	    	PlanEval.setValor(request.getParameter("Valor"));
	    	PlanEval.setActividadId(request.getParameter("Actividad"));
	    	
	    	if(aca.carga.CargaPlanEvalUtil.getSumEvaluaciones(conEnoc, cursoCargaId)<100){
	    	if (PlanEvalU.existeReg(conEnoc, cursoCargaId, request.getParameter("Evaluacion")) == false){
	    		PlanEval.setActividadId("77");
				if (PlanEvalU.insertReg(conEnoc, PlanEval)){
					Resultado = "Grabado: "+PlanEval.getCursoCargaId();					
					PlanEval.setEvaluacionId(PlanEvalU.maximoReg(conEnoc, cursoCargaId));
				}else{
					Resultado = "No Grabó: "+PlanEval.getCursoCargaId();
				}
			}else{	
				if (PlanEvalU.updateReg(conEnoc, PlanEval)){ 
					Resultado = "La estrategia:"+PlanEval.getCursoCargaId()+"ha sido modificada";										
				}else{
					Resultado = "No Cambió: "+PlanEval.getCursoCargaId();
				}
			}
	      }else{
	    	  Resultado = "<font size='3' color='orange'><b>La sumatoria de las actividades de la estrategia tiene que ser igual a 100</b></font>";
	      }
			break;	
			
		}
	    
		case 3:{//borrar
			
			PlanEval.setCursoCargaId(cursoCargaId);
			PlanEval.setEvaluacionId(request.getParameter("Evaluacion"));			
			
			if (PlanEvalU.existeReg(conEnoc, cursoCargaId, request.getParameter("Evaluacion")) == true){
				if (PlanEvalU.deleteReg(conEnoc, cursoCargaId, request.getParameter("Evaluacion"))){
					Resultado = "Borrado: "+PlanEval.getCursoCargaId();					
				}else{
					Resultado = "No Borró: "+PlanEval.getCursoCargaId();
				}	
			}else{
					Resultado = "No existe: "+PlanEval.getCursoCargaId();
			}
			break;
		}
		
		case 4:{  //Actualizar evaluaciones
			conEnoc.setAutoCommit(false);
			/********** Borrar las evaluaciones generadas automaticamente del contenido********/
			aca.carga.CargaPlanEvalUtil.deleteEvaluaciones(conEnoc, cursoCargaId);
			
			ArrayList<String> lisAct = aca.carga.CargaUnidadActividadUtil.getDistintasActividades(conEnoc, cursoCargaId, "ORDER BY 1");
			for(int j=0;j<lisAct.size();j++){
				String actividadId 			= (String)lisAct.get(j);
				String actividadNombre		= aca.catalogo.CatActividad.getDescripcion(conEnoc, actividadId);							
				double suma 				= aca.carga.CargaUnidadActividadUtil.sumaValorActividad(conEnoc, cursoCargaId, actividadId);
				PlanEval.setCursoCargaId(cursoCargaId);
		    	PlanEval.setEvaluacionId(PlanEvalU.maximoReg(conEnoc, cursoCargaId));
		    	PlanEval.setEvaluacionNombre(actividadNombre);
		    	PlanEval.setFecha(aca.util.Fecha.getHoy());
		    	PlanEval.setValor(String.valueOf(suma));
		    	PlanEval.setActividadId(actividadId);
		    	if (PlanEvalU.existeActividad(conEnoc, cursoCargaId, PlanEval.getActividadId())){
		    		if (PlanEvalU.updateActividad(conEnoc, PlanEval)){
		    			conEnoc.commit();
		    		}else{		
		    			conEnoc.rollback();
					}
				}else{
		    		if (PlanEvalU.insertReg(conEnoc, PlanEval)){
		    			conEnoc.commit();
		    		}else{		    			
		    			conEnoc.rollback();
		    		}
		    	}								
			}
			conEnoc.setAutoCommit(true);
			break;
		}	
		
	 }
     listEvaluaciones = PlanEvalU.getListEvaluaciones(conEnoc, cursoCargaId, "ORDER BY EVALUACION_ID");
%>
<body>
<table style="width:100%">
   <tr>
     <td colspan="2" align="center" style="font-size:10pt"><strong><%= Resultado %></strong></td>
   </tr>
   <tr>
     <td><a href="plan?CursoCargaId=<%=cursoCargaId%>&Maestro=<%=maestro %>&Materia=<%=materia%>">&lsaquo;&lsaquo; <spring:message code="aca.Regresar"/></a></td>
   </tr>
   <tr>
     <td colspan="2" align="center" style="font-size:12pt"><strong>Pautas para la Evaluación</strong></td>
   </tr>  
</table>
<form name="frmPlan" method="post" action="pautas?CursoCargaId=<%=cursoCargaId%>">
  <input type="hidden" name="Accion">
  <input type="hidden" name="Actividad" value="<%=PlanEval.getActividadId()%>">
  <table style="margin: 0 auto;  width:50%"> 
    <tr><td>&nbsp;</td></tr>
    <tr><td>&nbsp;</td></tr>
    <tr>
      <td style="background-color: #5FB404;font-size: 10pt; color:white;" align="center" colspan="2" align="center" width="50%"><b>Evaluaciones</b></td>     
    </tr>
    <tr><td>&nbsp;</td></tr>
    <tr>
      <td><strong><spring:message code="aca.Folio"/></strong></td>
      <td>
        <input name="Evaluacion" type="text" class="text" id="Evaluacion" value="<%= PlanEval.getEvaluacionId() %>" size="3" maxlength="2"> 
      </td>         
    </tr>
    <tr> 
      <td><strong><spring:message code="aca.Nombre"/></strong></td>
      <td>       
        <input name="Nombre" type="text" class="text" id="Nombre" value="<%= PlanEval.getEvaluacionNombre() %>" size="30" maxlength="70">        
      </td>          
    </tr>
     <tr> 
      <td><strong><spring:message code="aca.Fecha"/></strong></td>
      <td>      
         <input name="Fecha" type="text" class="text" id="Fecha" size="10" maxlength="10" onfocus="focusFecha(this);" value="<%= PlanEval.getFecha() %>" >       
        <img class="button" alt="calendario" id="fotoFecha" src="../../imagenes/calendario.gif" onClick="javascript:showCalendar(this, document.getElementById('Fecha'), 'dd/mm/yyyy',null,1,-1,-1);"> (DD/MM/AAAA)        
      </td>          
    </tr>
    <tr> 
      <td><strong>Valor</strong></td>
      <td>      
        <input name="Valor" type="text" class="text" id="Valor" value="<%= PlanEval.getValor() %>"  size="3" maxlength="4"> %     
      </td>
    </tr>
    <tr><td>&nbsp;</td></tr>
    <tr>
      <td colspan="2" align="center">
        <input type="submit" value="Nuevo" onclick="return Limpiar();" />&nbsp;&nbsp;
        <input type="submit" value="Guardar" onclick="return Guardar();" />
      </td>
    </tr>
    <tr><td>&nbsp;</td></tr>
	<tr><td style="background-color: #5FB404;" align="center" colspan="4" align="center" >&nbsp;</td></tr>
  </table>
</form>
<form name="frmPauta" method="post" action="pautas?CursoCargaId=<%=cursoCargaId%>">
<table style="margin: 0 auto;  width:50%" >
  <tr><td>&nbsp;</td></tr>
  <tr>
    <td>
      <table  width="50%" align="center">
        <tr>
          <td style="background-color: #D0F5A9;font-weight: bold; font-size:9pt" align="center"><spring:message code="aca.Operacion"/></td>
          <td style="background-color: #D0F5A9;font-weight: bold; font-size:9pt" align="center">Evaluación</td>
          <td style="background-color: #D0F5A9;font-weight: bold; font-size:9pt" align="center"><spring:message code="aca.Fecha"/></td>
          <td style="background-color: #D0F5A9;font-weight: bold; font-size:9pt" align="center">Valor %</td>
        </tr>
        <tr><td>&nbsp;</td></tr>  
<%   
    for ( int i=0; i < listEvaluaciones.size(); i++ ){
      	aca.carga.CargaPlanEval evaluacion = (aca.carga.CargaPlanEval) listEvaluaciones.get(i);
      	PlanEval.mapeaRegId(conEnoc, cursoCargaId, evaluacion.getEvaluacionId());
		sumaEvaluaciones = sumaEvaluaciones + Double.parseDouble(evaluacion.getValor());
 %>  
        <tr>
          <td align="left">
            <a href="pautas?Accion=0&Evaluacion=<%= evaluacion.getEvaluacionId() %>">
              <img title="Editar" src="../../imagenes/editar.gif" alt="Modificar" >
            </a>
<% 		if(evaluacion.getActividadId().equals("77")){ %>            
            <a href="pautas?Accion=3&Evaluacion=<%= evaluacion.getEvaluacionId()%>"> 
              <img src="../../imagenes/no.png" alt="Eliminar" >
            </a>
<% 		}%>
          </td>
          <td align"left"><%= evaluacion.getEvaluacionNombre() %></td>
          <td align="center"><%= evaluacion.getFecha() %></td>
          <td align="center"><b><%= formato.format(Double.parseDouble(evaluacion.getValor())) %>%</b></td>
        </tr>
<% }%>
        <tr><td>&nbsp;</td></tr>
        <tr>
          <td style="background-color: #D0F5A9;font-weight: bold; font-size:9pt" align="right" colspan="3">Calificación Final...</td>
          <td style="background-color: #D0F5A9;font-weight: bold; font-size:9pt" align="center"><%= formato.format(sumaEvaluaciones) %>%</td>
        </tr>
        <tr><td align="center" colspan="4">&nbsp;</td></tr>      
        <tr>
          <td align="center" colspan="4">            
            <input type="submit" value="Actualizar" onclick="return Actualizar();" />
          </td>
        </tr>
      </table> 
    </td>
  </tr>  
</table>
</form>
</body>
<%@ include file= "../../cierra_enoc.jsp" %>