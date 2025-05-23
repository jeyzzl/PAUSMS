/*
 * Created on 14/10/2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package aca.plan;

import java.util.Date;

/**
 * @author Pedro
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MateriaVO {
	String nombre_curso, plan_id, curso_id,estado, curso_clave;
	Date fCreada;
	int ciclo,ht,hp,notaAprobatoria,tipoCursoId;
	float creditos;

    public int getCiclo() {
        return ciclo;
    }
    public void setCiclo(int ciclo) {
        this.ciclo = ciclo;
    }
    public float getCreditos() {
		return creditos;
	}
	public void setCreditos(float creditos) {
		this.creditos = creditos;
	}
	public String getCurso_id() {
        return curso_id;
    }
    public void setCurso_id(String curso_id) {
        this.curso_id = curso_id;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public Date getFCreada() {
        return fCreada;
    }
    public void setFCreada(Date creada) {
        fCreada = creada;
    }
    public int getHp() {
        return hp;
    }
    public void setHp(int hp) {
        this.hp = hp;
    }
    public int getHt() {
        return ht;
    }
    public void setHt(int ht) {
        this.ht = ht;
    }
    public String getNombre_curso() {
        return nombre_curso;
    }
    public void setNombre_curso(String nombre_curso) {
        this.nombre_curso = nombre_curso;
    }
    public int getNotaAprobatoria() {
        return notaAprobatoria;
    }
    public void setNotaAprobatoria(int notaAprobatoria) {
        this.notaAprobatoria = notaAprobatoria;
    }
    public String getPlan_id() {
        return plan_id;
    }
    public void setPlan_id(String plan_id) {
        this.plan_id = plan_id;
    }
    public int getTipoCursoId() {
        return tipoCursoId;
    }
    public void setTipoCursoId(int tipoCursoId) {
        this.tipoCursoId = tipoCursoId;
    }
	public String getCurso_clave() {
		return curso_clave;
	}
	public void setCurso_clave(String curso_clave) {
		this.curso_clave = curso_clave;
	}
}