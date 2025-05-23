
<%@page import="java.util.*" %>
<%@page import="java.text.*" %>

<%@ page import="aca.bec.spring.BecPuesto"%>
<%@ page import="aca.bec.spring.BecPuestoAlumno"%>
<%@ page import="aca.bec.spring.BecCategoria"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<style>
	.circuloAzul{		
		width: 	120px;
		height: 120px;
		border-radius: 100%;		
		/*background:-moz-linear-gradient(top, red, gold);*/
		/*background:radial-gradient(center center, circle, black, #1162ac);*/
		/*background:-moz-radial-gradient(center center, circle, black, #1162ac);*/
		background:radial-gradient(100px top, ellipse, white, #1162ac, black);		
		background:-moz-radial-gradient(100px top, ellipse, white, #1162ac, black);
		background:-webkit-radial-gradient(100px top, ellipse, white, #1162ac, black);		
		background:-o-radial-gradient(100px top, ellipse, white, #1162ac, black);		
		background:-ms-radial-gradient(100px top, ellipse, white, #1162ac, black);									
	}
	.circuloGris{		
		width: 	120px;
		height: 120px;
		border-radius: 100%;		
		background:radial-gradient(100px top, ellipse, black, #787474, black);		
		background:-moz-radial-gradient(100px top, ellipse, black, #787474, black);
		background:-webkit-radial-gradient(100px top, ellipse, black, #787474, black);		
		background:-o-radial-gradient(100px top, ellipse, black, #787474, black);		
		background:-ms-radial-gradient(100px top, ellipse, black, #787474, black);									
	}
</style>
<%	
	String periodoId 						= (String)request.getAttribute("periodoId");
	String deptoId							= (String)request.getAttribute("deptoId");
	String deptoNombre						= (String)request.getAttribute("deptoNombre");
	List<BecPuesto> lisPuestos 						= (List<BecPuesto>)request.getAttribute("lisPuestos");
	List<BecPuestoAlumno> lisAlumnos				= (List<BecPuestoAlumno>)request.getAttribute("lisAlumnos");
	HashMap<String, BecCategoria> mapaCategorias 	= (HashMap<String, BecCategoria>)request.getAttribute("mapaCategorias");
	HashMap<String, String> mapaAlumnos 			= (HashMap<String, String>)request.getAttribute("mapaAlumnos");
	HashMap<String, String> mapaPuestos 			= (HashMap<String, String>)request.getAttribute("mapaPuestos");
%>
<%@ include file= "menu.jsp" %>
<div class="container-fluid">
	<h3><a href="plazas?PeriodoId=<%=periodoId%>"><i class="fas fa-arrow-left"></i></a>&nbsp;
		<%=deptoNombre%>
	</h3>
	<div class="alert alert-info">
		<h4> Experiencias de aprendizaje que se pueden realizar en el departamento</h4>
	</div>	
  <div class="row">
	<%	
	for (BecPuesto puesto : lisPuestos){
		boolean existe = false;
		String circulo = "circuloGris";		
		String usados = "0";
		if (mapaPuestos.containsKey(puesto.getIdCcosto()+puesto.getCategoriaId())){
			usados = mapaPuestos.get(puesto.getIdCcosto()+puesto.getCategoriaId());
			existe = true;
			circulo = "circuloAzul";
		}
		
		String categoriaNombre = "-";
		String pdf = "-";
		if (mapaCategorias.containsKey(puesto.getCategoriaId())){
			categoriaNombre = mapaCategorias.get(puesto.getCategoriaId()).getCategoriaNombre();
			pdf = mapaCategorias.get(puesto.getCategoriaId()).getPdf();			
		}
		String ruta = "aprendizaje/"+pdf;
		//System.out.println(ruta);
%>	
   <div class="col-xs-12 col-sm-6 col-md-4 col-lg-2 col-xl-1 justify-content-center mx-auto"  data-bs-toggle="tooltip" data-bs-placement="top" title="<%=puesto.getFuncion()%>">
		<div class="<%=circulo%> d-flex align-items-center justify-content-center mx-auto position-relative">
			<span class="text-center" style="color:white; font-size:9pt">
				<b><%=categoriaNombre%></b><div class="position-absolute bottom-0 start-50 translate-middle" style="color:white;"><b><%=usados%></b></div>
			</span>			
		</div>
<% 		if(pdf.contains(".pdf")){%>		
		<div class="d-flex align-items-center justify-content-center mx-auto mt-1" style="width:120px;">
			<div class="d-flex align-items-center"><a href="<%=ruta%>" target="_blanck"><i class="fas fa-file-pdf fa-1x"></i></a></div>
		</div>
<% 		}%>		
	</div>
	
<%
	} 
%>
</div>
	<br>	
	<div class="alert alert-info">
		<h4>Alumnos registrados en servicio becario</h4>
	</div>
		
	<div class="container-fluid">
  		<div class="row row-cols-1 row-cols-sm-2 row-cols-md-4">
	
<%	
	for (BecPuestoAlumno alumno : lisAlumnos){
	
		
		String categoriaNombre = "-";
		if (mapaCategorias.containsKey(alumno.getCategoriaId())){
			categoriaNombre = mapaCategorias.get(alumno.getCategoriaId()).getCategoriaNombre();
		}
		
		String alumnoNombre = "-";
		if (mapaAlumnos.containsKey(alumno.getCodigoPersonal())){
			alumnoNombre = mapaAlumnos.get(alumno.getCodigoPersonal());
		}
		
%>		  
		<div class="card border-dark  mb-3 mx-auto" style="max-width: 18rem; border: 1px solid; border-radius:10px;" > 
			<div class="card-header border-dark bg-transparent" style="height:44px">
    			<p class="text-center" style="font-size:9pt;"><%=categoriaNombre%></p>
			</div> 			
  			<div class="card-body text-dark mx-auto">
    			<img class="rounded border border-dark " src="../../fotoMenu?Codigo=<%=alumno.getCodigoPersonal()%>&Tipo=O" width="140">
  			</div>
  			<div class="card-footer bg-transparent border-dark text-center" style="font-size:9pt;"><%=alumno.getCodigoPersonal()%> <%=alumnoNombre%></div>
		</div>	
		
<%
	} 
%>		
		</div>
	</div>
</div>
<style>
	body{
 		background : white;
 	}
 </style>
 <script>
	var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
	var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
 	 return new bootstrap.Tooltip(tooltipTriggerEl)
	})
</script>