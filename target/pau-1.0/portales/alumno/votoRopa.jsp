<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@ page import="java.util.Date" %>

<%@page import="aca.voto.spring.VotoEvento"%>
<%@page import="aca.voto.spring.VotoCandidato"%>
<%@page import="aca.voto.spring.VotoCandidatoAlumno"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>
<script>
	function votar(){
		if (document.formVotar.voto.value != ""){			
			document.formVotar.submit();
		}else{
			alert("¡Elige un candidato para ésta categoría!")
		}				
	}
</script>
<style>	
	.img-container{		
		margin: 0 10px 0 0 !important;
		float: left;
	}
	
	input[type=radio]{
		margin: 2px 10px 0 0 !important;
	}
			
	img{
		width: 100%;
		border: 1px solid black;
		display: block;
	}	
</style>

<%	
	String codigoSesion 		= (String) session.getAttribute("codigoLogeado");
	String matricula 			= (String) session.getAttribute("codigoPersonal");
	String eventoVoto			= (String) request.getAttribute("eventoVoto");	
	String eventoNombre			= (String) request.getAttribute("eventoNombre");
	
	String candidatoId			= (String) request.getAttribute("candidatoId");
	String candidatoNombre		= (String) request.getAttribute("candidatoNombre");
	String candidatos			= (String) request.getAttribute("candidatos");		
	boolean existe				= (boolean) request.getAttribute("existe");
	boolean yaVoto				= (boolean) request.getAttribute("yaVoto");
	boolean esInscrito			= (boolean) request.getAttribute("esInscrito");
	String fechaFin				= (String) request.getAttribute("fechaFin");
	String estado				= (String) request.getAttribute("estado");
	String codigos				= (String) request.getAttribute("codigos");
	String[] arrCodigos = codigos.split(",");
	String mensaje				= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	
	List<VotoCandidato> lisCandidatos 			= (List<VotoCandidato>) request.getAttribute("lisCandidatos");
	List<String> lisElegidos 					= (List<String>) request.getAttribute("lisElegidos");	
	
	HashMap<String,String> mapVoto 				= (HashMap<String,String>) request.getAttribute("mapaVoto");
	HashMap<String,String> mapYaVoto 			= (HashMap<String,String>) request.getAttribute("mapaYaVoto");	
	
	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	Date dateEvento = sdf.parse(fechaFin);
	Date dateHoy 	= new Date();
%>
<div class="container-fluid">	
	<h2>Votación <small class="text-muted fs-4">(<%=eventoNombre%>)</small></h2>
	<div class="alert alert-info text-center">
<%
	String etiqueta = "<span class='btn btn-success '>"; // label-success label-warning label-inverse
	for (VotoCandidato candidato : lisCandidatos){
		if (mapYaVoto.containsKey(candidato.getCandidatoId())){
			etiqueta = "<span class='btn btn-dark '>";
		}else{
			etiqueta = "<span class='btn btn-success '>";
		}
		out.print(etiqueta);
%>		
		<a style="color:white; font-size:10px;" class="text-decoration-none" href="votoRopa?eventoVoto=<%=eventoVoto%>&candidatoId=<%=candidato.getCandidatoId() %>"><%=candidato.getCandidatoNombre()%></a>
		</span>
<%		
	}
%>		
	</div>
	<form name="formVotar" action="votarRopa?eventoVoto=<%=eventoVoto%>&candidatoId=<%=candidatoId%>" method="post">
	<div class="container">
<% 	
	int row=0;
	for(String voto : arrCodigos){
		row++;
		voto = voto.replace("'","");
		if(!voto.equals("-")){
			String ancho = "500px";
			if (voto.equals("S4") || voto.equals("G5")) ancho = "250px";
%>
		<div class="flex-row d-flex mb-4 justify-content-center">
		<div class="span4">
			<div class="img-container radio">				
<%			if (yaVoto){
				String votos = "0";
				if (mapVoto.containsKey(candidatoId+voto)){
					votos = mapVoto.get(candidatoId+voto);
				}
				out.print("<span style='font-size:10px' class='left badge bg-success'>"+votos+"</span><br>");
			}
%>
				<div class="text-center">
				<img src="<%=voto%>.png" style="width:<%=ancho%>" class="mb-2"/>
					<%	if(yaVoto){				
							if (lisElegidos.contains(voto)){
								out.print("<span class='badge bg-dark'>X</span>");
							}
						}else{ %>
				<input type="radio" name="voto" id="voto" value="<%=voto%>" class="align-items-center">
					<%	} %>
				<span class="fw-bold">Diseño <%=row%></span>
				</div>
			</div>
		</div>
		</div>		
<% 		}%>						
<%	} %>		
	</div>
	<div class="alert alert-info">
<%	  
	if( !yaVoto && (esInscrito || codigoSesion.equals("9800308"))){
		if(estado.equals("A") && dateHoy.before(dateEvento)) {
			out.print("<a href='javascript:votar()' class='btn btn-primary'>Votar</a>");
		}else{
			if (!mensaje.equals("-")){
				out.print(mensaje);
			}else{
				out.print("¡La votacion no esta activa!");
			}
		}
	}else if(yaVoto){
		out.print("¡Tu voto ya está registrado!");
	}else if (!esInscrito){
		out.print("¡Solo los alumnos inscritos pueden participar!");
	}
%>
	</div>	
	</form>
</div>