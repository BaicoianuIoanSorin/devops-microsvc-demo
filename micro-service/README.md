# Micro service demo using Spring Boot
This (sub) project holds the micro service. The micro service uses PostgreSQL to persist data.

The micro service and PostGRESQL each runs a their own container, and the pair service+postgresql is started with Docker Compose.
## API
This service exposes 3 endpoints on port 8080:
* GET: `/entries/v1/count` - returns current number of entries
* GET: `/entries/v1/entry/{id}` - returns info/data for entry with id={id}
* POST: `/entries/v1/entry` - inserts a new entry. When inserting new entries, the specified is ignored and
replaced with a unique id assigned by the database.

## Curl example
Insert new entry
```
curl -d '{"id":1000, "data":"Heeeehaw!"}' -H "Content-Type: application/json" -X POST http://localhost:8080/entries/v1/entry
```

## To inspect the database
```
❯ docker exec -it microsvc-demo-pgsql-container /bin/sh
/ # psql --username postgres
psql (15.2)
Type "help" for help.

postgres=# \l
                                                 List of databases
    Name     |  Owner   | Encoding |  Collate   |   Ctype    | ICU Locale | Locale Provider |   Access privileges
-------------+----------+----------+------------+------------+------------+-----------------+-----------------------
 microsvc-db | postgres | UTF8     | en_US.utf8 | en_US.utf8 |            | libc            |
 postgres    | postgres | UTF8     | en_US.utf8 | en_US.utf8 |            | libc            |
 template0   | postgres | UTF8     | en_US.utf8 | en_US.utf8 |            | libc            | =c/postgres          +
             |          |          |            |            |            |                 | postgres=CTc/postgres
 template1   | postgres | UTF8     | en_US.utf8 | en_US.utf8 |            | libc            | =c/postgres          +
             |          |          |            |            |            |                 | postgres=CTc/postgres
(4 rows)

postgres=# \c microsvc-db
You are now connected to database "microsvc-db" as user "postgres".

microsvc-db=# TABLE entry;
 id  |   data
-----+-----------
   1 | howdy
   2 | howdy
   3 | howdy
   4 | howdy
  52 | howdy
  53 | howdy
  54 | howdy
 102 | howdy
 103 | howdy
 104 | howdy
 105 | howdy
 106 | howdy
 107 | howdy
 108 | howdy
 109 | howdy
 110 | howdy
 111 | howdy
 112 | Heeeehaw!
 152 | howdy
 153 | howdy
 154 | howdy
 155 | howdy
(22 rows)
```

# OpenAPI specification

## /entries/v1/count
```
openapi: "3.0.3"
info:
  title: "micro_service API"
  description: "micro_service API"
  version: "1.0.0"
servers:
  - url: "https://micro_service"
paths:
  /entries/v1/count:
    get:
      summary: "GET entries/v1/count"
      operationId: "getEntryCount"
      responses:
        "200":
          description: "OK"
          content:
            '*/*':
              schema:
                type: "integer"
                format: "int64"
```

## /entries/v1/entry/{id}
```
openapi: "3.0.3"
info:
  title: "micro_service API"
  description: "micro_service API"
  version: "1.0.0"
servers:
  - url: "https://micro_service"
paths:
  /entries/v1/entry/{id}:
    get:
      summary: "GET entries/v1/entry/{id}"
      operationId: "getNewestEntry"
      parameters:
        - name: "id"
          in: "path"
          required: true
          schema:
            type: "integer"
            format: "int64"
      responses:
        "200":
          description: "OK"
          content:
            '*/*':
              schema:
                $ref: "#/components/schemas/Entry"
components:
  schemas:
    Entry:
      type: "object"
      properties:
        id:
          type: "integer"
          format: "int64"
        data:
          type: "string"
```

## /entries/v1/entry
```
openapi: "3.0.3"
info:
  title: "micro_service API"
  description: "micro_service API"
  version: "1.0.0"
servers:
  - url: "https://micro_service"
paths:
  /entries/v1/entry:
    post:
      summary: "POST entries/v1/entry"
      operationId: "createEntry"
      requestBody:
        content:
          application/json:
            schema:
              $ref: "#/components/schemas/Entry"
        required: true
      responses:
        "200":
          description: "OK"
          content:
            '*/*':
              schema:
                $ref: "#/components/schemas/Entry"
components:
  schemas:
    Entry:
      type: "object"
      properties:
        id:
          type: "integer"
          format: "int64"
        data:
          type: "string"
```