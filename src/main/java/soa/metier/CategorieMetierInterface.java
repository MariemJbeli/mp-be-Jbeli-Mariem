package soa.metier;

import java.util.List;

import soa.entities.Categorie;


public interface CategorieMetierInterface
{
    void ajouterCategorie(Categorie c);
    List<Categorie> listeCategorie();

}
