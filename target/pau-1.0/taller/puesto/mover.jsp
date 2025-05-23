<%@ include file="../../con_enoc.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="becPuestoAlumno" class="aca.bec.BecPuestoAlumno" 	scope="page" />
<jsp:useBean id="alumPersonal" scope="page"	class="aca.alumno.AlumPersonal" />
<jsp:useBean id="becPuesto" class="aca.bec.BecPuesto" scope="page" />
<jsp:useBean id="becPuestoUtil" class="aca.bec.BecPuestoUtil"	scope="page" />
<jsp:useBean id="BecAccesoU" scope="page" class="aca.bec.BecAccesoUtil" />
<jsp:useBean id="contEjercicioU"	class="aca.financiero.ContEjercicioUtil" scope="page" />
<jsp:useBean id="cCostoUtil" class="aca.financiero.ContCcostoUtil"	scope="page" />
<jsp:useBean id="becPuestoAlumnoU" class="aca.bec.BecPuestoAlumnoUtil"	scope="page" />
<jsp:useBean id="becTipo" class="aca.bec.BecTipo" scope="page" />
<jsp:useBean id="becAcuerdoU" class="aca.bec.BecAcuerdoUtil"	scope="page" />
<jsp:useBean id="becAcuerdoBasica" class="aca.bec.BecAcuerdo"	scope="page" />
<jsp:useBean id="becAcuerdoAdicional" class="aca.bec.BecAcuerdo"	scope="page" />

<jsp:useBean id="AfeAcuerdosU"  class="aca.afe.FesCcAfeAcuerdosUtil" scope="page"/>

<%	
	String codigo		= (String)session.getAttribute("codigoPersonal");

	String puestoId    	= request.getParameter("PuestoId");
	String periodoId    = request.getParameter("PeriodoId");	
	
	String folio    = request.getParameter("folio");	

	becPuestoAlumno.setPuestoId(puestoId);
	becPuestoAlumno.mapeaRegId(conEnoc);
	String idEjercicio 	= becPuestoAlumno.getIdEjercicio();
	
	String acceso		= aca.bec.BecAccesoUtil.getUsuarioCentrosCosto(conEnoc, idEjercicio, codigo);	
	boolean admin       = aca.acceso.AccesoUtil.getBecas(conEnoc, codigo);
	String alumno 		= aca.alumno.AlumUtil.getNombreAlumno(conEnoc, becPuestoAlumno.getCodigoPersonal(), "NOMBRE");
	String matricula 	= becPuestoAlumno.getCodigoPersonal();
	String categoria 	= becPuestoAlumno.getCategoriaId();
	
	//Nombres de categorías	
	java.util.HashMap <String, String> categorias = aca.bec.BecCategoriaUtil.getMapCategorias(conEnoc);
	
	java.util.HashMap <String, aca.financiero.ContCcosto> ccostos = aca.financiero.ContCcostoUtil.getMapCentrosCosto(conEnoc, idEjercicio);
	
	
	// PARA EL COMBO DE CENTRO DE COSTOS ---------------------------------->
	String idCcosto = ""; 
	
	ArrayList<aca.financiero.ContCcosto> cCostos = cCostoUtil.getListCentrosCosto(conEnoc, idEjercicio, "ORDER BY ID_CCOSTO");
	
	if(request.getParameter("ccosto")!=null){
		idCcosto = request.getParameter("ccosto");
	}else if((String)session.getAttribute("ccosto") != null){
		idCcosto = (String) session.getAttribute("ccosto");
	}
	
	session.setAttribute("ccosto", idCcosto);
	
	java.util.HashMap<String, String> niveles = new java.util.HashMap<String, String>();
	
	for(aca.financiero.ContCcosto ccosto: cCostos){
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
	
	String msj 			= "";	
	String accion 		= request.getParameter("Accion")==null?"":request.getParameter("Accion");
	String newDepto		= request.getParameter("ccosto")==null?"-":request.getParameter("ccosto");
	String newCat		= request.getParameter("categoria")==null?"-":request.getParameter("categoria");
	if(accion.equals("1")){//Modificar	
		
		conEnoc.setAutoCommit(false);
		boolean error = false;
		
		becPuestoAlumno.setIdCcosto(newDepto);
		becPuestoAlumno.setCategoriaId(newCat);
		if(becPuestoAlumnoU.update(conEnoc, newDepto, newCat, puestoId)){// (1) ACTUALIZAR BEC_PUESTOALUMNO
			
			ArrayList<aca.bec.BecAcuerdo> acuerdos = becAcuerdoU.getListPuestoId(conEnoc, idEjercicio, puestoId, "");
			for(aca.bec.BecAcuerdo acuerdo: acuerdos ){// (2) ACTUALIZAR LOS ACUERDOS DEL ALUMNO DE BEC_ACUERDO
				
				acuerdo.setIdCcosto(newDepto);			
				if(becAcuerdoU.updateCcosto(conEnoc, newDepto, acuerdo.getFolio(), acuerdo.getCodigoPersonal() )){
					//System.out.println("Modificó depto:"+newDepto);
				}else{					
					error = true;
					//System.out.println("Error modificar depto:"+newDepto);
					break;
				}
			}
			
			if(!error){// (3) ACTUALIZAR LOS ACUERDOS DEL ALUMNO DE MATEO.FES_CC_AFE_ACUERDOS
				ArrayList<aca.afe.FesCcAfeAcuerdos> acuerdosMateo = AfeAcuerdosU.getListPuestoId(conEnoc, idEjercicio, puestoId, "");
				//System.out.println("Size:"+acuerdosMateo.size()+":"+idEjercicio+":"+puestoId);
				for(aca.afe.FesCcAfeAcuerdos acuerdo: acuerdosMateo ){					
					
					acuerdo.setAcuerdoCcostoId(newDepto);
					acuerdo.setCategoriaId(newCat);					
					// Busca el nombre de la categoría
					String nombreCategoria = "-";
					if (categorias.containsKey(newCat)) nombreCategoria = categorias.get(newCat);					
					acuerdo.setCategoriaNombre(nombreCategoria);
					
					if(AfeAcuerdosU.updateCcostoCategoria(conEnoc, acuerdo)){
						//System.out.println("Correcto:"+acuerdo.getAcuerdoFolio());
					}else{
						//System.out.println("Error en FesCcAfeAcuerdos:"+acuerdo.getAlpuestoPuestoId()+":"+acuerdo.getAcuerdoFolio());
						error = true;
						break;
					}
				}
			}
			
			
		}else{
			error = true;
		}
		
		
		if(error){
			conEnoc.rollback();
			msj = "<div class='alert alert-danger'>Ocurrió un Error al Actualizar</div>";
		}else{
			conEnoc.commit();
			msj = "<div class='alert alert-success'>Se Actualizó Correctamente</div>";
		}

				
		conEnoc.setAutoCommit(true);		

	}
%>
<link rel="stylesheet" href="../../js/chosen/chosen.css" />
<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<style>
	body{
		background:white;
	}
	.puestosAlum td, .puestosAlum th{
		background: white !important;
	}
	
	.puestosAlum th{
		color: black !important;
		border: 1px solid #DCDEDE !important;
	}
</style>

<div class="container-fluid">

	<h2 style="margin-bottom: 10px;">Mover a otro puesto <small class="text-muted fs-4"> ( <%=matricula %> - <%=alumno%> - ) </small></h2>
	<%=msj %>
	<div class="alert alert-info">
		<a href="puesto" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>	
	<form action="mover" name="forma" method="get">
	<input type="hidden" name="Accion" id="Accion" />
	<input type="hidden" value=<%=puestoId %> name="PuestoId" id="PuestoId"/>
	<input type="hidden" value=<%=periodoId %> name="PeriodoId" id="PeriodoId"/>
	<div class="alert alert-info">
	<h3>Centros de Costo</h3>
	<select name="ccosto" id="ccosto" class="chosen"  style="width:600px;" onchange="document.forma.submit()">
	<%
		String group = "";
		boolean encontro = false;
		String centroCosto = ""; //AQUÍ IRÁ EL PRIMER CENTRO DE COSTO DE SUS PRIVILEGIOS EN CASO DE NO ENCONTRAR COINCIDENCIA CON EL QUE VENÍA ANTERIORMENTE
		for(aca.financiero.ContCcosto ccosto : cCostos){
				
				if( !admin && !acceso.contains("-"+ccosto.getIdCcosto()+"-") ){//SI NO TIENE ACCESO NO LO MUESTRES
					continue;
				}
				if(centroCosto.length()==0){//Graba el primer centro de costo para cargarlo por defecto para cuando no encuentre coincidencia
					centroCosto = ccosto.getIdCcosto();
				}				
				int puntos = 0;
				
				for(int i=0; i<ccosto.getIdCcosto().length(); i++){
					char chr = ccosto.getIdCcosto().charAt(i);
					
					if(chr == '.')puntos++;
				}
					
				
				if(puntos == 4){//Si estamos en el ultimo nivel de los centro de costo
					
					if(idCcosto.equals("")){
						idCcosto = ccosto.getIdCcosto();
					}
					
					String idccosto = ccosto.getIdCcosto();
					
					String [] arr 	= idccosto.split("\\.");
					String tmp 		= "";
					String groupTmp = "";
					
					int cont = 0;
					for(String str: arr){
						if(cont == arr.length-1)break;//El ultimo no se cuenta porque ya es el nombre de la ultimo nivel
						
						tmp+="."+str;
						
						if(tmp.charAt(0) == '.')tmp = tmp.substring(1);
						

						groupTmp += niveles.get(tmp)+" | "; 
						cont++;
					}
					
					if(!groupTmp.equals(group)){//Pintar la categoria de los centros de costo
						%>
							</optgroup>
							<optgroup label="<%=groupTmp.subSequence(0, groupTmp.length()-2) %>">
						<%
						group = groupTmp;
					}
					
	%>
					<option value="<%=ccosto.getIdCcosto() %>" <%if(idCcosto.equals(ccosto.getIdCcosto())){out.print("selected"); encontro = true;} %>><%=ccosto.getIdCcosto() %> | <%=ccosto.getNombre() %></option>
	<%
				}
		}
	%>
	</select>
	<%
		if(!encontro){//Si no encontró ocincidencia con departamento
			idCcosto = centroCosto;//Nuevo valor de idccosto
		}	
	//Lista de categorías	
		ArrayList<aca.bec.BecPuesto> lisPuestos = becPuestoUtil.getListCategorias(conEnoc, periodoId, idCcosto, " ORDER BY CATEGORIA_NOMBRE(CATEGORIA_ID)");
	%>
	<br><br>
	<h4>Categorías</h4>
	<select name="categoria" id="categoria" >
<%
		
		for(aca.bec.BecPuesto puesto : lisPuestos){
			
			// Busca el nombre de la categoría
			String nombreCategoria = "-";
			if (categorias.containsKey(puesto.getCategoriaId())) nombreCategoria = categorias.get(puesto.getCategoriaId());
%>
		<option value="<%=puesto.getCategoriaId() %>" <%if(categoria.equals(puesto.getCategoriaId()))out.print("selected"); %>><%=nombreCategoria%></option>
	<%	} %>
	</select>
	</div>
	</form>
	<button onclick="javascript:modificar();" class="btn btn-primary btn-large"><i class='icon-ok icon-white'></i> Modificar</button>

</div>

<script>
	function modificar() {
			if(document.forma.ccosto.value != "" && document.forma.categoria.value != ""){
				document.forma.Accion.value = "1";
				document.forma.submit();
			}else{
				alert("¡Completa todos los campos!");
			}
	}
</script>

<script src="../../js/chosen/chosen.jquery.js" type="text/javascript"></script>
<script type="text/javascript"> 
		jQuery(".chosen").chosen(); 
</script>
<%@ include file="../../cierra_enoc.jsp"%>