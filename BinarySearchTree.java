package BinarySearchTree;

public class BinarySearchTree {
    public static class Node {
        public int key;
        public int value;
        Node left;
        Node right;

        @Override
        public String toString() {
            return "Node{" +
                    "key=" + key +
                    ", value=" + value + "}";
        }

        public Node(int key, int value) {
            this.key = key;
            this.value = value;

        }
    }

    private Node root = null;

    //查找
    public Node search(int key) {
        //从root出发 比较key和root的大小 判断从左子树还是右子树出发
        Node cur = root;
        while (cur != null) {
            if (key == cur.key) {
                return cur;
            }else if (key < cur.key) {
                //在左子树中找
                cur = cur.left;
            }else{
                //在右子树中找
                cur = cur.right;
            }
        }
        //没找到
        return null;
    }

    //插入
    //1. 找到合适位置
    //2. 把新节点放在合适位置
    //插入失败的情况：约定如果新节点在树中已经存在 则插入失败
    public boolean insert(int key, int value) {
        //1.空树
        if (root == null) {
            root = new Node(key, value);
            return true;
        }

        //2.非空树
        // 查找过程中记录父节点
        Node cur = root;
        Node parent = null;
        while (cur != null) {
            if (key == cur.key) {
                return false;
            }else if (key < cur.key) {
                //左子树找
                parent = cur;
                cur = cur.left;
            }else {
                parent = cur;
                cur = cur.right;
            }
        }
        //循环目的：记录新节点的parent
        //此时cur为空 找到合适位置 新节点应插入到parent的下方
        Node newNode = new Node(key, value);
        if (key < parent.key) {
            parent.left = newNode;
        }else {
            parent.right = newNode;
        }
        return true;
    }

    public static void preOrder(Node root) {
       if (root == null) {
           return;
       }
       System.out.print(root.key + " ");
       preOrder(root.left);
       preOrder(root.right);
    }
    public static void inOrder(Node root) {
        if (root == null) {
            return;
        }
        inOrder(root.left);
        System.out.print(root.key + " ");
        inOrder(root.right);
    }

    public boolean remove(int key) {
        Node cur = root;
        Node parent = null;
        //查找要删除元素位置
        while (cur != null) {
            if (key == cur.key) {
                //找到了
                removeNode(parent, cur);
                return true;
            }else if(key < cur.key) {
                //左子树中找
                parent = cur;
                cur = cur.left;
            }else {
                //右子树中找
                parent = cur;
                cur = cur.right;
            }
        }
        return false;
    }

    private void removeNode(Node parent, Node cur) { // 画图说明
        if (cur.left == null) {
            if (cur == root) {
                root = cur.right;
            }else if (cur == parent.left) {
                parent.left = cur.right;
            }else {
                parent.right = cur.right;
            }
        }else if (cur.right == null) {
            if (cur == null) {
                root = cur.left;
            }else if (cur == parent.left) {
                parent.left = cur.left;
            }else {
                parent.right = cur.left;
            }
        }else {
            //同时有左右子树的情况 在删除节点的右子树中找最左侧元素(替罪羊)
            //用替罪羊替代要删除节点 删除替罪羊

            Node scapeGoat = cur.right;
            Node goatParent = cur;
            //1.找替罪羊
            while(scapeGoat.left != null) {
                goatParent = scapeGoat;
                scapeGoat = scapeGoat.left;
            }
            //循环结束 scapegoat是替罪羊节点
            //2.替罪羊代替删除节点
            cur.key = scapeGoat.key;
            cur.value  =scapeGoat.value;
            //3.删除替罪羊
            if (scapeGoat == goatParent.left) {
                goatParent.left = scapeGoat.right;
            }else {
                //当前替罪羊节点是cur.right满足此分支
                goatParent.right = scapeGoat.right;
            }
        }
    }


    public static void main(String[] args) {
        //建立二叉搜索树
        BinarySearchTree tree = new BinarySearchTree();
        int[] arr = {9, 5, 2, 7 ,3, 6, 8};
        for(int x : arr) {
            tree.insert(x, 0);
        }

        System.out.println(tree.search(3));
        tree.remove(3);
        preOrder(tree.root);
        System.out.println();
        inOrder(tree.root);
        System.out.println();
    }

}
