package org.foodshare.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "offers")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    private String description;

    @Min(0)
    private int quantity;        // quantité initiale

    @Min(0)
    private int quantityRemaining; // quantité restante (mise à jour lors des réservations)

    @DecimalMin("0.0")
    private double price;        // 0 = don

    @NotNull
    private LocalDateTime startRetrieval;

    @NotNull
    private LocalDateTime endRetrieval;

    @NotBlank
    private String location;

    @ManyToOne
    @JoinColumn(name = "offerer_id", nullable = false)
    private User offerer;   // l'utilisateur (offreur) qui crée l'offre

    public Offer() {}

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
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
        this.quantityRemaining = quantity;
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

    public User getOfferer() {
        return offerer;
    }
    public void setOfferer(User offerer) {
        this.offerer = offerer;
    }
}


