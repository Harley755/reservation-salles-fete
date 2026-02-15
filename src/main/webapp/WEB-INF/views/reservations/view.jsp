<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="fr">

        <head>
            <meta charset="UTF-8">
            <title>Détails Salle</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
            <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">
        </head>

        <body>
            <%@ include file="../common/navbar.jsp" %>
                <div class="container mt-4">
                    <div class="row justify-content-center">
                        <div class="col-md-8">
                            <div class="card">
                                <div class="card-header bg-info text-white">
                                    <h4><i class="bi bi-person-circle"></i> Détails de la réservation</h4>
                                </div>
                                <div class="card-body">
                                    <table class="table table-borderless">
                                        <tr>
                                            <th width="30%">ID :</th>
                                            <td>${reservation.id}</td>
                                        </tr>
                                        <tr>
                                            <th>Date de réservation :</th>
                                            <td><strong>${reservation.dateReservation}</strong></td>
                                        </tr>
                                        <tr>
                                            <th>Heure de début :</th>
                                            <td><i class="bi bi-people"></i> ${reservation.heureDebut}</td>
                                        </tr>
                                        <tr>
                                            <th>Heure de fin :</th>
                                            <td><i class="bi bi-people"></i> ${reservation.heureFin}</td>
                                        </tr>
                                        <tr>
                                            <th>Réservé par :</th>
                                            <td><span class="">${reservation.utilisateur.nom}</span></td>
                                        </tr>
                                        <tr>
                                            <th>Réservé à :</th>
                                            <td><span class="">${reservation.salle.nom}
                                                    (${reservation.salle.localisation})</span></td>
                                        </tr>
                                    </table>
                                    <div class="d-flex justify-content-between mt-4">
                                        <a href="${pageContext.request.contextPath}/reservations"
                                            class="btn btn-secondary">Retour</a>
                                        <div>
                                            <a href="${pageContext.request.contextPath}/reservations/${reservation.id}/edit"
                                                class="btn btn-warning">
                                                <i class="bi bi-pencil"></i> Modifier
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        </body>

        </html>