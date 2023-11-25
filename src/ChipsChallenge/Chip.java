package ChipsChallenge;

class Chip extends Item {
    // Constructor to create a Chip at a specific location
    public Chip(int row, int col) {
        // Calls the constructor of the base class (Item) with the name "Chip"
        super("Chip", row, col);
    }
}
