class Solution {
    static class Node {
        int prod;
        int[] cnt;

        Node(int k) {
            cnt = new int[k];
            prod = 1 % k;
        }
    }

    int n, k;
    int[] nums;
    Node[] tree;

    public int[] resultArray(int[] nums, int k, int[][] queries) {
        this.n = nums.length;
        this.k = k;
        this.nums = nums;

        tree = new Node[4 * n];
        build(1, 0, n - 1);

        int[] result = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            int index = queries[i][0];
            int value = queries[i][1];
            int start = queries[i][2];
            int x = queries[i][3];

            nums[index] = value;
            update(1, 0, n - 1, index, value);

            Node ans = query(1, 0, n - 1, start, n - 1);
            result[i] = ans.cnt[x];
        }

        return result;
    }

    void build(int node, int left, int right) {
        if (left == right) {
            tree[node] = createNode(nums[left]);
            return;
        }

        int mid = left + (right - left) / 2;

        build(node * 2, left, mid);
        build(node * 2 + 1, mid + 1, right);

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    void update(int node, int left, int right, int index, int value) {
        if (left == right) {
            tree[node] = createNode(value);
            return;
        }

        int mid = left + (right - left) / 2;

        if (index <= mid) {
            update(node * 2, left, mid, index, value);
        } else {
            update(node * 2 + 1, mid + 1, right, index, value);
        }

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    Node query(int node, int left, int right, int ql, int qr) {
        if (qr < left || right < ql) {
            return new Node(k);
        }

        if (ql <= left && right <= qr) {
            return tree[node];
        }

        int mid = left + (right - left) / 2;

        Node a = query(node * 2, left, mid, ql, qr);
        Node b = query(node * 2 + 1, mid + 1, right, ql, qr);

        if (isEmpty(a)) return b;
        if (isEmpty(b)) return a;

        return merge(a, b);
    }

    Node createNode(int value) {
        Node node = new Node(k);

        int rem = value % k;
        node.prod = rem;
        node.cnt[rem] = 1;

        return node;
    }

    Node merge(Node a, Node b) {
        Node result = new Node(k);

        result.prod = (a.prod * b.prod) % k;

        for (int r = 0; r < k; r++) {
            result.cnt[r] += a.cnt[r];

            int newRem = (a.prod * r) % k;
            result.cnt[newRem] += b.cnt[r];
        }

        return result;
    }

    boolean isEmpty(Node node) {
        int sum = 0;

        for (int value : node.cnt) {
            sum += value;
        }

        return sum == 0;
    }
}