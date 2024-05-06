package testing;

public class HugeInteger {
    private boolean isPositive;
    private Node head;
    private Node tail;
    private int length;

    public HugeInteger() {
        this.isPositive = true;
        head = tail = null;
        length = 0;
    }

    public HugeInteger(String number) {
        if (number.startsWith("-")) {
            isPositive = false;
            number = number.substring(1);
        } else {
            isPositive = true;
        }

        // Handle leading zeros
        number = number.replaceAll("^0+", "");

        // Create nodes
        int digit;
        for (int i = number.length() - 1; i >= 0; i--) {
            digit = number.charAt(i) - '0';
            Node newNode = new Node(digit);
            if (head == null) {
                head = tail = newNode;
            } else {
                tail.next = newNode;
                newNode.prev = tail;
                tail = newNode;
            }
            length++;
        }
    }

    public HugeInteger addPositive(HugeInteger num2) {
        HugeInteger result = new HugeInteger();
        Node current1 = tail;
        Node current2 = num2.tail;
        int carry = 0;

        while (current1 != null || current2 != null || carry != 0) {
            int sum = carry;
            if (current1 != null) {
                sum += current1.data;
                current1 = current1.prev;
            }
            if (current2 != null) {
                sum += current2.data;
                current2 = current2.prev;
            }
            Node newNode = new Node(sum % 10);
            carry = sum / 10;
            newNode.next = result.head;
            if (result.head != null) {
                result.head.prev = newNode;
            }
            result.head = newNode;
            if (result.tail == null) {
                result.tail = newNode;
            }
        }
        return result;
    }

    public int compareTo(HugeInteger num2) {
        if (this.isPositive && !num2.isPositive) {
            return 1; // Positive is greater than negative
        } else if (!this.isPositive && num2.isPositive) {
            return -1; // Negative is less than positive
        } else {
            // Both have the same sign, compare absolute values
            Node current1 = head;
            Node current2 = num2.head;
            while (current1 != null && current2 != null) {
                if (current1.data > current2.data) {
                    return this.isPositive ? 1 : -1; // Positive greater, negative less
                } else if (current1.data < current2.data) {
                    return this.isPositive ? -1 : 1; // Positive less, negative greater
                }
                current1 = current1.next;
                current2 = current2.next;
            }
            // If digits are equal so far, longer number is greater
            if (current1 != null) {
                return this.isPositive ? 1 : -1; // Positive greater, negative less
            } else if (current2 != null) {
                return this.isPositive ? -1 : 1; // Positive less, negative greater
            } else {
                return 0; // Digits and lengths are equal
            }
        }
    }

    public void concatenateDigit(int digit) {
        Node nn = new Node(digit);
        if (head == null) {
            head = tail = nn;
            return;
        }
        else {
            nn.next = head;
            head.prev = nn;
            head = nn;
        }
        length++;
    }

    public void addLast(int digit) {
        Node nn = new Node(digit);
        if (head == null) {
            head = tail = nn;
            return;
        }
        else {
            tail.next = nn;
            nn.prev = tail;
            tail = nn;
        }
        length++;
    }

    public String toString() {
        if (head == null) {
            return "0";
        }
        StringBuilder s = new StringBuilder();
        Node temp = head; 
        while (temp != null) {
            s.insert(0, temp.data); 
            temp = temp.next;
        }
        if (!isPositive) {
            s.insert(0, '-');
        }
        if (s.length() == 0) {
            s.append("0");
        }
        return s.toString();
    }
}
