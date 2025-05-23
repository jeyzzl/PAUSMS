<%@page import="aca.investiga.InvMetodologia"%>
<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="InvActividad" scope="page" class="aca.investiga.InvActividad"/>
<jsp:useBean id="InvActividadU" scope="page" class="aca.investiga.InvActividadUtil"/>
<jsp:useBean id="InvPresupuesto" scope="page" class="aca.investiga.InvPresupuesto"/>
<jsp:useBean id="InvPresupuestoU" scope="page" class="aca.investiga.InvPresupuestoUtil"/>
<jsp:useBean id="InvResultado" scope="page" class="aca.investiga.InvResultado"/>
<jsp:useBean id="InvResultadoU" scope="page" class="aca.investiga.InvResultadoUtil"/>

<%
	String proyectoId 		= request.getParameter("proyectoId")==null?"0":request.getParameter("proyectoId");
	String actividadId 		= request.getParameter("actividadId")==null?"0":request.getParameter("actividadId");
	String presupuestoId 	= request.getParameter("presupuestoId")==null?"0":request.getParameter("presupuestoId");
	String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String msj 	= "";	
	String nombreArchivo8	= (String)request.getAttribute("nombreArchivo8");	
	
	conEnoc.setAutoCommit(false);
	if(accion.equals("1")) { //guardar
		InvActividad.setProyectoId(proyectoId);
		if (actividadId.equals("0"))
			InvActividad.setActividadId(InvActividadU.maximoReg(conEnoc, proyectoId));
		else
			InvActividad.setActividadId(actividadId);
		InvActividad.setActividadNombre(request.getParameter("actividadNombre"));
		InvActividad.setFechaIni(request.getParameter("fechaIni"));
		InvActividad.setFechaFin(request.getParameter("fechaFin"));
			
		if (InvActividadU.existeRegId(conEnoc, proyectoId, InvActividad.getActividadId())==false){
			if (InvActividadU.insertReg(conEnoc, InvActividad)){
				msj = "<div class='alert alert-success' style='text-align:center; font-size:16px;'><button class='close' data-dismiss='alert'>&times;</button><strong>¡Excelente!</strong> El Registro se guardó correctamente.</div>";
				conEnoc.commit();
			}else{
				msj = "<div class='alert alert-danger' style='text-align:center; font-size:16px;'><button class='close' data-dismiss='alert'>&times;</button><strong>¡Atención!</strong> Ocurrió un error al guardar el registro</div>";
			}
		}else{ //modifica
			if(InvActividadU.updateReg(conEnoc, InvActividad)){
				msj = "<div class='alert alert-success' style='text-align:center; font-size:16px;'><button class='close' data-dismiss='alert'>&times;</button><strong>¡Excelente!</strong> El Registro se guardó correctamente.</div>";				
				conEnoc.commit();
			}else{
				msj = "<div class='alert alert-warning' style='text-align:center; font-size:16px;'><button class='close' data-dismiss='alert'>&times;</button><strong>¡Atención!</strong> Ocurrió un error al editar el registro</div>";
			}
		}
	} else if(accion.equals("2")){ //borrar
		InvActividad.setProyectoId(proyectoId);
		InvActividad.setActividadId(actividadId);		
		if(InvActividadU.existeRegId(conEnoc, proyectoId, InvActividad.getActividadId()) == true){
			if( InvActividadU.deleteRegId(conEnoc, proyectoId, InvActividad.getActividadId()) ){
				msj = "<div class='alert alert-warning' style='text-align:center; font-size:16px;'><button class='close' data-dismiss='alert'>&times;</button> <strong>¡Bien hecho!</strong> El registro se eliminó correctamente</div>";
				conEnoc.commit();
			} else {
				msj = "<div class='alert alert-danger' style='text-align:center; font-size:16px;'><button class='close' data-dismiss='alert'>&times;</button><strong>¡Atención!</strong> Ocurrió un error al eliminar el registro</div>";
			}
		}else {
			msj = "<div class='alert alert-danger' style='text-align:center; font-size:16px;'><button class='close' data-dismiss='alert'>&times;</button> Este Registro no existe</div>";
			
		}
	} else if(accion.equals("3")){ //editar		
		InvActividad.setProyectoId(proyectoId);
		InvActividad.setActividadId(actividadId);
		if(InvActividadU.existeRegId(conEnoc, proyectoId, actividadId)){			
			InvActividad.mapeaRegId(conEnoc, proyectoId, actividadId);			
		}
%>		
		<script type="text/javascript">document.presupuesto.presupuestoNombre.focus()</script>
<%		
		InvResultado.mapeaRegId(conEnoc, proyectoId);
	} else if (accion.equals("4")) {
		
		InvPresupuesto.setProyectoId(proyectoId);
		if (presupuestoId.equals("0"))			
			InvPresupuesto.setPresupuestoId(InvPresupuestoU.maximoReg(conEnoc, proyectoId));
		else
			InvPresupuesto.setPresupuestoId(presupuestoId);
		
		InvPresupuesto.setPresupuestoNombre(request.getParameter("presupuestoNombre"));
		InvPresupuesto.setTipo(request.getParameter("tipo"));
		InvPresupuesto.setMonto(request.getParameter("monto"));
		
		if (InvPresupuestoU.existeRegId(conEnoc, proyectoId, InvPresupuesto.getPresupuestoId())==false){
			if (InvPresupuestoU.insertReg(conEnoc, InvPresupuesto)){
				msj = "<div class='alert alert-success' style='text-align:center; font-size:16px;'><button class='close' data-dismiss='alert'>&times;</button><strong>¡Excelente!</strong> El Registro se guardó correctamente</div>";
				conEnoc.commit();
			}else{
				msj = "<div class='alert alert-danger' style='text-align:center; font-size:16px;'><button class='close' data-dismiss='alert'>&times;</button><strong>¡Atención!</strong> Ocurrió un error al guardar el registro</div>";
			}
		}else{ //modifica
			if(InvPresupuestoU.updateReg(conEnoc, InvPresupuesto)){
				msj = "<div class='alert alert-success' style='text-align:center; font-size:16px;'><button class='close' data-dismiss='alert'>&times;</button><strong>¡Buen trabajo!</strong> El registro se editó correctamente</div>";
				conEnoc.commit();
			}else{
				msj = "<div class='alert alert-warning' style='text-align:center; font-size:16px;'><button class='close' data-dismiss='alert'>&times;</button><strong>¡Atención!</strong> Ocurrió un error al editar el registro</div>";
			}
		}
	} else if (accion.equals("5")) {
		InvResultado.setProyectoId(proyectoId);
		InvResultado.setInfraestructura(request.getParameter("infraestructura"));
		InvResultado.setBibliografia(request.getParameter("bibliografia"));
		
		if (InvResultadoU.existeReg(conEnoc, proyectoId)==false){
			if (InvResultadoU.insertReg(conEnoc, InvResultado)){
				//msj = "<div class='alert alert-success' style='text-align:center; font-size:16px;'><button class='close' data-dismiss='alert'>&times;</button><strong>¡Excelente!</strong> El Registro se guardó correctamente</div>";
				msj = "<div class='alert alert-success' style='text-align:center; font-size:16px;'><button class='close' data-dismiss='alert'>&times;</button><strong>¡Excelente!</strong>"
				+"El Registro se guardó correctamente. &nbsp;&nbsp;"+"¡Recuerda ir a la sección <b>Proyectos Empleado</b> y dar clic en el botón <b>Ver doc</b> para generar el PDF de tu documento!"
				+"</div>";
				conEnoc.commit();
			}else{
				msj = "<div class='alert alert-danger' style='text-align:center; font-size:16px;'><button class='close' data-dismiss='alert'>&times;</button><strong>¡Atención!</strong> Ocurrió un error al guardar el registro</div>";
			}
		}else{ //modifica
			if(InvResultadoU.updateReg(conEnoc, InvResultado)){
				//msj = "<div class='alert alert-success' style='text-align:center; font-size:16px;'><button class='close' data-dismiss='alert'>&times;</button><strong>¡Buen trabajo!</strong> El registro se editó correctamente</div>";
				msj = "<div class='alert alert-success' style='text-align:center; font-size:16px;'><button class='close' data-dismiss='alert'>&times;</button><strong>¡Excelente!</strong>"
				+"El Registro se guardó correctamente. &nbsp;&nbsp;"+"¡Recuerda ir a la sección <b>Proyectos Empleado</b> y dar clic en el botón <b>Ver doc</b> para generar el PDF de tu documento!"
				+"</div>";
				conEnoc.commit();
			}else{
				msj = "<div class='alert alert-warning' style='text-align:center; font-size:16px;'><button class='close' data-dismiss='alert'>&times;</button><strong>¡Atención!</strong> Ocurrió un error al editar el registro</div>";
			}
		}
	}else if (accion.equals("6")){
		// Editar el presupuesto
		InvPresupuesto.setProyectoId(proyectoId);
		InvPresupuesto.setPresupuestoId(presupuestoId);
		if(InvPresupuestoU.existeRegId(conEnoc, proyectoId, presupuestoId)){			
			InvPresupuesto.mapeaRegId(conEnoc, proyectoId, presupuestoId);			
		}
		
	}else if (accion.equals("7")){
		// Borrar un presupuesto
		InvPresupuesto.setProyectoId(proyectoId);
		InvPresupuesto.setPresupuestoId(presupuestoId);		
		if( InvPresupuestoU.existeRegId(conEnoc, proyectoId, presupuestoId) == true ){
			if( InvPresupuestoU.deleteRegId(conEnoc, proyectoId, presupuestoId) ){
				msj = "<div class='alert alert-warning' style='text-align:center; font-size:16px;'><button class='close' data-dismiss='alert'>&times;</button> <strong>¡Bien hecho!</strong> El registro se eliminó correctamente</div>";
				conEnoc.commit();
			} else {
				msj = "<div class='alert alert-danger' style='text-align:center; font-size:16px;'><button class='close' data-dismiss='alert'>&times;</button><strong>¡Atención!</strong> Ocurrió un error al eliminar el registro</div>";
			}
		}else {
			msj = "<div class='alert alert-danger' style='text-align:center; font-size:16px;'><button class='close' data-dismiss='alert'>&times;</button> Este Registro no existe</div>";
			
		}
	}	
	conEnoc.setAutoCommit(true);
	
	// Busca los datos de InvResultado
	if (InvResultadoU.existeReg(conEnoc, proyectoId)){
		InvResultado = InvResultadoU.mapeaRegId(conEnoc, proyectoId);		
	}
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
		<link rel="stylesheet" href="../../js/chosen/chosen.css" />
		<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
		<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
		<script src="../../js/chosen/chosen.jquery.js" type="text/javascript"></script>

		<link rel="stylesheet" href="../../js-plugins/bootstrap-fileupload/bootstrap-fileupload.min.css" />
		<script src="../../js-plugins/bootstrap-fileupload/bootstrap-fileupload.min.js"></script>		
			
		<style type="text/css">
			.file {
				width: 80%;
			}
		</style>
	</head>
	
	<body><br>
	<%=msj %>
		<div class="container-fluid">
			<div class="alert alert-info">
				<h1 style="text-align:center">Formato para el registro y evaluación de proyectos</h1>
				<a href="proyecto" class="btn btn-primary"><i class="icon-chevron-left icon-white"></i><spring:message code='aca.Regresar'/></a>&nbsp;<br>		
			</div> 	
			<hr>
			<ul class="nav nav-tabs nav-justified">
				<li>
					<a href="accion">
<!-- 				  		<img class="icon icons8-1st" width="35" height="35" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAEm0lEQVRoQ+1ZTXYaRxD+ahg82lk5QeA9wzYocAB0ApQTRNrYsBI6QfAJglYQb0ROYHwC4T3YaAt+j9EJIu1kMTOV1wPY8z/dwziJ8jzb6a6ur+qrn64mPPGPnrj++A7g3/Zgbh6ovVnUdKYWM2oELgFU84PjOYNMgCe2hvfzl9V5HuD3AlAbrEo6WecMPiFQSUUhBpsEGlusX847ZVNlr3dtJgC1q9Wh/tn6DUA368GBfX3L0F/Pz8p3qvKUAdT/+HQC5isCDlUPS1rPwB2IzmavXoxV5CoBaAyXvydZnYF3zDzWQKZV4Lsdz934sOnQAZeI6ISAVoKS/Wm7ciELQgqAoEzhsyWsfhIUzIxbBvecg+JYlgJCnvawFnHTI8KPIZngkW0UL2TkSQGoD5dvg8ozcE+MvnWg92UOirKoG0sPVpcJXQKee9cweDRrV8/SPJEKoD781CfwuV847m2Nm3mlQkGxgkOTMAi6nLVfJCaKRAAiYIn5bcAyN7ZRbGa1epxFNzRdTwj0k+88ol+SAjsWwJb3K2+2EbSxDb2Ut/I7hbdnml5PiOxkG3o57sxYAEHquMpnpI0oeCisD2UoF0UnRjyVIgFsK+zK527G62mn0ksLKp81H+0WMZ8CaILxftqpNGX2NwbLHgiiUH75LNbLURU7EkDI+oxb+0CvyVCnMVy2tq2FUPzrpwDApdKDNfel2BgDxgBYrLy9jcN89qFTHSVZb+v669gKrQBAnPPzYHGqEV3tzhS906xdLQd1CAFwq6ZDH33uM/Qf0qzfGCybIFzHglQEsO23/vLpofFRMI5CAIL8E+3BrF0JVeCgojsADL5hopHj6GOdrK9xpAhAyK8Pl2Nv2+EQLj68qvS9Z4cAhDZJ0EcIdDMNAG+gNYZL/nJYBgBhGoWNGfbAcPHRdxlhHE87lYlM9gh5ZU8AYVryfNquHiV6wGc1AFYE72TB7OuBqHictis+o0d4wON2AMENssqLdfsCCMmI0Of/D+DJUag+XMx9HeF/KIhFip61q75pR25pNCo29o2BbGk00EjJFrJvASBTIcvaSuQNIHMr4ZbwwdL0doIyzVzeAEL0YdzOOpXQ8EyunQabtlE8Smvo8qrEUbdBqLTTeVxo9ilke19oNp2gfxrh3k01Ppa5Fu484fYy28876FK9VyhfKd3ucjPMUrpgq7QZUWuzDBKUxyoAzy2jeKwaD2ngNllnfR0cy3PWscruwJjBljKdVGkj1idRZycvdTK3SauLERH96lVCxAQR+tYz/TKrN1yrP1rnzO5o0TftZuY/Z52qfzAQYQUpAJspwbofBLGxEpsgrWc/K7yTBeLKe7RbYO5HDQGE8vZBsSsjTwpAEp0CXhmDaKwRm2vwvXe8XgQ9d5hKYPGaE55y7+TI0CbxRpYWbNsHjlFwEJu2L+2/mPyB6PSbPnDslNikO7sXnFqnKRn3X1jdNgo9GcoEZShRKLhZVOwC2V2XFhEPFUmAxMMIASML+ugff+SLLEJvFjWNqUmMJsRTUnBMDr4ByGTCxCGeqFT0JEPs5YGslMlz33cAeVozi6wn74G/AW8G+k+aMcHEAAAAAElFTkSuQmCC"><br> -->
	 			  		<span>Registro</span>
				  	</a>
				</li>
<!-- 				<li> -->
<%-- 				  	<a href="metodologia?Accion=3&proyectoId=<%=proyectoId%>"> --%>
<!-- 	 			  		<img class="icon icons8--Circled-2" width="35" height="35" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAFEElEQVRoQ+1ZS1IbSRB92WpZ7MycYESEW1sLiwOIE8CcYGBjpBXiBBYnsFghe4PmBJZPgFiPZMRWcgTiBIYdWN2djmp96K7+VTViJoiwturKypf58luEF/6jF64/fgP4vz24Mg+UP4/KJtMOM8oELgJUDoLjIYMmAPccAxfD96XhKsA/CUD59Lpokn3I4F0CFXUUYvCEQF2bzZNhfWOic9b/bSYA5bPrdfPB/gCgkfVi6VzLLpjHw/2NW1152gAqn77vgvmMgHXdy5K+Z+AWRPuDgzddHblaALba449JVmfgKzN3DdDEzvHtgudefDi07oKLRLRLwE6Ckq1+zTpSBaEEQFAm92ALq+/Kgplxw+Cmu5bvqlJAyDPupyJumkT4MyQT3HEK+SMVeUoAKu3xF1l5Bu6I0bLXzJbKRVEW9WLp3m4woUHAa/83DO4MaqX9NE+kAqi0v7cIfBgUjjvH4OqqUqGgWM6lXhgEnQxqbxITRSIAEbDE/EWyzJVTyFezWj3OojOaTnsEehu4j+ivpMCOBTDn/bU/2wjaOAWzuGrlFwrP75z4PSGyk1MwN+LujAUgU8dTXpE2QpH81H7rulw2DPIq7r/vrYs0Pov/o+jEiKdSJIB5hb0OXMg47tetZpIScxp8JNBeOLPglgit/oF1nAZk63TcBEEUyuXPZnMjqmJHAghZn3HjrJnlJOrMqvP0PNwDhdTt9WvWdqoh7u1hIMXGGDAGwOja39u4zPvf6qVO0qWhVMsQlOkx0TrAe35eq8h7dzraM4jOFneK3mlQK23IOoQAeFXTpcuA+wrmH4nWnzV1S8qJijyoWcuit3U6roJw/qhM8P/YGvFg/wjoYfCmnLpDAGT+ycpEXRY6E5H6KqfjyZISjIt+3aqmxUKlPe762w6XcPTtwGr5z4UAhA4p0Ofdp3HDcB/bDDvHDdlSlfb4xzIlKwII0yjsubAH2qPLQCAytvt1q5dmraT/wxRKr7BCnnwO4GG/VtpM9MBWe8xpvNMFsyUZJS4lynKj4rFfswJGj/BAEIB8QFf5Snt05q8LSUUpMr4kg/6nALzYYIgZwvsxWLuPkhmhDcCOSF0qXogIwDuHzbLO/JuJQpX2aBjoCDMEcaTyin2U3zjh4OerQa0U2HasJI36L12V8kJmtjQqNVIqhWwBQHa5TgcbRctMhSxLKyEun7fB54ti9VTl56sb/VZCKBMo+wDSmi/RfufIvpSGny4R4rdvrtdOxBbIEH0YN4O6FVqeqbXT4IlTyG/GNXThiqmQpxLmi6hpEDrttO5As2oATx5oPBpJ2whvNjV4O2oTMV+PSMvcZC/YMCdRNUGOpVkB1BwpvaCcLbO0BmwF4iR+kmWRoL1WER2hXchvr3ozETeScta1ysJUMYutWDpl8UIUbdKos7gndTM3S6ujDhH97VdOxITYMtivzJOs3vCs/tM+ZPZWi4FtNzP/M6iXQtsN2UBKADxu3k9bMoh5hzkBGU3nVe6rKhBP3k9nB8ytqDW9UN5ZyzdU5CkBSKKT5JUuiLoG8WQKvvOv1/Og1y5TESxec8Jb7seWW21a06KQX8n5A0dHXsRm4b4E/g5Ee8/6wLFs2rwU6zTlrXVWECLPO4VcU4UymWIgTrFZD+Q0PFpEPFQkARIPIwR0bJgdnSFnpQD8wkQqNJiqxKhCPCXJa3LwFUATJvRc4t6q3ha0gjgrRZ7z3G8Az2ldFdkv3gO/AKBOJl4mGfklAAAAAElFTkSuQmCC"/><br> -->
<!-- 	 			  		<span>Metodología</span> -->
<!-- 				  	</a> -->
<!-- 				</li>			 -->
<!-- 				<li class="active"> -->
<!-- 				  	<a href="#"> -->
<!-- 	 			  		<img class="icon icons8-3-en-círculo-" width="35" height="35"	 			  		 -->
<!-- 	 			  		src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAFKElEQVRoQ+1ZTXLaSBR+T4jgXZwTDFQFtoMD+8EnsOcEsTeJWBmfIOQEI68gszFzguATmNlDjLeQKvAJBu/s0OKlWkK41frplozH5apoq9br9733vV8hvPAHX7j+8AvAc3twax6o/j2pmoQHRFBFoCIAVoPgaEyAcwAaOAb8O/5QGW8D/KMAVDuzoonshIAOEbCYRiECmiNgn5F5Nm6W5mm+Fc9mAlA9n+2a9+wTALSyXix9Z7OC+Xl8XFqklZcaQO3L90MgOkeA3bSXJZ0ngAUgHo8+vu2nkZsKQL07/SvJ6gRwQUR9A3DOcrTwee7Gh4O7K6AiIh4iwEGCkvbQKp/qgtACwCmTu2fc6oeyYCK4IaD2aiff16UAl2fcLXnctBHht5BMoJ5TyJ/qyNMCUOtOv8rKE8AtEthsx7R1LoqyqBtLd6xFCC0EeC2eIaDeyKocqzyhBFDrfrcR6CQoHG4dgxrbSoWcYrkVDsIg8GxkvU1MFIkAeMAi0VfJMtdOId/IavU4i3o0XQ4Q8PfAfYh/JgV2LIA172dituG0cQpmcdvK+wqv75yLnuDZySmYpbg7YwHI1HGV16SNX5VFSzKkCx3KRdGJIJ5KkQDWFXYWcDfB52Gz3E4KKlfxFfJU24g5N2AGnaqA1DvTNiDwQrl5GJmlqIodCSBkfYIbZ8esJlFnbblLVYFTUYJr7FLpjo0DKTbGgDEAJjOxt1kRHX9rVnpJ1q93p5ei5QnoGgn9qtoAhD/875Mo4Z9515kcGYjnD9/QfGRVSrIOIQBrGlwF3Fcw36gCt96d0uayCI/VO9OBD4I3clHKiHeu+63/AnoYtCfTLwRA5h9vD0ZWOVSBA5fxrhTY0QaAgWM59clyh1ZZowZN+2LbsUI4/faxbIt3h4TUutJHGvRRVUv+XgTA6TWyKtK8EJYSplHYmGEPdCdXgWGEYH/YLA90lFQUqatNXGlktDVoHjs8ttYPjYdWZS/RAyKX+UEWwTsVGDcNG+w9kNtyVwn4lOa130T0z6hZ2dAtSVZUPMrUi/DAQzBy4TpclZWod6aS5bwTaZT3ZcoGfVYALgjNLnNrALJQKJQO71grUFk1YyAThWrdyTjQEW4hiDmgWmfSQ8T3nhdgMbLKb1SxJFMxKns9Sxp1k4NGccyWRqVGSqeQyUUqSrnQmZjmTPRKqCbpFLIsrcS7L9OWQcC7UC/bSIHK02oO2dUmlQLcjqxy4lYjcyvh8XU6FztBVTMXPYi4iyt/YRVorzM1cwQ3o2Y5tDzTa6eB5k4hv5fU0EWNn1FBygNRNZJGTYOQpp1+1EDjoC22zg+08rYYqqFo3UI8bqBxaSRtI9xBxKB91TTFv12vS6pk4C6uaCEuuVSpM2owSj1S+krk7lmqAVulnOp9lkVC6rUKAI1ZIb+vGnBUysrvvayzvJTX8pR1reJfELPY0qaTDpC4eVonWymnIrkNEIJygQg2e2WeZfWGa/Uf7ITIXS0G6oJu56oFwNsSLG2/lxGtyudbQKPtvMpd6AJx5f1wDoDIjtpicOWdnXxLR54WgCQ6BcFAHxD7BtJ8CXQrrtfzgK9XhEUg/jcnvOV+8Kx6HyremQqASyfvB0dPXsTqcD3pDN/8AeLRk/7g8BXw0p3TlrfWWUHwYHUKubYOZeQ7UntAFOA1aU7LpUXEj4pEixPcIECPgdn733/yRSnGU6FB2EDie1EqhtbkQNcAOCeEwQppoFPRdTz6KA/oXPDUZ34BeGoLq+S/eA/8BKOUNF73TsOBAAAAAElFTkSuQmCC" -->
<!-- 	 			  		><br> -->
<!-- 				  		<span>Recursos</span> -->
<!-- 				  	</a> -->
<!-- 				</li> -->
				<li>
					<a href="resultados">
<!-- 	 			  		<img class="icon icons8-4-en-círculo-" width="35" height="35"  -->
<!-- 	 			  		src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAE70lEQVRoQ+1ZTXLaWBD+WojgXTwnGFwV2A4O7AefwJ4TjL1JYGV8giEnGLyCzMbMCUJOYLIeiOUtShXkBLF3TpDo1JMEJT39PQk8rlRFS9B7r7/ur7/u1yL84A/94PbjJ4CnjuDOIlD7Z1bTmY6ZUSNwGaBaEBwbDFoAPLY1fDBeVY1dgN8KQK0/L+tknTP4hEDlLAYxeEGgkcX6pdE+WGRZ6383F4Da1Xxf/2r9BaCT92BpXc8q6W+Ms4O7rPtlBlB/++kEzFcE7Gc9LOl9Bu5AdDZ9/WKUZd9MABoD8+8krzPwnplHGmhhFfhuzXMnP2zaX4HLRHRCwHGCkb1Jq3KhCkIJgKBM4aslvH4ib8yMzwzurvaKI1UKiP20h6XImy4Rfg3tCR7apeKFyn5KAOoD851sPAP3xOhZe3pP5aAojzq59GB1mNAh4Ln/HQYPp63qWVokUgHUB596BD4Pbo57W+PmrqRQUKywonEYBF1OWy8ShSIRgEhYYn4neebWLhWbeb0e51GXpssxgX4LnEf0R1JixwLweD/3q42gjV3Sy7s2fm2wd+bCHwmhTnZJP4g7MxaATB3H+By0EfQogjb8/u9V5UMSr6PoxIinUiQAr8LOAwcx3kzalW5aUvn/d+RzRTf+3yatSmreNfpmFwRRKDePxfpBVMWO3CzkfcZne0+vZaVOYzC7kXsiFQAOlR4sIyCxMQ6MATCb+3ubFfPZx3Z1mMX7L9+aHY0hCl/gUQEgFrzsz041oqv1YtE7TVvVA3m/EICosFsl/Zcs3hcULJB1E9VuqALw+q0vARppfChLdwiAzD/RHkxblVAFTopGY2BeA2hGvaMKQKytD8yRv+1YES4+vq70/PuGAIQWZaRPoHYwXMUh/L4+NAuAMI3CzgxHQE48xtGkXRmr8F+uHUI5dFjDvAAafbMJgoim97AxaVUPEyPQGJicxrs4MPX+bEhEfzr/e6rR6JvjvABUZDgiAkEAqiH3e0t0qNN2xbmhbQPAWS85VLZnJwC8PuZmI70+2j05ACtCumQK+ZWLmf+dtqun63e2AZCLQvXBzAh0hClJHG472ABoc7dliClF4PrpCMKkVTlKEwY5iRl8O21VA9OOrWU0rBRpZrn/q+RWPhmVGqm0QvaYAHIVsqythHctlIZYPuUGn26k1ZVXhzpptSV3K+GU8L658HeCeZo5XxIHWmMV6oi1Ifr4pDmxkLk9SPAeLDpBu1Q8zNLQbQMg6ja4Loxyhj3qhcYrZJkjsPWFJjoKuLM1Pso6iXBkFtZmbprKfXdCcR28i2e8UgoAeS7YagIa/1aeQULmsQrAhlUqHuXJhySAruosr+UrKOcdq6wPixls5aJTHABvEhGgjau4Ww62NiD8bbL3o5jXEKFnPdMv80bD8fo365zZGS0Gpt1yTxUHPnXEscmHh2UvUJA2QHgB0rr2s8J7VSAO17/Zx2DuRd2bhfH2XrGjsp8SgCQ6+T3DwAhEI414sQTf+8frYri1YiqDxdec8JR7vY8KbVILWVKyeR84hvIgdlsFEpM/EJ0+6geOtZGu3NldeWqdF4Twul0qdFUoo1SJVQ1x5z92x6FFxIeKpH3EtZOAoQV9+L9/5IsyTEihxtQkFvMgLofG5OBbgBZMGK+Ix1kr+lYqpBqRp3gvkwo9hYFpZ/4EkOahx/7/O2t79U8rziZVAAAAAElFTkSuQmCC"/><br> -->
				  		<span>Documentos</span>
				  	</a>
				</li>
			</ul>
			<hr>
			<h2><small class="text-muted">Proyecto N° <%=proyectoId %> <%=accion %></small></h2> 
			<form name="cronograma" id="cronograma" action="resultados" method="post">
  				<input type="hidden" name="proyectoId"  value="<%=proyectoId%>"/>
				<input type="hidden" name="Accion"/>
				
				<div class="panel panel-info">
					<div class="panel-heading"><h3>Cronograma<small class="text-muted fs-5">( Refleja la duración de la investigación en orden cronológico y determina con precisión las actividades a realizar )</small></h3></div>
	  				<div class="panel-body">
	  					<div class="col-md-12">
	  						<div id="crono" class="crono">
	  							<button type="button" id="btnNew" class="btn btn-primary" onclick="nuevaActividad()"><i class="glyphicon glyphicon-plus"></i> Nueva</button>&nbsp;
	  							<input type="hidden" name="actividadId" id="actividadId" value="<%=InvActividad.getActividadId()%>"/>&nbsp;
	  							<strong>Actividad</strong>&nbsp; 
	  							<input type="text" id="actividadNombre" class="input-xlarge" name="actividadNombre" placeholder="Agregar actividad..." value="<%=InvActividad.getActividadNombre()=="-"?"":InvActividad.getActividadNombre() %>" required/>&nbsp;
	  							<strong>Fecha Inicio</strong>&nbsp;
	  							<input class="fecha input-input-small" type="text" data-date-format="dd/mm/yyyy" id="fechaIni" name="fechaIni" value="<%=InvActividad.getFechaIni()=="-"?"":InvActividad.getFechaIni() %>" required/>&nbsp;
	  							<strong>Fecha Final</strong>
	  							<input class="fecha input-input-small" type="text" data-date-format="dd/mm/yyyy" id="fechaFin" name="fechaFin" value="<%=InvActividad.getFechaFin()=="-"?"":InvActividad.getFechaFin() %>" required/>&nbsp;&nbsp;
	  							<button type="button" id="btnAdd" class="btn btn-success" onclick="javascript:guardarAct()"><i class="glyphicon glyphicon-save"></i> Guardar</button><br><br>	  								  						
	  						</div>
	  						<table class="table table-bordered table-hover table-responsive">
	  							<tr class="info">
	  								<th width="2%"><strong>N°</strong></th>
	  								<th width="8%"><strong>Opciones</strong></th>
	  								<th width="45%"><strong>Actividad</strong></th>
	  								<th width="8%"><strong>Fecha Inicio</strong></th>
	  								<th width="8%"><strong>Fecha Fin</strong></th>
	  							</tr>
<%
								ArrayList<aca.investiga.InvActividad> listActividad = InvActividadU.getListActividad(conEnoc, proyectoId, " ORDER BY ACTIVIDAD_ID");
								int row = 0;
								for (aca.investiga.InvActividad actividad: listActividad){
									row++;%>
								<tr>
									<td class=""><%=row %></td>
									<td>
										<a class="btn btn-sm btn-info" title="Editar" data-toggle="modal" id="edit" href="javascript:editar('<%=actividad.getActividadId()%>','<%=proyectoId%>');"><i class="icon-white icon-pencil"></i></a>
										<button class="btn btn-sm btn-danger" title="Borrar" id="btnDel" onclick="javascript:borrar('<%=actividad.getActividadId()%>','<%=proyectoId%>')"><i class="icon-white icon-trash"></i></button>
									</td>
									<td><%=actividad.getActividadNombre() %></td>
									<td><%=actividad.getFechaIni() %></td>
									<td><%=actividad.getFechaFin() %></td>
								</tr>
<%
								}
%>
	  						</table>
	  						<input type="hidden" value="<%=row %>" id="numrow"/>
	  					</div>	  	
	  				</div>
	  			</div>
  			</form>
  			
  			<div class="panel panel-info">
  				<div class="panel-heading"><h3>Presupuesto</h3></div>
  				<div class="panel-body">
  				<div class="col-md-12">					
  					<form name="presupuesto" id="presupuesto" action="resultados" method="post">
  					<input type="hidden" name="proyectoId"  value="<%=proyectoId%>"/>
  					<input type="hidden" name="Accion"/>
					<input type="hidden" name="presupuestoId" id="presupuestoId" value="<%=InvPresupuesto.getPresupuestoId()%>"/>
					<button id="btnAddPres" class="btn btn-primary" onclick="javascript:crearPres()"><i class="glyphicon glyphicon-plus"></i> Nuevo</button>&nbsp;						  					
		  			<strong>Presupuesto</strong>&nbsp;
		  			<input class="input-xlarge" type="text" id="presupuestoNombre" name="presupuestoNombre" placeholder="Agregar presupuesto..." value="<%=InvPresupuesto.getPresupuestoNombre()%>" required/>&nbsp;
		  			<strong>Tipo</strong>&nbsp;
		  			<select id="tipo" name="tipo" required>
			  			<option value="-">Seleccione...</option>
			  			<option value="C" <%=InvPresupuesto.getTipo().equals("C")?" SELECTED":""%>>Corriente</option>
			  			<option value="I" <%=InvPresupuesto.getTipo().equals("I")?" SELECTED":""%>>Inversión</option>
			  		</select>&nbsp;
		  			<strong>Monto</strong>
		  			<span><strong>$ </strong><input type="number" id="monto" name="monto" style="width:90px" onchange="sumar()" value="<%=InvPresupuesto.getMonto()%>" required/></span>&nbsp;&nbsp;
		  			<button type="button" id="btnGuardarPres" class="btn btn-success" onclick="javascript:guardarPres()"><i class="glyphicon glyphicon-save"></i> Guardar</button><br><br>		  					  						  				
		  			<table class="table table-bordered table-hover table-responsive">
	  					<tr class="info">
	  						<th width="2%"><strong>N°</strong></th>
	  						<th width="2%"><strong>Opciones</strong></th>
	  						<th width="8%"><strong>Tipo</strong></th>
	  						<th width="40%"><strong>Presupuesto</strong></th>
	  						<th width="8%"><strong>Monto Corr.</strong></th>
	  						<th width="8%"><strong>Monto Inv.</strong></th>
	  					</tr>
<%
							ArrayList<aca.investiga.InvPresupuesto> listPres = InvPresupuestoU.getListPresupuesto(conEnoc, proyectoId, " ORDER BY PRESUPUESTO_ID");
							int num = 0;
							int totalC = 0;
							int totalI = 0;
							for (aca.investiga.InvPresupuesto presupuesto : listPres){
								num++;
								String tipo = "-";
								String corriente = "";
								String inversion = "";
								
								if (presupuesto.getTipo().equals("C")){
									tipo = "Corriente";
									corriente = "$ "+presupuesto.getMonto();
									totalC += Integer.parseInt(presupuesto.getMonto());
								}
								if (presupuesto.getTipo().equals("I")){
									tipo = "Inversión";
									inversion = "$ "+presupuesto.getMonto();
									totalI += Integer.parseInt(presupuesto.getMonto());
								}
%>
						<tr>
							<td><%=num %></td>
							<td>
								<a class="btn btn-sm btn-info" title="Editar" href="javascript:editarPres('<%=presupuesto.getPresupuestoId()%>','<%=proyectoId%>')"><i class="icon-white icon-pencil"></i></a>
								<button class="btn btn-sm btn-danger" title="Borrar" id="borrarPres" onclick="javascript:deletePres('<%=presupuesto.getPresupuestoId()%>','<%=proyectoId%>')"><i class="icon-white icon-trash"></i></button>
							</td>
							<td><%=tipo %></td>
							<td><%=presupuesto.getPresupuestoNombre() %></td>
							<td><%=corriente %></td>
							<td><%=inversion %></td>
						</tr>
<%
							}
							int suma = totalC + totalI;
%>
						<tr>
							<td colspan="4" style="text-align:right"><h4>Total General: <%=suma %></h4></td>
							<td><strong id="corr">$ <%=totalC %></strong></td>
							<td><strong>$ <%=totalI %></strong></td>
						</tr>
	  				</table>
	  				<input type="hidden" value="<%=num%>" id="numpres"/>
	  				</form>
  				</div>
  				</div>
  			</div>
  			<form name="resultado" id="resultado" action="resultados" method="post">
  			<input type="hidden" name="proyectoId" value="<%=proyectoId%>"/>
			<input type="hidden" name="Accion"/>
  			<div class="panel panel-info">
  				<div class="panel-heading"><h3>Infraestructura disponible</h3></div>
  				<div class="panel-body">  						
 					<textarea name="infraestructura" id="infraestructura" rows="3" cols="100" placeholder="Escribe todas tus infraestructuras disponibles"><%=InvResultado.getInfraestructura()=="-"?"":InvResultado.getInfraestructura() %></textarea><br><br>  					
  				</div>
  			</div>  			
  			<div class="panel panel-info">
  				<div class="panel-heading"><h3>Referencias</h3></div>
  				<div class="panel-body">  					 					
  					<label for="bibliografia"><strong>Bibliográfica</strong></label>
  					<textarea id="bibliografia" name="bibliografia" rows="3" cols="100" placeholder="Añade aquí tus bibliografías. Utiliza el estilo que cada escuela o facultad maneja."><%=InvResultado.getBibliografia()=="-"?"":InvResultado.getBibliografia() %></textarea><br><br>  					
  				</div>
  			</div>
  			<div class="alert alert-info">
  				<button type="button" id="resultado" class="btn btn-success" onclick="javascript:guardarRes();"><i class="glyphicon glyphicon-save"></i> Guardar</button>
  			</div>
  			</form>
		</div>
</body>
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script src="../../js/chosen/chosen.jquery.js" type="text/javascript"></script>
<script type="text/javascript">
	function guardar(){
		if(document.formaEnviar.archivo.value != ""){
			document.formaEnviar.btnGuardar.disabled = true;
			document.formaEnviar.btnGuardar.value = "Guardando...";			
			document.getElementById("formaEnviar").submit();
		}else
			alert("¡Selecciona un documento!");
	}

//guardar resultado
	function guardarRes(){
		if(document.resultado.infraestructura.value != ""
		&& document.resultado.bibliografia.value != ""){	
			document.resultado.Accion.value = "5";
			document.resultado.submit();			
		}else {
			alert("¡Completa todos los campos!");
		}
	}
//Agregar presupuesto
	btnAddPres = document.getElementById('btnAddPres');
	
	function crearPres(){
		divPres = document.getElementById('pres');
		divPres.removeAttribute('style');
	}
	
	if (document.presupuesto.numpres.value >= 10) {
		btnAddPres.setAttribute('disabled','disabled');
	}
	
	function editar(actId, proyectoId){ 		
		document.location.href="resultados?Accion=3&actividadId="+actId+"&proyectoId="+proyectoId;
	}
	
	function guardarPres(){
		if(document.presupuesto.presupuestoNombre.value != ""
		&& document.presupuesto.tipo.value != ""
		&& document.presupuesto.monto.value != ""){
			document.presupuesto.Accion.value = "4";
			document.presupuesto.submit();
		} else {
			alert("¡Completa todos los campos!");
		}
	}
	
	function deletePres(presupuestoId, proyectoId){
		if (confirm("¿Estás seguro que deseas eliminar éste presupuesto?"+presupuestoId+" - "+proyectoId)){
			location.href="resultados?Accion=7&presupuestoId="+presupuestoId+"&proyectoId="+proyectoId;
		}
	}
	
//Este es el agregar y Cronograma
	jQuery('div.crono > input.fecha').datepicker();
	btnDel = document.getElementById('btnDel');
	btnAdd = document.getElementById('btnAdd');
	divCrono = document.getElementById('crono');	
	
	if (document.cronograma.numrow.value >= 10) {
		btnAdd.setAttribute('disabled','disabled');
	}
	
	function nuevaActividad(){		
		document.cronograma.Accion.value		= "0";
		document.cronograma.actividadId.value	= "0";
		document.cronograma.submit();
	}
	
 	function editarPres(presId, proyectoId){ 		
		document.location.href="resultados?Accion=6&presupuestoId="+presId+"&proyectoId="+proyectoId;
	}
		
	function borrar(actId, proyectoId){
		if (confirm("¿Estás seguro que deseas eliminar esta actividad?")){
			document.location.href="resultados?Accion=2&actividadId="+actId+"&proyectoId="+proyectoId;
		}
	}
	
	function guardarAct(){
		if(document.cronograma.actividadNombre.value != ""
		&& document.cronograma.fechaIni.value != ""
		&& document.cronograma.fechaFin.value != ""){
			document.cronograma.Accion.value = "1";
			document.cronograma.submit();
		
		} else {
			alert("¡Completa todos los campos!");
		}
	}
</script>
</html>
<%@ include file= "../../cierra_enoc.jsp" %>