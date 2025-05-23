<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.plan.MapaNuevoActividad"%>

<jsp:useBean id="UnidadU" scope="page" class="aca.carga.CargaUnidadUtil"/>
<jsp:useBean id="UnidadCompU" scope="page" class="aca.carga.CargaUnidadCompUtil"/>
<jsp:useBean id="TemaU" scope="page" class="aca.carga.CargaUnidadTemaUtil"/>
<jsp:useBean id="ActividadU" scope="page" class="aca.carga.CargaUnidadActividadUtil"/>
<jsp:useBean id="CriterioU" scope="page" class="aca.carga.CargaUnidadCriterioUtil"/>
<jsp:useBean id="InstrumentoU" scope="page" class="aca.carga.CargaUnidadInstrumentoUtil"/>
<jsp:useBean id="mapaNuevoActividad" class="aca.plan.MapaNuevoActividad" scope="page"/>
<jsp:useBean id="mapaNuevoActividadU" class="aca.plan.MapaNuevoActividadUtil" scope="page"/>
<jsp:useBean id="mapaNuevoUnidadU" class="aca.plan.MapaNuevoUnidadUtil" scope="page"/>
<% 
	String cursoCargaId 		= (String) session.getAttribute("CursoCargaId");
	String maestro 			    = (String) session.getAttribute("Maestro");
	String materia 			    = (String) session.getAttribute("Materia");
	
	String cursoId				= aca.carga.CargaGrupoCursoUtil.cursoIdOrigen(conEnoc, cursoCargaId);
	String version 				= aca.plan.MapaNuevoCursoUtil.getMaxVersionCurso(conEnoc, cursoId); 
	String newCursoId			= aca.plan.MapaNuevoCursoUtil.getNuevoCursoId(conEnoc, cursoId, version);	
	int numUnidades 			= aca.carga.CargaUnidadUtil.numUnidades(conEnoc, cursoCargaId);
	int grabo					= 0;
	int error					= 0;
	
	int numNewUnidades			= Integer.parseInt(aca.plan.MapaNuevoUnidadUtil.numUnidades(conEnoc, newCursoId));	
	if (numUnidades < numNewUnidades){
		//System.out.println("Num Unidades2:"+numUnidades+":"+numNewUnidades);
		int row 					= 0;
		while (grabo <= numNewUnidades && row < 100){
			String strUnidad = "";
			/*Mapear la unidad en los planes nuevos*/
			aca.plan.MapaNuevoUnidad newUnidad  = new aca.plan.MapaNuevoUnidad();
			newUnidad.setCursoId(newCursoId);
			newUnidad.setUnidadId(String.valueOf(row));
			if (mapaNuevoUnidadU.existeReg(conEnoc, newCursoId, newUnidad.getUnidadId())){
				
				newUnidad.mapeaRegId(conEnoc, newCursoId, String.valueOf(row));
				/********Grabar las unidades en el plan de curso****/
				aca.carga.CargaUnidad cargaUnidad = new aca.carga.CargaUnidad();
				
				/*****Formar la clave de la unidad de 2 caracteres*****/
				strUnidad = String.valueOf(row).length()==1?"0"+String.valueOf(row):String.valueOf(row);
				
				cargaUnidad.setCursoCargaId(cursoCargaId);
				cargaUnidad.setUnidadId(strUnidad);
				cargaUnidad.setOrden(String.valueOf(row));
				cargaUnidad.setUnidadNombre(newUnidad.getNombre());
				if(UnidadU.existeReg(conEnoc, strUnidad, cursoCargaId )){					
					grabo++;
				}else{					
					if (UnidadU.insertReg(conEnoc, cargaUnidad)){
						grabo++;
					}else{
						error++;
					}
				}
				
			}
			row++;
		}
	}
	
	ArrayList<aca.carga.CargaUnidad> lisUnidad       			= UnidadU.getListUnidad(conEnoc, cursoCargaId, "ORDER BY ORDEN");
	ArrayList<aca.carga.CargaUnidadComp> lisCompetencias  		= UnidadCompU.getListCompetencias(conEnoc, cursoCargaId, "ORDER BY COMPETENCIA_ID");
	ArrayList<aca.carga.CargaUnidadTema> lisTemas         		= TemaU.getListTema(conEnoc, cursoCargaId, "ORDER BY ORDEN");
    ArrayList<aca.carga.CargaUnidadActividad> lisActividades   	= ActividadU.getListActividades(conEnoc, cursoCargaId, "ORDER BY ORDEN");
	ArrayList<aca.carga.CargaUnidadCriterio> lisCriterios     	= CriterioU.getListCriterio(conEnoc, cursoCargaId, "ORDER BY CRITERIO_ID");
	ArrayList<aca.carga.CargaUnidadInstrumento> lisInstrumentos = InstrumentoU.getListInstrumento(conEnoc, cursoCargaId, "ORDER BY INSTRUMENTO_ID");
	
	
%>
<head>
	<style type="text/css">
		td.header2{
			background-color: #EDED97;
			font-weight: bold;
			text-align: center;
		}
	</style>
</head>
<body>
<table style="width:100%">
   <tr>
     <td><a href="plan">&lsaquo;&lsaquo; <spring:message code="aca.Regresar"/></a></td>
   </tr>
   <tr>
     <td colspan="5" align="center" style="font-size:12pt"><strong>Organización y Evaluación del Aprendizaje</strong></td>
   </tr>
   <tr>
     <td colspan="5" align="center" style="font-size:10pt; color:black;"><strong>Maestro: <%= maestro %></strong></td>
   </tr>
   <tr>
     <td colspan="5" align="center" style="font-size:10pt; color:black;"><strong>Materia: <%= materia %></strong></td>
   </tr>
   <tr><td>&nbsp;</td></tr>
   <tr>
    <td align="center"><a href="unidad?Accion=1" style="color:#EC8D12">[Añadir Unidad]</td>
  </tr>
</table>
<% 
   for (int i=0; i<lisUnidad.size(); i++){
   aca.carga.CargaUnidad unidad = (aca.carga.CargaUnidad) lisUnidad.get(i);
%> 
<table style="margin: 0 auto; width:75%; border-bottom: dotted 3px green;">
  <tr><td>&nbsp;</td></tr>
  <tr>
    <th colspan="5">
      <a href="unidad?Accion=0&Unidad=<%=unidad.getUnidadId()%>">
        <img title="Editar" src="../../imagenes/editar.gif" alt="Modificar" ></a>
      <% if(!aca.carga.CargaUnidadUtil.tieneCompetencias(conEnoc, cursoCargaId, unidad.getUnidadId())&& (!aca.carga.CargaUnidadUtil.tieneTemas(conEnoc, cursoCargaId, unidad.getUnidadId()))){ %> 
      <a href="unidad?Accion=3&Unidad=<%=unidad.getUnidadId()%>"> 
        <img title="Eliminar" src="../../imagenes/no.png" alt="Eliminar" ></a>
      <% }%>
      &nbsp;<b><%= unidad.getUnidadNombre().toUpperCase()%></b></th>
  </tr>
<%
		if (lisUnidad.size() > 0){
			ArrayList<MapaNuevoActividad> listActividades = mapaNuevoActividadU.getListUnidad(conEnoc, newCursoId, unidad.getUnidadId(), "ORDER BY TIPO, ACTIVIDAD_ID");
%>
  <tr>
	<td colspan="5" align="center">
		<table style="width:75: margin:0 auto;">
			<tr>
				<th class="th2" colspan="2">Competencias</td>
			</tr>
<%
			String tipo = "";
			for(int j = 0; j < listActividades.size(); j++){
				mapaNuevoActividad = (MapaNuevoActividad) listActividades.get(j);
				
				if(!tipo.equals(mapaNuevoActividad.getTipo().trim())){
					tipo = mapaNuevoActividad.getTipo().trim();
%>
			<tr>
				<td colspan="2"><b><%=tipo.equals("1")?"Conocimientos:":tipo.equals("2")?"Habilidades:":"Actitudes:" %></b></td>
			</tr>
<%
				}
				String frase = mapaNuevoActividad.getObjetivo().replaceAll("\n"," ").replaceAll(" ","&nbsp;");
				frase = frase.replaceAll("2.","\n 2.").replaceAll("3.","\n 3.").replaceAll("4.","\n 4.");
%>
			<tr>
				<td width="6px" valign="top">-</td>
				<td><%=frase%></td>
			</tr>
<%
			}
%>
		</table>
	</td>  
  </tr>  
  <tr>
    <td><a href="tema?Accion=1&Unidad=<%= unidad.getUnidadId()%>" style="color:#EC8D12">[Añadir Tema]</td>
  </tr>
  <tr>
	 <td width="10%" class="th2" align="center"><strong><spring:message code="aca.Fecha"/></strong></td>
	 <td width="20%" class="th2" align="center"><strong>Tema</strong></td>
	 <td width="25%" class="th2" align="center"><strong>Experiencias de Aprendizaje</strong></td>
	 <td width="25%" class="th2" align="center"><strong></strong></td>
	 <td width="5%" class="th2" align="center"><strong>Valor</strong></td>
  </tr>
<%      for (int z=0; z<lisTemas.size();z++){
         aca.carga.CargaUnidadTema tema = (aca.carga.CargaUnidadTema) lisTemas.get(z);
           if (tema.getTemaId().substring(0,2).equals(unidad.getUnidadId())){
        		   
        		  
%> 
   <tr><td>&nbsp;</td></tr>  
  <tr>
    <td align="left">
       <a href="tema?Accion=0&Tema=<%=tema.getTemaId()%>"> 
         <img title="Editar" src="../../imagenes/editar.gif" alt="Modificar" ></a>
       <% if(!aca.carga.CargaUnidadTemaUtil.tieneActividades(conEnoc,cursoCargaId, tema.getTemaId())){%>
       <a href="tema?Accion=3&Tema=<%=tema.getTemaId()%>"> 
         <img title="Eliminar" src="../../imagenes/no.png" alt="Eliminar" ></a>
       <% }%>
     <strong><%= tema.getFecha() %></strong></td>
    <td align="left"><strong><%=tema.getTemaNombre() %></strong></td>
    <td align="left">&nbsp;</td>
    <td align="left">&nbsp;</td>
    <td align="left">&nbsp;</td>
  </tr>
  <% if(lisTemas.size()>0){ %>
  <tr>
    <td><a href="unidadAct?Unidad=<%= unidad.getUnidadId()%>&Tema=<%=tema.getTemaId()%>" style="color:#EC8D12">[Añadir Actividad]</td>
  </tr>            
<%        
        		
	      for (int k=0 ; k<lisActividades.size(); k++){
	        aca.carga.CargaUnidadActividad actividad = (aca.carga.CargaUnidadActividad) lisActividades.get(k);
	         if(actividad.getActividadId().substring(0,4).equals(tema.getTemaId())){
          
%> 
  <tr>
    <td align="left" style="background-color: #E6E6E6;">&nbsp;</td>
    <td align="left" style="background-color: #E6E6E6;">&nbsp;</td>
    <td align="left" style="background-color: #E6E6E6;" >
      <a href="unidadActEdita?Accion=0&Actividad=<%= actividad.getActividadId()%>">
         <img title="Editar" src="../../imagenes/editar.gif" alt="Modificar" ></a>
      <% if(!aca.carga.CargaUnidadActividadUtil.tieneCriterios(conEnoc, cursoCargaId, actividad.getActividadId())) {%> 
      <a href="unidadAct?Accion=3&Actividad=<%=actividad.getActividadId() %>">
        <img title="Eliminar" src="../../imagenes/no.png" alt="Eliminar" ></a>
      <% } %>  
      <font color="5FB404"><strong><%= actividad.getActividadNombre() %></strong></td>
    <td align="left" style="background-color: #E6E6E6;"><font color="5FB404"><%= actividad.getComentario() %></font></td>
    <td align="left" style="background-color: #E6E6E6;"><font color="5FB404"><%= actividad.getValor()%></font></td>
  </tr>
  <% if (lisActividades.size()>0){%>
  <tr>
    <td align="left">&nbsp;</td>
    <td align="left">&nbsp;</td>
    <td><a href="criterio?Unidad=<%= unidad.getUnidadId()%>&Tema=<%=tema.getTemaId()%>&Actividad=<%=actividad.getActividadId()%>" style="color:#EC8D12">[Añadir Criterio]</td>
  </tr> 
<%              for (int a=0; a<lisCriterios.size(); a++){
                   aca.carga.CargaUnidadCriterio criterio = (aca.carga.CargaUnidadCriterio ) lisCriterios.get(a);
                     if(criterio.getCriterioId().substring(0,6).equals(actividad.getActividadId())){
%>           
  <tr>
    <td align="left">&nbsp;</td>
    <td align="left">&nbsp;</td>
    <td align="left">&nbsp;&nbsp;&nbsp; 
      <% if(!aca.carga.CargaUnidadCriterioUtil.tieneInstrumentos(conEnoc, cursoCargaId, criterio.getCriterioId())){%>
      <a href="criterio?Accion=3&Criterio=<%=criterio.getCriterioId()%>&Actividad=<%=actividad.getActividadId()%>"> 
        <img title="Eliminar" src="../../imagenes/no.png" alt="Eliminar" ></a>
      <% }%>
      <strong><%= criterio.getCriterioNombre()%></strong></td>
  <%if(lisCriterios.size()>0){ %>
    <td><a href="instrumento?Unidad=<%= unidad.getUnidadId()%>&Tema=<%=tema.getTemaId()%>&Actividad=<%=actividad.getActividadId() %>&Criterio=<%=criterio.getCriterioId()%>" style="color:#EC8D12">[Añadir Instrumento]</td>
  </tr>
<%                     for (int x=0; x<lisInstrumentos.size(); x++){
                         aca.carga.CargaUnidadInstrumento instrumento = (aca.carga.CargaUnidadInstrumento) lisInstrumentos.get(x);
                          if(instrumento.getInstrumentoId().substring(0,8).equals(criterio.getCriterioId())){
%>
  <tr>
    <td align="left">&nbsp;</td>
    <td align="left">&nbsp;</td>
    <td align="left">&nbsp;</td>
    <td align="left">
        <a href="instrumento?Accion=3&Instrumento=<%= instrumento.getInstrumentoId()%>&Criterio=<%=criterio.getCriterioId()%>"> 
          <img title="Eliminar" src="../../imagenes/no.png" alt="Eliminar" ></a>
        <%=instrumento.getInstrumentoNombre()%>*</td>
  </tr>                                        
<%                       
     
                       }// if de instrumentos  
                      }//for de instrumentos
                     }// fin de lisCriterios>0
                   }//if de criterios 
                 }// for de criterios
                }// fin de lisActividades>0
              }//if de actividades          
            }// for de actividades           
           }// fin lisTemas>0
         } //if de temas
       }// for de temas 
     }//fin de lisUnidad>0
    }// for de unidades
%>
</table>
</body>
<%@ include file= "../../cierra_enoc.jsp" %>