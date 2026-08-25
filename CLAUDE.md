# GM Screen

Rundenbasiertes Kampfsystem für das Brettspiel **Dark Star**. Solo-Projekt.
Regelquelle: `Dark_Star_Offline_Kompendium_1_6_2.pdf` (70 Seiten, Version 1.6.2).

## Rolle in diesem Projekt

Mentor und Pair-Programmer, **kein Code-Generator**. Der Nutzer kann Java; ihm fehlen
Ökosystem-Erfahrung (Maven-Multi-Modul, Spring, Vaadin) und Struktur, nicht Syntax.

- **Infrastruktur** (poms, Konfiguration, Boilerplate, Test-Setup) → vollständig von Claude.
- **Fachlogik** (alles in `engine`) → Claude entwirft Signaturen und schreibt die JUnit-Tests,
  Methodenrümpfe bleiben `// TODO`. **Der Nutzer implementiert.** Danach ehrliches Review.
- **UI** → ein View gemeinsam, den Rest baut der Nutzer nach dem Muster.

Regeln: immer nur **eine Phase pro Durchgang**, danach stoppen und auf Go warten.
Erst erklären, dann bauen. Maximal ~100 Zeilen Code am Stück. Kein Vorziehen.
Erklärungen auf Deutsch, Code und Commit-Messages auf Englisch.

## Die drei Architektur-Grundregeln

1. **`engine` kennt die UI nicht.** Keine Dependencies außer JUnit im Test-Scope.
   Kein Spring, kein Vaadin, kein Jackson. Ein Import, der nicht `java.*` ist, ist ein Fehler.
   `app` hängt von `engine` ab, niemals umgekehrt.
2. **Actions rein, Events raus.** `CombatEngine.apply(Action) -> List<GameEvent>`.
   Ein Event ist ein Fakt über Geschehenes. Die UI rendert Events und rechnet nichts nach.
3. **Zufall wird injiziert, nie erzeugt.** `Random` kommt in den Konstruktor.
   Niemals `Math.random()` oder `new Random()` in der Logik.

Zusatzregel: Mechaniken werden in Java ausprogrammiert, nur **Inhalte** (Gegnerwerte,
Klassenwerte, Skill-Zahlen) kommen ab Phase 10 aus JSON. Keine generische Regel-Engine.

## Struktur

```
gm-screen/
├── pom.xml          Parent, packaging=pom, verwaltet alle Versionen
├── engine/          Reines Java. Die Spielregeln.
│   └── .../engine/{model,action,event,effect,ai}
└── app/             Spring Boot + Vaadin. Hängt von engine ab.
    └── .../app/{session,content,persistence,ui}
```

**Warum der JSON-Loader in `app` liegt und nicht in `engine`:** Jackson ist eine
Dependency. Läge der Loader in `engine`, hätte `engine` eine Fremdabhängigkeit und
Regel 1 wäre gebrochen. `engine` definiert nur die Typen; `app/content` liest JSON
und *baut* daraus engine-Objekte. Die Engine erfährt nie, dass es JSON gibt.

**Warum Spring Boot als BOM importiert und nicht als Parent-POM gesetzt ist:**
Ein `spring-boot-starter-parent` würde Spring-Plugin-Konfiguration in *jedes* Modul
drücken, auch in `engine`. Der BOM-Import verwaltet nur Versionen.

**Warum `junit-bom` vor `spring-boot-dependencies` steht:** Bei Maven gewinnt der
zuerst importierte BOM. Andersherum wäre die `junit.version`-Property wirkungslos.

## Bauen

Maven ist **nicht** installiert, das Projekt nutzt den Wrapper. Überall wo im
Fahrplan `mvn` steht, gilt:
```
.\mvnw.cmd clean test                    # Windows
./mvnw clean test                        # Git Bash
.\mvnw.cmd -pl app -am spring-boot:run   # App starten
```

`-pl app` allein schlägt fehl, weil `engine` nicht im lokalen Repository liegt.
`-am` (also-make) baut die benötigten Module mit.

Versionen (Stand Phase 0, verifiziert gegen maven-metadata.xml):
Java 21 · Spring Boot 4.1.1 · Vaadin 25.2.6 · JUnit 6.1.3 · Maven 3.9.16

Node.js 24 ist lokal installiert; `vaadin.require-home-node=true` verhindert,
dass Vaadin sich eine eigene Kopie zieht.

## Regelbefunde aus dem Kompendium

Wichtige Abweichungen vom ursprünglichen Briefing, geprüft am PDF:

- **Es gibt keine Initiative.** Kein Initiative-Wert, keine Regel dafür. Das Spiel
  läuft über **Heldenphase → Gegnerphase**; eine "Runde" ist beides zusammen.
  Phase 2 baut daher *keine* Initiative-Sortierung.
- **Es gibt keinen Trefferwurf.** Angriffe treffen immer. Schaden ist
  `Würfel + Angriff − Verteidigung`. Der W20 dient nur kritischen Treffern
  (natürliche 20) und klassenspezifischen Sondermechaniken.
- **Jeder** der 19 Helden hat eine eigene Marker-Ressource. Das ist Kernmechanik,
  gehört in Phase 1 ins Domänenmodell.
- Fähigkeiten referenzieren Evo-Waffen-Stufen. Wir implementieren zunächst
  **Stufe 1 ohne Evo** und lassen Tier-Modifikatoren weg.
- **AP-Startregel:** Runde 1 starten alle mit 0 AP, Regeneration ab Runde 2.
  In Runde 1 steht also nur der 0-AP-Grundangriff zur Verfügung.

Offene Regelfragen liegen als GitHub-Issues mit Label `rules-question`.

## MVP-Umfang

Das Kompendium enthält ~380 Regelobjekte (19 Helden × 5 Fähigkeiten, 34 Gegner,
58 Items, 19 Evo-Waffen × 5 Stufen). Für Block A bis C gilt:

- **Klassen:** Mercenary Fighter, Chaos Paladin, High Priest, Demonic Archer
- **Gegner:** Goblin, Knight, Wolf, Archer (alle Basic)
- **Nicht jetzt:** Beschwörer, Transformationen, Bosse (Phasen-Gates, Skalierung),
  Items, Evo-Waffen, Eidola

## Git-Workflow

Direkt auf `main`. Keine Feature-Branches, keine PRs. Commit-Messages schließen
Issues mit `Closes #<nr>`. Labels: `phase`, `bug`, `idea`, `rules-question`.

## Phasenstand

**Phase 0 und 1 abgeschlossen.** Gerüst steht, Domänenmodell steht, 22 Tests grün.

Entwurfsentscheidungen aus Phase 1, die weiter tragen:

- **`record` für Werte ohne Identität, Klasse für Dinge mit Lebenslauf.**
  `Stats`, `MarkerType` und `CharacterClass` sind unveränderliche Records — sie
  beschreiben, *was* etwas ist. `Combatant` und `CombatState` sind veränderliche
  Klassen, weil sich ihr Zustand mehrmals pro Runde ändert.
- **`Combatant` ist `sealed`** mit exakt zwei Erben, `Hero` und `Enemy`. Ab Phase 6
  lässt sich darüber ohne `default`-Zweig pattern-matchen.
- **Ausnahmekonvention:** `null` wirft `NullPointerException` (meist über
  `Objects.requireNonNull`), vorhandene aber unbrauchbare Werte werfen
  `IllegalArgumentException`. Gilt im ganzen Modul.
- **Marker sind Kernmechanik**, nicht Sonderfall — jeder der 19 Helden hat genau
  eine Ressource. `Hero.setMarker` kappt auf `[0, Maximum]`, weil das Kompendium
  überzählige Marker verfallen lässt statt sie abzulehnen.
- Noch **keine Mutatoren** für LP und AP. Die kommen in Phase 2 (AP) und 3 (Schaden).

Als Nächstes: **Phase 2 — Rundenlauf**. Heldenphase → Gegnerphase, Rundenzähler,
AP-Regeneration. Keine Initiative, siehe Regelbefunde oben.

**Blockiert:** Phase 2 hängt an den Regelfragen #14 (Aktionen pro Zug) und
#16 (Reihenfolge innerhalb einer Phase). Beide müssen vom Spieldesigner beantwortet
werden, bevor der Rundenlauf festgeschrieben wird.
