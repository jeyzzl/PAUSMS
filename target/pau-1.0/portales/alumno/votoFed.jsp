<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.voto.spring.VotoEvento"%>
<%@page import="aca.voto.spring.VotoCandidato"%>
<%@page import="aca.voto.spring.VotoCandidatoAlumno"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../body.jsp"%>
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
	body{
		padding: 0 20px;
	}
	
	.img-container{
		width: 180px;
		margin: 0 20px 0 0 !important;
		float: left;
	}
	
	input[type=radio]{
		margin: 2px 10px 0 0 !important;
	}
	
	.candidatos{
		overflow: hidden;
		margin-bottom: 20px;	
	}
		
	img{
		width: 100%;
		border: 3px solid gray;
		display: block;
	}
	
	#pacman{
		width: 200px;
		height: 200px;
		border: 3px solid gray;
		display: block;
	}
	
	.graficas{
		width: 50%;
		padding: 30px;
		float: left;
		border: 1px solid #BDBDBD;
	}
</style>

<%	
	String codigoSesion 		= (String) session.getAttribute("codigoLogeado");
	String matricula 			= (String) session.getAttribute("codigoPersonal");
	String eventoVoto			= (String) request.getAttribute("eventoVoto");
	VotoEvento votoEvento		= (VotoEvento)request.getAttribute("votoEvento");
	String eventoNombre			= (String) request.getAttribute("eventoNombre");
	String candidatoId			= (String) request.getAttribute("candidatoId");
	String candidatoNombre		= (String) request.getAttribute("candidatoNombre");	
	String estado				= (String) request.getAttribute("estado");
	String ganador				= (String) request.getAttribute("ganador");
	boolean existe				= (boolean) request.getAttribute("existe");
	boolean yaVoto				= (boolean) request.getAttribute("yaVoto");
	boolean esVotante			= (boolean) request.getAttribute("esVotante");
	String voto					= (String) request.getAttribute("voto");
	
	List<VotoCandidato> lisCandidatos 			= (List<VotoCandidato>) request.getAttribute("lisCandidatos");
	List<VotoCandidatoAlumno> lisAlumnos	 	= (List<VotoCandidatoAlumno>) request.getAttribute("lisAlumnos");	
	List<String> lisFacultades 					= (List<String>) request.getAttribute("lisFacultades");	
	HashMap<String,String> mapCandidatos 		= (HashMap<String,String>) request.getAttribute("mapa");
	HashMap<String,String> mapVoto 				= (HashMap<String,String>) request.getAttribute("mapaVoto");
	HashMap<String,String> mapYaVoto 			= (HashMap<String,String>) request.getAttribute("mapaYaVoto");
	HashMap<String,CatFacultad> mapaFacultades	= (HashMap<String,CatFacultad>) request.getAttribute("mapaFacultades");
%>

<div class="container-fluid">	
	<h2>Vota por tus candidatos<small class="text-muted fs-4">(<%=eventoNombre%>)</small></h2>
<!-- 	<hr /> -->
	<div class="alert alert-info">
<%
	String etiqueta = "<span class='label label-pill label-success'>"; // label-success label-warning label-inverse
	for (VotoCandidato candidato : lisCandidatos){
		if (mapYaVoto.containsKey(candidato.getCandidatoId())){
			etiqueta = "<span class='label label-pill label-inverse'>";
		}else if (candidato.getEstado().equals("I")){
			etiqueta = "<span class='label label-pill label-default'>";
		}else{
			etiqueta = "<span class='label label-pill label-success'>";
		}
		out.print(etiqueta);		
%>
		<a style="color:white;" href="votoFed?eventoVoto=<%=eventoVoto%>&candidatoId=<%=candidato.getCandidatoId() %>"><%=candidato.getCandidatoNombre()%></a>
		</span>&nbsp;&nbsp;		
<%	} %>		
	</div>	
	<form name="formVotar" action="votarFed?eventoVoto=<%=eventoVoto%>&candidatoId=<%=candidatoId%>" method="post">	
	<h4><%=candidatoNombre%><h4>
	<hr/>
		<div class="Hombres">
			<div class="row">
				<div class="span14">
<%	
				int row = 0;
				for (VotoCandidatoAlumno candidato: lisAlumnos ){
					
					String nombre = "-";
					if(mapCandidatos.containsKey(candidato.getCodigoPersonal())){
						nombre = mapCandidatos.get(candidato.getCodigoPersonal());
					}			
					
					String facultadNombre 	= "-";
					if (mapaFacultades.containsKey(candidato.getFacultadId())){
						facultadNombre = mapaFacultades.get(candidato.getFacultadId()).getNombreCorto();
					}					
					row++;
					if ( !lisFacultades.contains(candidato.getFacultadId()) || candidato.getCodigoPersonal().equals(ganador) ){
%>							
					<div class="img-container radio" style="height: 140px; width:140px;">
<%						if (yaVoto==true || !ganador.equals("0")){
							String votos = "0";
							if (mapVoto.containsKey(candidatoId+candidato.getCodigoPersonal())){
								votos = mapVoto.get(candidatoId+candidato.getCodigoPersonal());
							}
							String esGanador = candidato.getCodigoPersonal().equals(ganador)?"¡Electo!":"";
							out.print("<span style='font-size:14px;' class='label label-pill label-success'>"+votos+"</span>"+esGanador+"<br>");
						}
%>
							<span style="font-size:11px"><%=facultadNombre%></span>
							<img src="../../foto?Codigo=<%=candidato.getCodigoPersonal()%>" alt="" class="opacar"/>
							<%	if(yaVoto){				
									if (voto.equals(candidato.getCodigoPersonal())){
										out.print("<span class='label label-pill label-inverse'>X</span>");
									}
								}else if (!lisFacultades.contains(candidato.getFacultadId())){ %>
							<input type="radio" name="voto" id="voto" value="<%=candidato.getCodigoPersonal()%>">&nbsp;&nbsp;&nbsp;
							<%	} %>
							<span style="font-size:9px"><%=nombre%></span>
					</div>
<%					}
				} %>							
				</div>
			</div>
		</div>
<%
	if(!yaVoto && estado.equals("A") && ganador.equals("0") && esVotante){
%>			
		<div class="alert alert-info">		
			<a href="javascript:votar()" class="btn btn-primary">Votar</a>
		</div>	
<%	}else if (!yaVoto && estado.equals("I")){
		out.print("<br><span style='font-size:11px'>¡No está habilitada la votación para esta categoría!</span>");
	}else if (!yaVoto && !ganador.equals("0")){
		out.print("<br><span style='font-size:11px'><b>¡Ya existe un ganador para esta categoría!</b></span>");
	}else if (yaVoto){
		out.print("<br><span style='font-size:11px'><b>¡Tu voto ya está registrado!</b></span>");
	}else if(esVotante==false){
		out.print("<br><span style='font-size:11px'><b>¡No puedes participar en esta votación!</b></span>");
	}
%>	
	</form>
</div>