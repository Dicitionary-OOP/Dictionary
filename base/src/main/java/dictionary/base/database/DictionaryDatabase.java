package dictionary.base.database;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dictionary.base.Word;
import dictionary.base.WordExplain;
import dictionary.base.utils.Utils;

public class DictionaryDatabase extends Database {
    public DictionaryDatabase() throws SQLException {
        super(Utils.getResource("/database/database.db"));
    }

    public void createTables() throws SQLException, FileNotFoundException, IOException {
        executeSQLFile(Utils.getResource("/sql/dictionary.sql"));
    }

    public ArrayList<String> getAllWords() throws SQLException {
        final ResultSet resultSet = executeQuery("select * from words");
        final ArrayList<String> words = new ArrayList<>();

        while (resultSet.next()) {
            words.add(resultSet.getString("word"));
        }

        return words;
    }

    public void addLanguage(String languageId, String languageName) throws SQLException {
        executeUpdate(String.format(("INSERT INTO languages(lang_id,lang_name) VALUES('%s','%s')"),
                languageId,
                languageName));
    }

    public void addWord(Word word) throws SQLException {
        executeUpdate(String.format(("INSERT INTO words(word,lang_id) VALUES('%s','%s')"),
                word.getTarget(),
                word.getLanguage().code));

        executeUpdate(
                String.format(("INSERT INTO explains(word,lang_id, meaning) VALUES('%s','%s','%s')"),
                        word.getTarget(),
                        word.getLanguage().code,
                        word.getExplain().getMeaning()));
    }

    public WordExplain getWordExplain(final String word) throws SQLException {
        final StringBuilder query = new StringBuilder();
        query.append("select * from explains ");
        query.append(String.format("where word = '%s'", word));

        final ResultSet resultSet = executeQuery(query.toString());
        final String type = resultSet.getString("type");
        final String pronounce = resultSet.getString("pronounce");
        final String meaning = resultSet.getString("meaning");
        final String example = resultSet.getString("examples");
        final String lang = resultSet.getString("lang_id");
        return new WordExplain(type, pronounce, meaning, example, lang);
    }
}
