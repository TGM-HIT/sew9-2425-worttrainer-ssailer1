# Worttrainer reloaded

@auhtor: Sebastian Sailer <br>
@version: 20.09.2024


Der **WortTrainer** ist eine Java-basierte Anwendung, die Schülern hilft, Rechtschreibung zu üben, indem sie Wörter zu Bildern eingeben und deren Korrektheit überprüft wird. Die Anwendung ist Teil einer SEW-Übung und setzt den Fokus auf den Softwareentwicklungsprozess unter Verwendung von Git, Gradle und weiteren Entwicklungswerkzeugen.

## Funktionalität

- **Wort-Bild-Paare**: Zu einem Bild (über eine URL verlinkt) muss das entsprechende Wort eingegeben werden.
- **Überprüfung**: Die eingegebene Antwort wird mit der korrekten Schreibweise verglichen, und eine Rückmeldung wird angezeigt.
- **Statistik**: Der Trainer führt eine Statistik über richtig/falsch geratene Wörter.
- **Persistenz**: Der aktuelle Zustand des Trainers (Wortpaare, ausgewähltes Paar, Statistik) kann gespeichert und geladen werden (z. B. im JSON-Format).
- **Grafische Oberfläche**: Das Programm verwendet einfache JOptionPane-Dialoge zur Anzeige der Bilder und Eingabe der Wörter.

## Technologien

- **Java**: Hauptprogrammiersprache.
- **Gradle**: Build-System.
- **JSON**: Daten werden im JSON-Format gespeichert und geladen.
- **GitHub**: Versionskontrolle und Repository.
- **JUnit**: Für automatisierte Tests.
- **Externe Bibliotheken**: Integration weiterer Bibliotheken über Gradle.

## Entwicklungsprozess

- **Git Repository**: Alle Änderungen werden über Git versioniert.
- **Entwurf und Implementierung**: Features werden zuerst geplant (UML-Diagramme) und dann implementiert, getestet und dokumentiert.
- **Continuous Integration**: Automatisierung (z. B. Tests) über GitHub Actions.

## Verwendung

1. Lade die persistierten Daten oder starte eine neue Session mit vordefinierten Wortpaaren.
2. Wähle ein Wort-Bild-Paar und gib das entsprechende Wort ein.
3. Überprüfe die Eingabe und erhalte Rückmeldungen zur Korrektheit.
4. Beende die Session und speichere den Fortschritt.
