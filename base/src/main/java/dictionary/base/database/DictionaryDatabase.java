package dictionary.base.database;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dictionary.base.WordExplain;
import dictionary.base.utils.Utils;

public class DictionaryDatabase extends Database {
    public DictionaryDatabase(final String databaseFile) throws SQLException {
        super(databaseFile);
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

    public WordExplain getWordExplain(final String word) throws SQLException {
        final StringBuilder query = new StringBuilder();
        query.append("select * from explains ");
        query.append("inner join words using(word_id) ");
        query.append(String.format("where words.word = %s", word));

        final ResultSet resultSet = executeQuery(query.toString());
        return new WordExplain(resultSet.getString("meaning"));
    }

    public void addLanguage() throws SQLException {
    }

    public void addWord() throws SQLException {
    }

    public void addExplain() throws SQLException {
    }
}
