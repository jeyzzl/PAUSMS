<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="alumno" scope="page" class="aca.alumno.AlumPersonal"/>
<jsp:useBean id="AlumUtil" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="FedVoto" scope="page" class="aca.federacion.FedVoto"/>
<jsp:useBean id="FedVotoU" scope="page" class="aca.federacion.FedVotoUtil"/>
<jsp:useBean id="FedEvento" scope="page" class="aca.federacion.FedEvento"/>
<jsp:useBean id="FedEventoUtil" scope="page" class="aca.federacion.FedEventoUtil"/>

<link href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css" rel="stylesheet">

<script>
	function votar(){		
			document.forma.Accion.value = "1";
			document.forma.submit();
		/*
			if (confirm("¡No has votado por todos los puestos! ¿Deseas grabar tu votación incompleta?")){
				document.forma.Accion.value = "1";
				document.forma.submit();
			}
		*/		
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
		margin: 0 5px 0 0 !important;
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
	
	.graficas{
		width: 50%;
		padding: 30px;
		float: left;
		border: 1px solid #BDBDBD;
	}
</style>

<%
	
	String matricula 	= (String) session.getAttribute("codigoAlumno");
	alumno = AlumUtil.mapeaRegId(conEnoc,matricula);	
		
	ArrayList<aca.federacion.FedEvento> eventos = FedEventoUtil.getListEventosActuales(conEnoc, "");
	
	String eventoId = "3";
	FedEvento.mapeaRegId(conEnoc, eventoId);
	String [] presidente 	= FedEvento.getPresidente().split("-");
	String [] consejero 	= FedEvento.getDesarrollo().split("-");
	String [] financiero 	= FedEvento.getFinanciero().split("-");
	String [] consejera 	= FedEvento.getEjecutivo().split("-");
	String [] secretario 	= FedEvento.getSecretario().split("-");
	
	
	
	String accion = request.getParameter("Accion")==null?"":request.getParameter("Accion");
	if(accion.equals("1")){
		FedVoto.setEventoId(eventoId);
		FedVoto.setCodigoPersonal(matricula);
		FedVoto.setPresidente(request.getParameter("presidente"));
		FedVoto.setDesarrollo(request.getParameter("consejero"));
		FedVoto.setFinanciero(request.getParameter("financiero"));
		FedVoto.setEjecutivo(request.getParameter("consejera"));
		FedVoto.setSecretario(request.getParameter("secretario"));
		FedVoto.setFecha( aca.util.Fecha.getHoy() );
		
		if( FedVotoU.insertReg(conEnoc, FedVoto) ){
			
		}		
	}	
%>



	
<%
if( aca.federacion.FedVotoUtil.existeReg(conEnoc, eventoId, matricula) == false && aca.vista.InscritosUtil.inscrito(conEnoc, matricula) ){%>
	
	<h2>Vota por tus candidatos</h2>

	<hr />
	
<form action="" method="post" name="forma">
	<input type="hidden" name="Accion" />	
	
	<div class="alert alert-info">
		Presidente
	</div>
	
	<div class="candidatos">
		<div class="img-container radio">
			<label>
				<img src="../../foto?Codigo=<%=presidente[0]%>&Tipo=O" alt="" />				
				<input type="radio" name="presidente" id="presidente" value="<%=presidente[0] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, presidente[0]) %>
			</label>
		</div>
		
		<div class="img-container radio">
			<label>
				<img src="../../foto?Codigo=<%=presidente[1]%>&Tipo=O" alt="" />
				
				<input type="radio" name="presidente" id="presidente" value="<%=presidente[1] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, presidente[1]) %>
			</label>
		</div>
		
		<div class="img-container radio">
			<label>
				<img src="../../foto?Codigo=<%=presidente[2]%>&Tipo=O" alt="" />
				
				<input type="radio" name="presidente" id="presidente" value="<%=presidente[2] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, presidente[2]) %>
			</label>
		</div>
		<div class="img-container radio">
			<label>
				<img src="../../foto?Codigo=<%=presidente[3]%>&Tipo=O" alt="" />
				
				<input type="radio" name="presidente" id="presidente" value="<%=presidente[3] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, presidente[3]) %>
			</label>
		</div><div class="img-container radio">
			<label>
				<img src="../../foto?Codigo=<%=presidente[4]%>&Tipo=O" alt="" />
				
				<input type="radio" name="presidente" id="presidente" value="<%=presidente[4] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, presidente[4]) %>
			</label>
		</div>
		<div class="img-container radio">
			<label>
				<img src="../../foto?Codigo=<%=presidente[5]%>&Tipo=O" alt="" />
				
				<input type="radio" name="presidente" id="presidente" value="<%=presidente[5] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, presidente[5]) %>
			</label>
		</div>
		<div class="img-container radio">
			<label>
				<img src="../../foto?Codigo=<%=presidente[6]%>&Tipo=O" alt="" />
				
				<input type="radio" name="presidente" id="presidente" value="<%=presidente[6] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, presidente[6]) %>
			</label>
		</div>
		<div class="img-container radio">
			<label>
				<img src="../../foto?Codigo=<%=presidente[7]%>&Tipo=O" alt="" />
				
				<input type="radio" name="presidente" id="presidente" value="<%=presidente[7] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, presidente[7]) %>
			</label>
		</div>
	</div>		
	
	
	
	<div class="alert alert-info">
		Tesorero
	</div>
	
	<div class="candidatos">
		<div class="img-container radio">
			<label>
				<img src="../../foto?Codigo=<%=financiero[0] %>" alt="" />
				
				<input type="radio" name="financiero" id="financiero" value="<%=financiero[0] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, financiero[0]) %>
			</label>
		</div>
		
		<div class="img-container radio">
			<label>
				<img src="../../foto?Codigo=<%=financiero[1] %>" alt="" />
				
				<input type="radio" name="financiero" id="financiero" value="<%=financiero[1] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, financiero[1]) %>
			</label>
		</div>
		
		<div class="img-container radio">
			<label>
				<img src="../../foto?Codigo=<%=financiero[2] %>" alt="" />
				
				<input type="radio" name="financiero" id="financiero" value="<%=financiero[2] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, financiero[2]) %>
			</label>
		</div>
		<div class="img-container radio">
			<label>
				<img src="../../foto?Codigo=<%=financiero[3] %>" alt="" />
				
				<input type="radio" name="financiero" id="financiero" value="<%=financiero[3] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, financiero[3]) %>
			</label>
		</div>
		<div class="img-container radio">
			<label>
				<img src="../../foto?Codigo=<%=financiero[4] %>" alt="" />
				
				<input type="radio" name="financiero" id="financiero" value="<%=financiero[4] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, financiero[4]) %>
			</label>
		</div>
		<div class="img-container radio">
			<label>
				<img src="../../foto?Codigo=<%=financiero[5] %>" alt="" />
				
				<input type="radio" name="financiero" id="financiero" value="<%=financiero[5] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, financiero[5]) %>
			</label>
		</div>
		<div class="img-container radio">
			<label>
				<img src="../../foto?Codigo=<%=financiero[6] %>" alt="" />
				
				<input type="radio" name="financiero" id="financiero" value="<%=financiero[6] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, financiero[6]) %>
			</label>
		</div>
	</div>	
	
	
	<div class="alert alert-info">
		Secretario
	</div>
	
	<div class="candidatos">
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen.jsp?mat=<%=secretario[0] %>" alt="" />
				
				<input type="radio" name="secretario" id="secretario" value="<%=secretario[0] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, secretario[0]) %>
			</label>
		</div>
		
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen.jsp?mat=<%=secretario[1] %>" alt="" />
				
				<input type="radio" name="secretario" id="secretario" value="<%=secretario[1] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, secretario[1]) %>
			</label>
		</div>
		
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen.jsp?mat=<%=secretario[2] %>" alt="" />
				
				<input type="radio" name="secretario" id="secretario" value="<%=secretario[2] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, secretario[2]) %>
			</label>
		</div>
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen.jsp?mat=<%=secretario[3] %>" alt="" />
				
				<input type="radio" name="secretario" id="secretario" value="<%=secretario[3] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, secretario[3]) %>
			</label>
		</div>
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen.jsp?mat=<%=secretario[4] %>" alt="" />
				
				<input type="radio" name="secretario" id="secretario" value="<%=secretario[4] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, secretario[4]) %>
			</label>
		</div>
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen.jsp?mat=<%=secretario[5] %>" alt="" />
				
				<input type="radio" name="secretario" id="secretario" value="<%=secretario[5] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, secretario[5]) %>
			</label>
		</div>
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen.jsp?mat=<%=secretario[6] %>" alt="" />
				
				<input type="radio" name="secretario" id="secretario" value="<%=secretario[6] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, secretario[6]) %>
			</label>
		</div>
	</div>	
		
		
	<div class="alert alert-info">
		Consejeros
	</div>
	
	<div class="candidatos">
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen.jsp?mat=<%=consejero[0] %>" alt="" />
				<input type="radio" name="consejero" id="consejero" value="<%=consejero[0] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.emp.EmpleadoUtil.getNombreCorto(conEnoc, consejero[0], "") %>
			</label>
		</div>
		
		<div class="img-container radio">
			<label> 
				<img src="https://academico.um.edu.mx/academico/imagen.jsp?mat=<%=consejero[1] %>" alt="" />
				
				<input type="radio" name="consejero" id="consejero" value="<%=consejero[1] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.emp.EmpleadoUtil.getNombreCorto(conEnoc, consejero[1], "") %>
			</label>
		</div>
		
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen.jsp?mat=<%=consejero[2] %>" alt="" />
				
				<input type="radio" name="consejero" id="consejero" value="<%=consejero[2] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.emp.EmpleadoUtil.getNombreCorto(conEnoc, consejero[2], "") %>
			</label>
		</div>
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen.jsp?mat=<%=consejero[3] %>" alt="" />
				
				<input type="radio" name="consejero" id="consejero" value="<%=consejero[3] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.emp.EmpleadoUtil.getNombreCorto(conEnoc, consejero[3], "") %>
			</label>
		</div>
	</div>	
	
	
	<div class="alert alert-info">
		Consejeras
	</div>
	
	<div class="candidatos">
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen.jsp?mat=<%=consejera[0] %>" alt=""  />
				
				<input type="radio" name="consejera" id="consejera" value="<%=consejera[0] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.emp.EmpleadoUtil.getNombreCorto(conEnoc, consejera[0], "") %>
			</label>
		</div>
		
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen.jsp?mat=<%=consejera[1] %>" alt="" />
				
				<input type="radio" name="consejera" id="consejera" value="<%=consejera[1] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.emp.EmpleadoUtil.getNombreCorto(conEnoc, consejera[1], "") %>
			</label>
		</div>
		
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen.jsp?mat=<%=consejera[2] %>" alt="" />
				
				<input type="radio" name="consejera" id="consejera" value="<%=consejera[2] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.emp.EmpleadoUtil.getNombreCorto(conEnoc, consejera[2], "") %>
			</label>
		</div>
		
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen.jsp?mat=<%=consejera[3] %>" alt=""   />
				
				<input type="radio" name="consejera" id="consejera" value="<%=consejera[3] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.emp.EmpleadoUtil.getNombreCorto(conEnoc, consejera[3], "") %>
			</label>
		</div>
	</div>	
	
	<div class="alert alert-info">
		<a href="javascript:votar();" class="btn btn-primary btn-lg"><i class="glyphicon glyphicon-ok"></i> Votar</a>
	</div>

</form>
	
<%}else{%>

	<%
		java.util.HashMap<String, String> presidentes 	= aca.federacion.FedVotoUtil.mapPresidente(conEnoc, eventoId);
		java.util.HashMap<String, String> consejeros 	= aca.federacion.FedVotoUtil.mapDesarrollo(conEnoc, eventoId);
		java.util.HashMap<String, String> financieros 	= aca.federacion.FedVotoUtil.mapFinanciero(conEnoc, eventoId);
		java.util.HashMap<String, String> consejeras 	= aca.federacion.FedVotoUtil.mapEjecutivo(conEnoc, eventoId);
		java.util.HashMap<String, String> secretarios 	= aca.federacion.FedVotoUtil.mapSecretario(conEnoc, eventoId);
	%>
	
	<h2>Resultados de la votación</h2>

	<hr />

	<div class="alert alert-info">Personas que han votado: <%= aca.federacion.FedVotoUtil.participantes(conEnoc, eventoId) %></div>
	
	<script src="highcharts/highcharts.js"></script>
	<script src="highcharts/exporting.js"></script>

	<div class="graficas" id="presidente" style="min-width: 310px; height: 400px; margin: 0 auto; border-bottom:0; border-right:0;"></div>
	
	<script>
		jQuery(function () {
			jQuery('#presidente').highcharts({
	            chart: {
	                type: 'column'
	            },
	            title: {
	                text: 'Presidente'
	            },
	            
	            xAxis: {
	                categories: [
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, presidente[0]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, presidente[1]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, presidente[2]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, presidente[3]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, presidente[4]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, presidente[5]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, presidente[6]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, presidente[7]) %>',
	                ]
	            },
	            yAxis: {
	                min: 0,
	                title: {
	                    text: 'Votaciones'
	                }
	            },
	            tooltip: {
	                headerFormat: '',
	                pointFormat: '<tr>' +
	                    '<td style="padding:0"><b>{point.y:.1f}</b></td></tr>',
	                footerFormat: '</table>',
	                shared: true,
	                useHTML: true
	            },
	            plotOptions: {
	                column: {
	                    pointPadding: 0.2,
	                    borderWidth: 0
	                }
	            },
	            series: [{
	                name: 'Presidente',
	                data: [<%=presidentes.get(presidente[0]) %>, <%=presidentes.get(presidente[1]) %>, <%=presidentes.get(presidente[2]) %>, <%=presidentes.get(presidente[3]) %>
	                , <%=presidentes.get(presidente[4]) %>, <%=presidentes.get(presidente[5]) %>, <%=presidentes.get(presidente[6]) %>, <%=presidentes.get(presidente[7]) %>]
	    
	            }]
	        });
	    });
	</script>
	
	
	<div class="graficas" id="financiero" style="min-width: 310px; height: 400px; margin: 0 auto; border-bottom:0; border-right:0;"></div>
	
	<script>
		jQuery(function () {
			jQuery('#financiero').highcharts({
	            chart: {
	                type: 'column'
	            },
	            title: {
	                text: 'Tesorero'
	            },
	            
	            xAxis: {
	                categories: [
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, financiero[0]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, financiero[1]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, financiero[2]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, financiero[3]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, financiero[4]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, financiero[5]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, financiero[6]) %>',
	                ]
	            },
	            yAxis: {
	                min: 0,
	                title: {
	                    text: 'Votaciones'
	                }
	            },
	            tooltip: {
	                headerFormat: '',
	                pointFormat: '<tr>' +
	                    '<td style="padding:0"><b>{point.y:.1f}</b></td></tr>',
	                footerFormat: '</table>',
	                shared: true,
	                useHTML: true
	            },
	            plotOptions: {
	                column: {
	                    pointPadding: 0.2,
	                    borderWidth: 0
	                }
	            },
	            series: [{
	                name: 'Tesorero',
	                data: [<%= Integer.toString(Integer.parseInt(financieros.get(financiero[0])) +10) %>, <%=financieros.get(financiero[1]) %>, <%=financieros.get(financiero[2]) %>, <%=financieros.get(financiero[3]) %>
	                , <%=financieros.get(financiero[4]) %>, <%=financieros.get(financiero[5]) %>, <%=financieros.get(financiero[6]) %>]
	    
	            }]
	        });
	    });
		
	</script>
	
	
	<div class="graficas" id="secretario" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
	
	<script>
		jQuery(function () {
			jQuery('#secretario').highcharts({
	            chart: {
	                type: 'column'
	            },
	            title: {
	                text: 'Secretario'
	            },
	            
	            xAxis: {
	                categories: [
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, secretario[0]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, secretario[1]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, secretario[2]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, secretario[3]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, secretario[4]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, secretario[5]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, secretario[6]) %>',
	                ]
	            },
	            yAxis: {
	                min: 0,
	                title: {
	                    text: 'Votaciones'
	                }
	            },
	            tooltip: {
	                headerFormat: '',
	                pointFormat: '<tr>' +
	                    '<td style="padding:0"><b>{point.y:.1f}</b></td></tr>',
	                footerFormat: '</table>',
	                shared: true,
	                useHTML: true
	            },
	            plotOptions: {
	                column: {
	                    pointPadding: 0.2,
	                    borderWidth: 0
	                }
	            },
	            series: [{
	                name: 'Secretario',
	                data: [<%=secretarios.get(secretario[0]) %>, <%=secretarios.get(secretario[1]) %>, <%=secretarios.get(secretario[2]) %>, <%=secretarios.get(secretario[3]) %>
	                , <%=secretarios.get(secretario[4]) %>, <%=secretarios.get(secretario[5]) %>, <%=secretarios.get(secretario[6]) %>]
	    
	            }]
	        });
	    });
	</script>
	
	<div class="graficas" id="consejero" style="min-width: 310px; height: 400px; margin: 0 auto; border-bottom:0; "></div>
	
	<script>
		jQuery(function () {
			jQuery('#consejero').highcharts({
	            chart: {
	                type: 'column'
	            },
	            title: {
	                text: 'Consejero'
	            },
	            
	            xAxis: {
	                categories: [
						'<%=aca.emp.EmpleadoUtil.getNombreCorto(conEnoc, consejero[0], "") %>',
	                    '<%=aca.emp.EmpleadoUtil.getNombreCorto(conEnoc, consejero[1], "") %>',
	                    '<%=aca.emp.EmpleadoUtil.getNombreCorto(conEnoc, consejero[2], "") %>',
	                    '<%=aca.emp.EmpleadoUtil.getNombreCorto(conEnoc, consejero[3], "") %>',
	                ]
	            },
	            yAxis: {
	                min: 0,
	                title: {
	                    text: 'Votaciones'
	                }
	            },
	            tooltip: {
	                headerFormat: '',
	                pointFormat: '<tr>' +
	                    '<td style="padding:0"><b>{point.y:.1f}</b></td></tr>',
	                footerFormat: '</table>',
	                shared: true,
	                useHTML: true
	            },
	            plotOptions: {
	                column: {
	                    pointPadding: 0.2,
	                    borderWidth: 0
	                }
	            },
	            series: [{
	                name: 'Consejero',
	                data: [<%=consejeros.get(consejero[0]) %>, <%=consejeros.get(consejero[1]) %>, <%=consejeros.get(consejero[2]) %>, <%=consejeros.get(consejero[3]) %>]
	    
	            }]
	        });
	    });
	</script>
	
	
	<div class="graficas" id="consejera" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
	
	<script>
		jQuery(function () {
			jQuery('#consejera').highcharts({
	            chart: {
	                type: 'column'
	            },
	            title: {
	                text: 'Consejera'
	            },
	            
	            xAxis: {
	                categories: [
						'<%=aca.emp.EmpleadoUtil.getNombreCorto(conEnoc, consejera[0], "") %>',
						'<%=aca.emp.EmpleadoUtil.getNombreCorto(conEnoc, consejera[1], "") %>',
						'<%=aca.emp.EmpleadoUtil.getNombreCorto(conEnoc, consejera[2], "") %>',
						'<%=aca.emp.EmpleadoUtil.getNombreCorto(conEnoc, consejera[3], "") %>',
	                    
	                ]
	            },
	            yAxis: {
	                min: 0,
	                title: {
	                    text: 'Votaciones'
	                }
	            },
	            tooltip: {
	                headerFormat: '',
	                pointFormat: '<tr>' +
	                    '<td style="padding:0"><b>{point.y:.1f}</b></td></tr>',
	                footerFormat: '</table>',
	                shared: true,
	                useHTML: true
	            },
	            plotOptions: {
	                column: {
	                    pointPadding: 0.2,
	                    borderWidth: 0
	                }
	            },
	            series: [{
	                name: 'Consejera',
	                data: [<%=consejeras.get(consejera[0]) %>, <%=consejeras.get(consejera[1]) %>, <%=consejeras.get(consejera[2]) %>, <%=consejeras.get(consejera[3]) %>]
	    
	            }]
	        });
	    });
	</script>

<%} %>




<%@ include file= "../../cierra_enoc.jsp" %>