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
                                    <h4><i class="bi bi-person-circle"></i> Détails de la salle</h4>
                                </div>
                                <div class="card-body">
                                    <table class="table table-borderless">
                                        <tr>
                                            <th width="30%">ID :</th>
                                            <td>${salle.id}</td>
                                        </tr>
                                        <tr>
                                            <th>Nom :</th>
                                            <td><strong>${salle.nom}</strong></td>
                                        </tr>
                                        <tr>
                                            <th>Capacité :</th>
                                            <td><i class="bi bi-people"></i> ${salle.capacite}</td>
                                        </tr>
                                        <tr>
                                            <th>Localisation :</th>
                                            <td><span class="">${salle.localisation}</span></td>
                                        </tr>
                                    </table>
                                    <div class="d-flex justify-content-between mt-4">
                                        <a href="${pageContext.request.contextPath}/salles"
                                            class="btn btn-secondary">Retour</a>
                                        <div>
                                            <a href="${pageContext.request.contextPath}/salles/${salle.id}/edit"
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