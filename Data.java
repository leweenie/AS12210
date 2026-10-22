public class Data {
    private final String cfg;
    private final int sc;

    public Data(String config, int score) { cfg = config; sc = score; }

    public String getConfiguration() { return cfg; }

    public int getScore() { return sc; }
}