# MealRestController curl requests
### delete
```bash
curl -X DELETE http://localhost:8080/rest/meals/100004
```
### get
```bash
curl -X GET http://localhost:8080/rest/meals/100003
```
### getBetween
```bash
curl -X GET "http://localhost:8080/rest/meals/filter?startDate=2020-01-31&endDate=2020-01-31&startTime=09:00:00&endTime=14:00:00"
```
### getBetweenWithNull
```bash
curl -X GET http://localhost:8080/rest/meals/filter
```
### createWithLocation
```bash
curl -X POST http://localhost:8080/rest/meals \
  -H "Content-Type: application/json" \
  -d '{
	    "id": "null",
        "dateTime": "2026-04-01T12:00:00",
        "description": "Обед",
        "calories": 500
      }'
```
### update
```bash
curl -X PUT http://localhost:8080/rest/meals/100003 \
  -H "Content-Type: application/json" \
  -d '{
        "id": 100003,
        "dateTime": "2020-01-31T10:02:00",
        "description": "Обновленный завтрак",
        "calories": 200
      }'
curl -X GET http://localhost:8080/rest/meals/100003
```
