<html lang="ru">
<head>
	<title>Meals</title>
</head>
	<body>
		<h3><a href="index.html">Home</a></h3>
		<hr>
		<h2>Edit meal</h2>
		<form method="post" action="meals">
			<input type="hidden" name="id" value="${meal.id}">
			<p>
		        <label>DateTime:</label>
		        <input type="datetime-local" name="dateTime" value="${meal.dateTime}" required>
	    	</p>
		    <p>
		        <label>Description:</label>
		        <input type="text" name="description" value="${meal.description}" required>
		    </p>
		    <p>
		        <label>Calories:</label>
		        <input type="number" name="calories" value="${meal.calories}" required>
		    </p>
		    <p>
		        <button type="submit">Save</button>
		        <button onclick="window.history.back()" type="button">Cancel</button>
		    </p>
		</form>
	</body>
</html>