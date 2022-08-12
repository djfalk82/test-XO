import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


/**
 * Tic Tac Toe game.
 * class XO contains the main method, a constructor and a subclass called XOCanvas
 */
public class XO {

    private final char[][] myArray = new char[3][3];
    private char player = 'X';
    boolean gameActive = true;


    /**
     * Constructor
     * Has an initialised internal char array to store data. (myArray)
     * Has a JFrame with a instance of the subclass XOCanvas added to it.
     */
    public XO(){

        for(int i=0; i<3; i++) {
            for (int j = 0; j < 3; j++) {
                myArray[i][j] = 'N';
            }
        }

        JFrame f = new JFrame("Tik Tac Toe");

        XOCanvas canvas = new XOCanvas();
        canvas.addMouseListener(canvas);

        f.add(canvas);

        f.setLayout(null);
        f.setSize(500,600);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

  //  public static void main(String[] args ){

    //    new XO();
    //}

    /**
     * Subclass of XO.
     * Extends Canvas (the paint(...) method is overridden )
     * Has a listener attached (implements interface MouseListener, with many empty
     *                          but required methods )
     * Method drawPlays() updates the internal array (myArray) and writes an X or O in a
     * square if required.
     * Method CheckEnd() checks whether there is now a winner or no more squares left.
     * Method WriteMessage() writes the text under the game play board
     * Method Paint(...) draws the board lines.
     *
     */
    class XOCanvas extends Canvas implements MouseListener{

        /**
         * Constructor
         */
        public XOCanvas(){
            setBackground(Color.WHITE);
            setSize(500,600);
        }

        /**
         * overwritten method for Canvas. Draws the starting board.
         * @param g
         */
        public void paint(Graphics g){

            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(50,50,400,400);
            g.setColor(Color.BLACK);

            g.drawLine(183,50,183,450);
            g.drawLine(317,50,317,450);
            g.drawLine(50,183,450,183);
            g.drawLine(50,317,450,317);

            WriteMessage();
        }

        /**
         * The text at the bottom of the screen : whose turn it is and whether the game
         * has finished.
         */
        public void WriteMessage(){

            Graphics g = this.getGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(50,451,400,150);
            g.setColor(Color.BLACK);
            g.setFont(g.getFont().deriveFont(30f));
            if (gameActive) {
                g.drawString("Player " + player + "'s turn.", 150, 520);
            }
            else {
                g.drawString("Game Finished " , 150, 520);
            }
        }

        /**
         * Checks whether three of the same type in a line.
         * Checks vertical lines, then horizontal lines, then the two diagonals.
         * If so, then a red line if drawn through the relevant squares.
         * At the end there is a check whether all squares have been taken up (game finished).
         * The gameActive global boolean variable is updated when relevant.
         */
        public void checkEnd(){

            Graphics g = this.getGraphics();
            g.setFont(g.getFont().deriveFont(60f));
            g.setColor(Color.RED);
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.RED);
            g2.setStroke(new BasicStroke(10));

            for(int i=0; i<3; i++){
                if ( (myArray[0][i]==myArray[1][i]) &&
                        (myArray[0][i]==myArray[2][i]) &&
                        (myArray[0][i]!='N')){
                    g2.drawLine(116,116+i*(133),383,116+i*(133));
                    gameActive =false;
                }
                else if ( (myArray[i][0]==myArray[i][1]) &&
                        (myArray[i][0]==myArray[i][2]) &&
                        (myArray[i][0]!='N')){
                    g2.drawLine(116+i*(133),116,116+i*(133),383);
                    gameActive =false;
                }
                else if ((myArray[2][0]==myArray[1][1])&&
                        (myArray[0][2]==myArray[1][1])&&
                        (myArray[2][0]!='N')){
                    g2.drawLine(383,116,116,383);
                    gameActive =false;
                }
                else if ((myArray[0][0]==myArray[1][1])&&
                        (myArray[2][2]==myArray[1][1])&&
                        (myArray[0][0]!='N')){
                    g2.drawLine(116,116,383,383);
                    gameActive =false;
                }
            }
            boolean existSquares = false;
            for(int j=0;j<3;j++){
                for (int k=0; k<3; k++){
                    if (myArray[j][k]=='N'){
                        existSquares = true;
                    }
                }
            }
            if (!existSquares){
                gameActive =false;
            }
            WriteMessage();
        }


        /**
         * The changes to the internal array (myArray) is drawn here. A letter X or O
         * is drawn in the middle of the square.
         */
        public void drawPlays(){

            Graphics g = this.getGraphics();
            g.setFont(g.getFont().deriveFont(60f));

            for (int i=0; i<3; i++){
                for (int j=0; j<3; j++){
                    char c = myArray[i][j];
                    if (c!='N'){
                        g.drawString(String.valueOf(c),100+i*130,135+j*133);
                    }
                }
            }
            checkEnd();
        }


        /**
         * When the mouse is clicked (and the game is active [gameActive==true]),
         * it's position is checked to ensure it was a click
         * inside one of the boxes. The column and row ('col' and 'row') is determined
         * at the same time. This corresponds to the position inside the internal
         * array (myArray).
         * If this position hasn't been already taken it is assigned the relevant
         * value (character 'X' or 'O'). The nextplayer is then called ('player' moves to
         * X from O or vice versa)
         *
         * @param e
         */
        @Override
        public void mouseClicked(MouseEvent e) {

            if (!gameActive){
                return;
            }

            int x= e.getX();
            int y = e.getY();
            int col;
            int row;

            if (x>50 && x<183){
                col = 0;
            }
            else if (x>183 && x<317){
                col = 1;
            }
            else if (x>317 && x<450){
                col = 2;
            }
            else{
                return;
            }
            if (y>50 && y<183){
                row = 0;
            }
            else if (y>183 && y<317){
                row = 1;
            }
            else if (y>317 && y<450){
                row = 2;
            }
            else{
                return;
            }
            //System.out.println("row is : "+ row + ", column is : "+ col);
            if (myArray[col][row] =='N'){
                myArray[col][row] = player;
                if (player=='X'){
                    player = 'O';
                }
                else{
                    player = 'X';
                }
            }

            drawPlays();
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }


}
