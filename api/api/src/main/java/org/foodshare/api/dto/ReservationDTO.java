package org.foodshare.api.dto;

import java.time.LocalDateTime;

public class ReservationDTO {
    private Long id;
    private Long offerId;
    private String offerTitle;
    private Long studentId;
    private LocalDateTime reservationDate;
    private String status;

    //Constructeur
    public ReservationDTO(Long id, Long offerId, String offerTitle, Long studentId, LocalDateTime reservationDate, String status) {
        this.id = id;
        this.offerId = offerId;
        this.offerTitle = offerTitle;
        this.studentId = studentId;
        this.reservationDate = reservationDate;
        this.status = status;
    }

    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public String getOfferTitle() {
        return offerTitle;
    }

    public void setOfferTitle(String offerTitle) {
        this.offerTitle = offerTitle;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}