public class OffByN implements CharacterComparator {
    private int N;

    public OffByN(int N) {
        this.N = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        if (x - y == N || y - x == -1 * N) {
            return true;
        }
        return false;
    }
}
