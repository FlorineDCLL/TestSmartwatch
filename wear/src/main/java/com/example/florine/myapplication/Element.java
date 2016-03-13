package com.example.florine.myapplication;

/**
 * Created by florine on 3/13/16.
 */
public class Element {

    private String titre;
    private String texte;
    private int color;

    public Element(String titre, String texte, int color){
        this.titre = titre;
        this.texte = texte;
        this.color = color;
    }

    public String getTitre(){
        return this.titre;
    }

    public String getTexte(){
        return this.texte;
    }

    public int getColor(){
        return this.color;
    }
}
