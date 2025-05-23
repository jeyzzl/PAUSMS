package aca.catalogo.spring;
/*

import org.springframework.data.domain.Sort;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
*/
//@Repository
//public interface CatNivelRepository extends CrudRepository<CatNivel,Integer> {
public interface CatNivelRepository {
	/*
    List<CatNivel> findAll( Sort sort);
    List<CatNivel> findAllByOrderByOrdenAsc();
    
    @Query("SELECT * FROM CAT_NIVEL WHERE NIVEL_ID = :nivelId")
    List<CatNivel> findByNivelId(@Param("nivelId") String nivelId);
    */
    /*
    @Modifying
    @Query("UPDATE person SET first_name = :name WHERE id = :id")
    boolean updateByFirstName(@Param("id") Long id, @Param("name") String name);
    */
}
