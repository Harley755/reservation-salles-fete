<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Liste des Salles</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body>
    <%@ include file="../common/navbar.jsp" %>
    <div class="container mt-4">
        <div class="d-flex justify-content-between mb-4">
            <h2><i class="bi bi-building text-success"></i> Liste des Salles</h2>
            <a href="${pageContext.request.contextPath}/salles/create" class="btn btn-success">
                <i class="bi bi-plus-circle"></i> Nouvelle Salle
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
                    <c:when test="${empty salles}">
                        <div class="alert alert-info">Aucune salle trouvée</div>
                    </c:when>
                    <c:otherwise>
                        <table class="table table-striped table-hover">
                            <thead class="table-success">
                                <tr><th>ID</th><th>Nom</th><th>Capacité</th><th>Localisation</th><th>Disponible</th><th>Actions</th></tr>
                            </thead>
                            <tbody>
                                <c:forEach var="salle" items="${salles}">
                                    <tr>
                                        <td>${salle.id}</td>
                                        <td><strong>${salle.nom}</strong></td>
                                        <td><i class="bi bi-people"></i> ${salle.capacite}</td>
                                        <td><i class="bi bi-geo-alt"></i> ${salle.localisation}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${salle.disponible}">
                                                    <span class="badge bg-success">Oui</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge bg-danger">Non</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/salles/${salle.id}" class="btn btn-sm btn-info"><i class="bi bi-eye"></i></a>
                                            <a href="${pageContext.request.contextPath}/salles/${salle.id}/edit" class="btn btn-sm btn-warning"><i class="bi bi-pencil"></i></a>
                                            <form action="${pageContext.request.contextPath}/salles/${salle.id}/delete" method="post" style="display:inline;" onsubmit="return confirm('Confirmer ?');">
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
