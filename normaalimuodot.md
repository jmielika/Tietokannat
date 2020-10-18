myynnit kassaan kirjaamalla myydyn kirjan nimen, hinnan ja aiheen. Lisäksi talteen jää myyjän nimi ja asiakkaan maksutapa, ja vapaaehtoisesti tallennetaan asiakkaan käyttämä kanta-asiakaskortti ja asiakkaan sähköpostiosoite.


Normaalimuotoja käytetään poistamaan relaatiomallisten tietokantojen toisteisuutta, eli sitä, ettei samoja tietoja tallenneta useasti tietokantaan, ja tallennetun tiedon eheyttä, eli sitä, että tieto, joka on tallennettu on oikeaa. Toisteisen tiedon tallentaminen vie turhaa tilaa (=lisääntyneet kustannukset tallennustilasta), hidastaa ja vaikeuttaa tietojan muokkaamista tietokannassa ja vaikeuttaa tiedon hakemista tietokannasta. Normalisoinnilla helpotetaan myös tietokannan rakenteen muuttamista.

Tietokannoissa tieto tallennetaan tauluihin, joita voisi kuvailla vaikka taulukoiksi, joissa on ensimmäisellä rivillä eri sarakkeiden otsikot ja seuraavilla riveillä tauluun tallennettu tieto oikean otsikon alla.  Taulun perusavain tarkoittaa sarakkeita/saraketta, joilla yksi taulun rivi voidaan yksilöidä uniikisti. Riippuen tietokannan rakenteesta perusavain voisi olla esimerkiksi henkilötunnus (yksiarvoinen perusavain) tai ostopäivämäärä ja asiakkaan nimi (moniarvoinen perusavain).

1. normaalimuoto

Ensimmäisen normaalimuodon tarkoituksena on poistaa toistuvat ryhmät ja moniarvoiset sarakkeet. Esimerkiksi tauluun on voitu tallentaa käyttäjiä ja puhelinnumeroita. Jos jollain käyttäjällä on useampi puhelinnumero, pitää puhelinnumeroille luoda uusi taulu, johon numerot tallennetaan. Tämä tilanne kuvaa moniarvoisen sarakkeen tilannetta. Toisessa esimerkissä tauluun on tallennettu asiakkaiden kuitteja ja kuitilla on sarakkeet ostetuille tuotteille, esimerkiksi tuote1, tuote2, tuote3 jne. Tuotteet täytyy 1. normaalimuodon periaatteiden mukaisesti poistaa (ovat moniarvoisia sarakkeita) ja luoda uusi taulu, johon voidaan lisätä esimerkiksi sarakkeet kuitin numero ja tuote, jolloin jokainen tuote tulee omalle rivilleen ja samalle riville tallennetaan kyseisen kuitin numero. Ensimmäiseen normaalimuotoon normalisoidaan, jotta tauluja ei tarvitse muuttaa, kun tietoa lisätään ja voidaan tallentaa monipuolisemmin eri tyyppisiä tietoja.

2. normaalimuoto
Toisessa normaalimuodossa taulu jaetaan, jos siinä on arvoja, jotka ovat nk. funktionaalisesti riippuvaisia vain osasta avainarvoista. Taulussa olevat sarakkeet eivät saa olla riippuvaisia muista kuin taulun perusavaimesta. Taulu on toisessa normaalimuodossa, kun se täyttää ensimmäisen normaalimuodon periaatteet ja lisäksi edellämainitun periaatteen. Jos taulussa on vain yksi perusavain, silloin taulu on käytönnössä aina toisessa normaalimuodossa. Kun taulun perusavain koostuu useammasta arvosta, pitää tarkistaa, että kaikki arvot ovat riippuvaisia kaikkiin perusavaimen arvoihin. Esimerkiksi teollisuualan yritys tilaa tuotteita suuria määriä eri toimittajilta. Taulun sarakkeina on toimittajan tunnus, raakamateriaalin tuotenumero, ostopäivämäärä, toimittajan nimi, raakamateriaalin nimike ja tilattu määrä. Jotta yksittäinen rivi voidaan tunnistaa uniikisti, taulun perusavaimena toimii sarakkeet toimittajan tunnus, raakamateriaalin tuotenumero ja ostopäivämäärä. Esimerkiksi raakamateriaalin nimike ja määrä eivät ole funktionaalisesti riippuvaisia toimittajan tunnuksesta. Taulu pitää jakaa useammaksi tauluksi, eli 1: taulu, jossa sarakkeina on Toimittajan tunnus ja toimittajan nimi, 2: taulu, jossa sarakkeina on raakamateriaalin tuotenumero ja nimike, ja 3: taulu, jossa kuvataan yksittäinen tilaus ja perusavaimena toimii toimittajatunnus, raakamateriaalin tuotenumero ja päivämäärä. Neljäs sarake tässä taulussa on tilatun materiaalin määrä. Näin yhdessäkään taulussa ei ole arvoja, jotka ovat funktionaalisesti riippuvaisia vain osasta perusavainta. Tämä on hyödyllistä, sillä tietoja ei tarvitse tallentaa montaa kertaa, kun osa tiedosta siirretään uusiin tauluihin. 

3. normaalimuoto

Kolmannessa normaalimuodossa taulussa olevan tiedon pitää olla funktionaalisesti riippuvaista vain perusavaimesta. Esimerkkinä henkilöistä ylläpidettävä tietokanta, jossa on sarakkeina henkilötunnus (perusavain), sukunimi, etunimi, katuosoite, postinumero ja postitoimipaikka. Katuosoite on henkilötunnuksen lisäksi riippuvainen postinumerosta ja postinumero on henkilötunnuksen lisäksi riippuvainen postitoimipaikasta. Osoitteet voidaan tallentaa omaan tauluunsa postinumeron kanssa ja postitoimipaikat voidaan tallentaa omaan tauluunsa postinumeroiden kanssa. Tämä normaalimuoto helpottaa yhä enemmän 

Boyce-Codd, 4. ja 5. normaalimuoto

Lisäksi on olemassa kolme muuta normaalimuotoa. Nämä muodot ovat usein käytännössä tarpeettomia, sillä ongelmat, joita niiden avulla ratkaistaan, ovat hieman keinotekoisia. Boyce-Codd normaalimuodossa määräävän ominaisuuden esiintyminen taulussa on kielletty, jollei kyseessä ole taulun avain. Esimerkiksi tauluun on tämän normaalimuodon periaatteilla turhaa tallentaa kahta eri tunnistavaa tietoa, esimerksiksi samaan tauluun ei pidä tallentaa henkilötunnusta ja työntekijänumeroa, toinen tieto riittää. Henkilötunnus ja työntekijänumero voidaan yhdistää erillisessä taulussa sitten. Boyce-Codd muotoa kutsutaan joskus 3.5 normaalimuodoksi.

4. normaalimuodossa arvot tallennetaan niin, että perusavaimeen liittyvät tiedot ovat riippuvaisia myös toisistaan. Esimerkiksi taulu, johon tallennetaan tieto kurssin nimestä, opettajasta ja luokkatilasta. Opettaja ja luokkatila eivät ole riippuvaisia toisistaan, joten pitäisi tallentaa kahteen tauluun; Toiseen tallennetaan kurssin nimi ja opettaja, ja toiseen tauluun tallennetaan kurssin nimi ja luokka, jossa opetus järjestetään.

5. normaalimuodossa pitää pyrkiä tallentamaan tieto tauluissa, joiden asteluku on pienin. Toisin sanoen jos on mahdollistaa tallentaa yhden taulun, jossa on esimerkiksi 5 saraketta, tiedot kahteen tauluun, jossa toisessa on 3 ja toisessa 2 saraketta, pitää ne tallentaa kahteen eri tauluun.

Vertaa normaalimuotoja kurssin materiaalin luvussa 5 annettuihin tietokannan suunnitteluperiaatteisiin. Mitä yhteistä näillä periaatteilla on normaalimuotojen kanssa? 
Normalisointi vastaa hyvin pitkälti kurssilla kerrottuja suunnitteluperiaatteita. Normalisoinnin ensimmäinen muoto vastaa periaatetta tiedon atomisuudesta. Samaan sarakkeeseen ei saa tallentaa moniarvoisia tietoja kuten listoja. Normalisointi vähentää toisteista tietoa. Jokainen tieto on tallennettu vain kerran yhteen paikkaan, joten tietojen muuttaminen on sujuvaa ja yksinkertaista. Suunnitteluperiatteet vievät tämän suhteen asian hieman pidemmälle; niiden perusteella tietokantaan ei saa tallentaa tietoa, joka voidaan laskea tai päätellä muusta tiedosta. Normalisointi ei ota kantaa tällaisen tiedon tallentamiseen. Tietokannan suunnittelun periaatteissa on yhteyksiä normalisoinnin funktionaalisiin riippuvuuksiin. Tietokannan suunnitteluperiaatteissa ei oteta kantaa, kuinka paljon toisteista tietoa on sopivaa. Tämä vertaantuukin hyvin BC-normaalimuodon, 4. ja 5. normaalimuodon käyttöön. Käytännössä 1.-3. normaalimuodot riittävät toisteisen tiedon poistamiseen ja epämääräinen raja järkevän normalisoinnin kannalta on jossain 3.-BC-normaalimuodon välillä.



http://homes.jamk.fi/~huojo/opetus/IIO30100/IIO30100m5_normalisointi.pdf

https://tim.jyu.fi/view/kurssit/tktl/itka204/kurssimoniste

https://fi.wikipedia.org/wiki/Tietokannan_normalisointi

https://docs.microsoft.com/fi-fi/office/troubleshoot/access/database-normalization-description

https://www.youtube.com/watch?v=UrYLYV7WSHM (tässä videossa oli joitain virheitä, mutta auttoi alkuun)

http://appro.mit.jyu.fi/2002/kevat/tietokannat/luennot/luento4/
