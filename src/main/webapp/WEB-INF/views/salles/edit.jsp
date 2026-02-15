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
                                    <h4><i class="bi bi-pencil"></i> Modifier la salle</h4>
                                </div>
                                <div class="card-body">
                                    <form action="${pageContext.request.contextPath}/salles/${salle.id}/edit"
                                        method="post">
                                        <div class="mb-3">
                                            <label for="nom" class="form-label">Nom de la Salle *</label>
                                            <input type="text" class="form-control" id="nom" name="nom"
                                                value="${salle.nom}" required>
                                        </div>
                                        <div class="mb-3">
                                            <label for="capacite" class="form-label">Capacité (personnes) *</label>
                                            <input type="number" class="form-control" id="capacite" name="capacite"
                                                value="${salle.capacite}" min="1" required>
                                        </div>
                                        <div class="mb-3">
                                            <label for="localisation" class="form-label">Localisation *</label>
                                            <input type="text" class="form-control" id="localisation"
                                                name="localisation" value="${salle.localisation}" required>
                                        </div>
                                        <div class="mb-3 form-check">
                                            <input type="checkbox" class="form-check-input" id="disponible"
                                                name="disponible" <c:if test="${salle.disponible}">checked</c:if>
                                            >
                                            <label class="form-check-label" for="disponible">Salle disponible</label>
                                        </div>
                                        <div class="d-flex justify-content-between">
                                            <a href="${pageContext.request.contextPath}/salles/${salle.id}"
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