<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Réservation de Salles de Fête</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body>
    <%@ include file="common/navbar.jsp" %>

    <div class="container mt-5">
        <div class="jumbotron bg-light p-5 rounded-3 mb-5">
            <h1 class="display-4">
                <i class="bi bi-calendar-event text-primary"></i>
                Plateforme de Réservation de Salles
            </h1>
            <p class="lead">Gérez facilement vos réservations de salles de fête</p>
            <hr class="my-4">
            <p>Système complet de gestion avec utilisateurs, salles et réservations</p>
            <a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/reservations/create" role="button">
                <i class="bi bi-plus-circle"></i> Nouvelle Réservation
            </a>
        </div>

        <!-- Statistiques -->
        <div class="row text-center mb-5">
            <div class="col-md-3">
                <div class="card border-primary">
                    <div class="card-body">
                        <i class="bi bi-people-fill text-primary" style="font-size: 3rem;"></i>
                        <h3 class="card-title mt-3">${totalUtilisateurs}</h3>
                        <p class="card-text">Utilisateurs</p>
                        <a href="${pageContext.request.contextPath}/utilisateurs" class="btn btn-outline-primary btn-sm">
                            Voir <i class="bi bi-arrow-right"></i>
                        </a>
                    </div>
                </div>
            </div>

            <div class="col-md-3">
                <div class="card border-success">
                    <div class="card-body">
                        <i class="bi bi-building text-success" style="font-size: 3rem;"></i>
                        <h3 class="card-title mt-3">${totalSalles}</h3>
                        <p class="card-text">Salles</p>
                        <a href="${pageContext.request.contextPath}/salles" class="btn btn-outline-success btn-sm">
                            Voir <i class="bi bi-arrow-right"></i>
                        </a>
                    </div>
                </div>
            </div>

            <div class="col-md-3">
                <div class="card border-warning">
                    <div class="card-body">
                        <i class="bi bi-calendar-check text-warning" style="font-size: 3rem;"></i>
                        <h3 class="card-title mt-3">${totalReservations}</h3>
                        <p class="card-text">Réservations</p>
                        <a href="${pageContext.request.contextPath}/reservations" class="btn btn-outline-warning btn-sm">
                            Voir <i class="bi bi-arrow-right"></i>
                        </a>
                    </div>
                </div>
            </div>

            <div class="col-md-3">
                <div class="card border-info">
                    <div class="card-body">
                        <i class="bi bi-check-circle text-info" style="font-size: 3rem;"></i>
                        <h3 class="card-title mt-3">${sallesDisponibles}</h3>
                        <p class="card-text">Salles Disponibles</p>
                        <a href="${pageContext.request.contextPath}/salles?disponible=true" class="btn btn-outline-info btn-sm">
                            Voir <i class="bi bi-arrow-right"></i>
                        </a>
                    </div>
                </div>
            </div>
        </div>

        <!-- Actions rapides -->
        <div class="row">
            <div class="col-md-12">
                <h3 class="mb-4">Actions Rapides</h3>
            </div>
            <div class="col-md-4 mb-3">
                <div class="card h-100">
                    <div class="card-body">
                        <h5 class="card-title">
                            <i class="bi bi-person-plus text-primary"></i> Utilisateurs
                        </h5>
                        <p class="card-text">Gérer les utilisateurs du système</p>
                        <a href="${pageContext.request.contextPath}/utilisateurs/create" class="btn btn-primary">
                            Ajouter un utilisateur
                        </a>
                    </div>
                </div>
            </div>
            <div class="col-md-4 mb-3">
                <div class="card h-100">
                    <div class="card-body">
                        <h5 class="card-title">
                            <i class="bi bi-building-add text-success"></i> Salles
                        </h5>
                        <p class="card-text">Gérer les salles de fête disponibles</p>
                        <a href="${pageContext.request.contextPath}/salles/create" class="btn btn-success">
                            Ajouter une salle
                        </a>
                    </div>
                </div>
            </div>
            <div class="col-md-4 mb-3">
                <div class="card h-100">
                    <div class="card-body">
                        <h5 class="card-title">
                            <i class="bi bi-calendar-plus text-warning"></i> Réservations
                        </h5>
                        <p class="card-text">Créer et gérer les réservations</p>
                        <a href="${pageContext.request.contextPath}/reservations/create" class="btn btn-warning">
                            Nouvelle réservation
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
