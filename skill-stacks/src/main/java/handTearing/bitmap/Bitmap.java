package handTearing.bitmap;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-04-17
 * @Description: 手写bitmap
 * @Version: 1.0
 */
public class Bitmap {
    private final byte[] bitmap;
    private final int size;

    public Bitmap(int size) {
        this.size = size;
        // +7非2次幂向上取整
        this.bitmap = new byte[(size + 7) / 8];
    }

    public void set(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        bitmap[index / 8] |= (byte) (1 << (index % 8));
    }

    public void clear(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        bitmap[index / 8] &= (byte) ~(1 << (index % 8));
    }

    public boolean get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return (bitmap[index / 8] & (1 << (index % 8))) != 0;
    }

    public int size() {
        return size;
    }

    public static void main(String[] args) {
        Bitmap bitmap = new Bitmap(10);
        bitmap.set(3);
        bitmap.set(7);

        System.out.println(bitmap.get(3));
        System.out.println(bitmap.get(7));
        System.out.println(bitmap.get(8));

        bitmap.clear(3);
        System.out.println(bitmap.get(3));
    }
}
