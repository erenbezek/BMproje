public class HammingDecoder {
    public static int detectError(String encodedData) {
        int n = encodedData.length();
        int r = 0;
        while (Math.pow(2, r) < n) r++;
        int errorPosition = 0;
        for (int i = 0; i < r; i++) {
            int parityPos = (int) Math.pow(2, i);
            int count = 0;
            for (int j = 1; j <= n; j++) {
                if (((j >> i) & 1) == 1) {
                    int idx = n - j;
                    if (encodedData.charAt(idx) == '1') count++;
                }
            }
            if (count % 2 != 0) errorPosition += parityPos;
        }
        return errorPosition;
    }

    public static String correctError(String encodedData, int errorBitPosition) {
        if (errorBitPosition < 1 || errorBitPosition > encodedData.length()) return encodedData;
        int idx = encodedData.length() - errorBitPosition;
        char[] chars = encodedData.toCharArray();
        chars[idx] = (chars[idx] == '1') ? '0' : '1';
        return new String(chars);
    }
}
