/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formularios;

/**
 *
 * @author USUARIO
 */
public class Usuario {

    /**
     * @return the ced
     */
    
    public Usuario(String ced, String nom, String ape, String cargo) {
        this.ced = ced;
        this.nom = nom;
        this.ape = ape;
        this.cargo = cargo;
    }

    public String getCed() {
        return ced;
    }

    /**
     * @param ced the ced to set
     */
    public void setCed(String ced) {
        this.ced = ced;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return the ape
     */
    public String getApe() {
        return ape;
    }

    /**
     * @param ape the ape to set
     */
    public void setApe(String ape) {
        this.ape = ape;
    }

    /**
     * @return the cargo
     */
    public String getCargo() {
        return cargo;
    }

    /**
     * @param cargo the cargo to set
     */
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    private String ced,nom,ape,cargo;
    
    
    
}
