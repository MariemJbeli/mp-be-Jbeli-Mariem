package soa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import soa.entities.Categorie;
import soa.entities.Produit;


public interface CategorieRepository extends JpaRepository<Categorie, Long> { 
	List<Categorie> findAll();
}
