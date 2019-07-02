## PROGETTO DI PROGRAMMAZIONE AD OGGETTI

a cura di **Samuele Leli**

## Scopo e Descrizione

Lo scopo del progetto era quello di realizzare una piattaforma web che restituiva dei dati, ottenuti da un dataset a seconda della richiesta da parte dell'utente, in formato JSON attraverso il framework SPRING MVC.
Il dataset assegnato contiene le informazioni riguardanti le strutturre extra-alberghiere di Milano.
All'avvio del programma viene scaricato il dataset e il file geojson.
Le richieste sono state gestite utilizzando il protocollo GET a rotte distinte e a seconda della richiesta GET vengono restituiti dati diversi.

REST GET consentite: 

 `localhost:8080`
* `/data`: restituisce tutti i dati del dataset
 
* `/data/filtro?` : restituisce i dati a seconda dei parametri scelti (attraverso '&' si possono combinare più filtri)
  * TipoAttivita : filtro per tipo di attività
  *  Camere: filtro per numero di camere disponibili
   * Municipio: filtro per zona
	* Map: può assumere valore true o false , nel caso sia true restituisce i valori filtrati in formato GeoJSON, in caso non sia presente nella richiesta restituirà i valori in formato json      
* `/data/filtro/{operazione}?` : restituisce i dati a seconda dei parametri scelti (attraverso '&' si possono combinare più filtri), dove
{operazione} può assumere valore "maggiore" o "minore" (da utilizzare solo nel caso di camere).
	* "maggiore": restituisce i dati delle strutture che hanno più camere rispetto al valore assegnato al parametro "Camere"
  * "minore": restituisce i dati delle strutture che hanno meno camere rispetto al valore assegnato al parametro "Camere"
  * "maggioreuguale": restituisce i dati delle strutture che hanno più o lo stesso numero di camere rispetto al valore assegnato al parametro "Camere"
  * "minoreuguale": restituisce i dati delle strutture che hanno meno o lo stesso numero di camere rispetto al valore assegnato al parametro "Camere"
   Sono ammessi gli stessi parametri del filtro precedente.
* `/data/stats?` : restituisce gli elementi unici e le occorrenze a seconda del parametro scelto (solo per stringhe)
	* Field: campo di cui si vogliono trovare gli elementi unici e le 	occorrenze

* `/metadata`: restituisce i metadati ovvero nomi degli attributi e tipo
 * `/map`: restituisce tutti i dati del dataset in formato geojson in modo da poter essere visualizzata in una mappa (ES: geojson.io)

**ESEMPI :**

* [http://localhost:8080/data/filtro?TipoAttivita=BedeBreakfast&Camere=3&Map=true&Municipio=1](h) : 
 restituisce i dati in formato geoJSON (Map=true) corrispondenti al tipo di attività ricettiva Bed & Breakfast con 3 camere e situato nel municipio 1
 * [http://localhost:8080/data](h) : restituisce tutti i dati
* [http://localhost:8080/metadata](h) :restituisce i metadati (Nomi attributi, record e tipi)
* [http://localhost:8080/map](h) : restituisce tutti i dati in formato geoJSON
* [http://localhost:8080/data/stats?Field=TipoAttivita](h) : restuisce il numero di occorrenze per ogni elemento unico (solo stringhe)
* [http://localhost:8080/data/filtro/minore?TipoAttivita=BedeBreakfast&Camere=3&Map=false&Municipio=1](h) :
  restituisce i dati in formato JSON (Map=false) corrispondenti al tipo di attività ricettiva Bed & Breakfast con meno di 3 camere e situato nel municipio 1

**VALORI CONSENTITI (per filtri e statistiche)**

Per quanto riguarda i valori consentiti per i vari filtri, si possono ricorrere alle funzioni di cui sopra. Per esempio se si vuole sapere quali sono i valori consentiti per TipoAttivita si sfruttano le stats con Field=TipoAttivita, in questo modo verranno stampati tutti gli elementi unici dei tipi di attività presenti nel dataset. In questo modo attraverso il filtro si possono trovare tutti i dati d'interesse relativo all'attività.
Per i tipi numerici (quali Camere e Municipio) sono consentiti solo valori numerici.
## DIAGRAMMI UML (CARTELLA /Diagrams)
**DIAGRAMMA DELLE CLASSI**

**DIAGRAMMA DEI CASI D'USO**

**DIAGRAMMA DELLE SEQUENZE**
