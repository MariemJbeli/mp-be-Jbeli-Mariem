package soa.metier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soa.entities.Categorie;
import soa.repository.CategorieRepository;

import java.util.List;

@Service
@Transactional
public class CategorieMetierImpl implements CategorieMetierInterface {

    @Autowired
    private CategorieRepository categorieRepo;

    @Override
    public void ajouterCategorie(Categorie c) {
        // Validate that both code and libelle are not empty before saving
        if (c.getCode() != null && !c.getCode().trim().isEmpty() &&
            c.getLibelle() != null && !c.getLibelle().trim().isEmpty()) {
            categorieRepo.save(c);
        } else {
            // Handle validation error, throw an exception, log a message, etc.
            throw new IllegalArgumentException("Code and Libelle must not be empty");
        }}

    @Override
    public List<Categorie> listeCategorie() {
        return categorieRepo.findAll();
    }
}
