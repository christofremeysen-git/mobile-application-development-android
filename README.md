# mobile-application-development-android
Project voor de cursus "Mobile Application Development: Android" (HoGent TILE).

# Algemene info

Dit project werd gecreëerd in Kotlin. 

Dokka werd gehanteerd om de code te documenteren. De output directory staat op ./documentation.html.


# De webapplicatie opstarten

De webapplicatie lokaal opstarten doe je door de volgende stappen chronologisch uit te voeren:

## 1) Visual Studio (server)

Hierop draait de webservice (Swagger API). Je kan deze starten aan de hand van IIS Express op een localhost (play icoon).

Conveyor by Keyoti werd als extensie gebruikt om via HTTPS de connectie te leggen via de app.

## 2) Android Studio (client)

Hierop draait de applicatiecode van het project. Je kan deze starten aan de hand van het play icoon in de IDE.

Zie het certificaat in network-security-config om de connectie via HTTPS te leggen tussen de app en de webservice (API). Gebruik het commando "adb reverse tcp:44342 tcp:44342" om beide te "mirror'en".


