Vue.component('saludo',{
	template:
	`
	<div>
	<h5>{{saludo}}</h5>
	<span>2.0</span>
	</div>
	`,
	data(){
		return {
			saludo:'Saludos desde componente AA'
		}
	}
})