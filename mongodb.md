    Missä muodossa tieto tallennetaan MongoDB-tietokantaan?
    
    Tieto tallennetaan MongoDB-tietokantaan JSON-muodon kaltaisessa formaatissa, jota on helppo käyttää Javascript-ohjelmointikielen kanssa. JSON-tallennusformaatti on luotu sellaiseksi, jota ihmisen on helppo lukea ja kirjoittaa ja jota tietokoneen on helppo käsitellä. Tallennettava tieto jäsennetään nimi-arvo-pareihin esimerkiksi { nimi : "Joonas" }, { koko: "6 kg" } ja näitä tietoja voidaan listata taulukkoon. Useimmissa ohjelmistokielissä on JSON-formaattia vastaavia rakenteita tiedon tallentamista varten, joten se on hyvin yhteensopiva olemassa olevien ohjelmointikielien kanssa.
    
    Millainen on kieli, jolla MongoDB:tä käytetään? Anna muutama esimerkki kyselyistä, joilla haetaan tietoa tietokannasta.
    Kieli muistuttaa nykyaikaisia ohjelmointikieliä. Metodilla db.inventory.insertMany() voidaan lisätä useita tietueita tietokantaan. Komennon osa 'db' on tietokannan nimi. Sulkeiden sisään lisätään nimi-arvoparit hakasulkeuden ja aaltosulkeiden sisään esim: [ { item: "journal", qty: 25, status: "A", size: { h: 14, w: 21, uom: "cm" }, tags: [ "blank", "red" ] } ]. 
    
    Kun tietoa on lisätty tietokantaan, sitä voidaan hakea seuraavanlaisella komennolla: db.inventory.find({}). Kyseinen komento vastaa SQL-kielen SELECT * FROM db -komentoa. Se hakee kaikki tietokantaan tallennetut tiedot. MongoDB:n tietokanta ei kuitenkaan vastaa täysin SQL-mallista taulua. Kun halutaan löytää jotain tiettyä tietoa MongoDB-tietokannasta, kysely näyttää esimerkiksi tältä: db.inventory.find( { status: "D" } );. Kysely hakee kaikki dokumentit, joissa status-nimen arvo vastaa tekstijonoa "D". Samoin voidaan hakea esimerkiksi db.inventory.find( { qty: 25 } ), jolloin haetaan kaikki dokumentit, joiden qty vastaa lukuarvoa 25. Jos halutaan rajata sarakkeita, lisätään find-metodiin hakuehdon jälkeen pilkku, ja merkataan, mitkä sarakkeet halutaan näkyviksi, esim db.inventory.find( {}, { _id: 0, item: 1, status: 1 } );. Kun sarakkeen arvoksi laitetaan 1, sarake näkyy ja kun laitetaan 0, niin sarake piilotetaan.
    
    
    Mitä hyvää MongoDB:ssä on verrattuna SQL-tietokantoihin?
    MongoDB:tä varten on helpompi ohjelmoida sovelluksia, sillä sen kieli on dokumentti-orientoitunutta. Dokumentit vastaavat olio-ohjelmointikielissä olioita ja muun tyyppisissä ohjelmointikielissä voidaan muuttaa MongoDB:n dokumentit esimerkiksi listoiksi tai taulukoiksi. MongoDB:n tietokannat ovat käteviä erittäin suurten ja nopeasti muuttuvien tietojen tallentamiseen. MongoDB-tietokanta voidaan jakaa useammalle palvelimelle ja sen toiminta voidaan varmistaa helpommin. Tiedon haku MongoDB-tietokannoista on nopeampaa kuin SQl-tietokannoista.
    
    Mitä huonoa MongoDB:ssä on verrattuna SQL-tietokantoihin? 
    MongoDB:n tietokanta ei ole vahvasti määritelty, joten siihen voidaan tallentaa monenlaista tietoa ja jokaisella komennolla voidaan lisätä uusia sarakkeita/tietueita tietokantaan. Jos tästä eheydestä ei pidetä huolta, voi tietokannasta tulla tehoton ja sekava. Tiedot MongoDB:n tietokannassa eivät ole atomisia, vaan jokaiseen tietueeseen voidaan tallentaa monen muotoista tietoa ja esimerkiksi listoja. Normalisointisääntöjä ei voida myöskään hyödyntää MongoDB:n suunnittelussa. Tietokannat voivat olla kooltaan suurempia MongoDB:ssä, sillä tiedon toisteisuutta ei ole optimoitu. Tämä on oikeastaan loogista, sillä SQL:ssä indeksointi lisää toisteisuutta, mutta nopeuttaa kyselyitä. MongoDB:ssä huonona puolena on myös se, ettei relaatioita voida samalla tavalla merkitä tietokantaan, kuten SQL:ssä.
    
    
    Lähteet: 
    
    https://www.mongodb.com/what-is-mongodb
    
    https://docs.mongodb.com/manual/tutorial/getting-started/
    
    https://www.w3resource.com/mongodb/introduction-mongodb.php
    
    https://en.wikipedia.org/wiki/JSON
    
    https://www.json.org/json-en.html
    
    https://petrikonkka.com/fi/pilvipalvelujen-tietokannat-nosql-mongodb/
    
    https://www.mongodb.com/nosql-explained/nosql-vs-sql
