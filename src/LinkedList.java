public class LinkedList {
    private static class Node {
        int val;
        Node next;
        Node prev;

        Node(int val) {
            this.val = val;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    public long elementAccesses = 0;

    public LinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void add(int x) {
        Node newNode = new Node(x);
        if (size == 0) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
        elementAccesses++;
    }

    private Node getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node curr;
        if (index < size / 2) {
            curr = head;
            for (int i = 0; i < index; i++) {
                curr = curr.next;
                elementAccesses++;
            }
        } else {
            curr = tail;
            for (int i = size - 1; i > index; i--) {
                curr = curr.prev;
                elementAccesses++;
            }
        }
        elementAccesses++;
        return curr;
    }

    public void add(int index, int x) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (index == size) {
            add(x);
            return;
        }
        Node newNode = new Node(x);
        if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
            elementAccesses++;
        } else {
            Node target = getNode(index);
            Node prevNode = target.prev;
            prevNode.next = newNode;
            newNode.prev = prevNode;
            newNode.next = target;
            target.prev = newNode;
            elementAccesses++;
        }
        size++;
    }

    public int remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node target = getNode(index);
        if (target.prev != null) {
            target.prev.next = target.next;
        } else {
            head = target.next;
        }
        if (target.next != null) {
            target.next.prev = target.prev;
        } else {
            tail = target.prev;
        }
        size--;
        elementAccesses++;
        return target.val;
    }

    public int get(int index) {
        return getNode(index).val;
    }

    public boolean contains(int x) {
        Node curr = head;
        while (curr != null) {
            elementAccesses++;
            if (curr.val == x) return true;
            curr = curr.next;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public void resetMetrics() {
        this.elementAccesses = 0;
    }
}