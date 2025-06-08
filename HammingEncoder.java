class HammingEncoder {
    public static String encode(String dataBits) {
        dataBits = new StringBuilder(dataBits).reverse().toString();
        int m = dataBits.length();
        int r = 0;
        while (Math.pow(2, r) < m + r + 1) r++;

        int totalBits = m + r + 1;
        char[] encoded = new char[totalBits];
        for (int i = 0; i < totalBits; i++) encoded[i] = '0';

        int dataIndex = 0;
        for (int i = 1; i <= totalBits; i++) {
            if (!isPowerOfTwo(i) && i != 1 && dataIndex < dataBits.length()) {
                encoded[totalBits - i] = dataBits.charAt(dataIndex++);
            }
        }

        for (int i = 0; i < r; i++) {
            int parityPos = (int) Math.pow(2, i);
            int parityIdx = totalBits - parityPos;
            int count = 0;
            for (int j = 1; j <= totalBits; j++) {
                if (((j >> i) & 1) == 1 && j != 1) {
                    int idx = totalBits - j;
                    if (encoded[idx] == '1') count++;
                }
            }
            encoded[parityIdx] = (count % 2 == 1) ? '1' : '0';
        }

        int ones = 0;
        for (int i = 0; i < totalBits - 1; i++) {
            if (encoded[i] == '1') ones++;
        }
        encoded[totalBits - 1] = (ones % 2 == 1) ? '1' : '0';

        return new String(encoded);
    }

    private static boolean isPowerOfTwo(int x) {
        return (x & (x - 1)) == 0 && x != 0;
    }
}