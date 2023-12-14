package soa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import soa.entities.Categorie;
import soa.entities.Produit;

import soa.repository.CategorieRepository;


@RestController
@RequestMapping("/categories")
@CrossOrigin(origins = "http://localhost:4200")
public class CategorieRESTController {
	 private  CategorieRepository categorieRepo;
	 
	 @Autowired  // Add this annotation
	    public CategorieRESTController(CategorieRepository categorieRepo) {
	        this.categorieRepo = categorieRepo;
	    }


    @GetMapping(value = "/", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public List<Categorie> getAllCategories() {
        return categorieRepo.findAll();
    }

    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public Categorie getCategorie(@PathVariable Long id) {
        Categorie p = categorieRepo.findById(id).get();
        return p;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteCategorie(@PathVariable Long id) {
        // Vérifiez si la catégorie avec l'ID spécifié existe
        if (!categorieRepo.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Supprimez la catégorie
        categorieRepo.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping(
            // spécifier le path de la méthode
            value = "/"  ,
            //spécifier le format de retour
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public Categorie saveCategorie(@RequestBody Categorie p)
    {
        return categorieRepo.save(p);
    }

    @PutMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Categorie> updateCategorie(@PathVariable Long id, @RequestBody Categorie updatedCategorie) {
        // Vérifiez si la catégorie avec l'ID spécifié existe
        if (!categorieRepo.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Mettez à jour la catégorie
        updatedCategorie.setId(id);
        Categorie savedCategorie = categorieRepo.save(updatedCategorie);

        return new ResponseEntity<>(savedCategorie, HttpStatus.OK);
    }

}
