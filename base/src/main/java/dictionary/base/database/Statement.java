package dictionary.base.database;

public class Statement {
    public static String getAllWord() {
        return "SELECT * FROM words";
    }

    public static String getExplainsByWord() {
        final StringBuilder query = new StringBuilder();
        query.append("SELECT * ");
        query.append("FROM explains ");
        query.append("INNER JOIN words USING(word_id) ");
        query.append("WHERE words.word = ? ");
        return query.toString();
    }

    public static String getExamplesByExplainID() {
        final StringBuilder query = new StringBuilder();
        query.append("SELECT *");
        query.append("FROM examples ");
        query.append("GROUP BY explain_id");
        query.append("WHERE explain_id = ?");
        return query.toString();
    }

    public static String getWordsLike() {
        final StringBuilder query = new StringBuilder();
        query.append("SELECT * ");
        query.append("FROM words ");
        query.append("WHERE words.word LIKE ?");
        return query.toString();
    }

    public static String addWord(){
        final StringBuilder query = new StringBuilder();
        query.append("INSERT INTO words (word, pronounce) ");
        query.append("VALUES (?, ?)");
        return query.toString();
    }

    public static String removeWord(){
        final StringBuilder query = new StringBuilder();
        query.append("DELETE FROM words ");
        query.append("WHERE word_id = ?");
        return query.toString();
    }

    /**.
     *Create SQL querry to add explain(tạo câu lệnh để thêm 1 giải thích).
     *
     * @return the string is SQL querry.
     */
    public static String addExplain(){
        final StringBuilder query = new StringBuilder();
        query.append("INSERT INTO explains (word_id, type, meaning) ");
        query.append("VALUES (?, ?, ?)");
        return query.toString();
    }
    /**.
     *Create SQL querry to remove explain(tạo câu lệnh để xóa 1 giải thích).
     *
     * @return the string is SQL querry.
     */

    public static String removeExplain(){
        final StringBuilder query = new StringBuilder();
        query.append("DELETE FROM explains ");
        query.append("WHERE word_id = ?");
        return query.toString();
    }
}
