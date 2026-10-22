public class HashDictionary implements DictionaryADT {
    private static final int Z = 131;

    private static class Node {
        Data d;
        Node nxt;
        Node(Data d, Node nxt) { this.d = d; this.nxt = nxt; }
    }

    private final Node[] tab;
    private int cnt = 0;

    public HashDictionary(int size) {
        tab = new Node[size];
    }

    private int hash(String s) {
        long h = 0;
        for (int i = 0; i < s.length(); i++) h = (h * Z + s.charAt(i)) % tab.length;
        return (int) h;
    }

    public int put(Data p) throws DictionaryException {
        String k = p.getConfiguration();
        int h = hash(k);
        for (Node x = tab[h]; x != null; x = x.nxt)
            if (x.d.getConfiguration().equals(k)) throw new DictionaryException();
        int col = tab[h] != null ? 1 : 0;
        tab[h] = new Node(p, tab[h]);
        cnt++;
        return col;
    }

    public void remove(String k) throws DictionaryException {
        int h = hash(k);
        for (Node x = tab[h], pr = null; x != null; pr = x, x = x.nxt) {
            if (!x.d.getConfiguration().equals(k)) continue;
            if (pr == null) tab[h] = x.nxt;
            else pr.nxt = x.nxt;
            cnt--;
            return;
        }
        throw new DictionaryException();
    }

    public int get(String k) {
        for (Node x = tab[hash(k)]; x != null; x = x.nxt)
            if (x.d.getConfiguration().equals(k)) return x.d.getScore();
        return -1;
    }

    public int numRecords() {
        return cnt;
    }
}