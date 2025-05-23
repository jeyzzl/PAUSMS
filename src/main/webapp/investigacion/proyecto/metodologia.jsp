<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
    
<jsp:useBean id="InvProyecto" scope="page" class="aca.investiga.InvProyecto"/>
<jsp:useBean id="InvProyectoU" scope="page" class="aca.investiga.InvProyectoUtil"/>
<jsp:useBean id="CarreraU" scope="page" class="aca.catalogo.CatCarreraUtil"/>
<jsp:useBean id="InvMetodologia" scope="page" class="aca.investiga.InvMetodologia"/>
<jsp:useBean id="InvMetodologiaU" scope="page" class="aca.investiga.InvMetodologiaUtil"/>

<%  
	String proyectoId		= request.getParameter("proyectoId")==null?"":request.getParameter("proyectoId");
	String accion			= request.getParameter("Accion")==null?"":request.getParameter("Accion");
	String nombreArchivo	= (String)request.getAttribute("nombreArchivo");
	
	String mostrar			= "block"; 
	
	String msj 	= "";
    // PARA EL COMBO DE CARRERAS ---------------------------------->
	ArrayList<aca.catalogo.CatCarrera> lisCarreras = CarreraU.getListAll(conEnoc, " ORDER BY FACULTAD_ID, NOMBRE_CARRERA");
	
	
	conEnoc.setAutoCommit(false);
	//nuevo
	if(accion.equals("0")){
		InvMetodologia.setProyectoId(proyectoId);
	}else if(accion.equals("1")){//guardar
		InvMetodologia.setProyectoId(proyectoId);
		InvMetodologia.setHumanos(request.getParameter("humanos"));
		InvMetodologia.setDiseno(request.getParameter("diseno"));
		InvMetodologia.setMuestra(request.getParameter("muestra"));
		InvMetodologia.setRecoleccion(request.getParameter("recoleccion"));
		InvMetodologia.setConfidencialidad(request.getParameter("confidencialidad"));
		InvMetodologia.setVinculacion(request.getParameter("vinculacion"));
		InvMetodologia.setProblema(request.getParameter("problema"));
		InvMetodologia.setObjetivo(request.getParameter("objetivo"));
		InvMetodologia.setHipotesis(request.getParameter("hipotesis"));
		InvMetodologia.setValidez(request.getParameter("validez"));

		
		if(request.getParameter("vinculacion").equals("I")){
			InvMetodologia.setOrganizacion(request.getParameter("carrera"));
		}else if (request.getParameter("vinculacion").equals("E")) {
			InvMetodologia.setOrganizacion(request.getParameter("organizacion"));
		}
		InvMetodologia.setResponsable(request.getParameter("responsable"));
		InvMetodologia.setActividades(request.getParameter("actividades"));
		InvMetodologia.setEntregable(request.getParameter("entregable"));
		InvMetodologia.setPlan(request.getParameter("plan"));
		
		if (InvMetodologiaU.existeReg(conEnoc, proyectoId)==false){
			if (InvMetodologiaU.insertReg(conEnoc, InvMetodologia)){
				msj = "<div class='alert alert-success' style='text-align:center; font-size:16px;'><button class='close' data-dismiss='alert'>&times;</button><strong>¡Excelente!</strong> El Registro se guardó correctamente</div>";
				conEnoc.commit();
			}else{
				msj = "<div class='alert alert-danger' style='text-align:center; font-size:16px;'><button class='close' data-dismiss='alert'>&times;</button><strong>¡Atención!</strong> Ocurrió un error al guardar el registro</div>";
			}
		}else{ //modifica
			if(InvMetodologiaU.updateReg(conEnoc, InvMetodologia)){
				msj = "<div class='alert alert-success' style='text-align:center; font-size:16px;'><button class='close' data-dismiss='alert'>&times;</button><strong>¡Buen trabajo!</strong> El registro se editó correctamente</div>";
				conEnoc.commit();
			}else{
				msj = "<div class='alert alert-warning' style='text-align:center; font-size:16px;'><button class='close' data-dismiss='alert'>&times;</button><strong>¡Atención!</strong> Ocurrió un error al editar el registro</div>";
			}
		}
	}else if(accion.equals("2")){
		InvMetodologia.setProyectoId(proyectoId);
		if(InvMetodologiaU.existeReg(conEnoc, proyectoId) == true){
			if(InvMetodologiaU.deleteReg(conEnoc, proyectoId)){
				msj = "<div class='alert alert-warning' style='text-align:center; font-size:16px;'><button class='close' data-dismiss='alert'>&times;</button> <strong>¡Bien hecho!</strong> El registro se eliminó correctamente</div>";
				conEnoc.commit();
			} else {
				msj = "<div class='alert alert-danger' style='text-align:center; font-size:16px;'><button class='close' data-dismiss='alert'>&times;</button><strong>¡Atención!</strong> Ocurrió un error al eliminar el registro</div>";
			}
		}else {
			msj = "<div class='alert alert-danger' style='text-align:center; font-size:16px;'><button class='close' data-dismiss='alert'>&times;</button> Este Registro no existe</div>";
		}
	}else if(accion.equals("3")){
		InvMetodologia.setProyectoId(request.getParameter("proyectoId"));
		InvMetodologia.mapeaRegId(conEnoc, request.getParameter("proyectoId"));
	}	 
	conEnoc.setAutoCommit(true);
/*	
	if (InvMetodologia.getHumanos().equals("N")){
		mostrar = "none";
	}
*/
%>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="../../js/jquery-3.2.1.min.js"></script>
	<script src="../../js/chosen/chosen.jquery.js" type="text/javascript"></script>
	<link rel="stylesheet" href="../../js/chosen/chosen.css" />
	
	<link rel="stylesheet" href="../../js-plugins/bootstrap-fileupload/bootstrap-fileupload.min.css" />
	<script src="../../js-plugins/bootstrap-fileupload/bootstrap-fileupload.min.js"></script>
	
	<style type="text/css">
		textarea, #responsable {
			width: 100%;
		}
	</style>
</head>
<body><br>
<%=msj %>
	<div class="container-fluid">
		<div class="alert alert-info">
			<h1 style="text-align:center">Formato para el registro y evaluación de proyectos</h1>
			<a href="proyecto" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>	
<%
			int temp = 0;
			if(InvMetodologia.getHumanos().equals("S")
			&& InvMetodologia.getDiseno()			!= ""
			&& InvMetodologia.getMuestra()			!= ""
			&& InvMetodologia.getRecoleccion()		!= ""
			&& InvMetodologia.getConfidencialidad() != ""
			&& InvMetodologia.getVinculacion()		!= "-"	
			&& InvMetodologia.getOrganizacion()		!= "-"
			&& InvMetodologia.getResponsable()		!= "-"
			&& InvMetodologia.getActividades()		!= "-"
			&& InvMetodologia.getEntregable()		!= "-"
			&& InvMetodologia.getPlan()				!= "-"
			&& InvMetodologia.getOrganizacion()		!= "-"
			&& InvMetodologia.getProblema()			!= "-"
			&& InvMetodologia.getObjetivo()			!= "-"
			&& InvMetodologia.getHipotesis()		!= "-"
			&& InvMetodologia.getValidez()			!= "-"){
%>				
				<a href="permisos?Accion=3&proyectoId=<%=proyectoId %>" class="btn btn-success pull-right">Siguiente <i class="icon-chevron-right icon-white"></i></a>
<%
				temp = 1;
			} else if (InvMetodologia.getHumanos().equals("N")
					&& InvMetodologia.getDiseno()			== ""
					&& InvMetodologia.getMuestra()			== ""
					&& InvMetodologia.getRecoleccion()		== ""
					&& InvMetodologia.getConfidencialidad() == ""
					&& InvMetodologia.getVinculacion()		!= "-"
					&& InvMetodologia.getOrganizacion()		!= "-"
					&& InvMetodologia.getResponsable()		!= "-"
					&& InvMetodologia.getActividades()		!= "-"
					&& InvMetodologia.getEntregable()		!= "-"
					&& InvMetodologia.getPlan()				!= "-"
					&& InvMetodologia.getOrganizacion()		!= "-"
					&& InvMetodologia.getProblema()			!= "-"
					&& InvMetodologia.getObjetivo()			!= "-"
					&& InvMetodologia.getHipotesis()		!= "-"
					&& InvMetodologia.getValidez()			!= "-"){
				
%>
				<a href="permisos?Accion=3&proyectoId=<%=proyectoId %>" class="btn btn-success pull-right">Siguiente <i class="icon-chevron-right icon-white"></i></a>
<%
			}
			temp = 1;
%>
		</div> 	
		<hr>
		<ul class="nav nav-tabs nav-justified">
			<li>
				<a href="accion?proyectoId=<%=proyectoId%>&Accion=3">
				  	<img class="icon icons8-1st" width="35" height="35" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAEm0lEQVRoQ+1ZTXYaRxD+ahg82lk5QeA9wzYocAB0ApQTRNrYsBI6QfAJglYQb0ROYHwC4T3YaAt+j9EJIu1kMTOV1wPY8z/dwziJ8jzb6a6ur+qrn64mPPGPnrj++A7g3/Zgbh6ovVnUdKYWM2oELgFU84PjOYNMgCe2hvfzl9V5HuD3AlAbrEo6WecMPiFQSUUhBpsEGlusX847ZVNlr3dtJgC1q9Wh/tn6DUA368GBfX3L0F/Pz8p3qvKUAdT/+HQC5isCDlUPS1rPwB2IzmavXoxV5CoBaAyXvydZnYF3zDzWQKZV4Lsdz934sOnQAZeI6ISAVoKS/Wm7ciELQgqAoEzhsyWsfhIUzIxbBvecg+JYlgJCnvawFnHTI8KPIZngkW0UL2TkSQGoD5dvg8ozcE+MvnWg92UOirKoG0sPVpcJXQKee9cweDRrV8/SPJEKoD781CfwuV847m2Nm3mlQkGxgkOTMAi6nLVfJCaKRAAiYIn5bcAyN7ZRbGa1epxFNzRdTwj0k+88ol+SAjsWwJb3K2+2EbSxDb2Ut/I7hbdnml5PiOxkG3o57sxYAEHquMpnpI0oeCisD2UoF0UnRjyVIgFsK+zK527G62mn0ksLKp81H+0WMZ8CaILxftqpNGX2NwbLHgiiUH75LNbLURU7EkDI+oxb+0CvyVCnMVy2tq2FUPzrpwDApdKDNfel2BgDxgBYrLy9jcN89qFTHSVZb+v669gKrQBAnPPzYHGqEV3tzhS906xdLQd1CAFwq6ZDH33uM/Qf0qzfGCybIFzHglQEsO23/vLpofFRMI5CAIL8E+3BrF0JVeCgojsADL5hopHj6GOdrK9xpAhAyK8Pl2Nv2+EQLj68qvS9Z4cAhDZJ0EcIdDMNAG+gNYZL/nJYBgBhGoWNGfbAcPHRdxlhHE87lYlM9gh5ZU8AYVryfNquHiV6wGc1AFYE72TB7OuBqHictis+o0d4wON2AMENssqLdfsCCMmI0Of/D+DJUag+XMx9HeF/KIhFip61q75pR25pNCo29o2BbGk00EjJFrJvASBTIcvaSuQNIHMr4ZbwwdL0doIyzVzeAEL0YdzOOpXQ8EyunQabtlE8Smvo8qrEUbdBqLTTeVxo9ilke19oNp2gfxrh3k01Ppa5Fu484fYy28876FK9VyhfKd3ucjPMUrpgq7QZUWuzDBKUxyoAzy2jeKwaD2ngNllnfR0cy3PWscruwJjBljKdVGkj1idRZycvdTK3SauLERH96lVCxAQR+tYz/TKrN1yrP1rnzO5o0TftZuY/Z52qfzAQYQUpAJspwbofBLGxEpsgrWc/K7yTBeLKe7RbYO5HDQGE8vZBsSsjTwpAEp0CXhmDaKwRm2vwvXe8XgQ9d5hKYPGaE55y7+TI0CbxRpYWbNsHjlFwEJu2L+2/mPyB6PSbPnDslNikO7sXnFqnKRn3X1jdNgo9GcoEZShRKLhZVOwC2V2XFhEPFUmAxMMIASML+ugff+SLLEJvFjWNqUmMJsRTUnBMDr4ByGTCxCGeqFT0JEPs5YGslMlz33cAeVozi6wn74G/AW8G+k+aMcHEAAAAAElFTkSuQmCC"><br>
	 			  	<span>Fundamentación</span>
				</a>
			</li>
			<li class="active">
				  <a href="#">
	 			  	<img class="icon icons8--Circled-2" width="35" height="35" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAFEElEQVRoQ+1ZS1IbSRB92WpZ7MycYESEW1sLiwOIE8CcYGBjpBXiBBYnsFghe4PmBJZPgFiPZMRWcgTiBIYdWN2djmp96K7+VTViJoiwturKypf58luEF/6jF64/fgP4vz24Mg+UP4/KJtMOM8oELgJUDoLjIYMmAPccAxfD96XhKsA/CUD59Lpokn3I4F0CFXUUYvCEQF2bzZNhfWOic9b/bSYA5bPrdfPB/gCgkfVi6VzLLpjHw/2NW1152gAqn77vgvmMgHXdy5K+Z+AWRPuDgzddHblaALba449JVmfgKzN3DdDEzvHtgudefDi07oKLRLRLwE6Ckq1+zTpSBaEEQFAm92ALq+/Kgplxw+Cmu5bvqlJAyDPupyJumkT4MyQT3HEK+SMVeUoAKu3xF1l5Bu6I0bLXzJbKRVEW9WLp3m4woUHAa/83DO4MaqX9NE+kAqi0v7cIfBgUjjvH4OqqUqGgWM6lXhgEnQxqbxITRSIAEbDE/EWyzJVTyFezWj3OojOaTnsEehu4j+ivpMCOBTDn/bU/2wjaOAWzuGrlFwrP75z4PSGyk1MwN+LujAUgU8dTXpE2QpH81H7rulw2DPIq7r/vrYs0Pov/o+jEiKdSJIB5hb0OXMg47tetZpIScxp8JNBeOLPglgit/oF1nAZk63TcBEEUyuXPZnMjqmJHAghZn3HjrJnlJOrMqvP0PNwDhdTt9WvWdqoh7u1hIMXGGDAGwOja39u4zPvf6qVO0qWhVMsQlOkx0TrAe35eq8h7dzraM4jOFneK3mlQK23IOoQAeFXTpcuA+wrmH4nWnzV1S8qJijyoWcuit3U6roJw/qhM8P/YGvFg/wjoYfCmnLpDAGT+ycpEXRY6E5H6KqfjyZISjIt+3aqmxUKlPe762w6XcPTtwGr5z4UAhA4p0Ofdp3HDcB/bDDvHDdlSlfb4xzIlKwII0yjsubAH2qPLQCAytvt1q5dmraT/wxRKr7BCnnwO4GG/VtpM9MBWe8xpvNMFsyUZJS4lynKj4rFfswJGj/BAEIB8QFf5Snt05q8LSUUpMr4kg/6nALzYYIgZwvsxWLuPkhmhDcCOSF0qXogIwDuHzbLO/JuJQpX2aBjoCDMEcaTyin2U3zjh4OerQa0U2HasJI36L12V8kJmtjQqNVIqhWwBQHa5TgcbRctMhSxLKyEun7fB54ti9VTl56sb/VZCKBMo+wDSmi/RfufIvpSGny4R4rdvrtdOxBbIEH0YN4O6FVqeqbXT4IlTyG/GNXThiqmQpxLmi6hpEDrttO5As2oATx5oPBpJ2whvNjV4O2oTMV+PSMvcZC/YMCdRNUGOpVkB1BwpvaCcLbO0BmwF4iR+kmWRoL1WER2hXchvr3ozETeScta1ysJUMYutWDpl8UIUbdKos7gndTM3S6ujDhH97VdOxITYMtivzJOs3vCs/tM+ZPZWi4FtNzP/M6iXQtsN2UBKADxu3k9bMoh5hzkBGU3nVe6rKhBP3k9nB8ytqDW9UN5ZyzdU5CkBSKKT5JUuiLoG8WQKvvOv1/Og1y5TESxec8Jb7seWW21a06KQX8n5A0dHXsRm4b4E/g5Ee8/6wLFs2rwU6zTlrXVWECLPO4VcU4UymWIgTrFZD+Q0PFpEPFQkARIPIwR0bJgdnSFnpQD8wkQqNJiqxKhCPCXJa3LwFUATJvRc4t6q3ha0gjgrRZ7z3G8Az2ldFdkv3gO/AKBOJl4mGfklAAAAAElFTkSuQmCC"/><br>
	 			  	<span>Metodología</span>
				  </a>
			</li>
			<li <%if(temp != 1)out.print("class=\"disabled\"");%>>
				<a <%if(temp == 1)out.print("href=\"resultados?proyectoId="+proyectoId+"\"");%>>				
	 			  	<img class="icon icons8-3-en-círculo-" width="35" height="35"
	 			  	src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAFKElEQVRoQ+1ZTXLaSBR+T4jgXZwTDFQFtoMD+8EnsOcEsTeJWBmfIOQEI68gszFzguATmNlDjLeQKvAJBu/s0OKlWkK41frplozH5apoq9br9733vV8hvPAHX7j+8AvAc3twax6o/j2pmoQHRFBFoCIAVoPgaEyAcwAaOAb8O/5QGW8D/KMAVDuzoonshIAOEbCYRiECmiNgn5F5Nm6W5mm+Fc9mAlA9n+2a9+wTALSyXix9Z7OC+Xl8XFqklZcaQO3L90MgOkeA3bSXJZ0ngAUgHo8+vu2nkZsKQL07/SvJ6gRwQUR9A3DOcrTwee7Gh4O7K6AiIh4iwEGCkvbQKp/qgtACwCmTu2fc6oeyYCK4IaD2aiff16UAl2fcLXnctBHht5BMoJ5TyJ/qyNMCUOtOv8rKE8AtEthsx7R1LoqyqBtLd6xFCC0EeC2eIaDeyKocqzyhBFDrfrcR6CQoHG4dgxrbSoWcYrkVDsIg8GxkvU1MFIkAeMAi0VfJMtdOId/IavU4i3o0XQ4Q8PfAfYh/JgV2LIA172dituG0cQpmcdvK+wqv75yLnuDZySmYpbg7YwHI1HGV16SNX5VFSzKkCx3KRdGJIJ5KkQDWFXYWcDfB52Gz3E4KKlfxFfJU24g5N2AGnaqA1DvTNiDwQrl5GJmlqIodCSBkfYIbZ8esJlFnbblLVYFTUYJr7FLpjo0DKTbGgDEAJjOxt1kRHX9rVnpJ1q93p5ei5QnoGgn9qtoAhD/875Mo4Z9515kcGYjnD9/QfGRVSrIOIQBrGlwF3Fcw36gCt96d0uayCI/VO9OBD4I3clHKiHeu+63/AnoYtCfTLwRA5h9vD0ZWOVSBA5fxrhTY0QaAgWM59clyh1ZZowZN+2LbsUI4/faxbIt3h4TUutJHGvRRVUv+XgTA6TWyKtK8EJYSplHYmGEPdCdXgWGEYH/YLA90lFQUqatNXGlktDVoHjs8ttYPjYdWZS/RAyKX+UEWwTsVGDcNG+w9kNtyVwn4lOa130T0z6hZ2dAtSVZUPMrUi/DAQzBy4TpclZWod6aS5bwTaZT3ZcoGfVYALgjNLnNrALJQKJQO71grUFk1YyAThWrdyTjQEW4hiDmgWmfSQ8T3nhdgMbLKb1SxJFMxKns9Sxp1k4NGccyWRqVGSqeQyUUqSrnQmZjmTPRKqCbpFLIsrcS7L9OWQcC7UC/bSIHK02oO2dUmlQLcjqxy4lYjcyvh8XU6FztBVTMXPYi4iyt/YRVorzM1cwQ3o2Y5tDzTa6eB5k4hv5fU0EWNn1FBygNRNZJGTYOQpp1+1EDjoC22zg+08rYYqqFo3UI8bqBxaSRtI9xBxKB91TTFv12vS6pk4C6uaCEuuVSpM2owSj1S+krk7lmqAVulnOp9lkVC6rUKAI1ZIb+vGnBUysrvvayzvJTX8pR1reJfELPY0qaTDpC4eVonWymnIrkNEIJygQg2e2WeZfWGa/Uf7ITIXS0G6oJu56oFwNsSLG2/lxGtyudbQKPtvMpd6AJx5f1wDoDIjtpicOWdnXxLR54WgCQ6BcFAHxD7BtJ8CXQrrtfzgK9XhEUg/jcnvOV+8Kx6HyremQqASyfvB0dPXsTqcD3pDN/8AeLRk/7g8BXw0p3TlrfWWUHwYHUKubYOZeQ7UntAFOA1aU7LpUXEj4pEixPcIECPgdn733/yRSnGU6FB2EDie1EqhtbkQNcAOCeEwQppoFPRdTz6KA/oXPDUZ34BeGoLq+S/eA/8BKOUNF73TsOBAAAAAElFTkSuQmCC"><br>
				  	<span>Recursos</span>
				</a>
			</li>
			<li class="disabled">
				<a href="#">
	 				<img class="icon icons8-4-en-círculo-" width="35" height="35"
	 				src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAE70lEQVRoQ+1ZTXLaWBD+WojgXTwnGFwV2A4O7AefwJ4TjL1JYGV8giEnGLyCzMbMCUJOYLIeiOUtShXkBLF3TpDo1JMEJT39PQk8rlRFS9B7r7/ur7/u1yL84A/94PbjJ4CnjuDOIlD7Z1bTmY6ZUSNwGaBaEBwbDFoAPLY1fDBeVY1dgN8KQK0/L+tknTP4hEDlLAYxeEGgkcX6pdE+WGRZ6383F4Da1Xxf/2r9BaCT92BpXc8q6W+Ms4O7rPtlBlB/++kEzFcE7Gc9LOl9Bu5AdDZ9/WKUZd9MABoD8+8krzPwnplHGmhhFfhuzXMnP2zaX4HLRHRCwHGCkb1Jq3KhCkIJgKBM4aslvH4ib8yMzwzurvaKI1UKiP20h6XImy4Rfg3tCR7apeKFyn5KAOoD851sPAP3xOhZe3pP5aAojzq59GB1mNAh4Ln/HQYPp63qWVokUgHUB596BD4Pbo57W+PmrqRQUKywonEYBF1OWy8ShSIRgEhYYn4neebWLhWbeb0e51GXpssxgX4LnEf0R1JixwLweD/3q42gjV3Sy7s2fm2wd+bCHwmhTnZJP4g7MxaATB3H+By0EfQogjb8/u9V5UMSr6PoxIinUiQAr8LOAwcx3kzalW5aUvn/d+RzRTf+3yatSmreNfpmFwRRKDePxfpBVMWO3CzkfcZne0+vZaVOYzC7kXsiFQAOlR4sIyCxMQ6MATCb+3ubFfPZx3Z1mMX7L9+aHY0hCl/gUQEgFrzsz041oqv1YtE7TVvVA3m/EICosFsl/Zcs3hcULJB1E9VuqALw+q0vARppfChLdwiAzD/RHkxblVAFTopGY2BeA2hGvaMKQKytD8yRv+1YES4+vq70/PuGAIQWZaRPoHYwXMUh/L4+NAuAMI3CzgxHQE48xtGkXRmr8F+uHUI5dFjDvAAafbMJgoim97AxaVUPEyPQGJicxrs4MPX+bEhEfzr/e6rR6JvjvABUZDgiAkEAqiH3e0t0qNN2xbmhbQPAWS85VLZnJwC8PuZmI70+2j05ACtCumQK+ZWLmf+dtqun63e2AZCLQvXBzAh0hClJHG472ABoc7dliClF4PrpCMKkVTlKEwY5iRl8O21VA9OOrWU0rBRpZrn/q+RWPhmVGqm0QvaYAHIVsqythHctlIZYPuUGn26k1ZVXhzpptSV3K+GU8L658HeCeZo5XxIHWmMV6oi1Ifr4pDmxkLk9SPAeLDpBu1Q8zNLQbQMg6ja4Loxyhj3qhcYrZJkjsPWFJjoKuLM1Pso6iXBkFtZmbprKfXdCcR28i2e8UgoAeS7YagIa/1aeQULmsQrAhlUqHuXJhySAruosr+UrKOcdq6wPixls5aJTHABvEhGgjau4Ww62NiD8bbL3o5jXEKFnPdMv80bD8fo365zZGS0Gpt1yTxUHPnXEscmHh2UvUJA2QHgB0rr2s8J7VSAO17/Zx2DuRd2bhfH2XrGjsp8SgCQ6+T3DwAhEI414sQTf+8frYri1YiqDxdec8JR7vY8KbVILWVKyeR84hvIgdlsFEpM/EJ0+6geOtZGu3NldeWqdF4Twul0qdFUoo1SJVQ1x5z92x6FFxIeKpH3EtZOAoQV9+L9/5IsyTEihxtQkFvMgLofG5OBbgBZMGK+Ix1kr+lYqpBqRp3gvkwo9hYFpZ/4EkOahx/7/O2t79U8rziZVAAAAAElFTkSuQmCC" 
	 				/><br>
					<span>Anexos</span>
				</a>
			</li>			
		</ul>
		<hr>
		<h2><small class="text-muted">Proyecto N° <%=proyectoId %></small></h2>
		<div class="alert alert-warning" style="text-align:center; font-size:18px;"><button class="close" data-dismiss="alert">×</button><strong>¡Atención!</strong> Seleccione si los sujetos de estudio son seres humanos</div>
		<form name="forma" id="forma" action="metodologia" method="post">
		<input type="hidden" name="proyectoId"  value="<%=proyectoId%>"/>
		<input type="hidden" name="Accion" id="Accion" />
		<div>
  			<label for="invol"><strong>¿Los sujetos de estudio son seres humanos?</strong></label>
  			<select id="humanos" name="humanos" onchange="ocultar()">
  				<option value="-">Seleccione</option> 
  				<option value="S" <%if(InvMetodologia.getHumanos().equals("S"))out.print("selected"); %>>Si</option>
  				<option value="N" <%if(InvMetodologia.getHumanos().equals("N"))out.print("selected"); %>>No</option>
  			</select>
  		</div><br>
		<div class="panel panel-info" id="metodologia" style="display:<%=mostrar%>">
			<div class="panel-heading"><h3>Metodología</h3></div>
  			<div class="panel-body">
  			<fieldset>
  				<div class="col-md-4">
  					<label for="diseno"><strong>Diseño de investigación</strong></label>
  					<textarea maxlength="2000" name="diseno" id="diseno" placeholder="El diseño de investigación contesta cuatro preguntas: ¿Qué vas ha hacer?, ¿Cuando lo vas ha hacer?, ¿Dónde los vas ha hacer? y ¿Cómo los vas ha hacer?" rows="3" cols="70" required><%= InvMetodologia.getDiseno()=="-"?"":InvMetodologia.getDiseno() %></textarea>
  				</div>
  				<div class="col-md-4">
  					<label for="muestra"><strong>Población y Muestra</strong></label>
  					<textarea name="muestra" id="muestra" placeholder="Población es el total de individuos, objetos o medidas que poseen algunas características comunes observables en un lugar y en un momento determinado.
					Muestra es un subconjunto de la población en la que se llevará a cabo la investigación." rows="3" cols="70" required><%= InvMetodologia.getMuestra()=="-"?"":InvMetodologia.getMuestra() %></textarea>
  				</div>
  				<div class="col-md-4">
  					<label for="recoleccion"><strong>Recolección de datos</strong></label>
  					<textarea name="recoleccion" id="recoleccion" rows="3" cols="70" 
  					placeholder="Descripción de técnicas y herramientas que pueden ser utilizadas para desarrollar los sistemas de información, los cuales pueden ser la entrevistas, la encuesta, el cuestionario, la observación." required><%= InvMetodologia.getRecoleccion()=="-"?"":InvMetodologia.getRecoleccion() %></textarea>
  				</div>
  				<div class="col-md-4">
  					<br><label for="confidencialidad"><strong>Aspectos éticos</strong></label>
  					<textarea name="confidencialidad" id="confidencialidad" rows="3" cols="70" placeholder="Evidenciar el respeto del derecho de los participantes del estudio a fin de salvaguardar su integridad, así como la información que provee." required><%= InvMetodologia.getConfidencialidad()=="-"?"":InvMetodologia.getConfidencialidad() %></textarea>
  				</div>
  				<div class="col-md-4">
  					<br><label for="problema"><strong>Declaración del Problema</strong></label>
  					<textarea name="problema" id="problema" rows="3" cols="70" placeholder="Se debe explicar claramente cuál es el problema en que se enmarca el estudio y generalmente se redacta en pregunta de investigación." required><%=InvMetodologia.getProblema()=="-"?"":InvMetodologia.getProblema()%></textarea>
  				</div>
  				<div class="col-md-4">
  					<br><label for="objetivo"><strong>Objetivos</strong></label>
  					<textarea name="objetivo" id="objetivo" rows="3" cols="70" placeholder="Declarar de forma detallada los objetivos generales y específicos(el qué)." required><%=InvMetodologia.getObjetivo()=="-"?"":InvMetodologia.getObjetivo()%></textarea>
  				</div>
  				<div class="col-md-4">
  					<br><label for="validez"><strong>Confiabilidad y Validez de los instrumentos</strong></label>
  					<textarea name="validez" id="validez" rows="3" cols="70" placeholder="Describir el objeto/instrumento de medidción que logra medir la variable que se propone y su validez." required><%=InvMetodologia.getValidez()=="-"?"":InvMetodologia.getValidez()%></textarea>
  				</div>
  				<div class="col-md-4">
  					<br><label for="hipotesis"><strong>Hipótesis</strong></label>
  					<textarea name="hipotesis" id="hipotesis" rows="3" cols="70" 
  					placeholder="Posible respuesta a la formulación del problema de investigación. Busca aprobar el impacto de las variables entre sí, muestra la relación causa-efecto." required><%=InvMetodologia.getHipotesis()=="-"?"":InvMetodologia.getHipotesis()%></textarea>
  				</div>
  			</fieldset><br>
<!--   				<button type="button" id="btnGuardar" class="btn btn-success" onclick="guardar();" style="margin-left:13px;"><i class="glyphicon glyphicon-save"></i><strong> Guardar</strong></button>
 -->  			</div>
		</div>		
		<div class="panel panel-info">
			<div class="panel-heading"><h3>Vinculación</h3></div>
  			<div class="panel-body">
  				<label for="vinculacion"><strong>Tipo de vinculación</strong></label>
	  			<select id="vinculacion" name="vinculacion">
	  					<option value="">Seleccione...</option>
	 					<option value="I" <% if(InvMetodologia.getVinculacion()!=null && InvMetodologia.getVinculacion().equals("I"))out.print("selected");%>>Interna</option>
	  					<option value="E" <% if(InvMetodologia.getVinculacion()!=null && InvMetodologia.getVinculacion().equals("E"))out.print("selected");%>>Externa</option>
	  					<option value="N" <% if(InvMetodologia.getVinculacion()!=null && InvMetodologia.getVinculacion().equals("N"))out.print("selected");%>>Ninguna</option>
	  			</select><br><br>	
  				<div>
  					<div id="desplegable">
	  					<div id="I" class="col-md-12 col-md-12">
	  						<label for="carrera"><strong><spring:message code="aca.Carrera"/></strong></label>
							<select name="carrera" id="carrera" class="chosen"  style="width:500px;">
								<option value="1">Selecciona la carrera</option>
							<%
								String facultad 		= "0";
								String facultadNombre 	= "-";
								for(aca.catalogo.CatCarrera carrera : lisCarreras){
									if (!carrera.getFacultadId().equals(facultad)){
										facultadNombre = aca.catalogo.CatFacultadUtil.getNombreFacultad(conEnoc, carrera.getFacultadId());
										if (!facultad.equals("0")){
											out.print("</optgroup>");	
										}
										out.print("<optgroup label='"+facultadNombre+"'>");
										facultad = carrera.getFacultadId();
									}		
							%>
									<option value="<%=carrera.getCarreraId() %>" <%if(carrera.getCarreraId().equals(InvMetodologia.getOrganizacion()))out.print("selected"); %>>
									  <%= carrera.getNombreCarrera() %>
									</option>
							<%				
								}
								
								// Cerrando el ultimo grupo
								out.print("</optgroup>");
							%>
							</select>
	  					</div>
	  					<div id="E" class="col-md-12 col-md-12">
	  						<label for="organizacion"><strong>Nombre de la organización</strong></label>
	  						<input type="text" class="input-xxlarge" name="organizacion" id="organizacion" placeholder="Nombre del organanismo, instituto o persona física" required value="<%=InvMetodologia.getOrganizacion()=="-"?"":InvMetodologia.getOrganizacion() %>"/>
	  					</div>
	  				</div>
	  					<div class="col-md-4">
	  						<br><label for="responsable"><strong>Responsable</strong></label>
	  						<input name="responsable" id="responsable" placeholder="Nombre del responsable de la vinculación" required value="<%= InvMetodologia.getResponsable()=="-"?"":InvMetodologia.getResponsable() %>"/>
	  					</div>
	  					<div class="col-md-4">
	  						<br><label for="actividades"><strong>Actividades a realizar</strong></label>
	  						<textarea name="actividades" id="actividades" rows="3" cols="70" 
	  						placeholder="Describir aquellas actividades que son responsabilidad del colaborador." required><%= InvMetodologia.getActividades()=="-"?"":InvMetodologia.getActividades() %></textarea>
	  					</div>
	  					<div class="col-md-4">
	  						<br><label for="entregable"><strong>Entregable</strong></label>
	  						<textarea name="entregable" id="entregable" rows="3" cols="70" 
	  						placeholder="Aportes realizados al protocolo de investigación " required><%= InvMetodologia.getEntregable()=="-"?"":InvMetodologia.getEntregable() %></textarea>
	  					</div>	  							 
  				</div>  				
			</div>
		</div>
		<div class="alert alert-info">
	  						<a href="javascript:guardar()" class="btn btn-success"><i class="glyphicon glyphicon-save"></i><strong> Guardar</strong></a> 	  						
	  	</div>	
		</form>
	</div>	
</body>
<style>
#desplegable div{
	display: none;
}
</style>

<script type="text/javascript">
/*	
	function ocultar(){
		var div = document.getElementById("metodologia");
		var select = document.getElementById("humanos").value;
		if(select.localeCompare("S")){
			div.style.display = 'none';
		} else {
			div.style.display = '';
		}
	}
*/	
	function guardar(){
		
		if(document.forma.diseno.value != ""
			&& document.forma.muestra.value != ""
			&& document.forma.recoleccion.value != ""
			&& document.forma.confidencialidad.value != ""
			&& document.forma.vinculacion.value != ""
			&& document.forma.responsable.value != ""
			&& document.forma.actividades.value != ""
			&& document.forma.entregable.value != ""			
			&& document.forma.organizacion != ""
			&& document.forma.problema != ""
			&& document.forma.objetivo != ""
			&& document.forma.hipotesis!= ""
			&& document.forma.validez != ""
		){			
			document.forma.Accion.value = "1";
			document.forma.submit();
		}else{		
			alert("¡Complete todos los campos!");
		}
	}
</script>

<script>
$(document).ready(function(){
	$('#vinculacion').on('change',function(){
		var select = '#'+$(this).val();
		$('#desplegable').children('div').hide();
		$('#desplegable').children(select).show();
	});
});
</script>

</html>
<%@ include file= "../../cierra_enoc.jsp" %>