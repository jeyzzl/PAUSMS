const app = new Vue({
	el:'#app',
	data:{
		titulo:'saludos desde vue',
		niveles:[
			{nombre:'Prepa',cantidad:2},
			{nombre:'Lic',cantidad:3},
			{nombre:'Mae',cantidad:5},
			{nombre:'Esp',cantidad:7},
			{nombre:'Doc',cantidad:0}
		],
		nuevoNivel:'',
		total:0
	},
	methods:{
		agregarNivel(){
			this.niveles.push({
				nombre:this.nuevoNivel,cantidad:0
			});
			this.nuevoNivel='';
		}
	},
	computed:{
		sumarNiveles(){
			this.total=0;
			for (nivel of this.niveles){
				this.total = this.total + nivel.cantidad;
			}
			return this.total;
		}
	}
})