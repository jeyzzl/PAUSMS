<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.financiero.spring.ContCcosto"%>
<%@page import="aca.financiero.spring.ContEjercicio"%>
<%@page import="aca.bec.spring.BecTipo"%>
<%@page import="aca.bec.spring.BecAcceso"%>
<%@page import="aca.bec.spring.BecAcuerdo"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<%		
	String ejercicioId 		= request.getParameter("EjercicioId")==null?"0":request.getParameter("EjercicioId");
	String tipo 			= request.getParameter("Tipo")==null?"0":request.getParameter("Tipo");
	String codigoAlumno 	= request.getParameter("CodigoAlumno")==null?"0":request.getParameter("CodigoAlumno");
	String folio 			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
	String mensaje 			= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	
	BecTipo becTipo			= (BecTipo) request.getAttribute("becTipo");
	BecAcuerdo becAcuerdo	= (BecAcuerdo) request.getAttribute("becAcuerdo");
	boolean admin      		= (boolean)request.getAttribute("esAdmin");		
	String acceso			= (String)request.getAttribute("acceso");
	String maximo			= becTipo.getMaximo();
	
	HashMap<String,ContCcosto> ccostos = (HashMap<String,ContCcosto>)request.getAttribute("mapaDeptos");	
%>
<style>
	body{
		background:white;
	}
</style>
<div class="container-fluid">
	<h2 style="margin-bottom:10px;">Añadir Acuerdo<small class="text-muted fs-5">( <%=becTipo.getNombre() %> porcentaje de beca entre 0% y <%=maximo%>% )</small></h2>	
	<div class="alert alert-info">
		<a href="acuerdo?EjercicioId=<%=ejercicioId %>&Tipo=<%=tipo %>" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
	<%=mensaje%>	
	<form  method="post" action="grabarAcuerdo" name="forma" id="forma">		
		<input type="hidden" id="Folio" name="Folio" value="<%=folio%>" />
		<input type="hidden" id="EjercicioId" name="EjercicioId" value="<%=ejercicioId %>" />
		<input type="hidden" id="Tipo" name="Tipo" value="<%=tipo %>" />
		<input type="hidden" id="Admin" name="Admin" value="<%=admin %>" />
		<input type="hidden" id="Maximo" name="Maximo" value="<%=Integer.parseInt(maximo) %>" />	
	    <div class="row align-items-start">
    	<div class="col">
        	<div class="control-group ">
                <label for="nombre">
                    Matrícula del Alumno
                </label>
                <input id="CodigoAlumno" name="CodigoAlumno" type="text" class="form-control"  value="<%=codigoAlumno%>" maxlength="7"/>
                &nbsp;
                <img src="../../imagenes/loading.gif" alt="" style="display:none;" class="loading-bar" />
                <span class="result"></span>
            </div>	        	
        	<div class="control-group ">
                <label for="nombre">
                    Promesa de Depósito
                </label>
                <div lang="en-US">
                	<input id="Promesa" name="Promesa" type="number" class="form-control"  value="<%=becAcuerdo.getPromesa().equals("")?"0":becAcuerdo.getPromesa() %>" maxlength="9" readonly step="any" />
            	</div>
            </div>	            
             <div class="control-group ">
                <label for="correo">
                    Valor
                </label>
               <select name="Valor" id="Valor" class="form-select" >
					<option value="P" <%if(becAcuerdo.getValor().equals("P"))out.print("selected"); %>>Porcentaje(%)</option>
	               	<option value="C" <%if(becAcuerdo.getValor().equals("C"))out.print("selected"); %>><spring:message code='aca.Cantidad'/>($)</option>
               </select>	                
            </div>	        	
        	<div class="control-group ">
                <label for="nombre">
                    Ayuda en Matricula
                </label>
                <div lang="en-US">
                	<input id="Matricula" name="Matricula" type="number" class="form-control"  value="<%=becAcuerdo.getMatricula().equals("")?"0":becAcuerdo.getMatricula() %>" maxlength="9" step="any" />
            	</div>
            </div>	            
            <div class="control-group ">
                <label for="nombre">
                    Ayuda en Enseñanza
                </label>
                <div lang="en-US">
                	<input id="Ensenanza" name="Ensenanza" type="number" class="form-control"  value="<%=becAcuerdo.getEnsenanza().equals("")?"0":becAcuerdo.getEnsenanza() %>" maxlength="9" step="any" />
            	</div>
            </div>
        </div>   
        <div class="col">  
            <div class="control-group ">
                <label for="nombre">
                    Ayuda en Internado
                </label>
                <div lang="en-US">
                	<input id="Internado" name="Internado" type="number" class="form-control"  value="<%=becAcuerdo.getInternado().equals("")?"0":becAcuerdo.getInternado() %>" maxlength="9" step="any" />
                	       &nbsp;
            	</div>
            </div>	            
            <div class="control-group ">
                <label for="nombre">
                    Horas
                </label>
                <div lang="en-US">
                	<input id="Horas" name="Horas" type="number" class="form-control"  value="<%=becAcuerdo.getInternado().equals("")?"0":becAcuerdo.getInternado() %>" maxlength="9" disabled step="any" />
            	</div>
            </div>	            
            <div class="control-group ">
                <label for="apPaterno">
                    Vigencia
                </label>
                <input id="Vigencia" data-date-format="dd/mm/yyyy" name="Vigencia" type="text" class="form-control"  value="<%=becAcuerdo.getVigencia() %>" maxlength="10"/>
            </div>	        	
        	<div class="control-group ">
                <label for="correo">
                    Estado
                </label>
               <select name="Estado" id="Estado" class="form-select" >
	               	<option value="A" <%if(becAcuerdo.getEstado().equals("A"))out.print("selected"); %>><spring:message code='aca.Activo'/></option>    	
	               	<option value="C" <%if(becAcuerdo.getEstado().equals("C"))out.print("selected"); %>><spring:message code='aca.Inactivo'/></option>
               </select>	                
            </div>	        	
        	<div class="control-group ">
                <label for="correo">
                    Centro de Costo
                </label>
	               
<%	               	
	               	String [] deptos =  becTipo.getDepartamentos().split("-"); 
	%>
               <select name="Ccosto" id="Ccosto" class="form-select">
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
	        <a class="btn btn-danger" href="acuerdo?EjercicioId=<%=ejercicioId %>&Tipo=<%=tipo %>"><i class="fas fa-trash-alt"></i> Cancelar</a>
	    </p>
	</form>
</div>	

<script>
	jQuery('.guardar').on('click', function(){
			
			if( document.forma.CodigoAlumno.value!="" && document.forma.Promesa.value!="" && document.forma.Matricula.value!="" && document.forma.Ensenanza.value!="" && document.forma.Internado.value!="" && document.forma.Vigencia.value!="" ){
				if(document.forma.Admin.value != "true"){
					if(parseInt(document.forma.Ensenanza.value) > parseInt(document.forma.Maximo.value) && document.forma.Valor.value == "P"){
						alert("El porcentaje de beca es mayor al permitido "+document.forma.Ensenanza.value+":"+document.forma.Maximo.value+":"+document.forma.Valor.value);
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

	jQuery('#Vigencia').datepicker();
	
	var loadingBar 	= jQuery('.loading-bar');
	var result 		= jQuery('.result');
	jQuery("#CodigoAlumno").keyup(function(){
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