<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Détails Utilisateur</title>
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
                        <h4><i class="bi bi-person-circle"></i> Détails de l'Utilisateur</h4>
                    </div>
                    <div class="card-body">
                        <table class="table table-borderless">
                            <tr><th width="30%">ID :</th><td>${utilisateur.id}</td></tr>
                            <tr><th>Nom :</th><td><strong>${utilisateur.nom}</strong></td></tr>
                            <tr><th>Email :</th><td><i class="bi bi-envelope"></i> ${utilisateur.email}</td></tr>
                            <tr><th>Rôle :</th><td><span class="badge bg-info">${utilisateur.role}</span></td></tr>
                            <tr><th>Réservations :</th><td>${utilisateur.reservations.size()}</td></tr>
                        </table>
                        <div class="d-flex justify-content-between mt-4">
                            <a href="${pageContext.request.contextPath}/utilisateurs" class="btn btn-secondary">Retour</a>
                            <div>
                                <a href="${pageContext.request.contextPath}/utilisateurs/${utilisateur.id}/edit" class="btn btn-warning">
                                    <i class="bi bi-pencil"></i> Modifier
                                </a>
                                <a href="${pageContext.request.contextPath}/reservations/utilisateur/${utilisateur.id}" class="btn btn-primary">
                                    <i class="bi bi-calendar-check"></i> Voir réservations
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
