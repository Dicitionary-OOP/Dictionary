DROP TABLE IF EXISTS explains;
DROP TABLE IF EXISTS words;
DROP TABLE IF EXISTS languages;

CREATE TABLE languages (
    lang_id VARCHAR(255) NOT NULL PRIMARY KEY, 
    lang_name VARCHAR(255)
);

CREATE TABLE words (
    word VARCHAR(255) NOT NULL PRIMARY KEY,
    lang_id VARCHAR(255) NOT NULL,
    FOREIGN KEY (lang_id) REFERENCES languages(lang_id)
);

CREATE TABLE explains (
    explain_id INTEGER PRIMARY KEY, 
    word VARCHAR(255) NOT NULL,
    lang_id VARCHAR(255) NOT NULL,
    type VARCHAR(255),
    pronounce VARCHAR(255),
    meaning VARCHAR(255),
    examples VARCHAR(255),
    FOREIGN KEY (word) REFERENCES words(word),
    FOREIGN KEY (lang_id) REFERENCES languages(lang_id)
);

