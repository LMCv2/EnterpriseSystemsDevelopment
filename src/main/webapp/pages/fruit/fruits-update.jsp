<%@taglib uri="/WEB-INF/tlds/taglib.tld" prefix="taglib" %>
<taglib:layout title="Home">
<div class="container">
    <p

    <h2>Fruits List</h2>
    <ul id="fruitsList" class="list-group">
        <li class="list-group-item">Loading fruits...</li>
    </ul>
    <h2>Update fruit type</h2>
    <form id="updateFruitForm" action="/add-fruit" method="post">
        <div class="mb-3">
            <label for="fruitType" class="form-label">Fruit Type</label>
            <input type="text" class="form-control" id="fruitName" name="fruitName" required>
        </div>
        <button type="submit" class="btn btn-primary">Update</button>
</div>

<script>
    fetch("/get-fruits")
        .then(response => response.json())
        .then(fruits => {
            const fruitsList = document.getElementById("fruitsList");
            fruitsList.innerHTML = "";
            
            if (fruits && fruits.length > 0) {
                fruits.forEach(fruit => {
                    const li = document.createElement("li");
                    li.className = "list-group-item";
                    li.textContent = typeof fruit === 'object' ? (fruit.name || JSON.stringify(fruit)) : fruit;
                    fruitsList.appendChild(li);
                });
            } else {
                const li = document.createElement("li");
                li.className = "list-group-item";
                li.textContent = "No fruits available";
                fruitsList.appendChild(li);
            }
        })
        .catch(error => {
            console.error("Error fetching fruits:", error);
            document.getElementById("fruitsList").innerHTML = 
                "<li class='list-group-item text-danger'>Error loading fruits</li>";
        });
</script>
</taglib:layout>
