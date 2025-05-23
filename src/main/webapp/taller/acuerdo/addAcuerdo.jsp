<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="becAcuerdo"  class="aca.bec.BecAcuerdo" scope="page"/>
<jsp:useBean id="alumPersonal"  class="aca.alumno.AlumPersonal" scope="page"/>
<jsp:useBean id="AlumUtil" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="becTipo"  class="aca.bec.BecTipo" scope="page"/>
<jsp:useBean id="becTipoU"  class="aca.bec.BecTipoUtil" scope="page"/>
<jsp:useBean id="becAcuerdoU"  class="aca.bec.BecAcuerdoUtil" scope="page"/>
<jsp:useBean id="becPuestoAlumno"  class="aca.bec.BecPuestoAlumno" scope="page"/>
<jsp:useBean id="becTipo1"  class="aca.bec.BecTipo" scope="page"/>
<jsp:useBean id="becTipo2"  class="aca.bec.BecTipo" scope="page"/>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<%	
	String codigo		= (String)session.getAttribute("codigoPersonal");
	boolean admin       = aca.acceso.AccesoUtil.getBecas(conEnoc, codigo);

	String idEjercicio 	= request.getParameter("idEjercicio");
	String tipo 		= request.getParameter("tipo");
	String tipoAlumno 	= "";
	String msj 			= "";
	//System.out.println("UNO : "+request.getParameter("codigoPersonal"+" add Acuerdo"));
	//System.out.println("DOS : "+request.getParameter("usuario"+" add Acuerdo"));	
	if(idEjercicio==null || tipo==null){
		out.print("<div class='alert alert-success'><a class='btn btn-primary' href='acuerdo'>Regresar</a></div>");			
	}	
	
	//CENTRO DE COSTOS
	java.util.HashMap <String, aca.financiero.ContCcosto> ccostos = aca.financiero.ContCcostoUtil.getMapCentrosCosto(conEnoc, idEjercicio);
	
	String codigoPersonal 	= request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal");
	String folio 			= request.getParameter("folio")==null?"0":request.getParameter("folio");	
	String accion 			= request.getParameter("Accion")==null?"":request.getParameter("Accion");
	
	//TRAER EL TIPO DE LA BECA BASICA
	String tipoBasica 		= becTipoU.getTipo(conEnoc, idEjercicio, "B").equals("none")?"0":becTipoU.getTipo(conEnoc, idEjercicio, "B");
	
	//TRAER EL TIPO DE LA BECA BASICA INDUSTRIAL
	String tipoBasicaInd 	= becTipoU.getTipo(conEnoc, idEjercicio, "I").equals("none")?"0":becTipoU.getTipo(conEnoc, idEjercicio, "I");
	
	//TRAER EL TIPO DE LA BECA ADICIONAL
	String tipoAdicional 	= becTipoU.getTipo(conEnoc, idEjercicio, "A").equals("none")?"0":becTipoU.getTipo(conEnoc, idEjercicio, "A");

	String maximo			= becTipoU.getMaximo(conEnoc, idEjercicio, tipo); 
	
	//ACUERDOS DEL ALUMNO	
	if (!folio.equals("0")){
		becAcuerdo = becAcuerdoU.mapeaRegId(conEnoc, folio, codigoPersonal);
	}	
	
	if(accion.equals("1")){//GRABAR		
		
		becAcuerdo.setCodigoPersonal(codigoPersonal);
	
		if(AlumUtil.existeReg(conEnoc, becAcuerdo.getCodigoPersonal())){
			
			if(folio == null || folio.equals("null") || folio.equals("0")){
				folio = becAcuerdoU.maximoReg(conEnoc, becAcuerdo.getCodigoPersonal());				
			}
			
			becAcuerdo.setFolio(folio);			
			
			String horas = "";
			//NIVEL DEL ALUMNO
			String nivel = aca.catalogo.CatCarreraUtil.getNivelId(conEnoc, aca.alumno.PlanUtil.getCarreraId(conEnoc,becAcuerdo.getCodigoPersonal()));
			becTipo.setIdEjercicio(idEjercicio);
			becTipo.setTipo(tipo);
			becTipo.mapeaRegId(conEnoc);
			//PREPA
			if(nivel.equals("1")){
				horas = becTipo.getHorasPrepa();
			}else{
				horas = becTipo.getHoras();
			}
			
			ArrayList<aca.bec.BecAcuerdo> acuerdos = becAcuerdoU.getAcuerdosVigentesAlumno(conEnoc, idEjercicio, becAcuerdo.getCodigoPersonal(), tipoBasica+","+tipoAdicional+","+tipoBasicaInd, " AND FOLIO NOT IN("+becAcuerdo.getFolio()+") ");
			
			// Consulta el tipo de alumno
			tipoAlumno = aca.alumno.AcademicoUtil.getTipoAlumnoId(conEnoc, becAcuerdo.getCodigoPersonal());
			
			if(acuerdos.size()!=0){
				msj = "<div class='alert alert-danger'>Este Alumno Ya tiene un Acuerdo</div>";
			}else if( becTipo.getTipoAlumno()==null || becTipo.getTipoAlumno().equals("null") ){
				msj = "<div class='alert alert-danger'>A este tipo de beca no se le ha dado de alta los tipos de alumnos</div>";
			}else if( !becTipo.getTipoAlumno().contains("-"+tipoAlumno)){
				msj = "<div class='alert alert-danger'>Este alumno de tipo <strong>"+aca.alumno.AcademicoUtil.getTipoAlumno(conEnoc, becAcuerdo.getCodigoPersonal()) +" ("+aca.alumno.AcademicoUtil.getTipoAlumnoId(conEnoc, becAcuerdo.getCodigoPersonal())+")</strong> no puede tener este tipo de acuerdo (tipos permitidos: "+becTipo.getTipoAlumno().replaceAll("-"," ").trim().replaceAll(" ",", ")+") </div>";
			}else{
				
				becAcuerdo.setHoras(horas);
				becAcuerdo.setIdEjercicio(idEjercicio);
				becAcuerdo.setTipo(tipo);
				becAcuerdo.setFecha(aca.util.Fecha.getHoy());
				becAcuerdo.setPromesa(request.getParameter("promesa"));
				becAcuerdo.setMatricula(request.getParameter("matricula"));
				becAcuerdo.setEnsenanza(request.getParameter("ensenanza"));
				becAcuerdo.setInternado(request.getParameter("internado"));
				becAcuerdo.setValor(request.getParameter("valor"));
				becAcuerdo.setVigencia(request.getParameter("vigencia"));
				becAcuerdo.setEstado(request.getParameter("estado"));
				becAcuerdo.setTipoadicional("X");
				becAcuerdo.setIdCcosto(request.getParameter("ccosto"));
				becAcuerdo.setUsuario(codigo);
				
				if(becAcuerdoU.existeReg(conEnoc, codigoPersonal, folio)){
					
					if(becAcuerdoU.updateReg(conEnoc, becAcuerdo)){						
						msj = "<div class='alert alert-success'>Se Modificó Correctamente</div>";
					}else{
						msj = "<div class='alert alert-danger'>Ocurrió un Error al Modificar</div>";
					}
				}else{
					
					if(becAcuerdoU.insertReg(conEnoc, becAcuerdo)){						
						msj = "<div class='alert alert-success'>Se Guardó Correctamente</div>";
					}else{
						msj = "<div class='alert alert-danger'>Ocurrió un Error al Guardar</div>";
					}					
					becAcuerdo = new aca.bec.BecAcuerdo();
				}
			}
		}else{
			msj = "<div class='alert alert-danger'>La Matricula "+becAcuerdo.getCodigoPersonal()+" No Existe</div>";
		}
	}
	
	if(codigoPersonal!=null && !codigoPersonal.equals("null")){
		becAcuerdo.setCodigoPersonal(codigoPersonal);
		becAcuerdo.setFolio(folio);
		becAcuerdo = becAcuerdoU.mapeaRegId(conEnoc, folio, codigoPersonal);
	}	
%>

<style>
	body{
		background:white;
	}
</style>


<div class="container-fluid">
	
<%
	becTipo.setIdEjercicio(idEjercicio);
	becTipo.setTipo(tipo);
	becTipo.mapeaRegId(conEnoc);
%>
	<h2 style="margin-bottom:10px;">Añadir Acuerdo<small class="text-muted fs-5">( <%=becTipo.getNombre() %> porcentaje de beca entre 0% y <%=maximo%>% )</small></h2>
	
	<%=msj %>
	
	<div class="alert alert-info">
		<a href="acuerdo?idEjercicio=<%=idEjercicio %>&tipo=<%=tipo %>" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>

	<form  method="post" action="addAcuerdo?Accion=1" name="forma" id="forma">		
		<input type="hidden" id="folio" name="folio" value="<%=folio %>" />
		<input type="hidden" id="idEjercicio" name="idEjercicio" value="<%=idEjercicio %>" />
		<input type="hidden" id="tipo" name="tipo" value="<%=tipo %>" />
		<input type="hidden" id="admin" name="admin" value="<%=admin %>" />
		<input type="hidden" id="maximo" name="maximo" value="<%=Integer.parseInt(maximo) %>" />
	
	    <div class="row align-items-start">
	    	<div class="col">
	        	<div class="control-group ">
	                <label for="nombre">
	                    Matricula del Alumno
	                </label>
	                <input id="CodigoPersonal" name="CodigoPersonal" type="text" value="<%=codigoPersonal%>" maxlength="7"/>
	                &nbsp;
	                <img src="../../imagenes/loading.gif" alt="" style="display:none;" class="loading-bar" />
	                <span class="result"></span>
	            </div>	        	
	        	<div class="control-group ">
	                <label for="nombre">
	                    Promesa de Depósito
	                </label>
	                <div lang="en-US">
	                	<input id="promesa" name="promesa" type="number" value="<%=becAcuerdo.getPromesa().equals("")?"0":becAcuerdo.getPromesa() %>" maxlength="9" readonly step="any" />
	            	</div>
	            </div>	            
	             <div class="control-group ">
	                <label for="correo">
	                    Valor
	                </label>
	               <select name="valor" id="valor">
						<option value="P" <%if(becAcuerdo.getValor().equals("P"))out.print("selected"); %>>Porcentaje(%)</option>
		               	<option value="C" <%if(becAcuerdo.getValor().equals("C"))out.print("selected"); %>><spring:message code='aca.Cantidad'/>($)</option>
	               </select>	                
	            </div>	        	
	        	<div class="control-group ">
	                <label for="nombre">
	                    Ayuda en Matricula
	                </label>
	                <div lang="en-US">
	                	<input id="matricula" name="matricula" type="number" value="<%=becAcuerdo.getMatricula().equals("")?"0":becAcuerdo.getMatricula() %>" maxlength="9" step="any" />
	            	</div>
	            </div>	            
	            <div class="control-group ">
	                <label for="nombre">
	                    Ayuda en Enseñanza
	                </label>
	                <div lang="en-US">
	                	<input id="ensenanza" name="ensenanza" type="number" value="<%=becAcuerdo.getEnsenanza().equals("")?"0":becAcuerdo.getEnsenanza() %>" maxlength="9" step="any" />
	            	</div>
	            </div>
	        </div>   
	        <div class="col">  
	            <div class="control-group ">
	                <label for="nombre">
	                    Ayuda en Internado
	                </label>
	                <div lang="en-US">
	                	<input id="internado" name="internado" type="number" value="<%=becAcuerdo.getInternado().equals("")?"0":becAcuerdo.getInternado() %>" maxlength="9" step="any" />
	            	</div>
	            </div>	            
	            <div class="control-group ">
	                <label for="nombre">
	                    Horas
	                </label>
	                <div lang="en-US">
	                	<input id="horas" name="horas" type="number" value="<%=becAcuerdo.getInternado().equals("")?"0":becAcuerdo.getInternado() %>" maxlength="9" disabled step="any" />
	            	</div>
	            </div>	            
	            <div class="control-group ">
	                <label for="apPaterno">
	                    Vigencia
	                </label>
	                <input id="vigencia" data-date-format="dd/mm/yyyy" name="vigencia" type="text" value="<%=becAcuerdo.getVigencia() %>" maxlength="10"/>
	            </div>	        	
	        	<div class="control-group ">
	                <label for="correo">
	                    Estado
	                </label>
	               <select name="estado" id="estado">
		               	<option value="A" <%if(becAcuerdo.getEstado().equals("A"))out.print("selected"); %>><spring:message code='aca.Activo'/></option>    	
		               	<option value="C" <%if(becAcuerdo.getEstado().equals("C"))out.print("selected"); %>><spring:message code='aca.Inactivo'/></option>
	               </select>	                
	            </div>	        	
	        	<div class="control-group ">
	                <label for="correo">
	                    Centro de Costo
	                </label>
	               
<%
	               	String acceso		= aca.bec.BecAccesoUtil.getUsuarioCentrosCosto(conEnoc, idEjercicio, codigo);
	               	String [] deptos =  becTipo.getDepartamentos().split("-"); 
	%>
	               <select name="ccosto" id="ccosto" class="input-xxlarge">
		<%
	               		for(String depto: deptos){
	               			if(depto.equals(""))continue;
	               			//SI NO TIENE ACCESO NO LO MUESTRES
	               			if( admin || acceso.contains("-"+depto+"-") ){              			
		%>
		               	<option value="<%=depto %>" <%if(becAcuerdo.getIdCcosto().equals(depto))out.print("selected"); %>><%=depto%> | <%=ccostos.get( depto ).getNombre() %></option>
<%
	               			}
	               		}
%>
	               </select>
	                
	            </div>
	        </div>
	    </div>
	
	    <p class="alert alert-info" style="margin-top: 10px;">
	    
	        <a class="btn btn-primary btn-large guardar" ><i class='icon-ok icon-white'></i>  Guardar</a>
	        
	        <a class="btn btn-large" href="acuerdo?idEjercicio=<%=idEjercicio %>&tipo=<%=tipo %>"><i class="fas fa-trash-alt"></i> Cancelar</a>
	    </p>
	</form>

</div>	

<script>
	jQuery('.guardar').on('click', function(){
			
			if( document.forma.CodigoPersonal.value!="" && document.forma.promesa.value!="" && document.forma.matricula.value!="" && document.forma.ensenanza.value!="" && document.forma.internado.value!="" && document.forma.vigencia.value!="" ){
				if(document.forma.admin.value != "true"){
					if(parseInt(document.forma.ensenanza.value) > parseInt(document.forma.maximo.value) && document.forma.valor.value == "P"){
						alert("El porcentaje de beca es mayor al permitido "+document.forma.ensenanza.value+":"+document.forma.maximo.value+":"+document.forma.valor.value);
					}else{
						document.forma.submit();
					} 
				}else{
					document.forma.submit();	
				}
			}else{
				alert("Todos los campos son requeridos");
			}	
		
	});

	jQuery('#vigencia').datepicker();
	
	var loadingBar 	= jQuery('.loading-bar');
	var result 		= jQuery('.result');
	jQuery("#CodigoPersonal").keyup(function(){
		$this = jQuery(this);
		var value 	= $this.val();
		var length 	= value.length;
		if(length == 7){
			loadingBar.show();
			jQuery.get("getNombreAlumno?matricula="+value, function(r){
				loadingBar.hide();
				var nombre = jQuery.trim(r);
				
				result.html(nombre);
			});
		}else{
			loadingBar.hide();
			result.html("");
		}
	});
</script>	
<%@ include file= "../../cierra_enoc.jsp" %>