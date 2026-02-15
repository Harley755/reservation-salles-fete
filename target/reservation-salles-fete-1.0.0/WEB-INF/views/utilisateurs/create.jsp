<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Créer un Utilisateur</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body>
    <%@ include file="../common/navbar.jsp" %>

    <div class="container mt-4">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header bg-primary text-white">
                        <h4><i class="bi bi-person-plus"></i> Créer un Nouvel Utilisateur</h4>
                    </div>
                    <div class="card-body">
                        <form action="${pageContext.request.contextPath}/utilisateurs" method="post">
                            <div class="mb-3">
                                <label for="nom" class="form-label">Nom <span class="text-danger">*</span></label>
                                <input type="text" 
                                       class="form-control ${not empty errors.nom ? 'is-invalid' : ''}" 
                                       id="nom" 
                                       name="nom" 
                                       value="${utilisateur.nom}"
                                       required>
                                <c:if test="${not empty errors.nom}">
                                    <div class="invalid-feedback">${errors.nom}</div>
                                </c:if>
                            </div>

                            <div class="mb-3">
                                <label for="email" class="form-label">Email <span class="text-danger">*</span></label>
                                <input type="email" 
                                       class="form-control ${not empty errors.email ? 'is-invalid' : ''}" 
                                       id="email" 
                                       name="email" 
                                       value="${utilisateur.email}"
                                       required>
                                <c:if test="${not empty errors.email}">
                                    <div class="invalid-feedback">${errors.email}</div>
                                </c:if>
                                <small class="form-text text-muted">Format: exemple@email.com</small>
                            </div>

                            <div class="mb-3">
                                <label for="role" class="form-label">Rôle <span class="text-danger">*</span></label>
                                <select class="form-select ${not empty errors.role ? 'is-invalid' : ''}" 
                                        id="role" 
                                        name="role" 
                                        required>
                                    <option value="">-- Sélectionnez un rôle --</option>
                                    <option value="ADMIN" ${utilisateur.role == 'ADMIN' ? 'selected' : ''}>Administrateur</option>
                                    <option value="GESTIONNAIRE" ${utilisateur.role == 'GESTIONNAIRE' ? 'selected' : ''}>Gestionnaire</option>
                                    <option value="CLIENT" ${utilisateur.role == 'CLIENT' ? 'selected' : ''}>Client</option>
                                </select>
                                <c:if test="${not empty errors.role}">
                                    <div class="invalid-feedback">${errors.role}</div>
                                </c:if>
                            </div>

                            <div class="d-grid gap-2 d-md-flex justify-content-md-between mt-4">
                                <a href="${pageContext.request.contextPath}/utilisateurs" class="btn btn-secondary">
                                    <i class="bi bi-arrow-left"></i> Annuler
                                </a>
                                <button type="submit" class="btn btn-primary">
                                    <i class="bi bi-save"></i> Créer
                                </button>
                            </div>
                        </form>
                    </div>
                </div>

                <div class="mt-3">
                    <div class="alert alert-info">
                        <strong><i class="bi bi-info-circle"></i> Information :</strong>
                        Les champs marqués d'une étoile (<span class="text-danger">*</span>) sont obligatoires.
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
