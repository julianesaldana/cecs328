package notes;

public class notes_10_30_20 {
    public static void main(String[]arg){
        BinaryTree bst = new BinaryTree();
    }

    public static class BinaryTree{
        class Node{
            Node left, right, parent;
            int value;
        }

        Node root;

        public int getHeight(){
            return getHeight(root);
        }

        private int getHeight(Node n){
            if (n == null)
                return -1;

            int leftHeight = getHeight(n.left);
            int rightHeight = getHeight(n.right);

            return 1 + Math.max(leftHeight, rightHeight);
        }

        public int count(int x){
            return count(root, x);
        }

        private int count(Node n, int x){
            if (n == null)
                return 0;

            int leftCount = count(n.left, x);
            int rightCount = count(n.right, x);

            if (n.value == x)
                return 1 + leftCount + rightCount;
            else
                return leftCount + rightCount;

            return n != null && (n.value == x || contains(n.left, x) || contains(n.right, x));
        }

        public boolean contains(Node n, int x){
            if (n == null)
                return false;

            if (n.value == x)
                return true;

            boolean leftContains = contains(n.left, x);
            boolean rightContains = contains(n.right, x);
            return leftContains || rightContains;
        }
    }
}
