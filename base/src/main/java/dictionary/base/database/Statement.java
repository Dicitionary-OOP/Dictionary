package dictionary.base.database;

public class Statement {
    public static String getAllWord() {
        return "SELECT word FROM words";
    }

    public static String getExamples() {
        final StringBuilder query = new StringBuilder();
        query.append("SELECT *");
        query.append("FROM examples ");
        query.append("GROUP BY explain_id");
        query.append("WHERE explain_id = ?");
        return query.toString();
    }

    public static String getWordExplains() {
        final StringBuilder query = new StringBuilder();
        query.append("SELECT explains.explain_id, explains.type, explains.meaning ");
        query.append("FROM explains ");
        query.append("INNER JOIN words USING(word_id) ");
        query.append("WHERE words.word = ?");
        return query.toString();
    }
}
