
# Gestion contacts API

## Présentation

API pour la gestion des contacts d'une entreprise

## Démarrer l'application

Démarrer la classe Main : GestionContactsApplication, l'application est accessible via le lien suivant :
 
http://localhost:8080/gc-api/

Pour changer le numéro de port, ouvrir le fichier src/main/resources/application.properties et changer la valeur du champ 
<b>server.port</b>

## Swagger UI

La documentation Swagger est accessible via le lien suivant : 

http://localhost:8080/gc-api/swagger-ui/index.html

## Base de données

La base de données utilisée est H2. Vous pouvez accéder à la console via le lien suivant : 
http://localhost:8080/gc-api/h2-console/login.jsp (Le mot de passe par défaut est : password)

Le fichier data.sql est lancé automatiquement pour peupler la base de données lors de du démarrage de l'application.
