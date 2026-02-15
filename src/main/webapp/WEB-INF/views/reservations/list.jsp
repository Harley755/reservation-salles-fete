<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Liste des Réservations</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body>
    <%@ include file="../common/navbar.jsp" %>
    <div class="container mt-4">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2><i class="bi bi-calendar-check text-warning"></i> Liste des Réservations</h2>
            <a href="${pageContext.request.contextPath}/reservations/create" class="btn btn-warning">
                <i class="bi bi-plus-circle"></i> Nouvelle Réservation
            </a>
        </div>
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success alert-dismissible fade show">
                ${successMessage}<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>
        <div class="card">
            <div class="card-body">
                <c:choose>
                    <c:when test="${empty reservations}">
                        <div class="alert alert-info">Aucune réservation trouvée</div>
                    </c:when>
                    <c:otherwise>
                        <table class="table table-striped table-hover">
                            <thead class="table-warning">
                                <tr><th>ID</th><th>Date</th><th>Heure Début</th><th>Heure Fin</th><th>Utilisateur</th><th>Salle</th><th>Actions</th></tr>
                            </thead>
                            <tbody>
                                <c:forEach var="reservation" items="${reservations}">
                                    <tr>
                                        <td>${reservation.id}</td>
                                        <td><i class="bi bi-calendar"></i> ${reservation.dateReservation}</td>
                                        <td><i class="bi bi-clock"></i> ${reservation.heureDebut}</td>
                                        <td><i class="bi bi-clock-fill"></i> ${reservation.heureFin}</td>
                                        <td>${reservation.utilisateur.nom}</td>
                                        <td>${reservation.salle.nom}</td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/reservations/${reservation.id}" class="btn btn-sm btn-info"><i class="bi bi-eye"></i></a>
                                            <a href="${pageContext.request.contextPath}/reservations/${reservation.id}/edit" class="btn btn-sm btn-warning"><i class="bi bi-pencil"></i></a>
                                            <form action="${pageContext.request.contextPath}/reservations/${reservation.id}/delete" method="post" style="display:inline;" onsubmit="return confirm('Confirmer ?');">
                                                <button type="submit" class="btn btn-sm btn-danger"><i class="bi bi-trash"></i></button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
