package soa.controller;

import java.util.List;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import soa.entities.Categorie;
import soa.entities.Produit;
import soa.repository.CategorieRepository;
import soa.repository.ProduitRepository;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController // pour déclarer un service web de type REST
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/produits")  //    http://localhost:8080/produits
public class ProduitRESTController {
     // pour l'injection de dépendances
    private ProduitRepository produitRepos;
    private CategorieRepository categorieRepo;
    @Autowired
    public ProduitRESTController(ProduitRepository produitRepos, CategorieRepository categorieRepo) {
        this.produitRepos = produitRepos;
        this.categorieRepo = categorieRepo;
    }

   

    //  Message d'accueil
    //  http://localhost:8080/produits/index  (GET)
    @GetMapping(value ="/index" )
    public String accueil() {
        return "BienVenue au service Web REST 'produits'.....";
    }

    //  Afficher la liste des produits
    //  http://localhost:8080/produits/ (GET)

    @GetMapping(
            // spécifier le path de la méthode
            value= "/",
            // spécifier le format de retour en XML
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
    )
    public  List<Produit> getAllProduits() {
        return produitRepos.findAll();

    }

    //  Afficher un produit en spécifiant son 'id'
    //  http://localhost:8080/produits/{id} (GET)
    @GetMapping(
    	    value = "/{id}",
    	    produces = { MediaType.APPLICATION_JSON_VALUE }
    	)
    	public Produit getProduit(@PathVariable Long id) {
    	    Produit p = produitRepos.findById(id).orElse(null);

    	    if (p != null) {
    	        // Charger la catégorie si elle n'est pas déjà chargée
    	        p.getCategorie(); // Cela déclenchera le chargement de la catégorie s'il n'est pas déjà chargé
    	    }

    	    return p;
    	}

   

    //  ajouter un produit avec la méthode "POST"
    //  http://localhost:8080/produits/   (POST)
    @PostMapping(
            // spécifier le path de la méthode
            value = "/"  ,
            //spécifier le format de retour
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public Produit saveProduit(@RequestBody Produit p)
    {
        return produitRepos.save(p);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduit(@PathVariable Long id, @RequestBody Produit produit) {
        try {
            System.out.println("ID à mettre à jour : " + id);

            Produit existingProduit = produitRepos.findById(id).orElse(null);

            if (existingProduit != null) {
                System.out.println("Produit existant : " + existingProduit);

                // Charger la catégorie si elle n'est pas déjà chargée
                existingProduit.getCategorie();

                // Vérifier si les champs nécessaires ne sont pas vides ou nuls
                if (produit.getCode() != null && !produit.getCode().isEmpty()) {
                    existingProduit.setCode(produit.getCode());
                }

                if (produit.getDesignation() != null && !produit.getDesignation().isEmpty()) {
                    existingProduit.setDesignation(produit.getDesignation());
                }

                if (produit.getPrix() > 0) {
                    existingProduit.setPrix(produit.getPrix());
                }

                // Vérifier si la catégorie est définie dans la demande
                existingProduit.getCategorie();

                // Vérifier si la catégorie est définie dans la demande
                if (produit.getCategorie() != null) {
                    // Charger la catégorie complète à partir de la base de données
                    Categorie updatedCategorie = categorieRepo.findById(produit.getCategorie().getId()).orElse(null);

                    // Vérifier si le libellé de la catégorie est défini dans la demande
                    if (updatedCategorie != null) {
                        existingProduit.setCategorie(updatedCategorie);
                    }
                }
                // Vérifier d'autres champs si nécessaire

                produitRepos.save(existingProduit);

                System.out.println("Produit mis à jour : " + existingProduit);
                return ResponseEntity.ok("Produit mis à jour avec succès");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produit non trouvé");
            }
        } catch (Exception e) {
            // Gérer les exceptions ici
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la mise à jour du produit");
        }
    }
    
    


 // Supprimer un produit avec la méthode 'DELETE'
 // http://localhost:8080/produits/{id} (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduit(@PathVariable Long id) {
        produitRepos.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
