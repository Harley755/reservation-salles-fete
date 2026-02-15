<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="fr">

        <head>
            <meta charset="UTF-8">
            <title>Modifier Utilisateur</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
            <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">
        </head>

        <body>
            <%@ include file="../common/navbar.jsp" %>
                <div class="container mt-4">
                    <div class="row justify-content-center">
                        <div class="col-md-8">
                            <div class="card">
                                <div class="card-header bg-warning">
                                    <h4><i class="bi bi-pencil"></i> Modifier la réservation</h4>
                                </div>
                                <div class="card-body">
                                    <form
                                        action="${pageContext.request.contextPath}/reservations/${reservation.id}/edit"
                                        method="post">
                                        <div class="mb-3">
                                            <label for="utilisateur.id" class="form-label">Utilisateur *</label>
                                            <select class="form-select" id="utilisateur.id" name="utilisateur.id"
                                                required>
                                                <option value="">-- Sélectionnez un utilisateur --</option>
                                                <c:forEach var="user" items="${utilisateurs}">
                                                    <option value="${user.id}" ${reservation.utilisateur !=null &&
                                                        reservation.utilisateur.id==user.id ? 'selected' : '' }>
                                                        ${user.nom} (${user.email})
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </div>

                                        <div class="mb-3">
                                            <label for="salle.id" class="form-label">Salle *</label>
                                            <select class="form-select" id="salle.id" name="salle.id" required>
                                                <option value="">-- Sélectionnez une salle --</option>
                                                <c:forEach var="s" items="${salles}">
                                                    <option value="${s.id}" ${reservation.salle !=null &&
                                                        reservation.salle.id==s.id ? 'selected' : '' }>
                                                        ${s.nom} - ${s.localisation} (${s.capacite} pers.)
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </div>

                                        <div class="mb-3">
                                            <label for="dateReservation" class="form-label">Date de Réservation
                                                *</label>
                                            <input type="date" class="form-control" id="dateReservation"
                                                name="dateReservation" value="${reservation.dateReservation}"
                                                min="<%= java.time.LocalDate.now() %>" required>
                                            <small class="form-text text-muted">La date doit être aujourd'hui ou dans le
                                                futur</small>
                                        </div>

                                        <div class="row">
                                            <div class="col-md-6 mb-3">
                                                <label for="heureDebut" class="form-label">Heure de Début *</label>
                                                <input type="time" class="form-control" id="heureDebut"
                                                    name="heureDebut" value="${reservation.heureDebut}" required>
                                            </div>
                                            <div class="col-md-6 mb-3">
                                                <label for="heureFin" class="form-label">Heure de Fin *</label>
                                                <input type="time" class="form-control" id="heureFin" name="heureFin"
                                                    value="${reservation.heureFin}" required>
                                            </div>
                                        </div>

                                        <div class="alert alert-info">
                                            <strong><i class="bi bi-info-circle"></i> Important :</strong>
                                            Le système vérifiera automatiquement qu'aucune autre réservation ne
                                            chevauche ce créneau horaire pour la salle
                                            sélectionnée.
                                        </div>
                                        <div class="d-flex justify-content-between">
                                            <a href="${pageContext.request.contextPath}/reservations/${reservation.id}"
                                                class="btn btn-secondary">Annuler</a>
                                            <button type="submit" class="btn btn-warning">Modifier</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        </body>

        </html>