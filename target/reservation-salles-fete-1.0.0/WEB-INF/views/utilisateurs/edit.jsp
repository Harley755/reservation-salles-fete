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
                        <h4><i class="bi bi-pencil"></i> Modifier l'Utilisateur</h4>
                    </div>
                    <div class="card-body">
                        <form action="${pageContext.request.contextPath}/utilisateurs/${utilisateur.id}/edit" method="post">
                            <div class="mb-3">
                                <label for="nom" class="form-label">Nom *</label>
                                <input type="text" class="form-control" id="nom" name="nom" value="${utilisateur.nom}" required>
                            </div>
                            <div class="mb-3">
                                <label for="email" class="form-label">Email *</label>
                                <input type="email" class="form-control" id="email" name="email" value="${utilisateur.email}" required>
                            </div>
                            <div class="mb-3">
                                <label for="role" class="form-label">Rôle *</label>
                                <select class="form-select" id="role" name="role" required>
                                    <option value="ADMIN" ${utilisateur.role == 'ADMIN' ? 'selected' : ''}>Administrateur</option>
                                    <option value="GESTIONNAIRE" ${utilisateur.role == 'GESTIONNAIRE' ? 'selected' : ''}>Gestionnaire</option>
                                    <option value="CLIENT" ${utilisateur.role == 'CLIENT' ? 'selected' : ''}>Client</option>
                                </select>
                            </div>
                            <div class="d-flex justify-content-between">
                                <a href="${pageContext.request.contextPath}/utilisateurs/${utilisateur.id}" class="btn btn-secondary">Annuler</a>
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
