import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;


public class GameBoard extends JFrame {
    private static final int SIZE = 8;
    private JPanel[][] squares = new JPanel[SIZE][SIZE];
    private Piece[][] piecesArray = new Piece[SIZE][SIZE];
    private List<Piece> allPieces = new ArrayList<>();

    public GameBoard() {
        setTitle("Chess Board (Sorted via Merge Sort)");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(SIZE, SIZE));

        initializeBoard();
        initializeAndShufflePieces();
        sortAndPlacePieces(); 
        updateBoard();
    }

    private void initializeBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                squares[row][col] = new JPanel(new BorderLayout());
                squares[row][col].setBackground((row + col) % 2 == 0 ? new Color(200, 200, 200) : new Color(100, 100, 100));
                add(squares[row][col]);
            }
        }
    }

    private void initializeAndShufflePieces() {
        // Define all pieces with ranks
        Piece[] correctPieces = {
            new Piece("Rook", "B", 0), new Piece("Knight", "B", 1), new Piece("Bishop", "B", 2), new Piece("Queen", "B", 3),
            new Piece("King", "B", 4), new Piece("Bishop", "B", 5), new Piece("Knight", "B", 6), new Piece("Rook", "B", 7),
            new Piece("Pawn", "B", 8), new Piece("Pawn", "B", 9), new Piece("Pawn", "B", 10), new Piece("Pawn", "B", 11),
            new Piece("Pawn", "B", 12), new Piece("Pawn", "B", 13), new Piece("Pawn", "B", 14), new Piece("Pawn", "B", 15),

            new Piece("Pawn", "W", 16), new Piece("Pawn", "W", 17), new Piece("Pawn", "W", 18), new Piece("Pawn", "W", 19),
            new Piece("Pawn", "W", 20), new Piece("Pawn", "W", 21), new Piece("Pawn", "W", 22), new Piece("Pawn", "W", 23),
            new Piece("Rook", "W", 24), new Piece("Knight", "W", 25), new Piece("Bishop", "W", 26), new Piece("Queen", "W", 27),
            new Piece("King", "W", 28), new Piece("Bishop", "W", 29), new Piece("Knight", "W", 30), new Piece("Rook", "W", 31)
        };

        allPieces.addAll(Arrays.asList(correctPieces));
        Collections.shuffle(allPieces);
    }

    private void sortAndPlacePieces() {
        mergeSort(allPieces, 0, allPieces.size() - 1);

        piecesArray = new Piece[SIZE][SIZE];

        int index = 0;
        
        for (int i = 0; i < SIZE; i++) {
            piecesArray[0][i] = allPieces.get(index++);
        }
        for (int i = 0; i < SIZE; i++) {
            piecesArray[1][i] = allPieces.get(index++);
        }
        for (int i = 0; i < SIZE; i++) {
            piecesArray[6][i] = allPieces.get(index++);
        }
        for (int i = 0; i < SIZE; i++) {
            piecesArray[7][i] = allPieces.get(index++);
        }
    }

    private void updateBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                squares[row][col].removeAll();
                if (piecesArray[row][col] != null) {
                    String pieceImage = piecesArray[row][col].getPieceImage();
                    ImageIcon icon = new ImageIcon(pieceImage);
                    Image scaledImage = icon.getImage().getScaledInstance(40, 60, Image.SCALE_SMOOTH);
                    squares[row][col].add(new JLabel(new ImageIcon(scaledImage)), BorderLayout.CENTER);
                }
                squares[row][col].revalidate();
                squares[row][col].repaint();
            }
        }
    }

    private void mergeSort(List<Piece> pieces, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(pieces, left, mid);
            mergeSort(pieces, mid + 1, right);
            merge(pieces, left, mid, right);
        }
    }

    private void merge(List<Piece> pieces, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        List<Piece> leftArray = new ArrayList<>(pieces.subList(left, mid + 1));
        List<Piece> rightArray = new ArrayList<>(pieces.subList(mid + 1, right + 1));

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (leftArray.get(i).getRank() <= rightArray.get(j).getRank()) {
                pieces.set(k++, leftArray.get(i++));
            } else {
                pieces.set(k++, rightArray.get(j++));
            }
        }

        while (i < n1) {
            pieces.set(k++, leftArray.get(i++));
        }

        while (j < n2) {
            pieces.set(k++, rightArray.get(j++));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameBoard board = new GameBoard();
            board.setVisible(true);
        });
    }

    static class Piece {
        private String type;
        private String color;
        private int rank;

        public Piece(String type, String color, int rank) {
            this.type = type;
            this.color = color;
            this.rank = rank;
        }

        public String getPieceImage() {
            return color + "_" + type + ".png";
        }

        public int getRank() {
            return rank;
        }
    }
}
