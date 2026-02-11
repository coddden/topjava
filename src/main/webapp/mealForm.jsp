<html lang="ru">
<head>
	<title>Meals</title>
</head>
	<body>
		<h3><a href="index.html">Home</a></h3>
		<hr>
		<h2>Edit meal</h2>
		<form method="post" action="meals">
			<p>
		        <label>DateTime:</label>
		        <input type="datetime-local" name="dateTime">
	    	</p>
		    <p>
		        <label>Description:</label>
		        <input type="text" name="description">
		    </p>
		    <p>
		        <label>Calories:</label>
		        <input type="number" name="calories">
		    </p>
		    <p>
		        <button type="submit">Save</button>
		        <a href="meals">Cancel</a>
		    </p>
		</form>
	</body>
</html>