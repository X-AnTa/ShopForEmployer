<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<body>

<h2>All Products</h2>
<br>

<table>
    <tr>
        <th>Name</th>
        <th>Description</th>
        <th>Price</th>
        <th>Currencies</th>
        <th>Descriptions</th>
    </tr>

    <c:forEach var="pr" items="${allPr}">
        <tr>
            <td>${pr.name}</td>
            <td>${pr.description}</td>
            <td>${pr.price}</td>
            <td>${pr.currencies}</td>
            <td>${pr.descriptions}</td>
        </tr>
    </c:forEach>

</table>

</body>
</html>