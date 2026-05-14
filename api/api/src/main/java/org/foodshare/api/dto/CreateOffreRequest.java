package org.foodshare.api.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public class CreateOffreRequest {

    @NotBlank(message = "Le titre est obligatoire")
    private String titre;

    private String description;

    @Min(value = 1, message = "La quantité doit être au moins 1")
    private int quantity;

    @DecimalMin(value = "0.0", message = "Le prix ne peut pas être négatif")
    private double price;

    @NotNull(message = "La date de début est obligatoire")
    private LocalDateTime startRetrieval;

    @NotNull(message = "La date de fin est obligatoire")
    private LocalDateTime endRetrieval;

    @NotBlank(message = "Le lieu est obligatoire")
    private String location;

    //Getters and Setters
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


}