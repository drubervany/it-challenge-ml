# Magneto api - Meli challenge

[![codecov](https://codecov.io/gh/gvquiroz/it-challenge-ml/branch/master/graph/badge.svg)](https://codecov.io/gh/gvquiroz/it-challenge-ml)
[![Build Status](https://travis-ci.org/gvquiroz/it-challenge-ml.svg?branch=master)](https://travis-ci.org/gvquiroz/it-challenge-ml)

Magneto quiere reclutar la mayor cantidad de mutantes para poder luchar contra los X-Men

![Screenshot](diagram/arch.png)

## Endpoints
52.206.172.60:8080/v1/mutant

52.206.172.60:8080/v1/stats

## Usage

El endpoint /mutant/ detecta si un humano es mutante enviando la secuencia de ADN mediante un HTTP POST
En caso de verificar un mutante, devuelve un HTTP 200-OK, en caso contrario un 403-Forbidden

```
POST → /mutant/ 
BODY {“dna”:["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]}
```

El endpoint /stats/ devuelve un Json con las estadísticas de las verificaciones de ADN: 

{“count_mutant_dna”:40, “count_human_dna”:100: “ratio”:0.4}

```
GET→“/stats/” 
```



---

## Setup develop
Core specs
- Java 7, Jersey, Grizzly, Dynamo db

Es necesario tener instalado Maven 3.5.0, Java 7 y Docker

- Clonar el repo en su directorio favorito.
Git clone https://github.com/gvquiroz/it-challenge-ml.git

- Bajar las dependencias, correr los test local
Mvn:test

- Levantar el storage local (Dynamodb). Crear el schema.

sudo docker run -d --name dynamodb deangiberson/aws-dynamodb-local

Luego de tener dynamodb corriendo local. Ejecutar la clase CreateDBSchema (que se encuentra dentro del proyecto/utils) para crear el schema.

- Levantar la api local (por default levantara la api en el Puerto 8080)
mvn exec:java

http://localhost:8080/v1/stats

Notas: Para correr los tests end to end deberá parar el container de dynamo db y el servicio (por conflictos de puertos) ya que estos levantan en memoria los mismos.

### Overview

### Picos de trafico dinamicos (un rango de 100 hasta picos de 1 millón de request por segundo)

Para ello se tomó como unidad mínima de server uno que pueda responder en average 200 req/seg y escalar horizontalmente. Este fue implementado con un Autoscaling group de Amazon web services que va ajustando la cantidad de servers según la carga correspondiente

Para el storage se utilizó el autoscaling de dynamo db para acompañar los req/seg

### Secret management
La aplicación solo necesita credenciales para acceso al servicio de aws de DynamoDB, para ello se creo un rol especifico asignado al servidor. La aplicación consigue estas credenciales de la metadata del servidor en el cual se ejecuta.

El acceso al servidor es por pem y no esta compartido.

## Futures/Contemplaciones

### Env local 
Se utiliza dynamo db en un container aparte en vez de que sea levantado en memoria cuando se arranca la aplicación local para poder ser probado local en modo cluster. En el caso de levantar varias aplicaciones todas le pegarían al dynamo db del container. Simulando un ambiente más parecido a prod.

### Algoritmo 
Se contempló agregar un algoritmo de compresión de strings para ahorrar storage a costo de cpu. Pero fue priorizado el cpu sobre el storage.

### Tests
Ahora mismo con el goal de mvn:test corre los tests unitarios, de integración y end to end. Suponiendo que en un futuro los tests end to end se volvieron costosos en tiempo estaría se haría que se corran en 2 goals diferentes. Tanto local como en un pipeline de CI/CD.

Los test end to end se podrían extender para agregar tests de benchmark/performance con un threshold a superar.

### Error handling y respuestas
Como el ejercicio solo plantea devolver ok o forbidden según el caso. Se consideró que está bien que no devuelva ningún tipo de información extra al poder ser intencional este comportamiento.

### Capacity planning y configuration fina
No se realizo configuración fina del threads pools de java al reservarse ese paso para un tuning mas empírico.
El tamaño de los servers se eligió de una forma ‘a ojo’ con el fin de que escalen horizontalmente y ver su respuesta para un futuro tuning empirico.

### Security Deployment Monitoreo Logging
No fueron contemplados en el scope del ejercicio.
