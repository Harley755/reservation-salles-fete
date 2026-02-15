<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des Utilisateurs</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body>
    <%@ include file="../common/navbar.jsp" %>

    <div class="container mt-4">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2><i class="bi bi-people-fill text-primary"></i> Liste des Utilisateurs</h2>
            <a href="${pageContext.request.contextPath}/utilisateurs/create" class="btn btn-primary">
                <i class="bi bi-plus-circle"></i> Nouvel Utilisateur
            </a>
        </div>

        <!-- Messages Flash -->
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <i class="bi bi-check-circle"></i> ${successMessage}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="bi bi-exclamation-triangle"></i> ${errorMessage}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <!-- Barre de recherche -->
        <div class="card mb-3">
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/utilisateurs/search" method="get" class="row g-3">
                    <div class="col-md-10">
                        <input type="text" class="form-control" name="nom" placeholder="Rechercher par nom..." value="${searchQuery}">
                    </div>
                    <div class="col-md-2">
                        <button type="submit" class="btn btn-primary w-100">
                            <i class="bi bi-search"></i> Rechercher
                        </button>
                    </div>
                </form>
            </div>
        </div>

        <!-- Table des utilisateurs -->
        <div class="card">
            <div class="card-body">
                <c:choose>
                    <c:when test="${empty utilisateurs}">
                        <div class="alert alert-info text-center">
                            <i class="bi bi-info-circle"></i> Aucun utilisateur trouvé
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="table-responsive">
                            <table class="table table-striped table-hover">
                                <thead class="table-primary">
                                    <tr>
                                        <th>ID</th>
                                        <th>Nom</th>
                                        <th>Email</th>
                                        <th>Rôle</th>
                                        <th class="text-center">Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="utilisateur" items="${utilisateurs}">
                                        <tr>
                                            <td>${utilisateur.id}</td>
                                            <td><strong>${utilisateur.nom}</strong></td>
                                            <td><i class="bi bi-envelope"></i> ${utilisateur.email}</td>
                                            <td>
                                                <span class="badge bg-info">${utilisateur.role}</span>
                                            </td>
                                            <td class="text-center">
                                                <a href="${pageContext.request.contextPath}/utilisateurs/${utilisateur.id}" 
                                                   class="btn btn-sm btn-info" title="Voir">
                                                    <i class="bi bi-eye"></i>
                                                </a>
                                                <a href="${pageContext.request.contextPath}/utilisateurs/${utilisateur.id}/edit" 
                                                   class="btn btn-sm btn-warning" title="Modifier">
                                                    <i class="bi bi-pencil"></i>
                                                </a>
                                                <form action="${pageContext.request.contextPath}/utilisateurs/${utilisateur.id}/delete" 
                                                      method="post" style="display:inline;"
                                                      onsubmit="return confirm('Êtes-vous sûr de vouloir supprimer cet utilisateur ?');">
                                                    <button type="submit" class="btn btn-sm btn-danger" title="Supprimer">
                                                        <i class="bi bi-trash"></i>
                                                    </button>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <div class="mt-3">
                            <p class="text-muted">Total : ${utilisateurs.size()} utilisateur(s)</p>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
