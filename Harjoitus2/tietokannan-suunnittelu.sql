
--  1  Käyttäjä voi etsiä videoita antamalla sanan, joka esiintyy videon nimessä tai kuvauksessa.
--  2  Käyttäjä voi arvioida videon (peukku ylös tai alas) ja videon yhteydessä näkyy yhteenveto käyttäjien arvioista. Sama käyttäjä voi antaa vain yhden arvion tietylle videolle.
--  3  Videon alla näkyy kommentteja käyttäjiltä. Myös näissä voi antaa arvion samaan tapaan kuin videossa (peukku ylös tai alas, vain yksi arvio tietylle kommentille samalta käyttäjältä).
--  4  Käyttäjä voi perustaa oman kanavan ja julkaista siellä videoita. Kanavan sisällä kanavan omistaja voi luokitella videoita niiden aiheen mukaan.
--  5  Käyttäjä voi tilata toisen käyttäjän kanavan, jolloin hän saa tietoa uusista videoista.
--  6  Videoissa näkyy katsojien määrä ja kanavissa näkyy tilaajien määrä.
--  7  Käyttäjä voi luoda soittolistoja, joihin voi valita videoita eri kanavista. Soittolistan videoilla on tietty järjestys.
--  8  Käyttäjä voi lähettää viestin toiselle käyttäjälle sekä estää toista käyttäjää lähettämästä viestejä hänelle. 

  PRAGMA foreign_keys = ON;

  CREATE TABLE Kayttajat (
    id INTEGER PRIMARY KEY,
    username TEXT UNIQUE NOT NULL,
    email TEXT UNIQUE NOT NULL,
    salasana TEXT NOT NULL,
    CHECK (email LIKE '_%@__%.__%')
  );

  CREATE TABLE Kanavat (
    id INTEGER PRIMARY KEY,
    nimi TEXT UNIQUE NOT NULL,
    kayttaja_id INTEGER REFERENCES Kayttajat ON DELETE CASCADE NOT NULL
  );

  CREATE TABLE Videot (
    id INTEGER PRIMARY KEY,
    kuvaus TEXT,
    nimi TEXT NOT NULL,
    aihe TEXT,
    julkaisuaika DATETIME DEFAULT current_timestamp,
    video DATA,
    kanava_id INTEGER REFERENCES Kanavat ON DELETE CASCADE NOT NULL
  );

  CREATE TABLE Kommentit (
    id INTEGER PRIMARY KEY,
    kayttaja_id INTEGER REFERENCES Kayttajat ON DELETE SET NULL,
    viesti TEXT NOT NULL,
    video_id INTEGER REFERENCES Videot ON DELETE CASCADE NOT NULL,
    julkaisuaika DATETIME DEFAULT current_timestamp
  );

  CREATE TABLE Tykkaykset (
    id INTEGER PRIMARY KEY,
    kayttaja_id INTEGER REFERENCES Kayttajat ON DELETE CASCADE NOT NULL,
    video_id INTEGER REFERENCES Videot ON DELETE CASCADE DEFAULT NULL,
    kommentti_id INTEGER REFERENCES Kommentit ON DELETE CASCADE DEFAULT NULL,
    reaktio BOOLEAN NOT NULL,
    UNIQUE (video_id, kayttaja_id),
    UNIQUE (kommentti_id, kayttaja_id)
  );

  CREATE TABLE Tilaajat (
    id INTEGER PRIMARY KEY,
    tilaaja_id INTEGER REFERENCES Kayttajat ON DELETE CASCADE NOT NULL,
    kanava_id INTEGER REFERENCES Kanavat ON DELETE CASCADE NOT NULL,
    UNIQUE(tilaaja_id, kanava_id)
  );

  CREATE TABLE Katsontakerrat (
    id INTEGER PRIMARY KEY,
    katsoja_id INTEGER REFERENCES Kayttajat ON DELETE SET NULL,
    video_id INTEGER REFERENCES Videot ON DELETE CASCADE NOT NULL,
    katsottu DATETIME DEFAULT current_timestamp
  )

  CREATE TABLE Soittolistat (
    id INTEGER PRIMARY KEY,
    nimi TEXT NOT NULL, 
    kayttaja_id INTEGER REFERENCES Kayttajat ON DELETE CASCADE NOT NULL
  );

  CREATE TABLE Soittolistan_sisalto (
    id INTEGER PRIMARY KEY,
    video_id INTEGER REFERENCES Videot ON DELETE CASCADE NOT NULL,
    soittolista_id INTEGER REFERENCES Soittolistat ON DELETE CASCADE NOT NULL,
    jarjestysnro SERIAL,
    UNIQUE(jarjestysnro, soittolista_id)
  );

  CREATE TABLE Viestit (
    id INTEGER PRIMARY KEY,
    lahettaja_id INTEGER REFERENCES Kayttajat ON DELETE SET NULL,
    vastaanottaja_id INTEGER REFERENCES Kayttajat ON DELETE SET NULL,
    viesti TEXT,
    aika DATETIME DEFAULT current_timestamp,
    CHECK(lahettaja_id <> vastaanottaja_id)
  );

  CREATE TABLE Estetyt (
    id INTEGER PRIMARY KEY,
    kayttaja_id INTEGER REFERENCES Kayttajat ON DELETE CASCADE NOT NULL,
    estetty_id INTEGER REFERENCES Kayttajat ON DELETE CASCADE NOT NULL,
    UNIQUE (kayttaja_id, estetty_id)
  );

  CREATE TRIGGER tarkista_esto
  BEFORE INSERT ON Viestit
  WHEN NEW.lahettaja_id == (SELECT estetty_id FROM Estetyt WHERE NEW.lahettaja_id == estetty_id AND NEW.vastaanottaja_id == kayttaja_id)
  BEGIN
    SELECT RAISE(FAIL, 'vastaanottaja on estänyt lähettäjän');
  END;

  -- Lisätään käyttäjiä

INSERT INTO Kayttajat (username, email, salasana)
            VALUES ('abc', 'abc@abc.fi', 'salasana');
INSERT INTO Kayttajat (username, email, salasana)
            VALUES ('def', 'def@abc.fi', 'salasana');
INSERT INTO Kayttajat (username, email, salasana)
            VALUES ('ghi', 'ghi@abc.fi', 'salasana');
INSERT INTO Kayttajat (username, email, salasana)
            VALUES ('alssalks', 'alsalks@abc.fi', 'salasana');
INSERT INTO Kayttajat (username, email, salasana)
            VALUES ('12345484', '12345484', 'salasana');

-- Lisätään kanavia

INSERT INTO Kanavat (kayttaja_id, nimi)
            VALUES (1, 'ruokaohjeet');
INSERT INTO Kanavat (kayttaja_id, nimi)
            VALUES (2, 'pyöräilykanava');
INSERT INTO Kanavat (kayttaja_id, nimi)
            VALUES (1, 'leipomiskanava');

-- Lisätään videoita

INSERT INTO Videot (kuvaus, nimi, aihe, kanava_id)
            VALUES ('leivotaan raparperikakku', 'maailman paras raparperikakku', 'reseptit', 3);
INSERT INTO Videot (kuvaus, nimi, aihe, kanava_id)
            VALUES ('Timon kanssa tehtiin polkupyörän vuosihuolto', 'Näin teet polkupyörän vuosihuollon!', 'polkupyörät', 2);
INSERT INTO Videot (kuvaus, nimi, aihe, kanava_id)
            VALUES ('Ajoin pitkän pyörälenkin', 'Lassen pyörälenkki', 'polkupyörät', 2);
INSERT INTO Videot (kuvaus, nimi, aihe, kanava_id)
            VALUES ('Nykyään on saatavilla paljon erilaisia pyöriä. Mikä sopii minulle?', 'Mikä on sopiva pyörä minulle?', 'polkupyörät', 2);
INSERT INTO Videot (kuvaus, nimi, aihe, kanava_id)
            VALUES ('Timon kanssa tehtiin polkupyörän vuosihuolto', 'Näin teet polkupyörän vuosihuollon!', 'polkupyörät', 2);
INSERT INTO Videot (kuvaus, nimi, aihe, kanava_id)
            VALUES ('Timon kanssa tehtiin polkupyörän vuosihuolto', 'Näin teet polkupyörän vuosihuollon!', 'polkupyörät', 2);

-- Lisätään kommentteja videoihin

INSERT INTO Kommentit (kayttaja_id, viesti, video_id)
            VALUES (1, 'tuli tosi hyvä raparperikakku!!', 1);
INSERT INTO Kommentit (kayttaja_id, viesti, video_id)
            VALUES (3, 'mä en tykkää raparperistä', 1);
INSERT INTO Kommentit (kayttaja_id, viesti, video_id)
            VALUES (2, 'mun pyörä meni rikki näillä ohjeilla. Miten vaihdan renkaan?', 2);
INSERT INTO Kommentit (kayttaja_id, viesti, video_id)
            VALUES (4, 'Voitko viel opettaa, miten kitaraa soitetaan?', 250);
INSERT INTO Kommentit (kayttaja_id, viesti, video_id)
            VALUES (1, 'tuli tosi hyvä raparperikakku!!', 1);

-- Lisätään tykkäyksiä videoihin tai kommentteihin

INSERT INTO Tykkaykset (kayttaja_id, video_id, reaktio)
            VALUES (1, 1, 1);
INSERT INTO Tykkaykset (kayttaja_id, video_id, reaktio)
            VALUES (2, 1, 0);
INSERT INTO Tykkaykset (kayttaja_id, kommentti_id, reaktio)
            VALUES (1, 1, 1);
INSERT INTO Tykkaykset (kayttaja_id, kommentti_id, reaktio)
            VALUES (2, 1, 0);
INSERT INTO Tykkaykset (kayttaja_id, video_id, reaktio)
            VALUES (1, 1, 1);
INSERT INTO Tykkaykset (kayttaja_id, video_id, reaktio)
            VALUES (1, 1, 1);
INSERT INTO Tykkaykset (kayttaja_id, video_id, reaktio)
            VALUES (1, 1);

-- Lisätään Tilaajia kanaville

INSERT INTO Tilaajat (tilaaja_id, kanava_id)
            VALUES (1, 1);
INSERT INTO Tilaajat (tilaaja_id, kanava_id)
            VALUES (1, 2);
INSERT INTO Tilaajat (tilaaja_id, kanava_id)
            VALUES (1, 3);
INSERT INTO Tilaajat (tilaaja_id, kanava_id)
            VALUES (5, 1);
INSERT INTO Tilaajat (tilaaja_id, kanava_id)
            VALUES (2, 3);
INSERT INTO Tilaajat (tilaaja_id, kanava_id)
            VALUES (2, 4);   

-- Lisätään soittolistoja

INSERT INTO Soittolistat (nimi, kayttaja_id)
            VALUES ('ruokavideoita', 1);
INSERT INTO Soittolistat (nimi, kayttaja_id)
            VALUES ('pyörävideoita', 2);

-- Lisätään sisältöä soittolistoille. tietokantaa käyttävä sovellus käsitteleen järjestysnumeron muuttamisen.

INSERT INTO Soittolistan_sisalto (video_id, soittolista_id, jarjestysnro)
            VALUES (1,1,1),
                   (1,1,2),
                   (2,2,1),
                   (3,2,2),
                   (4,2,3);

-- Lisätään viestejä

INSERT INTO Viestit (lahettaja_id, vastaanottaja_id, viesti)
            VALUES (1, 2, 'mitäs kuuluu?'),
                   (2, 1, 'ihan hyvää');

-- Lisätään esto ja kokeillaan lähettää viesti

INSERT INTO Estetyt (kayttaja_id, estetty_id)
            VALUES (4, 3);
INSERT INTO Viestit (lahettaja_id, vastaanottaja_id, viesti)
            VALUES (3, 4, 'mitäs kuuluu?');