public class Configurations {
    private static final int[][] dg = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
    private static final int[][] or = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private static final int ht = 9973;

    private final char[][] board;
    private final int sz, need;

    public Configurations(int board_size, int lengthToWin, int max_levels) {
        sz = board_size;
        need = lengthToWin;
        board = new char[sz][sz];
        for (char[] row : board) java.util.Arrays.fill(row, ' ');
    }

    public HashDictionary createDictionary() { return new HashDictionary(ht); }

    public int repeatedConfiguration(HashDictionary hashTable) {
        return hashTable.get(str());
    }

    public void addConfiguration(HashDictionary hashDictionary, int score) {
        try { hashDictionary.put(new Data(str(), score)); }
        catch (DictionaryException e) {}
    }

    public void savePlay(int row, int col, char symbol) { board[row][col] = symbol; }

    public boolean squareIsEmpty(int row, int col) { return board[row][col] == ' '; }

    public boolean wins(char symbol) {
        for (int r = 0; r < sz; r++)
            for (int c = 0; c < sz; c++)
                if (board[r][c] == symbol && (shape(r, c, symbol, dg) || shape(r, c, symbol, or)))
                    return true;
        return false;
    }

    public boolean isDraw() {
        for (char[] row : board)
            for (char ch : row)
                if (ch == ' ') return false;
        return !wins('X') && !wins('O');
    }

    public int evalBoard() {
        if (wins('O')) return 3;
        if (wins('X')) return 0;
        return isDraw() ? 2 : 1;
    }

    private String str() {
        StringBuilder sb = new StringBuilder();
        for (char[] row : board) sb.append(row);
        return sb.toString();
    }

    // center needs all 4 arms nonempty; total = 1 + sum of arm lengths
    private boolean shape(int r, int c, char s, int[][] dirs) {
        int tot = 1;
        for (int[] d : dirs) {
            int len = arm(r, c, d[0], d[1], s);
            if (len == 0) return false;
            tot += len;
        }
        return tot >= need;
    }

    private int arm(int r, int c, int dr, int dc, char s) {
        int len = 0;
        for (int i = r + dr, j = c + dc; i >= 0 && i < sz && j >= 0 && j < sz && board[i][j] == s; i += dr, j += dc)
            len++;
        return len;
    }
}