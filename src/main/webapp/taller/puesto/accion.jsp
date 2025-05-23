<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.bec.spring.BecCompetencia"%>
<%@page import="aca.bec.spring.BecCategoria"%>
<%@page import="aca.bec.spring.BecPuesto"%>
<%@page import="aca.financiero.spring.ContCcosto"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%
	String idEjercicio		= (String)request.getAttribute("ejercicioId");
	String accion 			= (String)request.getAttribute("accion");	
	String codigo			= (String)session.getAttribute("codigoPersonal");
	boolean admin       	= (boolean)request.getAttribute("admin");
	String acceso			= (String)request.getAttribute("acceso");
	String periodoNombre	= (String) request.getAttribute("periodoNombre");
	
	List<BecCompetencia> competencias = (List<BecCompetencia>) request.getAttribute("competencias");
	
	String msj = (String) request.getAttribute("msj");
	
	BecPuesto becPuesto = (BecPuesto) request.getAttribute("becPuesto");

	List <BecCategoria> categorias 	= (List<BecCategoria>) request.getAttribute("categorias");
	List<ContCcosto> ccostos 		= (List<ContCcosto>) request.getAttribute("ccostos");
	
	HashMap<String, String> niveles = new HashMap<String, String>();
	
	for(ContCcosto ccosto: ccostos){
		int puntos = 0;
		
		for(int i=0; i<ccosto.getIdCcosto().length(); i++){
			char chr = ccosto.getIdCcosto().charAt(i);
			
			if(chr == '.')puntos++;
		}
		
		if(puntos == 4){
			//do nothing
		}else{
			niveles.put(ccosto.getIdCcosto(), ccosto.getNombre());
		}
	}
	
	//Verifica si hay alumnos en el puesto o categoría 
	boolean tieneAlumnos = (boolean) request.getAttribute("tieneAlumnos");
	
%>

<link rel="stylesheet" href="../../js/chosen/chosen.css" />
<style>
	body{
		background:white;
	}
</style>

<div class="container-fluid">
	<h2>Editar Puesto<small class="text-muted fs-5">( <%=idEjercicio%>&nbsp;-&nbsp;<%=periodoNombre%> )</small></h2>
	<div class="alert alert-info">
		<a href="puesto" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
<%	if(msj.equals("0")){%>
		<div class='alert alert-success'><button class='close' data-dismiss='alert'>&times;</button> El Registro se Editó correctamente</div>
<%	} else if(msj.equals("1")){%>
		<div class='alert alert-error'><button class='close' data-dismiss='alert'>&times;</button> Ocurrió un error al editar el registro</div>
<%	} else if(msj.equals("2")){%>
		<div class='alert alert-success'><button class='close' data-dismiss='alert'>&times;</button> El Registro se guardó correctamente</div>
<%	} else if(msj.equals("3")){%>
		<div class='alert alert-error'><button class='close' data-dismiss='alert'>&times;</button> Ocurrió un error al guardar el registro</div>
<%	}%>
	
	<form name="forma" id="forma" action="grabar" method="post">
		<input type="hidden" name="ejercicioId" value="<%=idEjercicio%>"/>
		<p>
			<label for="ccosto">Centro de Costo</label>
			<select name="ccosto" id="ccosto" class="chosen"  style="width:800px;" <%if(accion.equals("0"))out.print("disabled"); %> >
<%		String group = "";
		for(ContCcosto ccosto : ccostos){
			if( !admin && !acceso.contains("-"+ccosto.getIdCcosto()+"-") ){//SI NO TIENE ACCESO NO LO MUESTRES
				continue;
			}
				
			int puntos = 0;
				
			for(int i=0; i<ccosto.getIdCcosto().length(); i++){
				char chr = ccosto.getIdCcosto().charAt(i);
				if(chr == '.'){
					puntos++;
				}
			}
					
			if(puntos == 4){//Si estamos en el ultimo nivel de los centro de costo
				String idccosto = ccosto.getIdCcosto();
					
				String [] arr 	= idccosto.split("\\.");
				String tmp 		= "";
				String groupTmp = "";
				
				int cont = 0;
				for(String str: arr){
					if(cont == arr.length-1){
						break;//El ultimo no se cuenta porque ya es el nombre de la ultimo nivel
					}
					
					tmp+="."+str;
					
					if(tmp.charAt(0) == '.'){
						tmp = tmp.substring(1);
					}
					
					groupTmp += niveles.get(tmp)+" | "; 
					cont++;
				}
					
				if(!groupTmp.equals(group)){//Pintar la categoria de los centros de costo%>
				</optgroup>
				<optgroup label="<%=groupTmp.subSequence(0, groupTmp.length()-2) %>">
<%					group = groupTmp;
				}%>
					<option value="<%=ccosto.getIdCcosto() %>" <%if(ccosto.getIdCcosto().equals(becPuesto.getIdCcosto()))out.print("selected"); %>><%=ccosto.getIdCcosto() %> | <%=ccosto.getNombre() %></option>
<%			}
		}%>
			</select>
		</p>
		<p>
			<label for="categoria">Puesto</label>
			<select name="categoria" id="categoria" class="chosen" <%if(accion.equals("0"))out.print("disabled"); %> style="width:500px;">
<%		for(BecCategoria categoria : categorias){%>
				<option value="<%=categoria.getCategoriaId() %>" <%if(categoria.getCategoriaId().equals(becPuesto.getCategoriaId()))out.print("selected"); %>><%=categoria.getCategoriaNombre() %></option>
<%		}%>
			</select>
		</p>
		<p>
			<label for="justificacion">Justificación</label>
			<textarea name="justificacion" id="justificacion" cols="100" rows="3"><%=becPuesto.getJustificacion()==null?"":becPuesto.getJustificacion() %></textarea>
		</p>
		<p>
			<label for="funcion">Función</label>
			<textarea name="funcion" id="funcion" cols="100" rows="3"><%=becPuesto.getFuncion()==null?"":becPuesto.getFuncion() %></textarea>
		</p>
		<p>
			<label for="competencias">Competencias <a href="" class="todas">Todas</a> | <a href="" class="ninguna">Ninguna</a></label>
			<div style="margin-left:30px;">
<%		String tmpComp = becPuesto.getCompetencias()==null?"":becPuesto.getCompetencias();
		List<String> arr = java.util.Arrays.asList( tmpComp.split(","));
		for(BecCompetencia comp : competencias){	
			String checked = "";
			
			if(arr.contains(comp.getCompetenciaId())){
				checked = "checked=checked";
			}%>
			<p><input <%=checked %> type="checkbox" id="competencia<%=comp.getCompetenciaId() %>" name="competencia<%=comp.getCompetenciaId() %>" class="comp" /> <%=comp.getCompetenciaNombre() %></p>
<%		}%>
			</div>
		</p>
		<p>
			<label for="otrasComp">Otras competencias</label>
			<textarea name="otrasComp" id="otrasComp" cols="100" rows="3" maxlength="1500"><%=becPuesto.getOtrasComp()==null?"":becPuesto.getOtrasComp() %></textarea>
		</p>
		<p>
			<label for="certificaciones">Certificaciones</label>
			<textarea name="certificaciones" id="certificaciones" cols="100" rows="3"><%=becPuesto.getCertificaciones()==null?"":becPuesto.getCertificaciones() %></textarea>
		</p>
		<p>
			<div class="control-group ">
		        <label for="cantidad">
		            Cantidad
		        </label>
		        <input id="cantidad" name="cantidad" required type="text" value="<%=becPuesto.getCantidad().equals("")?"0":becPuesto.getCantidad() %>" maxlength="3" readonly />
			</div>
		</p>
		<p>
			<label for="estado">Estado </label>
<% 	if(!admin && false){//esta opcion se desabilita temporalmente para que siempre se muestre lo que esta en el "else"
		if (becPuesto.getEstado().equals("I")){
			out.print("&nbsp; <b><spring:message code='aca.Inactivo'/></b>"); 
		}else {
			out.print(" &nbsp; <b><spring:message code='aca.Activo'/></b>");
		}%>
			<input name="estado" type="hidden" value="<%=becPuesto.getEstado().equals("")%>"/>
<% 	}else{%>					
			<select name="estado" id="estado">
				<option value="A" <% if(becPuesto.getEstado().equals("A")) out.println("Selected");%>><spring:message code='aca.Activo'/></option>
				<option value="I" <% if(becPuesto.getEstado().equals("I")) out.println("Selected");%>><spring:message code='aca.Inactivo'/></option>
			</select>		
<% 	}%>
		</p>
		<div class="alert alert-info">
<%	if(accion.equals("0")){%>		
			<button class="btn btn-primary btn-large"><i class="fas fa-edit icon-white"></i> Editar</button>
			<input type="hidden" name="ccosto" value="<%=becPuesto.getIdCcosto() %>" />
			<input type="hidden" name="categoria" value="<%=becPuesto.getCategoriaId() %>" />
<%		if(admin && tieneAlumnos==false){ %>
			<a href="borrar?ccosto=<%=becPuesto.getIdCcosto() %>&categoria=<%=becPuesto.getCategoriaId() %>" class="btn btn-danger btn-large"><i class="icon-trash icon-white"></i> Eliminar</a>
<%		} 	
	}else{%>
			<button class="btn btn-primary btn-large grabar"><i class="icon-ok icon-white"></i> Grabar</button>
<% 	}%>
			<a href="puesto" class="btn btn-light"><i class="fas fa-trash-alt"></i> Cancelar</a>
		</div>
	</form>
	<br/><br/>
	<script src="../../js/chosen/chosen.jquery.js" type="text/javascript"></script>
	<script type="text/javascript"> 
		jQuery(".chosen").chosen(); 
		
		var chkbox = jQuery('.comp');
		jQuery('.todas').on('click', function(e){
			e.preventDefault();
			chkbox.prop('checked', true);
		});
		
		jQuery('.ninguna').on('click', function(e){
			e.preventDefault();
			chkbox.prop('checked', false);
		});
		
		jQuery('#forma').on('submit', function(e){
			e.preventDefault();
			document.forma.submit();
		});
	</script>
</div>