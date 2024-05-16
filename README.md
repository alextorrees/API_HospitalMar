# API App Hospital del Mar

Realitzada per l'equip de DAM del Institut Tecnològic de Barcelona (ITB): 
- [Alejandro Torres](https://github.com/alextorrees)
- [Gerard Sanchez](https://github.com/Gesa09)
- [Marçal Herraiz](https://github.com/MarcalHerraiz)
- [Daniel Reinosa](https://github.com/DReino03)

## Descripció
La API Hospital de la Mar proporciona els serveis necessaris per a la gestió d'App. Permet als usuaris, tant professor i alumne, accedir a funcionalitats com la creació d'informes i visualització d'aquests.

# Funcionalitats Professor
## Avaluació mitjançant rúbriques 
Proporcionarà una interfície clara i intuïtiva per a l'avaluació de les habilitats dels alumnes basada en les rúbriques assignades. El professor podrà registrar les puntuacions i les observacions pertinents per a cada criteri d'avaluació.

## Seguiment del progrés:
Permetrà al professor visualitzar i fer seguiment del progrés individual de cada alumne al llarg del temps, identificant àrees de fortalesa i àrees per millorar.

## Generació d'informes: 
Facilitarà la generació d'informes detallats sobre el rendiment i l'evolució de l'alumne, incloent-hi les puntuacions obtingudes en cada rúbrica, els comentaris del professor i altres indicadors rellevants.

# Funcionalitats Alumne
## Accés als resultats:
Permetria als alumnes visualitzar les seves puntuacions i els comentaris associats a les avaluacions realitzades pel professor.

## Seguiment del progrés personal
Proporcionaria una visió del progrés personal de l'alumne en diferents àrees d'habilitats al llarg del temps, destacant els punts forts i les àrees de millora.

# Tecnologies utilitzades 
- Llenguatge de programació: [Kotlin](https://kotlinlang.org/)
- Desenvolupament de interfícies:[Jetpack Compose](https://developer.android.com/develop/ui/compose/documentation?hl=es-419)
- Framework: [Android Studio](https://developer.android.com/studio)
- Base de Dades: [PostgreSQL](https://www.postgresql.org/)

## Endpoints
- `/LoginRoute`: Gestió de l'inici de sessió.
- `/AlumnoRoute`: Gestió per les rutes per l'alumne.
- `/ProfesorRoute`: Gestió per les rutes del professor.
- `/CompetenciaRoute`: Gestió de totes les competències.
- `/ModuloRoute`: Gestió dels mòduls establerts.
- `/InformeRoute`: Gestió dels informes per generar o generats.

## Licencia
[MIT License]()

# FrontEnd
[Accés al FrontEnd](https://github.com/DReino03/HospitalMar)

