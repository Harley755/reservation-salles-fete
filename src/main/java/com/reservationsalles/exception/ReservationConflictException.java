package com.reservationsalles.exception;

/**
 * Exception levée lorsqu'une réservation entre en conflit avec une réservation existante
 * (même salle, même date, créneaux horaires qui se chevauchent)
 */
public class ReservationConflictException extends RuntimeException {

    public ReservationConflictException(String message) {
        super(message);
    }

    public ReservationConflictException(String salleName, String date, String heureDebut, String heureFin) {
        super(String.format(
            "Conflit de réservation : La salle '%s' est déjà réservée le %s entre %s et %s",
            salleName, date, heureDebut, heureFin
        ));
    }
}
