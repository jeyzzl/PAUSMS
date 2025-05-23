<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.bec.spring.BecPuestoAlumno"%>
<%@page import="aca.bec.spring.BecFija"%>
<%@page import="aca.bec.spring.BecTipo"%>
<%@page import="aca.bec.spring.BecAcuerdo"%>
<%@page import="aca.alumno.spring.AlumAcademico"%>
<%@page import="aca.financiero.spring.ContCcosto"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%
	String usuario					= (String)session.getAttribute("codigoPersonal");
	String periodoId 				= (String) session.getAttribute("periodoBecas");
	String fechaPuesto 				= (String) session.getAttribute("fechaPuesto");
	
	String idEjercicio 				= request.getParameter("idEjercicio");
	String idCcosto 				= request.getParameter("idCcosto");
	String categoriaId 				= request.getParameter("categoriaId");
	String puestoId					= request.getParameter("puestoId");
	
	boolean esAdmin					= (boolean)request.getAttribute("esAdmin");
	String alumnoNombre				= (String)request.getAttribute("alumnoNombre");
	String tipoNombre				= (String)request.getAttribute("tipoNombre");
	String nivel 					= (String)request.getAttribute("nivel");
	String horasBasicas 			= (String)request.getAttribute("horasBasicas");
	String horasAdicionales			= (String)request.getAttribute("horasAdicionales");
	String mensaje					= (String)request.getAttribute("mensaje");
	String folioBasico				= (String)request.getAttribute("folioBasico");
	String folioAdicional			= (String)request.getAttribute("folioAdicional");
	
	BecPuestoAlumno becPuestoAlumno = (BecPuestoAlumno) request.getAttribute("becPuestoAlumno");
	BecAcuerdo becAcuerdoBasico		= (BecAcuerdo)request.getAttribute("becAcuerdoBasico");
	BecAcuerdo becAcuerdoAdicional 	= (BecAcuerdo)request.getAttribute("becAcuerdoAdicional");
		
	HashMap<String,String> mapaDepositos	= (HashMap<String,String>) request.getAttribute("mapaDepositos");
	HashMap<String,BecFija> mapaFija 		= (HashMap<String,BecFija>) request.getAttribute("mapaFija");
	HashMap<String,ContCcosto> mapaDeptos 	= (HashMap<String,ContCcosto>) request.getAttribute("mapaDeptos");
	HashMap<String,String> mapaTipos		= (HashMap<String,String>) request.getAttribute("mapaTipos");
	
	int numero = 1;
	double totalbasica= 0;
	double totaladicional= 0;
	
	
	//FORMATO
	java.text.DecimalFormat getFormato	= new java.text.DecimalFormat("###,##0.00;(###,##0.00)");
	
	if( idEjercicio == null || idCcosto == null || categoriaId == null || puestoId == null){
		response.sendRedirect("puesto");
	}
	
	//BASICA
	String Basica = "B";
	if(becPuestoAlumno.getTipo().equals("I")){//INDUSTRIAL
		Basica = "I";
	}
	
	String BasicaImporteYHoras = Basica;
	if(becPuestoAlumno.getTipo().equals("M")){//MAESTRIA O POSTGRADO
		BasicaImporteYHoras = "M";
	}else if(becPuestoAlumno.getTipo().equals("P")){//PREINDUSTRIAL
		BasicaImporteYHoras = "P";
	}
	
	//TRAER EL IMPORTE BASICO QUE ESTA GRABADO EN BEC_TIPO
	String importeBasico 		= (String)request.getAttribute("importeBasico");
	
	//TRAER EL TIPO DE LA BECA BASICA
	String tipoBasica 			= (String)request.getAttribute("tipoBasica");
	
	//TRAER EL TIPO DE LA BECA ADICIONAL
	String tipoAdicional 		= (String)request.getAttribute("tipoAdicional");
	
	//PRESUPUESTO BECA BASICA
	BecTipo becTipoBasico 		= (BecTipo)request.getAttribute("becTipoBasico");	
	String presupuestoBasica 	= (String)request.getAttribute("presupuestoBasica");
	
	//PRESUPUESTO BECA ADICIONAL
	BecTipo becTipoAdicional 	= (BecTipo)request.getAttribute("becTipoAdicional");
	String presupuestoAdicional = (String)request.getAttribute("presupuestoAdicional");
	
	//PRESUPUESTO USADO BECA BASICA	
	String usadoBasica 			= (String)request.getAttribute("usadoBasica");
	//PRESUPUESTO USADO BECA ADICIONAL
	String usadoAdicional 		= (String)request.getAttribute("usadoAdicional");
		
	double basicaDisponible 	= Double.parseDouble(presupuestoBasica)-Double.parseDouble(usadoBasica);
	double adicionalDisponible 	= Double.parseDouble(presupuestoAdicional)-Double.parseDouble(usadoAdicional);
	
	//ACUERDOS DEL ALUMNO
	List<BecAcuerdo> lisAcuerdos 			= (List<BecAcuerdo>) request.getAttribute("lisAcuerdos");
	//EL ALUMNO YA TIENE ASIGNADA UNA BECA BASICA AQUI?
	boolean existeBecaBasicaDelAlumno 		= (boolean)request.getAttribute("existeBecaBasicaDelAlumno");	
	//HAY UNA BECA BASICA DADA DE ALTA EN EL EJERCICIO ACTUAL
	boolean becaBasicaDisponible 			= (boolean)request.getAttribute("becaBasicaDisponible");	
	//EL ALUMNO YA TIENE ASIGNADA UNA BECA ADICIONAL AQUI?
	boolean existeBecaAdicionalDelAlumno 	= (boolean)request.getAttribute("existeBecaAdicionalDelAlumno");	
	//HAY UNA BECA BASICA DADA DE ALTA EN EL EJERCICIO ACTUAL
	boolean becaAdicionalDisponible 		= (boolean)request.getAttribute("becaAdicionalDisponible");	
	
	aca.util.Fecha fecha = new aca.util.Fecha();
	String fecha20dias = fecha.getDiaSiguiente(aca.util.Fecha.getHoy(), 20);	
%>
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
	<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
	<style>
		body{
			background:white;
		}
	</style>
	
	<form action="addBecas" method="post" name="forma">
	<input type="hidden" name="Accion" id="Accion" />
	<input type="hidden" name="Folio" id="Folio" />
	
	<input type="hidden" name="puestoId" value="<%=puestoId%>" />
    <input type="hidden" name="idEjercicio" value="<%=idEjercicio%>" />
    <input type="hidden" name="idCcosto" value="<%=idCcosto%>" />
    <input type="hidden" name="categoriaId" value="<%=categoriaId%>" />
	
<div class="container-fluid">
	
	<h2 style="margin-bottom:10px;">
		<a href="puestoAlumno?idEjercicio=<%=idEjercicio%>&idCcosto=<%=idCcosto%>&categoriaId=<%=categoriaId%>" class="btn btn-primary rounded-pill"><i class="fas fa-arrow-left"></i></a>&nbsp;
		Añadir Becas 
		<small class="text-muted fs-6">
		( <%=alumnoNombre %>&nbsp;-&nbsp;
		<%=idEjercicio %> | Centro de Costo: <%= mapaDeptos.get(idCcosto)!=null?mapaDeptos.get(idCcosto).getNombre():idCcosto%>&nbsp;-&nbsp;
		Tipo: <%=tipoNombre%>
		)
		</small>
	</h2>
<%	if (!mensaje.equals("-")){%>
	<%=mensaje%>
<%	} %>	
	<div class="alert alert-info">
		<strong>Presupuesto Beca Básica:</strong>
		<%=getFormato.format(Double.parseDouble(presupuestoBasica)) %>
		&nbsp;&nbsp;
		<strong>Usado:</strong>
		<%=getFormato.format(Double.parseDouble(usadoBasica)) %>
		&nbsp;&nbsp;
		<strong>Disponible:</strong>
		<%= getFormato.format(basicaDisponible) %>		
		<br />		
		<strong>Presupuesto Beca Adicional:</strong>
		<%=getFormato.format(Double.parseDouble(presupuestoAdicional)) %>
		&nbsp;&nbsp;
		<strong>Usado:</strong>
		<%=getFormato.format(Double.parseDouble(usadoAdicional)) %>
		&nbsp;&nbsp;
		<strong>Disponible:</strong>
		<%=getFormato.format(adicionalDisponible) %>
		
	</div>
	<!----------------------------BECA BASICA------------------------->
	<div class="row">
		<div class="col-6">
<%	if(becaBasicaDisponible){ %>
			<h2>Beca <%if(becPuestoAlumno.getTipo().equals("I")){out.print("Institucional"); }%> Básica</h2>
			<p></p>	
<%		if(!existeBecaBasicaDelAlumno){ %>					
							
            <label for="vigenciaBasica">Importe</label>
            <input type="text" class="form-control" value="<%=importeBasico %>" disabled/>
            <br>
            <label for="vigenciaBasica">Horas</label>
            <input type="text" class="form-control" value="<%=horasBasicas %>" disabled/>
            <br>
            <label for="vigenciaBasica">Fecha de Vigencia**</label>
            <input id="vigenciaBasica" name="vigenciaBasica" type="text" class="form-control" value="<%=fecha20dias%>" data-date-format="dd/mm/yyyy"/>
            <br>					
			<a onclick="addBasica()" class="btn btn-success "><i class="icon-folder-open icon-white"></i><spring:message code='aca.Añadir'/></a>
<%
		}else{			
			horasBasicas = becAcuerdoBasico.getHoras();
%>			
            <label for="vigenciaBasica">Importe</label>
            <input type="text" class="form-control" value="<%=becAcuerdoBasico.getEnsenanza() %>" disabled/>
            <br>            
            <label for="vigenciaBasica">Horas</label>		                
            <input class="numHoras form-control" type="text" value="<%=becAcuerdoBasico.getHoras() %>" disabled/>            
        <%
			if(mapaFija.containsKey(idCcosto+idEjercicio) || esAdmin){
        %>
                <!-- EDITAR NUMERO DE HORAS PARA ADMINS -->
                <input type="hidden" class="folio" value="<%=folioBasico%>" />
                <a href="" class="btn btn-sm btn-info editBasica"><i class="fas fa-pencil-alt"></i></a>
                <a style="display:none;" href="" class="btn btn-sm btn-info guardarBasica"><i class="fas fa-save"></i> Guardar</a>
                <a style="display:none;" href="" class="btn btn-sm btn-danger cancelarBasica"><i class="fas fa-trash-alt"></i> Cancelar</a>
                <br>
        <%	} %>          
			<br>        	
            <label for="vigenciaBasica">Fecha de Vigencia</label>
            <input type="text" class="form-control" value="<%=becAcuerdoBasico.getVigencia() %>" disabled/>
            <br>			            
		<%
			boolean show = true;
			//VERIFICAR SI TIENE UN ACUERDO FIJO ADICIONAL DE OTRA PARTE YA ASOCIADO
			for(BecAcuerdo acuerdo: lisAcuerdos){
				
				//SI TIENE ACUERDOS EN ESTE PUESTO, SI SON DE OTRO ENTONCES NO LO PONGAS
				if(!acuerdo.getPuestoId().equals("0") && !acuerdo.getPuestoId().equals(becPuestoAlumno.getPuestoId())){
					continue;
				}
				if(!acuerdo.getPuestoId().equals("0")){
					//Ya esta asociado algo
					show = false;
					break;
				}
			}			
			if(!existeBecaAdicionalDelAlumno && show){
		%>
				<a onclick="deleteBasica('<%=folioBasico%>')" class="btn btn-danger "><i class="fas fa-trash-alt icon-white"></i> Eliminar</a>
		<%
			} 
		%>
<%		} %>
<%
	}else{
%>
		<div class="alert alert-danger">
			No hay Beca Básica Disponible
		</div>
<%
	}
%>
	</div>
	<div class="col-6">

<!--  --------------------------BECA ADICIONAL----------------------- -->	
<%		
		// Lista de tipos de alumnos permitidos en la beca adicional		
		String tiposAlumnoAdicional = (String)request.getAttribute("tiposAlumnoAdicional");

		String tipo = becPuestoAlumno.getTipo();
		boolean mostrar = true;
		if(tipo.equals("I") || tipo.equals("P") || tipo.equals("M") || tipo.equals("T")){
			mostrar = false;	
		}else{
			
		}
		
		// Tipo de alumno
		String tipoAlumno 		= (String)request.getAttribute("tipoAlumno");
		
		//System.out.println("Existe Basica:"+existeBecaBasicaDelAlumno+" Mostrar:"+mostrar+" Adicional: "+becaAdicionalDisponible+"::"+existeBecaAdicionalDelAlumno+":::"+tiposAlumnoAdicional+"|| -"+tipoAlumno);   
		if( existeBecaBasicaDelAlumno && mostrar && tiposAlumnoAdicional.contains("-"+tipoAlumno)){
			
			if(becaAdicionalDisponible){				
		%>
			<h2>Beca Adicional</h2>
			<p></p>
		
				<%
					if(!existeBecaAdicionalDelAlumno){						
				%>						
							<div class="control-group ">
				                <label for="tipoadicional">
				                    Criterio
				                </label>
				                <br>
				                <%
				                String valor = "";
				                if(mapaDepositos.containsKey(becPuestoAlumno.getCodigoPersonal())){
				                	valor = mapaDepositos.get(becPuestoAlumno.getCodigoPersonal());
				                }
				                %>
				                <select  id="tipoadicionnal" name="tipoadicional"  style="width: 280px;" onchange="recargar('<%=valor%>');">				                										
									<option value="D" <%if(becAcuerdoAdicional.getTipoadicional().equals("D"))out.print("selected"); %>>Cantidad Fija</option>
								</select>
				            </div>
				            <br>
				        	<div class="control-group abc" style="display:none;">
				                <label for="porcentaje">
				                    Porcentaje
				                </label>
				                <input id="porcentaje" name="porcentaje" type="text" maxlength="2"/>
				            </div>
				            <div class="control-group abc" style="display:none;">
				                <label class="deposito" for="promesa" >
				                   Depósito de colportaje
				                </label>
				                <input class="depositoValor" id="promesa" name="promesa" type="number" value="<%=valor%>" maxlength="9"/>
				            </div>
				            <div class="control-group cantidadfija">
				                <label for="porcentaje">
				                    Importe
				                </label>			                
				                <br>
				                <input id="cantidadfija" name="cantidadfija" type="number" value="0" maxlength="9"/>
				            </div>
				            <br>
				            <div class="control-group ">
				                <label for="vigenciaBasica">
				                    Horas
				                </label>
				                <br>				                
				                <input type="text" value="<%=horasAdicionales %>" disabled="true"/>
				            </div>	
				            <br>			            
				            <div class="control-group ">
				                <label for="vigenciaBasica">
				                    Fecha de Vigencia
				                </label>				                
				                <br>
				                <input data-date-format="dd/mm/yyyy" id="vigenciaAdicional" name="vigenciaAdicional" type="text" value="<%=fecha20dias%>"/>
				            </div>
				            <br>						
						<p>
							<a onclick="addAdicional();" class="btn btn-success"><i class="icon-folder-open icon-white"></i><spring:message code='aca.Añadir'/></a>
						</p>
				<%
					}else{						
						String acuerdo="";
            			if(becAcuerdoAdicional.getTipoadicional().equals("A")){
            				acuerdo = "Solo Depósitos de Colportaje";
            			}else if(becAcuerdoAdicional.getTipoadicional().equals("B")){
            				acuerdo = "Depósitos de Colportaje y Familia";
            			}else if(becAcuerdoAdicional.getTipoadicional().equals("C")){
            				acuerdo = "Solo Depósitos de Familia";
            			}else if(becAcuerdoAdicional.getTipoadicional().equals("D")){
            				acuerdo = "Cantidad Fija";
            			} 
            	%>						
						<fieldset>
				        	<div class="control-group ">				                
				                <input type="hidden" style="width: 280px;" value="<%=acuerdo%>"/>
				            </div>
				            
				            <% if(becAcuerdoAdicional.getTipoadicional().equals("D")){%>
				        	<div class="control-group">
				                <label for="Cantidad">
				                    Cantidad Fija
				                </label>
				                <br>
				                <br>
				                <input id="cantidadfija" name="cantidadfija" type="number" value="<%=becAcuerdoAdicional.getEnsenanza() %>" maxlength="9"/>
				            </div>			
							<%}else if(becAcuerdoAdicional.getTipoadicional().equals("A")){%>
				        	<div class="control-group ">
				                <label for="porcentaje">
				                    Porcentaje
				                </label>
				                <input type="text" value="<%=becAcuerdoAdicional.getEnsenanza()+"%" %>" maxlength="3" disabled="true"/>
				            </div>
				            
				            <div class="control-group ">
				                <label for="promesa">
				                    Depósito de colportaje
				                </label>
				                <input type="text" value="<%=becAcuerdoAdicional.getPromesa() %>" maxlength="9" disabled="true"/>
				            </div>
				            <%} else { %>
				            <div class="control-group ">
				                <label for="porcentaje">
				                    Porcentaje
				                </label>
				                <input type="text" value="<%=becAcuerdoAdicional.getEnsenanza()+"%" %>" maxlength="3" disabled="true"/>
				            </div>
				            
				            <div class="control-group ">
				                <label for="promesa">
				                    Promesa
				                </label>
				                <input type="text" value="<%=becAcuerdoAdicional.getPromesa() %>" maxlength="9" disabled="true"/>
				            </div>
				            <%} %>
				            <div class="control-group ">
				                <label for="vigenciaBasica">
				                    Horas
				                </label>
				                <br>
				                <input class="numHoras" type="text" value="<%=becAcuerdoAdicional.getHoras() %>" disabled="true"/>
				                <%
				                if(mapaFija.containsKey(idCcosto+idEjercicio) || esAdmin){
				                %>
				                <!-- EDITAR NUMERO DE HORAS PARA ADMINS -->
				                <input type="hidden" class="folio" value="<%=folioAdicional%>" />
				                <a href="" class="btn btn-sm btn-info editAdicional"><i class="fas fa-pencil-alt"></i></a>
				                <a style="display:none;" href="" class="btn btn-sm btn-info guardarAdicional"><i class="icon-ok icon-white"></i> Guardar</a>
				                <a style="display:none;" href="" class="btn btn-sm cancelarAdicional"><i class="fas fa-trash-alt"></i> Cancelar</a>
				                <%} %>
				            </div>
				            
				            <div class="control-group ">
				                <label for="vigenciaBasica">
				                    Fecha de Vigencia
				                </label>
				                <br>
				                <br>
				                <input type="text" value="<%=becAcuerdoAdicional.getVigencia() %>" disabled/>
				            </div>
			            </fieldset>
				
						<a onclick="deleteAdicional('<%=folioAdicional%>')" class="btn btn-danger"><i class="fas fa-trash-alt icon-white"></i> Eliminar</a>
				<%
				    }
				%>
		<%
			}else{
		%>
				<div class="alert alert-danger">
					No hay Beca Adicional Disponible
				</div>
		<%
			}			
		}else if( mostrar==true && !tipoAlumno.equals("2") && !tipoAlumno.equals("3") && !tipoAlumno.equals("5") && !tipoAlumno.equals("9") ){
		%>
				<div class="alert alert-info">
					Necesita Tener una Beca Básica para agregar una Adicional
				</div>
		<%
		
		
		}else if(!(becAcuerdoAdicional.getPuestoId() != null && !becAcuerdoAdicional.getPuestoId().equals(becPuestoAlumno.getPuestoId()))){
		%>
		
		<%
		}%> 
			
</div>
<div class="col">
<%
		for(BecAcuerdo acuerdo: lisAcuerdos){
			
			//SI TIENE ACUERDOS EN ESTE PUESTO, SI SON DE OTRO ENTONCES NO LO PONGAS
			if(!acuerdo.getPuestoId().equals("0") && !acuerdo.getPuestoId().equals(becPuestoAlumno.getPuestoId())){
				continue;
			}	
			String tipoBeca = "-";
			if (mapaTipos.containsKey(acuerdo.getTipo())){
				tipoBeca = mapaTipos.get(acuerdo.getTipo());
			}
%>			
			<h2>Acuerdo  <small class="text-muted fs-4"><%=tipoBeca%></small></h2>
			<p></p>
			
				
				<div class="row">
						<div class="col">
							<fieldset>
								<div class="control-group ">
					                <label for="vigenciaBasica">
					                    Fecha
					                </label>
					                <%=acuerdo.getFecha() %>
					            </div>
					            <div class="control-group ">
					                <label for="vigenciaBasica">
					                    Promesa
					                </label>
					                <%=acuerdo.getPromesa() %>
					            </div>
					            <div class="control-group ">
					                <label for="vigenciaBasica">
					                    Matricula
					                </label>
					                <%=acuerdo.getMatricula() %>
					            </div>
					            <div class="control-group ">
					                <label for="vigenciaBasica">
					                    Enseñanza
					                </label>
					                <%=acuerdo.getEnsenanza() %>
					            </div>
					            <div class="control-group ">
					                <label for="vigenciaBasica">
					                    Internado
					                </label>
					                <%=acuerdo.getInternado() %>
					            </div>
					          </fieldset> 
						</div>
						<div class="col">
							<fieldset>
								<div class="control-group ">
					                <label for="vigenciaBasica">
					                    Valor
					                </label>
					                <%=acuerdo.getValor().equals("C")?"Cantidad":"Porcentaje" %>
					            </div>
					            <div class="control-group ">
					                <label for="vigenciaBasica">
					                    Horas
					                </label>
					                <%=acuerdo.getHoras() %>
					            </div>
					            <div class="control-group ">
					                <label for="vigenciaBasica">
					                    Vigencia
					                </label>
					                <%=acuerdo.getVigencia() %>
					            </div>
					            <div class="control-group ">
					                <label for="vigenciaBasica">
					                    Estado
					                </label>
					                <%=acuerdo.getEstado() %>
					            </div>
					       </fieldset>
						</div>
				</div>
			
			<%
			
			if(existeBecaBasicaDelAlumno){//SI TIENE UNA BECA BASCIA GRABADA
				
				//SI AUN NO TIENE PUESTO 
				if(acuerdo.getPuestoId()==null || acuerdo.getPuestoId().equals("0")){
			%>
	           		<a class="btn btn-primary" onclick="asociar('<%=acuerdo.getFolio()%>');"><i class="icon-random icon-white"></i> Asociar con este puesto</a>
	        <%
	        	//SI YA TIENE PUESTO
	        	}else{
	        %> 
	        		<a href="javascript:desasociar('<%=acuerdo.getFolio()%>');" class="btn btn-danger"><i class="fas fa-trash-alt icon-white"></i> Desasociar con este puesto</a>
	        <%
	            }
			}
	        %>
	            
<%	
		}
%>
</div>
</div>
<br>

<div class="alert alert-succes"><%if(existeBecaBasicaDelAlumno){ %>
		BECA BÁSICA: $<strong><%=(getFormato.format(totalbasica=((Double.parseDouble(importeBasico))*(Integer.parseInt(horasBasicas))))) %></strong>
						    <% if((existeBecaAdicionalDelAlumno)&&(!becAcuerdoAdicional.getTipoadicional().equals("D"))){%>   
						    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;BECA ADICIONAL: $
						    <strong><%=(getFormato.format(totaladicional=(((Double.parseDouble(becAcuerdoAdicional.getPromesa()))*(Double.parseDouble(becAcuerdoAdicional.getEnsenanza())))/100))) %></strong>
						    <%} else if((existeBecaAdicionalDelAlumno)&&(becAcuerdoAdicional.getTipoadicional().equals("D"))){%>
						    &nbsp;&nbsp;&nbsp; BECA ADICIONAL: $<strong><%=totaladicional=(Double.parseDouble(becAcuerdoAdicional.getEnsenanza())) %></strong>
						   <%}%>
	 &nbsp;&nbsp;&nbsp;&nbsp; 	<%} %><strong>TOTAL: $<%=(getFormato.format(totalbasica+totaladicional))%></strong>
</div>

</form>

	<script>
	
		jQuery('#vigenciaBasica').datepicker();
		jQuery('#vigenciaAdicional').datepicker();
	
		function addBasica(){
			
			if(document.forma.vigenciaBasica.value != ""){
				document.forma.Accion.value = "1";
				document.forma.submit();
			}else{
				alert("Favor de especificar una fecha de vigencia");
			}
		}
		
		function deleteBasica(folio){
			document.forma.Accion.value = "2";
			document.forma.Folio.value = folio;
			document.forma.submit(); 	
		}
		
		function addAdicional(){			
			var tmp = jQuery("#tipoadicionnal").val();
			if(tmp=="D"){
				if( document.forma.vigenciaAdicional.value != "" && document.forma.tipoadicional.value !="" && document.forma.cantidadfija.value !=""){
					document.forma.Accion.value = "3";
					document.forma.submit();
				}else{
					alert("Todos los campos son requeridos");
				}
			}else{
				if( document.forma.porcentaje.value != "" && document.forma.promesa.value != "" && document.forma.vigenciaAdicional.value != "" && document.forma.tipoadicional.vale !=""){
					document.forma.Accion.value = "3";
					document.forma.submit();
				}else{
					alert("Todos los campos son requeridos");
				}
			}
		}
		
		function deleteAdicional(folio){
			document.forma.Accion.value = "4";
			document.forma.Folio.value = folio;
			document.forma.submit();
		}
		
		function asociar(folio){
				document.forma.Folio.value = folio;
				document.forma.Accion.value = "5";
				document.forma.submit();
		}
		
		function desasociar(folio){
			document.forma.Folio.value = folio;
			document.forma.Accion.value = "6";
			document.forma.submit();
		}

		function recargar(valor){			
			jQuery("#porcentaje").val("");
			var tmp = jQuery("#tipoadicionnal").val();
			if(tmp=="D"){
				jQuery(".cantidadfija").slideDown(300);
				jQuery(".abc").hide();
			}else{
				jQuery(".cantidadfija").hide();
				jQuery(".abc").slideDown(300);
			}
			
			if(tmp!="A"){
				jQuery(".deposito").html("Promesa");
				jQuery(".depositoValor").val("0");
			}else{
				jQuery(".deposito").html("Depósito de colportaje");
				jQuery(".depositoValor").val(valor);
			}
		}
		
		jQuery("#porcentaje").keyup(function(){ 
			var tmp = jQuery("#tipoadicionnal").val();
			$this = jQuery(this); 
			console.log($this.val());
			var maximo = parseInt($this.val());
			console.log(typeof maximo);
			if((tmp=="A")&&(maximo>33)){
					$this.val("33");
					alert("El porcentaje no puede rebasar el 33%");
			}else if(maximo>25&&tmp=="B"){
				$this.val("25");
				alert("El porcentaje no puede rebasar el 25%");
			}else if(maximo>25&&tmp=="C"){
				$this.val("25");
				alert("El porcentaje no puede rebasar el 25%");
			}
		});
		
		
		//-------------------------------- EDITAR HORAS BASICAS (SOLO PARA ADMINS) -------->
		
		var editBasica 		= jQuery('.editBasica'); 
		var guardarBasica 	= jQuery('.guardarBasica');
		var cancelarBasica 	= jQuery('.cancelarBasica');
		var inputBecaBasica = editBasica.siblings('input.numHoras');
		var inputFolioBasica= editBasica.siblings('input.folio');
		
		editBasica.on('click', function(e){
			e.preventDefault();
			
			$this = jQuery(this);
			inputBecaBasica.attr('disabled', false);
			$this.hide();
			guardarBasica.show();
			cancelarBasica.show();
		});
		
		guardarBasica.on('click', function(e){
			e.preventDefault();
			
			if(inputBecaBasica.val() != ""){
				document.location.href = "addBecas?Accion=7&Folio="+inputFolioBasica.val()+"&numHoras="+inputBecaBasica.val()+"&puestoId=<%=puestoId%>&idEjercicio=<%=idEjercicio%>&idCcosto=<%=idCcosto%>&categoriaId=<%=categoriaId%>";	
			}else{
				alert("Necesita Especificar un Número de Horas");
			}
			
		});
		
		cancelarBasica.on('click', function(e){
			e.preventDefault();
			location.href = "addBecas?puestoId=<%=puestoId%>&idEjercicio=<%=idEjercicio%>&idCcosto=<%=idCcosto%>&categoriaId=<%=categoriaId%>";
		})
		
		//-------------------------------- EDITAR HORAS ADICIONALES (SOLO PARA ADMINS) -------->
		
		var editAdicional 		= jQuery('.editAdicional'); 
		var guardarAdicional 	= jQuery('.guardarAdicional');
		var cancelarAdicional 	= jQuery('.cancelarAdicional');
		var inputBecaAdicional 	= editAdicional.siblings('input.numHoras');
		var inputFolioAdicional = editAdicional.siblings('input.folio');
		
		editAdicional.on('click', function(e){
			e.preventDefault();
			
			$this = jQuery(this);
			inputBecaAdicional.attr('disabled', false);
			$this.hide();
			guardarAdicional.show();
			cancelarAdicional.show();
		});
		
		guardarAdicional.on('click', function(e){
			e.preventDefault();
			
			if(inputBecaAdicional.val() != ""){
				document.location.href = "addBecas?Accion=7&Folio="+inputFolioAdicional.val()+"&numHoras="+inputBecaAdicional.val()+"&puestoId=<%=puestoId%>&idEjercicio=<%=idEjercicio%>&idCcosto=<%=idCcosto%>&categoriaId=<%=categoriaId%>";	
			}else{
				alert("Necesita Especificar un Número de Horas");
			}
			
		});
		
		cancelarAdicional.on('click', function(e){
			e.preventDefault();
			document.location.href = "addBecas?puestoId=<%=puestoId%>&idEjercicio=<%=idEjercicio%>&idCcosto=<%=idCcosto%>&categoriaId=<%=categoriaId%>";
		})
	
	</script>