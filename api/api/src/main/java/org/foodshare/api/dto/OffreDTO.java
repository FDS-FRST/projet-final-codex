package org.foodshare.api.dto;

import java.time.LocalDateTime;

public class OffreDTO {
    private Long id;
    private String titre;
    private String description;
    private int quantity;
    private int quantityRemaining;
    private double price;
    private LocalDateTime startRetrieval;
    private LocalDateTime endRetrieval;
    private String location;
    private Long offererId;   // seul l'ID, pas tout l'objet User

    // Constructeur par défaut
    public OffreDTO() {}

    // Constructeur avec tous les champs
    public OffreDTO(Long id, String titre, String description, int quantity, int quantityRemaining,
                    double price, LocalDateTime startRetrieval, LocalDateTime endRetrieval,
                    String location, Long offererId) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.quantity = quantity;
        this.quantityRemaining = quantityRemaining;
        this.price = price;
        this.startRetrieval = startRetrieval;
        this.endRetrieval = endRetrieval;
        this.location = location;
        this.offererId = offererId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantityRemaining() {
        return quantityRemaining;
    }

    public void setQuantityRemaining(int quantityRemaining) {
        this.quantityRemaining = quantityRemaining;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getStartRetrieval() {
        return startRetrieval;
    }

    public void setStartRetrieval(LocalDateTime startRetrieval) {
        this.startRetrieval = startRetrieval;
    }

    public LocalDateTime getEndRetrieval() {
        return endRetrieval;
    }

    public void setEndRetrieval(LocalDateTime endRetrieval) {
        this.endRetrieval = endRetrieval;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getOffererId() {
        return offererId;
    }

    public void setOffererId(Long offererId) {
        this.offererId = offererId;
    }

}