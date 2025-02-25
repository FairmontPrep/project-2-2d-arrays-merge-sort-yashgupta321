import javax.swing.*;
import java.awt.*;
import java.util.*;


public class GameBoard extends JFrame {
    private static final int SIZE = 8;
    private JPanel[][] squares = new JPanel[SIZE][SIZE];
    private Piece[][] piecesArray = new Piece[SIZE][SIZE];

    public GameBoard() {
        setTitle("Chess Board");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(SIZE, SIZE));

        initializeBoard();
        initializeAndSortPieces();
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

    private void initializeAndSortPieces() {
        // Create white pieces
        Piece[] whitePieces = {
            new Piece("Rook", "W", 0), new Piece("Knight", "W", 1), new Piece("Bishop", "W", 2), new Piece("Queen", "W", 3),
            new Piece("King", "W", 4), new Piece("Bishop", "W", 5), new Piece("Knight", "W", 6), new Piece("Rook", "W", 7),
            new Piece("Pawn", "W", 8), new Piece("Pawn", "W", 9), new Piece("Pawn", "W", 10), new Piece("Pawn", "W", 11),
            new Piece("Pawn", "W", 12), new Piece("Pawn", "W", 13), new Piece("Pawn", "W", 14), new Piece("Pawn", "W", 15)
        };

        // Create black pieces
        Piece[] blackPieces = {
            new Piece("Rook", "B", 0), new Piece("Knight", "B", 1), new Piece("Bishop", "B", 2), new Piece("Queen", "B", 3),
            new Piece("King", "B", 4), new Piece("Bishop", "B", 5), new Piece("Knight", "B", 6), new Piece("Rook", "B", 7),
            new Piece("Pawn", "B", 8), new Piece("Pawn", "B", 9), new Piece("Pawn", "B", 10), new Piece("Pawn", "B", 11),
            new Piece("Pawn", "B", 12), new Piece("Pawn", "B", 13), new Piece("Pawn", "B", 14), new Piece("Pawn", "B", 15)
        };

        // Merge sort for white and black pieces
        mergeSort(whitePieces, 0, whitePieces.length - 1);
        mergeSort(blackPieces, 0, blackPieces.length - 1);

        // Place the sorted white pieces
        for (int i = 0; i < 8; i++) {
            piecesArray[0][i] = whitePieces[i]; // First row for white
            piecesArray[1][i] = whitePieces[i + 8]; // Second row for white pawns
        }

        // Place the sorted black pieces
        for (int i = 0; i < 8; i++) {
            piecesArray[6][i] = blackPieces[i + 8]; // Seventh row for black pawns
            piecesArray[7][i] = blackPieces[i]; // Last row for black
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

    private void mergeSort(Piece[] pieces, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(pieces, left, mid);
            mergeSort(pieces, mid + 1, right);
            merge(pieces, left, mid, right);
        }
    }

    private void merge(Piece[] pieces, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        Piece[] leftArray = new Piece[n1];
        Piece[] rightArray = new Piece[n2];

        System.arraycopy(pieces, left, leftArray, 0, n1);
        System.arraycopy(pieces, mid + 1, rightArray, 0, n2);

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (leftArray[i].getRank() <= rightArray[j].getRank()) {
                pieces[k] = leftArray[i];
                i++;
            } else {
                pieces[k] = rightArray[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            pieces[k] = leftArray[i];
            i++;
            k++;
        }

        while (j < n2) {
            pieces[k] = rightArray[j];
            j++;
            k++;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameBoard board = new GameBoard();
            board.setVisible(true);
        });
    }

    // Piece class representing each chess piece
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
            String piece = color + "_" + type + ".png";
            return piece;
        }

        public int getRank() {
            return rank;
        }
    }
}
